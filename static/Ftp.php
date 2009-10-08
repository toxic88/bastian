<?php

class Ftp
{
    const NO_PASSIV = 0;
    const PASSIV = 1;

    const NO_SSL = 0;
    const SSL = 1;

    const ASCII = 1;
    const BINARY = 2;

    const TIMEOUT_SEC = 0;
    const AUTOSEEK = 1;

    const FAILD = 0;
    const FINISHED = 1;
    const MOREDATA = 2;

    protected $_server;
    protected $_username;
    protected $_password;
    protected $_port = 21;
    protected $_timeout = 90;

    protected $_ssl = self::NO_SSL;
    protected $_passiv = self::PASSIV;

    protected $_connection = null;

    protected $_nbFile = null; // used for none blocking uploads / downloads

    public function __construct(array $options = array())
    {
        if (!empty($options)) {
            $this->setOptions($options);
        }
    }

    public function __destruct()
    {
        $this->close();
    }

    public function __sleep()
    {
        return array(
            '_server', '_username', '_password', '_port', '_timeout', '_ssl', '_passiv'
        );
    }

    public function __wakeup()
    {
        $this->_connection = null;
    }

    public function setOptions(array $options)
    {
        foreach ($options as $name => $value) {
            if (method_exists($this, ($method = 'set' . ucfirst($name)))) {
                $this->$method($value);
            }
        }

        return $this;
    }

    public function setServer($server)
    {
        $this->_server = $server;

        return $this;
    }

    public function setUsername($username)
    {
        $this->_username = $username;

        return $this;
    }

    public function setPassword($password)
    {
        $this->_password = $password;

        return $this;
    }

    public function setPort($port)
    {
        $this->_port = (int) $port;

        return $this;
    }

    public function setTimeout($timeout)
    {
        $this->_timeout = (int) $timeout;

        return $this;
    }

    public function setSsl($ssl)
    {
        if ($ssl === self::SSL || $ssl === self::NO_SSL) {
            $this->_ssl = $ssl;
        }

        return $this;
    }

    public function setPassiv($passiv)
    {
        if ($passiv === self::PASSIV || $passiv === self::NO_PASSIV) {
            $this->_passiv = $passiv;
        }

        return $this;
    }

    public function getUsername()
    {
        return $this->_username;
    }

    public function getPassword()
    {
        return $this->_password;
    }

    public function getServer()
    {
        return $this->_server;
    }

    public function getPort()
    {
        return $this->_port;
    }

    public function getTimeout()
    {
        return $this->_timeout;
    }

    public function getSsl()
    {
        return $this->_ssl;
    }

    public function getPassiv()
    {
        return $this->_passiv;
    }

    protected function _connect()
    {
        if (null !== $this->_connection) {
            return;
        }
        if ($this->getSsl() === self::SSL) {
            $this->_connection = ftp_ssl_connect($this->getServer(), $this->getPort(), $this->getTimeout());
        } else {
            $this->_connection = ftp_connect($this->getServer(), $this->getPort(), $this->getTimeout());
        }
        if (!$this->_connection) {
            $this->_connection = null;
            throw new Exception('Could not connect to server "' . $this->getServer() . '".');
        }
        $this->_login();
    }

    protected function _login()
    {
        if (!ftp_login($this->_connection, $this->getUsername(), $this->getPassword())) {
            throw new Exception('Could not login as "' . $this->getUsername() . '".');
        }
        if ($this->getPassiv() === self::PASSIV) {
            $this->_passiv();
        }
    }

    protected function _passiv()
    {
        if (!ftp_pasv($this->_connection, true)) {
            throw new Exception('Failed to enable passiv mode');
        }
    }

    public function close()
    {
        if (null !== $this->_connection) {
            if (!ftp_close($this->_connection)) {
                throw new Exception('Could not logout from "' . $this->getServer() . '".');
            }
        }

        return $this;
    }

    public function currentDir()
    {
        $this->_connect();
        if (!($dir = ftp_pwd($this->_connection))) {
            throw new Exception('Could not recieve the current directory.');
        }

        return $dir;
    }

    public function cd($directory)
    {
        $this->_connect();
        if (!ftp_chdir($this->connection, $directory)) {
            throw new Exception('Failed to change the directory to "' . $directory . '"');
        }

        return $this;
    }

    public function upload($local, $remote = '', $mode = self::BINARY, $startpos = 0, $nb = false)
    {
        $this->_connect();
        if ($local instanceof Zend_Config) {
            $local = $local->toArray();
        }
        if (is_array($local)) {
            $remote = $local['remote'];
            $mode = ($local['mode'] === self::ASCII) ? self::ASCII : self::BINARY;
            $startpos = (int) $local['startpos'];
            $nb = (bool) $local['nb'];
            $local = $local['local'];
        }

        if ($nb) {
            $result = $this->_nbupload($local, $remote, $mode, $startpos);
        } else {
            $result = $this->_upload($local, $remote, $mode, $startpos);
        }

        return $result;
    }

    protected function _upload($local, $remote, $mode, $startpos)
    {
        if (is_resource($local)) {
            $result = ftp_fput($this->_connection, $remote, $local, $mode, $startpos);
        } else {
            $result = ftp_put($this->_connection, $remote, $local, $mode, $startpos);
        }

        return $result ? self::FINISHED : self::FAILD;
    }

    protected function _nbupload($local, $remote, $mode, $startpos)
    {
        if ($this->_nbFile === $remote) {
            $result = ftp_nb_continue($this->_connection);
        } else {
            if (is_resource($local)) {
                $result = ftp_nb_fput($this->_connection, $remote, $local, $mode, $startpos);
            } else {
                $result = ftp_nb_put($this->_connection, $remote, $local, $mode, $startpos);
            }
        }

        $this->_nbFile = ($result === self::MOREDATA) ? $remote : null;

        return $result;
    }

    public function download($remote, $local = '', $mode = self::BINARY, $resumepos = 0, $nb = false)
    {
        $this->_connect();
        if ($remote instanceof Zend_Config) {
            $remote = $remote->toArray();
        }
        if (is_array($remote)) {
            $local = $remote['local'];
            $mode = ($remote['mode'] === self::ASCII) ? self::ASCII : self::BINARY;
            $resumepos = (int) $remote['resumepos'];
            $nb = (bool) $remote['nb'];
            $remote = $remote['remote'];
        }

        if ($nb) {
            $result = $this->_nbdownload($remote, $local, $mode, $resumepos);
        } else {
            $result = $this->_download($remote, $local, $mode, $resumepos);
        }

        return $result;
    }

    protected function _download($remote, $local, $mode, $resumepos)
    {
        if (is_resource($local)) {
            $result = ftp_fget($this->_connection, $local, $remote, $mode, $resumepos);
        } else {
            $result = ftp_get($this->_connection, $local, $remote, $mode, $resumepos);
        }

        return $result ? self::FINISHED : self::FAILD;
    }

    protected function _nbdownload($remote, $local, $mode, $resumepos)
    {
        if ($this->_nbFile === $remote) {
            $result = ftp_nb_continue($this->_connection);
        } else {
            if (is_resource($local)) {
                $result = ftp_nb_fget($this->_connection, $local, $remote, $mode, $resumepos);
            } else {
                $result = ftp_nb_get($this->_connection, $local, $remote, $mode, $resumepos);
            }
        }

        $this->_nbFile = ($result === self::MOREDATA) ? $remote : null;
        
        return $result;
    }

    public function chmod($chmod, $file)
    {
        $this->_connect();
        if (!ftp_chmod($this->_connection, $chmod, $file)) {
            throw new Exception('Failed to set the chmod  for "' . $file . '" to "' . $chmod . '".');
        }

        return $this;
    }

    protected function _renameOrMove($oldname, $newname)
    {
        return ftp_rename($this->_connection, $oldname, $newname);
    }

    public function rename($oldname, $newname)
    {
        $this->_connect();
        if (!$this->_renameOrMove($oldname, $newname)) {
            throw new Exception('Could not rename the file/directory "' . $oldname . '" to "' . $newname . '".');
        }

        return $this;
    }

    public function move($oldname, $newname)
    {
        $this->_connect();
        if (!$this->_renameOrMove($oldname, $newname)) {
            throw new Exception('Could not move the file/directory "' . $oldname . '" to "' . $newname . '".');
        }

        return $this;
    }

    public function raw($command)
    {
        $this->_connect();
        return ftp_raw($this->_connection, $command);
    }

    protected function _checkOption($option)
    {
        return $option === self::TIMEOUT_SEC || $option === self::AUTOSEEK;
    }

    public function setOption($option, $value)
    {
        $this->_connect(); 
        if (!$this->_checkOption($option)) {
            throw new Exception('Wrong option. Please use ' . __CLASS__ . '::TIMEOUT_SEC and ' . __CLASS__ . '::AUTOSEEK only!');
        }
        if (!ftp_set_option($this->_connection, $option, $value)) {
            throw new Exception('The option "' . $option . '" could not be set to "' . $value . '".');
        }

        return $this;
    }

    public function getOption($option)
    {
        $this->_connect(); 
        if (!$this->_checkOption($option)) {
            throw new Exception('Wrong option. Please use ' . __CLASS__ . '::TIMEOUT_SEC and ' . __CLASS__ . '::AUTOSEEK only!');
        }

        return ftp_get_option($this->_connection, $option);
    }

    public function ls($directory, $recrusiv = false)
    {
        $this->_connect();
        $directory = ((strrpos($directory, '/') + 1) == strlen($directory)) ? substr($directory, 0, - 1) : $directory;

        return $this->_ls($directory, $recrusiv);
    }

    protected function _ls($directory, $recrusiv, $returnNoFileInfo = false)
    {
        $rawfiles = ftp_rawlist($this->_connection, $directory, $recrusiv);

        $structure = array();
        $arraypointer = &$structure;
        foreach ($rawfiles as $rawfile) {
            if ($rawfile[0] == '/') { // change directory
                $paths = array_slice(explode('/', str_replace(':', '', $rawfile)), 1); // array('test', 'eins', 'subeins')
                $arraypointer = &$structure;
                foreach ($paths as $path) {
                    foreach ($arraypointer as $i => $file) { // find dir
                        if ($file['text'] == $path) {
                            $arraypointer = &$arraypointer[ $i ]['children']; // 'children' may does not exists at the moment but will be created
                            break;
                        }
                    }
                }
            } else if(!empty($rawfile)) { // handle files
                $info = preg_split("/[\s]+/", $rawfile, 9);        
                $arraypointer[] = array(
                    'name'   => $info[8],
                    'isDir'  => $info[0]{0} == 'd',
                    'size'   => $this->_byteconvert($info[4]),
                    'chmod'  => $this->_chmodnum($info[0]),
                    'date'   => strtotime(
                        implode(
                            ' ',
                            array(
                                $info[6],
                                $info[5],
                                ((strpos($info[7], ':') === false)
                                    ? $info[7]
                                    : (date('Y') . ' ' . $info[7])
                                )
                            )
                        )
                    ),
                    'raw'    => $rawfile
                );
            }
        }

        if ($returnNoFileInfo && count($structure) === 1) {
            $folders = explode('/', $directory);
            if ($folders[ count($folders) - 1 ] == $structure[0]['name']) {
                return array();
            }
        }

        return $structure;
    }

    private function _byteconvert($bytes)
    {
        $symbol = array('B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB');
        if ($bytes <= 0) {
            return $bytes . ' ' . $symbol[0];
        }
        $exp = floor( log($bytes) / log(1024) );
        return sprintf( '%.2f' . $symbol[ $exp ], ($bytes / pow(1024, floor($exp))) );
    }

    private function _chmodnum($chmod)
    {
        $trans = array('-' => '0', 'r' => '4', 'w' => '2', 'x' => '1');
        $chmod = substr(strtr($chmod, $trans), 1);
        $array = str_split($chmod, 3);
        return array_sum(str_split($array[0])) . array_sum(str_split($array[1])) . array_sum(str_split($array[2]));
    }

    public function getSize($file, $format = false)
    {
        $this->_connect();
        if (!($size = ftp_size($this->_connection, $file))) {
            throw new Exception('Could not get the filesize of "' . $file . '".');
        }

        return $format ? $this->_byteconvert($size) : $size;
    }

    public function mkdir($directory, $autoselect = false)
    {
        $this->_connect();
        if (!ftp_mkdir($this->_connection, $directory)) {
            throw new Exception('Could not create the directory "' . $directory . '".');
        }
        if ($autoselect) {
            $this->cd($directory);
        }

        return $this;
    }

    // todo: try to do it simpler
    public function remove($directory)
    {
        $this->_connect();
        $directory = ((strrpos($directory, '/') + 1) == strlen($directory)) ? substr($directory, 0, - 1) : $directory;
        $info = $this->_ls($directory, true, true);
        $this->_remove($directory, $info);

        if ( count($this->_ls($directory, false, false)) === 0 ) {
            ftp_rmdir($this->_connection, $directory);
        } else {
            ftp_delete($this->_connection, $directory);
        }

        return $this;
    }
    
    protected function _remove($directory, $info)
    {
        foreach ($info as $id => $file) {
            $filename = $directory . '/' . $file['name'];
            if (!$file['isDir']) {
                if (!ftp_delete($this->_connection, $filename)) {
                    echo 'delete "' . $filename . '"<br />';
                }
            } else if (count($file['children']) === 0) {
                if (!ftp_rmdir($this->_connection, $filename)) {
                    echo 'delete "' . $filename . '"<br />';
                }
            } else {
                $this->_remove($filename, $info[ $id ]);
            }
        }
    }

}

<?php

class Bootstrap extends Zend_Application_Bootstrap_Bootstrap
{

    protected function _initAcl()
    {
        $this->bootstrap(array('config', 'auth')); // this has to be init first
        $config = Zend_Registry::get('config');
        $data = Zend_Auth::getInstance()->getIdentity();

        $role = $data !== null ?
            $config->acl->roles->{$data->Rights} :
            $config->acl->roles->{0};

        $config->user->role = $role;

        $acl = new Zend_Acl();
        $acl->addRole('guest')
            ->addRole('member', 'guest')
            ->addRole('admin')

            ->addResource('index')
            ->addResource('error')
            ->addResource('auth')
            ->addResource('user')
            ->addResource('targetprotein')
            ->addResource('incubationprotocol')
            ->addResource('antibody')
            ->addResource('admin')

            ->allow('guest', 'error')
            ->allow('guest', 'auth')
            ->deny('guest', 'index')
            ->deny('guest', 'user')
            ->deny('guest', 'admin')

            ->allow('member', 'index')
            ->allow('member', 'user')
            ->allow('member', 'targetprotein')
            ->allow('member', 'incubationprotocol')
            ->allow('member', 'antibody')

            ->allow('admin'); // allow all

        $front = Zend_Controller_Front::getInstance();
        $front->registerPlugin(new Zion_Controller_Plugin_Acl($acl, $role));
    }

    protected function _initAuth()
    {
        Zend_Auth::getInstance()->setStorage(new Zend_Auth_Storage_Session('Antibodydb_Auth'));
    }

    protected function _initAutoload()
    {
        new Zend_Application_Module_Autoloader(array(
            'namespace' => 'Antibodydb',
            'basePath'  => dirname(__FILE__) . DIRECTORY_SEPARATOR . 'modules' . DIRECTORY_SEPARATOR . 'default'
        ));
    }

    protected function _initDbDefinition()
    {
        $this->bootstrap('config');
        $config = Zend_Registry::get('config');
        $tables = $config->db->tables;

        Zend_Registry::set('dbdefinition', new Zend_Db_Table_Definition(array(
            'Antibody' => array(
                'name'            => $tables->Antibody,
                'dependentTables' => array('Images'),
                'referenceMap'    => array(
                    'Targetprotein' => array(
                        'columns'       => 'fs_T_Targetprotein_id',
                        'refTableClass' => 'Targetprotein',
                        'refColumns'    => 'id'
                    ),
                    'Incubationprotocol' => array(
                        'columns'       => 'fs_T_Incubationprotocol_id',
                        'refTableClass' => 'Incubationprotocol',
                        'refColumns'    => 'id'
                    ),
                    'Comments' => array(
                        'columns'       => 'fs_T_Comments_id',
                        'refTableClass' => 'Comments',
                        'refColumns'    => 'id'
                    )
                )
            ),
            'Incubationprotocol' => array(
                'name'            => $tables->Incubationprotocol,
                'dependentTables' => array('Antibody'),
                'referenceMap'    => array(
                    'Bufferset' => array(
                        'columns'       => 'fs_T_Bufferset_id',
                        'refTableClass' => 'Bufferset',
                        'refColumns'    => 'id'
                    )
                )
            ),
            'Images' => array(
                'name'         => $tables->Images,
                'referenceMap' => array(
                    'Antibody' => array(
                        'columns'       => 'fs_T_Antibody_id',
                        'refTableClass' => 'Antibody',
                        'refColumns'    => 'id'
                    ),
                    'Lane' => array(
                        'columns'       => 'fs_T_Lane_id',
                        'refTableClass' => 'Lane',
                        'refColumns'    => 'id'
                    ),
                    'Scannersettings' => array(
                        'columns'       => 'fs_T_Scannersettings_id',
                        'refTableClass' => 'Scannersettings',
                        'refColumns'    => 'id'
                    ),
                    'SDS' => array(
                        'columns'       => 'fs_T_SDS_id',
                        'refTableClass' => 'SDS',
                        'refColumns'    => 'id'
                    )
                )
            ),
            'Lane' => array(
                'name'            => $tables->Lane,
                'dependentTables' => array('Images')
            ),
            'SDS' => array(
                'name'            => $tables->SDS,
                'dependentTables' => array('Images')
            ),
            'Scannersettings' => array(
                'name'            => $tables->Scannersettings,
                'dependentTables' => array('Images')
            ),
            'Targetprotein' => array(
                'name'            => $tables->Targetprotein,
                'dependentTables' => array('Antibody')
            ),
            'Comments' => array(
                'name'            => $tables->Comments,
                'dependentTables' => array('Antibody')
            ),
            'Bufferset' => array(
                'name'            => $tables->Bufferset,
                'dependentTables' => array('Incubationprotocol')
            ),
            'User' => array(
                'name' => $tables->User
            ),
            'Assessment' => array(
                'name' => $tables->Assessment
            )
        )));
    }

    protected function _initConfig()
    {
        Zend_Registry::set('config', new Zend_Config($this->getOptions(), true));
    }

    protected function _initFilter()
    {
        Zend_Filter::addDefaultNamespaces('Antibodydb_Filter');
    }

    protected function _initHtml()
    {
        $this->bootstrap('view');
        $view = $this->getResource('view');
        
        $view->doctype(Zend_View_Helper_Doctype::XHTML1_STRICT);
        $view->headMeta()->appendHttpEquiv('Content-Type', 'text/html; charset=UTF-8');
    }

    protected function _initParams()
    {
        if (get_magic_quotes_gpc()) {
            function stripslashes_deep($value)
            {
                $value = is_array($value) ?
                            array_map('stripslashes_deep', $value) :
                            stripslashes($value);

                return $value;
            }

            $_POST    = array_map('stripslashes_deep', $_POST);
            $_GET     = array_map('stripslashes_deep', $_GET);
            $_COOKIE  = array_map('stripslashes_deep', $_COOKIE);
            $_REQUEST = array_map('stripslashes_deep', $_REQUEST);
        }
    }

    protected function _initSession()
    {
        Zend_Session::rememberMe(); // defaults to 2 weeks
        Zend_Registry::set('session', new Zend_Session_Namespace('Antibodydb'));
    }

}

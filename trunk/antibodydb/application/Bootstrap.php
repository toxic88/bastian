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

        $acl = new Zend_Acl();
        $acl->addRole('guest')
            ->addRole('member', 'guest')
            ->addRole('admin')

            ->addResource('index')
            ->addResource('auth')
            ->addResource('user')
            ->addResource('error')
            ->addResource('targetprotein')
            ->addResource('incubationprotocol')
            ->addResource('antibody')
            ->addResource('admin')

            ->deny('guest', 'index')
            ->deny('guest', 'user')
            ->deny('guest', 'admin')
            ->allow('guest', 'auth')

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
        $autoloader = new Zend_Application_Module_Autoloader(array(
            'namespace' => 'Antibodydb',
            'basePath'  => dirname(__FILE__)
        ));
        return $autoloader;
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

<?php

class Bootstrap extends Zend_Application_Bootstrap_Bootstrap
{

    protected function _initHtml()
    {
        $this->bootstrap('view');
        $view = $this->getResource('view');
        
        $view->doctype(Zend_View_Helper_Doctype::XHTML1_STRICT);
        $view->headMeta()->appendHttpEquiv('Content-Type', 'text/html; charset=UTF-8');
    }

    protected function _initConfig()
    {
        Zend_Registry::set('config', new Zend_Config($this->getOptions(), true));
    }

    protected function _initSession()
    {
        Zend_Registry::set('session', new Zend_Session_Namespace());
    }

    protected function _initAutoload()
    {
        new Zend_Application_Module_Autoloader(array(
            'namespace' => 'Application',
            'basePath'  => dirname(__FILE__) . DIRECTORY_SEPARATOR . 'modules' . DIRECTORY_SEPARATOR . 'default',
        ));
    }

    protected function _initAuth()
    {
        Zend_Auth::getInstance()->setStorage(new Zend_Auth_Storage_Session());
    }

    protected function _initFilter()
    {
        Zend_Filter::addDefaultNamespaces('Application_Filter');
    }

    protected function _initAcl()
    {
        $this->bootstrap(array('config', 'auth')); // this has to be init first
        $config = Zend_Registry::get('config');
        $data = Zend_Auth::getInstance()->getIdentity();

        $role = $data !== null ?
            $config->acl->roles->{$data->rights} :
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
            ->addResource('vokabeln')

            //->addResource('test')

            ->allow('guest', 'index')
            ->allow('guest', 'error')
            ->allow('guest', 'auth')

            ->allow('member', 'vokabeln')

            ->allow('admin'); // allow all
        
        $front = Zend_Controller_Front::getInstance();
        $front->registerPlugin(new Zion_Controller_Plugin_Acl($acl, $role));
    }

}

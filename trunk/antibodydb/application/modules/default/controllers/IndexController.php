<?php

class IndexController extends Zend_Controller_Action
{

    public function init()
    {

    }

    public function indexAction()
    {
        $this->view->headTitle('Antibodydb');

        $this->view->headLink()->appendStylesheet('css/ext.css')
                               ->appendStylesheet('css/Antibodydb.css');
        $this->view->headScript()->appendFile('js/ext.js')
                                 ->appendFile('js/Antibodydb.js');

        if($this->_isAdmin()) {
            $this->view->headTitle(' - Admin');
        }
    }

    private function _isAdmin()
    {
        $config = Zend_Registry::get('config');
        return $config->user->role === $config->acl->roles->{1};
    }

}

<?php

class IndexController extends Zend_Controller_Action implements Application_Controller_AjaxInterface
{

    public function init()
    {

    }

    public function indexAction()
    {
        $this->view->headTitle('Rebecca\'s Homepage');
        
        $this->view->headLink()->appendStylesheet('css/ext.css')
                               ->appendStylesheet('css/Application.css');
        $this->view->headScript()->appendFile('js/ext.js')
                                 ->appendFile('js/ext-lang-de.js')
                                 ->appendFile('js/Application.base.js')
                                 ->appendFile($this->getJavascriptFile());
    }

    public function getJavascriptFile()
    {
        return 'js/' . 'Application.' . Zend_Registry::get('config')->user->role . '.js';
    }

}

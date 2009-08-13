<?php

class TestController extends Zend_Rest_Controller
{

    const CONTEXT_JSON = 'json';

    public function init()
    {
        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->initContext(self::CONTEXT_JSON);

        $this->view->assign($this->getRequest()->getParams());
        $front     = Zend_Controller_Front::getInstance();
        $restRoute = new Zend_Rest_Route($front, array(), array(
            'default' => array('test')
        ));
        $front->getRouter()->addRoute('rest', $restRoute);
    }


    public function indexAction()
    {
        $this->view->myaction = 'index';
    }

    public function getAction()
    {
        $this->view->myaction = 'get';
    }
    
    public function putAction()
    {
        $this->view->myaction = 'put';
    }

    public function deleteAction()
    {
        $this->view->myaction = 'delete';
    }
    
    public function postAction()
    {
        $this->view->myaction = 'post';
    }

}

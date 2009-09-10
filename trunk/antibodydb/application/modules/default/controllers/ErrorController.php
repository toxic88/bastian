<?php

class ErrorController extends Zend_Controller_Action
{

    const CONTEXT_JSON = 'json';

    public function init()
    {
        $this->view->success = false;

        $contextSwitch = $this->_helper->contextSwitch();
        $contextSwitch->addActionContext('error',  self::CONTEXT_JSON)
                      ->addActionContext('denied', self::CONTEXT_JSON)
                      ->initContext();
    }

    public function errorAction()
    {
        $errors = $this->_getParam('error_handler');
        
        $this->getResponse()->setHttpResponseCode(503);

        switch ($errors->type) { 
            case Zend_Controller_Plugin_ErrorHandler::EXCEPTION_NO_CONTROLLER:
            case Zend_Controller_Plugin_ErrorHandler::EXCEPTION_NO_ACTION:
        
                // 404 error -- controller or action not found
                $this->getResponse()->setHttpResponseCode(404);
                $this->view->message = 'Page not found';
                break;
            default:
                // application error 
                $this->getResponse()->setHttpResponseCode(500);
                $this->view->message = 'Application error';
                break;
        }
        $this->getResponse()->clearBody();
        $this->view->params    = $errors->request->getParams();
        $this->view->error     = $errors->exception->getMessage();
        $this->view->errors    = $errors->exception->getTraceAsString();
    }

    public function deniedAction()
    {
        if(!Zend_Auth::getInstance()->hasIdentity() && !$this->getRequest()->isXmlHttpRequest()) {
            $this->_redirect('/auth');
        }
    
        $this->getResponse()->setHttpResponseCode(403);
        $this->view->error   = 'Access denied';
    }

}

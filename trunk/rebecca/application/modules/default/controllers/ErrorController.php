<?php

class ErrorController extends Zend_Controller_Action implements Application_Controller_AjaxInterface
{

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
    	$this->view->headTitle('Error');

        $errors = $this->_getParam('error_handler');

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

        $this->view->type      = $errors->type;
        $this->view->request   = $errors->request;

        $this->view->error     = $errors->exception->getMessage();
        $this->view->trace     = $errors->exception->getTraceAsString();
    }

    public function deniedAction()
    {
    	$this->view->headTitle('Access denied!');

        $this->getResponse()->setHttpResponseCode(403);
        $this->view->message   = 'Access denied!';
    }

}

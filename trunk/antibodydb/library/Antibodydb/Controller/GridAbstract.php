<?php

abstract class Antibodydb_Controller_GridAbstract extends Zend_Controller_Action implements Antibodydb_Controller_AjaxInterface
{

    protected $_model;

    public function init()
    {
        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->addActionContext('select',  self::CONTEXT_JSON)
                    ->addActionContext('create',  self::CONTEXT_JSON)
                    ->addActionContext('remove',  self::CONTEXT_JSON)
                    ->addActionContext('destroy', self::CONTEXT_JSON)
                    ->initContext();

        $this->view->success = false;
    }

    public function selectAction()
    {
        try {
            $data = $this->_model->select($_POST);
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->assign($data);
        $this->view->success = true;
    }

    public function removeAction()
    {
        $this->view->data = new stdClass();

        try {
            $this->_model->remove(array(
                'id' => $_POST['data']
            ));
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->success = true;
    }

    public function createAction()
    {
        $data = Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY);
        $this->view->data = new stdClass(); // default data

        try {
            $data = $this->_model->create($data);
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->data = $data;
        $this->view->success = true;
    }

    public function updateAction()
    {
        $data = Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY);
        $this->view->data = new stdClass(); // default data

        try {
            $data = $this->_model->update($data);
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->data = $data;
        $this->view->success = true;
    }

}

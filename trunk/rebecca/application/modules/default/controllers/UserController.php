<?php

class UserController extends Zend_Controller_Action
{

    const CONTEXT_JSON = 'json';

    protected $_model;

    public function init()
    {
        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->addActionContext('select',  self::CONTEXT_JSON)
                    ->addActionContext('create',  self::CONTEXT_JSON)
                    ->addActionContext('update',  self::CONTEXT_JSON)
                    ->addActionContext('destroy', self::CONTEXT_JSON)
                    ->initContext();

        $this->_model = new Application_Model_DbTable_User();

        $this->view->success = false;
    }

    public function selectAction()
    {
        try {
            $data = $this->_model->select($_POST);
        } catch (Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->assign($data);
        $this->view->success = true;
    }

    /**
     * 1. set default return data
     * 2. check if the row exists
     * 3. delete the row
     */
    public function destroyAction()
    {
        $this->view->data = new stdClass();

        try {
            $this->_model->destroy(array(
                'id' => $_POST['data']
            ));
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->success = true;
    }

    /**
     * 1. validate userinput
     * 2. set default return data
     * 3. checking required fields
     * 4. prevent the same username
     * 5. create password hash
     * 6. create and save the row
     */
    public function createAction()
    {
        $data = Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY);
        $this->view->data = new stdClass(); // default data

        try {
            $data = $this->_model->create($data);
        } catch (Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->data = $data;
        $this->view->success = true;
    }

    /**
     * 1. valdate userinput
     * 2. set the defaut return data
     * 3. prevent the same username
     * 4. check if the row exists
     * 5. create password hash
     * 6. setting the modified data and update the row
     */
    public function updateAction()
    {
        $data = Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY);
        $this->view->data = new stdClass(); // default data

        try {
            $data = $this->_model->update($data);
        } catch (Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->data = $data;
        $this->view->success = true;
    }

}

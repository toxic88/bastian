<?php

class ChatController extends Zend_Controller_Action implements Application_Controller_AjaxInterface
{

    protected $_message;
    protected $_user;

    public function init()
    {
        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->addActionContext('refresh',            self::CONTEXT_JSON)
                    ->addActionContext('send.message',       self::CONTEXT_JSON)
                    ->addActionContext('change.status',      self::CONTEXT_JSON)
                    ->addActionContext('change.status.text', self::CONTEXT_JSON)
                    ->initContext();

        $this->view->success = false;

        $this->_message = new Application_Model_DbTable_ChatMessage();
        $this->_user = new Application_Model_DbTable_ChatUser();
        // check if user allready exist in the database (maybe this can be done in the change status, because you first have to go online to chat)
    }

    /**
     * just for testing
     *
    public function indexAction()
    {
        // $rowset = $this->_user->getTable()->find(1);
        // $row = $rowset->current();
        // var_dump($row->findDependentRowset('rebecca_user'));

        $rowset = $this->_message->getTable()->find(1);
        $row = $rowset->current();
        var_dump($row->findDependentRowset('rebecca_chat_user', 'from'));
        var_dump($row->findDependentRowset('rebecca_chat_user', 'to'));
    }
     */

    public function sendMessageAction()
    {
        $data = Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY);
        $data['from'] = Zend_Auth::getInstance()->getIdentity()->id;
        $data['send_date'] = date('Y-m-d H:i:s');

        try {
            $this->_message->create($data);
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->success = true;
    }

    public function refreshAction()
    {
        /**
         * messages:
         * where('(to = {my user id} OR to = 0) AND (read = 0 OR send_date > {last checked date}')
         * status
         * where('status_change_date > {last checked date}')
         * status_text
         * where('status_text_change_date > {last checked date}')
         *
         * note: after that set the read flag to 1
         */

        $this->view->success = true;
    }

    public function changeStatusAction()
    {
        $data = Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY);
        $data['status_change_date'] = date('Y-m-d H:i:s');

        // update userinfo

        $this->view->success = true;
    }

    public function changeStatusTextAction()
    {
        $data = Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY);
        $data['status_text_change_date'] = date('Y-m-d H:i:s');

        try {
            $this->_user->update($data);
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->success = true;
    }

}

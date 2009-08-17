<?php

abstract class ChatController extends Zend_Controller_Action implements Application_Controller_AjaxInterface
{

    public function init()
    {
        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->addActionContext('refresh',            self::CONTEXT_JSON)
                    ->addActionContext('send.message',       self::CONTEXT_JSON)
                    ->addActionContext('change.status',      self::CONTEXT_JSON)
                    ->addActionContext('change.status.text', self::CONTEXT_JSON)
                    ->initContext();

        $this->view->success = false;
    }

    public function sendMessageAction()
    {
        /**
         * to (0 = all)
         * from
         * message
         * send_date (set by the server)
         */
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
         */
    }

    public function changeStatusAction()
    {
        /**
         * status
         * status_change_date (set by server)
         */
    }

    public function changeStatusTextAction()
    {
        /**
         * status_text
         * status_text_change_date (set by server)
         */
    }

}

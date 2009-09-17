<?php

class UserController extends Zend_Controller_Action implements Antibodydb_Controller_AjaxInterface
{
    protected $_table;

    public function init()
    {
        $this->view->success = false;
        
        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->addActionContext('change.password', self::CONTEXT_JSON)
                    ->initContext();

        $this->_table = new Antibodydb_Db_Table('User', Zend_Registry::get('dbdefinition'));
    }

    public function changePasswordAction()
    {
        $oldpassword  = $this->getRequest()->getPost('oldpassword');
        $newpassword1 = $this->getRequest()->getPost('newpassword1');
        $newpassword2 = $this->getRequest()->getPost('newpassword2');

        if(in_array(null, array($oldpassword, $newpassword1, $newpassword2), true)) {
            $this->view->error = 'Please fill in all fields.';
            return;
        }

        if($newpassword1 !== $newpassword2) {
            $this->view->error = 'New passwords do not match.';
            return;
        }

        if(strlen($newpassword1) < 4) {
            $this->view->error = 'The password has to be 4 characters long.';
            return;
        }

        $rows = $this->_table->find(Zend_Auth::getInstance()->getIdentity()->id);

        if($rows->count() <= 0) { // should never happend!
            $this->view->error = 'You are not logged in.';
            return;
        }

        $row = $rows->current();

        if($row->Password !== md5($oldpassword)) {
            $this->view->error = 'Old password is wrong.';
            return;
        }

        $row->Password = md5($newpassword1);
        $row->save();
		
        $this->view->success = true;
    }

}

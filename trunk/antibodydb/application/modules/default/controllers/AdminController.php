<?php

class AdminController extends Zend_Controller_Action implements Antibodydb_Controller_AjaxInterface
{

    protected $user;

    public function init()
    {
    	$this->view->success = false;

        $contextSwitch = $this->_helper->contextSwitch();
        $contextSwitch//->addActionContext('save', self::CONTEXT_JSON)
                      ->initContext();

		$this->user = Antibodydb_TableManager::get('User');
    }

}

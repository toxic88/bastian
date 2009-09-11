<?php

class IncubationController extends Zend_Controller_Action implements Antibodydb_Controller_AjaxInterface
{

	protected $anitbody;
	protected $incubationprotocol;
	protected $bufferset;

    public function init()
    {
    	$this->view->success = false;
    	
        $contextSwitch = $this->_helper->contextSwitch();
        $contextSwitch->addActionContext('save',   self::CONTEXT_JSON)
                      ->addActionContext('select', self::CONTEXT_JSON)
                      ->addActionContext('load',   self::CONTEXT_JSON)
                      ->addActionContext('delete', self::CONTEXT_JSON)
                      ->initContext();
    	
		$this->antibody           = Antibodydb_TableManager::get('Antibody');
		$this->incubationprotocol = Antibodydb_TableManager::get('Incubationprotocol');
		$this->bufferset          = Antibodydb_TableManager::get('Bufferset');
    }

}

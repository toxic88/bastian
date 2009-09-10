<?php

class AntibodyController extends Zend_Controller_Action
{

    const CONTEXT_JSON = 'json';
    
    protected $anitbody;
    protected $comments;
    
    public function init()
    {
    	$this->view->success = false;
    	
        $contextSwitch = $this->_helper->contextSwitch();
        $contextSwitch->addActionContext('save',   self::CONTEXT_JSON)
                      ->addActionContext('select', self::CONTEXT_JSON)
                      ->addActionContext('load',   self::CONTEXT_JSON)
                      ->addActionContext('delete', self::CONTEXT_JSON)
                      ->initContext();
    	
		$this->antibody = Antibodydb_TableManager::get('Antibody');
		$this->comments = Antibodydb_TableManager::get('Comments');
    }
    
    
}

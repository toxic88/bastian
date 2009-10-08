<?php

require_once 'ZendX/Ext/View/Helper/Container.php';

class ZendX_Ext_View_Helper_Toolbar extends ZendX_Ext_View_Helper_Container
{
	
	protected $_classname = 'Ext.Toolbar';
	
	public function __construct()
	{		
		$myConfig = array(
			'xtype' => 'toolbar'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function toolbar($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
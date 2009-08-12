<?php

require_once 'ZendX/Ext/View/Helper/Store.php';

class ZendX_Ext_View_Helper_ArrayStore extends ZendX_Ext_View_Helper_Store
{
	
	protected $_classname = 'Ext.data.ArrayStore';
	
	public function __construct()
	{
		$myConfig = array(
			'fields' => null,
			'id'     => null,
			'xtype'  => 'arraystore'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function arrayStore($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
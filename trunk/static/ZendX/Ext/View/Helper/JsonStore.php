<?php

require_once 'ZendX/Ext/View/Helper/Store.php';

class ZendX_Ext_View_Helper_JsonStore extends ZendX_Ext_View_Helper_Store
{
	
	protected $_classname = 'Ext.data.JsonStore';
	
	public function __construct()
	{
		$myConfig = array(
			'fields'          => null,
			'id'              => null,
			'idProperty'      => null,
			'root'            => null,
			'successProperty' => null,
			'totalProperty'   => null,
			'xtype'           => 'jsonstore'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function jsonStore($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
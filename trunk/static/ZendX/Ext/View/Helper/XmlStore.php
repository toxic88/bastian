<?php

require_once 'ZendX/Ext/View/Helper/Store.php';

class ZendX_Ext_View_Helper_XmlStore extends ZendX_Ext_View_Helper_Store
{
	
	protected $_classname = 'Ext.data.XmlStore';
	
	public function __construct()
	{
		$myConfig = array(
			'id'           => null,
			'idPath'       => null,
			'record'       => null,
			'success'      => null,
			'totalRecords' => null,
			'xtype'        => 'xmlstore'
		);
		$this->_apply($myConfig);
		
		$myRequired = array(
			'record'			
		);
		
		parent::__construct();
	}
	
	public function xmlStore($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
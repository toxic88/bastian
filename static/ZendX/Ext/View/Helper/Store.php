<?php

require_once 'ZendX/Ext/View/Helper/UiWidget.php';

class ZendX_Ext_View_Helper_Store extends ZendX_Ext_View_Helper_UiWidget
{
	
	protected $_classname = 'Ext.data.Store';
	
	public function __construct()
	{
		$myConfig = array(
			'autoDestry'           => null,
			'autoLoad'             => null,
			'baseParams'           => null,
			'batchSave'            => null,
			'data'                 => null,
			'proxy'                => null,
			'pruneModifiedRecords' => null,
			'reader'               => null,
			'remoteSort'           => null,
			'sortInfo'             => null,
			'storeId'              => null,
			'url'                  => null,
			'writer'               => null,
			'xtype'                => 'store'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function toArray()
	{
		if (isset($this->proxy)) {
			require_once 'ZendX/Ext/View/Exception.php';
			throw new ZendX_Ext_View_Exception('Proxy is not implemented yet! Please use "url"');
		}
		if (isset($this->reader)) {
			require_once 'ZendX/Ext/View/Exception.php';
			throw new ZendX_Ext_View_Exception('Reader is not implemented yet!');
		}
		if (isset($this->writer)) {
			require_once 'ZendX/Ext/View/Exception.php';
			throw new ZendX_Ext_View_Exception('Writer is not implemented yet!');
		}
		
		return parent::toArray();
	}
	
	public function store($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
<?php

require_once 'ZendX/Ext/View/Helper/Toolbar.php';

class ZendX_Ext_View_Helper_PagingToolbar extends ZendX_Ext_View_Helper_Toolbar
{
	
	protected $_classname = 'Ext.PagingToolbar';
	
	public function __construct()
	{		
		$myConfig = array(
			'afterPageText'  => null,
			'beforePageText' => null,
			'displayInfo'    => null,
			'displayMsg'     => null,
			'emptyMsg'       => null,
			'firstText'      => null,
			'lastText'       => null,
			'refreshText'    => null,
			'nextText'       => null,
			'pageSize'       => null,
			'prependButtons' => null,
			'prevText'       => null,
			'store'          => null,
			'xtype'          => 'paging'
		);
		$this->_apply($myConfig);
		
		$myRequired = array(
			'store'
		);
		$this->_applyRequired($myRequired);
		
		parent::__construct();
	}
	
	public function toArray()
	{
		if ($this->store instanceof ZendX_Ext_View_Helper_Store) {
			$this->store = $this->store->toArray();
		}
		return parent::toArray();
	}
	
	public function pagingToolbar($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
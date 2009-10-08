<?php

require_once 'ZendX/Ext/View/Helper/TextField.php';

class ZendX_Ext_View_Helper_TextArea extends ZendX_Ext_View_Helper_TextField
{
		
	protected $_classname = 'Ext.form.TextArea';
	
	public function __construct()
	{		
		$myConfig = array(
			'growMax'           => null,
			'growMin'           => null,
			'preventScrollbars' => null,
			'xtype'             => 'textarea'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function textArea($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
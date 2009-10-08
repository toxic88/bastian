<?php

require_once 'ZendX/Ext/View/Helper/FormField.php';

class ZendX_Ext_View_Helper_Checkbox extends ZendX_Ext_View_Helper_FormField
{
		
	protected $_classname = 'Ext.form.Checkbox';
	
	public function __construct()
	{		
		$myConfig = array(
			'boxLabel'   => null,
			'checked'    => null,
			'inputValue' => null,
			'xtype'      => 'checkbox'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function checkbox($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
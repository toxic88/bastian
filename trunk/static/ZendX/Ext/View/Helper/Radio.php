<?php

require_once 'ZendX/Ext/View/Helper/Checkbox.php';

class ZendX_Ext_View_Helper_Radio extends ZendX_Ext_View_Helper_Checkbox
{
		
	protected $_classname = 'Ext.form.Radio';
	
	public function __construct()
	{		
		$myConfig = array(
			'xtype' => 'radio'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function radio($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
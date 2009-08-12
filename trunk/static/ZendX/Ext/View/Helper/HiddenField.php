<?php

require_once 'ZendX/Ext/View/Helper/FormField.php';

class ZendX_Ext_View_Helper_HiddenField extends ZendX_Ext_View_Helper_FormField
{
		
	protected $_classname = 'Ext.form.Hidden';
	
	public function __construct()
	{		
		$myConfig = array(
			'xtype' => self::TYPE_HIDDEN
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function hiddenField($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
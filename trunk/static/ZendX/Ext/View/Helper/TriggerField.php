<?php

require_once 'ZendX/Ext/View/Helper/TextField.php';

class ZendX_Ext_View_Helper_TriggerField extends ZendX_Ext_View_Helper_TextField
{
		
	protected $_classname = 'Ext.form.TriggerField';
	
	public function __construct()
	{		
		$myConfig = array(
			'editable'      => null,
			'hideTrigger'   => null,
			'triggerClass'  => null,
			'triggerConfig' => null,
			'xtype'         => 'trigger'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function triggerField($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
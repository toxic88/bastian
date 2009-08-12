<?php

require_once 'ZendX/Ext/View/Helper/BoxComponent.php';

class ZendX_Ext_View_Helper_Label extends ZendX_Ext_View_Helper_BoxComponent
{
	
	protected $_classname = 'Ext.form.Label';
	
	public function __construct()
	{		
		$myConfig = array(
			'forId' => null,
			'html'  => null,
			'text'  => null,
			'xtype' => 'label'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function label($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
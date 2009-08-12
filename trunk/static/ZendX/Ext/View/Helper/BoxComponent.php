<?php

require_once 'ZendX/Ext/View/Helper/Component.php';

class ZendX_Ext_View_Helper_BoxComponent extends ZendX_Ext_View_Helper_Component
{
	
	protected $_classname = 'Ext.BoxComponent';
	
	public function __construct()
	{		
		$myConfig = array(
			'autoHeigt'  => null,
			'autoWidth'  => null,
			'height'     => null,
			'pageX'      => null,
			'pageY'      => null,
			'region'     => null,
			'width'      => null,
			'x'          => null,
			'y'          => null,
			'xtype'      => 'boxcomponent'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function boxComponent($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
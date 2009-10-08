<?php

require_once 'ZendX/Ext/View/Helper/UiWidget.php';

class ZendX_Ext_View_Helper_Component extends ZendX_Ext_View_Helper_UiWidget
{
	
	protected $_classname = 'Ext.Component';
	
	public function __construct()
	{		
		$myConfig = array(
			'allowDomMove'   => null,
			'anchor'         => null,
			'applyTo'        => null,
			'autoEl'         => null,
			'autoShow'       => null,
			'clearCls'       => null,
			'cls'            => null,
			'ctCls'          => null,
			'disabled'       => null,
			'disabledClass'  => null,
			'fieldLabel'     => null,
			'hidden'         => null,
			'hideLabel'      => null,
			'hideMode'       => null,
			'hideParent'     => null,
			'id'             => null,
			'itemCls'        => null,
			'itemId'         => null,
			'labelSeperator' => null,
			'labelStyle'     => null,
			'listeners'      => null,
			'overCls'        => null,
			'plugins'        => null,
			'renderTo'       => null,
			'stateId'        => null,
			'stateful'       => null,
			'style'          => null,
			'xtype'          => 'component'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function toArray()
	{
		$config = parent::toArray();
		
		if ($this->forceNew()) {
			unset($config['xtype']);
		}
		
		return $config;
	}
	
	public function component($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
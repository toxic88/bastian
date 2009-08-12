<?php

require_once 'ZendX/Ext/View/Helper/Panel.php';

class ZendX_Ext_View_Helper_Window extends ZendX_Ext_View_Helper_Panel
{
	
	protected $_classname = 'Ext.Window';
	
	public function __construct()
	{
		$myConfig = array(
			'animateTarget'   => null,
			'baseCls'         => null,
			'closable'        => null,
			'closeAction'     => null,
			'collapsed'       => null,
			'constrain'       => null,
			'constrainHeader' => null,
			'defaultButton'   => null,
			'draggable'       => null,
			'expandOnShow'    => null,
			'manager'         => null,
			'maximizable'     => null,
			'maximized'       => null,
			'minHeight'       => null,
			'minWidth'        => null,
			'minimizable'     => null,
			'modal'           => null,
			'onEsc'           => null,
			'plain'           => null,
			'resizable'       => null,
			'resizeHandles'   => null,
			'xtype'           => 'window'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function window($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
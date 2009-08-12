<?php

require_once 'ZendX/Ext/View/Helper/Container.php';

class ZendX_Ext_View_Helper_Viewport extends ZendX_Ext_View_Helper_Container
{
	
	protected $_classname = 'Ext.Viewport';
	
	protected $_forceNew = true;
	
	public function viewport($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
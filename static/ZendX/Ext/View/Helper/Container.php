<?php

require_once 'ZendX/Ext/View/Helper/BoxComponent.php';

class ZendX_Ext_View_Helper_Container extends ZendX_Ext_View_Helper_BoxComponent
{
	
	protected $_classname = 'Ext.Container';
	
	public function __construct()
	{		
		$myConfig = array(
			'activeItem'    => null,
			'autoDestroy'   => null,
			'bufferResize'  => null,
			'defaultType'   => null,
			'defaults'      => null,
			'hideBorders'   => null,
			'items'         => null,
			'layout'        => null,
			'layoutConfig'  => null,
			'monitorResize' => null,
			'xtype'         => 'container'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function toArray()
	{
		if (null !== ($items = $this->items)) {
			$this->items = array();
			if (!is_array($items)) {
				$items = array($items);
			}
			foreach ($items as $item) {
				if ($item instanceof ZendX_Ext_View_Helper_Container) {
					$this->items[] = $item->setForceNew(false)->toArray();
				} else {
					$this->items[] = $item;
				}
			}
		}
		
		if ($this->layout instanceof ZendX_Ext_View_Helper_LayoutContainer) {
			$this->layout = $this->layout->toArray();
		}
		
		return parent::toArray();
	}
	
	public function container($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
<?php

require_once 'ZendX/Ext/View/Helper/Container.php';

class ZendX_Ext_View_Helper_Panel extends ZendX_Ext_View_Helper_Container
{
	
	protected $_classname = 'Ext.Panel';
	
	public function __construct()
	{		
		$myConfig = array(
			'animCollapse'     => null,
			'autoLoad'         => null,
			'autoScroll'       => null,
			'baseCls'          => null,
			'bbar'             => null,
			'bodyBorder'       => null,
			'bodyCfg'          => null,
			'bodyStyle'        => null,
			'border'           => null,
			'buttonAlign'      => null,
			'buttons'          => null,
			'closable'         => null,
			'collapseFirst'    => null,
			'collapsed'        => null,
			'collapsedCls'     => null,
			'collapsible'      => null,
			'contentEl'        => null,
			'disabled'         => null,
			'draggable'        => null,
			'elements'         => null,
			'floating'         => null,
			'footer'           => null,
			'footerCfg'        => null,
			'frame'            => null,
			'header'           => null,
			'headerAsText'     => null,
			'headerCfg'        => null,
			'hideCollapseTool' => null,
			'html'             => null,
			'iconCls'          => null,
			'maskDisabled'     => null,
			'minButtonWidth'   => null,
			'resetBodyCss'     => null,
			'shadow'           => null,
			'shadowOffset'     => null,
			'shim'             => null,
			'tabTip'           => null,
			'tbar'             => null,
			'title'            => null,
			'titleCollapse'    => null,
			'toolTemplate'     => null,
			'tools'            => null,
			'unstyled'         => null,
			'xtype'            => 'panel'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function toArray()
	{
		if (null !== ($tools = $this->tools)) {
			$this->tools = array();
			if (!is_array($tools)) {
				$tools = array($tools);
			}
			foreach ($tools as $tool) {
				if (is_array($tool)) {
					$this->tools[] = $tool;
				}
			}
		}
		
		if (null !== ($tbar = $this->tbar)) {
			$this->tbar = array();
			if (!is_array($tbar)) {
				$tbar = array($tbar);
			}
			foreach ($tbar as $item) {
				if ($item instanceof ZendX_Ext_View_Helper_Toolbar) {
					$this->tbar[] = $item->toArray();
				} else if (is_array($item)) {
					$this->tbar[] = $item;
				}
			}
		}
		
		if (null !== ($bbar = $this->bbar)) {
			$this->bbar = array();
			if (!is_array($bbar)) {
				$bbar = array($bbar);
			}
			foreach ($bbar as $item) {
				if ($item instanceof ZendX_Ext_View_Helper_Toolbar) {
					$this->bbar[] = $item->toArray();
				} else if (is_array($item)) {
					$this->bbar[] = $item;
				}
			}
		}
		
		return parent::toArray();
	}
	
	public function panel($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}
	
}
<?php

require_once 'ZendX/Ext/View/Helper/ComboBox.php';

class ZendX_Ext_View_Helper_TimeField extends ZendX_Ext_View_Helper_ComboBox
{
		
	protected $_classname = 'Ext.form.TimeField';
	
	public function __construct()
	{		
		$myConfig = array(
			'altFormats'  => null,
			'format'      => null,
			'increment'   => null,
			'invalidText' => null,
			'maxText'     => null,
			'maxValue'    => null,
			'minText'     => null,
			'minValue'    => null,
			'xtype'       => 'timefield'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function timeField($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
<?php

require_once 'ZendX/Ext/View/Helper/TextField.php';

class ZendX_Ext_View_Helper_NumberField extends ZendX_Ext_View_Helper_TextField
{
		
	protected $_classname = 'Ext.form.NumberField';
	
	public function __construct()
	{		
		$myConfig = array(
			'allowDecimals'    => null,
			'allowNegative'    => null,
			'baseChars'        => null,
			'decimalPrecisin'  => null,
			'decimalSeperator' => null,
			'fieldClass'       => null,
			'maxText'          => null,
			'maxValue'         => null,
			'minText'          => null,
			'minValue'         => null,
			'nanText'          => null,
			'xtype'            => 'numberfield'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function numberField($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
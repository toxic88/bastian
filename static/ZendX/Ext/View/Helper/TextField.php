<?php

require_once 'ZendX/Ext/View/Helper/FormField.php';

class ZendX_Ext_View_Helper_TextField extends ZendX_Ext_View_Helper_FormField
{
		
	protected $_classname = 'Ext.form.TextField';
	
	public function __construct()
	{		
		$myConfig = array(
			'allowBlank'       => null,
			'blankText'        => null,
			'disableKeyFilter' => null,
			'emptyClass'       => null,
			'emptyText'        => null,
			'enableKeyEvents'  => null,
			'grow'             => null,
			'growMax'          => null,
			'growMin'          => null,
			'maskRe'           => null,
			'maxLength'        => null,
			'maxLengthText'    => null,
			'minLength'        => null,
			'minLengthText'    => null,
			'regex'            => null,
			'regexText'        => null,
			'selectOnFocus'    => null,
			'stribCharsRe'     => null,
			'validator'        => null,
			'vtype'            => null,
			'vtypeText'        => null,
			'xtype'            => 'textfield'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function textField($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
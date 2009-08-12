<?php

require_once 'ZendX/Ext/View/Helper/BoxComponent.php';

class ZendX_Ext_View_Helper_FormField extends ZendX_Ext_View_Helper_BoxComponent
{
	const TYPE_TEXT     = 'text';
	const TYPE_RADIO    = 'radio';
	const TYPE_CHECKBOX = 'checkbox';
	const TYPE_FILE     = 'file';
	const TYPE_HIDDEN   = 'hidden';
	const TYPE_PASSWORD = 'password';
	
	protected $_classname = 'Ext.form.FormField';
	
	public function __construct()
	{		
		$myConfig = array(
			'autoCreate'      => null,
			'cls'             => null,
			'disabled'        => null,
			'fieldClass'      => null,
			'fieldLabel'      => null,
			'focusClass'      => null,
			'inputType'       => null,
			'invalidClass'    => null,
			'invalidText'     => null,
			'msgFx'           => null,
			'msgTarget'       => null,
			'name'            => null,
			'readOnly'        => null,
			'tabIndex'        => null,
			'validateOnBlur'  => null,
			'validationDelay' => null,
			'validationEvent' => null,
			'value'           => null,
			'xtype'           => 'field'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function formField($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
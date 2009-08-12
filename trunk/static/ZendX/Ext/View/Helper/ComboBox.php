<?php

require_once 'ZendX/Ext/View/Helper/TriggerField.php';

class ZendX_Ext_View_Helper_ComboBox extends ZendX_Ext_View_Helper_TriggerField
{
		
	protected $_classname = 'Ext.form.ComboBox';
	
	public function __construct()
	{		
		$myConfig = array(
			'allQuery'          => null,
			'displayField'      => null,
			'forceSelection'    => null,
			'handleHeight'      => null,
			'hiddenId'          => null,
			'hiddenName'        => null,
			'hiddenValue'       => null,
			'itemSelector'      => null,
			'lazyInit'          => null,
			'lazyRender'        => null,
			'listAlign'         => null,
			'listClass'         => null,
			'listWidth'         => null,
			'loadingText'       => null,
			'minChars'          => null,
			'minHeight'         => null,
			'minListWidth'      => null,
			'mode'              => null,
			'pageSize'          => null,
			'queryDelay'        => null,
			'queryParam'        => null,
			'resizable'         => null,
			'selectOnFocus'     => null,
			'selectedClass'     => null,
			'shadow'            => null,
			'store'             => null,
			'title'             => null,
			'tpl'               => null,
			'transform'         => null,
			'triggerAction'     => null,
			'triggerClass'      => null,
			'typeAhead'         => null,
			'typeAheadDelay'    => null,
			'valueField'        => null,
			'valueNotFoundText' => null,
			'xtype'             => 'combo'
		);
		$this->_apply($myConfig);
		
		parent::__construct();
	}
	
	public function comboBox($config = array())
	{
		$this->setConfig($config);
		
		return $this;
	}

}
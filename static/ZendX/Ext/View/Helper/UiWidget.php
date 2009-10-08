<?php

require_once 'Zend/View/Helper/Abstract.php';

abstract class ZendX_Ext_View_Helper_UiWidget extends Zend_View_Helper_Abstract
{
	
	protected $_ext;
	
	protected $_config = array();
	
	protected $_required = array();
	
	protected $_classname = '';
	
	protected $_forceNew = false;
	
	public function __construct()
	{
	}
	
    public function setView(Zend_View_Interface $view)
    {
        parent::setView($view);
        $this->_ext = $this->view->Ext();
        $this->_ext->enable();
        return $this;
    }
	
	protected function _apply($config = array())
	{
		// debug
		foreach ($config as $name => $value) {
			if (array_key_exists($name, $this->_config)) {
				echo '"' . $name '" is allready in the config\n';
			}
		}
		$this->_config = array_merge($config, $this->_config);
	}
	
	protected function _applyRequired($required)
	{
		$this->_required = array_merge($this->_required, $required);
	}
	
	public function __set($name, $value)
	{
		if (array_key_exists($name, $this->_config)) {
			$this->_config[ $name ] = $value;
		}
	}

	public function __get($name)
	{
		if (array_key_exists($name, $this->_config)) {
			return $this->_config[ $name ];
		}
		return null;
	}

	public function __unset($name)
	{
		if (array_key_exists($name, $this->_config)) {
			unset($this->_config[ $name ]);
		}
	}

	public function __isset($name)
	{
		if (array_key_exists($name, $this->_config)) {
			return isset($this->_config[ $name ]);
		}
		return false;
	}

	public function __toString()
	{
		$json = $this->toJson();
		if ($this->forceNew()) {
			if ($json == '[]') {
				$json = '';
			}
			$json = 'new ' . $this->_classname . '(' . $json . ');';
		}
		return $json;
	}
	
	public function toJson()
	{
		$config = $this->toArray();
        if(!class_exists('Ext')) {
            require_once 'ZendX/Ext.php';
        }
		$json = Ext::encodeJson($config);
		
		return $json;
	}
	
	public function toArray()
	{
		$config = array();
		foreach ($this->_config as $name => $value) {
			if (isset($this->_config[ $name ])) {
				$config[ $name ] = $value;
			} else if (in_array($name, $this->_required)) {
				require_once 'ZendX/Ext/View/Exception.php';
				throw new ZendX_Ext_View_Exception('Config "' . $name . '" is required!');
			}
		}
		
		return $config;
	}
	
	public function getConfig()
	{
		return $this->_config;
	}
	
	public function setConfig($config = array(), $reset = false)
	{
		if (empty($config)) {
			return $this;
		}
		
		if ($reset !== false) {
			$this->resetConfig();
		}
		
		foreach ($config as $name => $value) {
			$this->$name = $value; // use the magic __set method
		}
		
		return $this;
	}
	
	public function resetConfig()
	{
		foreach ($this->_config as $name => $value) {
			unset($this->_config[ $name ]);
		}
		
		return $this;
	}
	
	public function setForceNew($flag)
	{
		$this->_forceNew = (bool) $flag;
		
		return $this;
	}
	
	public function forceNew()
	{
		return $this->_forceNew;
	}
	
}
<?php

require_once 'Zend/Registry.php';

require_once 'Zend/View/Helper/Abstract.php';

class ZendX_Ext_View_Helper_Ext extends Zend_View_Helper_Abstract
{
	
	public $view;
	
    public function __construct()
    {
        $registry = Zend_Registry::getInstance();
        if (!isset($registry[__CLASS__])) {
            require_once "ZendX/Ext/View/Helper/Ext/Container.php";
            $container = new ZendX_Ext_View_Helper_Ext_Container();
            $registry[__CLASS__] = $container;
        }
        $this->_container = $registry[__CLASS__];
    }

    public function Ext()
    {
        return $this->_container;
    }

    public function setView(Zend_View_Interface $view)
    {
        $this->view = $view;
        $this->_container->setView($view);
    }

    public function __call($method, $args)
    {
        if (!method_exists($this->_container, $method)) {
            require_once 'Zend/View/Exception.php';
            throw new Zend_View_Exception(sprintf('Invalid method "%s" called on Ext view helper', $method));
        }

        return call_user_func_array(array($this->_container, $method), $args);
    }
    
}
<?php

// ZendX/Exception.php

class Ext
{
	
    public static function enableView(Zend_View_Interface $view)
    {
        if (false === $view->getPluginLoader('helper')->getPaths('ZendX_Ext_View_Helper')) {
            $view->addHelperPath('ZendX/Ext/View/Helper', 'ZendX_Ext_View_Helper');
        }
    }
	
    public static function encodeJson($value)
    {
        if(!class_exists('Zend_Json')) {
            /**
             * @see Zend_Json
             */
            require_once "Zend/Json.php";
        }
        return Zend_Json::encode($value, false, array('enableJsonExprFinder' => true));
    }
    
}
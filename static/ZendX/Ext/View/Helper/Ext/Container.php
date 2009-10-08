<?php

class ZendX_Ext_View_Helper_Ext_Container
{
	
	protected $_javascriptSources = array();
	
	protected $_javascriptStatements = array();
	
	protected $_stylesheets = array();
	
    public $view = null;

    public function setView(Zend_View_Interface $view)
    {
        $this->view = $view;
    }
	
    public function addJavascriptFile($path)
    {
        $path = (string) $path;
        if (!in_array($path, $this->_javascriptSources)) {
            $this->_javascriptSources[] = $path;
        }
        return $this;
    }

    public function addJavascript($js)
    {
        $js = trim($js);
        if (!in_array(substr($js, -1), array(';', '}'))) {
            $js .= ';';
        }

        if (in_array($js, $this->_javascriptStatements)) {
            return $this;
        }

        $this->_javascriptStatements[] = $js;
        return $this;
    }

    public function getJavascriptFiles()
    {
        return $this->_javascriptSources;
    }

    public function getJavascript()
    {
        return $this->_javascriptStatements;
    }

    public function clearJavascriptFiles()
    {
        $this->_javascriptSources = array();
        return $this;
    }

    public function clearJavascript()
    {
        $this->_javascriptStatements = array();
        return $this;
    }

    public function addStylesheet($path)
    {
        $path = (string) $path;
        if (!in_array($path, $this->_stylesheets)) {
            $this->_stylesheets[] = $path;
        }
        return $this;
    }
    
    public function getStylesheets()
    {
        return $this->_stylesheets;
    }
    
    public function __toString()
    {
        $html  = $this->_renderStylesheets() . PHP_EOL
               . $this->_renderScriptTags();
        return $html;
    }
    
    protected function _renderStylesheets()
    {
		$stylesheets = $this->getStylesheets();
        array_reverse($stylesheets);

        $style = "";
        foreach($stylesheets as $stylesheet) {
            if ($this->view instanceof Zend_View_Abstract) {
                $closingBracket = ($this->view->doctype()->isXhtml()) ? ' />' : '>';
            } else {
                $closingBracket = ' />';
            }

            $style .= '<link rel="stylesheet" href="'.$stylesheet.'" '.
                      'type="text/css" media="screen"' . $closingBracket . PHP_EOL;
        }

        return $style;
    }
    
    protected function _renderScriptTags()
    {
    	$scripts = $this->getJavascriptFiles();
    	array_reverse($scripts);
    	
    	$scriptTags = '';
        foreach($scripts as $javascriptFile) {
            $scriptTags .= '<script type="text/javascript" src="' . $javascriptFile . '"></script>' . PHP_EOL;
        }

        return $scriptTags;
    }
    
}
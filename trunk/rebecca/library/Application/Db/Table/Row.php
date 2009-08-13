<?php

class Application_Db_Table_Row extends Zend_Db_Table_Row_Abstract
{
    
    public function __get($columnName)
    {
        $value = parent::__get($columnName);
        
        if (array_key_exists($columName, $this->_primary)) {
            $value = (int) ((string) $value);
        }
        return $value;
    }
    
    public function toArray()
    {
    	$ret = array();
    	foreach ($this as $key => $value) {
    		$ret[$key] = $value;
    	}
    	return $ret;
    }
    
}

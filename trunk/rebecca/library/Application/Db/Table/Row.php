<?php

class Application_Db_Table_Row extends Zend_Db_Table_Row_Abstract
{

    /**
     * @override
     */
    public function __get($columnName)
    {
        $value = parent::__get($columnName);

        if (in_array($columnName, $this->_primary)) {
            $value = (int) ((string) $value);
        }
        return $value;
    }

    /**
     * @override
     */
    public function toArray()
    {
    	$ret = array();
    	foreach (array_keys($this->_data) as $columnName) {
    		$ret[$columnName] = $this->$columnName;
    	}
    	return $ret;
    }
    
}

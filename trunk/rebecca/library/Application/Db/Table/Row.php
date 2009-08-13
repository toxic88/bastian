<?php

class Application_Db_Table_Row extends Zend_Db_Table_Row_Abstract
{

    /**
     * @override
     */
    public function __set($columnName, $value)
    {
        $tColumnName = $this->_transformColumn($columnName);
        if (in_array($tColumnName, $this->_primary)) { // don't set primary columns
            return $this;
        }

        return parent::__set($columnName, $value);
    }

    /**
     * @override
     */
    public function __get($columnName)
    {
        $value = parent::__get($columnName);

        if (in_array($columnName, $this->_primary)) { // parse primary to integer
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
    		$ret[$columnName] = $this->$columnName; // force to use __get
    	}

    	return $ret;
    }
    
}

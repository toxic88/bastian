<?php

class Application_Db_Table_Row extends Zend_Db_Table_Row_Abstract
{

    /**
     * @override
     */
    public function __set($columnName, $value)
    {
        $columnName = $this->_transformColumn($columnName);
        if (!array_key_exists($columnName, $this->_data)) {
            require_once 'Zend/Db/Table/Row/Exception.php';
            throw new Zend_Db_Table_Row_Exception("Specified column \"$columnName\" is not in the row");
        }

        if (in_array($columnName, $this->_primary)) { // don't set primary columns
            return;
        }

        $this->_data[$columnName] = $value;

        if ($value === $this->_cleanData[$columnName]) {
            if (isset($this->_modifiedFields[$columnName])) {
                unset($this->_modifiedFields[$columnName]);
            }
        } else {
            $this->_modifiedFields[$columnName] = true;
        }
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

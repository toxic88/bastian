<?php

class Antibodydb_Model_DbTable_Targetprotein extends Antibodydb_Model_DbTable_Abstract
{

    public function __construct()
    {
        $this->_messages[self::MESSAGE_FIELD_EXISTS] = 'Targetprotein "%s" allready exists.';

        $this->_table = new Antibodydb_Db_Table('DEV_T_Targetprotein', Zend_Registry::get('dbdefinition'));
    }

//    protected function _checkRequiredColumns(array $data)
//    {
//        if (!array_key_exists('deutsch', $data)) { // checking required fields
//            throw new Antibodydb_Model_DbTable_Exception($this->_messages[self::MESSAGE_MISSING_FIELDS]);
//        }
//    }

//    protected function _checkDublicateRows(array $data)
//    {
//
//    }

//    protected function _filter(array $data)
//    {
//        $data = parent::_filter($data);
//
//        return $data;
//    }

}

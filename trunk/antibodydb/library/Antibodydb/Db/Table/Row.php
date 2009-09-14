<?php

class Antibodydb_Db_Table_Row extends Zend_Db_Table_Row_Abstract
{

    public function toArray()
    {
        foreach ($this->_primary as $primary) {
            $this->_data[ $primary ] = (int) $this->_data[ $primary ];
        }

        $ret = array();
        $database = $this->_table->getAdapter()->getConfig(); $database = $database['dbname'];
        $table = $this->_table->info(Zend_Db_Table::NAME);
        foreach ($this->_data as $field => $value) {
            $ret[ $database . ':' . $table . '.' . $field ] = $value;
        }

        return $ret;
    }

    public function setFromArray(array $data)
    {
        foreach ($data as $column => $value) {
            $data[ $this->_transformColumn($column) ] = $value;
        }

        $data = array_intersect_key($data, $this->_data);

        foreach ($data as $columnName => $value) {
            $this->__set($columnName, $value);
        }

        return $this;
    }

    protected function _transformColumn($columnName)
    {
        $columnName = parent::_transformColumn($columnName);
        $database = $this->_table->getAdapter()->getConfig(); $database = $database['dbname'];
        $table = $this->_table->info(Zend_Db_Table::NAME);
        // $columnName = 'phpwebdb:DEV_T_Targetprotein.id';
        if (stristr($database . ':' . $columnName, $table) !== false) {
            $tmp = explode('.', $columnName);
            $columnName = $tmp[1];
        }

        return $columnName;
    }

    /**
     * @override
     * see r47 for details
     */
    protected function _prepareReference(Zend_Db_Table_Abstract $dependentTable, Zend_Db_Table_Abstract $parentTable, $ruleKey)
    {
        $parentTableName = ($parentTable instanceof Zend_Db_Table) ? $parentTable->getDefinitionConfigName() : get_class($parentTable);
        $map = $dependentTable->getReference($parentTableName, $ruleKey);

        if (!isset($map[Zend_Db_Table_Abstract::REF_COLUMNS])) {
            $parentInfo = $parentTable->info();
            $map[Zend_Db_Table_Abstract::REF_COLUMNS] = array_values((array) $parentInfo['primary']);
        }

        $map[Zend_Db_Table_Abstract::COLUMNS] = (array) $map[Zend_Db_Table_Abstract::COLUMNS];
        $map[Zend_Db_Table_Abstract::REF_COLUMNS] = (array) $map[Zend_Db_Table_Abstract::REF_COLUMNS];

        return $map;
    }

}

<?php

class Antibodydb_Db_Table_Row extends Zend_Db_Table_Row_Abstract
{

    public function setFromArray(array $data)
    {
        foreach ($data as $key => $value) {
            $data[ str_replace($this->_table->getPrefix(), $key) ] = $value;
        }
    
        return parent::setFromArray($data);
    }

    public function toArray()
    {
        foreach ($this->_primary as $primary) {
            $this->_data[ $primary ] = (int) $this->_data[ $primary ];
        }
        return parent::toArray();
    }

    public function toClient()
    {
        $data = $this->toArray();
        $ret = array();
        foreach ($data as $key => $value) {
            $ret[ $this->_table->getPrefix() . $key ] = $value;
        }
        return $ret;
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

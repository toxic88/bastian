<?php

class Antibodydb_Db_Table_Row extends Zend_Db_Table_Row
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
    
}

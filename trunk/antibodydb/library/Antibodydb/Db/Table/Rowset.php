<?php

class Antibodydb_Db_Table_Rowset extends Zend_Db_Table_Rowset_Abstract
{

    protected $_rowClass = 'Antibodydb_Db_Table_Row';

    public function toClient()
    {
        $ret = array();
        foreach ($this as $row) {
            $ret[] = $row->toClient();
        }
        return $ret;
    }

}

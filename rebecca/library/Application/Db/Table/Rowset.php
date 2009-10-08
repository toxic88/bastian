<?php

class Application_Db_Table_Rowset extends Zend_Db_Table_Rowset_Abstract
{

    protected $_rowClass = 'Application_Db_Table_Row';

    /**
     * @override
     */
    public function toArray()
    {
        $ret = array();
        foreach($this as $i => $row) {
            $ret[$i] = $row->toArray();
        }
        return $ret;
    }

}

<?php

class TargetproteinController extends Antibodydb_Controller_GridAbstract
{

    public function init()
    {
        parent::init();
        $this->_model = new Antibodydb_Model_DbTable_Targetprotein();
//        Just for testing. (and to remember it :))
//        $row = $this->_model->getTable()->find(1)->current();
//        var_dump($row->toArray());
//        var_dump($row->findDependentRowset('Antibody')->current());
    }

}

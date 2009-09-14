<?php

class TargetproteinController extends Antibodydb_Controller_GridAbstract
{

    public function init()
    {
        parent::init();
        $this->_model = new Antibodydb_Model_DbTable_Targetprotein();
    }

}

<?php

class VokabelnController extends Application_Controller_GridAbstract
{

    public function init()
    {
        parent::init();
        $this->_model = new Application_Model_DbTable_Vokabeln();
    }

}

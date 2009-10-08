<?php

class IndexController extends Zend_Controller_Action
{

    public function init()
    {

    }

    public function indexAction()
    {
        $this->view->headTitle('Antibodydb');

        $this->view->headLink()->appendStylesheet('css/ext.css')
                               ->appendStylesheet('css/Antibodydb.css');
        $this->view->headScript()->appendFile('js/ext-debug.js')
                                 ->appendFile('index/settings')
                                 ->appendFile('js/Antibodydb.js');

        if($this->_isAdmin()) {
            $this->view->headTitle(' - Admin');
        }
    }

    public function settingsAction()
    {
        $ret = array();
        $config = Zend_Registry::get('config');
        $tables = $config->db->tables;
        $dbdefinition = Zend_Registry::get('dbdefinition');

        foreach ($tables->toArray() as $table => $tmp) {
            $tmp = new Antibodydb_Db_Table($table, $dbdefinition);
            $ret['tables'][$table]['cols'] = array_keys($tmp->createRow()->toArray());
        }

        $ret['tables']['Antibody']['fields'] = array(
            1 => 'Antibody ID',
            2 => 'Lot#',
            4 => 'Dilution Western'
        ) + $ret['tables']['Antibody']['cols'];

        $ret['tables']['Comments']['fields'] = array(
            1 => 'Western Comment',
            2 => 'Capture Array Comment',
            3 => 'RPPA Comment',
            4 => 'IP Comment',
            5 => 'IF Comment',
            6 => 'IHC Comment',
            7 => 'FACS Comment'
        ) + $ret['tables']['Comments']['cols'];

        $ret['tables']['Bufferset']['fields'] = array(
            3 => 'Incubation 1°AB',
            4 => 'Incubation 2°AB'
        ) + $ret['tables']['Bufferset']['cols'];

        $ret['tables']['Incubationprotocol']['fields'] = array(
            1 => 'Incubation Protocol',
            2 => 'Blocking',
            3 => '1°AB Incubation',
            4 => '2°AB Incubation',
            5 => 'Washing l',
            6 => 'Washing 2'
        ) + $ret['tables']['Incubationprotocol']['cols'];

        $this->view->config = $ret;
    }

    private function _isAdmin()
    {
        $config = Zend_Registry::get('config');
        return $config->user->role === $config->acl->roles->{1};
    }

}

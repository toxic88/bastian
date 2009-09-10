<?php

class Antibodydb_TableManager
{

    protected static $_instance = null;

    protected $_tables = array();

    protected function __construct()
    {}

    protected function __clone()
    {}

    public function & __get($name)
    {
        if(array_key_exists($name, $this->_tables)) {
            return $this->_tables[$name];
        }
        if(isset(Zend_Registry::get('config')->db->tables->$name)) {
            return $this->_tables[$name] = $this->_createTable(Zend_Registry::get('config')->db->tables->$name, $name);
        }
        throw new Exception("Invalid tablename $name.");
    }

    protected function _createTable($name, $prefix)
    {
        return new Antibodydb_Db_Table(array(
            Antibodydb_Db_Table::NAME     => $name,
            Antibodydb_Db_Table::PRIMARY  => 'id',
            Antibodydb_Db_Table::PREFIX => $prefix
        ));
    }

    public static function getInstance()
    {
        if(self::$_instance === null) {
            self::$_instance = new self();
        }
        return self::$_instance;
    }

    public static function get($name)
    {
        $instance = self::getInstance();
        return $instance->$name;
    }

}
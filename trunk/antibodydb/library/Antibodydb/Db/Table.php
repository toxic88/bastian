<?php

class Antibodydb_Db_Table extends Zend_Db_Table
{
    
    const PREFIX = 'prefix';

    protected $_primary = 'id';

    protected $_rowClass = 'Antibodydb_Db_Table_Row';

    protected $_rowsetClass = 'Antibodydb_Db_Table_Rowset';

    protected $_prefix;

    public function __construct($config = array())
    {
        if (is_array($config)) {
            if ($config[ self::PREFIX ]) {
                $this->setIdentity($config[ self::PREFIX ]);
            }
        }
        parent::__construct($config);
    }

    public function setPrefix($prefix)
    {
        $this->_prefix = ((string) $prefix) . ':';
        return $this;
    }

    public function getPrefix()
    {
        return $this->_prefix;
    }

}

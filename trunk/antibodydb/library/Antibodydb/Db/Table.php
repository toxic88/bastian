<?php

class Antibodydb_Db_Table extends Zend_Db_Table
{
    
    protected $_primary = 'id';

    protected $_rowClass = 'Antibodydb_Db_Table_Row';

    protected $_rowsetClass = 'Antibodydb_Db_Table_Rowset';

}

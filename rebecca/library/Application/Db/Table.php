<?php

class Application_Db_Table extends Zend_Db_Table
{

    protected $_primary = 'id';

    protected $_rowClass = 'Application_Db_Table_Row';

    protected $_rowsetClass = 'Application_Db_Table_Rowset';

}

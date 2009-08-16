<?php

class Application_Model_DbTable_User extends Application_Model_DbTable_Abstract
{

    public function __construct()
    {
        $this->_messages[self::MESSAGE_ROW_NOT_EXISTS] = 'Der Benutzer mit der id "%d" existiert nicht.';
        $this->_messages[self::MESSAGE_FIELD_EXISTS]   = 'Der Benutzer "%s" existiert bereits.';

        $this->_table = new Application_Db_Table(array(
            Application_Db_Table::NAME => 'rebecca_user'
        ));
    }

    protected function _checkRequiredColumns(array $data)
    {
        if (!array_key_exists('username', $data) || // checking required fields
            !array_key_exists('password', $data) ||
            !array_key_exists('rights',   $data)
           ) {
            throw new Application_Model_DbTable_Exception($this->_messages[self::MESSAGE_MISSING_FIELDS]);
        }
    }

    protected function _checkDublicateRows(array $data)
    {
        if ($data['username']) { // prevent the same usernames
            $rowset = $this->_table->fetchAll($this->_table->select()->where('username = ?', $data['username']));

            if (count($rowset) > 0) {
                throw new Application_Model_DbTable_Exception(sprintf($this->_messages[self::MESSAGE_FIELD_EXISTS], $data['username']));
            }
        }
    }

    protected function _filter(array $data)
    {
        $data = parent::_filter($array);

        if ($data['password']) {
            $data['password'] = md5($data['password']);
        }

        return $data;
    }

}

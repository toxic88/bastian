<?php

class Application_Model_DbTable_ChatMessage extends Application_Model_DbTable_Abstract
{

    public function __construct()
    {
        $this->_messages[self::MESSAGE_ROW_NOT_EXISTS] = 'Die Nachricht mit der id "%d" existiert nicht.';

        $this->_table = new Application_Db_Table(array(
            Application_Db_Table::NAME => 'rebecca_chat_message'
        ));
    }

    public function _checkRequiredColumns(array $data)
    {
        if (!array_key_exists('to', $data) || // checking required fields
            !array_key_exists('from', $data) ||
            !array_key_exists('message', $data)
           ) {
            throw new Application_Model_DbTable_Exception($this->_messages[self::MESSAGE_MISSING_FIELDS]);
        }
    }

    protected function _filter(array $data)
    {
        $data = parent::_filter($data);

        if ($data['message']) {
            $data['message'] = Zend_Filter::filterStatic($data['message'], 'EncodeTags');
        }

        return $data;
    }

}

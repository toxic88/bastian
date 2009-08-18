<?php

class Application_Model_DbTable_ChatUser extends Application_Model_DbTable_Abstract
{

    public function __construct()
    {
        $this->_messages[self::MESSAGE_ROW_NOT_EXISTS] = 'Der Benutzer mit der id "%d" existiert nicht.';
        $this->_messages[self::MESSAGE_FIELD_EXISTS]   = 'Der Benutzer "%s" existiert bereits.';
    }

    public function _checkRequiredColumns(array $data)
    {
        if (!array_key_exists('fk_rebecca_user_id', $data)) { // checking required fields
            throw new Application_Model_DbTable_Exception($this->_messages[self::MESSAGE_MISSING_FIELDS]);
        }
    }

    protected function _filter(array $data)
    {
        $data = parent::_filter($data);

        if ($data['status_text']) {
            $data['status_text'] = Zend_Filter::filterStatic($data['status_text'], 'EncodeTags');
        }

        return $data;
    }

}

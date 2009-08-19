<?php

class Application_Model_DbTable_Vokabeln extends Application_Model_DbTable_Abstract
{

    public function __construct()
    {
        $this->_messages[self::MESSAGE_ROW_NOT_EXISTS] = 'Die Vokabel mit der id "%d" existiert nicht.';
        $this->_messages[self::MESSAGE_FIELD_EXISTS]   = 'Die Vokabel "%s" existiert bereits.';

        $this->_table = new Application_Db_Table('rebecca_vokabeln', Zend_Registry::get('dbdefinition'));
    }

    protected function _checkRequiredColumns(array $data)
    {
        if (!array_key_exists('deutsch', $data)) { // checking required fields
            throw new Application_Model_DbTable_Exception($this->_messages[self::MESSAGE_MISSING_FIELDS]);
        }
    }

    protected function _checkDublicateRows(array $data)
    {
        if ($data['deutsch']) { // prevent the same usernames
            $rowset = $this->_table->fetchAll($this->_table->select()->where('deutsch = ?', $data['deutsch']));

            if (count($rowset) > 0) {
                throw new Application_Model_DbTable_Exception(sprintf($this->_messages[self::MESSAGE_FIELD_EXISTS], $data['deutsch']));
            }
        }
    }

    protected function _filter(array $data)
    {
        $data = parent::_filter($data);

        if ($data['deutsch']) {
            $data['deutsch'] = Zend_Filter::filterStatic($data['deutsch'],   'EncodeTags');
        }
        if ($data['englisch']) {
            $data['englisch'] = Zend_Filter::filterStatic($data['englisch'], 'EncodeTags');
        }
        if ($data['spanisch']) {
            $data['spanisch'] = Zend_Filter::filterStatic($data['spanisch'], 'EncodeTags');
        }

        return $data;
    }

}

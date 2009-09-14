<?php

abstract class Antibodydb_Model_DbTable_Abstract
{

    const MESSAGE_ROW_NOT_EXISTS = 'row_not_exists';
    const MESSAGE_MISSING_FIELDS = 'missing_fields';
    const MESSAGE_FIELD_EXISTS   = 'field_exists';

    protected $_table;

    protected $_messages = array(
        self::MESSAGE_ROW_NOT_EXISTS => 'The id "%d" does not exists.',
        self::MESSAGE_MISSING_FIELDS => 'Please fill in all fields!',
        self::MESSAGE_FIELD_EXISTS   => 'The field "%s" allready exists.'
    );

    abstract public function __construct();

    public function getTable()
    {
        return $this->_table;
    }

    public function select(array $data)
    {
        if ($data['id']) {
            $rowset = $this->_table->find($data['id']);
        } else {
            $data = $this->_filter($data);
            $cols = $this->_getSearchColumns($data);
            $select = $this->_table->select();

            if (in_array($data['sort'], $cols)) {
                $select->order($data['sort'] . ' ' . $data['dir']);
            }

            if ($data['start'] || $data['limit']) {
                $select->limit($data['start'], $data['limit']);
            }

            if ($data['query']) {
                foreach($cols as $col) {
                    $select->orWhere($col . ' LIKE ?', '%' . $data['query'] . '%');
                }
            }

            $rowset = $this->_table->fetchAll($select);
        }
        
        $numrows = Antibodydb_Db_Table::getDefaultAdapter()->fetchOne(
            $this->_table->select()->from($this->_table, 'count(*)')
        );

        return array(
            'data'  => $rowset->toArray(),
            'total' => (int) $numrows
        );
    }

    public function destroy(array $data)
    {
        $data = $this->_filter($data);

        $rowset = $this->_table->find($data['id']);

        if (count($rowset) <= 0) {
            throw new Antibodydb_Model_DbTable_Exception(sprintf($this->_messages[self::MESSAGE_ROW_NOT_EXISTS], $data['id']));
        }

        $row = $rowset->current();
        $row->delete();

        return new stdClass();
    }

    public function update(array $data)
    {
        $data = $this->_filter($data);

        $this->_checkDublicateRows($data);

        $rowset = $this->_table->find($data['id']);

        if (count($rowset) <= 0) {
            throw new Antibodydb_Model_DbTable_Exception(sprintf($this->_messages[self::MESSAGE_ROW_NOT_EXISTS], $data['id']));
        }

        $row = $rowset->current();
        $row->setFromArray($data);
        $row->save();

        return $row->toArray();
    }

    public function create(array $data)
    {
        $data = $this->_filter($data);

        $this->_checkRequiredColumns($data);

        $this->_checkDublicateRows($data);

        $row = $this->_table->createRow($data);
        $row->save();

        return $row->toArray();
    }

    /**
     * Check if a row allready exists on insert
     * @param array $data
     */
    protected function _checkDublicateRows(array $data)
    {

    }

    /**
     * Check the columns wich can not be null.
     * Throw an exception if a column does not exists
     * @param array $data
     */
    protected function _checkRequiredColumns(array $data)
    {

    }

    /**
     * Validate userinput.
     * @param array $data from client as reference
     */
    protected function _filter(array $data)
    {
        return array_filter($data);
    }

    /**
     *
     * @param array $data
     * @return array of columns
     */
    protected function _getSearchColumns(array $data)
    {
        return $this->_table->info(Antibodydb_Db_Table::COLS);
    }

/**
 * For bachted requests
    public function __call($name, $arguments)
    {
        if (in_array($name, array('destroy', 'update', 'create'))) {
            if (is_array($arguments)) {
                foreach ($arguments as $arg) {
                    $data[] = call_user_func(array($this, '_' . $name), $arg);
                }
            } else {
                $data = call_user_func(array($this, '_' . $name), $arguments);
            }

            return $data;
        }
    }
*/

}

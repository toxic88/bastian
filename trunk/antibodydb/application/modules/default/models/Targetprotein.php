<?php

class Antibodydb_Model_Targetprotein
{

    protected $_targetprotein;
    protected $_antibody;
    
    public function __construct()
    {
        $this->_targetprotein = Antibodydb_TableManager::get('Targetprotein');
        $this->_antibody = Antibodydb_TableManager::get('Antibody');
    }
    
    public function save($data)
    {
        $row = $this->_targetprotein->createRow($data);
        
        if (!empty($row->id)) {
            return $this->update($data);
        } else {
            return $this->insert($data);
        }
    }
    
    public function insert($data)
    {
        $row = $this->_targetprotein->createRow($data);
        
        unset($row->id);
        
        try {
            $row->save();
            return $this->_createResponse(array(
                'data' => $row->toClient()
            ));
        } catch(Exception $e) {
            return $this->_createResponse(array(
                'error' => $e->getMessage()
            ));
        }
    }
    
    public function update($data)
    {
        $row = $this->_targetprotein->createRow($data);
        
        $row = $this->_targetprotein->find($row->id)->current();
        
        if (!$row) {
        
        }
        
        try {
            $row->setFromArray($data)->save();
            return $this->_createResponse(array(
                'data' => $row->toClient()
            ));
        } catch(Exception $e) {
        
        }
    }
    
    public function delete($data)
    {
        
    }
    
    public function select($data)
    {
        $cols   = $this->_targetprotein->info(Antibodydb_Db_Table::COLS);
        $select = $this->_targetprotein->select();

        $query = $this->_check($data['query']);
        $start = $this->_check($data['start']);
        $limit = $this->_check($data['limit']);
        $dir   = $this->_check($data['dir'], Zend_Db_Table_Select::SQL_ASC);
        $sort  = $this->_check($data['sort']);

        if(in_array($sort, $cols)) {
            $select->order($sort . ' ' . $dir);
        }

        if($start || $limit) {
            $select->limit($limit, $start);
        }

        if($query !== null) {
           foreach($cols as $col) {
               if($col == 'References') $col = '[References]'; // don't know why this only here not works
               $select->orWhere($col . ' LIKE ?', '%' . $query . '%');
           }
        }
        $rows = $this->_targetprotein->fetchAll($select);

        $numrows = Antibodydb_Db_Table::getDefaultAdapter()->fetchOne(
            $this->_targetprotein->select()->from($this->_targetprotein, 'count(*)')
        );

        return $this->_createResponse(array(
            'data'  => $rows->toClient(),
            'total' => $numrows
        ));
    }
    
    protected function _check($value, $default)
    {
        if (empty($value)) {
            return $default ? $default : null;
        }
        return $value;
    }
    
    protected function _createResponse($ret)
    {
        return array(
            'success' => isset($ret['data']),
            'data'    => $ret['data']  ? $ret['data']  : null,
            'error'   => $ret['error'] ? $ret['error'] : null
        ) + $ret;
    }
    
}

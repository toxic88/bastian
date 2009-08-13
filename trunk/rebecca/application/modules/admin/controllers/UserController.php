<?php

class Admin_UserController extends Zend_Controller_Action
{

    const CONTEXT_JSON = 'json';

    protected $_table;

    public function init()
    {
        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->addActionContext('select',  self::CONTEXT_JSON)
                    ->addActionContext('create',  self::CONTEXT_JSON)
                    ->addActionContext('update',  self::CONTEXT_JSON)
                    ->addActionContext('destroy', self::CONTEXT_JSON)
                    ->initContext();

        $this->_table = new Application_Db_Table(array(
            Application_Db_Table::NAME => 'rebecca_user'
        ));

        $this->view->success = false;
    }

    public function selectAction()
    {
        $cols   = $this->_table->info(Application_Db_Table::COLS);
        $select = $this->_table->select();
        $request = $this->getRequest();

        $query = $request->getPost('query');
        $start = $request->getPost('start');
        $limit = $request->getPost('limit');
        $dir   = $request->getPost('dir', Zend_Db_Table_Select::SQL_ASC);
        $sort  = $request->getPost('sort');

        if(in_array($sort, $cols)) {
            $select->order($sort . ' ' . $dir);
        }

        if($start || $limit) {
            $select->limit($limit, $start);
        }

        if($query !== null) {
           foreach($cols as $col) {
               $select->orWhere($col . ' LIKE ?', '%' . $query . '%');
           }
        }
        $rowset = $this->_table->fetchAll($select);

        $numrows = Application_Db_Table::getDefaultAdapter()->fetchOne(
            $this->_table->select()->from($this->_table, 'count(*)')
        );
var_dump($rowset);
        $this->view->data = $rowset->toArray();
        $this->view->total = $numrows;
        $this->view->success = true;
    }
    
    public function destroyAction()
    {
        $this->view->data = new stdClass();
        $id = $_POST['data'] + 0; // typecast :)
        
        try {
            $rowset = $this->_table->find($id);
        } catch (Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $row = $rowset->current();

        try {
            $row->delete();
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->success = true;
    }
    
    public function createAction()
    {
        $data = array_filter( // filter '', null, false values
            Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY)
        );
        $this->view->data = new stdClass(); // default data

        if (!array_key_exists('username', $data) || // checking required fields
            !array_key_exists('password', $data) ||
            !array_key_exists('rights',   $data)
           ) {
            $this->view->message = 'Es wurden nicht alle Felder ausgef&uuml;llt';
            return;
        }

        try {
            $rowset = $this->_table->fetchAll($this->_table->select()->where('username = ?', $data['username']));
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        if (count($rowset) > 0) {
            $this->view->message = 'Der Benutzer ' . $data['username'] . ' existiert bereits!';
            return;
        }
        
        $data['password'] = md5($data['password']);

        $row = $this->_table->createRow($data);

        try {
            $row->save();
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->data = $row->toArray();
        $this->view->success = true;
    }
    
    public function updateAction()
    {
        $data = array_filter( // filter '', null, false values
            array('id' => $_POST['id']) +
            Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY)
        );
        $this->view->data = $data; // default data

        try {
            $rowset = $this->_table->find($_POST['id']);
        } catch (Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $row = $rowset->current();
        if (isset($data['password'])) {
            $data['password'] = md5($data['password']);
        }
        $row->setFromArray($data);

        try {
            $row->save();
        } catch (Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        $this->view->data = $row->toArray();
        $this->view->success = true;
    }

}

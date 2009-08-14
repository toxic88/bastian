<?php

class VokabelnController extends Zend_Controller_Action
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
            Application_Db_Table::NAME => 'rebecca_vokabeln'
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

        $this->view->data = $rowset->toArray();
        $this->view->total = $numrows;
        $this->view->success = true;
    }

    /**
     * 1. set default return data
     * 2. check if the row exists
     * 3. delete the row
     */
    public function destroyAction()
    {
        $this->view->data = new stdClass();
        
        try {
            $rowset = $this->_table->find($_POST['data']);
        } catch (Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        if (count($rowset) <= 0) {
            $this->view->message = 'Die Vokabel mit der id "' . $_POST['data'] . '" existiert nicht!';
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

    /**
     * 1. validate userinput
     * 2. set default return data
     * 3. checking required fields
     * 4. prevent the same username
     * 5. create password hash
     * 6. create and save the row
     */
    public function createAction()
    {
        $data = array_filter( // filter '', null, false values
            Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY)
        );
        $this->view->data = new stdClass(); // default data

        if (!array_key_exists('deutsch',  $data) || // checking required fields
            !array_key_exists('englisch', $data) ||
            !array_key_exists('spanisch', $data)
           ) {
            $this->view->message = 'Es wurden nicht alle Felder ausgef&uuml;llt';
            return;
        }

        try {
            $rowset = $this->_table->fetchAll($this->_table->select()->where('deutsch = ?', $data['deutsch']));
        } catch(Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        if (count($rowset) > 0) {
            $this->view->message = 'Die Vokabel "' . $data['deutsch'] . '" existiert bereits!';
            return;
        }
        
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

    /**
     * 1. valdate userinput
     * 2. set the defaut return data
     * 3. prevent the same username
     * 4. check if the row exists
     * 5. create password hash
     * 6. setting the modified data and update the row
     */
    public function updateAction()
    {
        $data = array_filter( // filter '', null, false values
            Zend_Json::decode($_POST['data'], Zend_Json::TYPE_ARRAY)
        );
        $this->view->data = $data; // default data

        if ($data['deutsch']) { // prevent the same usernames
            try {
                $rowset = $this->_table->fetchAll($this->_table->select()->where('deutsch = ?', $data['deutsch']));
            } catch(Exception $e) {
                $this->view->message = $e->getMessage();
                return;
            }

            if (count($rowset) > 0) {
                $this->view->message = 'Die Vokabel "' . $data['deutsch'] . '" existiert bereits!';
                return;
            }
        }

        try {
            $rowset = $this->_table->find($data['id']);
        } catch (Exception $e) {
            $this->view->message = $e->getMessage();
            return;
        }

        if (count($rowset) <= 0) {
            $this->view->message = 'Der Vokabel mit der id "' . $data['id'] . '" existiert nicht!';
            return;
        }

        $row = $rowset->current();
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

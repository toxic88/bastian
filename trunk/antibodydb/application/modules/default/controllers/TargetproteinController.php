<?php

class TargetproteinController extends Zend_Controller_Action implements Antibodydb_Controller_AjaxInterface
{

    protected $targetprotein;
    protected $antibody;

    public function init()
    {
        $this->view->success = false;

        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->addActionContext('save',   self::CONTEXT_JSON)
                    ->addActionContext('select', self::CONTEXT_JSON)
                    ->addActionContext('load',   self::CONTEXT_JSON)
                    ->addActionContext('delete', self::CONTEXT_JSON)
                    ->initContext();

        $this->targetprotein = Antibodydb_TableManager::get('Targetprotein');
        $this->antibody      = Antibodydb_TableManager::get('Antibody');
    }

    public function saveAction()
    {
        $request = $this->getRequest();

        if($data = $request->getPost('data')) {
            $_POST = Zend_Json::decode($data, Zend_Json::TYPE_ARRAY) + $_POST;
        }

        $this->view->data = $request->getPost();

        $targetprotein = $request->getPost('Targetprotein');
        if($targetprotein === null) {
            $this->view->error = 'Targetprotein not set.';
            return;
        }

        $test = $this->targetprotein->fetchAll(
            $this->targetprotein->select()->where('Targetprotein = ?', $targetprotein)
        );

        $id = $request->getPost('id');

        if($test->count() > 0 && $test->current()->id !== $id) {
            $this->view->error = 'Targetprotein "' . $targetprotein . '" allready exists.';
            return;
        }

        $row = null;

        if(empty($id)) {
            $row = $this->targetprotein->createRow();
        } else {
            $rows = $this->targetprotein->find($id);

            if($rows->count() <= 0) {
                $this->view->error = 'No record found to update.';
                return;
            }

            $row = $rows->current();
        }
        unset($_POST['id']); // can't set the id

        $row->setFromArray($request->getPost());
        $row->save();

        $this->view->data = $row->toArray();
        $this->view->success = true;
    }

    public function selectAction()
    {
        $request = $this->getRequest();

        if($id = $request->getPost('id')) {
            $rows = $this->targetprotein->find($id);
        } else {
            $cols   = $this->targetprotein->info(Antibodydb_Db_Table::COLS);
            $select = $this->targetprotein->select();

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
                   if($col == 'References') $col = '[References]'; // don't know why this only here not works
                   $select->orWhere($col . ' LIKE ?', '%' . $query . '%');
               }
            }
            $rows = $this->targetprotein->fetchAll($select);
        }

        $numrows = Antibodydb_Db_Table::getDefaultAdapter()->fetchOne(
            $this->targetprotein->select()->from($this->targetprotein, 'count(*)')
        );

        $this->view->success = true;
        $this->view->data = $rows->toClient();
        $this->view->total = $numrows;
    }

    public function deleteAction()
    {
        $request = $this->getRequest();
        
        if($data = $request->getPost('data')) {
            $this->view->id = $data;
            $_POST['id'] = (int) $data;
        }    
    
        $id = $request->getPost('id');
        
        $row = $this->findById($id);

        if($row === null) {
            return;
        }

        $test = $this->antibody->fetchAll(
            $this->antibody->select()->where('fs_T_Targetprotein_id = ?', $id)
        );

        if($test->count() > 0) {
            $this->view->error = $test->count() . ' antibodies have this targetprotein. Please change the antibodies targetprotein!';
            return;
        }

        $row->delete();
        $this->view->success = true;
    }

    protected function findById($id = null)
    {
        if(empty($id)) {
            $this->view->error = 'No id specified';
            return null;
        }

        $rows = $this->targetprotein->find($id);

        if($rows->count() <= 0) {
            $this->view->error = 'No record found';
            return null;
        }

        return $rows->current();
    }

}

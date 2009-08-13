<?php

class AuthController extends Zend_Controller_Action
{

    const CONTEXT_JSON = 'json';

    public function init()
    {
        $ajaxContext = $this->_helper->ajaxContext();
        $ajaxContext->addActionContext('login',  self::CONTEXT_JSON)
                    ->addActionContext('logout', self::CONTEXT_JSON)
                    ->initContext();
        
        $this->view->success = false;
    }

    public function loginAction()
    {
        $adapter = new Zend_Auth_Adapter_DbTable(
            Zend_Db_Table::getDefaultAdapter(),
            'rebecca_user', // the user database
            'username', // column names
            'password',
            'MD5(?)' // mysql supports MD5
        );

        $username = $this->getRequest()->getPost('Username');
        $password = $this->getRequest()->getPost('Password');

        if(in_array(null, array($username, $password), true)) {
            $this->view->message = 'No username or password given!';
            return;
        }

        $adapter->setIdentity($username)
                ->setCredential($password);

        $auth = Zend_Auth::getInstance();
        $result = $auth->authenticate($adapter); // get the result object

        if ($result->isValid()) {
            $auth->getStorage()->write($adapter->getResultRowObject(null, 'password'));

            $this->view->success = true;
        } else {
            $this->view->message = 'Falscher Benutzername oder Passwort!';
        }
    }

    public function logoutAction()
    {
        Zend_Auth::getInstance()->clearIdentity();
        $this->view->success = true;
    }

}

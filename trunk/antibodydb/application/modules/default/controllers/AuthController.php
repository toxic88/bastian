<?php
// TODO: use ajaxContext instead of contextSwitch
class AuthController extends Zend_Controller_Action
{

    const CONTEXT_JSON = 'json';

    protected $adapter;

    public function init()
    {
        $contextSwitch = $this->_helper->contextSwitch();
        $contextSwitch->addActionContext('login', self::CONTEXT_JSON)
                      ->initContext();

        $this->adapter = new Zend_Auth_Adapter_DbTable(
            Zend_Db_Table::getDefaultAdapter(),
            Zend_Registry::get('config')->db->tables->User, // the user database
            'Username', // column names
            'Password'
        );
    }

    public function indexAction()
    {
        if(Zend_Auth::getInstance()->hasIdentity()) {
            $this->_redirect('/'); // allready logged in
        }
        
        $this->view->headTitle('Antibodydb - Login');
        $this->view->headLink()->appendStylesheet('css/ext.css')
                               ->appendStylesheet('css/Antibodydb.css');
        $this->view->headScript()->appendFile('js/ext.js')
                                 ->appendFile('js/Antibodydb.login.js');
        
        if ($username = Zend_Registry::get('session')->username) {
        	$this->view->headScript()->appendScript('Ext.onReady(function(){Antibodydb.LoginWindow.form.username.setValue("' . $username . '").ownerCt.password.focus(false, 400);});');
        }
        
    }
    
    public function logoutAction()
    {
        Zend_Auth::getInstance()->clearIdentity(); // delete session

        $this->_redirect('/auth'); // don't know why _forward('index') or render('index') not working...
    }
    
    public function loginAction()
    {
        $this->view->success = false;

        $identity   = $this->getRequest()->getPost('Username'); // return null if not defined
        $credential = $this->getRequest()->getPost('Password');

        if(in_array(null, array($identity, $credential), true)) {
            $this->view->error = 'No username or password given!';
            return;
        }

        $this->adapter->setIdentity($identity)
                      ->setCredential(md5($credential)); // mssql server has no md5 method...
        
        $auth = Zend_Auth::getInstance();
        $result = $auth->authenticate($this->adapter); // get the result object
        
        if ($result->isValid()) {
            $auth->getStorage()->write($this->adapter->getResultRowObject(null, 'Password'));

            Zend_Registry::get('session')->username = $identity; // store it in the main session, wich normaly never gets destroyed

            $this->view->success = true;
        } else {
            $this->view->error = 'Wrong username or password!';
        }
        
    }
    
}

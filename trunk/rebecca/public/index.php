<?php
set_include_path(implode(PATH_SEPARATOR, array(
    realpath(dirname(__FILE__) . '/../../static/Zend-1.9.2'),
    get_include_path()
)));

defined('APPLICATION_PATH') || define('APPLICATION_PATH', realpath(dirname(__FILE__) . '/../application'));
defined('APPLICATION_ENV')  || define('APPLICATION_ENV',  'development'); // enviroment

require_once 'Zend/Application.php';

$application = new Zend_Application(
    APPLICATION_ENV,
    APPLICATION_PATH . '/config.ini'
);
$application->bootstrap();
$application->run();

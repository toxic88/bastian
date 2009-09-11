<?php
set_include_path(
	dirname(__FILE__) . '/library' . PATH_SEPARATOR . get_include_path()
);
require_once 'Zend/Loader/Autoloader.php';
Zend_Loader_Autoloader::getInstance();

$db = Zend_Db::factory('Pdo_Mssql', array(
	'host' => 'newage',
	'username' => 'phpdeveloper',
	'password' => 'midrange',
	'dbname' => 'phpwebdb'
));
Zend_Db_Table_Abstract::setDefaultAdapter($db);

echo '<pre>';

class Targetprotein extends Zend_Db_Table_Abstract
{

	protected $_name = 'DEV_T_Targetprotein';
	
	protected $_dependentTables = array('Antibody');

}

class Incubationprotocol extends Zend_Db_Table_Abstract
{

	protected $_name = 'DEV_T_Incubationprotocol';
	
	protected $_dependentTables = array('Antibody');
	
	protected $_referenceMap = array(
		'Bufferset' => array(
			'columns' => 'fs_T_Bufferset_id',
			'refTableClass' => 'Bufferset',
			'refColumns' => 'id'
		)
	);
	
}

class Bufferset extends Zend_Db_Table_Abstract
{
	
	protected $_name = 'DEV_T_Bufferset';
	
	protected $_dependentTables = array('Incubationprotocol');
	
}

class Comments extends Zend_Db_Table_Abstract
{

	protected $_name = 'DEV_T_Comments';
	
	protected $_dependentTables = array('Antibody');

}

class Antibody extends Zend_Db_Table_Abstract
{

	protected $_name = 'DEV_T_Antibody';

	protected $_dependentTables = array('Images');	
	
	protected $_referenceMap = array(
		'Targetprotein' => array(
			'columns' => 'fs_T_Targetprotein_id',
			'refTableClass' => 'Targetprotein',
			'refColumns' => 'id'
		),
		'Incubationprotocol' => array(
			'columns' => 'fs_T_Incubationprotocol_id',
			'refTableClass' => 'Incubationprotocol',
			'refColumns' => 'id'
		),
		'Comments' => array(
			'columns' => 'fs_T_Comments_id',
			'refTableClass' => 'Comments',
			'refColumns' => 'id'
		)
	);
	
}

class Images extends Zend_Db_Table_Abstract
{
	
	protected $_name = 'DEV_T_Images';
	
	protected $_referenceMap = array(
		'Antibody' => array(
			'columns' => 'fs_T_Antibody_id',
			'refTableClass' => 'Antibody',
			'refColumns' => 'id'
		),
		'Lane' => array(
			'columns' => 'fs_T_Lane_id',
			'refTableClass' => 'Lane',
			'refColumns' => 'id'
		),
		'Scannersettings' => array(
			'columns' => 'fs_T_Scannersettings_id',
			'refTableClass' => 'Scannersettings',
			'refColumns' => 'id'
		),
		'SDS' => array(
			'columns' => 'fs_T_SDS_id',
			'refTableClass' => 'SDS',
			'refColumns' => 'id'
		)
	);
	
}

class Lane extends Zend_Db_Table_Abstract
{
	
	protected $_name = 'DEV_T_Lane';
	
	protected $_dependentTables = array('Images');
	
}

class Scannersettings extends Zend_Db_Table_Abstract
{
	
	protected $_name = 'DEV_T_Scannersettings';
	
	protected $_dependentTables = array('Images');
	
}

class SDS extends Zend_Db_Table_Abstract
{
	
	protected $_name = 'DEV_T_SDS';
	
	protected $_dependentTables = array('Images');
	
}

$Antibody = new Antibody();

$antibody = $Antibody->find(1)->current();
$targetprotein = $antibody->findParentTargetprotein(); // n to 1
$incubationprotocol = $antibody->findParentIncubationprotocol(); // n to 1
$bufferset = $incubationprotocol->findParentBufferset(); // n to 1
$comments = $antibody->findParentComments(); // n to 1
$images = $antibody->findImages(); // 1 to n

$image = $images->current();
$lane = $image->findParentLane();

var_dump($lane->toArray());

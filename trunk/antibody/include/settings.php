<?php
/*
	Diese Datei wird von ALLEN anderen aufgerufen

	Es werden verschiedene Variablen gesetzt:
		- $antibody->time -> timestamp
		- $antibody->sessionName -> Name von der verwendeten SESSION (login)
		- $antibody->cookieName -> Name von dem verwendeten COOKIE (für autologin)
		- $antibody->postName -> Array mit Name und Value von dem POST-Parameter der immer an die AJAX Seiten gesendet wird
		- $antibody->mainPage -> Name der Hauptseite

	Zusätzlich wird noch die include/dbconnection.php required!
 */
header("Content-Type: text/html; charset=utf-8");
session_start();
require_once "dbconnection.php";

$antibody->time = time();
// Der Sessionname der immer verwendet wird
$antibody->sessionName = "Antibodydb";
// Der Cookiename der immer verwendet wird
$antibody->cookieName = "Antibodydb";
// Diese Variable ist extrem wichtig für die Sicherheit!!!
// $antibody->postName[0] ist der POST-key für die AJAX-Requests
// $antibody->postName[1] ist der POST-value für die AJAX-Requests
// siehe include/check.php und javascript/main.php
$antibody->postName = array(md5("78237asdf768a6sdf7as6df7"), md5("kasjdf86a7234lap21312hsd7f"));
// Die Default-Seite
$antibody->mainPage = "Default.php";
// Der Ordner für die Uploads
$antibody->uploadDir = "uploads/";

// Diese Array ist nur für das Userinterface und kann natürlich beliebig lang erweitert werden :)
// Dieses Array wird für die Beschriftung der Tabs bei den Bildern eingesetzt.
// Falls das Array nicht ausreicht wird anstatt des Zahlnamens die Zahl hineingeschrieben.
// siehe javascript/main.php oder changeDataset.php und newDataset.php (weit unten im Javascript-Teil)
$numbers = array(
	  "Zero"
	, "One"
	, "Two"
	, "Three"
	, "Four"
	, "Five"
	, "Six"
	, "Seven"
	, "Eight"
	, "Nine"
	, "Ten"
	, "Eleven"
	, "Twelve"
	, "Thirdteen"
	, "Fourteen"
	, "Fifteen"
	, "Sixteen"
	, "Seventeen"
	, "Eighteen"
	, "Nineteen"
	, "Twenty"
);

// Helper Function
// Da der Stoneage eine ältere PHP-Version hat gibt es ein paar nützliche Funktionen leider nicht...
// Daher habe ich sie hier einfach selber geschrieben (natürlich nur die die ich gebraucht habe)
// Diese Helper Function kann auch stehen bleiben wenn das Projekt verschoben wird

// json_encode codiert ein (Assoziatives)Array zu einem JSON String
// $data["test"] = 1;
// $data["blabla"] = array(1, 2);
// json_encode( $data ); // { "test":1, "blabla":[ 1, 2 ] }
if (!function_exists('json_encode'))
{
  function json_encode($a=false)
  {
    if (is_null($a)) return 'null';
    if ($a === false) return 'false';
    if ($a === true) return 'true';
    if (is_scalar($a))
    {
      if (is_float($a))
      {
        // Always use "." for floats.
        return floatval(str_replace(",", ".", strval($a)));
      }

      if (is_string($a))
      {
        static $jsonReplaces = array(array("\\", "/", "\n", "\t", "\r", "\b", "\f", '"'), array('\\\\', '\\/', '\\n', '\\t', '\\r', '\\b', '\\f', '\"'));
        return '"' . trim(str_replace($jsonReplaces[0], $jsonReplaces[1], $a)) . '"';
      }
      else
        return $a;
    }
    $isList = true;
    for ($i = 0, reset($a); $i < count($a); $i++, next($a))
    {
      if (key($a) !== $i)
      {
        $isList = false;
        break;
      }
    }
    $result = array();
    if ($isList)
    {
      foreach ($a as $v) $result[] = json_encode($v);
      return '[' . join(',', $result) . ']';
    }
    else
    {
      foreach ($a as $k => $v) $result[] = json_encode($k).':'.json_encode($v);
      return '{' . join(',', $result) . '}';
    }
  }
}

?>

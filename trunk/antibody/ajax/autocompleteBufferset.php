<?php

require "../include/settings.php";

$bu = $antibody->mssql->fetch("SELECT * FROM T_Bufferset WHERE Bufferset LIKE '" . mysql_real_escape_string($_GET["q"]) . "%'");

if(count($bu)>0) {

	$ids = array();

	foreach($bu as $b) {

		if(!in_array($b["Bufferset"], $ids) && trim($b["Bufferset"]) != "") {

			echo $b["Bufferset"] . "|" . $b["Bufferset"] . "|" . $b['Washbuffer'] . "|" . $b['Incubation_1_AB'] . "|" . $b['Incubation_2_AB'] . "|" . $b['Bufferset_Blocking'] . "\n";

			$ids[] = $b["Bufferset"];

		}

	}

}?>

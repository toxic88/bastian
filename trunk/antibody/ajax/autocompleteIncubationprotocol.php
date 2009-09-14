<?php

require "../include/settings.php";

$in = $antibody->mssql->fetch("SELECT * FROM T_Incubationprotokoll WHERE Incubationprotocol_id LIKE '" . mysql_real_escape_string($_GET["q"]) . "%'");

$ids = array();

if(count($in)>0) {

	foreach($in as $i) {

		if(!in_array($i["Incubationprotocol_id"] , $ids) && trim($i["Incubationprotocol_id"]) != "") {
			$b = $antibody->mssql->fetch("SELECT * FROM T_Bufferset WHERE Bufferset = '" . $i["fs_Bufferset"] . "'", "assoc", false);

			echo $i["Incubationprotocol_id"] . "|" . $i["Incubationprotocol_id"] . "|" . $i['Blocking'] . "|" . $i['AB_Incubation_1'] . "|" . $i['Washing_1'] . "|" . $i['AB_Incubation_2'] . "|" . $i['Washing_2'] . "|" . $i['fs_Bufferset'] . "|" . $i['fs_Bufferset'] . "|" . $b['Washbuffer'] . "|" . $b['Incubation_1_AB'] . "|" . $b['Incubation_2_AB'] . "|" . $b['Bufferset_Blocking'] . "\n";

			$ids[] = $i["Incubationprotocol_id"];
		}

	}
}

?>

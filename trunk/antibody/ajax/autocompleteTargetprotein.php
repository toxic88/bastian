<?php
header('Content-type: text/html; charset="utf-8"');
require "../include/settings.php";

$tp = $antibody->mssql->fetch("SELECT * FROM T_Targetprotein WHERE Target_Protein_ID LIKE '" . mysql_real_escape_string($_GET["q"]) . "%'");

if(count($tp)>0){

	foreach($tp as $t) {

		echo $t["Target_Protein_ID"] . "|" . $t["Target_Protein_ID"] . "|" . $t["MW_kD"] . "|" . $t["SwissProt_Accsession"] . "|" . $t["Supplier"] . "|" . $t["Target_Protein_Stock"] . "|" . $t["Target_Protein_References"] . "\n";

	}

}

?>

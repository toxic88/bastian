<?php
header('Content-type: text/html; charset="utf-8"');
include "../include/settings.php";

$tp = $antibody->mssql->fetch("SELECT * FROM T_Targetprotein WHERE Target_Protein_ID = '" . mysql_real_escape_string($_GET["Targetprotein"]) . "'", "assoc", false);

if(isset($tp["id"])) {
?>
<table class="table_view">
    <tr>
        <th>Target Protein</th>
        <th>MW [kD]</th>
        <th>GeneID</th>
        <th>Supplier</th>
        <th>Synonym</th>
        <th>References</th>
    </tr>
    <tr class="alt">
        <td><?=$tp["Target_Protein_ID"]?></td>
        <td><?=$tp["MW_kD"]?></td>
        <td><?=$tp["SwissProt_Accsession"]?></td>
        <td><?=$tp["Supplier"]?></td>
        <td><?=$tp["Target_Protein_Stock"]?></td>
        <td><?=$tp["Target_Protein_References"]?></td>
    </tr>
</table>
<?php
} else {
?>
    Error...<?=$_GET["Targetprotein"]?>
<?php
}
?>

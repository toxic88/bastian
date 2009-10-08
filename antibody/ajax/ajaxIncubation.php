<?php
header('Content-type: text/html; charset="utf-8"');
include "../include/settings.php";

$i = $antibody->mssql->fetch("SELECT * FROM T_Incubationprotokoll WHERE Gid = '" . mysql_real_escape_string($_GET["Antibody"]) . "'", "assoc", false);

?>
<table class="table_view">
    <tr>
        <th>Incubation Protocol</th>
        <th>Blocking</th>
        <th>1&deg;AB Incubation</th>
        <th>Washing l</th>
        <th>2&deg;AB Incubation</th>
        <th>Washing ll</th>
    </tr>
    <tr class="alt">
        <td><?=$i["Incubationprotocol_id"]?></td>
        <td><?=$i["Blocking"]?></td>
        <td><?=$i["AB_Incubation_1"]?></td>
        <td><?=$i["Washing_1"]?></td>
        <td><?=$i["AB_Incubation_2"]?></td>
        <td><?=$i["Washing_2"]?></td>
    </tr>
</table>
<br />
<?php

$b = $antibody->mssql->fetch("SELECT * FROM T_Bufferset WHERE Gid = '" . mysql_real_escape_string($_GET["Antibody"]) . "'", "assoc", false);

?>
<table class="table_view">
    <tr>
        <th>Bufferset</th>
        <th>Washbuffer</th>
        <th>Incubation 1&deg;AB</th>
        <th>Incubation 2&deg;AB</th>
        <th>Blocking</th>
    </tr>
    <tr class="alt">
        <td><?=$b["Bufferset"]?></td>
        <td><?=$b["Washbuffer"]?></td>
        <td><?=$b["Incubation_1_AB"]?></td>
        <td><?=$b["Incubation_2_AB"]?></td>
        <td><?=$b["Bufferset_Blocking"]?></td>
    </tr>
</table>

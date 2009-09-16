<?php
header('Content-type: text/html; charset="utf-8"');
include "../include/settings.php";

$wi = $antibody->mssql->fetch("SELECT * FROM T_Westernimage WHERE Gid = '" . mysql_real_escape_string($_GET["Antibody"]) . "' ORDER BY Westernimage_ID ASC", "assoc", true);

$display = " style='display:none;' ";

if(count($wi)==0)
    exit;

?>
<div id="<?=$antibody->time?>" class="ui-tabs-nav">
<?php
if(count($wi)>1) {
?>
    <ul class="ui-tabs-nav">
<?php
    foreach($wi as $w) {
?>
        <li class="ui-tabs-nav-item"><a href="javascript:;" onclick="$('#<?=$antibody->time?>').children('div').hide();$('#<?=$antibody->time.$w["Westernimage_ID"]?>').show();"><?=$w["Westernimage_ID"]+1?></a></li>
<?php
    }
?>
    </ul>
<?php
}
foreach($wi as $id => $w) {
?>
    <div class="ui-tabs-panel" id="<?=$antibody->time.$w["Westernimage_ID"]?>" <?=($id>0)? $display : ""?>>

        <table class="table_view" style="float:right;margin-left:10px;">
            <tr>
                <th>Lane</th>
                <th>Lysate/Protein</th>
                <th>Total Protein</th>
            </tr>
<?php
    $lane = $antibody->mssql->fetch("SELECT * FROM T_Lane WHERE Gid = '" . mysql_real_escape_string($_GET["Antibody"]) . "' AND Westernimage_ID = '" . mysql_real_escape_string($w["Westernimage_ID"]) . "'", "assoc", false);
    for($i=1;$i<=15;$i++) {
?>
            <tr class="<?=($i % 2 == 1) ? 'alt' : ''?>">
                <td><?=$i?></td>
                <td><?=$lane["Lysate_Protein_".$i]?></td>
                <td><?=$lane["Total_Protein_".$i]?></td>
            </tr>
<?php
    }
?>
        </table>


        <div style="width:110px;float:left;">
            <a href="<?=$w["Imagepath"]?>" target="_blank"><img src="<?=$w["Imagepath"]?>" style="max-height:150px;max-width:100px;margin-bottom:5px;" /></a>
<?php
    $ss = $antibody->mssql->fetch("SELECT * FROM T_Scannersettings WHERE Gid = '" . mysql_real_escape_string($_GET["Antibody"]) . "' AND Westernimage_ID = '" . mysql_real_escape_string($w["Westernimage_ID"]) . "'", "assoc", false);
?>
            <table class="table_view">
                <tr class="alt">
                    <th>Sensivity</th>
                    <td><?=$ss["Sensivity"]?></td>
                </tr>
                <tr>
                    <th>Linear Manual</th>
                    <td><?=$ss["Linear_Manual"]?></td>
                </tr>
                <tr class="alt">
                    <th>Brightness</th>
                    <td><?=$ss["Brightness"]?></td>
                </tr>
                <tr>
                    <th>Contrast</th>
                    <td><?=$ss["Contrast"]?></td>
                </tr>
            </table>
        </div>
        <br style="clear:both;" />
    </div>
<?php
}
?>
</div>

<?php
include "include/check.php";

// SIEHE Antibody1.php

$antibodies = $antibody->mssql->fetch("SELECT * FROM T_Antibodydb ");

$rating = $antibody->mssql->fetch("SELECT * FROM T_Assessment");
foreach($rating as $id => $rate) {
	$r[$rate["Number"]] = $rate["Text"];
}

$all = count($antibodies);
?>
<div style="float:right;">Antibodies: <?=$all?></div>
<h2>Antibodies</h2>

<div id="pager" class="pager" style="text-align:center;">
	<form>
			<div>
				Search: <input type="text" id="search" />
				in: <select id="colum" size="1">
				</select>
			</div>
			<br />
			<div>
				<img src="../images/first.png" class="first">
				<img src="../images/prev.png" class="prev">
				<span class="pagedisplay"></span>
				<img src="../images/next.png" class="next">
				<img src="../images/last.png" class="last">
				<select class="pagesize">
					<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
				</select>
			</div>
		</form>
</div>
<br />
<table id="Antibody2" class="table_view" style="margin:5px auto;">
	<thead>
		<tr class="table_header">
			<th>Antibody ID</th>
			<th>Target Protein</th>
			<th>IP</th>
			<th>Embedding</th>
			<th>Fixation</th>
			<th>FACS</th>
			<th>Capture Array</th>
			<th>RPPA</th>
			<th><img src="http://stoneage/bestelldatenbank/images/icon_edit.gif" /></th>
		</tr>
	</thead>
<?php
for($i=0;$i<$all;$i++) {
	$a = $antibodies[$i];
?>
	<tr>
		<?php
			if(trim($a["Data_Sheet_Path"])=="") {
		?>
		<td><?=$a["Antibody_ID"]?></td>
		<?php
			} else {
		?>
		<td><a href="<?=$a["Data_Sheet_Path"]?>"><?=$a["Antibody_ID"]?></a></td>
		<?php
			}
		?>
		<td><a href="javascript:;" onclick="return OLgetAJAX('ajax/ajaxTargetprotein.php?Targetprotein=<?=$a["fs_Target_Protein"]?>', tooltip.defaultCommand);" onmouseout="return nd();"><?=$a["fs_Target_Protein"]?></a></td>
		<?php
			if(trim($a["ip_comment"])=="") {
		?>
		<td><?=$r[$a["IP"]]?></td>
		<?php
			} else {
		?>
		<td><a href="javascript:;" onmouseover="overlib('<?=nl2br($a["ip_comment"])?>', CENTER)" onmouseout="nd();"><?=$r[$a["IP"]]?></a></td>
		<?php
			} if(trim($a["ihc_comment"])=="") {
		?>
		<td><?=$r[$a["IHC"]]?></td>
		<?php
			} else {
		?>
		<td><a href="javascript:;" onmouseover="overlib('<?=nl2br($a["ihc_comment"])?>', CENTER)" onmouseout="nd();"><?=$r[$a["IHC"]]?></a></td>
		<?php
			} if(trim($a["if_comment"])=="") {
		?>
		<td><?=$r[$a["[IF]"]]?></td>
		<?php
			} else {
		?>
		<td><a href="javascript:;" onmouseover="overlib('<?=nl2br($a["if_comment"])?>', CENTER)" onmouseout="nd();"><?=$r[$a["[IF]"]]?></a></td>
		<?php
			} if(trim($a["FACS"])=="") {
		?>
		<td><?=$r[$a["FACS"]]?></td>
		<?php
			} else {
		?>
		<td><a href="javascript:;" onmouseover="overlib('<?=nl2br($a["facs_comment"])?>', CENTER)"><?=$r[$a["FACS"]]?></a></td>
		<?php
			} if(trim($a["capture_array_comment"])=="") {
		?>
		<td><?=$r[$a["Evaluation_Capture_Array"]]?></td>
		<?php 
			} else {
		?>
		<td><a href="javascript:;" onmouseover="overlib('<?=nl2br($a["capture_array_comment"])?>', CENTER)" onmouseout="nd();"><?=$r[$a["Evaluation_Capture_Array"]]?></a></td>

		<?php
			} if(trim($a["rppa_comment"])=="") {
		?>
		<td><?=$r[$a["Evaluation_RPPA"]]?></td>
		<?php
			} else {
		?>
		<td><a href="javascript:;" onmouseover="overlib('<?=nl2br($a["rppa_comment"])?>', CENTER)" onmouseout="nd();"><?=$r[$a["Evaluation_RPPA"]]?></a></td>
		<?php
			}
		?>

		<td><a href="javascript:;" onclick="load('changeDataset.php?ID=<?=$a["Antibody_ID"]?>');">Edit</a></td>
	</tr>
<?php
}
?>
</table>

<br />
<br />
<script type="text/javascript">
login.check();
$("#Antibody2").tablesorter({ widthFixed: true }).tablesorterPager({ container: $("#pager"), positionFixed: false }).tablesorterSearch({ colums:"#colum", searchInput:$("#search") });
</script>

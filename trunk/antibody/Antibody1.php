<?php
include "include/check.php";

// Alle antibodies holen...
$antibodies = $antibody->mssql->fetch("SELECT * FROM T_Antibodydb ");

// Ratings holen
$rating = $antibody->mssql->fetch("SELECT * FROM T_Assessment");
// Ratings in ein schönes Array verwandeln
foreach($rating as $id => $rate) {
	$r[$rate["Number"]] = $rate["Text"];
}
// Alle antibodies Zählen
// count() ist sowas wie .length bei Arrays in anderen Sprachen wie Javascript,
// allerdings geht count() auch bei assoziativen arrays
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
<table class="table_view" id="Antibody1" style="margin:5px auto;">
	<thead>
		<tr class="table_header">
			<th>Antibody ID</th>
			<th>Lot#</th>
			<th>Target Protein</th>
			<th>Source</th>
			<th>Western Blot</th>
			<th>Dilution Western</th>
			<th>Incubation Protocol</th>
			<th>Stock</th>
			<th>Evaluation Western</th>
			<th><img src="http://stoneage/bestelldatenbank/images/icon_edit.gif" /></th>
		</tr>
	</thead>
<?php
// eine foreach($antibody as $i => $a) ginge auch aber ich habe hier eine for()-Schleife genommen,
// damit man später vielleicht noch eine Pager-Funktion einbauen kann
for($i=0;$i<$all;$i++) {
	$a = $antibodies[$i];
?>
	<tr>
		<?php
			// falls nichts drinsteht wird kein Link erzeugt
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
		<td><?=$a["Lot"]?></td>
		<!-- Die folgenden onclick Funktionen werden gebraucht damit man dann den Tooltip sieht -->
		<td><a href="javascript:;" onclick="return OLgetAJAX('ajax/ajaxTargetprotein.php?Targetprotein=<?=$a["fs_Target_Protein"]?>', tooltip.defaultCommand)" onmouseout="return nd();"><?=$a["fs_Target_Protein"]?></a></td>
		<td><?=$a["Source"]?></td>
		<td><a href="javascript:;" onclick="return OLgetAJAX('ajax/ajaxWesternimage.php?Antibody=<?=$a["Antibody_ID"]?>', tooltip.stickyCommand);" onmouseout="return nd();">Link</a></td>
		<td><?=$a["Dilution_Western"]?></td>
		<td><a href="javascript:;" onclick="return OLgetAJAX('ajax/ajaxIncubation.php?Antibody=<?=$a["Antibody_ID"]?>', tooltip.defaultCommand);" onmouseout="return nd();"><?=$a["Incubation_Protokoll"]?></a></td>
		<td><?=$a["Stock"]?></td>

		<?php
			// Siehe Antibody_ID
			if(trim($a["western_comment"])=="") {
		?>
		<td><?=$r[$a["Evaluation_Western"]]?></td>
		<?php
			} else {
		?>
		<td><a href="javascript:;" onmouseover="return overlib('<?=nl2br($a["western_comment"])?>', CENTER);" onmouseout="return nd();"><?=$r[$a["Evaluation_Western"]]?></a></td>

		<?php
			}
		?>
		<!-- hier gehts zum Editieren! -->
		<td><a href="javascript:;" onclick="load('changeDataset.php?ID=<?=$a["Antibody_ID"]?>');">Edit</a></td>
	</tr>
<?php
}
?>
</table>
<br />
<br />
<script type="text/javascript">
// login.check() überprüft die Links, ob sie mit dem aktuellen Login-Status übereinstimmen
login.check();
// Das ist ein Client-Side-Tablesorter man kann dann auf die header klicken und dann wird automatisch sortiert
// Siehe: http://tablesorter.com/docs/
$("#Antibody1").tablesorter({ widthFixed: true }).tablesorterPager({ container: $("#pager"), positionFixed: false }).tablesorterSearch({ colums:"#colum", searchInput:$("#search") });
</script>

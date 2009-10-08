<?php
include "include/check.php";
// so hier das große Formular:

// Ratings aus der Datenbank holen (somit kann man sie leicht verändern)
$rating = $antibody->mssql->fetch("SELECT * FROM T_Assessment", "assoc", true);

?>
<form action="upload.php" method="post" enctype="multipart/form-data" id="newDataset">
<h2>New Dataset</h2>

<h3>Antibody <img class="cursor" src="images/delete.png" onclick="$(this).parent().next().find(':input').clear();" /></h3>
<table class="table_view">
 <tr class="table_header">
  <th>Antibody ID</th>
  <th>Lot#</th>
  <th>Target Protein</th>
  <th>Source</th>
  <th>Dilution Western</th>
  <th>Incubation Protokoll</th>
  <th>Stock</th>
 </tr>
 <tr class="table_header alt">
  <td><input type="text" name="Antibody_ID" size="9" id="Antibody_ID" /></td>
  <td><input type="text" name="Lot" size="5" id="Lot" /></td>
  <td><input type="text" name="fs_Target_Protein" size="9" id="fs_Target_Protein" /></td>
  <td><input type="text" name="Source" size="5" id="Source" /></td>
  <td><input type="text" name="Dilution_Western" size="13" id="Dilution_Western" /></td>
  <td><input type="text" name="Incubation_Protokoll" size="18" id="Incubation_Protokoll" /></td>
  <td><input type="text" name="Stock" size="5" id="Stock" /></td>
 </tr>
</table>

<h3>Comments <img class="cursor" src="images/delete.png" onclick="$(this).parent().next().find(':input').clear();" /></h3>
<table class="table_view">
 <tr class="table_header alt">
  <th>Evaluation Western</th>
  <th>Evaluation Capture Array</th>
  <th>Evaluation RPPA</th>
 </tr>
 <tr class="table_header alt">
  <td><select size="1" name="Evaluation_Western" id="Evaluation_Western"><?php foreach($rating as $r) { echo "<option value='" . $r["Number"] . "'>" . $r["Text"] . "</option>"; } ?></select></td>
  <td><select size="1" name="Evaluation_Capture_Array" id="Evaluation_Capture_Array"><?php foreach($rating as $r) { echo "<option value='" . $r["Number"] . "'>" . $r["Text"] . "</option>"; } ?></select></td>
  <td><select size="1" name="Evaluation_RPPA" id="Evaluation_RPPA"><?php foreach($rating as $r) { echo "<option value='" . $r["Number"] . "'>" . $r["Text"] . "</option>"; } ?></select></td>
 </tr>
 <tr class="table_header">
  <td><textarea cols="35" name="western_comment" id="western_comment"></textarea></td>
  <td><textarea cols="35" name="capture_array_comment" id="capture_array_comment"></textarea></td>
  <td><textarea cols="35" name="rppa_comment" id="rppa_comment"></textarea></td>
 </tr>
</table>

<h3>New Fields <img class="cursor" src="images/delete.png" onclick="$(this).parent().next().find(':input').clear();" /></h3>
<table class="table_view">
 <tr class="table_header">
  <th>IP</th>
  <th>IF</th>
  <th>IHC</th>
  <th>FACS</th>
 </tr>
 <tr class="table_header alt">
  <td><select size="1" name="IP" id="IP"><?php foreach($rating as $r) { echo "<option value='" . $r["Number"] . "'>" . $r["Text"] . "</option>"; } ?></td>
  <td><select size="1" name="IF" id="[IF]"><?php foreach($rating as $r) { echo "<option value='" . $r["Number"] . "'>" . $r["Text"] . "</option>"; } ?></td>
  <td><select size="1" name="IHC" id="IHC"><?php foreach($rating as $r) { echo "<option value='" . $r["Number"] . "'>" . $r["Text"] . "</option>"; } ?></td>
  <td><select size="1" name="FACS" id="FACS"><?php foreach($rating as $r) { echo "<option value='" . $r["Number"] . "'>" . $r["Text"] . "</option>"; } ?></td>
 </tr>
 <tr class="table_header">
  <td><textarea cols="30" name="ip_comment" id="ip_comment"></textarea></td>
  <td><textarea cols="30" name="if_comment" id="if_comment"></textarea></td>
  <td><textarea cols="30" name="ihc_comment" id="ihc_comment"></textarea></td>
  <td><textarea cols="30" name="facs_comment" id="facs_comment"></textarea></td>
 </tr>
</table>

<h3>Target Protein <img class="cursor" src="images/delete.png" onclick="$(this).parent().next().find(':input').clear();" /></h3>
<table class="table_view">
 <tr class="table_header">
  <th>Target Protein</th>
  <th>MW [kD]</th>
  <th>GeneID</th>
  <th>Gene name</th>
  <th>Synonym</th>
  <th>References</th>
 </tr>
 <tr class="table_header alt">
  <td><input type="text" size="15" name="Target_Protein_ID" id="Target_Protein_ID" /></td>
  <td><input type="text" size="15" name="MW_kD" id="MW_kD" /></td>
  <td><input type="text" size="22" name="SwissProt_Accsession" id="SwissProt_Accsession" /></td>
  <td><input type="text" size="15" name="Supplier" id="Supplier" /></td>
  <td><input type="text" size="15" name="Target_Protein_Stock" id="Target_Protein_Stock" /></td>
  <td><input type="text" size="15" name="Target_Protein_References" id="Target_Protein_References" /></td>
 </tr>
</table>

<h3>Incubationprotocol <img class="cursor" src="images/delete.png" onclick="$(this).parent().next().find(':input').clear();" /></h3>
<table class="table_view">
 <tr class="table_header">
  <th>Incubationprotocol</th>
  <th>Blocking</th>
  <th>1&deg;AB Incubation</th>
  <th>Washing I</th>
  <th>2&deg;AB Incubation</th>
  <th>Washing II</th>
  <th>Bufferset</th>
 </tr>
 <tr class="table_header alt">
  <td><input type="text" size="20" name="Incubationprotocol_id" id="Incubationprotocol_id" /></td>
  <td><input type="text" size="10" name="Blocking" id="Blocking" /></td>
  <td><input type="text" size="15" name="AB_Incubation_1" id="AB_Incubation_1" /></td>
  <td><input type="text" size="10" name="Washing_1" id="Washing_1" /></td>
  <td><input type="text" size="15" name="AB_Incubation_2" id="AB_Incubation_2" /></td>
  <td><input type="text" size="10" name="Washing_2" id="Washing_2" /></td>
  <td><input type="text" size="10" name="fs_Bufferset" id="fs_Bufferset" /></td>
 </tr>
</table>

<h3>Bufferset <img class="cursor" src="images/delete.png" onclick="$(this).parent().next().find(':input').clear();" /></h3>
<table class="table_view">
 <tr class="table_header">
  <th>Bufferset</th>
  <th>Washbuffer</th>
  <th>1&deg;AB Incubation</th>
  <th>2&deg;AB Incubation</th>
  <th>Blocking</th>
 </tr>
 <tr class="table_header alt">
  <td><input type="text" size="15" name="Bufferset" id="Bufferset" /></td>
  <td><input type="text" size="15" name="Washbuffer" id="Washbuffer" /></td>
  <td><input type="text" size="15" name="Incubation_1_AB" id="Incubation_1_AB" /></td>
  <td><input type="text" size="15" name="Incubation_2_AB" id="Incubation_2_AB" /></td>
  <td><input type="text" size="15" name="Bufferset_Blocking" id="Bufferset_Blocking" /></td>
 </tr>
</table>

<h3>Images <img class="cursor" onclick="addImage();" src="images/add.png" /> <img class="cursor" onclick="removeImage();" src="images/delete.png" /></h3>

<div id="Images">
    <ul>
        <li><a href="#image-fragment-1"><span>One</span></a></li>
    </ul>
    <div id="image-fragment-1">

  <table class="table_view" style="float:right;">
   <tr class="table_header">
    <th>Lane</th>
    <th>Lysate/Protein</th>
    <th>Total Protein</th>
   </tr>
    <?php
        // Erspart enorme schreib arbeit
        // generiert 15 Reihen mit 3 Spalten
        for($i=1;$i<=15;$i++) {
            ?>
        <tr class='table_header <?=($i % 2 == 1) ? "alt" : ""?>'>
        <?php
                echo "<th>" . $i . "</th>";
            echo "<td><input type='text' size='13' name='Lysate_Protein_" . $i . "[]' /></td>";
            echo "<td><input type='text' size='8' name='Total_Protein_" . $i . "[]' /> &micro;g</td>";
        ?>
            </tr>
        <?php
        }
    ?>
   <tr>
    <td colspan="3"><img class="cursor" src="images/delete.png" onclick="$(this).parents('table').find(':input').clear();" /></td>
   </tr>
  </table>

  <table class="table_view">
   <tr class="table_header alt">
    <th>Image:</th>
    <td><input type="file" name="Westernimage[]" /></td>
    <td rowspan="6" style="background-color:#FFFFFF;"><img class="cursor" src="images/delete.png" onclick="$(this).parents('table').find(':input').clear();" /></td>
   </tr>
   <tr class="table_header">
    <th>Signal:</th>
    <td><select size="1" name="Signal[]"><option value="Luminescence">Luminescence</option><option value="Flurescence">Flurescence</option></select></td>
   </tr>
   <tr class="table_header alt">
    <th>Sensitivity:</th>
    <td><input type="text" name="Sensitivity[]" />
   </tr>
   <tr class="table_header">
    <th>Linear Manual:</th>
    <td><input type="text" name="Linear_Manual[]" />
   </tr>
   <tr class="table_header alt">
    <th>Brightness:</th>
    <td><input type="text" name="Brightness[]" />
   </tr>
   <tr class="table_header">
    <th>Contrast:</th>
    <td><input type="text" name="Contrast[]" />
   </tr>
  </table>

  <table class="table_view" style="margin-top:15px;">
   <tr class="table_header">
    <th>SDS-Page</th>
    <th>% Acrylamid</th>
    <th>Sep.time [h]</th>
    <th>Voltage</th>
    <th>Size [mm]</th>
    <td rowspan="2"><img class="cursor" src="images/delete.png" onclick="$(this).parents('table').find(':input').clear();" /></td>
   </tr>
   <tr class="table_header alt">
       <td><input type="text" size="10" name="SDS[]" /></td>
       <td><input type="text" size="10" name="Acrylamid[]" /></td>
       <td><input type="text" size="10" name="Sep[]" /></td>
       <td><input type="text" size="10" name="Voltage[]" /></td>
       <td><input type="text" size="10" name="SDS_Size[]" /></td>
   </tr>
  </table>

  <br style="clear:both;" />


    </div>
</div>

<h3>Other <img class="cursor" src="images/delete.png" onclick="$(this).parent().next().find(':input').clear();" /></h3>
<table class="table_view">
 <tr class="table_header alt">
  <th>Datasheet:</th>
  <td><input type="file" name="Data_Sheet" id="Data_Sheet" /></td>
 </tr>
 <tr class="table_header">
  <th>Embedding:</th>
  <td><input type="text" name="Embedding" id="Embedding" /></td>
 </tr>
 <tr class="table_header alt">
  <th>Fixation:</th>
  <td><input type="text" name="Fixation" id="Fixation" /></td>
 </tr>
 <tr class="table_header">
  <th>Validated by:</th>
  <td><input type="text" name="Validated" id="Validated" /></td>
 </tr>
</table>

<br />

<input type="submit" value="Save" />
<input type="button" value="Cancel" onclick="load('Antibody1.php');" />

</form>
<br />
<br />
<script type="text/javascript">
    // Das erste image-fragment clonen samt events
    var clone = $("#image-fragment-1").clone(true);
    var fragment = "image-fragment-";
    var counter = 1;

    // generiert einen neuen Tab
    function addImage() {
        counter++;
        // der Clone wir geklont | bekommt eine neue id | wird unsichbar gemacht | und nach dem Element mit der id Images angefügt
        $(clone).clone(true).attr("id", fragment + counter).css("display", "none").appendTo("#Images");
        // Dann dem jQuery-Tab-Plugin sagen das ein neuer Tab erzeugt wurde und dem Tab einen Namen oder eine Zahl geben
        $("#Images > ul").tabs("add", "#" + fragment + counter, (numbers[counter]) ? numbers[counter] : counter);
    }
    // zerstört den letzten Tab aber nicht den ersten
    function removeImage() {
        if(counter>1) {
            $("#Images > ul").tabs("remove", counter-1);
            counter--;
        }
    }
    // generiert die Tabs
    $("#Images > ul").tabs();

    // Zwei kleine jQuery helfer Funktionnen
    $.fn.extend({
        // hält beliebig viele Felder synchron (haben also immer den selben Inhalt)
        sync:function() {

            var all = this;
            return this.keyup(function() {
                $(all).val( $(this).val() );
            });

        },
        // Löscht den Inhalt aus den Feldern und fired die keyup() funktion wegen sync()
        clear:function() {

            return this.each(function() {
                this.value = "";
                $(this).keyup();
                this.selectedIndex = 0;
            });

        }
    });
    // Helper Funktion für Autocomplete
    function autocompleteFields(d, ids) {
        // Alle ids bekommen ihr value von dem Autocomplete
        $.each( ids, function(i, val) {
            $("#" + this).val( $.trim(d[i]) );
        });
    }

    // Setz die Verschiedenen autocompletes und syncs
    $("#fs_Target_Protein, #Target_Protein_ID").sync().autocomplete("ajax/autocompleteTargetprotein.php").result(function(e, d, f) {
        autocompleteFields(d, [ "fs_Target_Protein", "Target_Protein_ID", "MW_kD", "SwissProt_Accsession", "Supplier", "Target_Protein_Stock", "Target_Protein_References" ]);
    });

    $("#Incubationprotocol_id, #Incubation_Protokoll").sync().autocomplete("ajax/autocompleteIncubationprotocol.php").result(function(e, d, f) {
        autocompleteFields(d, [ "Incubation_Protokoll", "Incubationprotocol_id", "Blocking", "AB_Incubation_1", "Washing_1", "AB_Incubation_2", "Washing_2", "fs_Bufferset", "Bufferset", "Washbuffer", "Incubation_1_AB", "Incubation_2_AB", "Bufferset_Blocking" ]);
    });

    $("#Bufferset, #fs_Bufferset").sync().autocomplete("ajax/autocompleteBufferset.php").result(function(e, d, f) {
        autocompleteFields(d, [ "Bufferset", "fs_Bufferset", "Washbuffer", "Incubation_1_AB", "Incubation_2_AB", "Bufferset_Blocking" ]);
    });

    $("#newDataset").ajaxForm({
        // siehe include/settings.php
        data:{ "<?=$antibody->postName[0]?>":"<?=$antibody->postName[1]?>" },
        success:function() {
            load("Antibody1.php");
        }
    });


    login.check();
</script>

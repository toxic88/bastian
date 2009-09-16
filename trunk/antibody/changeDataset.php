<?php
include "include/check.php";

// Ratings aus der Datenbank holen (somit kann man sie leicht verändern)
$rating = $antibody->mssql->fetch("SELECT * FROM T_Assessment", "assoc", true);

// Antibody aus der Datenbank holen
$anti = $antibody->mssql->fetch("SELECT * FROM T_Antibodydb WHERE Antibody_ID = '" . mysql_real_escape_string($_GET["ID"]) . "'", "assoc", false);
// Bufferset aus der Datenbank holen
$buffer = $antibody->mssql->fetch("SELECT * FROM T_Bufferset WHERE Gid = '" . mysql_real_escape_string($anti["Antibody_ID"]) . "'", "assoc", false);
// Targetprotein aus der Datenbank holen
$target = $antibody->mssql->fetch("SELECT * FROM T_Targetprotein WHERE Target_Protein_ID = '" . mysql_real_escape_string($anti["fs_Target_Protein"]) . "'", "assoc", false);
// Incubationprotokoll aus der Datenbank holen
$inc = $antibody->mssql->fetch("SELECT * FROM T_Incubationprotokoll WHERE Gid = '" . mysql_real_escape_string($anti["Antibody_ID"]) . "'", "assoc", false);
// Images aus der Datenbank holen
$western = $antibody->mssql->fetch("SELECT * FROM T_Westernimage WHERE Gid = '" . mysql_real_escape_string($anti["Antibody_ID"]) . "'", "assoc", true);

if(is_array($western))
    foreach($western as $w) {
        $sds[] = $antibody->mssql->fetch("SELECT * FROM T_SDS WHERE Gid = '" . mysql_real_escape_string($anti["Antibody_ID"]) . "' AND Westernimage_ID = '" . mysql_real_escape_string($w["Westernimage_ID"]) . "'", "assoc", false);
        $lane[] = $antibody->mssql->fetch("SELECT * FROM T_Lane WHERE Gid = '" . mysql_real_escape_string($anti["Antibody_ID"]) . "' AND Westernimage_ID = '" . mysql_real_escape_string($w["Westernimage_ID"]) . "'", "assoc", false);
        $scan[] = $antibody->mssql->fetch("SELECT * FROM T_Scannersettings WHERE Gid = '" . mysql_real_escape_string($anti["Antibody_ID"]) . "' AND Westernimage_ID = '" . mysql_real_escape_string($w["Westernimage_ID"]) . "'", "assoc", false);
    }
else
    $western = $sds = $lane = $scan = array( null );

?>
<form action="update.php" method="post" enctype="multipart/form-data" id="newDataset">
<input type="hidden" name="oldAntibody_ID" value="<?=$anti["Antibody_ID"]?>" />
<h2>Change Dataset</h2>

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
  <th>Supplier</th>
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
<?php
for($i=1;$i<=count($western);$i++)
    echo "<li><a href=\"#image-fragment-" . $i . "\"><span>" . $numbers[$i] . "</span></a></li>";
?>
    </ul>
<?php
foreach($western as $id => $val) {
?>
    <div id="image-fragment-<?=$id+1?>">

  <table class="table_view" style="float:right;">
   <tr class="table_header">
    <th>Lane</th>
    <th>Lysate/Protein</th>
    <th>Total Protein</th>
   </tr>
    <?php
        for($i=1;$i<=15;$i++) {
            ?>
        <tr class='table_header <?=($i % 2 == 1) ? "alt" : ""?>'>
        <?php
                echo "<th>" . $i . "</th>";
            echo "<td><input id='Lysate_Protein_" . $i . "_" . $id . "' type='text' size='13' name='Lysate_Protein_" . $i . "[]' /></td>";
            echo "<td><input id='Total_Protein_" . $i . "_" . $id . "' type='text' size='8' name='Total_Protein_" . $i . "[]' /> &micro;g</td>";
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
   <tr>
     <td colspan="3"><img id="Imagepath_<?=$id?>" src="" height="450" width="355" /></td>
   </tr>
   <tr class="table_header alt">
    <th>Image:</th>
    <td><input type="file" name="Westernimage[]" /></td>
    <td rowspan="6" style="background-color:#FFFFFF;"><img class="cursor" src="images/delete.png" onclick="$(this).parents('table').find(':input').clear();" /></td>
   </tr>
   <tr class="table_header">
    <th>Signal:</th>
    <td><select id="Signal_<?=$id?>" size="1" name="Signal[]"><option value="Luminescence">Luminescence</option><option value="Flurescence">Flurescence</option></select></td>
   </tr>
   <tr class="table_header alt">
    <th>Sensitivity:</th>
    <td><input type="text" id="Sensitivity_<?=$id?>" name="Sensitivity[]" />
   </tr>
   <tr class="table_header">
    <th>Linear Manual:</th>
    <td><input type="text" id="Linear_Manual_<?=$id?>" name="Linear_Manual[]" />
   </tr>
   <tr class="table_header alt">
    <th>Brightness:</th>
    <td><input type="text" id="Brightness_<?=$id?>" name="Brightness[]" />
   </tr>
   <tr class="table_header">
    <th>Contrast:</th>
    <td><input type="text" id="Contrast_<?=$id?>" name="Contrast[]" />
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
       <td><input type="text" size="10" id="SDS_<?=$id?>" name="SDS[]" /></td>
       <td><input type="text" size="10" id="Acrylamid_<?=$id?>" name="Acrylamid[]" /></td>
       <td><input type="text" size="10" id="Sep_<?=$id?>" name="Sep[]" /></td>
       <td><input type="text" size="10" id="Voltage_<?=$id?>" name="Voltage[]" /></td>
       <td><input type="text" size="10" id="SDS_Size_<?=$id?>" name="SDS_Size[]" /></td>
   </tr>
  </table>

  <br style="clear:both;" />


    </div>
<?php
}
?>
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
    var counter = <?=count($western)?>;

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
            $("#" + this).val( d[i] );
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

    // Helper Funktion
    // Da ich das image-fragment-1 clone kann ich die Daten nicht mit PHP in die Felder schreiben
    // also habe ich mir eine Javascript-Funktion geschrieben
    function insertObj(obj, index) {
        if(!obj || obj == null || typeof obj != "object") return;
        else {
            $.each(obj, function(i, v) {
                if(typeof v == "object") {
                    // Hier wird es Rekrusiv!
                    // Übergeben wird i als index das ist bei den Images wichtig! siehe ids von Images
                    insertObj(v, i);
                } else if(typeof v == "string" || typeof v == "number") {
                    // Das hier ist der wichtigste Teil:
                    // Setzt den Wert von einem Feld mit der entsprechenden id
                    // und fired die keyup() Funktion wegen sync
                    if($.trim(String(v)) != "") {
                        var $ele = $("#" + i + ((index!=undefined) ? "_" + index : "")), ele = $ele[0];
                        console.log(i, index, v);
                        if(String(ele) == "[object HTMLImageElement]") {
                            $ele.attr("src", v);
                        } else {
                            $ele.val(v).keyup();
                        }
                    }
                }
            });
        }
    }
    // setz den Wert für jedes Feld mit der entsprechenden ID
    // z.B.:
    /*
    {
        "Bufferset":"Hallo",
        "Antibody_ID":"1237998",
        "0":{
            "Total_Protein1":"abc"
        }
    }
    => $("#Bufferset").val("Hallo").keyup();
    => $("#Antibody_ID").val("1237998").keyup();
    => $("#Total_Protein1_0").val("abc").keyup();
    */
    insertObj( <?=json_encode( $anti )?> );
    insertObj( <?=json_encode( $buffer )?> );
    insertObj( <?=json_encode( $target )?> );
    insertObj( <?=json_encode( $inc )?> );
    insertObj( <?=json_encode( $western )?> );
    insertObj( <?=json_encode( $sds )?> );
    insertObj( <?=json_encode( $lane )?> );
    insertObj( <?=json_encode( $scan )?> );

    $("#newDataset").ajaxForm({
        // siehe include/settings.php
        data:{ "<?=$antibody->postName[0]?>":"<?=$antibody->postName[1]?>" },
        success:function() {
            load("Antibody1.php");
        }
    });

    // Checkt die Links: siehe javascript/main.php
    login.check();
</script>

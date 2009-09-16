<?php
include "include/settings.php";

### Functions

// Validate strings and numerics
function validate($arr) {
    if(is_scalar($arr)) {
        if(is_numeric($arr)) {
            $arr = str_replace(".", ",", $arr);
        }
        return mysql_real_escape_string(trim(stripslashes($arr)));
    } else if(is_array($arr)) {
        foreach($arr as $id => $val) {
            if(is_array($val)) {
                $arr[$id] = validate($val);
            } else if(is_scalar($val)) {
                if(is_numeric($val)) {
                    $val = str_replace(".", ",", $val);
                }
                $arr[$id] = mysql_real_escape_string(trim(stripslashes($val)));
            }
        }
        return $arr;
    }
    return $arr;
}

// Make the $_FILES array a bit cleaner
// see: http://de.php.net/manual/de/features.file-upload.multiple.php#53240
function reArrayFiles(&$file_post) {

    $file_ary = array();
    $file_count = count($file_post['name']);
    $file_keys = array_keys($file_post);

    for ($i=0; $i<$file_count; $i++) {
        foreach ($file_keys as $key) {
            $file_ary[$i][$key] = $file_post[$key][$i];
        }
    }

    return $file_ary;
}

### Set some variables

$r = validate($_POST);
$OAID = $r["oldAntibody_ID"];
$AID  = $r["Antibody_ID"];

if($OAID == "") {
    echo "ERROR";
    exit;
}
if($AID == "") {
    $AID = $OAID;
}
// Check if the new AntibodyID is uniq
if($AID != $OAID) {
    $checkAntibodyID = $antibody->mssql->fetch("SELECT id FROM T_Antibodydb WHERE Antibody_ID = '" . $AID . "'");
    if(is_array($checkAntibodyID)) {
        echo "The AntibodyID $AID is allready used! The old AntibodyID $OAID was used for the update!";
        $AID = $OAID;
    }
}

### IMAGES, SCANNERSETTINGS, LANES

if(isset($_FILES["Westernimage"])) {
    $images = reArrayFiles($_FILES["Westernimage"]);

    foreach($images as $id => $image) {
        // this is an array if the image allready exists or not
        $newImage = $antibody->mssql->fetch("SELECT id FROM T_Westernimage WHERE Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", false);

        $scannersettings["Gid"]             = $AID;
        $scannersettings["Westernimage_ID"] = $id;
        $scannersettings["Sensitivity"]     = $r["Sensitivity"][$id];
        $scannersettings["Linear_Manual"]   = $r["Linear_Manual"][$id];
        $scannersettings["Brightness"]      = $r["Brightness"][$id];
        $scannersettings["Contrast"]        = $r["Contrast"][$id];

        $lane["Gid"]             = $AID;
        $lane["Westernimage_ID"] = $id;

        for($i = 1; $i <= 15; $i++) {
            $lane["Lysate_Protein_" . $i] = $r["Lysate_Protein_" . $i][$id];
            $lane["Total_Protein_" . $i]  = $r["Total_Protein_" . $i][$id];
        }

        $sds["Gid"]             = $AID;
        $sds["Westernimage_ID"] = $id;
        $sds["SDS"]             = $r["SDS"][$id];
        $sds["Acrylamid"]       = $r["Acrylamid"][$id];
        $sds["Sep"]             = $r["Sep"][$id];
        $sds["Voltage"]         = $r["Voltage"][$id];
        $sds["SDS_Size"]        = $r["SDS_Size"][$id];

        $westernimage["Gid"] = $AID; // This is declared here because all Gids should be updated!
        // this if is there because maybe someone only want to update the scannersettings and don't upload a new file
        if($image['tmp_name'] != "" && is_uploaded_file($image['tmp_name'])) {
            // delete the old image
            if(is_array($newImage)) {
                $oldImageName = array_pop(explode("/", $newImage["Imagepath"]));
                if(is_file($antibody->uploadDir . "picture/" . $oldImageName))
                    @unlink($antibody->uploadDir . "picture/" . $oldImageName);
            }
            // get the extension of the image (e.g. jpg, jpeg, png...)
            $ext = array_pop(explode(".", $image['name']));
            // generate the filename
            $imagename = $AID . "_" . $id . "." . $ext;
            // change the path of the uploaded file
            move_uploaded_file($image['tmp_name'], $antibody->uploadDir . "picture/" . $imagename);
            // set the rights (usefull if we later want to unlink()/delete them)
            // nobody is the apache server
            chown($antibody->uploadDir . "picture/" . $imagename, "nobody");
            chgrp($antibody->uploadDir . "picture/" . $imagename, "nogroup");
            chmod($antibody->uploadDir . "picture/" . $imagename, 0777);

            $westernimage["Westernimage_ID"] = $id;
            $westernimage["Imagepath"]       = "http://stoneage.inet.dkfz-heidelberg.de/php/antibody/uploads/picture/" . $imagename;

            if(is_array($newImage)) {
                $antibody->mssql->update($westernimage, "Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", "T_Westernimage");
                $antibody->mssql->update($scannersettings, "Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", "T_Scannersettings");
                $antibody->mssql->update($lane, "Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", "T_Lane");
                $antibody->mssql->update($sds, "Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", "T_SDS");
            } else {
                $antibody->mssql->insert($westernimage, "T_Westernimage");
                $antibody->mssql->insert($scannersettings, "T_Scannersettings");
                $antibody->mssql->insert($lane, "T_Lane");
                $antibody->mssql->insert($sds, "T_SDS");
            }
        } else if(is_array($newImage)) {
            $antibody->mssql->update($westernimage, "Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", "T_Westernimage");
            $antibody->mssql->update($scannersettings, "Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", "T_Scannersettings");
            $antibody->mssql->update($lane, "Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", "T_Lane");
            $antibody->mssql->update($sds, "Westernimage_ID = '" . $id . "' AND Gid = '" . $OAID . "'", "T_SDS");
        }
        $lastID = $id;
    }
}

// make something mit $lastID to delete the images wich come after it or so...

### ANTIBODYDB, DATA_SHEET

$antibodydb["Antibody_ID"]              = $AID;
$antibodydb["Lot"]                      = $r["Lot"];
$antibodydb["fs_Target_Protein"]        = $r["fs_Target_Protein"];
$antibodydb["Source"]                   = $r["Source"];
$antibodydb["Dilution_Western"]         = $r["Dilution_Western"];
$antibodydb["Incubation_Protokoll"]     = $r["Incubation_Protokoll"];
$antibodydb["Stock"]                    = $r["Stock"];
$antibodydb["Evaluation_Western"]       = $r["Evaluation_Western"];
$antibodydb["Evaluation_Capture_Array"] = $r["Evaluation_Capture_Array"];
$antibodydb["Evaluation_RPPA"]          = $r["Evaluation_RPPA"];
$antibodydb["western_comment"]          = $r["western_comment"];
$antibodydb["capture_array_comment"]    = $r["capture_array_comment"];
$antibodydb["rppa_comment"]             = $r["rppa_comment"];

$data_sheet = null;
if($_FILES['Data_Sheet']['tmp_name'] != "" && is_uploaded_file($_FILES['Data_Sheet']['tmp_name'])) {
    // delete the old one if it exist
    $oldDatasheet = $antibody->mssql->fetch("SELECT * FROM T_Antibodydb WHERE Antibody_ID = '" . $OAID . "'", false);
    $oldDatasheetName = array_pop(explode("/", $oldDatasheet["Data_Sheet_Path"]));
    if(is_file($antibody->uploadDir . "pdf/" . $oldDatasheetName))
        @unlink($antibody->uploadDir . "pdf/" . $oldDatasheetName);

    $ext = array_pop(explode(".", $_FILES['Data_Sheet']['name']));

    move_uploaded_file($_FILES['Data_Sheet']['tmp_name'], $antibody->uploadDir . "pdf/" . $AID . "." . $ext);

    chown($antibody->uploadDir . "pdf/" . $AID . "." . $ext, "nobody");
    chgrp($antibody->uploadDir . "pdf/" . $AID . "." . $ext, "nogroup");
    chmod($antibody->uploadDir . "pdf/" . $AID . "." . $ext, 0777);

    $data_sheet = "http://stoneage.inet.dkfz-heidelberg.de/php/antibody/uploads/pdf/" . $AID . "." . $ext;
}
if($data_sheet != null) {
    $antibodydb["Data_Sheet_Path"] = $data_sheet;
}
$antibodydb["IP"]                  = $r["IP"];
$antibodydb["[IF]"]                = $r["IF"]; // Ausnahme!!! See HTML-Code of newDataset.php PHP don't like names surrounded by []
$antibodydb["IHC"]                 = $r["IHC"];
$antibodydb["FACS"]                = $r["FACS"];
$antibodydb["ip_comment"]          = $r["ip_comment"];
$antibodydb["if_comment"]          = $r["if_comment"];
$antibodydb["ihc_comment"]         = $r["ihc_comment"];
$antibodydb["facs_comment"]        = $r["facs_comment"];
$antibodydb["Embedding"]           = $r["Embedding"];
$antibodydb["Fixation"]            = $r["Fixation"];
$antibodydb["Validated"]           = $r["Validated"];

$antibody->mssql->update($antibodydb, "Antibody_ID = '" . $OAID . "'", "T_Antibodydb");

### INCUBATIONPROTOKOLL

$incubation["Gid"]                   = $AID;
$incubation["Incubationprotocol_id"] = $r["Incubationprotocol_id"];
$incubation["Blocking"]              = $r["Blocking"];
$incubation["AB_Incubation_1"]       = $r["AB_Incubation_1"];
$incubation["Washing_1"]             = $r["Washing_1"];
$incubation["AB_Incubation_2"]       = $r["AB_Incubation_2"];
$incubation["Washing_2"]             = $r["Washing_2"];
$incubation["fs_Bufferset"]          = $r["fs_Bufferset"];

$antibody->mssql->update($incubation, "Gid = '" . $OAID . "'", "T_Incubationprotokoll");

### BUFFERSET

$bufferset["Gid"]                = $AID;
$bufferset["Bufferset"]          = $r["Bufferset"];
$bufferset["Incubation_1_AB"]    = $r["Incubation_1_AB"];
$bufferset["Incubation_2_AB"]    = $r["Incubation_2_AB"];
$bufferset["Bufferset_Blocking"] = $r["Bufferset_Blocking"];

$antibody->mssql->update($bufferset, "Gid = '" . $OAID . "'", "T_Bufferset");

### TARGETPROTEIN

$tp = $antibody->mssql->fetch("SELECT id FROM T_Targetprotein WHERE Target_Protein_ID = '" . $r["Target_Protein_ID"] . "'");

$targetprotein["Target_Protein_ID"]         = $r["Target_Protein_ID"];
$targetprotein["MW_kD"]                     = $r["MW_kD"];
$targetprotein["SwissProt_Accsession"]      = $r["SwissProt_Accsession"];
$targetprotein["Supplier"]                  = $r["Supplier"];
$targetprotein["Target_Protein_Stock"]      = $r["Target_Protein_Stock"];
$targetprotein["Target_Protein_References"] = $r["Target_Protein_References"];

if(is_array($tp)) {
    $antibody->mssql->update($targetprotein, "Target_Protein_ID = '" . $r["Target_Protein_ID"] . "'", "T_Targetprotein");
} else {
    $antibody->mssql->insert($targetprotein, "T_Targetprotein");
}

echo "Success";

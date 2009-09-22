<form action="" method="POST" enctype="multipart/form-data">
    <input type="file" name="files[]" /><br />
    <input type="file" name="files[]" /><br />
    <input type="submit" name="submit" value="Upload" />
</form>
<?php
if (isset($_POST['submit'])) {
    require_once '../include.php';

    $upload = new Zend_File_Transfer_Adapter_Http(); // Zend_File_Transfer not finished yet

    echo '<pre>';
    print_r($upload->getFileInfo());

    echo "\n\$_FILES:\n";
    print_r($_FILES);
    echo "\n\$_POST:\n";
    print_r($_POST);
    echo '</pre>';
}
?>
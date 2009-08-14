<?php
/**
 * Css Files
 */
$cssDir = './css/src/';
$cssFileName = './css/Application.css';
$cssFiles = array(
    $cssDir . 'Ext.ux.FormUploadField.css',
    $cssDir . 'Application.css',
    $cssDir . 'Icons.css'
);
@unlink($cssFileName);
foreach ($cssFiles as $file) {
    file_put_contents($cssFileName, file_get_contents($file), FILE_APPEND);
}



/**
 * Javascript Files
 */
// Application.base.js
$jsBaseDir = './js/src/';
$jsBaseFileName = './js/Application.base.js';
$jsBaseFiles = array(
    $jsBaseDir . 'Ext.ux.FileUploadField.js',
    $jsBaseDir . 'Ext.ux.SearchField.js',
    $jsBaseDir . 'Ext.ux.SelectBox.js',
    $jsBaseDir . 'Ext.ux.grid.BufferView.js',

    $jsBaseDir . 'Application.util.js',

    $jsBaseDir . 'Application.js',
    $jsBaseDir . 'Application.Navigation.js',
    $jsBaseDir . 'Application.Panel.js',
    $jsBaseDir . 'Application.Viewport.js'
);
@unlink($jsBaseFileName);
foreach ($jsBaseFiles as $file) {
    file_put_contents($jsBaseFileName, file_get_contents($file), FILE_APPEND);
}


// Application.guest.js
$jsGuestDir = $jsBaseDir . 'guest/';
$jsGuestFileName = './js/Application.guest.js';
$jsGuestFiles = array(
    $jsGuestDir . 'Application.guest.Login.js',

    $jsGuestDir . 'navigation.js'
);
@unlink($jsGuestFileName);
foreach ($jsGuestFiles as $file) {
    file_put_contents($jsGuestFileName, file_get_contents($file), FILE_APPEND);
}
$jsGuestFiles = array();


// Application.member.js
$jsMemberDir = $jsBaseDir . 'member/';
$jsMemberFileName = './js/Application.member.js';
$jsMemberFiles = array_merge($jsGuestFiles, array(
    $jsMemberDir . 'Application.member.Stundenplan.js',
    $jsMemberDir . 'Application.member.Vokabeln.js',

    $jsMemberDir . 'navigation.js'
));
@unlink($jsMemberFileName);
foreach ($jsMemberFiles as $file) {
    file_put_contents($jsMemberFileName, file_get_contents($file), FILE_APPEND);
}
array_pop($jsMemberFiles);


// Application.admin.js
$jsAdminDir = $jsBaseDir . 'admin/';
$jsAdminFileName = './js/Application.admin.js';
$jsAdminFiles = array_merge($jsMemberFiles, array(
    $jsAdminDir . 'Application.admin.User.js',

    $jsAdminDir . 'navigation.js'
));
@unlink($jsAdminFileName);
foreach ($jsAdminFiles as $file) {
    file_put_contents($jsAdminFileName, file_get_contents($file), FILE_APPEND);
}

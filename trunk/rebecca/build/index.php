<?php
define(DEBUG, true);
require_once 'jsmin.php';

/**
 * Css Files
 */
$cssDir = '../public/css/src/';
$cssFileName = '../public/css/Application.css';
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
$jsBaseDir = '../public/js/src/';
$jsBaseFileName = '../public/js/Application.base.js';
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
if (!DEBUG) {
    file_put_contents($jsBaseFileName, JSmin::minify(file_get_contents($jsBaseFileName)));
}


// Application.guest.js
$jsGuestDir = $jsBaseDir . 'guest/';
$jsGuestFileName = '../public/js/Application.guest.js';
$jsGuestFiles = array(
    $jsGuestDir . 'Application.guest.Login.js',

    $jsGuestDir . 'navigation.js'
);
@unlink($jsGuestFileName);
foreach ($jsGuestFiles as $file) {
    file_put_contents($jsGuestFileName, file_get_contents($file), FILE_APPEND);
}
if (!DEBUG) {
    file_put_contents($jsGuestFileName, JSmin::minify(file_get_contents($jsGuestFileName)));
}
$jsGuestFiles = array();


// Application.member.js
$jsMemberDir = $jsBaseDir . 'member/';
$jsMemberFileName = '../public/js/Application.member.js';
$jsMemberFiles = array_merge($jsGuestFiles, array(
    $jsMemberDir . 'Application.member.Stundenplan.js',
    $jsMemberDir . 'Application.member.Vokabeln.js',

    $jsMemberDir . 'navigation.js'
));
@unlink($jsMemberFileName);
foreach ($jsMemberFiles as $file) {
    file_put_contents($jsMemberFileName, file_get_contents($file), FILE_APPEND);
}
if (!DEBUG) {
    file_put_contents($jsMemberFileName, JSmin::minify(file_get_contents($jsMemberFileName)));
}
array_pop($jsMemberFiles);


// Application.admin.js
$jsAdminDir = $jsBaseDir . 'admin/';
$jsAdminFileName = '../public/js/Application.admin.js';
$jsAdminFiles = array_merge($jsMemberFiles, array(
    $jsAdminDir . 'Application.admin.User.js',

    $jsAdminDir . 'navigation.js'
));
@unlink($jsAdminFileName);
foreach ($jsAdminFiles as $file) {
    file_put_contents($jsAdminFileName, file_get_contents($file), FILE_APPEND);
}
if (!DEBUG) {
    file_put_contents($jsAdminFileName, JSmin::minify(file_get_contents($jsAdminFileName)));
}

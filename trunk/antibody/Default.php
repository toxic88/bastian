<?php
include "include/settings.php";
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>Antibody Database</title>

	<meta http-equiv="content-type" content="text/html;charset=utf-8">

	<link rel="stylesheet" href="styles/dkfz.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="styles/jquery.ui.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="styles/jquery.autocomplete.css" type="text/css" media="screen" />
	<!-- Javascript werden erst am ende included wegen prefomance! -->
</head>
<body>
	<div id="dkfz">

		<div id="dkfz_top_left" class="dkfz top left"></div>
		<div id="dkfz_top_navi" class="dkfz top navi"></div>
		<div id="dkfz_top_right" class="dkfz top right"></div>
		<div id="dkfz_top_content" class="dkfz top content"></div>

		<div class="clear"></div>

		<div id="dkfz_header_left" class="dkfz header left"></div>
		<div id="dkfz_header_navi" class="dkfz header navi">
			<ul>
				<li><a href="http://intranet/">Intranet</a></li>
			</ul>
		</div>
		<div id="dkfz_header_right" class="dkfz header right"></div>
		<div id="dkfz_header_content" class="dkfz header content">
			<a href="http://www.dkfz.de/" title="DKFZ Homepage">
				<img style="float:right;" src="http://www.dkfz.de/global/images/dkfz_logo.gif" />
			</a>
			<h1 style="font-size:180%;margin-left:10px;"><a href="Default.php">Antibody Database</a></h1>
		</div>

		<div class="clear"></div>

		<div id="dkfz_middle_left" class="dkfz middle left"></div>
		<div id="dkfz_middle_navi" class="dkfz middle navi">
			<div style="margin:3px 10px;">
			</div>
		</div>
		<div id="dkfz_middle_right" class="dkfz middle right"></div>
		<div id="dkfz_middle_content" class="dkfz middle content">
			<div style="text-align:center;margin-top:2px;">
				<b id="loading"></b>
			</div>
		</div>

		<div class="clear"></div>

		<div id="dkfz_bottom_left" class="dkfz bottom left"></div>
		<div id="dkfz_bottom_navi" class="dkfz bottom navi">

			<a href="Default.php #content > div">Home</a>

			<h4 class="login">Datenbank</h4>
			<a class="login" href="Antibody1.php">View Antibodies 1</a>
			<a class="login" href="Antibody2.php">View Antibodies 2</a>
			<a class="login" href="newDataset.php">New Dataset</a>

			<h4>Login</h4>
			<a class="logout" href="Login.php">Login</a>
			<a class="login" href="User.php">Your settings</a>
			<a class="login" href="Login.php?action=Logout">Logout</a>

		</div>
		<div id="dkfz_bottom_right" class="dkfz bottom right"></div>
		<div id="dkfz_bottom_content" class="dkfz bottom content">
			<div id="content" style="margin:20px;">

<div>
<!-- Default content -->
<h2>Willkommen</h2>

<p>
Ich habe die komplette Seite neu gemacht! Falls Fehler auftreten oder etwas nicht geht bitte mir eine E-Mail schreiben!
</p>



Danke <a href="mailto:b.buchholz@dkfz.de">Bastian Buchholz</a>

<h3>History</h3>
<ul>
  <li>01.10.2008: Tabs-Style im Internet Explorer verbessert.</li>
  <li>30.09.2008: Suche eingebaut und ein paar Fehler beim &Auml;ndern von Datens&auml;tzen behoben.</li>
</ul>
</div>

			</div>
		</div>
	</div>
	<!-- Tooltip Javascript   http://www.macridesweb.com/oltest/   -->
	<script src="javascript/overlib.js" type="text/javascript"></script>

	<!-- jQuery-Framework   http://jquery.com/   -->
	<script src="javascript/jquery.js" type="text/javascript"></script>
	<!-- jQuery-Cookie-Plugin   http://stilbuero.de/jquery/cookie/   -->
	<script src="javascript/jquery.cookie.js" type="text/javascript"></script>
	<!-- jQuery-Form-Plugin   http://malsup.com/jquery/form/   -->
	<script src="javascript/jquery.form.js" type="text/javascript"></script>
	<!-- jQuery-Autocomplete-Plugin   http://jquery.bassistance.de/autocomplete/demo/   -->
	<script src="javascript/jquery.autocomplete.js" type="text/javascript"></script>
	<!-- jQuery-UI   http://ui.jquery.com/   -->
	<script src="javascript/jquery.ui.js" type="text/javascript"></script>
	<!-- jQuery-Tablesorter-Plugin   http://tablesorter.com/docs/   -->
	<script src="javascript/jquery.tablesorter.js"></script>

	<!-- Mein Javascript -->
	<script src="javascript/main.php" type="text/javascript"></script>

	<script>
	if($.browser.msie)
		$("#content h2").after("It is better to choose Firefox as webbrowser for this page, because there are some bugs in IE which I didn't figure out at the moment...<br />");

	</script>

</body>
</html>

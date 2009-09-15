<?php
/*
	Diese Datei wird von den AJAX Seiten included.

	Es wird überprüft ob:
		- Man eingeloggt ist
		- Ob man auch wirklich die Links auf der Default.php Seite benutzt denn,
			bei jedem Link wird ein AJAX-Request gestartet und spezielle POST-Parameter
			übergeben, damit kein anderer einfach den Link in die Addressleiste eingeben
			kann, falls das trozdem einer machen sollte wird im Moment die Default.php Seite
			geladen was dann ein bisschen unschön aussieht...
 */

require_once "settings.php";

if($_POST[$antibody->postName[0]] == $antibody->postName[1] && isset($_COOKIE["Login"])) {
	// Alles Okay
} else {

	header("LOCATION: " . $antibody->mainPage);
	exit;
}

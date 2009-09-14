<?php
include "include/check.php";

// Checks if the user is an admin (Rights = 1)
if(!is_array($antibody->mssql->fetch("SELECT * FROM T_User WHERE Rights = 1 AND Password = '" . $_SESSION[$antibody->sessionName] . "'")))
	header("LOCATION: " . $antibody->mainPage);

// Falls das Formular für einen neuen Benutzer abgeschickt wurde...
if(isset($_POST["Username"]) && isset($_POST["Password"]) && isset($_POST["Rights"]) && trim($_POST["Username"]) != "" && trim($_POST["Password"]) != "") {
	// Stipslashes muss man immer machen siehe: http://de.php.net/manual/de/security.magicquotes.php
	$data["Username"] = trim(stripslashes($_POST["Username"]));
	// md5() hash fürs Passwort
	$data["Password"] = md5(trim(stripslashes($_POST["Password"])));
	$data["Rights"] = trim(stripslashes($_POST["Rights"]));
	// Schauen ob der Benutzername schon existiert
	if(!is_array($antibody->mssql->fetch("SELECT * FROM T_User WHERE Username = '" . $data["Username"] . "'"))) {
		$antibody->mssql->insert($data, "T_User");
		echo "New User: " . $data["Username"];
	} else
		echo $data["Username"] . " allready exists!";
	// ABBRECHEN!!!
	exit;
// Falls das Formular für ein neues Passwort abgeschickt wurde
} else if(isset($_POST["id"]) && isset($_POST["Password"])) {
	$data["Password"] = md5(stripslashes($_POST["Password"]));
	// Einfach das Passwort änder (man ist ja der Admin :))
	// Ich weiß nicht wirklich ob mysql_real_escape_sting hier richtig ist, gehen tut es auf jeden fall,
	// ob man trozdem die MSSQL-Datenbank injecten kann weiß ich aber nicht...
	$antibody->mssql->update($data, "id = '" . mysql_real_escape_string($_POST["id"]) . "'", "T_User");
	echo "Password changed!";
	// ABBRECHEN!!!
	exit;
}

$users = $antibody->mssql->fetch("SELECT * FROM T_User ORDER BY Username");

?>
<h2>ADMIN</h2>
<div style="text-align:center;"><b id="error"></b></div>
<div id="adminPanel">
    <ul>

        <li><a href="#users"><span>Users</span></a></li>
        <li><a href="#newUser"><span>New User</span></a></li>
        <li><a href="#pwChange"><span>Change password for user</span></a></li>
    </ul>
    <div id="users">

	<table class="table_view" style="margin:0px auto;">
		<tr>
			<th>Username</th>
			<th>Rigths</th>
		</tr>
	<?php
	// Listet alle Benutzer mit ihren rechten auf
	// Wenn man auf den Benutzernamen klickt kann man das Passwort für diesen ändern
	// TODO: Rechte ändern
	foreach($users as $user) {
		echo "<tr>";
		echo "<td><a onclick='$(\"#adminPanel > ul\").tabs(\"select\", 2);$(\"#pwChangeForm select\").val(" . $user["id"] . ");' href='javascript:;'>" . $user["Username"] . "</a></td>";
		echo "<td>" . $user["Rights"] . "</td>";
		echo "</tr>";
	}
	?>
	</table>

    </div>
    <div id="newUser">

	<form id="newUserForm" action="Admin.php" method="post" style="text-align:center;">
		<table class="table_view" style="margin:0px auto;">
			<tr>
				<th>Username</th>
				<th>Password</th>
				<th>Rights</th>
			</tr>
			<tr>
				<td><input type="text" name="Username" /></td>
				<td><input type="text" name="Password" /></td>
				<td><select size="1" name="Rights">
					<option value="2">User</option>
					<option value="1">Admin</option>
				</select></td>
			</tr>
		</table>
		<input type="submit" value="Create new user" />
	</form>

    </div>
    <div id="pwChange">

	<form id="pwChangeForm" action="Admin.php" method="post" style="text-align:center;">
		<table class="table_view" style="margin:0px auto;">
			<tr>
				<th>User</th>
				<th>Password</th>
			</tr>
			<tr>
				<td><select size="1" name="id">
				<?php
				foreach($users as $user) {
					echo "<option value=" . $user["id"] . ">" . $user["Username"] . "</option>";
				}
				?>
				</select></td>
				<td><input type="text" name="Password" /></td>
			</tr>
		</table>
			<input type="submit" value="Change password" />
	</form>

    </div>
</div>
<script type="text/javascript">
// Generiert die Tabs
$("#adminPanel > ul").tabs();

// Macht das Formular zu einem AJAX-Formular, dass heißt das die Seite nicht neu geladen werden muss
$("#newUserForm").ajaxForm({
	// siehe include/settings.php
	data:{ "<?=$antibody->postName[0]?>":"<?=$antibody->postName[1]?>" },
	// löscht alle felder nach dem submit
	resetForm:true,
	success:function(r) {
		$("#error").html(r);
		// Selectiert den ersten tab (bringt eigentlich nicht viel weil man eh nochmal auf "Admin" klicken muss
		// um die Änderung zu sehen)
		$("#adminPanel > ul").tabs("select", 0);
	}
});

// siehe oben
$("#pwChangeForm").ajaxForm({
	data:{ "<?=$antibody->postName[0]?>":"<?=$antibody->postName[1]?>" },
	resetForm:true,
	success:function(r) {
		$("#error").html(r);
	}
});

</script>

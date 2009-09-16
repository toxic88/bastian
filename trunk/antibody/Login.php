<?php
include "include/settings.php";

// Admin links
// leider ist mir noch kein besserer weg eingefallen...
$admin = "<script>$(\"<a id='admin' href='Admin.php'>Admin Page</a>\").click(function() { load(this.href); return false; }).appendTo(\"#dkfz_bottom_navi\");</script>";
$radmin = "<script>$(\"#admin\").remove();</script>";

// Wenn man auf den Logout-Link klickt kommt man hier her
if($_GET["action"] == "Logout") {
    // PHP Session zerstören
    session_destroy();
    // Login-Cookie löschen
    setcookie("Login", "");
    // Admin link zerstören
    echo $radmin;
// Falls man ein Login-Cookie gesetzt hat
} else if(isset($_COOKIE[$antibody->cookieName])) {
    // Das Cookie habe ich mit base64_encode() codiert, siehe unten
    // jetzt muss ich es decodieren
    $cookie = base64_decode($_COOKIE[$antibody->cookieName]);
    // Ich habe in die der Datenbank ein Feld namens Cookie,
    // dort speicher ich den Cookie-Wert ab, also sollte ich da was finden :)
    $user = $antibody->mssql->fetch("SELECT * FROM T_User WHERE Cookie = '" . $cookie . "'");
    // Trozdem lieber noch mal prüfen, man kann ja Cookies selber setzten
    // Das einzige was ich nicht abfange ist wenn einer das Cookie von einem anderen stielt
    if($antibody->mssql->numrows == 1) {
        // PHP Session setzten
        $_SESSION[$antibody->sessionName] = $user[0]["Password"];
        // Login-Cookie setzten (wird für Javascript Benötigt)
        setcookie("Login", true);
        // Leider wird das Cookie erst bei einem neuladen der Seite in die Globale Variable $_COOKIE,
        // geschrieben, da ich weiter unten aber was include muss ich das dieses mal selber machen...
        $_COOKIE["Login"] = true;
        // Admin-Link setzten (aber natürlich nur wenn man der Admin ist :))
        if($user[0]["Rights"] == 1) echo $admin;
        // Standartmäßig wird die "Antibody1.php" included und nicht die "Default.php" damit man sofort
        // loslegen kann
        include "Antibody1.php";
        // ABBRECHEN!!!
        exit;
    }
// Wenn man das Formular abgeschickt hat
} else if(isset($_POST["Username"]) && isset($_POST["Password"])) {
    // Versuchen den Benutzer in der Datenbank ausfindig zu machen
    // da es in PHP keinen mssql_real_escape_string() gibt benutzt ich einfach den von mysql
    // sollte *hoffentlich* auch gehen (bisher habe ich noch nicht bemerkt das es nicht geht),
    // aber ich habe auch noch nie versucht sowas zu injecten
    $user = $antibody->mssql->fetch("SELECT * FROM T_User WHERE Username = '" . mysql_real_escape_string($_POST["Username"]) . "' AND Password = '" . md5($_POST["Password"]) . "'");
    // Wurde der Benutzer gefunden?
    if($antibody->mssql->numrows == 1) {
        // Falls der Benutzer ein Cookie haben will soll er es bekommen :)
        // "true" deshalb weil alle Formular eingaben Strings sind
        if($_POST["Remeber"] == "true") {
            // Cookie setzten mit der id des Benutzers und dem jetzigen timestamp,
            // (damit es möglichst uniq bleibt). Ich speicher das Cookie ein Jahr lang bei dem Benutzer
            setcookie($antibody->cookieName, base64_encode( $user[0]["id"] . $antibody->time ), time()+3600*24*365);
            // Damit der Cookie-Wert auch in die Datenbank kommt...
            $tmp["Cookie"] = $user[0]["id"] . $antibody->time;
            $antibody->mssql->update($tmp, "id = " . $user[0]["id"], "T_User");
        }
        // Setzt die PHP Session (da kann ich das Passwort benutzten da keiner ausser PHP da dran kommt)
        $_SESSION[$antibody->sessionName] = md5($_POST["Password"]);
        // Siehe oben beim einloggen mit dem Cookie
        setcookie("Login", true);
        $_COOKIE["Login"] = true;
        if($user[0]["Rights"] == 1) echo $admin;
        include "Antibody1.php";
        exit;
    } else {
        // Sollte klar sein
        $error = "<span class='error'>Wrong username or password!</span>";
    }
}
?>
<h2>Login</h2>
<form id="loginForm" method="post" action="Login.php">
<?php
    echo $error;
?>
    <table>
        <tr>
            <td>Username:</td>
            <td><input type="text" name="Username" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="Password" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Login" /></td>
            <td><input type="checkbox" name="Remeber" value="true" /> Remember me!</td>
        </tr>
    </table>
</form>
<script type="text/javascript">

<?php
// Wenn man sich ausloggen will werden somit alle links versteckt
if($_GET["action"] == "Logout")
    echo "login.check();";
?>
    // Macht das Formular zu einem AJAX-Formular, damit es keinen reload beim submitten gibt
    $("#loginForm").ajaxForm({
        // Siehe include/settings.php
        data:{ "<?=$antibody->postName[0]?>":"<?=$antibody->postName[1]?>" },
        success:function(r) {
            $("#content").html(r);
            login.check();
        }
    });
</script>

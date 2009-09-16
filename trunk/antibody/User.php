<?php
include "include/check.php";

// Die Benutzerdaten aus der Datenbank holen
$user = $antibody->mssql->fetch("SELECT * FROM T_User WHERE Password = '" . $_SESSION[$antibody->sessionName] . "'", false);

// Man merkt ich habe immer was neues gemacht, diesesmal switch() :)
switch($_POST["action"]) {
    // Togglet das Cookie (mir is der deutsche Begriff nicht eingefallen...)
    case "cookie":
        // Falls das Cookie existiert -> lösch es
        if(isset($_COOKIE[$antibody->cookieName])) {
            // Cookie löschen
            setcookie($antibody->cookieName, "", time()-3600*24*365);
            // und auch aus der Datenbank
            $tmp["Cookie"] = "";
            $antibody->mssql->update($tmp, "id = " . $user["id"], "T_User");
            // noch ausgeben was gemacht wurde
            echo "Remember me!";
        // Falls das Cookie nicht existiert -> setz es
        } else {
            // Cookie setzen (1 Jahr)
            setcookie($antibody->cookieName, base64_encode( $user["id"] . $antibody->time ), time()+3600*24*365);
            // und auch in die Datenbank speichern
            $tmp["Cookie"] = $user["id"] . $antibody->time;
            $antibody->mssql->update($tmp, "id = " . $user["id"], "T_User");
            // noch ausgeben was gemacht wurde
            echo "Don't remember me!";
        }
        break;
    case "changePassword":
        // Schauen ob die Passwörter überhaupt übereinstimmen
        // * könnte ich auch Clientseitig machen aber man sollte dem Benutzer nie trauen *
        if($_POST["newPassword"] == $_POST["repeatPassword"]) {
            // Auf die übereinstimmung mit dem alten Password prüfen
            if(md5($_POST["oldPassword"]) ==  $user["Password"]) {
                // Alles Okay also das neue Passwort in die Session und in die
                // Datenbank speichern
                $tmp["Password"] = $_SESSION[$antibody->sessionName] = md5($_POST["newPassword"]);
                $antibody->mssql->update($tmp, "id = '" . $checkPW["id"] . "'", "T_User");
                // noch ausgeben was gemacht wurde
                echo "Password changed!";
            } else {
                // noch ausgeben was falsch war
                echo "The old Password is wrong!";
            }
        } else {
            // noch ausgeben was falsch war
            echo "The new Passwords don't match!";
        }
        break;
    default:
?>
<h2>User: <?=$user["Username"]?></h2>
<table style="margin:0px auto;text-align:center;">
    <tr>
        <th style="border:1px solid #D8D8D8;background-color:#BFD1ED;">Change Password</th>
        <th style="border:1px solid #D8D8D8;background-color:#BFD1ED;">Set/Delete Cookie</th>
    </tr>
    <tr>
        <td style="border:1px solid #D8D8D8;">
            <form id="changePasswordForm" method="POST" action="User.php">
                <b id="passwordError"></b>
                <!-- wird für das PHP script benötigt -->
                <input type="hidden" name="action" value="changePassword" />
                <table>
                    <tr>
                        <th>Old Password:</th><td><input type="password" name="oldPassword" /></td>
                    </tr>
                    <tr>
                        <th>New Password:</th><td><input type="password" name="newPassword" /></td>
                    </tr>
                    <tr>
                        <th>Repeat Password:</th><td><input type="password" name="repeatPassword" /></td>
                    </tr>
                </table>
                <input type="submit" value="Change Password!" />
            </form>
        </td>
        <td style="border:1px solid #D8D8D8;">
            <form id="cookieForm" method="POST" action="User.php">
                <!-- wird für das PHP script benötigt -->
                <input type="hidden" name="action" value="cookie" />
                <input type="submit" id="cookie" name="cookie" value="<?=(isset($_COOKIE[$antibody->cookieName])) ? "Don't remember me!" : "Remember me!"?>" />
            </form>
        </td>
    </tr>
</table>
<script type="text/javascript">

$("#changePasswordForm").ajaxForm({
    // siehe include/settings.php
    data:{ "<?=$antibody->postName[0]?>":"<?=$antibody->postName[1]?>" },
    success:function(r) {
        $("#passwordError").html(r);
        if(r!="Password changed!") // irgendwie gefällt mir sowas nicht so
            $("#passwordError").addClass("error"); // == Farbe rot
        else
            $("#passwordError").addClass("okay"); // == Farbe grün
    }
});
$("#cookieForm").ajaxForm({
    // siehe include/settings.php
    data:{ "<?=$antibody->postName[0]?>":"<?=$antibody->postName[1]?>" },
    success:function(r) {
        $("#cookie").val(r);
    }
});

// sieht schöner aus (Firefox macht um manche Elemente ein outline drumrum wenn man sie fokusiert. Zum Beispiel: <a> <button> ...)
$(":submit").click(function() {
    $(this).blur();
});

// Links überprüfen siehe javascript/main.php
login.check();
</script>
<?php
    break;
}
?>

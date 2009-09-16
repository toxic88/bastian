<?php
/*
    Diese Datei wird von der settings.php required!

    Es wird die Variable $mssql zum MSSQL Objekt gemacht:
        - $antibody->mssql->query; -> die letzte mssql_query();
        - $antibody->mssql->result; -> Ergebnis der letzten $mssql->fetch();
        - $antibody->mssql->numrows;-> Anzahl der zurückgegebenen Reihen der letzten $mssql->fetch();
        - $antibody->mssql->fetch(); -> Eine Methode die eine SELECT Anfrage ausführt und das ergebnis Standardmäßig als Assoziatives Array zurückgibt.
        - $antibody->mssql->insert(); -> Eine Methode die aufgrund eines Assoziativen Arrays und dem Datenbankname eine INSERT INTO Anfrage ausführt.
        - $antibody->mssql->update(); -> Eine Methode die aufgrund eines Assoziativen Arrays und dem Datenbankname sowie einer WHERE Klausel eine UPDATE Anfrage ausführt.
        - Mehr Infos in der mssql.class.php
 */
require_once "mssql.class.php";
$antibody->mssql = new MSSQL("newage", "phpdeveloper", "midrange", "phpwebdb");

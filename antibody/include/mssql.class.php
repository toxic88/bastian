<?php
/*
    Diese Datei wird von dbconnection.php required!

    Mit dieser Klasse kann man einfach eine Verbindung zu einer MSSQL-Datenbank herstellen
    und verschiedene aktionen durchführen wie z.B.
        - $antibody->mssql->query; -> die letzte mssql_query();
        - $antibody->mssql->result; -> Ergebnis der letzten $mssql->fetch();
        - $antibody->mssql->numrows;-> Anzahl der zurückgegebenen Reihen der letzten $mssql->fetch();
        - $antibody->mssql->fetch(); -> Eine Methode die eine SELECT Anfrage ausführt und das ergebnis Standardmäßig als Assoziatives Array zurückgibt.
        - $antibody->mssql->insert(); -> Eine Methode die aufgrund eines Assoziativen Arrays und dem Datenbankname eine INSERT INTO Anfrage ausführt.
        - $antibody->mssql->update(); -> Eine Methode die aufgrund eines Assoziativen Arrays und dem Datenbankname sowie einer WHERE Klausel eine UPDATE Anfrage ausführt.
        weitere Informationen siehe unten:
*/

/**
 * Class MSSQL.
 * With this class you can easily connect to a Database-Server, select a Database, send SQL-Queries
 * and get Datasets as any Array you like or only get the first result!
 * You can also easily change the current Database or the hole Server!
 *
 * @example
 * $mssql = new MSSQL("localhost", "root", "pass", "mydb");
 * foreach($mssql->fetch("SELECT * FROM users") as $row) {
 *         echo "<li>".$row['name']."</li>";
 * }
 *
 * @example
 * $mssql = new MSSQL("localhost", "root", "pass", "mydb");
 * foreach($mssql->fetch("SELECT * FROM users", "index", true, "newdb") as $row) {
 *         echo "<li>".$row[0]."</li>";
 * }
 *
 * @example
 * $mssql = new MSSQL("localhost", "root", "", "mydb");
 * foreach($mssql->fetch("SELECT * FROM users", "object", false, "newserver", "root", "pass", "newdb") as $row) {
 *         echo "<li>".$row"</li>";
 * }
 *
 * @example
 * $mssql = new MSSQL();
 * $mssql->query("INSERT INTO users ( 'name', 'password' ) VALUES ( 'newUser', '".md5($password)."' )", "localhost", "root", "pass", "mydb");
 */
class MSSQL {

    /**
     * Saves the current connection.
     *
     * @var {Resource ID} $connection
     */
    private $connection;

    /**
     * Saves the current SQL-Query.
     *
     * @var {Resource ID} $query
     */
    public $query;

    /**
     * Saves the current result from the last fetch()-Function.
     *
     * @var {Array} $result
     */
    public $result;

    /**
     * Saves the current number of rows from the last fetch()-Function.
     *
     * @var {Integer} $numrows
     */
    public $numrows;

    /**
     * Class constuctor.
     * This function is very usefull to change the current Database or the current Server.
     * If you only want to change the Database set $host to the Databse you want.
     * If you don't want to set the $db you don't have to ;).
     * And if you want to change the hole Server you have to set $host, $user, $pass, $db.
     *
     * @var {String} $host
     * @var {String} $user
     * @var {String} $pass
     * @var {String} $db
     */
    function MSSQL($host=null, $user=null, $pass=null, $db=null) {
        if($host!=null && $user!=null && $db!=null) {
            if(is_resource($this->connection)) $this->disconnect();
            return ($this->connection = mssql_connect($host, $user, $pass)) ? (mssql_select_db($db)) ? true : false : false;
        } else if($host!=null) return (mssql_select_db($host)) ? true : false;
        else return (is_resource($this->connection)) ? true : false;
    }

    /**
     * Function to close the current connection to the Database
     */
    function disconnect() {
        if(is_resource($this->connection)) mssql_close($this->connection);
    }

    /**
     * Function to send any SQL-Query to the current Database.
     * If you don't want to use the currently Database set $host to the Database you want.
     * And if you don't want to use the Server you can change that if you set $host, $user, $pass, $db.
     *
     * @var {String} $query
     * @var {String} $host
     * @var {String} $user
     * @var {String} $pass
     * @var {String} $db
     */
    function query($query, $host=null, $user=null, $pass=null, $db=null) {
        if($this->MSSQL($host, $user, $pass, $db)) return ($this->query = mssql_query($query, $this->connection)) ? $this->query : false;
    }

    /**
     * Function to get the result of a SELECT-Query.
     * You can get the result as an Assoziative-Array, Index-Array or as an Object.
     * Additionaly you can get all results or only the first Dataset.
     * If you don't want to use the currently Database set $host to the Database you want.
     * And if you don't want to use the Server you can change that if you set $host, $user, $pass, $db.
     *
     * @var {String}  $query
     * @var {String}  $format
     * @var {Boolean} $all
     * @var {String}  $host
     * @var {String}  $user
     * @var {String}  $pass
     * @var {String}  $db
     */
    function fetch($query, $format="assoc", $all=true, $host=null, $user=null, $pass=null, $db=null) {
        $all = (is_bool($format)) ? $format : $all;
        $host = (is_string($all)) ? $all : $host;
        if($this->MSSQL($host, $user, $pass, $db) && $this->query($query)) {
            $format = strtolower($format);
            $this->result = null;
            switch($format) {
                case "object":
                    if($all)
                        while($row = mssql_fetch_object($this->query)) $this->result[] = $row;
                    else
                        $this->result = mssql_fetch_object($this->query);
                    break;
                case "index":
                    if($all)
                        while($row = mssql_fetch_row($this->query)) $this->result[] = $row;
                    else
                        $this->result = mssql_fetch_row($this->query);
                    break;
                default:
                    if($all)
                        while($row = mssql_fetch_assoc($this->query)) $this->result[] = $row;
                    else
                        $this->result = mssql_fetch_assoc($this->query);
                    break;
            }
            $this->numrows = mssql_num_rows($this->query);
            return $this->result;
        }
        return false;
    }
    /**
     * Function to INSERT a new Datarow.
     * The $keys-Parameter requires an assoziatives Array, where the keys should be the
     * datacolum and the value the data to insert.
     * The $table-Parameter is the tablename where the new row should be inserted.
     * If you don't want to use the currently Database set $host to the Database you want.
     * And if you don't want to use the Server you can change that if you set $host, $user, $pass, $db.
     *
     * @var {Assoziatives Array} $keys
     * @var {String}             $table
     * @var {String}             $host
     * @var {String}             $user
     * @var {String}             $pass
     * @var {String}             $db
     */
    function insert($keys, $table, $host=null, $user=null, $pass=null, $db=null) {
        if(is_array($keys) && $this->MSSQL($host, $user, $pass, $db) && is_string($table)) {
            foreach($keys as $id => $key) {
                $idString .= $id.", ";
                $keyString .= (is_numeric($key)) ? mysql_real_escape_string($key) : "'".mysql_real_escape_string($key)."'";
                $keyString .= ", ";
            }
            $idString = substr($idString, 0, -2);
            $keyString = substr($keyString, 0, -2);

            $insert = "INSERT INTO $table ( ".$idString." ) VALUES ( ".$keyString." )";

            return ($this->query($insert)) ? $this->result : false;
        } else
            return false;
    }
    /**
     * Function to UPDATE (a) Datarow(s).
     * The $keys-Parameter requires an assoziatives Array, where the keys should be the
     * datacolum and the value the new data.
     * The $where-Parameter is the WHERE-Statement in a SQL-Query, e.g. "id = 1",
     * notice that you don't have to write "WHERE"!!! just "id = 1"
     * The $table-Parameter is the tablename where the rows would be updated.
     * If you don't want to use the currently Database set $host to the Database you want.
     * And if you don't want to use the Server you can change that if you set $host, $user, $pass, $db.
     *
     * @var {Assoziatives Array} $keys
     * @var {String}             $where
     * @var {String}             $table
     * @var {String}             $host
     * @var {String}             $user
     * @var {String}             $pass
     * @var {String}             $db
     */
    function update($keys, $where, $table, $host=null, $user=null, $pass=null, $db=null) {
        if(is_array($keys) && $this->MSSQL($host, $user, $pass, $db) && is_string($where) && is_string($table)) {
            foreach($keys as $id => $key) {
                $updateString .= $id." = ";
                $updateString .= (is_numeric($key)) ? mysql_real_escape_string($key) : "'".mysql_real_escape_string($key)."'";
                $updateString .= ", ";
            }
            $updateString = substr($updateString, 0, -2);

            $update = "UPDATE $table SET ".$updateString." WHERE $where ";

            return ($this->query($update)) ? $this->result : false;
        } else
            return false;
    }

}

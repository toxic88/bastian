<?php
/*
 * !!!Diese Datei wird nicht mehr verwendet!!!
 * Früher habe ich alles was der Benutzer macht mitgeloggt und in eine Datei geschrieben
 * und damit ich nicht soviel schreiben musste habe ich meine Datei-Klasse verwendet
*/

/**
 * Class File.
 * With this class you can easily modiefy and read files.
 * There are different modes to read and write files. 
 * So you can e.g. write some content at the top, at the bottom or overwrite the file.
 * And you can split the content with a string or a regulare expression!
 * You can also always change the current file!
 *
 * @example
 * $file = new File("test.txt");
 * echo $file->read();
 *
 * @example
 * $file = new File("test.txt");
 * $content = $file->read("array", "\n", "newfile.txt");
 *
 * @example
 * $file = new File();
 * $file->write("This is new content!", "start", "test.txt");
 *
 * @example
 * $file = new File("test.txt");
 * $file->write("This is new content!");
 */
class File {

	/**
	 * The URL to the current file
	 *
	 * @var		{String}	$file
	 */
	public $file;

	/**
	 * The class constructor.
	 * The function sets the current file, but only if it is one.
	 *
	 * @var		{String}	$file
	 */
	function File($file=null) {
		if( is_file($file) && is_readable($file) && is_writeable($file) ) {
			$this->file = $file;
			return true;
		} else return is_file($this->file);
	}

	/**
	 * Function to read a file into a string.
	 * With the $mode you can choose how to split the content that will be returned. ("string", "array", "regex")
	 * With the $split you can choose how to split up the file content.
	 * With the $file you can choose a(n) (other) file.
	 *
	 * @var		{String}	$mode
	 * @var		{String}	$split
	 * @var		{String}	$file
	 */
	function read($mode="string", $split="\n", $file=null) {
		if($this->File($file)) {
			$mode = strtolower($mode);
			switch($mode) {
				case "array":
					return explode($split, file_get_contents($this->file));
					break;
				case "regex":
					if($split == "\n") $split = "/\n/";
					return preg_split($split, file_get_contents($this->file));
					break;
				default:
					return file_get_contents($this->file);
					break;
			}
		}
		return false;
	}

	/**
	 * Function to write a string into a file.
	 * In the $content there has to be the content as a string.
	 * With the $mode you can choose where the new content will be inserted. ("end", "start", "overwrite")
	 * With the $file you can choose a(n) (other) file.
	 *
	 * @var		{String}	$content
	 * @var		{String}	$mode
	 * @var		{String}	$file
	 */
	function write($content, $mode="end", $file=null) {
		if($this->File($file)) {
			$mode = strtolower($mode);
			switch($mode) {
				case "start":
					$old = file_get_contents($this->file);
					$File = fopen($this->file, "w");
					fputs($File, $content.$old);
					break;
				case "overwrite":
					$File = fopen($this->file, "w");
					fputs($File, $content);
					break;
				default:
					$File = fopen($this->file, "a");
					fputs($File, $content);
					break;
			}
			fclose($File);
			return true;
		}
		return false;
	}
}
?>
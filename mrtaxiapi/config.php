<?php
	$servername = "localhost";
	$username = "stimulus_app";
	$password = "Harsha@123";
	$dbname = "stimulus_app";
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
?>
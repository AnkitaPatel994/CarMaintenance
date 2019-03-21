<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"client_id"}) && isset($data_back->{"client_name"}))
	{
		if(!empty($data_back->{"client_id"}) && !empty($data_back->{"client_name"}))
		{
			$client_id=$data_back->{"client_id"};
			$client_name=$data_back->{"client_name"};
			
			$q_dp="update client set client_name = '".$client_name."' where client_id = '".$client_id."'";
			
			$stdp=$conn->prepare($q_dp);
			$stdp->execute();
			$details = array('status'=>"1",'message'=>"success");
		}
		else
			$details = array('status'=>"0",'message'=>"Parameter is Empty");
	}
	else
		$details = array('status'=>"0",'message'=>"parameter missing");
	
	echo json_encode($details);
	
	$conn->close();
?>
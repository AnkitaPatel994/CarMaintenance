<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"o_id"}) && isset($data_back->{"o_cost"}) && isset($data_back->{"o_maintenance"}) && isset($data_back->{"o_m_cost"}))
	{
		if(!empty($data_back->{"o_id"}) && !empty($data_back->{"o_cost"}) && !empty($data_back->{"o_maintenance"}) && !empty($data_back->{"o_m_cost"}))
		{
			$o_id=$data_back->{"o_id"};
			$o_cost=$data_back->{"o_cost"};
			$o_maintenance=$data_back->{"o_maintenance"};
			$o_m_cost=$data_back->{"o_m_cost"};
			
			$q_dp="update oilchange set o_cost = '".$o_cost."',o_maintenance = '".$o_maintenance."',o_m_cost = '".$o_m_cost."' where o_id = '".$o_id."'";
			
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
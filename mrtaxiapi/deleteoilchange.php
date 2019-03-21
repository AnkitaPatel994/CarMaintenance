<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"o_id"}) && isset($data_back->{"v_id"}))
	{
		if(!empty($data_back->{"o_id"}) && !empty($data_back->{"v_id"}))
		{
			$o_id=$data_back->{"o_id"};
			$v_id=$data_back->{"v_id"};
			
			$q_dp="delete from oilchange where o_id = '".$o_id."'";
			$stdp=$conn->prepare($q_dp);
			$stdp->execute();
			
			$q_dp1="delete from vehicle where v_id = '".$v_id."'";
			$stdp1=$conn->prepare($q_dp1);
			$stdp1->execute();
			
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
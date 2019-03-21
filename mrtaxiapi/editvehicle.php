<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"v_id"}) && isset($data_back->{"v_name"}) && isset($data_back->{"v_no"}) && isset($data_back->{"v_kilometer"}))
	{
		if(!empty($data_back->{"v_id"}) && !empty($data_back->{"v_name"}) && !empty($data_back->{"v_no"}) && !empty($data_back->{"v_kilometer"}))
		{
			$v_id=$data_back->{"v_id"};
			$v_name=$data_back->{"v_name"};
			$v_no=$data_back->{"v_no"};
			$v_kilometer=$data_back->{"v_kilometer"};
			
			$q_dp="update vehicle set v_name = '".$v_name."', v_no = '".$v_no."', v_kilometer = '".$v_kilometer."' where v_id = '".$v_id."'";
			
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
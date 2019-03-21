<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"d_id"}) &&isset($data_back->{"d_name"}) && isset($data_back->{"d_licenceno"}) && isset($data_back->{"d_gst"}) && isset($data_back->{"d_commission"}))
	{
		if(!empty($data_back->{"d_id"}) && !empty($data_back->{"d_name"}) && !empty($data_back->{"d_licenceno"}) && !empty($data_back->{"d_gst"}) && !empty($data_back->{"d_commission"}))
		{
			$d_id=$data_back->{"d_id"};
			$d_name=$data_back->{"d_name"};
			$d_licenceno=$data_back->{"d_licenceno"};
			$d_gst=$data_back->{"d_gst"};
			$d_commission=$data_back->{"d_commission"};
			
			$q_dp="update driver set d_name = '".$d_name."', d_licenceno = '".$d_licenceno."', d_gst = '".$d_gst."', d_commission = '".$d_commission."' where d_id = '".$d_id."'";
			
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
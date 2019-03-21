<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"d_name"}) && isset($data_back->{"d_licenceno"}) && isset($data_back->{"d_gst"}) && isset($data_back->{"d_commission"}))
	{
		if(!empty($data_back->{"d_name"}) && !empty($data_back->{"d_licenceno"}) && !empty($data_back->{"d_gst"}) && !empty($data_back->{"d_commission"}))
		{
			$d_name=$data_back->{"d_name"};
			$d_licenceno=$data_back->{"d_licenceno"};
			$d_gst=$data_back->{"d_gst"};
			$d_commission=$data_back->{"d_commission"};
			
			$query = "select d_name,d_licenceno,d_gst,d_commission from driver where d_name = '".$d_name."'";
			$stm = $conn->prepare($query);
			if ($stm)
			{
				$stm->execute();
				$stm->bind_result($d_name,$d_licenceno,$d_gst,$d_commission);
				$stm->store_result();
				$count1=$stm->num_rows;
				
				if($count1!=0)
				{
					$details = array('status'=>"1",'message'=>"Alredy insert Driver");
				}
				else
				{
					$q_dp="insert into driver(d_name,d_licenceno,d_gst,d_commission) values(?,?,?,?)";
					$stdp=$conn->prepare($q_dp);
					
					if($stdp)
					{
						$stdp->bind_param('ssss',$d_name,$d_licenceno,$d_gst,$d_commission);
						$stdp->execute();
						$v_id=$stdp->insert_id;
						$details = array('status'=>"1",'message'=>"success", 'id'=>$v_id);
					}
				}
			}
			else 
			{
				$details = array('status'=>"0",'message'=>"connection error exist");
			}
			
			
		}
		else
			$details = array('status'=>"0",'message'=>"Parameter is Empty");
	}
	else
		$details = array('status'=>"0",'message'=>"parameter missing");
	
	echo json_encode($details);
	
	$conn->close();
?>
<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"client_name"}))
	{
		if(!empty($data_back->{"client_name"}))
		{
			$client_name=$data_back->{"client_name"};
			
			$query = "select client_id,client_name from client where client_name = '".$client_name."'";
			$stm = $conn->prepare($query);
			if ($stm)
			{
				$stm->execute();
				$stm->bind_result($client_id,$client_name);
				$stm->store_result();
				$count1=$stm->num_rows;
				
				if($count1!=0)
				{
					$details = array('status'=>"1",'message'=>"Alredy insert client");
				}
				else
				{
					$q_dp="insert into client(client_name) values(?)";
					$stdp=$conn->prepare($q_dp);
					
					if($stdp)
					{
						$stdp->bind_param('s',$client_name);
						$stdp->execute();
						$client_id=$stdp->insert_id;
						$details = array('status'=>"1",'message'=>"success", 'client_id'=>$client_id);
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
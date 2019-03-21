<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"o_v_id"}) && isset($data_back->{"o_cost"}) && isset($data_back->{"o_maintenance"}) && isset($data_back->{"o_m_cost"}))
	{
		if(!empty($data_back->{"o_v_id"}) && !empty($data_back->{"o_cost"}) && !empty($data_back->{"o_maintenance"}) && !empty($data_back->{"o_m_cost"}))
		{
			$o_v_id = $data_back->{"o_v_id"};
			$o_cost = $data_back->{"o_cost"};
			$o_maintenance = $data_back->{"o_maintenance"};
			$o_m_cost = $data_back->{"o_m_cost"};
			$o_date = date("d-m-Y");
			
			$query = "select o_id,o_v_id,o_cost,o_maintenance,o_m_cost,o_date from oilchange where o_v_id = '".$o_v_id."' and o_date = '".$o_date."'";
			$stm = $conn->prepare($query);
			if ($stm)
			{
				$stm->execute();
				$stm->bind_result($o_id,$o_v_id,$o_cost,$o_maintenance,$o_m_cost,$o_date);
				$stm->store_result();
				$count1=$stm->num_rows;
				
				if($count1!=0)
				{
					$details = array('status'=>"1",'message'=>"Alredy insert oilchange");
				}
				else
				{
					$q_dp="insert into oilchange(o_v_id,o_cost,o_maintenance,o_m_cost,o_date) values(?,?,?,?,?)";
					$stdp=$conn->prepare($q_dp);
					
					if($stdp)
					{
						$stdp->bind_param('sssss',$o_v_id,$o_cost,$o_maintenance,$o_m_cost,$o_date);
						$stdp->execute();
						$o_id=$stdp->insert_id;
						$details = array('status'=>"1",'message'=>"success", 'o_id'=>$o_id);
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
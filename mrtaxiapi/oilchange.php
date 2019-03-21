<?php 
	include "config.php";
	
	$data_back = json_decode(file_get_contents('php://input'));	
	
	$details=array();
	
	$query = "select o_id,v_id,v_name,v_no,v_kilometer,o_cost,o_maintenance,o_m_cost,o_date from vehicle,oilchange where v_id = o_v_id";
	$stm = $conn->prepare($query);
												
	if ($stm)
	{
		$stm->execute();
		$stm->bind_result($o_id,$v_id,$v_name,$v_no,$v_kilometer,$o_cost,$o_maintenance,$o_m_cost,$o_date);
		$stm->store_result();
		$count1=$stm->num_rows;
		
		if($count1!=0){			
		
		while($stm->fetch())
		{
			$vehicle[]=array('o_id'=>"$o_id",'v_id'=>"$v_id",'v_name'=>"$v_name",'v_no'=>"$v_no",'v_kilometer'=>"$v_kilometer",'o_cost'=>"$o_cost",'o_maintenance'=>"$o_maintenance",'o_m_cost'=>"$o_m_cost",'o_date'=>"$o_date");
		}
		
		$details = array('status'=>"1",'message'=>"Success",'vehicle'=>$vehicle);
	
		}else{
			$details = array('status'=>"0",'message'=>"No Category Found");
		}
	}
	else 
	{
		$details = array('status'=>"0",'message'=>"connection error exist");
	}
	$stm->close();
	
		
	echo json_encode($details);
	$conn->close();
	
	
?>
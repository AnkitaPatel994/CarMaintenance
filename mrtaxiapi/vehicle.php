<?php 
	include "config.php";
	
	$data_back = json_decode(file_get_contents('php://input'));	
	
	$details=array();
	
	$query = "select v_id,v_name,v_no,v_kilometer from vehicle ORDER BY v_id DESC";
	$stm = $conn->prepare($query);
												
	if ($stm)
	{
		$stm->execute();
		$stm->bind_result($v_id,$v_name,$v_no,$v_kilometer);
		$stm->store_result();
		$count1=$stm->num_rows;
		
		if($count1!=0){			
		
		while($stm->fetch())
		{
			$vehicle[]=array('id'=>"$v_id",'v_name'=>"$v_name",'v_no'=>"$v_no",'v_kilometer'=>"$v_kilometer");
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
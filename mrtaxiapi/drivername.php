<?php 
	include "config.php";
	
	$data_back = json_decode(file_get_contents('php://input'));	
	
	$details=array();
	
	$query = "select d_id,d_name from driver";
	$stm = $conn->prepare($query);
												
	if ($stm)
	{
		$stm->execute();
		$stm->bind_result($d_id,$d_name);
		$stm->store_result();
		$count1=$stm->num_rows;
		
		if($count1!=0){			
		
		while($stm->fetch())
		{
			$vehicle[]=array('d_id'=>"$d_id",'d_name'=>"$d_name");
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
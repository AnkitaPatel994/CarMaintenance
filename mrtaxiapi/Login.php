<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"uname"}) && isset($data_back->{"pass"}))
	{
		if(!empty($data_back->{"uname"}) && !empty($data_back->{"pass"}))
		{
			$uname = $data_back->{"uname"};
			$pass = $data_back->{"pass"};
		
			$query = "select id,uname,pass from admin WHERE uname='$uname' and pass='$pass'";
			$stm = $conn->prepare($query);
														
			if ($stm)
			{
				$stm->execute();
				$stm->bind_result($id,$uname,$pass);
				$stm->store_result();
				$count1=$stm->num_rows;
				
				if($count1!=0){			
				
				while($stm->fetch())
				{
					$Admin[]=array('id'=>"$id",'uname'=>"$uname",'pass'=>"$pass");
				}
				
				$details = array('status'=>"1",'message'=>"Success",'Admin'=>$Admin);
			
				}else{
					$details = array('status'=>"0",'message'=>"Username and password Wrong");
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
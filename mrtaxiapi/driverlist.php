<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"d_name"}))
	{
		if(!empty($data_back->{"d_name"}))
		{
			$d_name=$data_back->{"d_name"};
			
			$query = "select d_id,d_name,d_licenceno,d_gst,d_commission from driver where d_name = '".$d_name."'";
			$stm = $conn->prepare($query);
			if ($stm)
			{
				$stm->execute();
				$stm->bind_result($d_id,$d_name,$d_licenceno,$d_gst,$d_commission);
				$stm->store_result();
				$count1=$stm->num_rows;
				
				if($count1!=0)
				{
					while($stm->fetch())
					{
						$vehicle[]=array('d_id'=>"$d_id",'d_name'=>"$d_name",'d_licenceno'=>"$d_licenceno",'d_gst'=>"$d_gst",'d_commission'=>"$d_commission");
					}
					$details = array('status'=>"1",'message'=>"Success",'vehicle'=>$vehicle);
				}
				else
				{
					$details = array('status'=>"0",'message'=>"No Category Found");
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
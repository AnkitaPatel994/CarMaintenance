<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"c_d_id"}) && isset($data_back->{"c_date"}))
	{
		if(!empty($data_back->{"c_d_id"}) && !empty($data_back->{"c_date"}))
		{
			$c_d_id=$data_back->{"c_d_id"};
			$c_date=$data_back->{"c_date"};
			
			$query = "select c_id,c_dshift,d_name,client_name,c_other,c_cash,c_gastype,c_gascash,c_maintenance,c_commission,c_gst,c_cashleft,c_total,c_date from driver,dailycashout,client where client_id = c_c_id and c_d_id = d_id and c_d_id = '".$c_d_id."' and c_date = '".$c_date."'";
			$stm = $conn->prepare($query);
			if ($stm)
			{
				$stm->execute();
				$stm->bind_result($c_id,$c_dshift,$d_name,$client_name,$c_other,$c_cash,$c_gastype,$c_gascash,$c_maintenance,$c_commission,$c_gst,$c_cashleft,$c_total,$c_date);
				$stm->store_result();
				$count1=$stm->num_rows;
				
				if($count1!=0)
				{
					while($stm->fetch())
					{
						$vehicle[]=array('c_id'=>"$c_id",'c_dshift'=>"$c_dshift",'d_name'=>"$d_name",'client_name'=>"$client_name",'c_other'=>"$c_other",'c_cash'=>"$c_cash",'c_gastype'=>"$c_gastype",'c_gascash'=>"$c_gascash",'c_maintenance'=>"$c_maintenance",'c_commission'=>"$c_commission",'c_gst'=>"$c_gst",'c_cashleft'=>"$c_cashleft",'c_total'=>"$c_total",'c_date'=>"$c_date");
					}
					
					$details = array('status'=>"1",'message'=>"Success",'vehicle'=>$vehicle);
				}
				else
				{
					$details = array('status'=>"0",'message'=>"No Data Found");
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
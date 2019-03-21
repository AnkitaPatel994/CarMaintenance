<?php
	include "config.php";
	
	// read JSon input
	$data_back = json_decode(file_get_contents('php://input'));
 
	$details;
	
	if(isset($data_back->{"o_v_id"}) && isset($data_back->{"startdate"}) && isset($data_back->{"enddate"}))
	{
		if(!empty($data_back->{"o_v_id"}) && !empty($data_back->{"startdate"}) && !empty($data_back->{"enddate"}))
		{
			$o_v_id=$data_back->{"o_v_id"};
			$startdate=$data_back->{"startdate"};
			$enddate=$data_back->{"enddate"};
			
			$query = "select o_v_id,v_name,v_no,o_cost,o_maintenance,o_m_cost,o_date,(o_cost+o_m_cost) as 'MTotal' from oilchange,vehicle where o_v_id = v_id and o_v_id = '".$o_v_id."' and o_date between '".$startdate."' and '".$enddate."'";
			$stm = $conn->prepare($query);
			if ($stm)
			{
				$stm->execute();
				$stm->bind_result($o_v_id,$v_name,$v_no,$o_cost,$o_maintenance,$o_m_cost,$o_date,$Total);
				$stm->store_result();
				$count1=$stm->num_rows;
				
				
				
				if($count1!=0)
				{
				    $query1 = "select sum(o_cost) as 'Oil_Total',sum(o_m_cost) as 'Maintenance_Total',sum(o_cost+o_m_cost) as 'Main_Total' from oilchange";
    			    $stm1 = $conn->prepare($query1);
    			    $stm1->execute();
    				$stm1->bind_result($Oil_Total,$Maintenance_Total,$Main_Total);
    				$stm1->store_result();
    				$stm1->fetch();
				
					while($stm->fetch())
					{
						$vehicle[]=array('o_v_id'=>"$o_v_id",'v_name'=>"$v_name",'v_no'=>"$v_no",'o_cost'=>"$o_cost",'o_maintenance'=>"$o_maintenance",'o_m_cost'=>"$o_m_cost",'o_date'=>"$o_date",'Total'=>"$Total");
					}
					
					$details = array('status'=>"1",'message'=>"Success",'Oil_Total'=>"$Oil_Total",'Maintenance_Total'=>"$Maintenance_Total",'Main_Total'=>"$Main_Total",'vehicle'=>$vehicle);
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
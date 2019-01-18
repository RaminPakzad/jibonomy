<?php
error_reporting(0);
header("Content-type:application/json");
if(isset($_POST['token'])){
	
	$token = $_POST['token'];
	$token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJINGpXcmRDYl9sUHZRbmkwd3ZSOVQ2UkpMNlF2Mmt1V2NSakh4SjFrY2NRIn0.eyJqdGkiOiIzNTBhZjg3OC04ZTdjLTRjOTMtYmUwNi1jZjk1NzhhNTY0YjYiLCJleHAiOjE1NDc3OTkwMzksIm5iZiI6MCwiaWF0IjoxNTQ3Nzk4NjE0LCJpc3MiOiJodHRwOi8vcGZtLm15b3h5Z2VuLmlyL2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6IjNjY2JhYjkyLTRiOTMtNGJmNC04MmJiLTBjY2Q1Yzg4Iiwic3ViIjoiNzhhNmMxMWMtNDg1MC00NjZjLTg0MTYtMzc1NWU4OTJmNjViIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiM2NjYmFiOTItNGI5My00YmY0LTgyYmItMGNjZDVjODgiLCJhdXRoX3RpbWUiOjE1NDc3NjMwMzksInNlc3Npb25fc3RhdGUiOiI4ZTA2ODkyOS04OTFkLTRmZTktYmY1Mi01NzliMWZmNDk1ZjgiLCJhY3IiOiIwIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVzb3VyY2VfYWNjZXNzIjp7IlNCQWNjb3VudFRyYW5zYWN0aW9uIjp7InJvbGVzIjpbInN2Yy1tZ210LWFjY291bnQtdHJ4Il19LCJTQkFjY291bnRCYWxhbmNlIjp7InJvbGVzIjpbInN2Yy1tZ210LWFjY291bnQtYmFsYW5jZSJdfSwiU0JDdXN0b21lckFjY291bnRJbmZvIjp7InJvbGVzIjpbInN2Yy1tZ210LWN1c3RvbWVyLWFjY291bnQiXX19LCJzc24iOiIwNDUwMDkwOTAwIn0.SR1WM5Pso_5p619JpuIH_Q_0ZGis136vN0LfSNYTNV6dRAeRpNqxFIeSDA6aPEqrN7476jD2MY3_7t19NhaZIj7lJPUr66jzlPxAlmC0E2EQc7Jx8jvqG_-yvdK-ly8Q0Pw0OVsutj8E_Cz4sIMIqAn3nBZ3Dy4ILBZSbsVZ4nOdY2I8x1dxOpvP8Ssdgm29B13qFa-shNf0_QpH58H-a1QA4Ah2amcufxd1WDu2eizNdRohznH2axjHRIp-IHRidS9EMVwaAyqeIOB0U7uc5qC2g8bl3WThOP05FvKARyB6J6Ku51CQps0VMbfgRgrwfD9B6H0KbYYbR0Z8LkxWQw=";
	
	$curl = curl_init();
	curl_setopt_array($curl, array(
	  CURLOPT_URL => "http://pfm.myoxygen.ir/api/account/v1/customer/individual/accounts-info",
	  CURLOPT_RETURNTRANSFER => true,
	  CURLOPT_ENCODING => "",
	  CURLOPT_MAXREDIRS => 10,
	  CURLOPT_TIMEOUT => 30,
	  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
	  CURLOPT_CUSTOMREQUEST => "POST",
	  CURLOPT_POSTFIELDS => "{\r\n  \"nationalIdentifier\":\"."$_POST['nationalCode']".\"\r\n}",
	  CURLOPT_HTTPHEADER => array(
		"Authorization: Bearer ".$token,
		"Content-Type: application/json",
		"cache-control: no-cache"
	  ),
	));

	$response = curl_exec($curl);
	$err = curl_error($curl);

	curl_close($curl);

	if ($err) {
	  echo "cURL Error #:" . $err;
	} else {
	  echo $response;
	}
} else {
	
	echo "{}";
	
}


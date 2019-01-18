<?php
error_reporting(0);
header("Content-type:application/json");
$account = $_POST['account'];
if(isset($_POST['token'])){
	
	$token = $_POST['token'];
	$token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJINGpXcmRDYl9sUHZRbmkwd3ZSOVQ2UkpMNlF2Mmt1V2NSakh4SjFrY2NRIn0.eyJqdGkiOiIyMjIwNGZlMy01ZWY0LTQ4NTEtOWNhNy1jYWI2YWFiMzgxNDkiLCJleHAiOjE1NDc3OTkwMzksIm5iZiI6MCwiaWF0IjoxNTQ3NzYzNzI0LCJpc3MiOiJodHRwOi8vcGZtLm15b3h5Z2VuLmlyL2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6IjNjY2JhYjkyLTRiOTMtNGJmNC04MmJiLTBjY2Q1Yzg4Iiwic3ViIjoiNzhhNmMxMWMtNDg1MC00NjZjLTg0MTYtMzc1NWU4OTJmNjViIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiM2NjYmFiOTItNGI5My00YmY0LTgyYmItMGNjZDVjODgiLCJhdXRoX3RpbWUiOjE1NDc3NjMwMzksInNlc3Npb25fc3RhdGUiOiI4ZTA2ODkyOS04OTFkLTRmZTktYmY1Mi01NzliMWZmNDk1ZjgiLCJhY3IiOiIwIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVzb3VyY2VfYWNjZXNzIjp7IlNCQWNjb3VudFRyYW5zYWN0aW9uIjp7InJvbGVzIjpbInN2Yy1tZ210LWFjY291bnQtdHJ4Il19LCJTQkFjY291bnRCYWxhbmNlIjp7InJvbGVzIjpbInN2Yy1tZ210LWFjY291bnQtYmFsYW5jZSJdfSwiU0JDdXN0b21lckFjY291bnRJbmZvIjp7InJvbGVzIjpbInN2Yy1tZ210LWN1c3RvbWVyLWFjY291bnQiXX19LCJzc24iOiIwNDUwMDkwOTAwIn0.U1gHkM6hjrdQxgcF8WU-vE1N_uT7wIlpwWgAG2QcyuAAPjQudCuIjvdLzpikNpg9mUQJoFCbtANjHGOiugw5sZBiAez1JG5_NK5SiznORwipHdtbVWS7U5voFQcR14vRfTpHVkGmF78wvYaBkY3VSyOEojbw9Epym9EV2IY7WzGKRYv8Y4kEeD8IdpqTdLT237lrb7pRdJBNkx5G-YNnB1P4kmIsnY_ba-7arsI3zKN1TICp4P22G1dVT63PZiXlWkYTD3K5ckYlHlCGYoZwQnzR8y_0oNTPxuPC_pzHB_nWZF1ZrLYdARLN1C9UEPH8Umf-ObwtZKJ7DkMwhCHXMg=";
	
	$curl = curl_init();
	curl_setopt_array($curl, array(
  CURLOPT_URL => "http://pfm.myoxygen.ir/api/statement/v1/account/transactions",
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 30,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "POST",
  CURLOPT_POSTFIELDS => "{\r\n \"accountNumber\": \"".$account."\",\r\n \"dateRange\": {\r\n   \"fromDateTime\": \"2018-01-01T20:30:00.000Z\",\r\n   \"toDateTime\": \"2019-01-30T20:30:00.000Z\"\r\n },\r\n  \"pageable\": {\r\n    \"page\": 1,\r\n    \"size\": 10\r\n   }\r\n}\r\n",
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


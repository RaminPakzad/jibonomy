<?php
$redirect_uri = "http://192.168.25.135/jibonomy/";
if(isset($_GET['code'])){

	$curl = curl_init();
	curl_setopt_array($curl, array(
	  CURLOPT_URL => "http://pfm.myoxygen.ir/auth/realms/master/protocol/openid-connect/token",
	  CURLOPT_RETURNTRANSFER => true,
	  CURLOPT_ENCODING => "",
	  CURLOPT_MAXREDIRS => 10,
	  CURLOPT_TIMEOUT => 30,
	  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
	  CURLOPT_CUSTOMREQUEST => "POST",
	  CURLOPT_POSTFIELDS => "grant_type=authorization_code&code=".$_GET['code']."&redirect_uri=http%3A%2F%2F192.168.25.135%2Fjibonomy%2F&client_id=3ccbab92-4b93-4bf4-82bb-0ccd5c88&undefined=",
	  CURLOPT_HTTPHEADER => array(
		"Authorization: Basic M2NjYmFiOTItNGI5My00YmY0LTgyYmItMGNjZDVjODg6YjVmYWNmODUtOTQyOC00NzAyLWJmODktNjRjM2I3YTVlYmFk",
		"Content-Type: application/x-www-form-urlencoded",
		"Host: pfm.myoxygen.ir",
		"cache-control: no-cache"
	  ),
	));

	$response = curl_exec($curl);
	$err = curl_error($curl);

	curl_close($curl);
		
		$button = "";
		$err = "";
		$script = "";
		if ($err) {
			$err = "خطا<br/>";
		} else {
			$responseArray = json_decode($response);
			$button = "<br/>درحال بازگشت به جیبونومی<br/></br><a href=\"appsec://jibonomy.ir?".$responseArray->access_token."\" ><div style='width:100px; height:25px; border:1px solid #FFF; text-align:center; margin:0 auto; padding:15px; background-color:#FFF; color:#039BE5'>ورود به جیبونومی</div></a>";
			$script = "window.location ='appsec://jibonomy.ir?".$responseArray->access_token."';";
		}

}

?>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<style>
			body { 
				font-family:tahoma;
			}
			a{
				text-decoration: none;
				color: white;
			}
		</style>
	</head>
	
	<script>
		<?php echo $script; ?>
	</script>
	<body style="background-color:#039BE5"  >
		<div style="text-align:center">
			<?php
			echo $err;
			echo $button;
			echo "<br/><a href=\"http://pfm.myoxygen.ir/auth/realms/master/protocol/openid-connect/auth?response_type=code&state=&client_id=3ccbab92-4b93-4bf4-82bb-0ccd5c88&client_secret=b5facf85-9428-4702-bf89-64c3b7a5ebad&scope=&redirect_uri=".$redirect_uri."\">تلاش مجدد</a>";
			?>	
		</div>
	</body>
</html>





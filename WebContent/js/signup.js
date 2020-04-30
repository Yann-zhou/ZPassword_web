$(document).ready(function(){
	var json;
	$('#sign-in').click(function(){
		window.location.href="login.jsp";
	});
	
	$('#inputEmail').blur(function(){
		var pattern= /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		var strEmail=pattern.test($('#inputEmail').val());
		if(strEmail){
			$.post("ajax.jsp",
					{
					    signal:"9",
					    message:'["'+$('#inputEmail').val()+'"]'
					},
					function(data,status){
						json=JSON.parse(data);
						if(json.message=='SendMailSuccess'){
							$('#signup-info').html("验证码发送成功！");
						}else if(json.message=='UserExists'){
							$('#signup-info').html("该用户已存在！");
						}
					}
			);
		}else{
			$('#signup-info').html("邮箱格式不正确");
		}
	});
	
	$('#sign-up').click(function(){
		if($('#inputPassword').val()!=$('#inputPasswordRe').val()){
			$('#signup-info').html("密码输入不一致！");
		}else{
			$.post("ajax.jsp",
					{
					    signal:"2",
					    message:'["'+$('#inputEmail').val()+'", "'+CryptoJS.SHA256($('#inputPassword').val()).toString()+'"]'
					},
					function(data,status){
						json=JSON.parse(data);
						if(json.message=='InsertSuccess'){
							$('#signup-info').html("注册成功，正在跳转...");
							setTimeout(function(){window.location.href="login.jsp";}, 1500);
						}
					}
			);
		}
	});
})
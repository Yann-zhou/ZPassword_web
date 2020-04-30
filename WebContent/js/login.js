$(document).ready(function(){

$('#sign-in').click(function(){
	$.post("ajax.jsp",
		{
		    signal:"1",
		    message:'["'+$('#inputUsername').val()+'","'+CryptoJS.SHA256($('#inputPassword').val()).toString()+'"]'
		},
		function(data,status){
		    //alert("数据:" + data + "\n状态: " + status);
			var json=JSON.parse(data);
			console.log(json.message);
			if(json.message=="LoginSuccess"){
				$('#login-info').html("登陆成功！正在跳转...");
				setTimeout("window.location.href='index.jsp'",1000);
				
			}else{
				$('#login-info').html("登陆失败，用户名或密码错误！");
			}
	});
});

$('#sign-up').click(function(){
	window.location.href="signup.jsp";
});

});

function sendPost(value, temp_form, func){
    temp_form.action = "index.jsp";
    //如需打开新窗口，form的target属性要设置为'_blank'
    temp_form.method = "post";
    temp_form.style.display = "none";
    //添加参数
    var opt = document.createElement("textarea");
    opt.id = "id";
    opt.value = value;
    temp_form.appendChild(opt);
    alert(opt.value);
    document.body.appendChild(temp_form);
    func(temp_form);
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ZPassword注册</title>
  <script src="js/jquery-3.4.1.min.js"></script>
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/login.css">
  <script src="js/bootstrap.js"></script>
  <script src="js/crypto-js.js"></script>
  <script src="js/signup.js"></script>
  <style>
  	.center-box{height: 400px;}
  	.control-label{width: 90px;}
  	.center-form{height: 230px;}
  	#signup-info{position: absolute;bottom: 15px;left: 175px;}
  </style>
</head>
<body>
<div class="center center-box">
  <div class="page-header">
    <h1 style="text-align:center">ZPassword</h1>
  </div>
  <div class="center-form">
	<form class="form-horizontal">
	  <div class="form-group">
		<label class="col-sm-2 control-label">邮箱</label>
		<div class="col-sm-6">
		  <input type="text" class="form-control" id="inputEmail" placeholder="用户名">
		</div>
	  </div>
	  <div class="form-group">
		<label class="col-sm-2 control-label">密码</label>
		<div class="col-sm-6">
		  <input type="password" class="form-control" id="inputPassword" placeholder="密码">
		</div>
	  </div>
	  <div class="form-group">
		<label class="col-sm-2 control-label">重复密码</label>
		<div class="col-sm-6">
		  <input type="password" class="form-control" id="inputPasswordRe" placeholder="重复密码">
		</div>
	  </div>
	  <div class="form-group">
		<label class="col-sm-2 control-label">验证码</label>
		<div class="col-sm-6">
		  <input type="text" class="form-control" id="inputPasswordRe" placeholder="验证码">
		</div>
	  </div>
	  <div class="form-group">
		<div class="col-sm-offset-2 col-sm-2">
		  <button type="button" class="btn btn-info" id="sign-up">注册</button>
		</div>
		<div class="col-sm-offset-1 col-sm-2">
		  <button type="button" class="btn btn-default" id="sign-in">返回</button>
		</div>
	  </div>
	</form>
  <div id="signup-info" style="text-align:center; color:red;"></div>
  </div>
</div>
</body>
</html>
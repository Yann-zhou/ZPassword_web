<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ZPassword</title>
<script src="js/jquery-3.4.1.min.js"></script>
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <script src="js/bootstrap.js"></script>
  <link rel="stylesheet" href="css/index.css">
  <script>var id="<%=session.getAttribute("id")%>";</script>
</head>
<body>
<%
if(session.getAttribute("id")==null){
	out.print("<h1 style=\"color:red;\">请先登录！</h1>");
	//if(request.getParameter("id")==null){
	//	out.print("<h1 style=\"color:red;\">请先登录！</h1>");
	%>
	<script>
	setTimeout("window.location.href='login.jsp'",1000);
	</script>
	<%
}else{
%>
	<script src="js/index.js"></script>
	<ul class="list-group" id="list-index"></ul>
	<div id="list-content-body">
		<nav class="navbar navbar-default">
		  <div class="container-fluid">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <a class="navbar-brand" href="#">ZPassword</a>
		    </div>
		
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <form class="navbar-form navbar-left">
		        <div class="form-group">
		          <input type="text" class="form-control" id="list-search" placeholder="搜索...">
		        </div>
		        <input class="btn btn-danger" type="button" value="删除账户" id="user-btn-delete">
		        <input class="btn btn-default" type="button" value="修改密码" id="user-btn-password">
		        <input class="btn btn-warning" type="button" value="退出登录" id="user-btn-logout">
		      </form>
		    <form class="navbar-form navbar-right">
		      <input class="btn btn-warning" type="button" value="刷新" id="list-content-btn-refresh">
		      <input class="btn btn-primary" type="button" value="新增" id="list-content-btn-add">
		      <input class="btn btn-default" type="button" value="修改" id="list-content-btn-edit">
		      <input class="btn btn-warning" type="button" value="取消" id="list-content-btn-cancel" style="display: none;">
		      <input class="btn btn-success" type="button" value="保存" id="list-content-btn-save" style="display: none;">
		      <input class="btn btn-danger" type="button" value="删除" id="list-content-btn-delete" style="display: none;">
		    </form>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
	<div id="list-content"></div>
	<div id="message"></div>
	</div>
	<div id="record-add-box"  class="panel panel-info record-box" style="display: none;">
		<div class="panel-heading">
        <div class="close" id="close-box"></div>
        	添加记录
	    </div>
	    <div class="panel-body">
	        <form class="form-inline" role="form" id="record-add-form">
	            <div class="form-group form-group-box form-group-div">
	                <label for="URL" class="control-label form-control-label">URL</label>
	                <input type="text" class="form-control form-control-input" id="record-add-box-url" placeholder="*://*.*">
	            </div>
	            <div class="form-group form-group-box form-group-div">
	                <label for="username" class="control-label form-control-label">用户名</label>
	                <input type="text" class="form-control form-control-input" id="record-add-box-username" placeholder="在此输入你的用户名">
	            </div>
	            <div class="form-group form-group-box form-group-div">
	                <label for="password" class="control-label form-control-label">密码</label>
	                <input type="text" class="form-control form-control-input" id="record-add-box-password" placeholder="在此输入你的密码">
	            </div>
                <div class="form-control-btn">
                    <button type="reset" class="btn btn-default form-control-btn-bt">清空重填</button>
                    <button type="button" class="btn btn-info form-control-btn-bt" id="record-add-box-submit">确定添加</button>
                </div>
	        </form>
	    </div>
	</div>
	
	
	<div id="user-pass-box"  class="panel panel-info record-box" style="display: none;">
		<div class="panel-heading">
        <div class="close" id="close-box"></div>
        	修改密码
	    </div>
	    <div class="panel-body">
	        <form class="form-inline" role="form" id="record-add-form">
	            <div class="form-group form-group-box form-group-div">
	                <label for="URL" class="control-label form-control-label">原密码</label>
	                <input type="password" class="form-control form-control-input" id="user-pass-plain" placeholder="在此输入你的旧密码">
	            </div>
	            <div class="form-group form-group-box form-group-div">
	                <label for="username" class="control-label form-control-label">新密码</label>
	                <input type="password" class="form-control form-control-input" id="user-pass-new" placeholder="在此输入你的新密码">
	            </div>
	            <div class="form-group form-group-box form-group-div">
	                <label for="password" class="control-label form-control-label">重复密码</label>
	                <input type="password" class="form-control form-control-input" id="user-pass-re" placeholder="在此重复输入你的密码">
	            </div>
                <div class="form-control-btn">
                    <button type="reset" class="btn btn-default form-control-btn-bt">清空重填</button>
                    <button type="button" class="btn btn-info form-control-btn-bt" id="user-pass-submit">确定修改</button>
                </div>
	        </form>
	    </div>
	</div>
	
	
	<div id="user-delete-box"  class="panel panel-info record-box" style="display: none;">
		<div class="panel-heading">
        <div class="close" id="close-box"></div>
        	删除用户
	    </div>
	    <div class="panel-body">
	        <form class="form-inline" role="form" id="record-add-form">
	            <div class="form-group form-group-box user-delete-div">
	                <label for="URL" class="control-label" id="user-delete-label">确定要删除本账户吗？本操作将删除该账户的所有数据且不可逆！</label>
                </div>
                <div class="form-control-btn">
                    <button type="button" class="btn btn-danger form-control-btn-bt" id="user-delete-submit">确认删除</button>
                    <button type="button" class="btn btn-default form-control-btn-bt" id="user-delete-cancel">取消操作</button>
                </div>
	        </form>
	    </div>
	</div>
<%
}
%>
</body>
</html>
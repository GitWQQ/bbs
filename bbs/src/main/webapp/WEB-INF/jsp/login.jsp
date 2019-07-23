<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib  uri="http://www.tag.com/mytag" prefix="mytag" %>
<%
	String path=request.getContextPath();
	String basepath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basepath%>">
	<title>如意论坛</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@include file="../common/script_style.jsp" %>
	<link rel="stylesheet" href="js/layui/css/layui.css" />
</head>
<body>
	<div id="log_content" style="margin-top:30px;" >
	<form id="login_form" class="layui-form" action="" lay-filter="example">
  		<div class="layui-form-item">
    		<label class="layui-form-label"><strong>用户名:</strong></label>
    		<div class="layui-input-inline">
     	 		<input type="text" id="username" name="username" required  lay-verify="required" 
     	 		placeholder="请输入用户名" autocomplete="on" class="layui-input" style="width:90%">
    		</div>
  		</div>
  		<div class="layui-form-item">
   			<label class="layui-form-label"><strong>密&nbsp;&nbsp;码:</strong></label>
   	 		<div class="layui-input-inline">
      			<input type="password" id="password" name="password" required lay-verify="required" 
      			placeholder="请输入密码" autocomplete="off" class="layui-input" style="width:90%">
    		</div>
  		</div>
  		<div class="layui-form-item">
    		<label class="layui-form-label"><strong>记住我:</strong></label>
    		<div class="layui-input-block">
      			<input  type="checkbox" id="rememberMe" name="rememberMe" lay-skin="switch" lay-filter="rememberMe" lay-text="开启|关闭">
    		</div>
  		</div>
	</form>
	</div>
</body>
<script type="text/javascript">

</script>
</html>	


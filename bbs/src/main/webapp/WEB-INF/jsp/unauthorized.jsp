<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib  uri="http://www.tag.com/mytag" prefix="mytag" %>

<%
	String path=request.getContextPath();
	String basepath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basepath%>">
		<title>如意论坛</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<%@include file="../common/script_style.jsp" %>
		<script type="text/javascript">
			$(function(){
				jQuery('#qrcodeCanvas').qrcode({
				    width   : 300,
				    height  : 300,
				    text    : "http://127.0.0.1:8082/bbs/static/index"
				})
			})
		</script>
	</head>
	<body>
		<h1>没有授权</h1>
		<div id="qrcodeCanvas"></div>
	</body>
</html>	
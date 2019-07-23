<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
	String path=request.getContextPath();
	String basepath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<nav class="navbar navbar-fixed-top navbar-inverse" role="navigation" th:fragment="navbar_top">
			<div class="navbar-header">
				<a class="navbar-brand" href="javascript:void(0);" style="margin-left:60px;font-family:FZShuTi;font-weight:blod">
					ruyi&nbsp;如意商品--论坛</a>
			</div>
			<div class="collapse navbar-collapse">
				<div class="row">
					 <div class="col-md-6">
						<ul class="nav navbar-nav">
						<li>
							<a href="<%=basepath%>static/index">
								<span class="glyphicon glyphicon-home">&nbsp;首页</span>
							</a>
						</li>
						<li>
							<a id="layui_login">
								<span class="glyphicon glyphicon-log-in">
									<shiro:user>
									      【<shiro:principal property="username" />】已登录
									 </shiro:user>
									 <shiro:guest>
									 	登录
									 </shiro:guest>
								</span>
							</a>
						</li>
						<li>
							<shiro:user>
								<a href="<%=basepath%>static/publish">
									<span class="glyphicon glyphicon-pencil">&nbsp;发布论坛</span>
								</a>
							</shiro:user>
							<shiro:guest>
								<a id="a_publish">
								<span class="glyphicon glyphicon-pencil">&nbsp;发布论坛</span>
								</a>
							</shiro:guest>
						</li>
						<li>
							<a id="layui_logout">
								<span class="glyphicon glyphicon-log-out">&nbsp;退出</span>				
							</a>
						</li>
						</ul>
					</div>
				</div>
			</div>
</nav>

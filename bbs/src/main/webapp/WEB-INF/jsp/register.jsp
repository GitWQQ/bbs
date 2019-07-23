<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="../common/script_style.jsp" %>

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
  		<div class="layui-form-item">
    		<div class="layui-input-block">
    			<br/>
      			<button style="" class="layui-btn layui-btn-sm" lay-submit lay-filter="btn_login">
      			登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
    		</div>
  		</div>
</form>
<script type="text/javascript">
	$(function(){
		layui.use('form',function(){
			var form=layui.form;
			form.on('submit(form)',function(data){
				var data1=data.field;
				var loading=layui.load(0,{shade:false});
				$.get('')
			})
		})
		
	})
	

</script>


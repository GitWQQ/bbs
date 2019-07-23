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
			var ctx="<%=basepath%>";
			$(function(){
				KindEditor.ready(function(K) {
					var editor1 = K.create('textarea[name="publish_content"]', {
						items:[ 
						        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
			                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			                    'superscript', 'clearhtml', 'quickformat', 'selectall', '|',
			                    'formatblock', '/', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			                    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
			                    'flash', 'insertfile','hr', 'emoticons', 'baidumap', 'pagebreak',
			                    'anchor', '|', 'about'
			            ]
						,resizeType : 2
						,allowPreviewEmoticons : true
						/* ,filePostName: "uploadFile" */
						,uploadJson:ctx+'sys/fileUpload' //指定上传服务器端程序
						,fileManagerJson:ctx+'sys/fileManager' // 指定浏览器远程图片的服务器端程序
						,allowFileManager : true //文件管理器功能，true时显示浏览器远程服务器按钮
						,allowImageUpload:true
						,height:'150px'	
						,minHeight:150
						,afterCreate : function() {
							this.sync();
						}
						,afterBlur: function(){this.sync();}
						
					});
				});
			});
		</script>
	</head>
	<body style="background-color:#F5F5F5;margin-bottom:50px;">
		<div style="margin:0px;">
			<jsp:include page="../common/navbar.jsp"/>
		</div>
		<div align="center" style="margin-top:80px;width:100%;">
			<div>
				<img class="img-circle" src="<%=basepath%>/images/1.jpg"  style="width:130px;height:130px;"/>
			</div>
			<div style="margin-top:10px;">
				<table  style="width:15%;font-size:14px;
					font-weight:bold;text-align:center;" align="center">
					<tr>
						<td style="border-right:2px solid #CFCFCF">粉丝</td>
						<td style="border-right:2px solid #CFCFCF">帖子</td>
						<td>积分</td>
					</tr>
					<tr>
						<td style="border-right:2px solid #CFCFCF">111</td>
						<td style="border-right:2px solid #CFCFCF">${about.published_num}</td>
						<td>${about.jf}</td>
					</tr>
				</table>
			</div>
			
			<div style="width:100%;height:50px;"> 
				<div id="publish_paging"></div>
			</div>
			<div>
				<form id="form_new_publish" method="POST" enctype="multipart/form-data">
				<input type="hidden" id="modify_image_url" name="image_url" value="<%=session.getAttribute("imgUrl")%>"/>
				<input type="hidden" id="xh" name="xh" value="${bbs.xh}"/>
				<table style="width:80%" align="center">
					<tr>
						<td>
							<hr style="border:2px solid #FFF"/>
     	 					<br/>
							<div>
     	 						<input type="text" id="modify_title" name="title" required  lay-verify="required" 
     	 							placeholder="请输入主題......" autocomplete="on" class="layui-input" style="width:100%" 
     	 							value="${bbs.title}">
     	 					</div>
     	 					
						</td>
					</tr>
					<tr>
						<td>
							<br/>
							<textarea  id="modify_publish_new_content" name="publish_content" style="width:100%;" rows="12">
							${bbs.content}
       						</textarea>
						</td>
					</tr>
					<tr>
						<td align="center">
							<br/>
							<button id="btn_modify_publish" type="button" class="btn btn-primary btn-sm">保&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存</button>
						</td>
					</tr>
				</table>
				</form>	
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	$(function(){
		//===================================
		$("#btn_modify_publish").click(function(){
			  	var imgUrl=$("#modify_image_url").val();
				var title=$("#modify_title").val();
				var publish_new_content=$("#modify_publish_new_content").val();
				var xh=$("#xh").val();
				
				if(title=="" || title==null){
					layer.msg('主题不能为空!');
					return;
				}
				if(publish_new_content ==""){
					layer.msg('发布内容不能为空');
					return;
				}
				$.ajax({
					type:'POST',
					url:ctx+'bbs/publishOrmodify',
					data:{'title':title,'content':publish_new_content,"image_url":imgUrl,"xh":xh,"action":"modify"},
					dataType:'json',
					success:function(data){
						window.location.href=ctx+"static/publish";
					}
				})	
				
		})
		
	})
	
	function removeBbs(index){
		var xh=$("#bbsXh"+index).val();
		$.ajax({
			type:'POST',
			url:ctx+'bbs/removeBbsContent',
			data:{"xh":xh},
			dataType:'json',
			success:function(data){
				layer.msg(data.message,{icon:1,time:800},function(){
					window.location.reload();	
				});
			},
			error:function(e){
				layer.msg('系统出错了请及时联系开发人员,联系电话：17551066852',{icon:5,time:10000,btn:['好的']});
			}
		})	
	}
	function getAllBBSContent(){
		$.get(ctx+'bbs/getContentByAuthorId');
	}
</script>

<script type="text/javascript">
layui.use(['element','upload'], function() {
	var upload = layui.upload,
		element = layui.element;
		element.init();

		//指定允许上传的文件类型
		upload.render({
			elem: '#chooseFile'
			,url: ctx+'bbs/fileUpload'
			,accept: 'file' //普通文件
			,auto:false //设置不自动提交
			,bindAction:'#uploadBtn'//提交按钮
			,progress: function(e , percent) {
 				console.log("进度：" + percent + '%');
 				element.progress('progressBar',percent  + '%');
			}
			,choose:function(obj){
  				obj.preview(function(index,file,result){
	  				$("#fileName").html(file.name);
  				})
			}
			//,multiple: true
			,done: function(res){
  				layer.msg(res.msg,{icon:1,time:800},function(){
					window.location.reload();	
				});
				
			}
			,error:function(res){
 			 	layer.msg('错误');
			}
		});
});	
</script>


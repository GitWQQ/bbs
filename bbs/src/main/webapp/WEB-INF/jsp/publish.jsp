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
						,uploadJson:ctx+'sys/fileUpload' //指定上传服务器端程序
						,fileManagerJson:ctx+'sys/fileManager' // 指定浏览器远程图片的服务器端程序
						,allowFileManager : true //文件管理器功能，true时显示浏览器远程服务器按钮
						,allowImageUpload:true
						,height:'150px'	
						,minHeight:150
						,afterUpload: function(){this.sync();} //图片上传后，将上传内容同步到textarea中
						,afterCreate : function() {this.sync();}
						,afterBlur: function(){this.sync();}
						
					});
				});
			});
		</script>
	</head>
	<body style="background-color:#F5F5F5">
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
						<td style="border-right:2px solid #CFCFCF">粉丝													</td>
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
			<div id="published_content" style="margin:30px auto">
				
			</div>
			<div style="width:100%;height:50px;"> 
				<div id="publish_paging"></div>
			</div>
			<div>
				<form id="form_new_publish" method="POST" enctype="multipart/form-data">
				<input type="hidden" id="image_url" name="image_url" value="<%=session.getAttribute("imgUrl")%>"/>
				<table style="width:80%" align="center">
					<tr>
						<td>
							
							<hr style="border:2px solid #FFF"/>
							<div>
     	 						<button type="button" class="layui-btn" id="chooseFile">
     	 							<i class="layui-icon">&#xe67c;</i>选择文件
     	 						</button>
     	 						<strong>文件名称:</strong><span id="fileName"></span>
     	 						<div style="height:20px;"></div>
     	 						<button type="button" class="layui-btn" id="uploadBtn">
     	 							<i class="layui-icon">&#xe67c;</i>上传文件
     	 						</button>
     	 						<div style="height: 20px;"></div>
     	 						<div class="layui-progress layui-progress-big" lay-showPercent="yes" lay-filter="progressBar">
									<div class="layui-progress-bar layui-bg-red" lay-percent="0%"></div>
								</div>

     	 					</div>
     	 					
     	 					<br/>
							<div>
     	 						<input type="text" id="title" name="title" required  lay-verify="required" 
     	 							placeholder="请输入主題......" autocomplete="on" class="layui-input" style="width:100%" 
     	 							value="${bbs.title}">
     	 					</div>
     	 					
						</td>
					</tr>
					<tr>
						<td>
							<br/>
							<textarea  id="publish_new_content" name="publish_content" style="width:100%;" rows="12">
							${bbs.content}
       						</textarea>
						</td>
					</tr>
					<tr>
						<td align="center">
							<br/>
							<button id="btn_new_publish" type="button" class="btn btn-primary btn-sm">发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;布</button>
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
		$("#btn_new_publish").click(function(){
			  	var imgUrl=$("#image_url").val();
				var title=$("#title").val();
				var publish_new_content=$("#publish_new_content").val();
			
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
						data:{'title':title,'content':publish_new_content,"image_url":imgUrl,"action":"add"},
						dataType:'json',
						success:function(data){
							layer.msg(data.message,{time:1500},function(){
								window.location.reload();	
							});
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
 $(function(){
	$.ajax({
		type:'GET',
		url:ctx+'bbs/ownpagingCount',
		dataType:'json',
		success:function(data){
			var count=data.page.count;
			var limit=data.page.limit;
			//====================
			layui.use('laypage',function(){
				var laypage = layui.laypage;
				//执行一个laypage实例
				laypage.render({
					elem:'publish_paging'//指向存放分页的容器
	    			,count:count//数据总数，从服务端获取
					,limit:limit//一页展示多少数据
					,first:'首页'
					,last:'尾页'
					,theme:'#6495ED'
					,jump:function(obj,first){
						//====================
						$.ajax({
							type:'GET',
							url:ctx+'static/getPagingBbs',
							data:{"count":count,"curr":obj.curr,"limit":limit,'own':'1'},
							success:function(data){
								var published_content=$("#published_content");
								var html="";
								if(data.length==0){
									html='<font style="font-size:20px;">暂时没有发布任何论题......</font>';
								}else{
									for(var i=0;i<data.length;i++){
									
										html+='<div style="border-bottom:2px dashed #fff;width:80%;margin:30px auto" align="left">'
											+'<span class="glyphicon glyphicon-paperclip" style="font-size:25px;color:#808080">'
											+'</span>'
											+'<span style="font-size:14px;"><strong>'+data[i].title+':</strong>'
												+'<br/>'+data[i].content
											+'<span>'
												+'<img alt="" src="">'
											+'</span>'
											+'<span style="float:right;margin-right:20px">'
												+'<input type="hidden" id="bbsXh'+i+'" value="'+data[i].xh+'"/>'
												+'<a id="removeBbs'+i+'" href="javascript:void(0);" onclick="removeBbs('+i+');">'
													+'<abbr title="删除" class="glyphicon glyphicon-trash"></abbr></a>'
													+'&nbsp;&nbsp;&nbsp;'
													+'<a href="<%=basepath%>bbs/modifyBbsContent?xh='+data[i].xh+'">'
													+'<abbr title="修改" class="glyphicon glyphicon-pencil"></abbr></a>'
											+'</span>'
											+'</div>'
								
									}
									
								}	
								published_content.html(html);
							},
							error:function(data){
								
							}
						})
							
						//====================
						
						
						//首次不执行
						if(!first){
						//的作用就是设置首次渲染分页无需走业务逻辑处理函数,不然会陷入死循环
						}
					}
				})
			})
		}
	})
 })
 
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
			,before:function(input){
				/* var fileName=$("#fileName").html();
				if(fileName!=null && fileName !=""){
					var title=$("#title").val();
					var content=$("#publish_new_content").val();
					if(title==""){
						layer.msg('主题不能为空');
					}
				} */
			}
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
<script type="text/javascript">
	

</script>


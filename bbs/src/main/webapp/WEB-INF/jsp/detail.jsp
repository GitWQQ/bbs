<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib  uri="http://www.tag.com/mytag" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
	<link rel="shortcut icon" href="<%=basepath%>images/favicon.ico" >
	
	<style type="text/css">
		.tfoot a:link{color:#ABABAB}
		.tfoot a:visited{color: #FF0000;font-weight:bold}
		.tfoot a:hover {color: #000;} 
	</style>
</head>
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
			,width:'80%'
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
<body style="background-color:#F7F7F7" onload="pageSetUp_null()">
	<div>
		<jsp:include page="../common/navbar.jsp"/>
	</div>
	
	<div class="container" style="margin-top:80px">
	<!--startprint-->
		<c:forEach items="${list}" var="bbs">
		<table id="bbs_table" style="padding-top:10px;width:100%;">
			<tr style="width:100%;background:#E0EEEE;">
				<td style="padding-top:10px;width:15%">
					<table align="center">
						<td><strong>${bbs.user.username}</strong></td>
					</table>
				</td>
				<td style="width:70%">
					<c:if test="${bbs.is_parent eq '1'}">【楼主】</c:if>
					<span style="padding-left:1%;color:#D2691E" class="glyphicon glyphicon-user"></span>
					<span style="padding-left:1%;">[发布于]&nbsp;&nbsp;
						${fn:substring(bbs.created,0,19)}
					</span>
					
		       	</td>
				<td style="width:5%">
		       		<a href="javascript:printPage()"  id="printButton">
		       			<span title="打印" class="glyphicon glyphicon-print"></span>
		       		</a>	
		       	</td>
			</tr>
			<tr style="width:100%;">                                                                                          
				<td colspan="3">                                                                                             
					<table style="width:100%;height:100px;border-bottom:3px dotted #fff">	                                     
						<tr> 
							<td style="width:15%;background-color:#D1EEEE;border:3px dotted #fff">
								<div style="width:100%;margin-top:5px;margin-bottom:30px;">                                                                                    
									<table  style="width:80%;font-size:14px;font-weight:bold;text-align:center;" align="center">                            
										<tr>
											<td colspan="3">
												<img src="<%=basepath%>/images/1.jpg" style="width:100%;"/>
											</td>
										<tr>
										<tr>                                               
											<td style="border-right:2px solid #CFCFCF">粉丝</td>                             
											<td style="border-right:2px solid #CFCFCF">帖子</td>                                                                              
											<td>积分</td>                                
										</tr>                                                                                 
										<tr>                                                                                  
											<td style="border-right:2px solid #CFCFCF"></td>
											<td style="border-right:2px solid #CFCFCF">
												<a href="#">${bbs.about.published_num}</a>
											</td>
											<td>${bbs.about.jf}</td>     
										</tr>                                                        
									</table>
									<table style="width:80%;text-align:center;margin:10px auto;">
										<tr>
											<td>
												<span title="点击关注" style="font-size:17px;" class="glyphicon glyphicon-heart"></span>
											</td>
										</tr>
									</table>
								</div>                                                                                  
							</td>                                                                                       
							<td style="width:70%;border:3px dotted #fff">
								<table style="width:100%;height:100%;">
									<tr>
										<td>
											<span>
												<div id="lt_title">
												    <strong>${bbs.title}</strong>
											    </div>                                                                             
												<div style="width:100%; word-wrap:break-word;">
													${bbs.content}
												</div>                                                              
											</span>	   
										</td>
									</tr>
									<tr class="tfoot">
										<td style="vertical-align:bottom;padding-bottom:15px;">
											<hr style="border-top:2px dotted #F8F8F8">
											<span style="margin-left:50px;"><a href="#">回&nbsp;&nbsp;复</a></span>
											<span style="float:right;margin-right:50px;"><a href="#">举&nbsp;&nbsp;报</a></span>
										</td>
									</tr>                 
								</table>                                                                              
							</td>                                                                                            
							<td style="width:15%;border:3px dotted #fff">
								<div style="width:100%;text-align:center;">
									<c:choose>
										<c:when test="${likeStatusCode==1}">
											<a id="thumbUp" href="javascript:void(0);" onclick="thumbUp()">
												<input id="likeOrunLike" type="hidden" value="${likeStatus}"/>
												<span title="点赞"  style="color:red" class="glyphicon glyphicon-thumbs-up"></span>                                              
												<span id="thumbUpCount">(${likeCount})</span>&nbsp;&nbsp;&nbsp;
											</a>	
										</c:when>
										<c:otherwise>
											<a id="thumbUp" href="javascript:void(0);" onclick="thumbUp()">
												<input id="likeOrunLike" type="hidden" value="${likeStatus}"/>
												<span title="点赞" class="glyphicon glyphicon-thumbs-up"></span>                                              
												<span id="thumbUpCount">(${likeCount})</span>&nbsp;&nbsp;&nbsp;
											</a>
										</c:otherwise>
									</c:choose>
									
									<a id="toPl" href="javascript:void(0);returnToAnchor();">                                                  
										<span title="评论" class="glyphicon glyphicon-pencil"></span>(${bbsCount})&nbsp;&nbsp;&nbsp;
									</a>
									<c:choose>
										<c:when test="${is_store >0}">
											<a id="togz" href="javascript:void(0);" onclick="rem_store();">
												<span title="取消收藏" style="font-size:17px;color:red" class="glyphicon glyphicon-star-empty"></span>
											</a>										
										</c:when>
										<c:otherwise>
											<a id="togz" href="javascript:void(0);" onclick="store();">
												<span title="收藏" style="font-size:17px;" class="glyphicon glyphicon-star-empty"></span>
											</a>
										</c:otherwise>
									</c:choose>
								</div>                                                                                    
							</td>                                                                                        
						</tr>                                                                                        
					</table>                                                                                                  
				</td>                                                                                                         
			</tr>
		</table>
		</c:forEach>
		
		<!-- ================================ -->
		
		<c:forEach items="${comments}" var="comment" varStatus="status">
		<table id="bbs_table" style="padding-top:10px;width:100%;">
			<tr style="width:100%;background:#E0EEEE;">
				<td style="padding-top:10px;width:15%">
					<table align="center">
						<td><strong>${comment.user.username}</strong></td>
					</table>
				</td>
				<td style="width:70%">
					<strong>【${status.count}楼】</strong> 
					<span style="padding-left:1%;color:#D2691E" class="glyphicon glyphicon-user"></span>
					<span style="padding-left:1%;">[发布于]&nbsp;&nbsp;
						${fn:substring(comment.comment_time,0,19)}
					</span>
		       	</td>
				<td style="width:15%">
		       	</td>
			</tr>
			<tr style="width:100%;">                                                                                          
				<td colspan="3">                                                                                             
					<table style="width:100%;height:100px;border-bottom:3px dotted #fff">	                                     
						<tr> 
							<td style="width:15%;background-color:#D1EEEE;border:3px dotted #fff">
								<div style="width:100%;margin-top:5px;margin-bottom:30px;">                                                                                    
									<table  style="width:80%;font-size:14px;font-weight:bold;text-align:center;" align="center">                            
										<tr>
											<td colspan="3">
												<img src="<%=basepath%>/images/1.jpg" style="width:100%;"/>
											</td>
										<tr>
										<tr>                                               
											<td style="border-right:2px solid #CFCFCF">粉丝</td>                             
											<td style="border-right:2px solid #CFCFCF">帖子</td>                                                                              
											<td>积分</td>                                
										</tr>                                                                                 
										<tr>                                                                                  
											<td style="border-right:2px solid #CFCFCF">(0)</td>
											<td style="border-right:2px solid #CFCFCF">
												<a href="#">${comment.about.published_num}</a>
											</td>
											<td>${comment.about.jf}</td>     
										</tr>                                                        
									</table>
									<table style="width:80%;text-align:center;margin:10px auto;">
										<tr>
											<td>
												<span title="点击关注" style="font-size:17px;" class="glyphicon glyphicon-heart"></span>
											</td>
										</tr>
									</table>
								</div>                                                                                  
							</td>                                                                                       
							<td style="width:70%;border:3px dotted #fff">
								<table style="margin-bottom:10px;width:100%;height:100%">                                                                                  
									<tr>                                      
										<td>                                                                                 
											<span>                                                                             
												<div style="width:100%; word-wrap:break-word;">
													${comment.content}
												</div>                                                              
											</span>	                                                                        
										</td>                                                                               
									</tr>
									<tr class="tfoot">
										<td style="vertical-align:bottom;padding-bottom:15px;">
											<hr style="border-top:1px dashed #F8F8F8;">
											<span style="margin-left:50px;"><a href="#">回&nbsp;&nbsp;复</a></span>
											<span style="float:right;margin-right:50px;"><a href="#">举&nbsp;&nbsp;报</a></span>
										</td>
									</tr>                                                                                         
								</table>                                                                                    
							</td>                                                                                            
							<td style="width:15%;border:3px dotted #fff">
								<div style="width:100%;text-align:center;">
									<a id="thumbUp2" href="javascript:void(0);" onclick="thumbUp2()">
										<span title="点赞" class="glyphicon glyphicon-thumbs-up"></span>                                              
										<span>(99+)</span>&nbsp;&nbsp;&nbsp;
									</a>
									
									<c:if test="${comment.user.username==currUser}">
										<a id="toRemove" href="javascript:void(0);">                                                  
											<span title="删除" class="glyphicon glyphicon-trash"></span>&nbsp;&nbsp;&nbsp;
										</a>
									</c:if>
								</div>                                                                                    
							</td>                                                                                        
						</tr>                                                                                        
					</table>                                                                                                  
				</td>                                                                                                         
			</tr>
		</table>
		</c:forEach>
		<!--endprint--> 
		<!-- ================================ -->
		
		<table style="width:100%;">
       		<tr>
       			<td style="background-color:#D1EEEE;width:15%;border:3px dotted #fff"></td>
       			<td  style="border:3px dotted #fff">
       				<form id="form_publish" method="POST" enctype="multipart/form-data">
       					<input id="pl_xh"  type="hidden" value="<%=request.getAttribute("xh")%>" />
       					<input id="is_store"  type="hidden" value="${is_store}"/>
       					
       					<div style="margin:20px;">
       						<a id="pl">
       							<textarea id="publish_content" name="publish_content" cols="100" rows="12">
       							</textarea>
       						</a>
       					</div>	
       					<div align="left" style="margin:20px;">
       						<button id="btn_publish" type="button" class="btn btn-primary btn-sm">回&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;复</button>
       					</div>
       				</form>	
       			</td>
       		</tr>
		</table>
	</div>
	<input id="hidden_username" type="hidden" value='<%=session.getAttribute("username")%>'/>
</body>
<script type="text/javascript">
	$(function(){
		submit();
	})
		//==========================
	function submit(){
		var username=$("#hidden_username").val().trim();
		$("#btn_publish").click(function(){
			if(username==undefined || username=="null" || username==null){
				layer.msg('您还没有登录,请先登录再发布信息!',{icon:7,time:1000,shade:0.4})
			}else{
				var publish_content=$("#publish_content").val();
				var pl_xh=$("#pl_xh").val();
				if(pl_xh==""){
					layer.msg('还没选择评论的主贴');
					return;
				}
				if(publish_content ==""){
					layer.msg('跟帖内容不能为空');
					return;
				}
				$.ajax({
					type:'POST',
					url:ctx+'bbs/republish',
					data:{'content':publish_content,'pl_xh':pl_xh,"action":'comment'},
					dataType:'json',
					success:function(data){
						layer.msg(data.msg,{icon:1,time:1500},function(){
							window.location.reload();	
						});
					}
				})
			}	
		})
	}
	
	function store(){
		var username=$("#hidden_username").val().trim();
		if(username==undefined || username=="null" || username==null){
			layer.msg('您还没有登录,请先登录再收藏该论题!',{ shift:6,icon:7,time:1200,shade:0.4})
			return;
		}
		//收藏
		var pl_xh=$("#pl_xh").val();
		var is_store=$("#is_store").val();
		if(is_store>0){
			layer.msg('该条论题您已经收藏过了',{shift:6,icon:7,time:1000,shade:0.4})
			return;
		}	
		$.ajax({
			type:'POST',
			url:ctx+'bbs/store',
			data:{"pl_xh":pl_xh},
			dataType:'json',
			success:function(data){
				layer.msg(data.msg,{icon:1,time:800, shift:6},function(){
					window.location.reload();	
				});
			},
			error:function(){
					
			}
		})
	}
	
    function rem_store(){
		//取消收藏
		var pl_xh=$("#pl_xh").val();
		$.ajax({
			type:'POST',
			url:ctx+'bbs/remove_store',
			data:{"pl_xh":pl_xh},
			dataType:'json',
			success:function(data){
				layer.msg(data.msg,{icon:1,time:800},function(){
					window.location.reload();	
				});
			},
			error:function(){
					
			}
		})
	}
		
	function thumbUp(){
		var username=$("#hidden_username").val().trim();
		if(username==undefined || username=="null" || username==null){
			layer.msg('您还没有登录,请先进行登录!',{ shift:6,icon:7,time:1200,shade:0.4})
			return;
		}
		
		var xh=$("#pl_xh").val();
		var likeOrunLike=$("#likeOrunLike").val();
		if(likeOrunLike=="unLike"){
			console.log("点赞");
			//点赞
			$.ajax({
				type:'POST',
				url:ctx+'bbs/likeOrUnLike',
				data:{"xh":xh,"action":"like"},
				dataType:'json',
				success:function(data){
					$("#thumbUpCount").html("("+data.count+")");
					window.location.reload();
				}
			})	
		}else{
			console.log("取消点赞");
			//取消点赞
			$.ajax({
				type:'POST',
				url:ctx+'bbs/likeOrUnLike',
				data:{"xh":xh,"action":"unLike"},
				dataType:'json',
				success:function(data){
					$("#thumbUpCount").html("("+data.count+")");
					window.location.reload();
				}
			})
		}
	}
	
	function thumbUp2(){
		alert("thumbUp2");
	}

	function printPage(){
		pageSetUp_null();
		console.log('dayin');
		bdhtml=window.document.body.innerHTML;//获取当前页的html代码  
		sprnstr="<!--startprint-->";//设置打印开始区域  
		eprnstr="<!--endprint-->";//设置打印结束区域  
		prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html  
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html 
		window.document.body.innerHTML=prnhtml;
		window.print();  
	}
	
	var hkey_root, hkey_path, hkey_key;
	hkey_root = "HKEY_CURRENT_USER";
	hkey_path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; //网页打印时设置清空页眉页脚  
	
	function pageSetUp_null(){
		try{
			var RegWsh = new ActiveXObject("WScript.Shell")
			hkey_key = "header"
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "");
			hkey_key = "footer";
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "");
		}catch(e){}
	}
	
	function pageSetUp_default(){//打印网页时设置页眉页脚默认值
		try{
			var RegWsh=new ActiveXObject("WScript.Shell");
			hkey_key="header";
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "&w&b页码，&p/&P");
			hkey_key = "footer";
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "&u&b&d")
		}catch(e){}
		
	}
	
</script>	
</html>



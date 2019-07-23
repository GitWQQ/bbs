<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib  uri="http://www.tag.com/mytag" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
</head>
<script type="text/javascript">
var ctx="<%=basepath%>";
$(function(){
	
});
</script>


<body style="background-color:#FCFCFC">
	<div>
		<jsp:include page="../common/navbar.jsp"/>
	</div>
	<div class="container" style="margin-top:80px">
	<table id="bbs_table" style="padding-top:10px;width:100%;">

	</table>
	<table style="padding-top:20px;width:100%;">
	<!-- ======分页区域====== -->
		<tr>
			<td align="center" colspan="3" style="width:100%;">
				<div id="paging"></div>
			</td>
		</tr>
	<!-- ======回复信息区域======== -->
		<tr id="hfxxq" style="width:100%;">
			
	   </tr>
	</table>
	<input id="hidden_username" type="hidden" value='<%=session.getAttribute("username")%>'/>
    </div>
</body>
<script type="text/javascript">
	
	$(function(){
		submit();
	})
</script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			type:'GET',
			url:ctx+'static/pagingCount',
			dataType:'json',
			success:function(data){
				var count=data.page.count;
				var limit=data.page.limit;
				//====================
				layui.use('laypage',function(){
					var laypage = layui.laypage;
					//执行一个laypage实例
					laypage.render({
						elem:'paging'//指向存放分页的容器
		    			,count:count//数据总数，从服务端获取
						,limit:limit//一页展示多少数据
						,first:'首页'
						,last:'尾页'
						,theme:'#6495ED'
						,jump:function(obj,first){
							//obj当前所有分页的所有参数
							console.log(obj.curr);//得到当前页以便向服务端请求相应页的数据
							$.ajax({
								type:'GET',
								url:ctx+'static/getPagingBbs',
								data:{"count":count,"curr":obj.curr,"limit":limit,"own":'0'},
								success:function(data){
									var bbs_table=$("#bbs_table");
									
									var html="";
									if(data.length==0){
										html='<font style="font-size:20px;">还没有任何的论题被发表......</font>'
									}else{
										//==
										 html+='<tr style="width:100%;background:#BCD2EE;">'
													+'<td style="width:25%">'
														+'<table align="center">'
															+'<tr>'
																+'<td><strong><a href="javascript:void(0);">最新</a></strong></td>'
																+'<td style="padding-left:10px;"><strong><a href="javascript:void(0);">新人贴</a></strong></td>'
																+'<td style="padding-left:10px;"><strong><a href="javascript:void(0);">热贴</a></strong></td>'
																+'<td style="padding-left:10px;"><strong><a href="javascript:void(0);">精华</a></strong></td>'
																+'<td style="padding-left:10px;"><strong><a href="javascript:void(0);">更多</a></strong></td>'
															+'</tr>'
														+'</table>'
													+'</td>'
													+'<td style="width:50%;padding:23px">'
		       	 									+'</td>'
													+'<td style="width:25%">'
														+'<table align="center">'
															+'<tr>'
																+'<td><strong>作者</strong></td>'
																+'<td style="padding-left:20px;"><strong>发布时间</strong></td>'
															+'</tr>'
														+'</table>'
		       	 									+'</td>'
												+'</tr>'
											 
										//==	
										
										for(var i=0;i<data.length;i++){
										
										//=====================
										 html+='<input type="hidden" id="bbsXh'+i+'"  value="'+data[i].xh+'"/>'
												+'<tr style="width:100%;">'                                                                                          
													+'<td colspan="3">'                                                                                             
														+'<table style="width:100%;border-bottom:3px dotted #DBDBDB">'	                                     
															+'<tr>' 
																+'<td align="center" style="width:5%;border-bottom:3px dotted #DBDBDB">'
																	+'<span style="font-size:35px;color:#D4D4D4;" class="glyphicon glyphicon-file"></span>'                                                                  
																+'</td>'                                                                                       
																+'<td style="width:70%;border-bottom:3px dotted #DBDBDB">'
																    +'<table style="width:100%;">'                                                                                  
																       +'<tr>'                                         
																	   	  +'<td>'                                                                                  
																		  	+'<span>'
																		  		+'<a href="javascript:void(0);">'
																		  			+'<div id="toPl" onclick="detail('+i+')" style="padding-bottom:40px;padding-top:20px;" >' 
																						+'<div style="word-wrap:break-word;padding:0px;float:left;height:100%">'+'<strong>'+data[i].title+'</strong></div>'
																							if(data[i].about.published_num<5){
																								html+='<div style="float:left;margin-left:8px;background-color:red;color:#fff;padding:3px 8px;font-size:11px;border-radius:5px;">'
																									 	+'新人贴'
																							 	 	+'</div>'	
																							}
																				html+='</div>'
																				+'</a>'	
																			+'</span>'	                                                                        
																		  +'</td>'                                                                                 
																	   +'</tr>'                                                                                     
																	+'</table>'                                                                                      
																+'</td>'                                                                                             
																+'<td style="width:25%;border-bottom:3px dotted #DBDBDB;font-size:12px;">'
																	+'<div style="width:100%;text-align:center;">'
																		+'<a>'+data[i].user.username+'</a>'
																		+'<a style="padding-left:20px;">'+data[i].created+'</a>'
																	+'</div>'                                                                                      
																+'</td>'                                                                                        
															+'</tr>'                                                                                         
														+'</table>'                                                                                                  
													+'</td>'                                                                                                         
												+'</tr>'
												
										}
										bbs_table.html(html);
									}
								}
							})
							//首次不执行
							if(!first){
							//的作用就是设置首次渲染分页无需走业务逻辑处理函数,不然会陷入死循环
							}
						}	
			
					})
				})		
				//=============
			}
		})
	})

	function detail(index){
		
        var xh=$("#bbsXh"+index).val();
        window.open(ctx+'static/detail?xh='+xh ,'_blank');
    }
	
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
						layer.msg(data.msg);							
					}
				})
			}	
		})
	}
		
	//============================	
	
</script>

</html>	





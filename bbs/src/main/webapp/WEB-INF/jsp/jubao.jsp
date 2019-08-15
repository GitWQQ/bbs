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
	<link rel="stylesheet" href="css/jubao.css" />
	<link rel="stylesheet" href="js/require.min.js" />
	<link rel="shortcut icon" href="<%=basepath%>images/favicon.ico" >
	<!-- <style type="text/css">
		body{
			color: #333;
    			line-height: 1;
   			 font: 14px "Microsoft Yahei",Arial,sans-serif;
   			 background: #f3f3f3;
		}
		#jb_container{
			margin:50px auto;
			width:65%;
			background-color:#FFF;
		}
		#jb_header{
			padding-top:30px;
			padding-left:50px;
			font-size:18px;
			font-weight:bold;
			width:100%;
		}
		#jb_content{
			width:100%;
			
		}
		span{
			padding:0px;
			margin:0px;
		}
		#content_top .lab{
			float:left;
			width:25%;
			height:30px;
			background-color:rgb(53,128,240);
		}
		#content_top .lab_pre{
			float:left;
			height:30px;
			color:rgb(53,128,240);
			padding-left:10px;
		}
		#content_top .lab_last{
			float:left;
			font-size:35px;
			color:rgb(53,128,240);
		}
		.jubao-flow {
    		font-size: 15px;
   	 		overflow: hidden;
		}
		ul li{
			    padding: 0;
    			margin: 0;
    border: 0;
    font-weight: 200;
    font-style: inherit;
    font-family: inherit;
    font-size: 100%;
    vertical-align: baseline;
    outline: 0;
		
		}
	</style> -->
	
	<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?0864b034737fecc9f098ee59b06785f3";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
<script>
    require(['addjubao'], function (index) {
        var jubaoConfFields = {"title":"\u8d34\u5427\u4e3e\u62a5","flowbar":["\u586b\u5199\u4e3e\u62a5\u4fe1\u606f","\u5b8c\u6210\u4e3e\u62a5"],"success":{"title":"\u4e3e\u62a5\u6210\u529f","content":"\u5ba2\u670d\u5c0f\u59d0\u59d0\u4f1a\u572824\u5c0f\u65f6\u5185\u901a\u8fc7\u7cfb\u7edf\u6d88\u606f\u544a\u77e5\u4f60\u5904\u7406\u7ed3\u679c\u54e6\uff01"},"savefields":["jubaotype","reason"],"queryparams":["p","client","category","pid","sign"],"flows":[{"jubaotype":{"label":"\u8bf7\u9009\u62e9\u6295\u8bc9\u4e3e\u62a5\u7c7b\u578b","type":"custom","tpl":"tieba\/jubaotype.tpl","required":true,"star":true,"changedFn":"changeCategory"},"reason":{"label":"\u8bf7\u586b\u5199\u4e3e\u62a5\u7406\u7531","type":"textarea","minlength":0,"maxlength":200,"placeholder":"\u63cf\u8ff0\u7406\u7531\u8bf7\u4e0d\u8981\u8d85\u51fa200\u5b57\u54e6","errmsg":"\u63cf\u8ff0\u7406\u7531\u8bf7\u4e0d\u8981\u8d85\u51fa200\u5b57\u54e6"},"tcontent":{"label":"\u8bf7\u6838\u5b9e\u4e3e\u62a5\u7684\u5185\u5bb9","type":"custom","tpl":"tieba\/jubaocontent.tpl"},"pid":{"type":"hidden","required":true,"value":"126952583757"}}]};
        index.init(jubaoConfFields, '/tousu/submit');
    });
</script>

</head>
	<!-- <body>
		<div id="jb_container">
			<div id="jb_header">
				<div>举报平台</div>
			</div>
			<div id="jb_content">
				<div id="content_top">
					<div class="jubao-flow">
            			<ul>
                           	<li class="on">
                    		  	<span class="fnum">01</span>
                   			  	<span class="ftit">填写举报信息</span>
                           	</li>
                           	<li>
                    			<span class="fnum">02</span>
                    			<span class="ftit">完成举报</span>
                			</li>
                        </ul>
       	 			</div>
				</div>
			</div>
			<div id="jb_footer">
		
			</div>
		</div>	
	</body> -->
	<body>
	<div class="main">
    <div class="layout-wrapper">
        <div class="jubao-title">贴吧举报</div>
        <div class="jubao-flow">
            <ul>
                <li class="on">
                    <span class="fnum">01</span>
                    <span class="ftit">填写举报信息</span>
                </li>
                <li>
                    <span class="fnum">02</span>
                    <span class="ftit">完成举报</span>
                </li>
            </ul>
        </div>
        <div class="jubao-form" id="jubaoForm">
            <div class="jubao-flow-part" id="jubaoFlowPart0">
            <div class="jubao-item">
        <div class="lbl"><span class="required">*</span>请选择投诉举报类型</div>
        <div>
    		<input type="hidden" name="jubaotype" id="jubaotype" changedFn="changeCategory">
      		<input type="radio" name="jblx" value="10001">&nbsp;低俗色情&nbsp;&nbsp;&nbsp;
      		<input type="radio" name="jblx" value="10002" style="margin-left:50px;">&nbsp;垃圾广告
      		<input type="radio" name="jblx" value="10003" style="margin-left:50px;">&nbsp;内容低俗无意义
      		<br/>
      		<input type="radio" name="jblx" value="10004">&nbsp;辱骂攻击&nbsp;&nbsp;&nbsp;
      		<input type="radio" name="jblx" value="10005" style="margin-left:50px;">&nbsp;抄袭我的内容
    		<div class="clear"></div>
    		<div class="tips"></div>
		</div>
    	</div>
        <div class="jubao-item">
        	<div class="lbl">
        		请填写举报理由<span class="sp-err" id="spErr_reason"></span>
        	</div>
        	<div class="detail">
            	<div class="limit-text-area">
                	<textarea id="reason" name="reason" maxlength="200" placeholder="描述理由请不要超出200字哦" autocomplete="off"></textarea>
                	<span class="limit-text">
                    	<span class="c">0</span>/<span class="n">200</span>
                	</span>
            	</div>
        	</div>
    	</div>
       <!--  <div class="jubao-item">
        		<div class="lbl">请核实举报的内容</div>
        		<div class="detail">
                        <div class="jubao-detail">
    			<div class="detail-user">
        		<div class="user-portrait">
            		<img src="http://tb.himg.baidu.com/sys/portrait/item/719de59583e59583e582b2e4b896733a">
        		</div>
        		<div class="user-name">墨年晨曦♬</div>
    			</div>
    			<div class="detail-content">
        		<div class="pertain"><span class="pertain-date">2019-08-06</span></div>
        			<div class="m-content">
            		<p class="line1">
              		  贴子: 有这个专业的？
            		</p>
                    <p class="line1">
              		  有这个专业的？ 
            </p>
            <div class="meta-list">
                                                <img src="http://imgsrc.baidu.com/forum/pic/item/9676bd99a9014c0821d32372047b020879f4f4cb.jpg">
                            </div>
                    </div>
    </div>
</div>
                    </div>
    </div> -->
                <input type="hidden" id="pid" name="pid" value="126952583757">
            <div class="jubao-btn-wrapper" data-flow="0">
                        <input class="form-submit btn disabled" value="提交" disabled="disabled" type="button">
            </div>
</div>
        </div>
        <div class="jubao-result-success">
            <i></i>
            <div>
                <h2>举报成功</h2>
                <p>客服小姐姐会在24小时内通过系统消息告知你处理结果哦！</p>
                <input class="btn finish-btn" value="完成" type="button">
            </div>
        </div>
    </div>
</div>
<div id="dialogApp" style="display: none">
    <el-dialog :visible.sync="dialogVisible" width="30%" center>
        <div class="jubao-result-failed" v-if="isSubmit">
            <i></i>
            <div>
                <h2>提交失败</h2>
                <p>{{ dialogMessage }}</p>
            </div>
        </div>
        <div v-else>
            {{ dialogMessage }}
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogVisible = false">关 闭</el-button>
        </span>
    </el-dialog>
</div>
<input type="hidden" id="product_id" value="216">
<input type="hidden" id="client" value="web">
<input type="hidden" id="category" value="1">
<input type="hidden" id="submit_token" value="8758e435851ee945d287b4d9da4a8072">
<input type="hidden" id="sign" value="9c174c7e9665ab1705b997b75e1e38e0">








</body>
	
</html>
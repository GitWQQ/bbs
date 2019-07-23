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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basepath%>">
	<title>如意论坛</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<%@include file="../common/script_style.jsp" %>
</head>
	<body>
		<div class="layui-row layui-col-space5">
                <div class="layui-form-item">
                    <label class="layui-form-label"><i class="i-red"></i>邀请说明</label>
                    <div class="layui-input-block">
                        <div class="layui-col-md2">
                        <button type="button" style="margin-top: 2px;" class="layui-btn" id="js_upload"><i class="layui-icon"></i>上传文件</button>
                        </div>
                        <div class="layui-col-md7">
                        <input type="text" name="fileName"  autocomplete="off" maxlength="50" class="layui-input js_upload_file_name" value="" placeholder="" readonly="readonly">
                        </div>
                    </div>
                </div>
                <input type="hidden" name="fileId" class="js_resource_invite_file_id" value="">
</div>
<!--弹出进度条-->
<div id="uploadLoadingDiv" style="display: none;">
<div class="layui-progress" lay-showpercent="true" lay-filter="js_upload_progress" style="margin: 10px;">
    <div class="layui-progress-bar layui-bg-red" lay-percent="0%"></div>
    </div>
<div class="layui-form-item" style="text-align: center">
<button class="layui-btn layui-btn-normal js_upload_progress_bar_sure" >确定</button>
</div>
</div>
	
	</body>
	<script type="text/javascript">
	layui.use(['form', 'layedit', 'laydate','element', 'upload','table'], function () {
        var upload = layui.upload,form=layui.form,
        element = layui.element,$=layui.$,table = layui.table,laydate=layui.laydate;
        element.init();
        form.render();
        //日期初始化
        laydate.render({
            elem: '#date_start'
            , type: 'datetime'
        });
      //创建监听函数
        var xhrOnProgress=function(fun) {
           xhrOnProgress.onprogress = fun; //绑定监听
            //使用闭包实现监听绑
           return function() {
               //通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
               var xhr = $.ajaxSettings.xhr();
                //判断监听函数是否为函数
                 if (typeof xhrOnProgress.onprogress !== 'function')
                      return xhr;
                  //如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
                   if (xhrOnProgress.onprogress && xhr.upload) {
                         xhr.upload.onprogress = xhrOnProgress.onprogress;
                   }
                   return xhr;
            }
      }
        
        upload.render({
            elem: '#js_upload',
            url:''
            ,multiple: false
            ,before:function(){
                element.progress('js_upload_progress', '0%');//设置页面进度条
                    layer.open({
                        type: 1,
                        title: '上传进度',
                        closeBtn: 1, //不显示关闭按钮
                        area: ['300px', '170px'],
                        shadeClose: false, //开启遮罩关闭
                        content: $("#uploadLoadingDiv").html(),
                        offset: '100px'
                    });
            }
            ,xhr:xhrOnProgress
            ,progress:function(value){//上传进度回调 value进度值
                element.progress('js_upload_progress', value+'%');//设置页面进度条
            }
            ,field: 'file'
            , accept: 'images'
            , data: {
                "index": 1,
                "appCbnid":""
            },
            accept: 'file',
            //普通文件
            done: function (res) {
                console.log(res);
                $(".js_upload_file_name").val(res[0].data.filename);
                $(".js_resource_invite_file_id").val(res[0].data.fid);
            },
            error: function () {
                layer.alert("上传失败",{offset: '100px'});
                return false;
            }

        });

        //关闭进度条提示
        $(document).on('click','.js_upload_progress_bar_sure',function(){
            layer.close(layer.index);
         });
    });
	</script>
</html>	

var rememberMe=false;
$(function(){
  layui.use(['form','layer', 'layedit'], function(){
	  
	 var form = layui.form,
	  layer = layui.layer,
	  layedit = layui.layedit;

	  //创建一个编辑器
	  var editIndex = layedit.build('LAY_demo_editor');
	 
	  //自定义验证规则
	  form.verify({
		username: function(value){
	      if(value.length < 5){
	        return '标题至少得5个字符啊';
	      }
	    }
	    ,password: [
	      /^[\S]{6,12}$/
	      ,'密码必须6到12位，且不能出现空格'
	    ]
	    ,content: function(value){
	      layedit.sync(editIndex);
	    }
	  });
	  
	  //监听指定开关
	  form.on('switch(rememberMe)', function(data){
	  	rememberMe=this.checked?'true':'false';
	  	alert(rememberMe);
	  });
	  
	  //监听提交
	  form.on('submit(btn_login)', function(data){
	    
	  });

	  form.val('example', {
		  "close": false //开关状态
	  });
	
	//---------------
//表单初始赋值
	  $("#layui_login").click(function(){
		layer.open({
            type:2,
            skin: 'layui-layer-molv',
            title: '登 录',
            shift:2,
            scrollbar:false,
            fix: false,
            btn:['登录','取消'],
            content:ctx+'static/toLoginPage',
            btnAlign:'c',
            yes: function(index, layero){
            	var body = top.layer.getChildFrame('body',index);
            	var iframeWin = window[layero.find('iframe')[0]['name']];
            	var username=body.find('#username').val();
        	    var password=body.find('#password').val();
        	     $.ajax({
        	    	type:'POST',
        			//url:ctx+'sys/doLogin',
        			url:ctx+'sys/ssoDoLogin',
        			data:{"username":username,"password":password,"rememberMe":rememberMe},
        			dataType:'json',
        			success:function(data){
        				if(data.status==200){
        					layer.closeAll();
        					location.reload();
        				}else{
        					 layer.msg(data.message,{time:2000});
        				}
        			}
        	     })
             },
             maxmin: false,
             shadeClose: true,
             shade: 0.5,
             area: ['380px', '300px'],
         });
	   });	
	 });
  
  //=======退出
  $("#layui_logout").click(function(){
	  $.ajax({
        type:'POST',
        url:ctx+'sys/logout',
        dataType:'json',
        success:function(data){
        	window.location.reload();
        }
      })
  });
  
  //=========发布论坛必须登录===
  $("#a_publish").click(function(){
	  layer.msg('必须登录才能发布论坛',{icon:7,time:1000});
  })
  
  
})
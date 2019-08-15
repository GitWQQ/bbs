$(function(){
	layui.use(['layer','form'],function(){
		var layer=layui.layer
		,form=layui.form
		,$=layui.jquery;
		
		$(".tipOffs").click(function(){
			layer.open({
				type:2
				,skin: 'layui-layer-molv'
				,title:'举报'
				,shift:2
				,scrollbar:false
				,fix: false
				,btn:['确定','取消']
				,content:ctx+'static/toTipOffsPage'
				,btnAlign:'c'
				,maxmin: false
	            ,shadeClose: true
	            ,shade: 0.5
	            ,area: ['480px', '300px']
				,yes: function(index, layero){
					var body = top.layer.getChildFrame('body',index);
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var username=body.find('#username').val();
					var password=body.find('#password').val();
					$.ajax({
						type:'POST',
						url:ctx+'',
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
				}

			})
		})
	})
})
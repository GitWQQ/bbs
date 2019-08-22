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
<body>
	

<div id="main" style="width: 600px;height:400px;"></div>
<div id="chart" style="width:600px;height:400px;"></div>
</body>
<script type="text/javascript">
		var apidata={
				params:[
				        	["17/7/10","17/7/12","17/7/14","17/7/16","17/7/18","17/7/20"],
				        	["11","15","8","33","55","21"]
				       ]
		}
		var dom = document.getElementById("main");
		var myChart = echarts.init(dom);
		
		function addData(shift){
			date=apidata.params[0];
			data=apidata.params[1];
			date.push(parseInt(Math.random()*10));
			data.push(parseInt(Math.random()*10));
			if(shift){
				date.shift();
				data.shift();
			}
		}
		
		option = {
				            title: {
				                text: 'echart实时数据监控--类似股票走势图效果'
				            },
				            tooltip: {trigger: 'axis',//提示跟随鼠标位置显示
				        		axisPointer: {
				            		type: 'cross',
				            		label: {
				                		backgroundColor: '#283b56'
				            		}
				        		}
							},
				            legend: {
				                data:['销量']
				            },
				            xAxis: {
				                data: apidata.params[0]
				            },
				            yAxis: {boundaryGap: [0, '100%']},
				            series: [{
				                name: '销量',
				                type: 'line',
				                data: apidata.params[1]
				            }]
				        };
		
		setInterval(function(){
			addData(true);
			myChart.setOption({
				xAxis:{data:date},
				series:[{
					name:'成交',
					data:data
				}]
			}
			)
		},2000);
		
	    if (option && typeof option === "object") {
	        myChart.setOption(option, true);
	    }
	</script>	
	
	<script type="text/javascript">  
            // 路径配置  
            require.config({  
                paths:{   
                    'echarts' : 'js/echarts/',  
                    'echarts/chart/bar' : 'js/echarts/'  
                }  
            });  
              
            // 使用  
            require(  
                [  
                    'echarts',  
                    'echarts/chart/bar' // 使用折线图就加载bar模块，按需加载  
                ],  
                function (ec) {  
                    // 基于准备好的dom，初始化echarts图表  
                    var myChart = ec.init(document.getElementById('drawEcharts'));   
                      
                    var jsonNum=[13,12,12,15,20,18,8];
					 var jsonTime=[71,71,70.21,94,11,11,42];
					 var jsonDay=["1月23日","1月24日","1月25日","1月26日","1月27日","1月28日","1月29日"];
                      
					 option = {
							    title : {
							        text: '在线直播人数',
							        subtext: ''
							    },
							    tooltip : {
							        trigger: 'axis'
							    },
							    legend: {
							        data:['在线人数', '预购队列']
							    },
 
							    dataZoom : {
							        show : false,
							        start : 0,
							        end : 100
							    },
							    xAxis : [
							        {
							            type : 'category',
							            boundaryGap : true,
							            data : (function (){
							                var now = new Date();
							                var res = [];
							                var len = 10;
							                while (len--) {
							                    res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
							                    now = new Date(now - 2000);
							                }
							                return res;
							            })()
							        },
							        {
							            type : 'category',
							            boundaryGap : true,
							            data : (function (){
							                var res = [];
							                var len = 10;
							                while (len--) {
							                    res.push(len + 1);
							                }
							                return res;
							            })()
							        }
							    ],
							    yAxis : [
							        {
							            type : 'value',
							            scale: true,
							            name : '价格',
							            boundaryGap: [0.2, 0.2]
							        },
							        {
							            type : 'value',
							            scale: true,
							            name : '预购量',
							            boundaryGap: [0.2, 0.2]
							        }
							    ],
							    series : [
							        {
							            name:'预购队列',
							            type:'bar',
							            xAxisIndex: 1,
							            yAxisIndex: 1,
							            data:(function (){
							                var res = [];
							                var len = 10;
							                while (len--) {
							                    res.push(Math.round(Math.random() * 1000));
							                }
							                return res;
							            })()
							        },
							        {
							            name:'在线人数',
							            type:'line',
							            data:(function (){
							                var res = [];
							                var len = 10;
							                while (len--) {
							                    res.push((Math.random()*10 + 5).toFixed(1) - 0);
							                }
							                return res;
							            })()
							        }
							    ]
							};
							var lastData = 11;
							var axisData;
							clearInterval(timeTicket);
							timeTicket = setInterval(function (){
							    lastData += Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1);
							    lastData = lastData.toFixed(1) - 0;
							    axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');
							    
							    // 动态数据接口 addData
							    myChart.addData([
							        [
							            0,        // 系列索引
							            Math.round(Math.random() * 1000), // 新增数据
							            true,     // 新增数据是否从队列头部插入
							            false     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
							        ],
							        [
							            1,        // 系列索引
							            lastData, // 新增数据
							            false,    // 新增数据是否从队列头部插入
							            false,    // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
							            axisData  // 坐标轴标签
							        ]
							    ]);
							}, 2000);
							                    
              
                    // 为echarts对象加载数据   
                    myChart.setOption(option);   
                }  
            );  
        
  </script> 
  

	<script type="text/javascript">var timeTicket;</script>
	<div id="drawEcharts" style="height:400px;"></div>
	<script type="text/javascript">
		var myChart2=echarts.init(document.getElementById("chart"));
		var options2={
				//定义一个标题
				title:{
					text:'测试'	
				},
				legend:{
					data:['销量']
				},
				//x轴
				xAxis:{
					data:['60分','70分','80分','90分','100分']
				},
				yAxis:{
					
				},
				series:[{
					name:'销量',
					type:'bar',
					data:['12','32','45','21','1']
				}]
		}
		myChart2.setOption(options2);
	</script>
</html>


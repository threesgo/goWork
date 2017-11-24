<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
 <script type="text/javascript" src="${pageContext.request.contextPath}/scriptPlugins/echarts-2.2.7/echarts.js"></script>
 <script language="javascript">
 require.config({
     paths: {
         echarts: '${pageContext.request.contextPath}/scriptPlugins/echarts-2.2.7'
     }
 });
   function dateAfter(dateStr,num){
	   var date1 = new Date(Date.parse(dateStr.replace(/-/g,   "/")));
	   //alert(date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate());
	   var date2 = new Date(date1);
	   date2.setDate(date1.getDate()+num);
	   var times = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
	   return times;
  }
  function GetDateDiff(startDate,endDate){  
      var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();     
      var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
      var dates = (startTime - endTime)/(1000*60*60*24);     
      return  dates;    
  }
  
  var json = new Function("return " + '${dateStr}')();
  var totalCategory = 0,category=[],planData=[],planBase=[],actualData=[],actualBase=[],legend=[];
  var globalmin = null;
  
  $(function(){
   if(json && json.stageList){
    totalCategory = json.stageList.length;
    var minPlan = null, minActual = null, max = null;
    for(var i=(totalCategory-1);i>=0;i--){
     var planStart = json.stageList[i].planBeginDate;
     var actualStart = json.stageList[i].beginDate;
     if(i==(json.stageList.length-1)){
      var dateTT = new Date();
      var dstr = dateTT.getFullYear()+"-"+(dateTT.getMonth()+1)+"-"+dateTT.getDate()
      minPlan = planStart || dstr;
      minActual = actualStart || dstr;
      max = planStart;
     }
     if(GetDateDiff(max,json.stageList[i].planEndDate)<0){
      max = json.stageList[i].planEndDate;
     }
     if(GetDateDiff(max,json.stageList[i].endDate)<0){
      max = json.stageList[i].endDate;
     }
     if(GetDateDiff(minPlan,planStart)>0){
      minPlan = planStart;
     }
     if(GetDateDiff(minActual,actualStart)>0){
      minActual = actualStart;
     }
    }
    
    var min = null;
    if(GetDateDiff(minPlan,minActual)>=0){
     min = minActual;
    }else{
     min = minPlan;
    }
    globalmin = min;
    for(var i=(totalCategory-1);i>=0;i--){
     var planStart = json.stageList[i].planBeginDate;
     var planEnd = json.stageList[i].planEndDate;
     var actualStart = json.stageList[i].beginDate;
     var actualEnd = json.stageList[i].endDate;
     
     category.push(json.stageList[i].name);
     planBase.push(GetDateDiff(planStart,globalmin));
     var pd = {};
     pd.value = GetDateDiff(actualEnd,actualStart);
     pd.color = '#FF0000';
     var currentStage = json.currStageList;
     var sName = json.stageList[i].name;
     if(currentStage && $.inArray(sName, currentStage)>-1){
      pd.color = '#FFFF00';
     }
     planData.push(GetDateDiff(planEnd,planStart));
     actualBase.push(GetDateDiff(actualStart,globalmin));
     actualData.push(pd);
     
    }
    for(var i=0;i<GetDateDiff(max,min);i++){
     var dt = dateAfter(min,i);
     legend.push(dt);
    }
    init();
   }
  });
  function init(){   
  	require(
     [
         'echarts',
         'echarts/chart/bar'
     ],
    //自建项目饼状图设置
    function (ec) {
        var myChart = ec.init(document.getElementById('charts_div'));
	     var option = {
	         title: {
	             text: '',
	             subtext: ''
	         },
	         tooltip : {
	             trigger: 'axis',
	             axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                 type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	             },
	             formatter: function (params) {
	                 var tar0 = params[0];
	                 var tar1 = params[1];
	              	 var tar2 = params[2]
	                 var tar3 = params[3];
	                 var result = tar2.seriesName + ' : ' + dateAfter(globalmin,tar2.value-0) + '<br/>' + 
	                 tar3.seriesName + ' : ' + dateAfter(globalmin,tar2.value+tar3.value)+ '<br/>' + 
	                 tar0.seriesName + ' : ' + dateAfter(globalmin,tar0.value-0) + '<br/>' + 
	                 tar1.seriesName + ' : ' + dateAfter(globalmin,tar0.value+tar1.value);
	                 return result;
	             }
	         },
	         xAxis : [
	             {
	                 type : 'value',
	                splitNumber: legend.length-1,
	                max: legend.length-1,
	                axisLabel:{
	                 show: true,
	                 rotate:70
	                },
	                splitLine:{
	                 show: true
	                }
	             }
	            /* ,{
	                 type : 'category',
	                 boundaryGap : false,
	                 data : legend
	             }
	            */
	         ],
	         yAxis : [
	             {
	                 type : 'category',
	                 splitLine: {show:false},
	                 data : category
	             }
	         ],
	         series : [
	             {
	                 name:'实际开始时间',
	                 type:'bar',
	                 stack: '总量1',
	                 itemStyle:{
	                     normal:{
	                         barBorderColor:'rgba(0,0,0,0)',
	                         color:'rgba(0,0,0,0)'
	                     },
	                     emphasis:{
	                         barBorderColor:'rgba(0,0,0,0)',
	                         color:'rgba(0,0,0,0)'
	                     }
	                 },
	                 data:actualBase
	             },
	             {
	                 name:'实际完成时间',
	                 type:'bar',
	                 stack: '总量1',
	                 itemStyle : { normal: {
	                   color:function(params) {
	                    //alert(JSON.stringify(params));
	                    return params.data.color;
	                   },label : {show: true, position: 'inside'}}},
	                 data:actualData
	             },
	             {
	                 name:'计划开始时间',
	                 type:'bar',
	                 stack: '总量',
	                 itemStyle:{
	                     normal:{
	                         barBorderColor:'rgba(0,0,0,0)',
	                         color:'rgba(0,0,0,0)'
	                     },
	                     emphasis:{
	                         barBorderColor:'rgba(0,0,0,0)',
	                         color:'rgba(0,0,0,0)'
	                     }
	                 },
	                 data:planBase
	             },
	             {
	                 name:'计划完成时间',
	                 type:'bar',
	                 stack: '总量',
	                 itemStyle : { 
	                  normal: {
	                   color:'#00AA55',
	                   label : {
	                    show: true, 
	                    position: 'inside'
	                   }
	                  }
	                 },
	                 data:planData
	             }
	         ]
	     };
         myChart.setOption(option);
     });
  }
</script>
<div id="charts_div" style="height: ${height}px;width: ${width}px"></div>
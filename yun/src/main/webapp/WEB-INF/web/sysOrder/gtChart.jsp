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
  
        
  //var url = "${path}/notice/groupFirstCheckNotice!initGantt.action?projectId=${resultjson.projectId}";
  var totalCategory = 0,category=[],planData=[],planBase=[],actualData=[],actualBase=[],legend=[];
  var globalmin = null;
  var json = {
     "currStageIndexList": [
      8, 
      9
     ], 
     "currStageList": [
      "初步验收", 
      "试运行"
     ], 
     "stageList": [
      {
       "planBeginDate": "2016-08-31", 
       "name": "设计审查", 
       "endDate": "2016-04-22", 
       "beginDate": "2016-04-22", 
       "planEndDate": "2016-10-05"
      }, 
      {
       "planBeginDate": "2016-10-05", 
       "name": "设计批复", 
       "endDate": "2016-04-22", 
       "beginDate": "2016-04-22", 
       "planEndDate": "2016-10-12"
      }, 
      {
       "planBeginDate": "2016-10-12", 
       "name": "工程交接", 
       "endDate": "2016-04-22", 
       "beginDate": "2016-04-22", 
       "planEndDate": "2016-10-26"
      }, 
      {
       "planBeginDate": "2016-10-26", 
       "name": "设备到货验收", 
       "endDate": "2016-04-22", 
       "beginDate": "2016-04-22", 
       "planEndDate": "2016-11-25"
      }, 
      {
       "planBeginDate": "2016-11-25", 
       "name": "开工启动", 
       "endDate": "2016-04-22", 
       "beginDate": "2016-04-22", 
       "planEndDate": "2016-12-25"
      }, 
      {
       "planBeginDate": "2016-12-25", 
       "name": "设备安装", 
       "endDate": "2016-04-22", 
       "beginDate": "2016-04-22", 
       "planEndDate": "2017-01-01"
      }, 
      {
       "planBeginDate": "2017-01-01", 
       "name": "系统调测", 
       "endDate": "2016-04-22", 
       "beginDate": "2016-04-22", 
       "planEndDate": "2017-01-08"
      }, 
      {
       "planBeginDate": "2017-01-08", 
       "name": "割接上线", 
       "endDate": "2016-04-22", 
       "beginDate": "2016-04-22", 
       "planEndDate": "2017-01-15"
      }, 
      {
       "planBeginDate": "2017-01-15", 
       "name": "初步验收", 
       "endDate": "2017-04-17", 
       "beginDate": "2017-01-22", 
       "planEndDate": "2017-04-15"
      }, 
      {
       "planBeginDate": "2017-04-15", 
       "name": "试运行", 
       "endDate": "2017-11-17", 
       "beginDate": "2017-05-12", 
       "planEndDate": "2017-10-12"
      },
      {
       "planBeginDate": "2017-10-12", 
       "name": "竣工验收", 
       "endDate": "", 
       "beginDate": "", 
       "planEndDate": "2017-12-11"
      }
     ], 
     "projectId": 8211
    }
  //$.getJSON(url,function(json){
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
    //---------------------------
    var min = null;
    if(GetDateDiff(minPlan,minActual)>=0){
     min = minActual;
    }else{
     min = minPlan;
    }
    //------------------------------
    globalmin = min;
    for(var i=(totalCategory-1);i>=0;i--){
     var planStart = json.stageList[i].planBeginDate;
     var planEnd = json.stageList[i].planEndDate;
     
     category.push(json.stageList[i].name);
     planBase.push(GetDateDiff(planStart,globalmin));
     var pd = {};
     pd.color = '#FF0000';
     var currentStage = json.currStageList;
     var sName = json.stageList[i].name;
     if(currentStage && $.inArray(sName, currentStage)>-1){
      pd.color = '#FFFF00';
     }
     planData.push(GetDateDiff(planEnd,planStart));
     
     
    }
    for(var i=0;i<GetDateDiff(max,min);i++){
     	var dt = dateAfter(min,i);
     	legend.push(dt);
    }
    init();
   }
  });
  
  alert(Some.util.toJson(legend));
  
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
         xAxis : [
             {
                 type : 'value',
                splitNumber: legend.length-1,
                max: legend.length-1,
                axisLabel:{
                 show: false
                },
                splitLine:{
                 show: false
                }
             },
             {
                 type : 'category',
                 boundaryGap : false,
                 data : legend
             }
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
             }
         );
         
         }
    </script>
<div id="charts_div" style="height: 1000px;width: 1000px"></div>
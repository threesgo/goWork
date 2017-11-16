<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--公共引用页面 --%>
<% 
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<meta http-equiv="Pragma" content="no-cache"/>    
<meta http-equiv="Cache-Control" content="no-cache"/>    
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10,11" />

<%-- 
<!-- EasyUI主题样式文件 -->
<!-- 图标样式 -->
<!-- 表单样式 -->
--%>
<%-- 加载jquery --%>
<script type="text/javascript"  src="${pageContext.request.contextPath}/jquery/jquery.min.js"></script>
<%-- 支持 $.browser --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-browser.js"></script>
<%-- EasyUI主题样式文件 --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scriptPlugins/jquery-easyui-1.4.5/themes/metro-silver/easyui.css" id="swicth-style" />

<%-- EasyUI附加主题样式文件 --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/jquery-easy.css" />

<%-- EasyUI扩展样式 --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/easyUIExt.css" /> 

<%-- 图标扩展样式 --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scriptPlugins/jquery-easyui-1.4.5/themes/extIcon.css" />
<%-- EasyUI主文件 --%>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/scriptPlugins/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<%-- EasyUI编辑扩展 --%>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/scriptPlugins/jquery-easyui-1.4.5/datagrid-cellediting.js"></script>

<%--引用中文语言包 --%>
<script type="text/javascript"  src="${pageContext.request.contextPath}/scriptPlugins/jquery-easyui-1.4.5/locale/<s:text name='label.local.script'/>"></script>

<%-- 弹出框扩展插件 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/dialog.js"></script>
<%-- 请求加载器扩展插件 --%> 
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/loading.js"></script> 
<%-- 表单扩展插件 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/form.js"></script> 
<%-- 工具方法扩展插件 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/util.js"></script> 
<%-- easyui扩展 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/extEasyui.js"></script>
<%-- jquery扩展 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/extJquery.js"></script>


<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/events.js"></script>

<%-- 数组方法扩展 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/extArray.js"></script> 

<!-- 引入EasyUI Portal插件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/scriptPlugins/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scriptPlugins/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>

<%-- 新增图标扩展样式 --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/ext/easyUIExt.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/extEasyUIIcon.css" type="text/css"></link>

<link rel="stylesheet" href="${pageContext.request.contextPath}/style/images/icon/sysIcon.css" type="text/css"></link>

<%-- 加载器样式 --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/loading.css" /> 

<%--封装的一些jquery easyui组件和自定义的一些编辑器--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/tree.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/tabs.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/editor.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/checkbox.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/datagrid-dnd.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/jsAddress.js"></script>

<script>
(function(window,$){
	/****取消页面缓存*****/
	$.ajaxSetup({cache:false});
	
	/***自定义弹出框****/
	window.$alert=function(msg){
		$.messager.alert("提示信息",msg);
	};
	
	/***自定义右下角提示框****/
	window.$show=function(args,args1){
		if(typeof args == "string"){
			 $.messager.show({
			 		  title:"提示信息",
			 		  msg:args,
			 		  timeout:args1||1500,
       				  showType:'slide'
			 	});
		}
		else{
			$.messager.show(args);
		}
    };
   
    /*******自定义对话框*******/
	window.$confirm=function(msg,fn1,fn2){
		$.messager.confirm("提示信息",msg,function(r){
				if(r){
					if(fn1) fn1();
				}
				else{
					if(fn2) fn2();
				}
		
		});
	};
	
	window.handlerResult=function(data,fn1,fn2,fn3){
		var json={};
		if(typeof data =="string"){
			try{
				json = (new Function("return " + data))();
			}catch (e) {
				json={message:data,status:"crash"};
			}				
		}
		else{
			json=data;
		}
		switch (json.status) {
			case "success":
				if(fn1){
					return fn1(json);
				}
				break;
			case "error":
				if(fn2){
					return fn2(json);
				}
				break;
			case "crash":
				var fn=fn3||fn2;
				if(fn){
					return fn(json);
				}
				break;
			case "warn":
				if(fn3){
					return fn3(json);
				}
				break;
			default:
				break;
		}
	};
	
	document.onkeydown = function () {
        if (window.event && window.event.keyCode == 13) {
            window.event.returnValue = false;
        }
    };
})(window,jQuery);

/*关闭多个tab*/
function closeAllTab(layoutTab){
	var allTabs = layoutTab.tabs('tabs');
	var closeTabsTitle = [];
	$.each(allTabs, function() {
		var opt = $(this).panel('options');
		closeTabsTitle.push(opt.title);
	});
	for ( var i = 0; i < closeTabsTitle.length; i++) {
		layoutTab.tabs('close', closeTabsTitle[i]);
	}
}

$(function(){
	var $parseJSON=$.parseJSON;
	$.parseJSON=function(value){
		if(value){
			return $parseJSON(value);
		}
		return null;
	}
});
</script>
<style>
form div{
    margin-top: 20px;
    margin-left: 20px;
}

.table_seach_div{
	float:left !important;
	width: 100%;
	padding-left: 30px;
	height:auto !important;
	padding:3px;
}

.table_seach_div .search-div{
	margin-left: 30px;
	height: 30px;
	margin-top:3px;
	float: left;
	width: 240px;
	*width: auto;
	*float: left !important;
	max-width: 300px;
}

.table_seach_div div label{
	float: left;
}

.table_seach_div div>input,.table_seach_div div>select{
	float: right;
	width: 152px;
}

.table_seach_div div.select{
	float: right;
	width: 152px;
}

.table_seach_div div>select{
	width: 152px;
}
</style>
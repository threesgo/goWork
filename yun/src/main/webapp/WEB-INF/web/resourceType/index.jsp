<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/public/public.jsp" />
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#resourceType_bar'" title=" " style="width:200px;" border="false">
			<ul id="resourceType_tree"></ul>
			<div  id="resourceType_bar">
		       <a href="#" class="icon-reflesh" onclick="reload()">刷新</a>
		     </div>
		</div>
		<div data-options="region:'center',split:true,title:''" border="false">
			<div id="resourceType_tab" ></div>
		</div>
	 </body>
</html>
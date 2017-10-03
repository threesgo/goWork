<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $resourceTypeChildrenTable;
$(function(){
	$resourceTypeChildrenTable=$("#resourceTypeChildrenTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#resource_type_tool_bar",
        animate : true,
 		collapsible : true,
  		pagination:true, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
      	url:"resourceTypeAction!childrenList.act?sysRsRcCatalog.id=${sysRsRcCatalog.id}",
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'catalogCode',
			title : "类型代号",
			width : '50%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'catalogName',
			title : "类型名称",
			width : '50%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		}
		]]
	});
});
</script>
<div id="processBomResourceEdit" class="easyui-layout" data-options="fit:true">  
	<div id="processBomResource_tool_bar">
		<a href="#" class="cog_add" onclick="resourceTypeOperation.add()">新建</a>
	</div>
	<div data-options="region:'center',split:false,title:' ',tools:'#processBomResource_tool_bar'" border="false">
		<table id="resourceTypeChildrenTable"></table>
	</div>
</div>
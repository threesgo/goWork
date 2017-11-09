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
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
      	url:"resourceTypeAction!childrenList.act?sysRsRcCatalog.id=${sysRsRcCatalog.id}",
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'catalogCode',
			title : "类型编号",
			width : '49%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'catalogName',
			title : "类型名称",
			width : '49%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		}
		]]
	});
});
</script>
<table id="resourceTypeChildrenTable"></table>
<div id="resource_type_tool_bar">
	<s:if test="#session.defaultMenu.resourceTypeActionAdd==1">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" 
		onclick="resourceTypeOperation.addType()">新增</a>
	</s:if>
</div>

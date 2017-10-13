<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$resourceTypeInfoTable=$("#resourceTypeInfoTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#resource_type_info_bar",
        url:"resourceTypeAction!infoData.act?sysRsRcCatalog.id=${sysRsRcCatalog.id}",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'attrName',
			title : "属性名称",
			width : '48%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'value',
			title : "属性值",
			width : '48%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		}
		]]
	});
});
</script>
<table id="resourceTypeInfoTable"></table>
<div id="resource_type_info_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" 
		onclick="resourceTypeOperation.editType()">编辑</a>
</div>
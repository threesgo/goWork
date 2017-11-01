<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$resourcePackageInfoTable=$("#resourcePackageInfoTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#resource_package_info_bar",
        url:"resourcePackageAction!infoData.act?sysRsRcPackage.id=${sysRsRcPackage.id}",
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
			sortable : false,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'value',
			title : "属性值",
			width : '48%',
			sortable : false,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		}
		]]
	});
});
</script>
<table id="resourcePackageInfoTable"></table>
<div id="resource_package_info_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" 
		onclick="resourcePackageOperation.editPackage()">编辑</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" 
		onclick="resourcePackageOperation.deletePackage()">删除</a>
</div>
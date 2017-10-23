<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $resourcePackageChildrenTable;
$(function(){
	$resourcePackageChildrenTable=$("#resourcePackageChildrenTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#resource_package_tool_bar",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
      	url:"resourcePackageAction!childrenData.act",
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'code',
			title : "组合编号",
			width : '49%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'name',
			title : "组合名称",
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
<table id="resourcePackageChildrenTable"></table>
<div id="resource_package_tool_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" 
		onclick="resourcePackageOperation.addPackage()">新增</a>
</div>

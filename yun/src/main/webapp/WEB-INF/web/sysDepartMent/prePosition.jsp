<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $resourcePackageChildrenTable;
$(function(){
	$sysPositionTable=$("#sysPositionTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#sys_Position_tool_bar",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
      	url:"sysPositionAction!findPosition.act",
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'code',
			title : "职位代号",
			width : '32%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'name',
			title : "职位名称",
			width : '32%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'positionType',
			title : "职位类别",
			width : '32%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		}
		]]
	});
});
</script>
<table id="sysPositionTable"></table>
<div id="sys_Position_tool_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" 
		onclick="addPosition()">新增</a>
</div>

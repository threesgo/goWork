<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $resourcePackageChildrenTable;
$(function(){
	$sysDepartMentTable=$("#sysDepartMentTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#sys_DepartMent_tool_bar",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
      	url:"sysDepartMentAction!listDepartMent.act",
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'code',
			title : "部门编号",
			width : '49%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'name',
			title : "部门名称",
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
<table id="sysDepartMentTable"></table>
<div id="sys_DepartMent_tool_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" 
		onclick="addDepartMent()">新增</a>
</div>

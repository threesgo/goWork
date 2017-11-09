<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $catalogTab;
var $brandTab;

$(function(){
	$catalogTab=$("#catalogTab").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#edit_tool_bar",
        url:"resourcePackageAction!allLastChildrenListData.act?sysRsRcPackage.id=${sysRsRcPackage.id}",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'name',
			title : "类别名称",
			width : '48%',
			sortable : false,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		}
		]]
	});
	
	
	$brandTab=$("#brandTab").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'name',
			title : "属性名称",
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
<div class="easyui-layout"  id="stepLayout" data-options="fit:true,border : false">
	<div data-options="region:'center',border:false,title:''">
		<table id="catalogTab"></table>
	</div>
	
	<div data-options="region:'east',border:true,title:'',split:true" style="height:100%;width: 50%;overflow: scroll;">
		 <table id="brandTab"></table>
	</div>
</div>

<div id="edit_tool_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok', plain:true" class="icon-ok" onclick="operStepManage.ok()">确认编辑行</a>
</div>

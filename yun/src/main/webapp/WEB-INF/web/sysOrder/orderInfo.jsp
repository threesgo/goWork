<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$sysOrderInfoTable=$("#sysOrderInfoTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#order_info_bar",
        url:"sysOrderAction!orderInfoData.act?sysOrder.id=${sysOrder.id}",
        animate : true,
 		collapsible : true,
  		//pagination:false, 
  		//pageSize:20,  
        //pageList:[10,20,50,100,150,200],
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
<table id="sysOrderInfoTable"></table>
<div id="order_info_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" 
		onclick="sysOrderFlowOperation.addOrderFlow()">新增步骤</a>
</div>

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="/public/public.jsp" />
	<head>
	<script type="text/javascript">
		var $sysWorkerDatagrid;	
		var sysWorkerOperation = {};
		$(function(){
			$sysWorkerDatagrid=$('#sysWorkerTable').datagrid({
				fitColumns:false,
		        striped:true,
		        singleSelect:true,
		        fit:true,
		        nowrap:true,
		        idField:"id",
		        remoteSort:false,
		        multiSort:true,
		        rownumbers : true,
		        selectOnCheck:false,
		        checkOnSelect:false,
		        pageSize:20,
		        pageList:[20,50,100,150,200],
		        pagination:true,
	            toolbar:"#sysworker_tabel_search",
	            height: 'auto',
	            url:"sysWorkerAction!listData.act",
				columns:[[
					
				]]
		  	});
		});
		
		sysWorkerOperation={
				
		};
	</script>
	</head>
	<body id="body" >
		<div class="easyui-panel" title="" data-options="border:false,fit:true,tools:'#sysworker_tool_bar'" style="background-color: #fcfdfe;" >
			<div id="sysworker_tool_bar">
				<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysWorkerOperation.addSysSupplier()">新增</a>
				<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysWorkerOperation.editSysSupplier()">编辑</a>
				<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="sysWorkerOperation.deleteSysSupplierS()">删除</a>
			</div>
			
			<table id="sysWorkerTable"></table>
			<div id="sysworker_tabel_search" class="table_seach_div">
				<div class="search-div">
					<label>供应商名称</label>
					<input type="text" id="name"/>
				</div>
				<div class="search-div">
					<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="sysWorkerOperation.search()">搜索</a> 
					<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="sysWorkerOperation.reset()">重置</a> 
				</div>
			</div>
		</div>
	</body>
</html>



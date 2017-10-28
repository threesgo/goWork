<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
  	<script type="text/javascript">
  		var workerDataGrid;
  		var resourceDataGrid;
  		
  		var workerEdit=undefined;
  		var resourceEdit=undefined;
  		
  		window.workerResourceEdit={
  			
  		};
  		
	  	$(function(){
	  		workerDataGrid=$("#workerGrid").datagrid({
				resizable:false,
				collapsible:true,
	       		//fitColumns:true, 
	          	striped:true,
	         	singleSelect:true, 
	         	multiSort:true,
	           	singleSelect:true,
	           	selectOnCheck:false,
	           	checkOnSelect:false,
	           	remoteSort:false,
	         	rownumbers:true,
	          	nowrap:false,
	          	fit:true,
	          	idField:'id',
	          	toolbar:'#workerGridBar',
          	 	//pagination:false, 
	          	//pageSize:20,  
	          	//pageList:[20,50,100,150,200],
	          	url:"",
				queryParams:{},
				onDblClickRow: function(index,row){
 	      		  	
			    },
			    onLoadSuccess:function(){
			    	
				},
	          	onSelect: function(index,row){
	          		
	   	  	  	},
			  	columns:[[
	  	          	{field:'_checkbox',checkbox:true},
 					
				]]
	 		});
	  		
	  		resourceDataGrid=$("#resourceGrid").datagrid({
	  			resizable:false,
				collapsible:true,
	       		//fitColumns:true, 
	          	striped:true,
	         	singleSelect:true, 
	         	multiSort:true,
	           	singleSelect:true,
	           	selectOnCheck:false,
	           	checkOnSelect:false,
	           	remoteSort:false,
	         	rownumbers:true,
	          	nowrap:false,
	          	fit:true,
	          	idField:'id',
	          	toolbar:'#resourceGridBar',
          	 	//pagination:false, 
	          	//pageSize:20,  
	          	//pageList:[20,50,100,150,200],
	          	url:"",
				queryParams:{},
				onLoadSuccess:function(){
				},
				onDblClickRow: function(row){
					
			    },
			    onSelect: function(row){
	  			
	   	  	  	},
				columns : [[ 
					{field:'_checkbox',checkbox:true},
					
				]]
	 		});
	  	});
  	</script>

<div id="workerResourceEdit" class="easyui-layout" data-options="fit:true">  
	<div data-options="region:'center',split:true,title:''" border="false">
		<div id="workerGridBar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="workerResourceEdit.addWorker()">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="workerResourceEdit.deleteWorker()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="workerResourceEdit.editWorker()">保存</a>
		</div>
		
		<div id="resourceGridBar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="workerResourceEdit.addResource()">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="workerResourceEdit.deleteResource()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="workerResourceEdit.editResource()">保存</a>
		</div>
		
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',split:true,title:''" style="height:300px" border="false">
				<table id="workerGrid"></table>
			</div>
			
			<div data-options="region:'center',split:true,title:''" border="false">
				<table id="resourceGrid"></table>
			</div>
		</div>
	</div>
</div>
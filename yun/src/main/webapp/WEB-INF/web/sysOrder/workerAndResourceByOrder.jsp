<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
  	<script type="text/javascript">
  		var workerDataGrid;
  		var resourceDataGrid;
  		
  		var sexObj = new Function("return " + '${hashMap.sexObj}')();
  		var supplierObj = new Function("return " + '${hashMap.supplierObj}')();
  		
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
	          	url:"sysOrderAction!workerDataByOrder.act",
				queryParams:{"sysOrder.id":'${sysOrder.id}'},
				onDblClickRow: function(index,row){
 	      		  	
			    },
			    onLoadSuccess:function(){
			    	
				},
	          	onSelect: function(index,row){
	          		
	   	  	  	},
	   	  	  	
	   	  		//frozenColumns:[[]],
 	 			columns:[[
 	 				{field:'ck',checkbox:true},
 	        		{field:'workTime',title:"工时(天)",width:80,sortable:true
		   	  		   
   	  		 		},
 	        		{field:'name',title: "姓名",width:100,sortable:true
 	 					
 	 				},
 	 				{field:'sex',title: "性别",width:80,sortable:true
 	 					,formatter:function(value, rowData) {
 	 						return sexObj[value];
 	 					} 
 	 				},
 	 				{field:'wages',title:"日薪",width:80,sortable:true,align:'right'
 	 					,formatter:function(value, rowData) {
 	 						if(value == 0){
 	 							return 0;
 	 						}else{
 	 							return value;
 	 						}
 	 					} 
 	 		        },
 	 				{field:'phoneNum',title: "手机号码",width:100,sortable:true
 	 					
 	 				},
 	 				{field:'telNum',title: "电话号码",width:100,sortable:true
 	 					
 	 				},
 	 				{field:'workAge',title: "工龄",width:100,sortable:true,
 	 		        	formatter:function(value, rowData) {
 	 						if(value==0){
 	 							return '';
 	 						}else{
 	 							return value;
 	 						}
 	 					} 
 	 				},
 	 				{field:'age',title: "年龄",width:100,sortable:true,
 	 		        	formatter:function(value, rowData) {
 	 						if(value==0){
 	 							return '';
 	 						}else{
 	 							return value;
 	 						}
 	 					} 
 	 				},
 	 				{field:'company',title: "所在公司",width:450,sortable:true
 	 					
 	 				}
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
	          	url:"sysOrderAction!resourceDataByOrder.act",
				queryParams:{"sysOrder.id":'${sysOrder.id}'},
				onLoadSuccess:function(){
				},
				onDblClickRow: function(row){
					
			    },
			    onSelect: function(row){
	  			
	   	  	  	},
   	  		    columns:[[
   	  		    	{field:'ck',checkbox:true},
   	  		    	{field:'quantity',title:"数量",width:80,sortable:true
		   	  		   
   	  		 		},
   	  				{field:'keyWord',title:"产品关键词",width:200,sortable:true
   	  		        	
   	  		        },
   	  		        {field:'rsrcName',title:"产品名称",width:80,sortable:true
   	  		        	
   	  		        },
   	  		        {field:'abbreviaName',title:"产品简称",width:80,sortable:true
   	  		        	
   	  		        },
   	  		        {field:'salePrice',title:"销售价格",width:80,sortable:true,align:'right'
   	  		        	,formatter:function(value, rowData) {
   	  						if(value == 0){
   	  							return 0;
   	  						}else{
   	  							return value;
   	  						}
   	  					} 
   	  		       	},
   	  		       	{field:'brand',title:"品牌",width:80,sortable:true
   	  		        	
   	  		       	},
					{field:'supplierId',title:"供应商名称",width:100,sortable:true,
						formatter:function(value, rowData) {
							if(value == 0 ){
								return "";
							}
							return supplierObj[value];
						} 
					},
					{field:'supplier',title:"供应商联系人",width:100,sortable:true
						
					},
					{field:'supplierPhone',title:"联系人手机",width:100,sortable:true
					
					},
					{field:'supplierTel',title:"供应商电话",width:100,sortable:true
						
					},
					{field:'supplierAddress',title:"供应商地址",width:450,sortable:true
						
					}     
   	  		    ]],
	 		});
	  	});
  	</script>

<div id="orderWorkerResourceShow" class="easyui-layout" data-options="fit:true">  
	<div data-options="region:'center',split:true,title:''" border="false">
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
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
  	<script type="text/javascript">
  		var workerDataGrid;
  		var resourceDataGrid;
  		
  		var workerEdit=undefined;
  		var resourceEdit=undefined;
  		
  		var workerResourceEdit={};
  		
  		var sexObj = new Function("return " + '${hashMap.sexObj}')();
  		var supplierObj = new Function("return " + '${hashMap.supplierObj}')();
  		var brandObj = new Function("return " + '${hashMap.brandObj}')();
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
	          	url:"sysOrderAction!workerDataByFlow.act",
				queryParams:{"sysOrderFlow.id":'${sysOrderFlow.id}'},
				onDblClickRow: function(index,row){
 	      		  	
			    },
			    onLoadSuccess:function(){
			    	
				},
				onAfterEdit:function(rowIndex,rowData,changes){
			    	$.post("sysOrderAction!updateOrderWorkerTime.act",
			    			{"sysOrderWorker.workTime":rowData.workTime,"sysOrderWorker.id":rowData.orderWorkerId},
		       			 function(data){
		      			 	handlerResult(data,
		      			 		function(json){
									$show(json.message);
								},
								function(json){
									$show(json.message);
								}
							);
		       			}
			    	);
		        },
		        onClickCell:function(index, field, value) {
			    	if("workTime" == field){
				    	if(workerEdit!=undefined){
				    		if(workerDataGrid.datagrid("validateRow",workerEdit)){
				    			workerDataGrid.datagrid("endEdit",workerEdit);
				    		}else{
								$show("请正确输入编辑行数据!");
								return false;	    		
				    		}
				    	}
				    	workerDataGrid.datagrid('editCell',{index:index,field:field});
			            workerEdit = index;
			    	}
		        },
	          	onSelect: function(index,row){
	          		
	   	  	  	},
	   	  		//frozenColumns:[[]],
 	 			columns:[[
 	 				{field:'ck',checkbox:true},
 	        		{field:'workTime',title:"工时(天)",width:80,sortable:true
		   	  		    ,editor:{
			   	  		   type:"numberspinner",
							options:{
								required:true,    
	    						increment:1,
	    						min:0.01,
	    						max:9999999.99,
	    						precision:2
					 		}
		   	        	}
   	  		 		},
 	        		{field:'name',title: "姓名",width:100,sortable:true
 	 					
 	 				},
 	 				{field:'sex',title: "性别",width:80,sortable:true,
 	 					formatter:function(value, rowData) {
 	 						return sexObj[value];
 	 					} 
 	 				},
 	 				{field:'wages',title:"日薪",width:80,sortable:true,align:'right',
 	 					formatter:function(value, rowData) {
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
	          	url:"sysOrderAction!resourceDataByFlow.act",
				queryParams:{"sysOrderFlow.id":'${sysOrderFlow.id}'},
				onLoadSuccess:function(){
				},
				onDblClickRow: function(row){
					
			    },
			    onSelect: function(row){
	  			
	   	  	  	},
	   	  	  	onAfterEdit:function(rowIndex,rowData,changes){
			    	$.post("sysOrderAction!updateOrderResourceQuantity.act",
			    			{"sysOrderResource.quantity":rowData.quantity,"sysOrderResource.id":rowData.orderResourceId},
		       			 function(data){
		      			 	handlerResult(data,
		      			 		function(json){
									$show(json.message);
								},
								function(json){
									$show(json.message);
								}
							);
		       			}
			    	);
		        },
		        onClickCell:function(index, field, value) {
			    	if("quantity" == field){
				    	if(resourceEdit!=undefined){
				    		if(resourceDataGrid.datagrid("validateRow",resourceEdit)){
				    			resourceDataGrid.datagrid("endEdit",resourceEdit);
				    		}else{
								$show("请正确输入编辑行数据!");
								return false;	    		
				    		}
				    	}
				    	resourceDataGrid.datagrid('editCell',{index:index,field:field});
			            resourceEdit = index;
			    	}
		        },
	   	  		//frozenColumns:[[ ]],
   	  		    columns:[[
   	  		    	{field:'ck',checkbox:true},
   	  		        {field:'quantity',title:"数量",width:80,sortable:true
		   	  		    ,editor:{
			   	  		   type:"numberspinner",
							options:{
								required:true,    
	    						increment:1,
	    						min:0.01,
	    						max:9999999.99,
	    						precision:2
					 		}
		   	        	}
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
   	  		       	{field:'brandId',title:"品牌",width:80,sortable:true,
   	  		       		formatter:function(value, rowData) {
							if(value == 0 ){
								return "";
							}
							return brandObj[value];
						}
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
	  	
	  	
	  	workerResourceEdit = {
	  		deleteResource:function(){
				var checks = resourceDataGrid.datagrid("getChecked");
				if(checks.length == 0){
					$alert("请勾选需要删除的产品!");
					return false;
				}
				$.messager.confirm('确认','确认要删除勾选的产品吗？',function(r){    
				    if (r){
				    	var ids = [];
				    	var deletes = [];
				    	$.each(checks,function(i,n){
				    		ids.push(n.orderResourceId);
				    		deletes.push(n);
				    	});
				        $.post("sysOrderAction!deleteOrderResource.act",
				        	{"ids":ids.join(",")},
				        	function(data){
							handlerResult(data,
					    		function(rs){
									$show(rs.message);
									resourceDataGrid.datagrid("reload");
								},
								function(rs){
									$alert(rs.message);
								}
							);  
						},"json");
				    }    
				});
			},
			
			deleteWorker:function(){
				var checks = workerDataGrid.datagrid("getChecked");
				if(checks.length == 0){
					$alert("请勾选需要删除的工人!");
					return false;
				}
				$.messager.confirm('确认','确认要删除勾选的工人吗？',function(r){    
				    if (r){
				    	var ids = [];
				    	var deletes = [];
				    	$.each(checks,function(i,n){
				    		ids.push(n.orderWorkerId);
				    		deletes.push(n);
				    	});
				        $.post("sysOrderAction!deleteOrderWorker.act",
				        	{"ids":ids.join(",")},
				        	function(data){
							handlerResult(data,
					    		function(rs){
									$show(rs.message);
									workerDataGrid.datagrid("reload");
								},
								function(rs){
									$alert(rs.message);
								}
							);  
						},"json");
				    }    
				});
			},
	  	
	  		editWorker:function(){
	  			if(workerEdit!=undefined){
					if(workerDataGrid.datagrid("validateRow",workerEdit)){
						workerDataGrid.datagrid("endEdit",workerEdit);
						workerEdit = undefined;
					}
				}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
    			}
	  		},
	  		editResource:function(){
	  			if(resourceEdit!=undefined){
					if(resourceDataGrid.datagrid("validateRow",resourceEdit)){
						resourceDataGrid.datagrid("endEdit",resourceEdit);
						resourceEdit = undefined;
					}
				}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
    			}
	  		},
	  		addWorker:function(){
	  			//添加页面
				var dialog = $('<div id="addWorker"></div>').dialog({    
					href : "sysOrderAction!selectWorker.act",
					width:880,
					height:500,
					title:"添加工人",
					method:'post',
					queryParams:{"sysOrderFlow.id":'${sysOrderFlow.id}'},
					modal:true,
					resizable:true,
					buttons:[
						{
							text:"确定",
							iconCls:'icon-ok',
							handler:function(){
								var checkeds = $sysWorkerDatagrid.datagrid("getChecked");
									if(checkeds.length == 0){
									$alert("请勾选工人!");
									return false;
								}
								var addIds = [];
								
								$.each(checkeds,function(i,n){
									//addIds.push({"id":n.id,"workTime":n.workTime});
									addIds.push(n.id);
								});
								var loading=new Some.loading();
								loading.show();
								$.post("sysOrderAction!addWorkerToFlow.act",
								{"jsonStr":addIds.join(","),//Some.util.jsonToStr(addIds),
								"sysOrderFlow.id":'${sysOrderFlow.id}'},
							  			function(data){
										loading.close();
						   	  			handlerResult(data,
										function(rs){
											$show(rs.message);
											dialog.close();
											workerDataGrid.datagrid("reload");
										},
										function(rs){
											$alert(rs.message);
										}
									);
								},"json");
							}
						},
						{
							text:"取消",
							iconCls:'icon-cancel',
							handler:function(){
								dialog.dialog("destroy");
							}
						}
					],
					onClose:function(){
						$(this).dialog("destroy");
					}
				});
	  		},
	  	
	  	
	  		addResource:function(){
	  			//添加页面
				var dialog = $('<div id="addResource"></div>').dialog({    
					href : "sysOrderAction!selectResource.act",
					width:880,
					height:500,
					title:"添加产品",
					method:'post',
					queryParams:{"sysOrderFlow.id":'${sysOrderFlow.id}'},
					modal:true,
					resizable:true,
					buttons:[
						{
							text:"确定",
							iconCls:'icon-ok',
							handler:function(){
								var checkeds =  $packageResourceGrid.datagrid("getChecked");
									if(checkeds.length == 0){
									$alert("请勾选产品!");
									return false;
								}
								var addIds = [];
								
								$.each(checkeds,function(i,n){
									//addIds.push({"id":n.id,"quantity":n.quantity});
									addIds.push(n.id);
								});
								var loading=new Some.loading();
								loading.show();
								$.post("sysOrderAction!addResourceToFlow.act",
									{"jsonStr":addIds.join(","),//Some.util.jsonToStr(addIds),
									"sysOrderFlow.id":'${sysOrderFlow.id}'},
							  			function(data){
										loading.close();
						   	  			handlerResult(data,
										function(rs){
											$show(rs.message);
											dialog.close();
											resourceDataGrid.datagrid("reload");
										},
										function(rs){
											$alert(rs.message);
										}
									);
								},"json");
							}
						},
						{
							text:"取消",
							iconCls:'icon-cancel',
							handler:function(){
								dialog.dialog("destroy");
							}
						}
					],
					onClose:function(){
						$(this).dialog("destroy");
					}
				});
	  		}
	  	}
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
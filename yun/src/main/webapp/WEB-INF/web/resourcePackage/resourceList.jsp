<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $resourceGrid;
var attrJsons={};
var resourceOperation = {};

$(function(){
 	columns=[];
 	<s:iterator value="attribCatalogs" id="attribCatalog" status="list">
		<s:if test="#attribCatalog.showInListView==1">
			columns.push(
				{field:"${attribCatalog.id}",title:"${attribCatalog.rsrcAttribName}",width:150,
					sortable:true,
					editor:{
						<s:if test="#attribCatalog.controlTypeId==104">
							<s:if test="#attribCatalog.dataTypeId==2">
								type:"numberbox",
								options:{
									min:0,
									max:${attribCatalog.max},
									precision:${attribCatalog.dataPrecision}
						 		}
							</s:if>
							
							<s:else>
								type:"textbox",
								options:{
									required:false,
									validType:['length[0,${attribCatalog.dataLength}]','illegal']
						 		}
							</s:else>
						</s:if>
						
						<s:if test="#attribCatalog.controlTypeId==105">
							type:"combobox",
							options:{
								valueField:'id',
								textField:'value',
								data:${attribCatalog.arrDefaultValues}
							}
						</s:if>
						
						<s:if test="#attribCatalog.controlTypeId==106">
							type:"combobox",
							options:{
								valueField:'id',
								textField:'value',
								multiple:true,
								data:${attribCatalog.arrDefaultValues}
							}
						</s:if>
						
						<s:if test="#attribCatalog.controlTypeId==107">
							type:"datebox"
						</s:if>
				 	},
					formatter:function(value,row,index){
						return value;
					}
				}
			);
		</s:if>
	</s:iterator>
	
	columns.push(
		{field:'supplierId',title:"供应商名称",width:100,sortable:true
			
        },
        {field:'supplier',title:"供应商联系人",width:100,sortable:true
        	
        },
        {field:'supplierTel',title:"联系人手机",width:100,sortable:true
        
        },
        {field:'supplierPhone',title:"供应商电话",width:100,sortable:true
        	
        }
        ,{field:'supplierAddress',title:"供应商地址",width:450,sortable:true
        	
        }
	);
	
 	$resourceGrid=$("#resourceGrid").datagrid({
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
        url:"resourceAction!resourceListData.act",
        toolbar:"#resource_operation_bar",
        queryParams:{"sysRsRcCatalog.id":'${sysRsRcCatalog.id}'},
        onBeforeLoad:function(){
        },
        onLoadSuccess:function(data){
        },
        frozenColumns:[[
	        {field:'ck',checkbox:true},
	        {field:'rsrcName',title:"产品名称",width:80,sortable:true,
	        	editor:{
	        		type:"textbox",
	        		options:{required:false,validType:['length[1,30]','illegal']}
	        	}
	        },
	        {field:'abbreviaName',title:"产品简称",width:80,sortable:true,
	        	editor:{
	        		type:"textbox",
	        		options:{validType:['length[1,30]','illegal']}
	        	}
	        },
	        {field:'purchasePrice',title:"采购价格",width:80,sortable:true,align:'right',
	        	editor:{
	        		type:"numberbox",
					options:{
						required:true,
						min:0,
						max:9999999.99,
						precision:2
			 		}
	        	}
	        },
	        {field:'salePrice',title:"销售价格",width:80,sortable:true,align:'right',
	        	editor:{
	        		type:"numberbox",
					options:{
						required:true,
						min:0,
						max:9999999.99,
						precision:2
			 		}
	        	}
	       	},
	       	{field:'brand',title:"品牌",width:80,sortable:true,
	        	editor:{
	        		type:"textbox",
	        		options:{validType:['length[1,30]','illegal']}
	        	}
	       	}
        ]],
	    columns:[columns],
	    onDblClickCell:function(index, field, value) {
        },
	    onAfterEdit:function(rowIndex,rowData,changes){
	    	$.post("resourceAction!saveOrUpdateResourceGrid.act",
	    			{"resourceJsonStr":Some.util.jsonToStr(rowData),"sysRsRcCatalog.id":${sysRsRcCatalog.id}},
       			 function(data){
      			 	handlerResult(data,
      			 		function(json){
							$show(json.message);
							$resourceGrid.datagrid('updateRow',{
								index: rowIndex,
								row: {
									id:json.data.id,
									rsrcCode:json.data.rsrcCode
								}
							});
						},
						function(json){
							$show(json.message);
						}
					);
       			}
	    	);
        }
	});
 	
 	$("#resourceGrid").parent(".datagrid-view").keyEvent({
		keyCode:13,
		handler:function(event){
			resourceOperation.updateResource();
			event.preventDefault();
		}
	});
 	
 	$("#searchForm").keyEvent({
		keyCode:13,
		handler:function(event){
			resourceOperation.search();
			event.preventDefault();
		}
	});
});

resourceOperation = {
	deleteResource:function(){
		var checks = $resourceGrid.datagrid("getChecked");
		if(checks.length == 0){
			$alert("请勾选需要删除的产品!");
			return false;
		}
		$.messager.confirm('确认','确认要删除勾选的产品吗？',function(r){    
		    if (r){
		    	var ids = [];
		    	var deletes = [];
		    	$.each(checks,function(i,n){
		    		ids.push(n.id);
		    		deletes.push(n);
		    	});
		        $.post("resourcePackageAction!deleteResource.act",
		        	{"ids":ids.join(",")},
		        	function(data){
					handlerResult(data,
			    		function(rs){
							$show(rs.message);
							$.each(deletes,function(i,n){
								var deleteIndex = $resourceGrid.datagrid("getRowIndex",n);
								$resourceGrid.datagrid("deleteRow",deleteIndex);
					    	});
						},
						function(rs){
							$alert(rs.message);
						}
					);  
				},"json");
		    }    
		});
	},
	
	addResource:function(){
		//添加页面
		var dialog =$('<div id="resourceSelect"></div>').dialog({    
			href : "resourceAction!resourceSelect.act",
			width:600,
			height:350,
			title:"新增产品",
			method:'post',
			queryParams:{"sysRsrcPackage.id":${sysRsrcPackage.id}},
			modal:true,
			resizable:true,
			buttons:[{
				text:"确定",
				iconCls:'icon-ok',
				handler:function(){
					
				}
			},{
				text:"取消",
				iconCls:'icon-cancel',
				handler:function(){
					dialog.dialog("destroy");
				}
			}],
			onClose:function(){
				$(this).dialog("destroy");
			}
		});
		*/
	},
	
	search:function(){
		var searchData = {};
		searchData["rsrcCode"] = $("#rsrcCode").val();
		searchData["rsrcName"] = $("#rsrcName").val();
		searchData["abbreviaName"] = $("#abbreviaName").val();
		searchData["brand"] = $("#brand").val();
		searchData["supplierName"] = $("#supplierName").val();
		
		var $attrs = $("input[id^='attrib_']");
		$.each($attrs,function(i,n){
			var id = $(n).attr("id").substring(7,$(n).attr("id").length);
			searchData[id] = $(n).val();
			if(isNaN(id)){
				searchData[id] = $(n).datebox("getValue");
			}
		})
		$resourceGrid.datagrid("reload",
			{
				"resourceJsonStr":Some.util.jsonToStr(searchData),
				"sysRsRcCatalog.id":'${sysRsRcCatalog.id}'
			}
		);
	},
	
	reset:function(){
		$("#rsrcCode").val('');
		$("#rsrcName").val('');
		$("#abbreviaName").val('');
		$("#brand").val('');
		$("#supplierName").val('');
		var $attrs = $("input[id^='attrib_']");
		$.each($attrs,function(i,n){
			var id = $(n).attr("id").substring(7,$(n).attr("id").length);
			if(isNaN(id)){
				$(n).datebox("setValue",'');
			}else{
				$(n).val('');
			}
		})
		resourceOperation.search();
	}
};
</script>
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="searchForm" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
		<div class="search-div">
			<lable for="">产品编号</lable>
			<div class="select">
				<input  type="text"  id="rsrcCode"/>
			</div>
		</div>
		<div class="search-div">
			<lable for="">产品名称</lable>
			<div class="select">
				<input  type="text"  id="rsrcName"/>
			</div>
		</div>
		<div class="search-div">
			<lable for="">产品简称</lable>
			<div class="select">
				<input  type="text"  id="abbreviaName"/>
			</div>
		</div>
		<div class="search-div">
			<lable for="">品牌</lable>
			<div class="select">
				<input  type="text"  id="brand"/>
			</div>
		</div>
		
		<!-- 
		<div class="search-div">
			<lable for="">供应商名称</lable>
			<div class="select">
				<input  type="text"  id="supplierName"/>
			</div>
		</div>
		 -->
		
		<div class="search-div">
			<label>供应商名称</label>
	       	<s:select id="sysSupplierId" style="height:22px"
	       		list="sysSuppliers"
		       	listKey="id"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		
		
		
		<s:iterator value="attribCatalogs" id="attribCatalog" status="list">
			<s:if test="#attribCatalog.showInFinder==1">
				<s:if test="#attribCatalog.controlTypeId==104||
							#attribCatalog.controlTypeId==105||
							#attribCatalog.controlTypeId==106">
					<div class="search-div">
						<lable for="">${attribCatalog.rsrcAttribName}</lable>
						<div class="select">
							<input  id="attrib_${attribCatalog.id}" type="text"/>
						</div>
					</div>
				</s:if>	
				<s:if test="#attribCatalog.controlTypeId==107">
					<div class="search-div" style = "width:488px;max-width: 488px;">
						<lable for="">${attribCatalog.rsrcAttribName}</lable>
						<div class="select" style="width: 400px;">
							<input  id="attrib_${attribCatalog.id}_start" class="easyui-datebox" />
							-
							<input  id="attrib_${attribCatalog.id}_end" class="easyui-datebox" />
						</div>
					 </div>
				</s:if>
			</s:if>
		</s:iterator>
		<div class="search-div">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="resourceOperation.search()">查询</a> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="resourceOperation.reset()">重置</a>	
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="resourceGrid"></table>
	</div>
</div>

<div  id="resource_operation_bar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="resourceOperation.addResource()">新增</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="resourceOperation.deleteResource()">删除</a>
</div> 
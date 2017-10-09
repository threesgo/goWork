<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $resourceGrid;
var attrJsons={};

var resourceEdit = undefined;
var resourceOperation = {};

$(function(){
 	columns=[];
 	<s:iterator value="attribCatalogs" id="attribCatalog" status="list">
		<s:if test="#attribCatalog.showInListView==1">
			columns.push(
				{field:"${attribCatalog.id}",title:"${attribCatalog.rsrcAttribName}",width:120,
					sortable:true,
					editor:{
						<s:if test="#attribCatalog.controlTypeId==104">
							<s:if test="#attribCatalog.dataTypeId==2">
								type:"numberbox",
								options:{
									precision:${attribCatalog.dataLength}
						 		}
							</s:if>
							
							<s:else>
								type:"textbox",
								options:{
									<s:if test="#attribCatalog.dataLength!=null">
										required:{validType:['length[1,${attribCatalog.dataLength}]','illegal']}
									</s:if>
					 				<s:else>
										required:{validType:['length[1,256]','illegal']}
									</s:else>
						 		}
							</s:else>
						</s:if>
						
						<s:if test="#attribCatalog.controlTypeId==105">
							type:"combobox",
							valueField:'id',
							textField:'value',
							data:'${attribCatalog.arrDefaultValues}'
						</s:if>
						
						<s:if test="#attribCatalog.controlTypeId==106">
							type:"combobox",
							valueField:'id',
							textField:'value',
							multiple:true,
							data:'${attribCatalog.arrDefaultValues}'
						</s:if>
						
						<s:if test="#attribCatalog.controlTypeId==107">
							type:"datebox"
						</s:if>
				 	},
					formatter:function(data,row,index){
						return value;
					}
				}
			);
		</s:if>
	</s:iterator>
 	$resourceGrid=$("#resourceGrid").datagrid({
        fitColumns:false,
        striped:true,
        singleSelect:false,
        fit:true,
        nowrap:true,
        idField:"id",
        remoteSort:false,
        multiSort:true,
        rownumbers : true,
        selectOnCheck:true,
        checkOnSelect:false,
        pageSize:20,
        pageList:[20,50,100,150,200],
        pagination:true,
        url:"resourceAction!resourceList.act",
        toolbar:"#resource_operation_bar",
        queryParams:{"sysRsRcCatalog.id":'${sysRsRcCatalog.id}'},
        onBeforeLoad:function(){
        },
        onLoadSuccess:function(data){
        },
        frozenColumns:[[
	        {field:'ck',checkbox:true},
	 		{field:'rsrcTypeName',title:"产品类别",width:80,sortable:true},
	 		{field:'rsrcOrgName',title:"产品类型",width:80,sortable:true},
	 		{field:'workType',title:"工程工种",width:80,sortable:true},
	  		{field:'rsrcCode',title:"产品代号",width:80,sortable:true,
				editor:{
					type:"textbox",
					options:{required:true,validType:['length[1,10]','illegal']}
				}
			},
	        {field:'rsrcName',title:"产品名称",width:80,sortable:true,
	        	editor:{
	        		type:"textbox",
	        		options:{validType:['length[1,30]','illegal']}
	        	}
	        },
	        {field:'purchasePrice',title:"采购价格",width:80,sortable:true},
	        {field:'salePrice',title:"销售价格",width:80,sortable:true}
        ]],
	    columns:[columns],
	    
	    onDblClickRow: function(index,row){
	    	resourceEdit=index;
  			$(this).datagrid('beginEdit',index);
	    },
	    
	});
 	
 	$("#resourceGrid").parent(".datagrid-view").keyEvent({
		keyCode:13,
		handler:function(event){
			if(resourceEdit!=undefined){
				$resourceGrid.datagrid("endEdit",resourceEdit);
				resourceEdit=undefined;
  	  		}
		}
	});
 	
 	$("#selectDiv").keyEvent({
		keyCode:13,
		handler:function(event){
			resourceOperation.search();
			event.preventDefault();
		}
	});
});

resourceOperation = {
	deleteResource:function(){
		$.messager.confirm('确认','确认要删除勾选的资源吗？',function(r){    
		    if (r){
		        $.post("resourceAction!deleteResource.act",
		        	{"ids":},
		        	function(data){
					handlerResult(data,
			    		function(rs){
							$show(rs.message);
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
	},
	
	editResource:function(){
		//不需要页面，直接表格编辑
	},
	
	updateResource:function(){
		
	},
	
	cancelEdit:function(){
		
	},
	
	search:function(){
		
	},
	
	reset:function(){
		
	},
	
	importResource:function(){
		
	},
	
	exportResource:function(){
		
	}
};
</script>
<style>
	.resource{
		padding:3px;
		float:left !important;
		height: auto !important;
		width: 100%;
	}
	.resource div{
		margin-left: 20px;
		height: 30px;
		margin-top:3px;
		float: left;
		*width: auto;
		*float: left !important;
		max-width: 300px;
	}
	.resource div.edit-item-resource{
		width: 150px;
		height: 20px;
	}
	.resource div label{
		float: left;
	}
	
	.resource div>input,.resource div>select,.resource div>ul,.resource div>span {
		float: right;
	}
</style>

<div class="easyui-layout" data-options="fit:true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false,split:true" style="height: 130px; overflow: hidden;background-color: #F8F8F8" >
		<div id="tools_div" class="resource variable">
			<div id="selectDiv">
				<form id="searchForm">
					<div class="attrs">
						<lable for="">产品代号：</lable><input  id="rsrcCode"  style="width:125px;"/>
					</div>
					<div class="attrs">
						<lable for="">产品名称：</lable><input id="rescName"  style="width:125px;"/>
					</div>
					<div class="attrs">
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="resourceOperation.search()">查询</a> 
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="resourceOperation.reset()">重置</a>	
					</div>
				</form>
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="resourceGrid"></table>
	</div>
</div>

<div  id="resource_operation_bar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="resourceOperation.addResource()">新增</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="resourceOperation.editResource();">编辑</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-quxiao', plain:true" onclick="resourceOperation.cancelEdit()">取消</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" onclick="resourceOperation.updateResource()">保存</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="resourceOperation.deleteResource()">删除</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-download', plain:true" onclick="resourceOperation.importResource()">导入</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:true" onclick="resourceOperation.exportResource()">导出</a>
</div> 
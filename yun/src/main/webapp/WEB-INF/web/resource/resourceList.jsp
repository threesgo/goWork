<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $resourceGrid;
var attrJsons={};

var resourceEdit = undefined;
var resourceOperation = {};
var addId = 0;
var isCancelOrSave = 1;

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
							valueField:'id',
							textField:'value',
							data:${attribCatalog.arrDefaultValues}
						</s:if>
						
						<s:if test="#attribCatalog.controlTypeId==106">
							type:"combobox",
							valueField:'id',
							textField:'value',
							multiple:true,
							data:${attribCatalog.arrDefaultValues}
						</s:if>
						
						<s:if test="#attribCatalog.controlTypeId==107">
							type:"datetimebox"
						</s:if>
				 	},
					formatter:function(value,row,index){
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
        url:"resourceAction!resourceListData.act",
        toolbar:"#resource_operation_bar",
        queryParams:{"sysRsRcCatalog.id":'${sysRsRcCatalog.id}'},
        onBeforeLoad:function(){
        },
        onLoadSuccess:function(data){
        },
        frozenColumns:[[
	        {field:'ck',checkbox:true},
	 		//{field:'rsrcTypeName',title:"产品类别",width:80,sortable:true},
	 		//{field:'rsrcOrgName',title:"产品类型",width:80,sortable:true},
	 		{field:'workType',title:"工种",width:80,sortable:true,
	 			editor:{
                     type:'combobox',
                     options:{
                          	  valueField:'value', 
                              textField:'name',
                              editable:false,
                              //url:"bopTmPbomInstanceAction!getBomInstanceByProcess.act?processId="+${hashMap.processId},
                              data:${hashMap.flowListArr},
                              panelHeight:'auto',
                              onLoadSuccess: function () {
                            	 
                              }	
                 	 	}
					},
				formatter : function(value, rowData) {
					var flowListObj = new Function("return " + '${hashMap.flowListObj}')();
					return flowListObj[value];
				} 
	 		},
	  		{field:'rsrcCode',title:"产品代号",width:80,sortable:true,
				editor:{
					type:"textbox",
					options:{required:true,validType:['length[1,10]','illegal']}
				}
			},
	        {field:'rsrcName',title:"产品名称",width:80,sortable:true,
	        	editor:{
	        		type:"textbox",
	        		options:{required:true,validType:['length[1,30]','illegal']}
	        	}
	        },
	        {field:'abbreviaName',title:"产品简称",width:80,sortable:true,
	        	editor:{
	        		type:"textbox",
	        		options:{validType:['length[1,30]','illegal']}
	        	}
	        },
	        {field:'purchasePrice',title:"采购价格",width:80,sortable:true,
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
	        {field:'salePrice',title:"销售价格",width:80,sortable:true,
	        	editor:{
	        		type:"numberbox",
					options:{
						required:true,
						min:0,
						max:9999999.99,
						precision:2
			 		}
	        	}
	       	}
        ]],
	    columns:[columns],
	    
	    onDblClickRow: function(index,row){
	    	if(resourceEdit!=undefined){
	    		if($resourceGrid.datagrid("validateRow",resourceEdit)){
	    			$resourceGrid.datagrid("endEdit",resourceEdit);
	    		}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
			resourceEdit=index;
 			$(this).datagrid('beginEdit',index);
	    },
	    onAfterEdit:function(rowIndex,rowData,changes){
	    	//if(isCancelOrSave==1){
		    	$.post("resourceAction!saveOrUpdateResourceGrid.act",
		    			{"resourceJsonStr":Some.util.jsonToStr(rowData),"sysRsRcCatalog.id":${sysRsRcCatalog.id}},
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
	    	//}
        }
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
		        $.post("resourceAction!deleteResource.act",
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
		if(resourceEdit!=undefined){
			if($resourceGrid.datagrid("validateRow",resourceEdit)){
				$resourceGrid.datagrid("endEdit",resourceEdit);
				resourceEdit = undefined;
			}else{
				$show("请正确输入编辑行数据!");
				return false;
			}
		}
		$resourceGrid.datagrid('insertRow',{
			index: 0,	// 索引从0开始
			row: {
				id:addId--,
				workType:1,
				rsrcCode:'',
				rsrcName:'',
				purchasePrice:0,
				salePrice:0
			}
		});
		$resourceGrid.datagrid("beginEdit",0);
		resourceEdit = 0;

		//$resourceGrid.datagrid("reload");
		/*
		//添加页面
		var node = resourceTypeTree.tree("getSelected");
		var dialog =$('<div id="addResource"></div>').dialog({    
			href : "resourceAction!addPage.act",
			width:600,
			height:350,
			title:"新增产品",
			method:'post',
			queryParams:{"sysRsRcCatalog.id":node.attributes.id},
			modal:true,
			resizable:true,
			buttons:[{
				text:"确定",
				iconCls:'icon-ok',
				handler:function(){
					$('#saveOrUpdate_resource').form({    
					    onSubmit: function(){  
					    },    
					    success:function(data){ 
					    	handlerResult(data,
					    		function(rs){
					    			$resourceGrid.datagrid("reload");
					    			dialog.dialog("destroy");
									$show(rs.message);
								},
								function(rs){
									$alert(rs.message);
								}
							);  
					    }    
					}).submit();    
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
	
	editResource:function(){
		//不需要页面，直接表格编辑
		var selected = $resourceGrid.datagrid("getSelected");
		if(null == selected){
			$alert("请单选产品行，进行编辑!");
			return false;
		}
		if(resourceEdit!=undefined){
    		if($resourceGrid.datagrid("validateRow",resourceEdit)){
    			$resourceGrid.datagrid("endEdit",resourceEdit);
    		}else{
				$show("请正确输入编辑行数据!");
				return false;	    		
    		}
		}
		var index = $resourceGrid.datagrid("getRowIndex",selected);
		$resourceGrid.datagrid("beginEdit",index);
		resourceEdit = index;
	},
	
	updateResource:function(){
		if(resourceEdit!=undefined){
			if($resourceGrid.datagrid("validateRow",resourceEdit)){
				$resourceGrid.datagrid("endEdit",resourceEdit);
				resourceEdit = undefined;
			}else{
				$show("请正确输入编辑行数据!");
				return false;	    		
    		}
		}
	},
	
	cancelEdit:function(){
		isCancelOrSave = 2;
		$resourceGrid.datagrid("refreshRow",resourceEdit);
		resourceEdit = undefined;
		isCancelOrSave = 1;
	},
	
	search:function(){
		var searchData = {};
		searchData["rsrcCode"] = $("#rsrcCode").val();
		searchData["rescName"] = $("#rescName").val();
		searchData["abbreviaName"] = $("#abbreviaName").val();
		
		var $attrs = $("input[id^='attrib_']");
		$.each($attrs,function(i,n){
			var id = $(n).attr("id").substring(7,$(n).attr("id").length);
			searchData[id] = $(n).val();
		})
		$resourceGrid.datagrid("reload",
			{
				"resourceJsonStr":Some.util.jsonToStr(searchData),
				"sysRsRcCatalog.id":'${sysRsRcCatalog.id}'
			}
		);
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
	#searchForm{
		padding:3px;
		float:left !important;
		height: auto !important;
		width: 100%;
	}
	#searchForm div{
		margin-left: 20px;
		height: 30px;
		margin-top:3px;
		float: left;
		*width: auto;
		*float: left !important;
		max-width: 300px;
	}
	#searchForm div.edit-item-resource{
		width: 150px;
		height: 20px;
	}
	#searchForm div label{
		float: left;
	}
	
	#searchForm div>input,#searchForm div>select,#searchForm div>ul,#searchForm div>span {
		float: right;
	}
</style>

<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="searchForm" data-options="region:'north',title:'查询条件',border:false,split:true" style="height: 130px; overflow: hidden;background-color: #F8F8F8" >
		<div class="attrs">
			<lable for="">产品代号：</lable><input  type="text"  id="rsrcCode"  style="width:125px;"/>
		</div>
		<div class="attrs">
			<lable for="">产品名称：</lable><input  type="text"  id="rescName"  style="width:125px;"/>
		</div>
		<div class="attrs">
			<lable for="">产品简称：</lable><input  type="text"  id="abbreviaName"  style="width:125px;"/>
		</div>
		<s:iterator value="attribCatalogs" id="attribCatalog" status="list">
			<s:if test="#attribCatalog.showInFinder==1">
				<div class="attrs">
					<lable for="">${attribCatalog.rsrcAttribName}：</lable>
					<input  id="attrib_${attribCatalog.id}"  
					
					<s:if test="#attribCatalog.controlTypeId==104">
						type="text"
					</s:if>
					
					<s:if test="#attribCatalog.controlTypeId==105">
						type="text"
					</s:if>
					
					<s:if test="#attribCatalog.controlTypeId==106">
						type="text"
					</s:if>
					
					<s:if test="#attribCatalog.controlTypeId==107">
						class="easyui-datetimebox"
					</s:if>
					style="width:125px;"/>
				 </div>
			</s:if>
		</s:iterator>
		<div class="attrs">
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
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="resourceOperation.editResource();">编辑</a>
	<!--
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-quxiao', plain:true" onclick="resourceOperation.cancelEdit()">取消</a>
	-->
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" onclick="resourceOperation.updateResource()">保存</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="resourceOperation.deleteResource()">删除</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-download', plain:true" onclick="resourceOperation.importResource()">导入</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:true" onclick="resourceOperation.exportResource()">导出</a>
</div> 
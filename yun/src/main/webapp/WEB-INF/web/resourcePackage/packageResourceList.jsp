<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $packageResourceGrid;
var packageResourceOperation = {};
$(function(){
 	columns=[];
 	/*
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
	*/
 	$packageResourceGrid=$("#packageResourceGrid").datagrid({
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
        url:"resourcePackageAction!packageResourceData.act",
        toolbar:"#package_resource_operation_bar",
        queryParams:{"sysRsRcPackage.id":'${sysRsRcPackage.id}'},
        onBeforeLoad:function(){
        },
        onLoadSuccess:function(data){
        },
        frozenColumns:[[
	        {field:'ck',checkbox:true},
	        {field:'rsrcName',title:"产品名称",width:80,sortable:true,
	        	
	        },
	        {field:'abbreviaName',title:"产品简称",width:80,sortable:true,
	        	
	        },
	        {field:'salePrice',title:"销售价格",width:80,sortable:true,align:'right',
	        	
	       	},
	       	{field:'brand',title:"品牌",width:80,sortable:true,
	        	
	       	}
        ]],
	    columns:[columns],
	    onDblClickCell:function(index, field, value) {
        },
	    onAfterEdit:function(rowIndex,rowData,changes){
	    	
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

packageResourceOperation = {
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
								var deleteIndex = $packageResourceGrid.datagrid("getRowIndex",n);
								$packageResourceGrid.datagrid("deleteRow",deleteIndex);
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
			width:800,
			height:450,
			title:"新增产品",
			method:'post',
			queryParams:{"sysRsRcPackage.id":'${sysRsRcPackage.id}'},
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
	},
	
	search:function(){
		var searchData = {};
		searchData["rsrcCode"] = $("#rsrcCode").val();
		searchData["rsrcName"] = $("#rsrcName").val();
		searchData["abbreviaName"] = $("#abbreviaName").val();
		searchData["brand"] = $("#brand").val();
		searchData["supplierId"] = $("#supplierId").val();
		$packageResourceGrid.datagrid("reload",
			{
				"resourceJsonStr":Some.util.jsonToStr(searchData)
			}
		);
	},
	
	reset:function(){
		$("#rsrcCode").val('');
		$("#rsrcName").val('');
		$("#abbreviaName").val('');
		$("#brand").val('');
		$("#supplierId").val('');
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
		
		<%--
		<div class="search-div">
			<label>供应商名称</label>
	       	<s:select id="supplierId" style="height:22px"
	       		list="sysSuppliers"
		       	listKey="id"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		 --%>
		
		<div class="search-div">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="packageResourceOperation.search()">查询</a> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="packageResourceOperation.reset()">重置</a>	
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="packageResourceGrid"></table>
	</div>
</div>

<div  id="package_resource_operation_bar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="packageResourceOperation.addResource()">新增</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="packageResourceOperation.deleteResource()">删除</a>
</div> 
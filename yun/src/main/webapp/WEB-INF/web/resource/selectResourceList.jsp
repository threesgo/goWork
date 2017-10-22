<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $selectResourceGrid;
var selectResourceOperation = {};
var supplierObj = new Function("return " + '${hashMap.supplierObj}')();

$(function(){
 	columns=[];
 	<s:iterator value="attribCatalogs" id="attribCatalog" status="list">
		<s:if test="#attribCatalog.showInListView==1">
			columns.push(
				{field:"${attribCatalog.id}",title:"${attribCatalog.rsrcAttribName}",width:150,
					sortable:true,
					formatter:function(value,row,index){
						return value;
					}
				}
			);
		</s:if>
	</s:iterator>
	
	columns.push(
		{field:'supplierId',title:"供应商名称",width:150,sortable:true,
			formatter:function(value, rowData) {
				if(value == 0 ){
					return "";
				}
				return supplierObj[value];
			} 
        },
        {field:'supplier',title:"供应商联系人",width:100,sortable:true
        	/*
        	,editor:{
        		type:"textbox",
        		options:{required:false,validType:['length[1,300]','illegal']}
        	}
        	*/
        },
        {field:'supplierPhone',title:"联系人手机",width:100,sortable:true
        	/*
        	,editor:{
        		type:"textbox",
        		options:{required:false,validType:['length[1,300]','illegal']}
        	}
        	*/
        },
        {field:'supplierTel',title:"供应商电话",width:100,sortable:true
        	/*
        	,editor:{
        		type:"textbox",
        		options:{required:false,validType:['length[1,80]','illegal']}
        	}
        	*/
        }
        ,{field:'supplierAddress',title:"供应商地址",width:450,sortable:true
        	/*
        	,editor:{
        		type:"textbox",
        		options:{required:false,validType:['length[1,300]','illegal']}
        	}
        	*/
        }
	);
	
 	$selectResourceGrid=$("#selectResourceGrid").datagrid({
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
        toolbar:"#selectResource_operation_bar",
        queryParams:{"sysRsRcCatalog.id":'${sysRsRcCatalog.id}'},
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
	        {field:'purchasePrice',title:"采购价格",width:80,sortable:true,align:'right',
	        	
	        },
	        {field:'salePrice',title:"销售价格",width:80,sortable:true,align:'right',
	        	
	       	},
	       	{field:'brand',title:"品牌",width:80,sortable:true,
	        	
	       	}
        ]],
	    columns:[columns]
	});
	
 	$("#selectSearchForm").keyEvent({
		keyCode:13,
		handler:function(event){
			selectResourceOperation.search();
			event.preventDefault();
		}
	});
});

selectResourceOperation = {
	search:function(){
		var searchData = {};
		searchData["rsrcCode"] = $("#selectSearchForm #rsrcCode").val();
		searchData["rsrcName"] = $("#selectSearchForm #rsrcName").val();
		searchData["abbreviaName"] = $("#selectSearchForm #abbreviaName").val();
		searchData["brand"] = $("#selectSearchForm #brand").val();
		searchData["supplierId"] = $("#selectSearchForm #supplierId").val();
		$resourceGrid.datagrid("reload",
			{
				"resourceJsonStr":Some.util.jsonToStr(searchData),
				"sysRsRcCatalog.id":'${sysRsRcCatalog.id}'
			}
		);
	},
	
	reset:function(){
		$("#selectSearchForm #rsrcCode").val('');
		$("#selectSearchForm #rsrcName").val('');
		$("#selectSearchForm #abbreviaName").val('');
		$("#selectSearchForm #brand").val('');
		$("#selectSearchForm #supplierId").val(0);
		selectResourceOperation.search();
	}
}
</script>
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="selectSearchForm" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
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
		<div class="search-div">
			<label>供应商</label>
	       	<s:select id="supplierId" style="height:22px"
	       		list="sysSuppliers"
		       	listKey="id"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		
		<div class="search-div">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="selectResourceOperation.search()">查询</a> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="selectResourceOperation.reset()">重置</a>	
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="selectResourceGrid"></table>
	</div>
</div>

<div  id="select_resource_operation_bar">
    
</div> 
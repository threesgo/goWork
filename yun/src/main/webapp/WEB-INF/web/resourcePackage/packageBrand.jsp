<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $catalogTab;
var $brandTab;

var brandTabEdit = undefined;
var packageBrand = {};
$(function(){
	$catalogTab=$("#catalogTab").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#catalog_tool_bar",
        url:"resourcePackageAction!allLastChildrenListData.act?sysRsRcPackage.id=${sysRsRcPackage.id}",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
        onSelect:function(index, row){
        	$brandTab.datagrid("loadData",row.brandArr);
        },
		onBeforeLoad:function(){
			
		},
		columns : [ [
			{
				field : 'name',
				title : "产品类别",
				width : '100%',
				sortable : false,
				sorter : function(a, b) {
					return a > b ? 1 : -1;
				}
			}
		]]
	});
	
	
	$brandTab=$("#brandTab").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        animate : true,
 		collapsible : true,
 		toolbar:"#brand_tool_bar",
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
		onBeforeLoad:function(){
		},
		onClickCell:function(index, field, value) {
	    	if("isCheck" == field){
		    	if(brandTabEdit!=undefined){
		    		$brandTab.datagrid("endEdit",brandTabEdit);
		    	}
		    	$brandTab.datagrid('editCell',{index:index,field:field});
	            brandTabEdit = index;
	    	}
        },
		columns : [ [
			{
				field : 'name',
				title : "品牌名称",
				width : '48%',
				sortable : false,
				sorter : function(a, b) {
					return a > b ? 1 : -1;
				}
			},
			{
				field : 'isCheck',
				title : "是否关联",
				width : '48%',
				editor:{
        			type:"checkbox",
        			options:{    
			            on: 1,    
			            off: 0   
			        }    
        		},
        		formatter:function(value,row,index){
	        		if(value == 0){
	        			return "";
	        		}else if(value == 1){
	        			return "√";
	        		}
	        	}
			}
		]]
	});
});

packageBrand = {
	endEdit:function(){
		if(brandTabEdit!=undefined){
    		$brandTab.datagrid("endEdit",brandTabEdit);
    	}
        brandTabEdit = undefined;	
	},
	exportRelation:function(){
		Some.util.newDownLoad({
			url:"resourcePackageAction!exportPackageBrand.act?sysRsRcPackage.id=${sysRsRcPackage.id}",
			handler:function(){
			}
		});
	},
	saveRelation:function(){
		var rows = $catalogTab.datagrid("getRows");
		$.post("resourcePackageAction!savePackageBrand.act",
	   		{"jsonStr":Some.util.jsonToStr(rows),
	   		"sysRsRcPackage.id":'${sysRsRcPackage.id}'},
			function(data){
				handlerResult(data,
	   			 	function(json){
						$show(json.message);
						$catalogTab.datagrid("reload");
					},
					function(json){
						$alert(json.message);
					}
				);
   			}
	   	);
	}
}
</script>
<div class="easyui-layout"  id="stepLayout" data-options="fit:true,border : false">
	<div data-options="region:'center',border:false,title:''">
		<table id="catalogTab"></table>
	</div>
	
	<div data-options="region:'east',border:true,title:'',split:true" style="width: 35%;">
		 <table id="brandTab"></table>
	</div>
</div>

<div id="catalog_tool_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" onclick="packageBrand.saveRelation()">保存</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:true" onclick="packageBrand.exportRelation()">导出</a>
</div>

<div id="brand_tool_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok', plain:true" onclick="packageBrand.endEdit()">确认编辑</a>
</div>

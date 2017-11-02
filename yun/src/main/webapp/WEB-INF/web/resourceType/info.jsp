<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 
<div id="type_info" class="easyui-layout" data-options="fit:true">  
	<div id="type_info_tool_bar">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" 
		onclick="resourceTypeOperation.save()">保存</a>
	</div>
	<div data-options="region:'center',split:true,title:' ',tools:'#type_info_tool_bar'" border="false">
		<form method="POST" id="form_type_info">
			<div class="fitem">  
		      	<label for="code">类型代号:</label>   
		      	<input name="sysRsRcCatalog.catalogCode" class="easyui-validatebox" id="orgCode" style="width:200px;"
			           data-options="{required:true,validType:['length[1,32]']}" value='${sysRsRcCatalog.catalogCode}'/>
		    </div>
		    
		    <div class="fitem">  
		      	<label for="name">类型名称:</label>   
		      	<input name="sysRsRcCatalog.catalogName" class="easyui-validatebox" id="orgName" style="width:200px;" 
			           data-options="{required:true,validType:['length[1,64]']}" value='${sysRsRcCatalog.catalogName}'/>
		    </div>
		    
		    <div class="fitem">  
		      	<label for="type">产品类别:</label>   
		        <select id="type" class="easyui-combobox" name="sysRsRcCatalog.catalogType" style="width:200px;">   
				    <option value="1">产品</option>
				    <option value="2">工人</option>   
				</select>   
		    </div>
		</form>
	</div>
</div>
 -->
<script type="text/javascript">
$(function(){
	$resourceTypeInfoTable=$("#resourceTypeInfoTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#resource_type_info_bar",
        url:"resourceTypeAction!infoData.act?sysRsRcCatalog.id=${sysRsRcCatalog.id}",
        animate : true,
 		collapsible : true,
  		//pagination:false, 
  		//pageSize:20,  
        //pageList:[10,20,50,100,150,200],
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'attrName',
			title : "属性名称",
			width : '48%',
			sortable : false,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		},
		{
			field : 'value',
			title : "属性值",
			width : '48%',
			sortable : false,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		}
		]]
	});
});
</script>
<table id="resourceTypeInfoTable"></table>
<div id="resource_type_info_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" 
		onclick="resourceTypeOperation.editType()">编辑</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" 
		onclick="resourceTypeOperation.deleteType()">删除</a>
</div>
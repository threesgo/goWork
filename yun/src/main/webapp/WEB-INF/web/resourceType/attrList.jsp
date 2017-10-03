<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,title:''" style="height:300px" border="false">
		<table id="extendsAttr"></table>
	</div>
	
	<div data-options="region:'center',split:true,title:''" border="false">
		<table id="attr"></table>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#attr').datagrid({
			data:ownAttrs,
			fit:true,
		    fitColumns:true, 
		    striped:true,
			singleSelect:true, 
			nowrap:false,
			columns:[[
				{field:'rsrcAttribCode',title:"<s:text name='attribute_code'/>",width:100},
				{field:'rsrcAttribName',title:"<s:text name='attribute_name'/>",width:100}			
			]],
			onSelect:function(index, rowData){
			}
		});

		$('#extendsAttr').datagrid({
			data:extendsAttrs,
		    fitColumns:true, 
		    striped:true,
			singleSelect:true, 
			nowrap:false,
			fit:true,
			columns:[[
				{field:'rsrcAttribCode',title:"<s:text name='extend_attribute_code'/>",width:100},
				{field:'rsrcAttribName',title:"<s:text name='extend_attribute_name'/>",width:100}
			]],
			onSelect:function(index, rowData){
				
			}
	    });
  	});
</script>
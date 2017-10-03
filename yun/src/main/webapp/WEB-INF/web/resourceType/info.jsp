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

<form method="POST" id="form_typeinfo"  class="variable edit-form" var="form_typeinfo">
	<div id="processFlowBar" style="height:auto !important;padding:10px;width: 100%;">
		<div class="fitem">  
	      	<label class="nameLeft"><s:property value="%{getText('resource_type_code')}"/>:</label>
	      	<input name="sysRcRsrcOrg.rsrcOrgCode" class="easyui-validatebox" id="orgCode"
		           data-options="{required:true,validType:['length[1,32]']}" value='${sysRcRsrcOrg.rsrcOrgCode}' disabled="disabled"/>
	    </div>
	    
	    <div class="fitem">  
	      	<label class="nameLeft"><s:property value="%{getText('resource_type_name')}"/>:</label>
	      	<input name="sysRcRsrcOrg.rsrcOrgName" class="easyui-validatebox" id="orgName"  
		           data-options="{required:true,validType:['length[1,64]']}" value='${sysRcRsrcOrg.rsrcOrgName}' disabled="disabled"/>
	    </div>
	    
	    <s:if test="null!=parentSysRcRsrcOrg">
		    <div class="fitem">  
		      	<label class="nameLeft"><s:property value="%{getText('resource_type')}"/>:</label>
		      		<%--<s:if test="parentSysRcRsrcOrg.parentId==0">
						<s:select  list="resourceTypeList" id="orgType"
							headerKey="0" headerValue="--%{getText('please_select')}--"
				       		listKey="id"   value="sysRcRsrcOrg.rsrcType"
				       		listValue="typeName" name="sysRcRsrcOrg.rsrcType" disabled="disabled"/>
			       	</s:if>
			       	<s:else>
						${sysRcRsrcOrg.resourceTypeName}
					</s:else>--%>
					<input class="easyui-validatebox" value='${sysRcRsrcOrg.resourceTypeName}' disabled="disabled"/>
		    </div>
	    </s:if>
	</div>
</form>
<script type="text/javascript">
	var form_typeinfo = null;
	$(function(){
		var ownAttrs=${hashMap.ownAttrs};
		$('#attr').datagrid({
			data:ownAttrs,
			fit:true,
		    fitColumns:true, striped:true,
			singleSelect:true, nowrap:false,
			columns:[[
				{field:'rsrcAttribCode',title:"<s:text name='attribute_code'/>",width:100},
				{field:'rsrcAttribName',title:"<s:text name='attribute_name'/>",width:100}			
			]],
			onSelect:function(index, rowData){
				$('#extendsAttr').datagrid('unselectAll');
				propertyLoad(rowData);
			}
		});

		var extendsAttrs=${hashMap.extendsAttrs};
		$('#extendsAttr').datagrid({
			data:extendsAttrs,
		    fitColumns:true, striped:true,
			singleSelect:true, nowrap:false,
			fit:true,
			toolbar:"#processFlowBar",
			columns:[[
				{field:'rsrcAttribCode',title:"<s:text name='extend_attribute_code'/>",width:100},
				{field:'rsrcAttribName',title:"<s:text name='extend_attribute_name'/>",width:100}
			]],
			onSelect:function(index, rowData){
				$('#attr').datagrid('unselectAll');
				propertyLoad(rowData);
			}
	    });
		
		if(ownAttrs.length>0){
			$('#attr').datagrid('selectRow',0);
		}else 	if(extendsAttrs.length>0){
			$('#extendsAttr').datagrid('selectRow',0);
		}else{
			propertyLoad({attrArray:{}});
		}
  	});
</script>


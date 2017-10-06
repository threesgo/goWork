<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	var attrOperation = {};
	$(function(){
		//继承属性
		$('#extendsAttr').datagrid({
			url:"resourceTypeAction!findExtendsAttr.act?sysRsRcCatalog.id=${sysRsRcCatalog.id}",
		    fitColumns:true, 
		    striped:true,
			multiSort:true,
            singleSelect:true,
            selectOnCheck:false,
            checkOnSelect:false,
            remoteSort:false,
            toolbar:"#extends_attr_bar",
			nowrap:false,
			fit:true,
			idField:'id',
			columns:[[
				{field:'_checkbox',checkbox:true},
				{field:'rsrcAttribCode',title:"属性代号",width:100},
				{field:'rsrcAttribName',title:"属性名称",width:100}
			]],
			onSelect:function(index, rowData){
				
			}
	    });
	
		//本身关联属性
		$('#attr').datagrid({
			url:"resourceTypeAction!findAttr.act?sysRsRcCatalog.id=${sysRsRcCatalog.id}",
			fit:true,
		    fitColumns:true, 
		    striped:true,
			multiSort:true,
            singleSelect:true,
            selectOnCheck:false,
            checkOnSelect:false,
            remoteSort:false,
			toolbar:"#attr_bar",
			nowrap:false,
			idField:'id',
			columns:[[
				{field:'_checkbox',checkbox:true},
				{field:'rsrcAttribCode',title:"属性代号",width:100},
				{field:'rsrcAttribName',title:"属性名称",width:100},
			]],
			onSelect:function(index, rowData){
			}
		});
  	});
  	
  	attrOperation = {
		addAttr:function(){
			var node = resourceTypeTree.tree("getSelected");
 			var dialog =$('<div id="addResourceTypeAttr"></div>').dialog({    
				href : "resourceTypeAction!saveOrUpdateAttrPage.act",
				width:600,
				height:350,
				resizable:true,
				title:"新增属性",
				method:'post',
				queryParams:{"sysRsRcCatalog.id":node.attributes.id},
				modal:true,
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$('#saveOrUpdate_resource_attr').form({    
						    onSubmit: function(){
						    	if(!validateForm()){
						    		return false;
						    	}
						    },    
						    success:function(data){ 
						    	handlerResult(data,
						    		function(rs){
						    			$('#attr').datagrid("reload");
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
		},
		updateAttr:function(status){
			var selected;
			var buttonArr;
			if(status==1){
				selected = $('#attr').datagrid("getSelected");
				if(null==selected){
					$alert("请选择属性行进行编辑!");
					return false;
				}
				buttonArr = [{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$('#saveOrUpdate_resource_attr').form({    
						    onSubmit: function(){
						    	if(!validateForm()){
						    		return false;
						    	}
						    },    
						    success:function(data){ 
						    	handlerResult(data,
						    		function(rs){
						    			$('#attr').datagrid("reload");
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
				}];
			}else{
				selected = $('#extendsAttr').datagrid("getSelected");
				buttonArr = [{
					text:"取消",
					iconCls:'icon-cancel',
					handler:function(){
						dialog.dialog("destroy");
					}
				}];
			}
 			var dialog =$('<div id="addResourceTypeAttr"></div>').dialog({    
				href : "resourceTypeAction!saveOrUpdateAttrPage.act",
				width:600,
				height:350,
				resizable:true,
				title:"新增属性",
				method:'post',
				queryParams:{"sysRsRcAttribCatalog.id":selected.id},
				modal:true,
				buttons:buttonArr,
				onClose:function(){
					$(this).dialog("destroy");
				}
 			});
		},
		deleteAttr:function(){
			//删除时需要验证该类型下是否创建了资源，这样删除的话会把资源关联的属性值表也给删除
		}
	}
	
	function validateForm(){
		if(!$("#saveOrUpdate_resource_attr #code").validatebox("isValid")){
    		$("#saveOrUpdate_resource_attr #code").focus();
    		return false;
    	}else
    	if(!$("#saveOrUpdate_resource_attr #name").validatebox("isValid")){
    		$("#saveOrUpdate_resource_attr #name").focus();
    		return false;
    	}else
    	if(!$("#saveOrUpdate_resource_attr #dataLength").validatebox("isValid")){
    		$("#saveOrUpdate_resource_attr #dataLength").focus();
    		return false;
    	}else
    	if(!$("#saveOrUpdate_resource_attr #orderNo").validatebox("isValid")){
    		$("#saveOrUpdate_resource_attr #orderNo").focus();
    		return false;
    	}
	}
</script>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,title:''" style="height:300px" border="false">
		<table id="extendsAttr"></table>
	</div>
	
	<div data-options="region:'center',split:true,title:''" border="false">
		<table id="attr"></table>
	</div>
</div>

<div id="extends_attr_bar" style="height:auto !important;padding:2px;width: 100%;" class="hide">
	<a href="#" iconCls="icon-edit" onclick="attrOperation.updateAttr(2)" 
		class="easyui-linkbutton edit" data-options="plain:true">属性查看</a>
</div>

<div id="attr_bar" style="height:auto !important;padding:2px;width: 100%;" class="hide">
	<a href="#" iconCls="icon-add " onclick="attrOperation.addAttr()" 
		class="easyui-linkbutton edit" data-options="plain:true">增加属性</a>
	<a href="#" iconCls="icon-remove" onclick="attrOperation.deleteAttr()" 
		class="easyui-linkbutton edit" data-options="plain:true">删除属性</a>
	<a href="#" iconCls="icon-edit" onclick="attrOperation.updateAttr(1)" 
		class="easyui-linkbutton edit" data-options="plain:true">编辑查看</a>
</div>
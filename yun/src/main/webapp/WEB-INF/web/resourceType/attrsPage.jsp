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
			singleSelect:true, 
			nowrap:false,
			fit:true,
			columns:[[
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
			singleSelect:true, 
			toolbar:"#attr_bar",
			nowrap:false,
			columns:[[
				{field:'rsrcAttribCode',title:"属性代号",width:100},
				{field:'rsrcAttribName',title:"属性名称",width:100}			
			]],
			onSelect:function(index, rowData){
			}
		});
  	});
  	
  	attrOperation = {
		addAttr:function(){
			var node = resourceTypeTree.tree("getSelected");
 			var dialog =$('<div id="addResourceTypeAttr"></div>').dialog({    
				href : "resourceTypeAction!addTypeAttr.act",
				width:450,
				height:350,
				title:"新增属性",
				method:'post',
				queryParams:{"sysRsRcCatalog.id":node.attributes.id},
				modal:true,
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$('#add_resource_type').form({    
						    onSubmit: function(){  
						    },    
						    success:function(data){ 
						    	handlerResult(data,
						    		function(rs){
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
		deleteAttr:function(){
			//删除时需要验证该类型下是否创建了资源，这样删除的话会把资源关联的属性值表也给删除
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

<div id="attr_bar" style="height:auto !important;padding:2px;width: 100%;" class="hide">
	<a href="#" iconCls="icon-add " onclick="attrOperation.addAttr()" 
		class="easyui-linkbutton edit" data-options="plain:true">增加属性</a>
	<a href="#" iconCls="icon-remove" onclick="attrOperation.deleteAttr()" 
		class="easyui-linkbutton edit" data-options="plain:true">删除属性</a>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/public/public.jsp" />
		<style type="text/css">					
		</style>
		
		<script type="text/javascript">
		var resourceTypeTree;
 		var resourceTypeTab;
 		var $resourceTypeInfoTable;
 		var resourceTypeOperation = {};
		$(function(){
			//添加树结构
	 		resourceTypeTree=$('#resourceTypeTree').tree(
 				{
 					url:"resourceTypeAction!findTree.act",
 					animate:true,
 					onSelect:function(node){//onSelect是选择节点时触发
 						closeAllTab(resourceTypeTab);
 						if("root" != node.id){
	 						resourceTypeTab.tabs('add',{    
						    	title:'基本信息', 
						    	href:"resourceTypeAction!info.act?sysRsRcCatalog.id="+ node.attributes.id
							});
							resourceTypeTab.tabs('add',{
	 							title:'属性列表', 
							    href:"resourceTypeAction!attrsPage.act?sysRsRcCatalog.id="+ node.attributes.id
							});
 						}
 						resourceTypeTab.tabs('add',{
 							title:'子集列表', 
						    href:"resourceTypeAction!childrenPage.act?sysRsRcCatalog.id="+ node.attributes.id
						});
						if("root" != node.id){
							resourceTypeTab.tabs("select",1);
						}
 					}
 				}
	 		);

	 		resourceTypeTab=$('#resourceTypeTab').tabs({//得到中间布局的tabs
	 			fit:true,
	 		    onSelect:function(title){  
	 		    }    
	 		}); 
		});
	 		
	 	resourceTypeOperation = {
	 		reload:function(){
	 			resourceTypeTree.tree("reload");
	 		},
	 		
	 		expand:function(){
	 			resourceTypeTree.tree("expandAll");
	 		},
	 	
	 		deleteType:function(){
	 			$.messager.confirm('确认','确认要删除选择产品类型吗？',function(r){    
				    if (r){
				        var node = resourceTypeTree.tree("getSelected");
				        $.post("resourceTypeAction!deleteSysRsRcCatalog.act",
				        	{"sysRsRcCatalog.id":node.attributes.id},
				        	function(data){
							handlerResult(data,
					    		function(rs){
				    				resourceTypeTree.tree("remove",node.target);
									$show(rs.message);
									/*
									var rootNode = resourceTypeTree.tree("getRoot");
									resourceTypeTree.tree('update', {
										target: rootNode.target,
										state:'closed'
									});
									*/
								},
								function(rs){
									$alert(rs.message);
								}
							);  
						},"json");
				    }    
				});
	 		},
	 		
	 		addType:function(){
	 			var node = resourceTypeTree.tree("getSelected");
	 			var dialog =$('<div id="addResourceType"></div>').dialog({    
					href : "resourceTypeAction!saveOrUpdateTypePage.act",
					width:350,
					height:250,
					title:"新增类型",
					method:'post',
					queryParams:{"sysRsRcCatalog.parentId":node.attributes.id},
					modal:true,
					resizable:true,
					buttons:[{
						text:"确定",
						iconCls:'icon-ok',
						handler:function(){
							$('#saveOrUpdate_resource_type').form({    
							    onSubmit: function(){  
							    },    
							    success:function(data){ 
							    	handlerResult(data,
							    		function(rs){
							    			dialog.dialog("destroy");
						    				//resourceTypeTree.tree("reload",node.target);
						    				resourceTypeTree.tree('append', {
												parent: node.target,
												data: [{
													id: rs.data.id,
													text: rs.data.catalogCode+","+rs.data.catalogName,
													attributes:rs.data
												}]
											});
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
	 		editType:function(){
	 			var node = resourceTypeTree.tree("getSelected");
	 			var dialog =$('<div id="updateResourceType"></div>').dialog({    
					href : "resourceTypeAction!saveOrUpdateTypePage.act",
					width:350,
					height:250,
					resizable:true,
					title:"编辑类型",
					method:'post',
					queryParams:{"sysRsRcCatalog.id":node.attributes.id},
					modal:true,
					buttons:[{
						text:"确定",
						iconCls:'icon-ok',
						handler:function(){
							$('#saveOrUpdate_resource_type').form({    
							    onSubmit: function(){  
							    },    
							    success:function(data){ 
							    	handlerResult(data,
							    		function(rs){
							    			dialog.dialog("destroy");
											$show(rs.message);
											$resourceTypeInfoTable.datagrid("reload");
											resourceTypeTree.tree('update', {
												target: node.target,
												text:rs.data.catalogCode+","+rs.data.catalogName
											});
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
	 	};
		</script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#resource_type_bar'" title=" " style="width:200px;" border="false">
			<ul id="resourceTypeTree"></ul>
			<div  id="resource_type_bar">
				<a href="#" class="bullet_arrow_down" onclick="resourceTypeOperation.expand()">展开全部</a>
		       	<a href="#" class="icon-reflesh" onclick="resourceTypeOperation.reload()">刷新</a>
		     </div>
		</div>
		<div data-options="region:'center',split:true,title:''" border="false">
			<div id="resourceTypeTab" ></div>
		</div>
	 </body>
</html>

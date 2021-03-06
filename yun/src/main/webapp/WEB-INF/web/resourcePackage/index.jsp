<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/public/public.jsp" />
		<style type="text/css">					
		</style>
		
		<script type="text/javascript">
		var resourcePackageTree;
 		var resourcePackageTab;
 		var $resourceTypeInfoTable;
 		var resourceTypeOperation = {};
		$(function(){
			//添加树结构
	 		resourcePackageTree=$('#resourcePackageTree').tree(
 				{
 					url:"resourcePackageAction!findTree.act",
 					animate:true,
 					onSelect:function(node){//onSelect是选择节点时触发
 						closeAllTab(resourcePackageTab);
 						if("root" != node.id){
							resourcePackageTab.tabs('add',{    
						    	title:'基本信息', 
						    	href:"resourcePackageAction!info.act?sysRsRcPackage.id="+ node.attributes.id
							});
							/*
							resourcePackageTab.tabs('add',{
	 							title:'产品列表', 
							    href:"resourcePackageAction!packageResourceList.act?sysRsRcPackage.id="+ node.attributes.id
							});
							*/
							resourcePackageTab.tabs('add',{
	 							title:'组合配置', 
							    href:"resourcePackageAction!packageBrand.act?sysRsRcPackage.id="+ node.attributes.id
							});
 						}else{
 							resourcePackageTab.tabs('add',{
 	 							title:'组合列表', 
 							    href:"resourcePackageAction!packageList.act"
 							});
 						}
 					}
 				}
	 		);

	 		resourcePackageTab=$('#resourcePackageTab').tabs({//得到中间布局的tabs
	 			fit:true,
	 		    onSelect:function(title){  
	 		    }    
	 		}); 
		});
	 		
		resourcePackageOperation = {
	 		reload:function(){
	 			resourcePackageTree.tree("reload");
	 		},
	 	
	 		deletePackage:function(){
	 			$.messager.confirm('确认','确认要删除选择产品组合吗？',function(r){    
				    if (r){
				        var node = resourcePackageTree.tree("getSelected");
				        $.post("resourcePackageAction!deleteResourcePackage.act",
				        	{"sysRsRcPackage.id":node.attributes.id},
				        	function(data){
							handlerResult(data,
					    		function(rs){
				    				resourcePackageTree.tree("remove",node.target);
									$show(rs.message);
									$resourcePackageChildrenTable.datagrid("reload");
								},
								function(rs){
									$alert(rs.message);
								}
							);  
						},"json");
				    }    
				});
	 		},
	 		
	 		addPackage:function(){
	 			var node = resourcePackageTree.tree("getSelected");
	 			var dialog =$('<div id="addResourcePackage"></div>').dialog({    
					href : "resourcePackageAction!saveOrUpdatePackagePage.act",
					width:350,
					height:280,
					title:"新增组合",
					method:'post',
					modal:true,
					resizable:true,
					buttons:[{
						text:"确定",
						iconCls:'icon-ok',
						handler:function(){
							$('#saveOrUpdate_resource_package').form({    
							    onSubmit: function(){  
							    },    
							    success:function(data){ 
							    	handlerResult(data,
							    		function(rs){
							    			dialog.dialog("destroy");
						    				resourcePackageTree.tree("reload",node.target);
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
	 		
	 		editPackage:function(){
	 			var node = resourcePackageTree.tree("getSelected");
	 			var dialog =$('<div id="updateResourceType"></div>').dialog({    
					href : "resourcePackageAction!saveOrUpdatePackagePage.act",
					width:350,
					height:280,
					resizable:true,
					title:"编辑组合",
					method:'post',
					queryParams:{"sysRsRcPackage.id":node.attributes.id},
					modal:true,
					buttons:[{
						text:"确定",
						iconCls:'icon-ok',
						handler:function(){
							$('#saveOrUpdate_resource_package').form({    
							    onSubmit: function(){  
							    },    
							    success:function(data){ 
							    	handlerResult(data,
							    		function(rs){
							    			dialog.dialog("destroy");
											$show(rs.message);
											$resourcePackageInfoTable.datagrid("reload");
											resourcePackageTree.tree('update', {
												target: node.target,
												text:rs.data.name
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
	 		}
	 	};
		</script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#resource_package_bar'" title="套餐结构树" style="width:200px;" border="false">
			<ul id="resourcePackageTree"></ul>
			<div  id="resource_package_bar">
		       <a href="#" class="icon-reflesh" onclick="resourcePackageOperation.reload()">刷新</a>
		     </div>
		</div>
		<div data-options="region:'center',split:true,title:''" border="false">
			<div id="resourcePackageTab" ></div>
		</div>
	 </body>
</html>

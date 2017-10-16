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
 					dnd:true,
					onBeforeDrag:function(node){
						if(node.attributes.id==0||node.attributes.id==1||node.attributes.id==2||node.attributes.id==3
								||node.attributes.id==4||node.attributes.id==5||node.attributes.id==6
								||node.attributes.id==7||node.attributes.id==8){
							return false;
						}
					},
					onBeforeDrop:function(target,source,point){
						var tarNode = resourceTypeTree.tree("getNode",target);
						var sourNode=source;
						var sourParNode = resourceTypeTree.tree("getParent",sourNode.target);
						var data={};
						switch (point) {
							//不同工厂不能相互移动
							case "append":
								if(tarNode.attributes.id == 0 || sourParNode.attributes.id == tarNode.attributes.id){
									return false;
								}
								data = {"point":0,"targetId":tarNode.attributes.id,"sourceId":source.attributes.id};
								break;
							case "bottom":
								return false;
								break;
							case "top":
								return false;
								break;
						}
						var load=new Some.loading();
						//相同的继承属性不删除，没有的删除
						$confirm("确定移动产品类型吗,移动后继承属性值数据将会丢失?",function(){
							load.show();
							$.post("resourceTypeAction!dragResourceType.act",data,
							function(json){
								load.close();
								handlerResult(json,
						    		function(rs){
										resourceTypeTree.tree('append', {
											parent: tarNode.target,
											data:sourNode
										});
										resourceTypeTree.tree("remove",sourNode.target);
										$show(rs.message);
									},
									function(rs){
										$alert(rs.message);
									}
								); 
							},"json");
						});
						return false;
					},
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
 					},
 					onLoadSuccess:function(node,data){
 						var root = resourceTypeTree.tree("getRoot");
 						resourceTypeTree.tree("select",root.target);
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
	 			$("#expandAll").show();
	 			$("#collapseAll").hide();
	 		},
	 		
	 		expand:function(){
	 			//resourceTypeTree.tree("reload");
	 			$("#expandAll").hide();
	 			$("#collapseAll").show();
	 			resourceTypeTree.tree("expandAll");
	 		},
	 		
	 		collapse:function(){
	 			//resourceTypeTree.tree("reload");
	 			$("#expandAll").show();
	 			$("#collapseAll").hide();
	 			resourceTypeTree.tree("collapseAll");
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
									$("#resourceTypeChildrenTable").datagrid("reload");
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
													//rs.data.catalogCode+","+
													text: rs.data.catalogName,
													attributes:rs.data
												}]
											});
											$("#resourceTypeChildrenTable").datagrid("reload");
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
	 		}
	 	};
		</script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#resource_type_bar'" title=" " style="width:200px;" border="false">
			<ul id="resourceTypeTree"></ul>
			<div  id="resource_type_bar">
				<a href="#" class="bullet_arrow_down" id="expandAll" onclick="resourceTypeOperation.expand()">展开子集分类</a>
				<a href="#" class="bullet_arrow_up" id="collapseAll" style="display: none;" onclick="resourceTypeOperation.collapse()">折叠子集分类</a>
		       	<a href="#" class="icon-reflesh" onclick="resourceTypeOperation.reload()">刷新</a>
		     </div>
		</div>
		<div data-options="region:'center',split:true,title:''" border="false">
			<div id="resourceTypeTab" ></div>
		</div>
	 </body>
</html>

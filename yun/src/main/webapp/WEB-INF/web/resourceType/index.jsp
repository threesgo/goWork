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
							    href:"resourceTypeAction!attrList.act?sysRsRcCatalog.id="+ node.attributes.id
							});
 						}
 						resourceTypeTab.tabs('add',{
 							title:'子集列表', 
						    href:"resourceTypeAction!childrenPage.act?sysRsRcCatalog.id="+ node.attributes.id
						});
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
	 		
	 		},
	 	
	 		del:function(){
	 			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
				    if (r){
				        
				    }    
				});
	 		},
	 		
	 		add:function(){
	 			var node = resourceTypeTree.tree("getSelected");
	 			var dialog =$('<div id="addResourceType"></div>').dialog({    
					href : "resourceTypeAction!addResourceType.act",
					width:350,
					height:250,
					title:"新增类型",
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
						    				resourceTypeTree.tree("reload",node.target);
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
	 		}
	 	};
		</script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#resource_type_bar'" title=" " style="width:200px;" border="false">
			<ul id="resourceTypeTree"></ul>
			<div  id="resource_type_bar">
		       <a href="#" class="icon-reflesh" onclick="reload()">刷新</a>
		     </div>
		</div>
		<div data-options="region:'center',split:true,title:''" border="false">
			<div id="resourceTypeTab" ></div>
		</div>
	 </body>
</html>

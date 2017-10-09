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
 		var resourceTab;
 		var resourceIndexOperation = {};
		$(function(){
			//添加树结构
	 		resourceTypeTree=$('#resourceTypeTree').tree(
 				{
 					url:"resourceTypeAction!findTree.act",
 					animate:true,
 					onSelect:function(node){//onSelect是选择节点时触发
 						closeAllTab(resourceTab);
 						resourceTab.tabs('add',{
 							title:'产品列表', 
						    href:"resourceAction!resourceList.act?sysRsRcCatalog.id="+ node.attributes.id
						});
 					}
 				}
	 		);

	 		resourceTab=$('#resourceTab').tabs({//得到中间布局的tabs
	 			fit:true,
	 		    onSelect:function(title){  
	 		    }    
	 		}); 
		});
	 		
	 	resourceIndexOperation = {
	 		reload:function(){
	 			resourceTypeTree.tree("reload");
	 		}
	 	};
		</script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#resource_bar'" title=" " style="width:200px;" border="false">
			<ul id="resourceTypeTree"></ul>
			<div  id="resource_bar">
		       <a href="#" class="icon-reflesh" onclick="resourceIndexOperation.reload()">刷新</a>
		     </div>
		</div>
		<div data-options="region:'center',split:true,title:'',tools:''" border="false">
			<div id="resourceTab" ></div>
		</div>
	 </body>
</html>
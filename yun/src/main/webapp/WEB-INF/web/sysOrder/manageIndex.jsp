<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/public/public.jsp" />
		<style type="text/css">					
		</style>
		
		<script type="text/javascript">
		var sysOrderTree;
 		var sysOrderTab;
 		var $sysOrderInfoTable;
 		var sysOrderOperation = {};
		$(function(){
			//添加树结构
	 		sysOrderTree=$('#sysOrderTree').tree(
 				{
 					url:"sysOrderAction!findTree.act",
 					animate:true,
 					onSelect:function(node){//onSelect是选择节点时触发
 						closeAllTab(sysOrderTab);
 						if("root" != node.id){
	 						sysOrderTab.tabs('add',{    
						    	title:'基本信息', 
						    	href:"sysOrderAction!info.act?sysRsRcCatalog.id="+ node.attributes.id
							});
							sysOrderTab.tabs('add',{
	 							title:'属性列表', 
							    href:"sysOrderAction!attrsPage.act?sysRsRcCatalog.id="+ node.attributes.id
							});
 						}
 						sysOrderTab.tabs('add',{
 							title:'子集列表', 
						    href:"sysOrderAction!childrenPage.act?sysRsRcCatalog.id="+ node.attributes.id
						});
						if("root" != node.id){
							sysOrderTab.tabs("select",1);
						}
 					},
 					onLoadSuccess:function(node,data){
 						var root = sysOrderTree.tree("getRoot");
 						sysOrderTree.tree("select",root.target);
 					}
 				}
	 		);

	 		sysOrderTab=$('#sysOrderTab').tabs({//得到中间布局的tabs
	 			fit:true,
	 		    onSelect:function(title){  
	 		    }    
	 		}); 
		});
	 		
	 	sysOrderOperation = {
	 		reload:function(){
	 			sysOrderTree.tree("reload");
	 		}
	 	};
		</script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#order_bar'" title=" " style="width:200px;" border="false">
			<ul id="sysOrderTree"></ul>
			<div  id="order_bar">
				<!-- 
				<a href="#" class="bullet_arrow_down" id="expandAll" onclick="sysOrderOperation.expand()">展开子集分类</a>
				<a href="#" class="bullet_arrow_up" id="collapseAll" style="display: none;" onclick="sysOrderOperation.collapse()">折叠子集分类</a>
		       	 -->
		       	<a href="#" class="icon-reflesh" onclick="sysOrderOperation.reload()">刷新</a>
		     </div>
		</div>
		<div data-options="region:'center',split:true,title:''" border="false">
			<div id="sysOrderTab" ></div>
		</div>
	 </body>
</html>

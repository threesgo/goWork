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
 						var idStart = node.id.substring(0,1);
 						//顶级以及时间节点
 						if("r" == idStart || "t" == idStart){
 							sysOrderTab.tabs('add',{    
						    	title:'订单列表', 
						    	href:"sysOrderAction!seachIndex.act"
							});
 						}else if("o" == idStart){
 							//订单节点  
 							sysOrderTab.tabs('add',{    
						    	title:'工人材料', 
						    	href:"sysOrderAction!workerAndResourceByOrder.act"
							});
 							sysOrderTab.tabs('add',{    
						    	title:'甘特图', 
						    	href:"sysOrderAction!gtChart.act"
							});
 							sysOrderTab.tabs('add',{    
						    	title:'费用分配图', 
						    	href:"sysOrderAction!barChart.act"
							});
 						}else if("s" == idStart){
 							sysOrderTab.tabs('add',{    
						    	title:'步骤信息', 
						    	href:"sysOrderAction!flowInfo.act"
							});
 							sysOrderTab.tabs('add',{    
						    	title:'工人材料', 
						    	href:"sysOrderAction!workerAndResourceByFlow.act"
							});
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

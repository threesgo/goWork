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
 					dnd:true,
					onBeforeDrag:function(node){
						var idStart = node.id.substring(0,1);
						if("s" != idStart){
							return false;
						}
					},
					onBeforeDrop:function(target,source,point){
						var tarNode = sysOrderTree.tree("getNode",target);
						var sourNode=source;
						var sourParNode = sysOrderTree.tree("getParent",sourNode.target);
						var tarParNode = sysOrderTree.tree("getParent",tarNode.target);
						var data={};
						switch (point) {
							//不同工厂不能相互移动
							case "append":
								return false;
								break;
							case "bottom":
								if(sourParNode.attributes.id != tarParNode.attributes.id){
									return false;
								}
								data = {"point":1,"targetId":tarNode.attributes.id,"sourceId":source.attributes.id};
								break;
							case "top":
								if(sourParNode.attributes.id != tarParNode.attributes.id){
									return false;
								}
								data = {"point":2,"targetId":tarNode.attributes.id,"sourceId":source.attributes.id};
								break;
						}
						var load=new Some.loading();
						//相同的继承属性不删除，没有的删除
						$confirm("确定移动订单步骤吗?",function(){
							load.show();
							$.post("sysOrderAction!dragOrderFlow.act",data,
							function(json){
								load.close();
								handlerResult(json,
						    		function(rs){
										sysOrderTree.tree("reload",tarParNode.target);
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
 						closeAllTab(sysOrderTab);
 						var idStart = node.id.substring(0,1);
 						//顶级以及时间节点 dateStr
 						if("r" == idStart){
 							sysOrderTab.tabs('add',{    
						    	title:'订单列表', 
						    	href:"sysOrderAction!seachIndex.act?dateStr=''"
							});
 						}else if("t" == idStart){
 							sysOrderTab.tabs('add',{    
						    	title:'订单列表', 
						    	href:"sysOrderAction!seachIndex.act?dateStr="+node.attributes.id
							});
 						}else if("o" == idStart){
 							//订单节点  
 							sysOrderTab.tabs('add',{    
						    	title:'订单信息', 
						    	href:"sysOrderAction!orderInfo.act?sysOrder.id="+node.attributes.id
							});
 							sysOrderTab.tabs('add',{    
						    	title:'工人材料', 
						    	href:"sysOrderAction!workerAndResourceByOrder.act?sysOrder.id="+node.attributes.id
							});
 							sysOrderTab.tabs('add',{    
						    	title:'甘特图', 
						    	href:"sysOrderAction!gtChart.act?sysOrder.id="+node.attributes.id
							});
 							sysOrderTab.tabs('add',{    
						    	title:'费用分配图', 
						    	href:"sysOrderAction!barChart.act?sysOrder.id="+node.attributes.id
							});
 						}else if("s" == idStart){
 							sysOrderTab.tabs('add',{    
						    	title:'步骤信息', 
						    	href:"sysOrderAction!flowInfo.act?sysOrderFlow.id="+node.attributes.id
							});
 							sysOrderTab.tabs('add',{    
						    	title:'工人材料', 
						    	href:"sysOrderAction!workerAndResourceByFlow.act?sysOrderFlow.id="+node.attributes.id
							});
 						}
 						sysOrderTab.tabs("select",0);
 					},
 					onLoadSuccess:function(node,data){
 						//var root = sysOrderTree.tree("getRoot");
 						//sysOrderTree.tree("select",root.target);
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
	 		},
	 		
	 		addOrderFlow:function(){
	 			var node = sysOrderTree.tree("getSelected");
	 			var dialog = $('<div id="addOrderFlow"></div>').dialog({    
					href : "sysOrderAction!saveOrUpdateOrderFlowPage.act",
					width:600,
					height:380,
					title:"新增订单步骤",
					method:'post',
					queryParams:{"sysOrderFlow.orderId":node.attributes.id},
					modal:true,
					resizable:true,
					buttons:[{
						text:"确定",
						iconCls:'icon-ok',
						handler:function(){
							$('#saveOrUpdate_orderFlow').form({    
							    onSubmit: function(){ 
							    	if(!$("#edit_name").validatebox("isValid")){
							    		$("#edit_name").focus();
							    		return false;
							    	}
							    },    
							    success:function(data){ 
							    	handlerResult(data,
							    		function(rs){
							    			dialog.dialog("destroy");
							    			sysOrderTree.tree('append', {
												parent: node.target,
												data: [{
													id: rs.data.id,
													text: rs.data.name,
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
	 		
	 		updateOrderFlow:function(){
	 			var node = sysOrderTree.tree("getSelected");
	 			var dialog = $('<div id="addOrderFlow"></div>').dialog({    
					href : "sysOrderAction!saveOrUpdateOrderFlowPage.act",
					width:600,
					height:380,
					title:"编辑订单步骤",
					method:'post',
					queryParams:{"sysOrderFlow.id":node.attributes.id},
					modal:true,
					resizable:true,
					buttons:[{
						text:"确定",
						iconCls:'icon-ok',
						handler:function(){
							$('#saveOrUpdate_orderFlow').form({    
							    onSubmit: function(){ 
							    	if(!$("#edit_name").validatebox("isValid")){
							    		$("#edit_name").focus();
							    		return false;
							    	}
							    },    
							    success:function(data){ 
							    	handlerResult(data,
							    		function(rs){
							    			dialog.dialog("destroy");
							    			sysOrderTree.tree('append', {
												parent: node.target,
												data: [{
													id: rs.data.id,
													text: rs.data.name,
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

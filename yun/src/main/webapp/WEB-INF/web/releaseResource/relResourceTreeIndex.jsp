<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<head>
	<style type="text/css">					
	</style>
	
	<script type="text/javascript">
	var resourceTypeTree;
	var relResourceTab;
	var resourceIndexOperation = {};
	$(function(){
		//添加树结构
 		resourceTypeTree=$('#resourceTypeTree').tree(
			{
				url:"resourceTypeAction!findTree.act",
				animate:true,
				onSelect:function(node){//onSelect是选择节点时触发
					closeAllTab(relResourceTab);
					relResourceTab.tabs('add',{
						title:'产品列表', 
				    	href:"resourceAction!relResourceTreeSelect.act?sysRsRcPackage.id=${sysRsRcPackage.id}"+
				    			"&sysRsRcCatalog.id="+ node.attributes.id
					});
				},
				onLoadSuccess:function(node,data){
					var root = resourceTypeTree.tree("getRoot");
					resourceTypeTree.tree("select",root.target);
				}
			}
 		);

 		relResourceTab=$('#relResourceTab').tabs({//得到中间布局的tabs
 			fit:true,
 		    onSelect:function(title){  
 		    }    
 		}); 
	});
 		
 	resourceIndexOperation = {
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
 		}
 	};
	</script>
</head>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true,tools:'#rel_resource_bar'" title=" " style="width:200px;" border="false">
		<ul id="resourceTypeTree"></ul>
		<div  id="rel_resource_bar">
		 	<a href="#" class="bullet_arrow_down" id="expandAll" onclick="resourceIndexOperation.expand()">展开子集分类</a>
			<a href="#" class="bullet_arrow_up" id="collapseAll" style="display: none;" onclick="resourceIndexOperation.collapse()">折叠子集分类</a>
			<a href="#" class="icon-reflesh" onclick="resourceIndexOperation.reload()">刷新</a>
		</div>
	</div>
	<div data-options="region:'center',split:true,title:'',tools:''" border="false">
		<div id="relResourceTab" ></div>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">					
</style>

<script type="text/javascript">
	var resourceSelectTree;
	var resourceSelectTab;
	var $resourceSelectInfoTable;
	var resourceSelectOperation = {};
	$(function(){
		//添加树结构
		resourceSelectTree=$('#resourceSelectTree').tree(
			{
				url:"resourceTypeAction!findTree.act",
				animate:true,
				dnd:true,
				onBeforeDrag:function(node){
					
				},
				onBeforeDrop:function(target,source,point){
					
				},
				onSelect:function(node){//onSelect是选择节点时触发
					closeAllTab(resourceTab);
					resourceTab.tabs('add',{
						title:'产品列表', 
				    	href:"resourceAction!resourceList.act?sysRsRcCatalog.id="+ node.attributes.id
					});
				},
				onLoadSuccess:function(node,data){
					var root = resourceSelectTree.tree("getRoot");
					resourceSelectTree.tree("select",root.target);
				}
			}
		);

		resourceSelectTab=$('#resourceSelectTab').tabs({//得到中间布局的tabs
			fit:true,
		    onSelect:function(title){  
		    }    
		}); 
	});
		
	resourceSelectOperation = {
		expand:function(){
			//resourceSelectTree.tree("reload");
			$("#expandAll").hide();
			$("#collapseAll").show();
			resourceSelectTree.tree("expandAll");
		},
		
		collapse:function(){
			//resourceSelectTree.tree("reload");
			$("#expandAll").show();
			$("#collapseAll").hide();
			resourceSelectTree.tree("collapseAll");
		},
		
		getResources:function(){
			
		}
	};
</script>
</head>
<div class="easyui-layout">
	<div data-options="region:'west',split:true,tools:'#resource_select_bar'" title=" " style="width:200px;" border="false">
	<ul id="resourceSelectTree"></ul>
	<div  id="resource_select_bar">
		<a href="#" class="bullet_arrow_down" id="expandAll" onclick="resourceSelectOperation.expand()">展开子集分类</a>
		<a href="#" class="bullet_arrow_up" id="collapseAll" style="display: none;" onclick="resourceSelectOperation.collapse()">折叠子集分类</a>
	     </div>
	</div>
	<div data-options="region:'center',split:true,title:''" border="false">
		<div id="resourceSelectTab" ></div>
	</div>
</div>

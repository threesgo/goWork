<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
				url:"sysOrderAction!findOrderPackageTree.act",
				animate:true,
				onSelect:function(node){//onSelect是选择节点时触发
					closeAllTab(resourcePackageTab);
					if("root" != node.id){
					
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
 		}
 	};
	</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,tools:'#resource_package_bar'" title=" " style="width:200px;" border="false">
		<ul id="resourcePackageTree"></ul>
		<div  id="resource_package_bar">
	       <a href="#" class="icon-reflesh" onclick="resourcePackageOperation.reload()">刷新</a>
	     </div>
	</div>
	<div data-options="region:'center',split:true,title:''" border="false">
		<div id="resourcePackageTab" ></div>
	</div>
 </body>

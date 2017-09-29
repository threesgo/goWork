<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="/public/public.jsp" />
		
		<style type="text/css">					
		</style>
		
		<script type="text/javascript">
		var roleTree;
 		var layoutTab;
		(function($){
	 		//添加树结构
	 		resourceTypeTree=$('#resourceTypeTree').tree(
 				{
 					url:"sysRoleAction!findTree.act",
 					animate:true,
 					onSelect:function(node){//onSelect是选择节点时触发
 					
 					}
 				}
	 		);

	 		layoutTab=$('#roleTab').tabs({//得到中间布局的tabs
	 			fit:true,
	 		    onSelect:function(title){  
	 		    }    
	 		}); 
		})(jQuery);
		
	 	function del(node){
	 		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){
			        
			    }    
			}); 
	 		
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
		<div data-options="region:'center',split:true,title:' '" border="false">
			<div id="resourceTypeTab" ></div>
		</div>
	 </body>
</html>
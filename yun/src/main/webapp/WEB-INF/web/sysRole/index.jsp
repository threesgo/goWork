<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="/public/public.jsp" />
	<style>
	</style>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#left_bar'" title="角色列表"  style="width:200px;" border="false">
			<ul id="roleTree" ></ul><!-- 存放树结构 -->
			<div  id="left_bar"><!-- 在标题栏添加刷新图标 -->
			<!-- icon-reload:刷新图标 -->
		       <a href="" class="icon-reflesh">刷新</a>
		     </div> 
		</div>
		<div data-options="region:'center',split:true,title:''" border="false">
			<div  id="roleTab" ></div>
		</div>
		<s:if test='#session.defaultMenu.sysRoleActionAdd==1'>
			<div id="addRole" class="easyui-menu" style="width:120px;">
				<div onclick="append()" data-options="iconCls:'icon-add'">添加</div>
			</div>
		</s:if>
		
		<s:if test='#session.defaultMenu.sysRoleActionDelete==1'>
			<div id="delRole" class="easyui-menu" style="width:120px;">
				<div onclick="del(this)" data-options="iconCls:'icon-remove'">删除</div>
			</div>
		</s:if>
		
 		<script type="text/javascript" > 
 		var roleTree;
 		var layoutTab;
 		var majorId;
 		var id;//所选角色id
	 	(function($){
	 		//添加树结构
	 		roleTree=$('#roleTree').tree({
 					url:"sysRoleAction!findTree.act?",
 					animate:true,
 					onSelect:function(node){//onSelect是选择节点时触发
 						closeAllTab(layoutTab);//此函数在public中定义，关闭中间布局的选项卡
 						if(roleTree.tree("isLeaf",node.target)){//判断是否为叶子节点
 							layoutTab.tabs('add',
 								{
 									title :"关联模块",
 									href :"sysRoleAction!edit.act?sysRole.id="+node.attributes.id
 								}
 							);   
 						}                           
 						else{
 							if(node.id=="root"){
 								layoutTab.tabs('add',
									{
 										title :"角色列表 ",
 	 									href :"sysRoleAction!list.act"
 	 								}
 								);
 							}
 						}
 					},
 					//右键菜单
 					onContextMenu:function(e,node){
 						id=node.id;
 						if(node.id.substring(0,4)=="root"){
	 						e.preventDefault();
							// 查找节点
							$('#roleTree').tree('select', node.target);
							// 显示快捷菜单
							$('#addRole').menu('show', {
								left: e.pageX,
								top: e.pageY,
							});
						}else if(node.id.substring(0,4)=="role"){
	 						e.preventDefault();
							// 查找节点
							$('#roleTree').tree('select', node.target);
							// 显示快捷菜单
							$('#delRole').menu('show', {
								left: e.pageX,
								top: e.pageY,
							});
						}
 					}
 				});
			
	 		 layoutTab=$('#roleTab').tabs({//得到中间布局的tabs
	 			fit:true,
	 		    onSelect:function(title){  
	 		    }    
	 		}); 
	 		
		})(jQuery);
		
		//添加角色
		function append(){
			var addRoleDilog=$('<div id="addRole"></div>').dialog({
				title:"<s:text name='添加角色'/>",
				width:400,
				top:160,
				height:"auto",
				resizable:true,
				href:"sysRoleAction!preAddOrEdit.act",
				method:"post", 
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$("#_fm").form({
							success:function(data){
								handlerResult(data,function(rs){
									$show(rs.message);
									var root = roleTree.tree("getRoot");
									roleTree.tree('reload',root.target);
									addRoleDilog.dialog("close");
								},function(rs){
									$alert(rs.message);
								});
							}
						}).submit(); 
					}
				},{
					text:"取消",
					iconCls:'icon-cancel',
					handler:function(){
						addRoleDilog.dialog("destroy");
					}
				}],
				onClose:function(){
					addRoleDilog.dialog("destroy");
					return true;
				}
			});
	 	};
	 	
	 	function del(node){
	 		$.messager.confirm('确认','您确认想要删除所选角色吗？',function(r){    
			    if (r){
			    	$.post("sysRoleAction!delete.act",
			    		   {"sysRole.id":id.substring(4,id.length)},//id是节点的id属性名
			    		   function(data){
								handlerResult(data,function(rs){
									$show(rs.message);
									$('#roleTree').tree('remove',node.target);
									var root = roleTree.tree("getRoot");
									roleTree.tree('reload',root.target);
									roleTree.tree('select',root.target);
								},function(rs){
									$alert(rs.message);
								});
			    		   }
					);
			        
			    }    
			}); 
	 		
	 	};
	 	</script>
	 </body>
</html>
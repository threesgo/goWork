<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="/public/public.jsp" />
	<style>
	</style>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,tools:'#left_bar'" title="部门列表"  style="width:200px;" border="false">
			<ul id="departMentTree" ></ul><!-- 存放树结构 -->
			<div  id="left_bar"><!-- 在标题栏添加刷新图标 -->
			<!-- icon-reload:刷新图标 -->
		       <a href="" class="icon-reflesh">刷新</a>
		     </div> 
		</div>
		<div data-options="region:'center',split:true,title:''" border="false">
			<div  id="departMentTab" ></div>
		</div>
		<div id="add_DepartMent" class="easyui-menu" style="width:120px;">
			<div onclick="append()" data-options="iconCls:'icon-add'">添加</div>
		</div>
		<div id="del_DepartMent" class="easyui-menu" style="width:120px;">
			<div onclick="del(this)" data-options="iconCls:'icon-remove'">删除</div>
		</div>
 		<script type="text/javascript" > 
 		var departMentTree;
 		var layoutTab;
 		var majorId;
 		var id;//所选角色id
	 	(function($){
	 		//添加树结构
	 		departMentTree=$('#departMentTree').tree({
 					url:"sysDepartMentAction!findTree.act?",
 					animate:true,
 					onSelect:function(node){//onSelect是选择节点时触发
 						closeAllTab(layoutTab);//此函数在public中定义，关闭中间布局的选项卡
 						if(departMentTree.tree("isLeaf",node.target)){//判断是否为叶子节点
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
							$('#departMentTree').tree('select', node.target);
							// 显示快捷菜单
							$('#add_DepartMent').menu('show', {
								left: e.pageX,
								top: e.pageY,
							});
						}else{
	 						e.preventDefault();
							// 查找节点
							$('#departMentTree').tree('select', node.target);
							// 显示快捷菜单
							$('#add_DepartMent').menu('show', {//添加
								left: e.pageX,
								top: e.pageY,
							});
							$('#del_DepartMent').menu('show', {//删除
								left: e.pageX,
								top: e.pageY,
							});
						}
 					}
 				});
			
	 		 layoutTab=$('#departMentTab').tabs({//得到中间布局的tabs
	 			fit:true,
	 		    onSelect:function(title){  
	 		    }    
	 		}); 
	 		
		})(jQuery);
 		
	 	//添加部门
		function append(){
			var addDepartMentDilog=$('<div id="addDepartMent"></div>').dialog({
				title:"<s:text name='添加部门'/>",
				width:400,
				top:160,
				height:"auto",
				resizable:true,
				href:"sysDepartMentAction!preAddOrEdit.act",
				queryParams:{"nodeId":id,"operateType":"add"},
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
									addDepartMentDilog.dialog("close");
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
						addDepartMentDilog.dialog("destroy");
					}
				}],
				onClose:function(){
					addDepartMentDilog.dialog("destroy");
					return true;
				}
			});
	 	};
	 	</script>
	 </body>
</html>
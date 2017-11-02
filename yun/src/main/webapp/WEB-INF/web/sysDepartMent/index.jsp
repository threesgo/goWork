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
		<div id="departMent" class="easyui-menu" style="width:120px;">
			<div onclick="append()" data-options="iconCls:'icon-add'">添加</div>
			<div onclick="del()" data-options="iconCls:'icon-remove'">删除</div>
		</div>
 		<script type="text/javascript" > 
 		var departMentTree;
 		var layoutTab;
 		var majorId;
 		var id;//所选角色id
 		var parentId;//所选角色parentid
	 	(function($){
	 		//添加树结构
	 		departMentTree=$('#departMentTree').tree({
 					url:"sysDepartMentAction!findTree.act?",
 					animate:true,
 					onSelect:function(node){//onSelect是选择节点时触发
 						closeAllTab(layoutTab);//此函数在public中定义，关闭中间布局的选项卡
 						 layoutTab.tabs('add',
  								{
  									title :"部门子集列表",
  									href :"sysDepartMentAction!preDepartMent.act?parentId="+node.id,
  									//selected:0
  								} 
  							); 
 						if(node.id != 0){//判断是否为根目录
 							layoutTab.tabs('add',
							{
								title :"职位列表 ",
								href :"sysDepartMentAction!prePosition.act?departMentId="+node.id,
								index: 0
 							});
 						}
 					},
 					//右键菜单
 					onContextMenu:function(e,node){
 						id=node.id;
 						//parentId = node.attributes.id;
 						if(node.id == 0){
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
							//$('#departMentTree').tree('select', node.target);
							// 显示快捷菜单
							$('#departMent').menu('show', {
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
				modal:true,
				resizable:true,
				href:"sysDepartMentAction!preAddOrEdit.act",
				queryParams:{"sysDepartMent.parentId":id},
				method:"post", 
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$("#_fm").form({
							success:function(data){
								handlerResult(data,function(rs){
									$show(rs.message);
									var departMent = departMentTree.tree("getRoot");
									departMentTree.tree('reload',departMent.target);
									$sysDepartMentTable.datagrid("reload");
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
	 	//删除部门
	 	function del(){
	 		$.messager.confirm('确认','您确认想要删除所选部门及其子子部门吗？',function(r){
			    if (r){
			    	$.post("sysDepartMentAction!deleteDepartMent.act",
			    		   {"ids":id},//id是节点的id属性名
			    		   function(data){
								handlerResult(data,function(rs){
									$show(rs.message);
									var departMent = departMentTree.tree("getRoot");
									//departMentTree.tree("select",departMent);
									//alert(Some.util.jsonToStr(departMent))
									departMentTree.tree('reload',departMent.target);
									$sysDepartMentTable.datagrid("reload");
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
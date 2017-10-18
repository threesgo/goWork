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
		<div id="mm" class="easyui-menu" style="width:120px;">
			<div onclick="append()" data-options="iconCls:'icon-add'">添加</div>
		</div>
		<div id="dd" class="easyui-menu" style="width:120px;">
			<div onclick="del(this)" data-options="iconCls:'icon-remove'">删除</div>
		</div>
 		<script type="text/javascript" > 
 		var roleTree;
 		var layoutTab;
 		var majorId;
 		var id;
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
 						if(node.id.substring(0,4)=="role"){
	 						e.preventDefault();
							// 查找节点
							$('#roleTree').tree('select', node.target);
							// 显示快捷菜单
							$('#mm').menu('show', {
								left: e.pageX,
								top: e.pageY,
							});
						}/*else if(node.id.substring(0,4)=="layo"){
	 						e.preventDefault();
							// 查找节点
							$('#roleTree').tree('select', node.target);
							// 显示快捷菜单
							$('#dd').menu('show', {
								left: e.pageX,
								top: e.pageY,
							});
						}*/
 					}
 				});
			
	 		 layoutTab=$('#roleTab').tabs({//得到中间布局的tabs
	 			fit:true,
	 		    onSelect:function(title){  
	 		    }    
	 		}); 
	 		
	 		/*
			function closeAllTab(layoutTab){
	 			var allTabs = layoutTab.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					closeTabsTitle.push(opt.title);
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					layoutTab.tabs('close', closeTabsTitle[i]);
				}
	 		}
	 		*/
			window.events={
				addNew:function(){
					new Some.dialog({
						href : "bopTrPlantAction!preAdd.act",
						top:100,
						width:350,
						height:"auto",
						title:"<s:text name='new_base'/>"
					});
				},
				/*
				reload:function(){
					tree.reload();
					tabs.closeAll();
				},
				deleteType:function(){
					var _01=tree.getSelected()||tree.getRoot();
					if(tree.isRoot(_01.target)){
						$alert("<s:text name='root_node_cannot_be_deleted'/>");
					}
					else{
						$confirm("<s:text name='do_you_want_to_delete_the_node'/>",function(){
							var loading=new Some.loading();
							loading.show();
							$.post("bopTrPlantAction!delete.act?bopTrPlant.id="+ _01.attributes.id, function(text) {
								loading.close();
								switch (""+text) {
									case "1":
										var _03=tree.getParent(_01.target);
										tree.remove(_01.target);
										tabs.closeAll();
										tree.expandTo(_03.target);
										if(tree.hasChildren(_03.target)){
											tree.select(tree.getLastChild(_03.target).target);
										}
										else{
											tree.changeFolder(_03.target);
											tree.select(_03.target);
										}
										break;
									case "0":
										$alert("<s:text name='operation_failed'/>！");
										break;
									default:
										$alert(text);
										break;
								}
							}, "text");
						});
					}
				}
				*/
			};
		})(jQuery);
		
		//添加角色
		function append(){
			var addRoleDilog=$('<div id="addRole"></div>').dialog({
				top:100,
				title:"<s:text name='添加角色'/>",
				width:450,
				height:"auto",
				resizable:true,
				href:"sysRoleAction!preAdd.act",
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
	 	/*
	 	function del(node){
	 		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){
			        $.ajax({
					   	type: "POST",
					   	url: "sysRoleAction!delete.act",
					   	data: "sysRole.id="+id.substring(4,id.length),//id是节点的id属性名
					   	success: function(data){
					   		handlerResult(data,function(rs){
								$show(rs.message);
								var node = roleTree.tree("find",id);
								$('#roleTree').tree('remove',node.target);
								var majorNode = $('#roleTree').tree('find',"bd"+rs.data.majorId);
								$('#roleTree').tree('select',majorNode.target);
							},function(rs){
								$alert(rs.message);
							});
						}
					});  
			    }    
			}); 
	 		
	 	};*/
	 	</script>
	 </body>
</html>
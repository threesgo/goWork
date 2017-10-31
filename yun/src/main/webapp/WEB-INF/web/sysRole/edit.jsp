<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	var menuTree;
	$(function(){
		menuTree=$('#menuTree').tree({
				checkbox:true,
				data:eval('${jsonInfo}')
			}
 		);
	});
	
	function saveMenuRel(){
		var menus = menuTree.tree('getChecked','indeterminate'); //获取不确定的节点(不是勾选,其子集中有选中的)
		var checkedmenus = menuTree.tree('getChecked');//获取勾选的节点
		var menusStr = []; 
		$.each(menus,function(index,item){
			menusStr.push(item.id);
		});
		$.each(checkedmenus,function(index,item){
			menusStr.push(item.id);
		});
		$.post("sysRoleAction!saveRoleRelMenu.act",{"menus":menusStr.join(","),"sysRole.id":${sysRole.id}}
			,function(data){
				handlerResult(data,function(rs){
					$show(rs.message);
				},function(rs){
					$show(rs.message);
				});
			});
		}
		//删除--删除选中的角色
		function delRole(){
	 		var node=$('#roleTree').tree('getSelected');
	 		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){   
			    if (r){
			        $.ajax({
					   	type: "POST",
					   	url: "sysRoleAction!delete.act",
					   	data: {"sysRole.id":'${sysRole.id}'},
					   	success: function(data){
					   		handlerResult(data,function(rs){
								$show(rs.message);
								$('#roleTree').tree('remove',node.target);
								var root = roleTree.tree("getRoot");
								roleTree.tree('select',root.target);
							},function(rs){
								$alert(rs.message);
							});
						}
					});  
			    }    
			});  
		};
		
		//编辑--修改角色姓名	
		function editRole(){
			var editRoleDilog=$('<div id="editRole"></div>').dialog({
				title:"<s:text name='编辑角色'/>",
				width:400,
				top:160,
				height:"auto",
				method:"post",
				resizable:true,
				href:"sysRoleAction!preAddOrEdit.act",
				method:"post",
				queryParams:{"sysRole.id":'${sysRole.id}'},
				
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$("#_fm").form({
							success:function(data){
								handlerResult(data,function(rs){
									editRoleDilog.dialog("destroy");
									$show(rs.message);
									var node = $('#roleTree').tree('getSelected');
									//更新编辑的节点名称
									 $('#roleTree').tree('update',{
										target:node.target,
										text:rs.data.name
									}); 
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
						editRoleDilog.dialog("destroy");
					}
				}],
				onClose:function(){
					editRoleDilog.dialog("destroy");
					return true;
				}
			});
			
		};
</script>

<style>
</style>
<div id="roleMenuEdit" class="easyui-layout" data-options="fit:true">   
	<div id="tool_bar">
		<a href="#" class="icon-edit" onclick="editRole()">编辑</a>
		<a href="#" class="icon-save" onclick="saveMenuRel()">保存</a>
		<a href="#" class="icon-remove" onclick="delRole()">删除</a>
	</div>
	<div data-options="region:'center',split:true,title:' ',tools:'#tool_bar'" border="false">
		<ul id="menuTree"></ul> 
	</div>
</div> 
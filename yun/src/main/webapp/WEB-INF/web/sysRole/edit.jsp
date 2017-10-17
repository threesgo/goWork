<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	var moudleTree;
	$(function(){
		moudleTree=$('#moudleTree').tree({
				//url:"sysRoleAction!findModelTree.act",
				checkbox:true,
				data:eval('${jsonInfo}')
				
			}
 		);
	});
	function saveModleRel(){
		var modules = moudleTree.tree('getChecked','indeterminate');
		var checkedModules = moudleTree.tree('getChecked');
		var modulesStr = ""; 
		var checkedsStr = ""; 
		$.each(modules,function(index,item){
			modulesStr += item.id+",";
		});
		$.each(checkedModules,function(index,item){
			modulesStr += item.id+",";
		});
		//alert(modulesStr+"---"+checkedsStr);
		//return false;
		$.post("sysRoleAction!saveRoleRelModule.act",{"moduleIds":modulesStr,"sysRole.id":${sysRole.id}}
			,function(data){
				handlerResult(data,function(rs){
					$show(rs.message);
				},function(rs){
					$show(rs.message);
				});
			});
		}
		//删除对应的角色
		function delRole(){
	 		var node=$('#roleTree').tree('getSelected');
	 	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){
			        $.ajax({
					   	type: "POST",
					   	url: "sysRoleAction!delete.act",
					   	data: "sysRole.id="+${sysRole.id},
					   	success: function(data){
					   		handlerResult(data,function(rs){
								$show(rs.message);
								 //node = $('#roleTree').tree("option",${sysRole.id});
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
	 		//del(node);
		};
		//修改角色姓名	
		function editRole(){
			var editRoleDilog=$('<div id="editRole"></div>').dialog({
				top:100,
				title:"<s:text name='编辑角色'/>",
				width:450,
				height:"auto",
				method:"post",
				resizable:true,
				href:"sysRoleAction!preEdit.act",
				method:"post",
				queryParams:{"sysRole.id":'${sysRole.id}'},
				onClose:function(){
					editRoleDilog.dialog("destroy");
					return true;
				}
			});
			
		};
</script>

<style>
</style>
<div id="roleMoudelEdit" class="easyui-layout" data-options="fit:true">   
	<div id="tool_bar">
		<a href="#" class="icon-edit" onclick="editRole()">编辑</a>
		<a href="#" class="icon-save" onclick="saveModleRel()">保存</a>
		<a href="#" class="icon-remove" onclick="delRole()">删除</a>
	</div>
	<div data-options="region:'center',split:true,title:' ',tools:'#tool_bar'" border="false">
		<ul id="moudleTree"></ul> 
	</div>
</div> 
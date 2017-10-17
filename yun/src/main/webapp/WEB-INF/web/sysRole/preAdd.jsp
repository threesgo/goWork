<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<form id="_fm" method="post" class="fm" action="sysRoleAction!add.act">  
	<fieldset>
		<legend>角色信息</legend>
		<%-- <input type="hidden" name="sysRole.id" value='${sysRole.id}'/> --%>
		<div class="fitem">  
			<span style="color: red;font-size:14;margin-right: 5px;">*</span> 
			<label>名称：</label>  
			<input name="sysRole.name" id="name" value='' class="easyui-validatebox" 
			       data-options="required:true,validType:['length[1,40]','illegal']">  
		</div>  
		<div class="fitem">  
			<%-- <span style="color: red;font-size:14;margin-right: 5px;">*</span> 
			<label><s:property value="%{getText('图标')}"/>：</label>   --%>
			<input name="sysRole.iconCls" id="iconCls" value='wrench' class="easyui-validatebox" type="hidden"
			       data-options="required:true,validType:['length[1,45]','illegal']">  
		</div>
	</fieldset>
	<%--<div class="dlg-buttons" style="text-align: center;">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" iconCls="icon-ok" onclick="add($(this))">
			<s:property value="%{getText('save')}"/>
		</a>
	</div>  
--%></form>
<script>
	/*function add($this){
		
		$("#_fm").form({
			success:function(data){
				handlerResult(data,function(rs){
					$this.close();
					$show(rs.message);
					var major = roleTree.tree("find","bd"+rs.data.majorId);
					roleTree.tree("reload",major.target);
				},function(rs){
					$alert(rs.message);
				});
			}
		}).submit();
	}*/
</script>


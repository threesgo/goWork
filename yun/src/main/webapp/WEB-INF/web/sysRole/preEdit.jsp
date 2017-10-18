<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<form id="_fm" method="post" class="fm" action="sysRoleAction!update.act">  
	<fieldset>
		<legend>角色信息</legend>
		<input type="hidden" name="sysRole.id" value='${sysRole.id}'/>
		<div class="fitem">  
			<span style="color: red;font-size:14;margin-right: 5px;">*</span> 
			<label>名称：</label>  
			<input name="sysRole.name" id="name" value='${sysRole.name}' class="easyui-validatebox" 
			       data-options="required:true,validType:['length[1,40]','illegal']">  
		</div>  
	</fieldset>
</form>
<script>
		
</script>


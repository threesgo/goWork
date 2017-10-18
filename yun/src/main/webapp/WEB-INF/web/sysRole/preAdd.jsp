<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="_fm" method="post" class="fm" action="sysRoleAction!add.act">  
		<div class="fitem" style="height: 35px">  
			<span style="color: red;font-size:14;margin-right: 5px;">*</span> 
			<label>角色名称：</label>  
			<input name="sysRole.name" id="name" value='' class="easyui-validatebox" 
			       data-options="required:true,validType:['length[1,40]','illegal']">  
		</div>
</form>


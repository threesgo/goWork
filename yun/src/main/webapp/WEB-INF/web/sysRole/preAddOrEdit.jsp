<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="_fm" method="post" class="fm">  
	<input type="hidden" name="sysRole.id" value='${sysRole.id}'/>
	<div class="fitem" style="height: 35px">  
		<span style="color: red;font-size:14;margin-right: 5px;">*</span> 
		<label>角色名称：</label>  
		<input name="sysRole.name" id="name" value='${sysRole.name}' class="easyui-validatebox" 
		       data-options="required:true,validType:['length[1,40]','illegal']">  
	</div>  
</form>

<script type="text/javascript" > 
(function($){
	var roleId = '${sysRole}';
	if(roleId){
		$("#_fm").attr("action","sysRoleAction!update.act");
	}else{
		$("#_fm").attr("action","sysRoleAction!add.act");
	} 
})(jQuery);
</script>
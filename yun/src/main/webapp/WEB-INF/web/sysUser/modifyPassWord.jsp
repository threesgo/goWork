<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="/public/public.jsp" />
	<script type="text/javascript">
		function update(){
			/*var $update_form=$("#modify_password_form");
			new Some.form({
					render:"#modify_password_form",
					success:function(data){
						handlerResult(data,function(rs){
							$show(rs.message);
							$update_form[0].reset();
						},function(rs){
							$show(rs.message);
						});
					}
				}).submit();*/
		}
	</script>
	<body>
		<form action="sysUserAction!updatePassword.act" id="modify_password_form">
			<input type="hidden" name="sysUser.id" value="${sessionScope._ADM.id}">
			<div class="ji">
				<span style="color: red;font-size:14;margin-right: 5px;">*</span>
				<label >原密码：</label>
				<input name="oldPassword" id="oldPassword" type="password" class="easyui-validatebox" 
     					    data-options="required:true,validType:['length[6,32]','illegal'],invalidMessage:'请输入原密码'"  style="margin-left: 12px"/>
			</div>
			
			<div class="ji">
				<span style="color: red;font-size:14;margin-right: 5px;">*</span>
				<label>新密码：</label>
				<input  name="sysUser.password" id="password" type="password" class="easyui-validatebox" 
     					    data-options="required:true,validType:['length[6,32]','illegal'],
     					    			  invalidMessage:'请输入新密码'" style="margin-left: 12px"/>
   			    
			</div>
			
			<div class="ji">
				<span style="color: red;font-size:14;margin-right: 5px;">*</span>
				<label>确认密码：</label>
				<input  type="password" class="easyui-validatebox" 
     					    data-options="required:true,validType:['equals[\'#password\']','illegal'],
     					    			  invalidMessage:'两次密码不一致'"/>
			</div>
		</form>
	</body> 
</html>
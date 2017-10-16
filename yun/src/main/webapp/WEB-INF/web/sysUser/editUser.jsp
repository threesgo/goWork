<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
    var role_id;
	$(function(){
		role_id = '${roleIds}';
		$('#editSysRole').combobox({
			  valueField:'id',
			  textField:'sysRole',
		      multiple:true,
		      editable:false,
		      url:"sysRoleAction!findAll.act",
		      onLoadSuccess:function(){
		    	   if(role_id){
		    		  var ids = role_id.split(",");
			    	  $.each(ids,function(index,n){
			    		 $('#editSysRole').combobox("select",n);
			    	  });
		    	  } 
		      }
		}); 
		
	});

</script>

<form id="edit_fm" method="post" class="fm" action="sysUserAction!edit.act"> 
	   <input id ="userId" name="sysUser.id" type="hidden" value="${userId}"/>
       <div class="fitem">  
	       	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
	        <label>用户名:</label>
	        <input name="sysUser.userName" id="editUserName" class="easyui-validatebox" 
			       data-options="required:true,validType:['length[1,32]','illegal']" 
			       style="margin-left:20px;" value="${sysUser.userName}"/>
       </div> 
        
       <div class="fitem">  
      		<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
           <label>真实姓名:</label>  
           <input name="sysUser.realName" id="editRealName" class="easyui-validatebox" 
		       data-options="required:true,validType:['length[1,64]','illegal']"
		       style="margin-left:10px;" value="${sysUser.realName}"/>  
       </div> 
       
       <div class="fitem">  
       	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
           <label>密码:</label>  
           <input name="sysUser.passWord" type="password" id="passWord" class="easyui-validatebox"
		       data-options="required:true,validType:['length[6,32]','illegal']"
		       style="margin-left:33px;" value="${sysUser.passWord}"/>  
       </div> 
       
       <div class="fitem">  
       	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
           <label>确认密码:</label>  
           <input id="cfmPassword" name="passWord" type="password" class="easyui-validatebox" 
		       data-options="required:true,validType:['length[6,32]','equals[\'#passWord\']','illegal'],invalidMessage:'两次密码不一致'"
		       style="margin-left:10px;" value="${sysUser.passWord}"/>  
       </div>
       
       <div class="fitem">  
       	<span style="color: red;font-size:14;margin-right: 7px;">&nbsp;&nbsp;</span> 
           <label>电子邮箱:</label>  
           <input name="sysUser.relMail"  class="easyui-validatebox" 
           	   data-options="validType:['email','max[128]','illegal']" 
           	   style="margin-left:10px;" value="${sysUser.relMail}"/>  
       </div> 
        
       <div class="fitem">  
       	<span style="color: red;font-size:14;margin-right: 7px;">&nbsp;&nbsp;</span> 
           <label>手机号码:</label>  
           <input name="sysUser.phoneNum" class="easyui-validatebox" data-options="validType:['phone','max[11]']"
           style="margin-left:10px;" value="${sysUser.phoneNum}"/>  
       </div>  
        
       <div class="fitem">  
	       	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;&nbsp;</span>
	           <label>部门：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>  
			<select id="editDept" name="sysUser.dept" style="width:152px;">
			</select> 
	   </div>
	
	   <div class="fitem">  
        	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span>
            <label>角色：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>  
			<select id="editSysRole" class="easyui-combobox" name="sysUser.roleIds" style="width:152px;" data-options="required:true">
			</select> 
	   </div>
        
</form>  


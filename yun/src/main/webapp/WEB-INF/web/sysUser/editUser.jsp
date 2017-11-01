<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
    var role_id;
    var department_id;
	$(function(){
		role_id = '${roleIds}';
		department_id = '${sysUser.departmentId}';
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
		
		$('#editDept').combobox({
			 valueField:'id',
			  textField:'userDepartMent',
		      multiple:false,
		      editable:false,
		      url:"sysDepartMentAction!findAll.act?noAll=1",
		      onLoadSuccess:function(){
		    	   if(department_id){
			    	  $('#editDept').combobox("select",department_id);
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
			       style="margin-left:20px;width:198px;" value="${sysUser.userName}"/>
      		<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
            <label>真实姓名:</label>  
            <input name="sysUser.realName" id="editRealName" class="easyui-validatebox" 
		       data-options="required:true,validType:['length[1,64]','illegal']"
		       style="margin-left:8px;width:198px;" value="${sysUser.realName}"/>  
       </div> 
       
       <div class="fitem">  
       	   <span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
           <label>密码:</label>  
           <input name="sysUser.passWord" type="password" id="passWord" class="easyui-validatebox"
		       data-options="required:false,validType:['length[6,32]','illegal']"
		       style="margin-left:33px;width:198px;" />  
       	   <!-- <span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
           <label>确认密码:</label>  
           <input id="cfmPassword" name="passWord" type="password" class="easyui-validatebox" 
		       data-options="required:false,validType:['length[6,32]','equals[\'#passWord\']','illegal'],invalidMessage:'两次密码不一致'"
		       style="margin-left:8px;width:198px;" />   -->
		   <span style="color: red;font-size:14;margin-right: 7px;">&nbsp;&nbsp;</span> 
           <label>电子邮箱:</label>  
           <input name="sysUser.relMail"  class="easyui-validatebox" 
           	   data-options="validType:['email','max[128]','illegal']" 
           	   style="margin-left:8px;width:198px;" value="${sysUser.relMail}"/>  
       </div>
       
       <div class="fitem">  
       	   <span style="color: red;font-size:14;margin-right: 7px;">&nbsp;&nbsp;</span> 
           <label>手机号码:</label>  
           <input name="sysUser.phoneNum" class="easyui-validatebox" data-options="validType:['mobile','max[11]']"
           style="margin-left:8px;width:198px;" value="${sysUser.phoneNum}"/>  
           <span style="color: red;font-size:14;margin-right: 5px;">&nbsp;&nbsp;</span>
	           <label style="margin-right:25px">部门：</label>  
			<select id="editDept"  name="sysUser.departmentId" style="width:200px;">
			</select> 
       </div>  
        
       <div class="fitem">  
        	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span>
            <label style="margin-right:20px">角色：</label>  
			<select id="editSysRole" name="sysUser.roleIds" style="width:200px;" data-options="required:true">
			</select> 
	   </div>
        
</form>  



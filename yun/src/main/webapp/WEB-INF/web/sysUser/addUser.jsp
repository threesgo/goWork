<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(function(){
		//角色下拉框，可多选
		$('#sysRole').combobox({
			  valueField:'id',
			  textField:'sysRole',
		      multiple:true,
		      editable:false,
		      url:"sysRoleAction!findAll.act",
		}); 
		
		$('#addDept').combobox({ 
			  valueField:'id',
			  textField:'dept',
		      multiple:false,
		      editable:true,
		     // url:"systemAdmin!findAllDept.act"
		}); 
	});

</script>

<form id="add_fm" method="post" class="fm" action="sysUserAction!add.act">  
       <div class="fitem">  
	       	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
	        <label>用户名:</label>
	        <input name="sysUser.userName" id="addUserName" class="easyui-validatebox" 
			       data-options="required:true,validType:['length[1,32]','illegal']" 
			       style="margin-left:20px;"/>
       </div> 
        
       <div class="fitem">  
      		<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
           <label>真实姓名:</label>  
           <input name="sysUser.realName" id="addRealName" class="easyui-validatebox" 
		       data-options="required:true,validType:['length[1,64]','illegal']"
		       style="margin-left:10px;"/>  
       </div> 
       
       <div class="fitem">  
       	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
           <label>密码:</label>  
           <input name="sysUser.passWord" type="password" id="passWord" class="easyui-validatebox"
		       data-options="required:true,validType:['length[6,32]','illegal']"
		       style="margin-left:33px;"/>  
       </div> 
       
       <div class="fitem">  
       	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span> 
           <label>确认密码:</label>  
           <input id="cfmPassword" name="passWord" type="password" class="easyui-validatebox" 
		       data-options="required:true,validType:['length[6,32]','equals[\'#passWord\']','illegal'],invalidMessage:'两次密码不一致'"
		       style="margin-left:10px;"/>  
       </div>
       
       <div class="fitem">  
       	<span style="color: red;font-size:14;margin-right: 7px;">&nbsp;&nbsp;</span> 
           <label>电子邮箱:</label>  
           <input name="sysUser.relMail"  class="easyui-validatebox" 
           	   data-options="validType:['email','max[128]','illegal']" 
           	   style="margin-left:10px;"/>  
       </div> 
        
       <div class="fitem">  
       	<span style="color: red;font-size:14;margin-right: 7px;">&nbsp;&nbsp;</span> 
           <label>手机号码:</label>  
           <input name="sysUser.phoneNum" class="easyui-validatebox" data-options="validType:['phone','max[11]']"
           style="margin-left:10px;"/>  
       </div>  
        
       <div class="fitem">  
	       	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;&nbsp;</span>
	           <label>部门：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>  
			<select id="addDept" name="sysUser.dept" style="width:152px;">
			</select> 
	   </div>
	
	   <div class="fitem">  
        	<span style="color: red;font-size:14;margin-right: 5px;">&nbsp;*</span>
            <label>角色：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>  
			<select id="sysRole" class="easyui-combobox" name="roleIds" style="width:152px;" data-options="required:true">
			</select> 
	   </div>
        
</form>  



<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="_fm" method="post" class="fm" action ="sysDepartMentAction!saveOrUpdate.act">  
	<input id = "departMentId" type="hidden" name="sysDepartMent.id" value='${sysDepartMent.id}'/>
	<input id ="parentId" type="hidden" name="sysDepartMent.parentId" value='${sysDepartMent.parentId}'/>
	<div class="fitem" style="height: 30px">  
		<span style="color: red;font-size:14;margin-right: 5px;">*</span> 
		<label>部门代号：</label> 
		<input name="sysDepartMent.code" id="code" value='${sysDepartMent.code}' class="easyui-validatebox" 
		       data-options="required:true,validType:['length[1,64]','illegal']">  
	</div> 
	<div class="fitem" style="height: 30px">  
		<span style="color: red;font-size:14;margin-right: 5px;">*</span> 
		<label>部门名称：</label>  
		<input name="sysDepartMent.name" id="name" value='${sysDepartMent.name}' class="easyui-validatebox" 
		       data-options="required:true,validType:['length[1,64]','illegal']">  
	</div> 
</form>

<script type="text/javascript" > 
(function($){
	
})(jQuery);
</script>
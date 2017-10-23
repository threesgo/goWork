<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="_fm" method="post" class="fm">  
		<input id = "departMentId" type="hidden" name="sysDepartMent.id"/>
		<input id ="parentId" type="hidden" name="sysDepartMent.parentId"/>
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
	 var operateType = '${operateType}';
	 if(operateType != "add"){
		 $("#_fm").attr("action","sysDepartMentAction!update.act");
	}else{
		$("#_fm").attr("action","sysDepartMentAction!add.act");
		var parentId = '${nodeId}';
		if(parentId =="root"){
			parentId == 0;
		}
		$("#parentId").val(parentId);
	} 
})(jQuery);
	
</script>
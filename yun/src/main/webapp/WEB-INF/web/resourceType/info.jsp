<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form method="POST" id="form_type_info">
	<div class="fitem">  
      	<label for="code">类型代号:</label>   
      	<input name="sysRsRcCatalog.catalogCode" class="easyui-validatebox" id="orgCode" style="width:200px;"
	           data-options="{required:true,validType:['length[1,32]']}" value='${sysRsRcCatalog.catalogCode}'/>
    </div>
    
    <div class="fitem">  
      	<label for="name">类型名称:</label>   
      	<input name="sysRsRcCatalog.catalogName" class="easyui-validatebox" id="orgName" style="width:200px;" 
	           data-options="{required:true,validType:['length[1,64]']}" value='${sysRsRcCatalog.catalogName}'/>
    </div>
    
    <div class="fitem">  
      	<label for="type">产品类别:</label>   
        <select id="type" class="easyui-combobox" name="sysRsRcCatalog.catalogType" style="width:200px;">   
		    <option value="1">产品</option>
		    <option value="2">工人</option>   
		</select>   
    </div>
</form>
<script type="text/javascript">
	
</script>


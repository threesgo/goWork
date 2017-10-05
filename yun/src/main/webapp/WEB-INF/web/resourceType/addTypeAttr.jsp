<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="add_resource_type" method="post" action="resourceTypeAction!save.act">
	<input type="hidden" name="sysRsRcCatalog.parentId" value="${sysRsRcCatalog.id}"/>  
    <div>   
        <label for="code">类型代号:</label>   
        <input class="easyui-validatebox" type="text" name="sysRsRcCatalog.catalogCode" 
        style="width:200px;" data-options="required:true" />   
    </div>   
    <div>   
        <label for="name">类型名称:</label>   
        <input class="easyui-validatebox" type="text" name="sysRsRcCatalog.catalogName" 
        style="width:200px;" data-options="required:true" />   
    </div>
</form>
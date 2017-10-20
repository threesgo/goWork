<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="saveOrUpdate_resource_package" method="post" action="resourcePackageAction!saveOrUpdatePackage.act">
	<input type="hidden" name="sysRsrcPackage.id" value="${sysRsrcPackage.id}"/>  
   
    <div>   
        <label for="name">组合名称:</label>   
        <input class="easyui-validatebox" id="edit_packageName" type="text" name="sysRsrcPackage.name" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysRsrcPackage.name}"/>   
    </div>
    
    <div>   
        <label for="type">产品类别:</label>
      	<s:select id="type" 
      	list="packageTypeList" style="width:200px;height:22px"
       	listKey="value"   
       	listValue="name" 
       	name="sysRsrcPackage.packageType"
       	/>
   	</div>
</form>
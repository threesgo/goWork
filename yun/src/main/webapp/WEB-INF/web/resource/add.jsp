<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	
});
</script>
<form id="saveOrUpdate_resource" method="post" action="resourceAction!save.act">
	<input type="hidden" name="sysResource.rsrcCatalogId" value="${sysRsRcCatalog.id}"/>
    <div>   
        <label for="code">产品代号:</label>   
        <input id="code" class="easyui-validatebox" type="text" name="sysResource.rsrcCode" 
        style="width:200px;" data-options="required:true,validType:['length[1,10]','illegal']" value=""/>   
        <label for="name">产品名称:</label>   
        <input id="name" class="easyui-validatebox" type="text" name="sysResource.rsrcName" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value=""/>   
    </div>
    
    <div>   
        <label for="purchasePrice">采购价格:</label>   
        <input id="code" class="easyui-numberbox" type="text" name="sysResource.purchasePrice" 
        style="width:200px;" data-options="required:true,min:0,precision:2,max:9999999.99" value=""/>   
        <label for="salePrice">销售价格:</label>   
        <input id="name" class="easyui-numberbox" type="text" name="sysResource.salePrice" 
        style="width:200px;" data-options="required:true,min:0,precision:2,max:9999999.99" value=""/>   
    </div>
    
    <div>  
    	<label for="unitId">流程工种:</label> 
    	<s:select id="flow" 
       		list="flowList" style="width:200px;"
	       	listKey="value"   
	       	listValue="name"
	       	name="sysResource.workType" />
	</div>
    <!-- 
    <s:iterator value="attribCatalogs" id="attribCatalog" status="list">
    	<div>   
	        <label for="code">产品代号:</label>   
	        <input id="code" class="easyui-validatebox" type="text" name="sysResource.code" 
	        style="width:200px;" data-options="required:true,validType:['length[1,10]','illegal']" value="${sysResource.code}"/>   
	        <label for="name">产品名称:</label>   
	        <input id="name" class="easyui-validatebox" type="text" name="sysResource.name" 
	        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysResource.name}"/>   
	    </div>
    </s:iterator>
     -->
</form>
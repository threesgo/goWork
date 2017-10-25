<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="saveOrUpdate_order" method="post" action="sysOrderAction!saveOrUpdateOrder.act">
	<input type="hidden" name="sysOrder.id" value="${sysOrder.id}"/>  
    
    <div>   
        <label for="name">订单名称:</label>   
        <input class="easyui-validatebox" id="edit_name" type="text" name="sysOrder.name" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysOrder.name}"/>   
    </div>
    
    <div>   
        <label for="name">订单名称:</label>   
        <input class="easyui-validatebox" id="edit_name" type="text" name="sysOrder.name" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysOrder.name}"/>   
    </div>
</form>
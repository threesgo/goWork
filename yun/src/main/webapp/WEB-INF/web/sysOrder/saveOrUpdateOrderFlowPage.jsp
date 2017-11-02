<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="saveOrUpdate_orderFlow" method="post" action="sysOrderAction!saveOrUpdateOrderFlow.act">
	<input type="hidden" name="sysOrderFlow.id" value="${sysOrderFlow.id}"/>
	<input type="hidden" name="sysOrderFlow.orderId" value="${sysOrderFlow.orderId}"/>  
    <div>   
        <label for="edit_name">步骤名称:</label>   
        <input class="easyui-validatebox" id="edit_name" name="sysOrderFlow.name" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysOrderFlow.name}"/>   
    </div>
    
    <div>
    	<label for="edit_name">工程类别:</label>   
    	<s:select id="type" 
       		list="flowList" style="width:200px;height:22px"
	       	listKey="value"   
	       	listValue="name" 
	       	name="sysOrderFlow.flowType"
	       	/>
    </div>
    
    <div>   
        <label for="edit_startTime">开始时间:</label>   
        <input class= "easyui-datetimebox" id="edit_startTime" name="sysOrderFlow.startTime" 
        style="width:200px;" value="${sysOrderFlow.startTimeStr}"/>
    </div>
    
    <div>
    	<label for="edit_endTime">结束时间:</label>   
        <input class= "easyui-datetimebox" id="edit_endTime" name="sysOrderFlow.endTime" 
        style="width:200px;" value="${sysOrderFlow.endTimeStr}"/>  
    </div>
    
    <div>   
        <label for="edit_info">步骤备注:</label>   
        <input class="easyui-validatebox" id="edit_info" type="text" name="ysOrderFlow.info"
        style="width:460px;" data-options="required:false,validType:['length[1,600]','illegal']" value="${sysOrderFlow.info}"/>   
    </div>
</form>
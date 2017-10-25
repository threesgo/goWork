<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="saveOrUpdate_order" method="post" action="sysOrderAction!saveOrUpdateOrder.act">
	<input type="hidden" name="sysOrder.id" value="${sysOrder.id}"/>  
    <div>   
        <label for="edit_name">订单名称:</label>   
        <input class="easyui-validatebox" id="edit_name" type="text" name="sysOrder.name" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysOrder.name}"/>   
    </div>
    
    <div>   
        <label for="edit_totalArea">总面积:</label>   
        <input class="easyui-numberbox" id="edit_totalArea" type="text" name="sysOrder.totalArea" 
        style="width:200px;" data-options="required:true,min:1,precision:2max:9999999.99" value="${sysOrder.totalArea}"/>   
    </div>
    
    <div>   
        <label for="edit_totalAmount">总定价:</label>   
        <input class="easyui-numberbox" id="edit_totalAmount" type="text" name="sysOrder.totalAmount" 
        style="width:200px;" data-options="required:true,min:1,precision:2,max:9999999.99" value="${sysOrder.totalAmount}"/>   
    </div>
    
    <div>   
        <label for="edit_orderType">装修级别:</label>   
       	<s:select id="edit_orderType" style="height:22px;width:200px;"
       		list="dTypeList"
	       	listKey="value"   
	       	listValue="name" 
	       	headerKey="0"
	       	headerValue="--请选择--"/>
    </div>
    
    <div>   
        <label for="edit_contact">联系人:</label>   
        <input class="easyui-validatebox" id="edit_contact" type="text" name="sysOrder.contact" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysOrder.contact}"/>   
    </div>
    
    <div>   
        <label for="edit_contactTel">联系方式:</label>   
        <input class="easyui-validatebox" id="edit_contactTel" type="text" name="sysOrder.contactTel" 
        style="width:200px;" data-options="required:false,validType:['length[1,40]','illegal']" value="${sysOrder.contactTel}"/>   
    </div>
    
    <div>   
        <label for="edit_address">装修地址:</label>   
        <input class="easyui-validatebox" id="edit_address" type="text" name="sysOrder.address" 
        style="width:200px;" data-options="required:true,validType:['length[1,300]','illegal']" value="${sysOrder.address}"/>   
    </div>
    
    <div>   
        <label for="edit_startTime">预计开始时间:</label>   
        <input class= "easyui-datebox" required ="required" id="edit_startTime" name="sysOrder.startTime" 
        style="width:200px;" value="${sysOrder.startTime}"/>   
    </div>
    
     <div>   
        <label for="edit_endTime">预计结束时间:</label>   
        <input class= "easyui-datebox" required ="required" id="edit_endTime" name="sysOrder.endTime" 
        style="width:200px;" value="${sysOrder.endTime}"/>   
    </div>
    
    <div>   
        <label for="edit_info">备注:</label>   
        <input class="easyui-validatebox" id="edit_info" type="text" name="sysOrder.info"
        style="width:200px;" data-options="required:false,validType:['length[1,600]','illegal']" value="${sysOrder.info}"/>   
    </div>
</form>
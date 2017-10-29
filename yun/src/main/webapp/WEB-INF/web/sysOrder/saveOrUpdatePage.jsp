<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="saveOrUpdate_order" method="post" action="sysOrderAction!saveOrUpdateOrder.act">
	<input type="hidden" name="sysOrder.id" value="${sysOrder.id}"/>  
    <div>   
        <label for="edit_name">订单名称:</label>   
        <input class="easyui-validatebox" id="edit_name" name="sysOrder.name" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysOrder.name}"/>   
    
    	<label for="edit_orderType">装修级别:</label>   
   		<s:select id="edit_orderType" style="height:22px;width:100px;"
       		list="typeList"
	       	listKey="value"   
	       	listValue="name"
	       	name = "sysOrder.orderType"
	       	/>
    </div>
    
    <div>   
        <label for="edit_totalRooms">房间数量:</label>   
   	室:<input class="easyui-numberbox" id="edit_roomNum" type="text" name="sysOrder.roomNum" 
        style="width:30px;" data-options="required:true,min:0,precision:0,max:9999999" value="${sysOrder.roomNum}"/>
            厅:<input class="easyui-numberbox" id="edit_hallNum" type="text" name="sysOrder.hallNum" 
        style="width:30px;" data-options="required:true,min:0,precision:0,max:9999999" value="${sysOrder.hallNum}"/>   
            厨房:<input class="easyui-numberbox" id="edit_kitchenNum" type="text" name="sysOrder.kitchenNum" 
        style="width:30px;" data-options="required:true,min:0,precision:0,max:9999999" value="${sysOrder.kitchenNum}"/>   
            卫生间:<input class="easyui-numberbox" id="edit_toiletNum" type="text" name="sysOrder.toiletNum" 
        style="width:30px;" data-options="required:true,min:0,precision:0,max:9999999" value="${sysOrder.toiletNum}"/>   
            阳台:<input class="easyui-numberbox" id="edit_balconyNum" type="text" name="sysOrder.balconyNum" 
        style="width:30px;" data-options="required:true,min:0,precision:0,max:9999999" value="${sysOrder.balconyNum}"/>   
    </div>
    
    <div>   
   	 	<label for="edit_totalArea">订单面积:</label>   
        <input class="easyui-numberbox" id="edit_totalArea" name="sysOrder.totalArea" 
        style="width:200px;" data-options="required:true,min:1,precision:2,max:9999999.99" value="${sysOrder.totalArea}"/>   
   		㎡
    </div>
    
    <div>   
   	 	<label for="edit_totalAmount">订单总价:</label>   
        <input class="easyui-numberbox" id="edit_totalAmount" type="text" name="sysOrder.totalAmount" 
        style="width:200px;" data-options="required:true,min:1,precision:2,max:9999999.99" value="${sysOrder.totalAmount}"/>   
    	元
    </div>
    
    <div>   
        <label for="edit_contact">联&nbsp;&nbsp;系&nbsp;人:</label>   
        <input class="easyui-validatebox" id="edit_contact" type="text" name="sysOrder.contact" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysOrder.contact}"/>   
    </div>
    
    <div>   
       	<label for="edit_contactTel">联系方式:</label>   
        <input class="easyui-validatebox" id="edit_contactTel" type="text" name="sysOrder.contactTel" 
        style="width:200px;" data-options="required:false,validType:['length[1,40]','illegal']" value="${sysOrder.contactTel}"/> 
    </div>
    
    <!-- 
  	<div>   
        <label for="edit_address">省&nbsp;&nbsp;市&nbsp;区:</label> 
        <select id="cmbProvince" name="sysOrder.cmbProvince" value="${sysOrder.cmbProvince}"></select>  
    	<select id="cmbCity" name="sysOrder.cmbCity" value="${sysOrder.cmbCity}"></select>  
    	<select id="cmbArea" name="sysOrder.cmbArea" value="${sysOrder.cmbArea}"></select>  
    </div>
     -->
     
    <div>   
        <label for="edit_address">装修地址:</label> 
        <input class="easyui-validatebox" id="edit_address" placeholder="康桥镇御青路1051弄25号601室" type="text" name="sysOrder.address" 
        style="width:460px;" data-options="required:true,validType:['length[1,300]','illegal']" value="${sysOrder.address}"/>   
    </div>
    
    <div>   
        <label for="edit_startTime">开始时间:</label>   
        <input class= "easyui-datetimebox" required ="required" id="edit_startTime" name="sysOrder.startTime" 
        style="width:200px;" value="${sysOrder.startTimeStr}"/>
        
        <label for="edit_endTime">结束时间:</label>   
        <input class= "easyui-datetimebox" required ="required" id="edit_endTime" name="sysOrder.endTime" 
        style="width:200px;" value="${sysOrder.endTimeStr}"/>    
    </div>
    
    <div>   
        <label for="edit_info">订单备注:</label>   
        <input class="easyui-validatebox" id="edit_info" type="text" name="sysOrder.info"
        style="width:460px;" data-options="required:false,validType:['length[1,600]','illegal']" value="${sysOrder.info}"/>   
    </div>
</form>

<script type="text/javascript"> 
/* 
$(function(){
	addressInit('cmbProvince', 'cmbCity', 'cmbArea');
	$("#cmbProvince option[value='四川']").click();
	$("#cmbCity option[value='成都市']").attr("selected", "selected");
	$("#cmbArea option[value='金牛区']").attr("selected", "selected");
});
*/
</script>  
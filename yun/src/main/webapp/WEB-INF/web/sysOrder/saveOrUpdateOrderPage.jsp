<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="saveOrUpdate_order" method="post" action="sysOrderAction!saveOrUpdateOrder.act">
	<input type="hidden" name="sysOrder.id" value="${sysOrder.id}"/>
	<input type="hidden" id="orderPackages" name="sysOrder.orderPackages" value=""/>
	<input type="hidden" id="relationMemberId" name="sysOrder.relationMemberId" value="${sysOrder.relationMemberId}"/>   
	
	<div>   
        <label for="edit_name">关联会员:</label>
        <input id="relationMemberName" value="${sysOrder.relationMemberName}" disabled="disabled"/>   
        <a href="#" class="easyui-linkbutton " data-options="iconCls:'icon-search', plain:true" onclick="selectSearchMember()"></a>
    </div>
	
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
   	室:<input id="edit_roomNum" name="sysOrder.roomNum" style="width:50px;" value="${sysOrder.roomNum}"/>
            厅:<input id="edit_hallNum" name="sysOrder.hallNum" style="width:50px;" value="${sysOrder.hallNum}"/>   
            厨房:<input id="edit_kitchenNum" name="sysOrder.kitchenNum" style="width:50px;" value="${sysOrder.kitchenNum}"/>   
            卫生间:<input id="edit_toiletNum" name="sysOrder.toiletNum" style="width:50px;" value="${sysOrder.toiletNum}"/>   
            阳台:<input id="edit_balconyNum" name="sysOrder.balconyNum" style="width:50px;" value="${sysOrder.balconyNum}"/>   
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
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysOrder.contactTel}"/> 
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
        <input class= "easyui-datebox" required ="required" id="edit_startTime" name="sysOrder.startTime" 
        style="width:200px;" value="${sysOrder.startTimeStr}"/>
        
        <label for="edit_endTime">结束时间:</label>   
        <input class= "easyui-datebox" required ="required" id="edit_endTime" name="sysOrder.endTime" 
        style="width:200px;" value="${sysOrder.endTimeStr}"/>    
    </div>
   
    <div>   
        <label for="relation_package">关联套餐:</label>   
       	<input id="relationPackage" class="easyui-combobox">
    </div>
    
    <div>   
        <label for="edit_info">订单备注:</label>   
        <input class="easyui-textbox" id="edit_info" type="text" name="sysOrder.info"
        style="width:460px;height: 80px" data-options="multiline:true,required:false,validType:['length[1,600]','illegal']" value="${sysOrder.info}"/>   
    </div>
</form>

<script type="text/javascript"> 
$(function(){
	$('#relationPackage').combobox({    
	    data:${hashMap.packageArr},    
	    valueField:'id',    
	    textField:'text',
	    //multiple:true,
	    editable:false,
	    required:true,
	    onLoadSuccess:function(){
	    	//$('#relationPackage').setValues();
	    }
	}); 
	
	$('#edit_roomNum').numberspinner({    
	    required:false,    
	    increment:1,
	    min:0,
	    max:100
	});
	
	$('#edit_hallNum').numberspinner({    
	    required:false,    
	    increment:1,
	    min:0,
	    max:100
	});
	
	$('#edit_kitchenNum').numberspinner({    
	    required:false,    
	    increment:1,
	    min:0,
	    max:100
	});
	
	$('#edit_toiletNum').numberspinner({    
	    required:false,    
	    increment:1,
	    min:0,
	    max:100
	});
	
	$('#edit_balconyNum').numberspinner({    
	    required:false,    
	    increment:1,
	    min:0,
	    max:100
	});
});

function selectSearchMember(){
	var memberDialog = $('<div id="selectSearchMember"></div>').dialog({    
		href : "sysMemberAction!listPage.act",
		width:700,
		height:450,
		title:"选择关联会员",
		method:'post',
		modal:true,
		resizable:true,
		buttons:[{
			text:"确定",
			iconCls:'icon-ok',
			handler:function(){
				 var selectNode = $('#sysMemberTable').datagrid("getSelected");
				 $("#relationMemberId").val(selectNode.id);
				  $("#relationMemberName").val(selectNode.userName);
				 memberDialog.dialog("destroy");
			}
		},{
			text:"取消",
			iconCls:'icon-cancel',
			handler:function(){
				memberDialog.dialog("destroy");
			}
		}],
		onClose:function(){
			$(this).dialog("destroy");
		}
	});
}

/* 
$(function(){
	addressInit('cmbProvince', 'cmbCity', 'cmbArea');
	$("#cmbProvince option[value='四川']").click();
	$("#cmbCity option[value='成都市']").attr("selected", "selected");
	$("#cmbArea option[value='金牛区']").attr("selected", "selected");
});
*/
</script>  
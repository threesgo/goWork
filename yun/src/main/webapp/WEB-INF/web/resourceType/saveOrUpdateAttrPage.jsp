<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$("#groupId").change(function(){
		$("#unitId").find("option").remove();
		$("#groupId").next(".loading").addClass("span-loading");
		$.post("resourceTypeAction!findSysRcBaseDataTypeByGroup.act",{"id":$("#groupId").val()},function(data){
			$("#groupId").next(".loading").removeClass("span-loading");
			if(data && data.length){
				$.each(data,function(index,item){
					$("#unitId").append("<option class='option' value='"+item.id+"'>"+item.displayName+"</option>");											
				});										
			}
		},"json");
	});
	
	$("#dataLength").numberbox({
		onChange:function(newValue,oldValue){
			var dataPrecisionMax = parseInt(newValue)-1;
			$('#dataPrecision').numberbox({    
			    min:0,    
			    precision:0,
			    max:dataPrecisionMax    
			}); 
		}
	});
});
</script>

<form id="saveOrUpdate_resource_attr" method="post" action="resourceTypeAction!saveOrUpdateAttr.act">
	<input type="hidden" name="sysRsRcAttribCatalog.rsrcCatalogId" value="${sysRsRcCatalog.id}"/>
	<input type="hidden" name="sysRsRcAttribCatalog.id" value="${sysRsRcAttribCatalog.id}"/>
    <div>   
        <label for="code">属性代号:</label>   
        <input id="code" class="easyui-validatebox" type="text" name="sysRsRcAttribCatalog.rsrcAttribCode" 
        style="width:200px;" data-options="required:true,validType:['length[1,10]','illegal']" value="${sysRsRcAttribCatalog.rsrcAttribCode}"/>   
        <label for="name">属性名称:</label>   
        <input id="name" class="easyui-validatebox" type="text" name="sysRsRcAttribCatalog.rsrcAttribName" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysRsRcAttribCatalog.rsrcAttribName}"/>   
    </div>
    
    <div>   
        <label for="dataTypeId">数据类型:</label>   
        <s:select id="dataTypeId" 
        	list="dataTypeList" style="width:200px;"
	       	listKey="id"   
	       	listValue="displayName"
	       	name="sysRsRcAttribCatalog.dataTypeId" />
        <label for="controlTypeId">控件类型:</label>
        <s:select id="controlTypeId" 
        	list="controlTypeList" style="width:200px;"
	       	listKey="id"   
	       	listValue="displayName" 
	       	name="sysRsRcAttribCatalog.controlTypeId" />   
    </div>
    
    <div>
        <label for="dataLength">数据长度:</label>   
        <input id="dataLength" class="easyui-numberbox" type="text" name="sysRsRcAttribCatalog.dataLength" 
        style="width:200px;" data-options="required:false,min:1,precision:0,max:256" value="${sysRsRcAttribCatalog.dataLength}"/>   
        <label for="dataPrecision">小数位数:</label>   
        <input id="dataPrecision" class="easyui-numberbox" type="text" name="sysRsRcAttribCatalog.dataPrecision" 
        style="width:200px;" value="${sysRsRcAttribCatalog.dataPrecision}"/>   
    </div>
    
    <div>
       	<label for="unitId">数据单位:</label>
       	<s:select id="groupId" 
       		list="unitGroupList" style="width:98px;"
	       	listKey="group"   
	       	listValue="group" 
	       	headerKey="0"
	       	headerValue="--请选择--"/>   
       	<s:select id="unitId" 
       		list="unitList" style="width:98px;"
	       	listKey="id"   
	       	listValue="displayName"
	       	headerKey="0"
	       	headerValue="--请选择--"
	       	name="sysRsRcAttribCatalog.unitId" />   
    
        <label for="showInListView">列表显示:</label>   
	    <select id="showInListView" class="easyui-combobox" name="sysRsRcAttribCatalog.showInListView" style="width:200px;">   
		    <option value="1" <s:if test="sysRsRcAttribCatalog.showInListView==1"> selected=‘selected’</s:if>>是</option>
			<option value="0" <s:if test="sysRsRcAttribCatalog.showInListView==0"> selected=‘selected’</s:if>>否</option>   
		</select>  
    </div>
    
    <div>
        <label for="showInFinder">查找显示:</label>   
	    <select id="showInFinder" class="easyui-combobox" name="sysRsRcAttribCatalog.showInFinder" style="width:200px;">   
		    <option value="1" <s:if test="sysRsRcAttribCatalog.showInFinder==1"> selected=‘selected’</s:if>>是</option>
			<option value="0" <s:if test="sysRsRcAttribCatalog.showInFinder==0"> selected=‘selected’</s:if>>否</option>   
		</select>  
    
        <label for="orderNo">属性顺序:</label>   
        <input id="orderNo" class="easyui-numberbox" type="text" name="sysRsRcAttribCatalog.orderNo" 
        style="width:200px;" data-options="required:true,min:1,precision:0" value="${sysRsRcAttribCatalog.orderNo}"/>   
    </div>
    
    <!-- “1|2|3” 选择框需要输入 -->
    <div>
        <label for="defaultValue">默认选项:</label>   
        <input id="defaultValue" class="easyui-validatebox" type="text" name="sysRsRcAttribCatalog.defaultValue" 
        style="width:200px;" data-options="required:false,validType:['length[1,1024]','illegal']" value="${sysRsRcAttribCatalog.defaultValue}"/>   
    </div>
</form>
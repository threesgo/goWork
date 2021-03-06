<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="saveOrUpdate_resource_type" method="post" action="resourceTypeAction!saveOrUpdateType.act">
	<input type="hidden" name="sysRsRcCatalog.parentId" value="${sysRsRcCatalog.parentId}"/>
	<input type="hidden" name="sysRsRcCatalog.id" value="${sysRsRcCatalog.id}"/>  
    <!-- 
    <div>   
        <label for="code">类型编号:</label>   
        <input class="easyui-validatebox" type="text" name="sysRsRcCatalog.catalogCode" 
        style="width:200px;" data-options="required:false,validType:['length[1,10]','illegal']" value="${sysRsRcCatalog.catalogCode}"/>   
    </div> 
     -->  
    <div>   
        <label for="name">类型名称:</label>   
        <input class="easyui-validatebox" id="edit_catalogName" type="text" name="sysRsRcCatalog.catalogName" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysRsRcCatalog.catalogName}"/>   
    </div>
    
    <div>   
        <label for="type">产品类别:</label>
        <!--    
        <select id="type" class="easyui-combobox" name="sysRsRcCatalog.catalogType" style="width:200px;">   
		    <option value="1" <s:if test="sysRsRcCatalog.catalogType==1"> selected=‘selected’</s:if>>产品</option>
			<option value="2" <s:if test="sysRsRcCatalog.catalogType==2"> selected=‘selected’</s:if>>工人</option>   
		</select>
		 -->
       	<s:if test="sysRsRcCatalog.parentId==0">
       	 	<s:select id="type" 
       		list="catalogTypeList" style="width:200px;height:22px"
	       	listKey="value"   
	       	listValue="name" 
	       	name="sysRsRcCatalog.catalogType"
	       	/>
       	</s:if>
       	<s:else>
       		<input class="easyui-validatebox" style="width:200px;" type="text" value="${sysRsRcCatalog.catalogTypeName}" readonly="readonly"/>   
       	</s:else>
     </div>
     
     
     <div>   
        <label for="type">做工种类:</label>
       		<s:if test="sysRsRcCatalog.parentWorkType==1">
		        <select id="type" class="easyui-combobox" name="sysRsRcCatalog.workType" style="width:200px;">   
				    <option value="1" <s:if test="sysRsRcCatalog.workType==1"> selected=‘selected’</s:if>>材料项</option>
					<option value="2" <s:if test="sysRsRcCatalog.workType==2"> selected=‘selected’</s:if>>做工项</option>   
				</select>
			</s:if>
			<s:else>
				<select id="type" class="easyui-combobox" name="sysRsRcCatalog.workType" style="width:200px;">   
					<option value="2">做工项</option>   
				</select>       		
			</s:else>
     </div>
</form>
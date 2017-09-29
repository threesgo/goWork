<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var $roleDataGrid;
$(function(){
	$roleDataGrid=$("#roleList").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        rownumbers:true,
        fit:true,
        animate:true,
 		collapsible:true,
 		data:eval('${roleListStr}'),
		onBeforeLoad:function(){
		},
		columns : [[
		{
			field : 'name',
			title : "角色名称",
			width : '250',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			}
		}] ],
		onDblClickRow : function(index, row) {
			selectRole(row,selectRoleDialog);
		}
	});
});
</script>

<div id="roleList"></div>

<!-- 
<div align="center" style="margin-top: 30px">
	<span>角色列表:</span>
	<select id="roleSelect"  style="width:150px;">   
	    <s:iterator value="roleList" var="role">
			<option value='${id}'>${name}</option>
		</s:iterator>
	</select>  
</div>
 -->   
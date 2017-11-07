<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
(function($){
	var columns = [{field:'name',title:'角色列表',sortable:false,width:150,
		formatter: function(value,row,index){
			
			return row.levelName;
			if(row.parentId){
				return "&nbsp;&nbsp;&nbsp;&nbsp;"+value;
			}else{
				return value;
			}
			
		}}];
	
	
	function getEmpty(num){
		var arr =[];
		while(num-->0){
			arr.push("&nbsp;&nbsp;&nbsp;&nbsp;")
		}
		return arr.join("");
	}
	
	var list = new Function("return " + '${roles}')();
	$.each(list,function(i,ele){
        columns.push({field:'role_'+ele.id, title:ele.name, width:200, sortable:false,
        	formatter: function(value,row,index){
        		if(row.parentId){
        			if(value){
	        			return '<input type="checkbox" checked="checked" onclick="return false;"/>';
        			}else{
	        			return '<input type="checkbox" onclick="return false;" value='+value+'/>';
        			}
        		}
			} 
        });
	});
	
	function loadData(data){
		var mapCache ={};
		$.each(data,function(index,item){
			mapCache["_"+item.id]=item;
			var _parent=mapCache["_"+item.parentId];
			if(_parent){
				item.level=(_parent.level||0)+1;
			}else{
				item.level=0;
			}
			item.levelName=getEmpty(item.level)+item.name;
		});
		
		return data;
	}
	var data = loadData(${menus});
	
	$("#roleTable").datagrid({
		 	fit : true,
		    fitColumns : true,
		    method:"post",
		    border : false,
		    rownumbers : true,
		    singleSelect : false,
			selectOnCheck : true,
			remoteSort : false,
			toolbar:"#operation_bar",
			striped : true,
			pagination : false,
			columns : [columns]
		}).datagrid("loadData",data); 
	
})(jQuery);
</script>
<div id="roleTable"></div>

<div  id="operation_bar">
	<s:if test='#session.defaultMenu.sysRoleActionAdd1==1'>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="append()">新增</a>
	</s:if>
    
</div> 
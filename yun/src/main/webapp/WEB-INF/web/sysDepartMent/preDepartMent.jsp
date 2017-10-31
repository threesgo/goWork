<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $sysDepartMentTable;
var departMentEdit = undefined;
var addId = 0; 
$(function(){
	$sysDepartMentTable=$("#sysDepartMentTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#sys_DepartMent_tool_bar",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
      	url:"sysDepartMentAction!listDepartMent.act",
		onBeforeLoad:function(){
		},
		columns : [ [
		{
			field : 'code',
			title : "部门编号",
			width : '49%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			},
			editor:{
				type:'textbox',
				options:{required:false,validType:['length[1,64]','illegal']}
			}
			
		},
		{
			field : 'name',
			title : "部门名称",
			width : '49%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			},
			editor:{
				type:'textbox',
				options:{required:false,validType:['length[1,64]','illegal']}
			}
		}
		]],
		onDblClickCell:function(index, field, value) {
	    	if(departMentEdit!=undefined){
	    		if($sysDepartMentTable.datagrid("validateRow",departMentEdit)){
	    			$sysDepartMentTable.datagrid("endEdit",departMentEdit);
	    		}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
	    	}
	    	$sysDepartMentTable.datagrid('editCell',{index:index,field:field});
            departMentEdit = index;
        },
        onAfterEdit:function(rowIndex,rowData,changes){
	    	$.post("sysDepartMentAction!saveOrUpdatePositionGrid.act",
	    			{"jsonStr":Some.util.jsonToStr(rowData),"departMentId":'${departMentId}'},
       			 function(data){
      			 	handlerResult(data,
      			 		function(json){
							$show(json.message);
							$sysDepartMentTable.datagrid('updateRow',{
								index: rowIndex,
								row: {
									id:json.data.id,
								}
							});
						},
						function(json){
							$show(json.message);
						}
					);
       			}
	    	);
        }
	});
});

sysDepartMenttHandle={
		//新增职位
		addDepartMent:function(){
			if(departMentEdit!=undefined){
				if($sysDepartMentTable.datagrid("validateRow",departMentEdit)){
					$sysDepartMentTable.datagrid("endEdit",departMentEdit);
					departMentEdit = undefined;
				}else{
					$show("请正确输入编辑行数据!");
					return false;
				}
			}
			var rows = $sysDepartMentTable.datagrid("getData").rows;
			var addIndex = rows.length;
			$sysDepartMentTable.datagrid('insertRow',{
				index: addIndex,	// 索引从0开始
				row: {
					id:addId--,
				}
			});
			$sysDepartMentTable.datagrid("beginEdit",addIndex);
			departMentEdit = addIndex;
		},

		editDepartMent:function(){
			//不需要页面，直接表格编辑
			var selected = $sysDepartMentTable.datagrid("getSelected");
			if(null == selected){
				$alert("请单选行进行编辑!");
				return false;
			}
			if(departMentEdit!=undefined){
	    		if($sysDepartMentTable.datagrid("validateRow",departMentEdit)){
	    			$sysDepartMentTable.datagrid("endEdit",departMentEdit);
	    		}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
			var index = $sysDepartMentTable.datagrid("getRowIndex",selected);
			$sysDepartMentTable.datagrid("beginEdit",index);
			departMentEdit = index;
		},
		cancelEdit:function(){
			$sysDepartMentTable.datagrid("reload");
			departMentEdit = undefined;
		},
		deleteDepartMent:function(){
			var checks = $sysDepartMentTable.datagrid("getChecked");
			if(checks.length == 0){
				$alert("请勾选需要删除部门!");
				return false;
			}
			$.messager.confirm('确认','确认要删除勾选的部门吗？',function(r){    
			    if (r){
			    	var ids = [];
			    	var deletes = [];
			    	$.each(checks,function(i,n){
			    		ids.push(n.id);
			    		deletes.push(n);
			    	});
			        $.post("sysDepartMentAction!deletePosition.act",
			        	{"ids":ids.join(",")},
			        	function(data){
						handlerResult(data,
				    		function(rs){
								$show(rs.message);
								$.each(deletes,function(i,n){
									var deleteIndex = $sysDepartMentTable.datagrid("getRowIndex",n);
									$sysDepartMentTable.datagrid("deleteRow",deleteIndex);
						    	});
							},
							function(rs){
								$alert(rs.message);
							}
						);  
					},"json");
			    }    
			});
		},
		updateDepartMent:function(){
			if(departMentEdit!=undefined){
				if($sysDepartMentTable.datagrid("validateRow",departMentEdit)){
					$sysDepartMentTable.datagrid("endEdit",departMentEdit);
					departMentEdit = undefined;
				}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
		},
		
	}
</script>
<table id="sysDepartMentTable"></table>
<div id="sys_DepartMent_tool_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysDepartMenttHandle.addDepartMent()">新增</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysDepartMenttHandle.editDepartMent()">编辑</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" onclick="sysDepartMenttHandle.updateDepartMent()">保存</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-quxiao', plain:true" onclick="sysDepartMenttHandle.cancelEdit()">取消编辑</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="sysDepartMenttHandle.deleteDepartMent()">删除</a>
</div>

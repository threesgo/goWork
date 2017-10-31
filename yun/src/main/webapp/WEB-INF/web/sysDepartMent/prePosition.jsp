<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $sysPositionTable;
var positionEdit = undefined;
var addId = 0;
$(function(){
	$sysPositionTable=$("#sysPositionTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        nowrap:false,
        fit:true,
        toolbar:"#sys_position_tool_bar",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
      	url:"sysDepartMentAction!findPosition.act?departMentId="+'${departMentId}',
		onBeforeLoad:function(){
		},
		columns : [ [
		 {field:'ck',checkbox:true},            
		{
			field : 'code',
			title : "职位代号",
			width : '32%',
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
			title : "职位名称",
			width : '32%',
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
			field : 'positionType',
			title : "职位类别",
			width : '32%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			},
			formatter: function(value,row,index){
				if (value==1){
					return "总监";
				}else if(value==2){
					return "经理";
				}else if(value==3){
					return "部长";
				}
			}
			,editor:{
            	type:'combobox',
             	options:{
                  	  valueField:'id', 
                      textField:'name',
                      editable:false,
                      data:[{    
                    	    "id":1,    
                    	    "name":"职员"   
                    	},{    
                    	    "id":2,    
                    	    "name":"经理"   
                    	},{    
                    	    "id":4,    
                    	    "name":"总监",    
                    	    "selected":false   
                    	}],  
                      panelHeight:'auto',
                      required:true,
                      onLoadSuccess: function () {
                    	 
                      }	
         	 	}
			},

		}
		]],
		onDblClickCell:function(index, field, value) {
	    	if(positionEdit!=undefined){
	    		if($sysPositionTable.datagrid("validateRow",positionEdit)){
	    			$sysPositionTable.datagrid("endEdit",positionEdit);
	    		}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
	    	}
	    	$sysPositionTable.datagrid('editCell',{index:index,field:field});
            positionEdit = index;
        },
        onAfterEdit:function(rowIndex,rowData,changes){
	    	$.post("sysDepartMentAction!saveOrUpdatePositionGrid.act",
	    			{"jsonStr":Some.util.jsonToStr(rowData),"departMentId":'${departMentId}'},
       			 function(data){
      			 	handlerResult(data,
      			 		function(json){
							$show(json.message);
							$sysPositionTable.datagrid('updateRow',{
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
	
 	$("#sysPositionTable").parent(".datagrid-view").keyEvent({
		keyCode:13,
		handler:function(event){
			sysPositionHandle.updatePosition();
			event.preventDefault();
		}
	});
 	
});
sysPositionHandle={
	//新增职位
	addPosition:function(){
		if(positionEdit!=undefined){
			if($sysPositionTable.datagrid("validateRow",positionEdit)){
				$sysPositionTable.datagrid("endEdit",positionEdit);
				positionEdit = undefined;
			}else{
				$show("请正确输入编辑行数据!");
				return false;
			}
		}
		var rows = $sysPositionTable.datagrid("getData").rows;
		var addIndex = rows.length;
		$sysPositionTable.datagrid('insertRow',{
			index: addIndex,	// 索引从0开始
			row: {
				id:addId--,
			}
		});
		$sysPositionTable.datagrid("beginEdit",addIndex);
		positionEdit = addIndex;
	},

	editPosition:function(){
		//不需要页面，直接表格编辑
		var selected = $sysPositionTable.datagrid("getSelected");
		if(null == selected){
			$alert("请单选行进行编辑!");
			return false;
		}
		if(positionEdit!=undefined){
    		if($sysPositionTable.datagrid("validateRow",positionEdit)){
    			$sysPositionTable.datagrid("endEdit",positionEdit);
    		}else{
				$show("请正确输入编辑行数据!");
				return false;	    		
    		}
		}
		var index = $sysPositionTable.datagrid("getRowIndex",selected);
		$sysPositionTable.datagrid("beginEdit",index);
		positionEdit = index;
	},
	cancelEdit:function(){
		$sysPositionTable.datagrid("reload");
		positionEdit = undefined;
	},
	deletePosition:function(){
		var checks = $sysPositionTable.datagrid("getChecked");
		if(checks.length == 0){
			$alert("请勾选需要删除职位!");
			return false;
		}
		$.messager.confirm('确认','确认要删除勾选的职位吗？',function(r){    
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
								var deleteIndex = $sysPositionTable.datagrid("getRowIndex",n);
								$sysPositionTable.datagrid("deleteRow",deleteIndex);
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
	updatePosition:function(){
		if(positionEdit!=undefined){
			if($sysPositionTable.datagrid("validateRow",positionEdit)){
				$sysPositionTable.datagrid("endEdit",positionEdit);
				positionEdit = undefined;
			}else{
				$show("请正确输入编辑行数据!");
				return false;	    		
    		}
		}
	},
	
}

</script>
<table id="sysPositionTable"></table>
<div id="sys_position_tool_bar">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysPositionHandle.addPosition()">新增</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysPositionHandle.editPosition()">编辑</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" onclick="sysPositionHandle.updatePosition()">保存</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-quxiao', plain:true" onclick="sysPositionHandle.cancelEdit()">取消编辑</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="sysPositionHandle.deletePosition()">删除</a>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var $sysDepartMentTable;
var nowSelectIndex = undefined;//实现单选下再次选中取消选择
$(function(){
	var nodeSelected = departMentTree.tree("getSelected");
	id = nodeSelected.id;
	$sysDepartMentTable=$("#sysDepartMentTable").datagrid({
        fitColumns:true,
        remoteSort:false,
        striped:true,
        singleSelect:true,
        checkOnSelect:false,
        selectOnCheck:false,
        nowrap:false,
        fit:true,
        toolbar:"#sys_DepartMent_tool_bar",
        animate : true,
 		collapsible : true,
  		pagination:false, 
  		pageSize:20,  
        pageList:[10,20,50,100,150,200],
      	url:"sysDepartMentAction!listChildrenDepartMent.act?parentId="+'${parentId}',
		onBeforeLoad:function(){
		},
		onClickRow:function(index, row){
			nowSelectIndex = index;
		},
		onSelect:function(index, row){
			if(index == nowSelectIndex){
				$sysDepartMentTable.datagrid("unselectRow",index)
			}
		},
		columns : [ [
		{field:'',checkbox:true},
		{
			field : 'code',
			title : "部门编号",
			width : '47%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			},
			/*editor:{
				type:'textbox',
				options:{required:true,validType:['length[1,64]','illegal']}
			}*/
			
		},
		{
			field : 'name',
			title : "部门名称",
			width : '47%',
			sortable : true,
			sorter : function(a, b) {
				return a > b ? 1 : -1;
			},
		}
		]],
		onDblClickCell:function(index, field, value) {
	    	
        }
        
       
	});
});


sysDepartMenttHandle={
		
		//编辑
		editDepartMent:function(){
			var selected = $sysDepartMentTable.datagrid("getSelected");
			if(null == selected){
				$alert("请单选行进行编辑!");
				return false;
			}
			var addDepartMentDilog=$('<div id="editDepartMent"></div>').dialog({
				title:"<s:text name='编辑部门'/>",
				width:400,
				top:160,
				height:"auto",
				resizable:true,
				modal:true,
				href:"sysDepartMentAction!preAddOrEdit.act",
				queryParams:{"sysDepartMent.id":selected.id},
				method:"post", 
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$("#_fm").form({
							success:function(data){
								handlerResult(data,function(rs){
									$show(rs.message);
									var departMent = departMentTree.tree("getRoot");
									departMentTree.tree('reload',departMent.target);
									$sysDepartMentTable.datagrid("reload");
									addDepartMentDilog.dialog("close");
								},function(rs){
									$alert(rs.message);
								});
							}
						}).submit(); 
					}
				},{
					text:"取消",
					iconCls:'icon-cancel',
					handler:function(){
						addDepartMentDilog.dialog("destroy");
					}
				}],
				onClose:function(){
					addDepartMentDilog.dialog("destroy");
					return true;
				}
			});
			
		},
		
		//删除
		deleteDepartMent:function(){
			var checks = $sysDepartMentTable.datagrid("getChecked");
			var ids = [];
			if(checks.length == 0){
				$alert("请勾选需要删除部门!");
				return false;
			}
			$.each(checks,function(i,n){
	    		ids.push(n.id);
	    	});
			id = ids.join(",");
			del();
		}
		
	}
</script>
<table id="sysDepartMentTable"></table>
<div id="sys_DepartMent_tool_bar">
	<s:if test='#session.defaultMenu.sysDepartMentActionAddDepart==1'>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="append()">新增</a>
	</s:if>
	<s:if test='#session.defaultMenu.sysDepartMentActionEditDepart==1'>
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysDepartMenttHandle.editDepartMent()">编辑</a>
	</s:if>
	<s:if test='#session.defaultMenu.sysDepartMentActionDeleteDepart==1'>
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="sysDepartMenttHandle.deleteDepartMent()">删除</a>
	</s:if>
</div>

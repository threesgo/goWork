<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/public/public.jsp" />
<head>
<script type="text/javascript">
	var $sysBrandDatagrid;	
	var sysBrandOperation = {};
	
	var sysBrandEdit = undefined;
	var addId = 0;
	$(function(){
		$sysBrandDatagrid=$('#sysBrandTable').datagrid({
			fitColumns:false,
	        striped:true,
	        singleSelect:true,
	        fit:true,
	        nowrap:false,
	        idField:"id",
	        remoteSort:false,
	        multiSort:true,
	        rownumbers : true,
	        selectOnCheck:false,
	        checkOnSelect:false,
	        pageSize:20,
	        pageList:[20,50,100,150,200],
	        pagination:true,
            toolbar:"#sysBrand_tool_bar",
            url:"sysBrandAction!listData.act",
			columns:[[
				{field:'ck',checkbox:true},
				{field:'name',title: "品牌名称",width:200,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:true,validType:['length[1,80]','illegal']}
		        	}
				},
				{field:'info',title: "品牌描述",width:600,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,300]','illegal']}
		        	}
				}
			]],
			onDblClickCell:function(index, field, value) {
				<s:if test="#session.defaultMenu.sysBrandActionEdit==1">
			    	if(sysBrandEdit!=undefined){
			    		if($sysBrandDatagrid.datagrid("validateRow",sysBrandEdit)){
			    			$sysBrandDatagrid.datagrid("endEdit",sysBrandEdit);
			    		}else{
							$show("请正确输入编辑行数据!");
							return false;	    		
			    		}
			    	}
			    	$sysBrandDatagrid.datagrid('editCell',{index:index,field:field});
		            sysBrandEdit = index;
	            </s:if>
	        },
	        onAfterEdit:function(rowIndex,rowData,changes){
		    	$.post("sysBrandAction!saveOrUpdateBrandGrid.act",
		    			{"jsonStr":Some.util.jsonToStr(rowData)},
	       			 function(data){
	      			 	handlerResult(data,
	      			 		function(json){
								$show(json.message);
								$sysBrandDatagrid.datagrid('updateRow',{
									index: rowIndex,
									row: {
										id:json.data.id,
									}
								});
							},
							function(json){
								$sysBrandDatagrid.datagrid("beginEdit",rowIndex);
								$sysBrandDatagrid = rowIndex;
								$show(json.message);
							}
						);
	       			}
		    	);
	        }
	  	});
	  	
	  	$("#sysBrandTable").parent(".datagrid-view").keyEvent({
			keyCode:13,
			handler:function(event){
				sysBrandOperation.updateSysBrand();
				event.preventDefault();
			}
		});
	 	
	 	$("#sysBrand_tabel_search").keyEvent({
			keyCode:13,
			handler:function(event){
				sysBrandOperation.search();
				event.preventDefault();
			}
		});
	});
	
	sysBrandOperation={
		relationSysBrand:function(){
			var selected = $sysBrandDatagrid.datagrid("getSelected");
			if(null == selected){
				$alert("请单选品牌行，设置关联关系!");
				return false;
			}
			var dialog = $('<div id="relationsysBrand"></div>').dialog({    
				href : "sysBrandAction!relationCatalog.act?sysBrand.id="+selected.id,
				width:600,
				height:380,
				title:"编辑关联关系",
				method:'post',
				modal:true,
				resizable:true,
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						var nodes=brandResourceTypeTree.tree("getChecked",["checked","indeterminate"]);
						var ids=[];
						$.each(nodes,function(index,node){
							ids.push(node.id);
						});
						$.post("sysBrandAction!updateRelationCatalog.act",
				    			{"ids":ids.join(","),"sysBrand.id":selected.id},
			       			 function(data){
			      			 	handlerResult(data,
			      			 		function(json){
										$show(json.message);
										dialog.dialog("destroy");
									},
									function(json){
										$alert(json.message);
									}
								);
			       			}
				    	);
					}
				},{
					text:"取消",
					iconCls:'icon-cancel',
					handler:function(){
						dialog.dialog("destroy");
					}
				}],
				onClose:function(){
					$(this).dialog("destroy");
				}
 			});
		},
		updateSysBrand:function(){
			if(sysBrandEdit!=undefined){
				if($sysBrandDatagrid.datagrid("validateRow",sysBrandEdit)){
					$sysBrandDatagrid.datagrid("endEdit",sysBrandEdit);
					sysBrandEdit = undefined;
				}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
		},
		addSysBrand:function(){
			if(sysBrandEdit!=undefined){
				if($sysBrandDatagrid.datagrid("validateRow",sysBrandEdit)){
					$sysBrandDatagrid.datagrid("endEdit",sysBrandEdit);
					sysBrandEdit = undefined;
				}else{
					$show("请正确输入编辑行数据!");
					return false;
				}
			}
			var rows = $sysBrandDatagrid.datagrid("getData").rows;
			var addIndex = rows.length;
			$sysBrandDatagrid.datagrid('insertRow',{
				index: addIndex,	// 索引从0开始
				row: {
					id:addId--
				}
			});
			$sysBrandDatagrid.datagrid("beginEdit",addIndex);
			sysBrandEdit = addIndex;
		},
		editSysBrand:function(){
			//不需要页面，直接表格编辑
			var selected = $sysBrandDatagrid.datagrid("getSelected");
			if(null == selected){
				$alert("请单选品牌行，进行编辑!");
				return false;
			}
			if(sysBrandEdit!=undefined){
	    		if($sysBrandDatagrid.datagrid("validateRow",sysBrandEdit)){
	    			$sysBrandDatagrid.datagrid("endEdit",sysBrandEdit);
	    		}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
			var index = $sysBrandDatagrid.datagrid("getRowIndex",selected);
			$sysBrandDatagrid.datagrid("beginEdit",index);
			sysBrandEdit = index;
		},
		cancelEdit:function(){
			$sysBrandDatagrid.datagrid("reload");
			sysBrandEdit = undefined;
		},
		deleteSysBrand:function(){
			var checks = $sysBrandDatagrid.datagrid("getChecked");
			if(checks.length == 0){
				$alert("请勾选需要删除的品牌!");
				return false;
			}
			$.messager.confirm('确认','确认要删除勾选的品牌吗？',function(r){    
			    if (r){
			    	var ids = [];
			    	var deletes = [];
			    	$.each(checks,function(i,n){
			    		ids.push(n.id);
			    		deletes.push(n);
			    	});
			        $.post("sysBrandAction!deleteBrand.act",
			        	{"ids":ids.join(",")},
			        	function(data){
						handlerResult(data,
				    		function(rs){
								$show(rs.message);
								$.each(deletes,function(i,n){
									var deleteIndex = $sysBrandDatagrid.datagrid("getRowIndex",n);
									$sysBrandDatagrid.datagrid("deleteRow",deleteIndex);
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
		search:function(){
			var searchData = {};
			searchData["name"]=$("#name").val();
			searchData["info"]=$("#info").val();
			
			$sysBrandDatagrid.datagrid("reload",
				{
					"jsonStr":Some.util.jsonToStr(searchData)
				}
			);
		},
		
		reset:function(){
			$("#name").val('');
			$("#info").val('');
			sysBrandOperation.search();
		}
	};
</script>
</head>
	
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="sysBrand_tabel_search" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
		<div class="search-div">
			<label>品牌名称</label>
			<input type="text" id="name"/>
		</div>
		<div class="search-div">
			<label>品牌描述</label>
			<input type="text" id="info"/>
		</div>
		
		<div class="search-div">
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="sysBrandOperation.search()">搜索</a> 
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="sysBrandOperation.reset()">重置</a> 
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="sysBrandTable"></table>
	</div>
</div>
<div id="sysBrand_tool_bar">
	<s:if test="#session.defaultMenu.sysBrandActionAdd==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysBrandOperation.addSysBrand()">新增</a>
	</s:if>
	<s:if test="#session.defaultMenu.sysBrandActionEdit==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysBrandOperation.editSysBrand()">编辑</a>
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" onclick="sysBrandOperation.updateSysBrand()">保存</a>
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-quxiao', plain:true" onclick="sysBrandOperation.cancelEdit()">取消编辑</a>
	</s:if>
	<s:if test="#session.defaultMenu.sysBrandActionDelete==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="sysBrandOperation.deleteSysBrand()">删除</a>	
	</s:if>
	<s:if test="#session.defaultMenu.sysBrandActionRelation==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'building_link', plain:true" onclick="sysBrandOperation.relationSysBrand()">关联类别</a>
	</s:if>
</div>



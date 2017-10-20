<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/public/public.jsp" />
<head>
<script type="text/javascript">
	var $sysSupplierDatagrid;	
	var sysSupplierOperation = {};
	
	var flowObj = new Function("return " + '${hashMap.flowObj}')();
	
	var sysSupplierEdit = undefined;
	var addId = 0;
	$(function(){
		$sysSupplierDatagrid=$('#sysSupplierTable').datagrid({
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
            toolbar:"#syssupplier_tool_bar",
            url:"sysSupplierAction!listData.act",
            frozenColumns:[[
       			{field:'ck',checkbox:true},
       			{field:'workType',title: "供应商工种",width:100,sortable:true
					,editor:{
                    	type:'combobox',
                     	options:{
                          	  valueField:'value', 
                              textField:'name',
                              editable:false,
                              data:${hashMap.flowArr},
                              panelHeight:'auto',
                              onLoadSuccess: function () {
                            	 
                              }	
                 	 	}
					},
					formatter:function(value, rowData) {
						return flowObj[value];
					} 
				},
       			{field:'name',title: "供应商名称",width:200,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:true,validType:['length[1,300]','illegal']}
		        	}
				}
            ]],
			columns:[[
				{field:'contact',title: "供应商联系人",width:120,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:true,validType:['length[1,40]','illegal']}
		        	}
				},
				{field:'phoneNum',title: "联系人手机",width:120,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:true,validType:['length[1,24]','mobile']}
		        	}
				},
				{field:'telNum',title: "供应商电话",width:150,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,24]','phone']}
		        	}
				},
				{field:'address',title: "供应商地址",width:450,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,600]','illegal']}
		        	}
				}
			]],
			onDblClickCell:function(index, field, value) {
		    	if(sysSupplierEdit!=undefined){
		    		if($sysSupplierDatagrid.datagrid("validateRow",sysSupplierEdit)){
		    			$sysSupplierDatagrid.datagrid("endEdit",sysSupplierEdit);
		    		}else{
						$show("请正确输入编辑行数据!");
						return false;	    		
		    		}
		    	}
		    	$sysSupplierDatagrid.datagrid('editCell',{index:index,field:field});
	            sysSupplierEdit = index;
	        },
	        onAfterEdit:function(rowIndex,rowData,changes){
		    	$.post("sysSupplierAction!saveOrUpdateSupplierGrid.act",
		    			{"jsonStr":Some.util.jsonToStr(rowData)},
	       			 function(data){
	      			 	handlerResult(data,
	      			 		function(json){
								$show(json.message);
								$sysSupplierDatagrid.datagrid('updateRow',{
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
	  	
	  	$("#sysSupplierTable").parent(".datagrid-view").keyEvent({
			keyCode:13,
			handler:function(event){
				sysSupplierOperation.updateSysSupplier();
				event.preventDefault();
			}
		});
	 	
	 	$("#syssupplier_tabel_search").keyEvent({
			keyCode:13,
			handler:function(event){
				sysSupplierOperation.search();
				event.preventDefault();
			}
		});
	});
	
	sysSupplierOperation={
		updateSysSupplier:function(){
			if(sysSupplierEdit!=undefined){
				if($sysSupplierDatagrid.datagrid("validateRow",sysSupplierEdit)){
					$sysSupplierDatagrid.datagrid("endEdit",sysSupplierEdit);
					sysSupplierEdit = undefined;
				}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
		},
		addSysSupplier:function(){
			if(sysSupplierEdit!=undefined){
				if($sysSupplierDatagrid.datagrid("validateRow",sysSupplierEdit)){
					$sysSupplierDatagrid.datagrid("endEdit",sysSupplierEdit);
					sysSupplierEdit = undefined;
				}else{
					$show("请正确输入编辑行数据!");
					return false;
				}
			}
			var rows = $sysSupplierDatagrid.datagrid("getData").rows;
			var addIndex = rows.length;
			$sysSupplierDatagrid.datagrid('insertRow',{
				index: addIndex,	// 索引从0开始
				row: {
					id:addId--,
					workType:1
				}
			});
			$sysSupplierDatagrid.datagrid("beginEdit",addIndex);
			sysSupplierEdit = addIndex;
		},
		editSysSupplier:function(){
			//不需要页面，直接表格编辑
			var selected = $sysSupplierDatagrid.datagrid("getSelected");
			if(null == selected){
				$alert("请单选供应商行，进行编辑!");
				return false;
			}
			if(sysSupplierEdit!=undefined){
	    		if($sysSupplierDatagrid.datagrid("validateRow",sysSupplierEdit)){
	    			$sysSupplierDatagrid.datagrid("endEdit",sysSupplierEdit);
	    		}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
			var index = $sysSupplierDatagrid.datagrid("getRowIndex",selected);
			$sysSupplierDatagrid.datagrid("beginEdit",index);
			sysSupplierEdit = index;
		},
		cancelEdit:function(){
			$sysSupplierDatagrid.datagrid("reload");
			sysSupplierEdit = undefined;
		},
		deleteSysSupplier:function(){
			var checks = $sysSupplierDatagrid.datagrid("getChecked");
			if(checks.length == 0){
				$alert("请勾选需要删除的供应商!");
				return false;
			}
			$.messager.confirm('确认','确认要删除勾选的供应商吗？',function(r){    
			    if (r){
			    	var ids = [];
			    	var deletes = [];
			    	$.each(checks,function(i,n){
			    		ids.push(n.id);
			    		deletes.push(n);
			    	});
			        $.post("sysSupplierAction!deleteSupplier.act",
			        	{"ids":ids.join(",")},
			        	function(data){
						handlerResult(data,
				    		function(rs){
								$show(rs.message);
								$.each(deletes,function(i,n){
									var deleteIndex = $sysSupplierDatagrid.datagrid("getRowIndex",n);
									$sysSupplierDatagrid.datagrid("deleteRow",deleteIndex);
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
			searchData["contact"]=$("#contact").val();
			searchData["address"]=$("#address").val();
			searchData["workType"]=$("#workType").val();
			searchData["phoneNum"]=$("#phoneNum").val();
			searchData["telNum"]=$("#telNum").val();
			$sysSupplierDatagrid.datagrid("reload",
				{
					"jsonStr":Some.util.jsonToStr(searchData)
				}
			);
		},
		
		reset:function(){
			sysSupplierOperation.search();
		}
	};
</script>
</head>
	
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="syssupplier_tabel_search" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
		<div class="search-div">
			<label>供应商类型</label>
	       	<s:select id="workType" style="height:22px"
	       		list="flowList"
		       	listKey="value"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		
		<div class="search-div">
			<label>供应商名称</label>
			<input type="text" id="name"/>
		</div>
		<div class="search-div">
			<label>供应商联系人</label>
			<input type="text" id="contact"/>
		</div>
		<div class="search-div">
			<label>联系人手机</label>
			<input type="text" id="phoneNum"/>
		</div>
		<div class="search-div">
			<label>供应商电话</label>
			<input type="text" id="telNum"/>
		</div>
		<div class="search-div">
			<label>供应商地址</label>
			<input type="text" id="address"/>
		</div>
		
		<div class="search-div">
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="sysSupplierOperation.search()">搜索</a> 
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="sysSupplierOperation.reset()">重置</a> 
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="sysSupplierTable"></table>
	</div>
</div>
<div id="syssupplier_tool_bar">
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysSupplierOperation.addSysSupplier()">新增</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysSupplierOperation.editSysSupplier()">编辑</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" onclick="sysSupplierOperation.updateSysSupplier()">保存</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-quxiao', plain:true" onclick="sysSupplierOperation.cancelEdit()">取消编辑</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="sysSupplierOperation.deleteSysSupplier()">删除</a>
</div>



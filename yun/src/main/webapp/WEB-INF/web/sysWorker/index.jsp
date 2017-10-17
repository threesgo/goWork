<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/public/public.jsp" />
<head>
<script type="text/javascript">
	var $sysWorkerDatagrid;	
	var sysWorkerOperation = {};
	
	var sysWorkerEdit = undefined;
	var addId = 0;
	$(function(){
		$sysWorkerDatagrid=$('#sysWorkerTable').datagrid({
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
            toolbar:"#sysWorker_tool_bar",
            url:"sysWorkerAction!listData.act",
            frozenColumns:[[
       			{field:'ck',checkbox:true},
       			{field:'workType',title: "工人工种",width:100,sortable:true
					,editor:{
                    	type:'combobox',
                     	options:{
                          	  valueField:'value', 
                              textField:'name',
                              editable:false,
                              data:${hashMap.flowListArr},
                              panelHeight:'auto',
                              onLoadSuccess: function () {
                            	 
                              }	
                 	 	}
					},
					formatter:function(value, rowData) {
						var flowListObj = new Function("return " + '${hashMap.flowListObj}')();
						return flowListObj[value];
					} 
				},
       			{field:'name',title: "工人姓名",width:200,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,300]','illegal']}
		        	}
				}
            ]],
			columns:[[
				{field:'phoneNum',title: "工人手机",width:120,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,24]','illegal']}
		        	}
				},
				{field:'address',title: "工人地址",width:450,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,600]','illegal']}
		        	}
				}
			]],
			onDblClickCell:function(index, field, value) {
		    	if(sysWorkerEdit!=undefined){
		    		if($sysWorkerDatagrid.datagrid("validateRow",sysWorkerEdit)){
		    			$sysWorkerDatagrid.datagrid("endEdit",sysWorkerEdit);
		    		}else{
						$show("请正确输入编辑行数据!");
						return false;	    		
		    		}
		    	}
		    	$sysWorkerDatagrid.datagrid('editCell',{index:index,field:field});
	            sysWorkerEdit = index;
	        },
	        onAfterEdit:function(rowIndex,rowData,changes){
		    	$.post("sysWorkerAction!saveOrUpdateWorkerGrid.act",
		    			{"jsonStr":Some.util.jsonToStr(rowData)},
	       			 function(data){
	      			 	handlerResult(data,
	      			 		function(json){
								$show(json.message);
								$sysWorkerDatagrid.datagrid('updateRow',{
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
	  	
	  	$("#sysWorkerTable").parent(".datagrid-view").keyEvent({
			keyCode:13,
			handler:function(event){
				sysWorkerOperation.updatesysWorker();
				event.preventDefault();
			}
		});
	 	
	 	$("#sysWorker_tabel_search").keyEvent({
			keyCode:13,
			handler:function(event){
				sysWorkerOperation.search();
				event.preventDefault();
			}
		});
	});
	
	sysWorkerOperation={
		updatesysWorker:function(){
			if(sysWorkerEdit!=undefined){
				if($sysWorkerDatagrid.datagrid("validateRow",sysWorkerEdit)){
					$sysWorkerDatagrid.datagrid("endEdit",sysWorkerEdit);
					sysWorkerEdit = undefined;
				}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
		},
		addsysWorker:function(){
			if(sysWorkerEdit!=undefined){
				if($sysWorkerDatagrid.datagrid("validateRow",sysWorkerEdit)){
					$sysWorkerDatagrid.datagrid("endEdit",sysWorkerEdit);
					sysWorkerEdit = undefined;
				}else{
					$show("请正确输入编辑行数据!");
					return false;
				}
			}
			var rows = $sysWorkerDatagrid.datagrid("getData").rows;
			var addIndex = rows.length;
			$sysWorkerDatagrid.datagrid('insertRow',{
				index: addIndex,	// 索引从0开始
				row: {
					id:addId--,
					workType:1
				}
			});
			$sysWorkerDatagrid.datagrid("beginEdit",addIndex);
			sysWorkerEdit = addIndex;
		},
		editsysWorker:function(){
			//不需要页面，直接表格编辑
			var selected = $sysWorkerDatagrid.datagrid("getSelected");
			if(null == selected){
				$alert("请单选供应商行，进行编辑!");
				return false;
			}
			if(sysWorkerEdit!=undefined){
	    		if($sysWorkerDatagrid.datagrid("validateRow",sysWorkerEdit)){
	    			$sysWorkerDatagrid.datagrid("endEdit",sysWorkerEdit);
	    		}else{
					$show("请正确输入编辑行数据!");
					return false;	    		
	    		}
			}
			var index = $sysWorkerDatagrid.datagrid("getRowIndex",selected);
			$sysWorkerDatagrid.datagrid("beginEdit",index);
			sysWorkerEdit = index;
		},
		cancelEdit:function(){
			$sysWorkerDatagrid.datagrid("reload");
			sysWorkerEdit = undefined;
		},
		deletesysWorker:function(){
			var checks = $sysWorkerDatagrid.datagrid("getChecked");
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
			        $.post("resourceAction!deleteResource.act",
			        	{"ids":ids.join(",")},
			        	function(data){
						handlerResult(data,
				    		function(rs){
								$show(rs.message);
								$.each(deletes,function(i,n){
									var deleteIndex = $sysWorkerDatagrid.datagrid("getRowIndex",n);
									$sysWorkerDatagrid.datagrid("deleteRow",deleteIndex);
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
			
			$sysWorkerDatagrid.datagrid("reload",
				{
					"jsonStr":Some.util.jsonToStr(searchData),
				}
			);
		},
		
		reset:function(){
			sysWorkerOperation.search();
		}
	};
</script>
</head>
	
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="sysWorker_tabel_search" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
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
			<label>供应商类型</label>
	       	<s:select id="workType" style="height:22px"
	       		list="flowList"
		       	listKey="value"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		<div class="search-div">
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="sysWorkerOperation.search()">搜索</a> 
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="sysWorkerOperation.reset()">重置</a> 
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="sysWorkerTable"></table>
	</div>
</div>
<div id="sysWorker_tool_bar">
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysWorkerOperation.addsysWorker()">新增</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysWorkerOperation.editsysWorker()">编辑</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-quxiao', plain:true" onclick="sysWorkerOperation.cancelEdit()">取消编辑</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="sysWorkerOperation.deletesysWorker()">删除</a>
</div>



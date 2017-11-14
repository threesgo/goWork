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
	
	var flowObj = new Function("return " + '${hashMap.flowObj}')();
	var sexObj = new Function("return " + '${hashMap.sexObj}')();
	var educationObj = new Function("return " + '${hashMap.educationObj}')();
	
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
       			{field:'workType',title: "工种",width:100,sortable:true
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
       			{field:'name',title: "姓名",width:100,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:true,validType:['length[1,20]','illegal']}
		        	}
				},
				{field:'sex',title: "性别",width:80,sortable:true
					,editor:{
                    	type:'combobox',
                     	options:{
                          	  valueField:'value', 
                              textField:'name',
                              editable:false,
                              data:${hashMap.sexArr},
                              panelHeight:'auto',
                              onLoadSuccess: function () {
                            	 
                              }	
                 	 	}
					},
					formatter:function(value, rowData) {
						return sexObj[value];
					} 
				},
				{field:'wages',title:"日薪",width:80,sortable:true,align:'right',
		        	editor:{
		        		type:"numberbox",
						options:{
							required:true,
							min:0,
							max:9999999.99,
							precision:2
				 		}
		        	}
					,formatter:function(value, rowData) {
						if(value == 0){
							return 0;
						}else{
							return value;
						}
					} 
		        }
            ]],
			columns:[[
				{field:'phoneNum',title: "手机号码",width:100,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,24]','mobile']}
		        	}
				},
				{field:'telNum',title: "电话号码",width:100,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,24]','phone']}
		        	}
				},
				{field:'workAge',title: "工龄",width:100,sortable:true
					,editor:{
						type:"numberbox",
						options:{
							min:0,
							max:150,
							precision:0
				 		}
		        	},
		        	formatter:function(value, rowData) {
						if(value==0){
							return '';
						}else{
							return value;
						}
					} 
				},
				{field:'address',title: "家庭住址",width:450,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,600]','illegal']}
		        	}
				},
				{field:'age',title: "年龄",width:100,sortable:true
					,editor:{
						type:"numberbox",
						options:{
							min:1,
							max:180,
							precision:0
				 		}
		        	},
		        	formatter:function(value, rowData) {
						if(value==0){
							return '';
						}else{
							return value;
						}
					} 
				},
				{field:'birthday',title: "生日",width:100,sortable:true
					,editor:{
						type:"datebox"
		        	}
				},
				{field:'education',title: "学历",width:100,sortable:true
					,editor:{
                    	type:'combobox',
                     	options:{
                          	  valueField:'value', 
                              textField:'name',
                              editable:false,
                              data:${hashMap.educationArr},
                              panelHeight:'auto',
                              onLoadSuccess: function () {
                            	 
                              }	
                 	 	}
					},
					formatter:function(value, rowData) {
						return educationObj[value];
					} 
				},
				{field:'company',title: "所在公司",width:450,sortable:true
					,editor:{
		        		type:"textbox",
		        		options:{required:false,validType:['length[1,80]','illegal']}
		        	}
				}
			]],
			onDblClickCell:function(index, field, value) {
				<s:if test="#session.defaultMenu.sysWorkerActionEdit==1">
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
		      	</s:if>
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
				sysWorkerOperation.updateSysWorker();
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
		updateSysWorker:function(){
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
		addSysWorker:function(){
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
					workType:1,
					sex:1,
					education:3
				}
			});
			$sysWorkerDatagrid.datagrid("beginEdit",addIndex);
			sysWorkerEdit = addIndex;
		},
		editSysWorker:function(){
			//不需要页面，直接表格编辑
			var selected = $sysWorkerDatagrid.datagrid("getSelected");
			if(null == selected){
				$alert("请单选工人行，进行编辑!");
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
		deleteSysWorker:function(){
			var checks = $sysWorkerDatagrid.datagrid("getChecked");
			if(checks.length == 0){
				$alert("请勾选需要删除的工人!");
				return false;
			}
			$.messager.confirm('确认','确认要删除勾选的工人吗？',function(r){    
			    if (r){
			    	var ids = [];
			    	var deletes = [];
			    	$.each(checks,function(i,n){
			    		ids.push(n.id);
			    		deletes.push(n);
			    	});
			        $.post("sysWorkerAction!deleteWorker.act",
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
			searchData["sex"]=$("#sex").val();
			searchData["address"]=$("#address").val();
			searchData["workType"]=$("#workType").val();
			searchData["phoneNum"]=$("#phoneNum").val();
			searchData["telNum"]=$("#telNum").val();
			searchData["company"]=$("#company").val();
			
			$sysWorkerDatagrid.datagrid("reload",
				{
					"jsonStr":Some.util.jsonToStr(searchData)
				}
			);
		},
		
		reset:function(){
			$("#workType").val(0);
			$("#name").val('');
			$("#sex").val(0);
			$("#phoneNum").val('');
			$("#telNum").val('');
			$("#address").val('');
			$("#company").val('');
			sysWorkerOperation.search();
		}
	};
</script>
</head>
	
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="sysWorker_tabel_search" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
		<div class="search-div">
			<label>工种</label>
	       	<s:select id="workType" style="height:22px"
	       		list="flowList"
		       	listKey="value"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		
		<div class="search-div">
			<label>姓名</label>
			<input type="text" id="name"/>
		</div>
		
		<div class="search-div">
			<label>性别</label>
			<s:select id="sex" style="height:22px"
	       		list="sexList"
		       	listKey="value"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		
		<div class="search-div">
			<label>手机号码</label>
			<input type="text" id="phoneNum"/>
		</div>
		
		<div class="search-div">
			<label>电话号码</label>
			<input type="text" id="telNum"/>
		</div>
		
		<div class="search-div">
			<label>家庭地址</label>
			<input type="text" id="address"/>
		</div>
		
		<div class="search-div">
			<label>所在公司</label>
			<input type="text" id="company"/>
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
	<s:if test="#session.defaultMenu.sysWorkerActionAdd==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysWorkerOperation.addSysWorker()">新增</a>
	</s:if>
	<s:if test="#session.defaultMenu.sysWorkerActionEdit==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysWorkerOperation.editSysWorker()">编辑</a>
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-save', plain:true" onclick="sysWorkerOperation.updateSysWorker()">保存</a>
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-quxiao', plain:true" onclick="sysWorkerOperation.cancelEdit()">取消编辑</a>
	</s:if>
	<s:if test="#session.defaultMenu.sysWorkerActionDelete==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="sysWorkerOperation.deleteSysWorker()">删除</a>
	</s:if>
</div>



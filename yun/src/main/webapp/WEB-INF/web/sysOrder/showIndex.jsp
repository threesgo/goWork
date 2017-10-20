<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/public/public.jsp" />
<head>
<script type="text/javascript">
	var $sysOrderDatagrid;	
	var sysOrderOperation = {};
	
	$(function(){
		$sysOrderDatagrid=$('#sysOrderTable').datagrid({
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
            toolbar:"#sysOrder_tool_bar",
            url:"sysOrderAction!listData.act",
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
		    	
	        },
	        onAfterEdit:function(rowIndex,rowData,changes){
		    	
	        }
	  	});
	 	
	 	$("#sysOrder_tabel_search").keyEvent({
			keyCode:13,
			handler:function(event){
				sysOrderOperation.search();
				event.preventDefault();
			}
		});
	});
	
	sysOrderOperation={
		addsysOrder:function(){
			
		},
		editSysOrder:function(){
			
		},
		cancelEdit:function(){
			$sysOrderDatagrid.datagrid("reload");
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
			
			$sysOrderDatagrid.datagrid("reload",
				{
					"jsonStr":Some.util.jsonToStr(searchData),
				}
			);
		},
		
		reset:function(){
			sysOrderOperation.search();
		}
	};
</script>
</head>
	
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="sysOrder_tabel_search" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
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
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="sysOrderOperation.search()">搜索</a> 
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="sysOrderOperation.reset()">重置</a> 
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="orderTable"></table>
	</div>
</div>
<div id="sysOrder_tool_bar">
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysOrderOperation.addSysOrder()">新增</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysOrderOperation.editSysOrder()">编辑</a>
</div>
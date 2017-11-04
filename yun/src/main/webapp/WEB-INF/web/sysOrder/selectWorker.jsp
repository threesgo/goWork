<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<head>
<script type="text/javascript">
	var $sysWorkerDatagrid;	
	var sysWorkerOperation = {};
	
	var sysWorkerEdit = undefined;
	
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
            //toolbar:"#sysWorker_tool_bar",
            url:"sysOrderAction!selectWorkerData.act",
            queryParams:{"sysOrderFlow.id":'${sysOrderFlow.id}'},
            //frozenColumns:[[ ]],
			columns:[[
				{field:'ck',checkbox:true},
				/*
       			{field:'workTime',title: "工时(天)",width:100,sortable:true,
       				editor:{
		        		type:"numberbox",
						options:{
							required:true,
							min:1,
							max:9999999.99,
							precision:2
				 		}
		        	}
	        	},
	        	*/
       			{field:'name',title: "姓名",width:100,sortable:true
					
				},
				{field:'sex',title: "性别",width:80,sortable:true
					,formatter:function(value, rowData) {
						return sexObj[value];
					} 
				},
				{field:'wages',title:"日薪",width:80,sortable:true,align:'right'
					,formatter:function(value, rowData) {
						if(value == 0){
							return 0;
						}else{
							return value;
						}
					} 
		        },
				{field:'phoneNum',title: "手机号码",width:100,sortable:true
					
				},
				{field:'telNum',title: "电话号码",width:100,sortable:true
					
				},
				{field:'workAge',title: "工龄",width:100,sortable:true,
		        	formatter:function(value, rowData) {
						if(value==0){
							return '';
						}else{
							return value;
						}
					} 
				},
				{field:'age',title: "年龄",width:100,sortable:true,
		        	formatter:function(value, rowData) {
						if(value==0){
							return '';
						}else{
							return value;
						}
					} 
				},
				{field:'company',title: "所在公司",width:450,sortable:true
					
				}
			]]
			/*
			,onClickCell:function(index, field, value) {
		    	$sysWorkerDatagrid.datagrid('editCell',{index:index,field:field});
	            sysWorkerEdit = index;
	        }
	        */
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
		search:function(){
			var searchData = {};
			searchData["name"]=$("#name").val();
			searchData["sex"]=$("#sex").val();
			searchData["address"]=$("#address").val();
			//searchData["workType"]=$("#workType").val();
			searchData["phoneNum"]=$("#phoneNum").val();
			searchData["telNum"]=$("#telNum").val();
			searchData["company"]=$("#company").val();
			
			$sysWorkerDatagrid.datagrid("reload",
				{
					"jsonStr":Some.util.jsonToStr(searchData),
					"sysOrderFlow.id":'${sysOrderFlow.id}'
				}
			);
		},
		
		reset:function(){
			//$("#workType").val(0);
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
		<%--
		<div class="search-div">
			<label>工种</label>
	       	<s:select id="workType" style="height:22px"
	       		list="flowList"
		       	listKey="value"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		 --%>
		
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
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	var $sysMemberDatagrid;	
	var sysMemberOperation = {};
	
	var memberTypeObj = new Function("return " + '${hashMap.memberTypeObj}')();
	
	$(function(){
		$sysMemberDatagrid=$('#sysMemberTable').datagrid({
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
            url:"sysMemberAction!listData.act",
            frozenColumns:[[
       			{field:'type',title: "类型",width:100,sortable:true,
       				formatter:function(value,rowData,rowIndex){
       					return memberTypeObj[value];
					}
				},
       			{field:'userName',title: "用户名",width:100,sortable:true
					
				},
				{field:'phoneNum',title: "手机号码",width:80,sortable:true
					
				},
				{field:'email',title:"邮箱",width:80,sortable:true
		        },
		        {field:'isAuthorize',title:"是否授权",width:80,sortable:true,
		        	formatter:function(value,rowData,rowIndex){
       					if(value == 0){
       						return "否";
       					}else{
       						return "是";
       					}
					}
		        }
            ]],
			columns:[[
				{field:'weName',title: "微信号",width:100,sortable:true
					
				},
				{field:'regTime',title: "注册时间",width:100,sortable:true
					
				},
				{field:'regIp',title: "注册IP",width:100,sortable:true
					
				},
				{field:'lastLoginTime',title: "最后登录时间",width:100,sortable:true
					
				},
				{field:'lastLoginIp',title: "最后登录IP",width:100,sortable:true
					
				}
			]]
	  	});
	  	
	  	$("#sysMemberTable").parent(".datagrid-view").keyEvent({
			keyCode:13,
			handler:function(event){
				sysMemberOperation.updatesysMember();
				event.preventDefault();
			}
		});
	 	
	 	$("#sysMember_tabel_search").keyEvent({
			keyCode:13,
			handler:function(event){
				sysMemberOperation.search();
				event.preventDefault();
			}
		});
	});
	
	sysMemberOperation={
		search:function(){
			var searchData = {};
			searchData["type"]=$("#type").val();
			searchData["userName"]=$("#userName").val();
			searchData["phoneNum"]=$("#phoneNum").val();
			searchData["weName"]=$("#weName").val();
			$sysMemberDatagrid.datagrid("reload",
				{
					"jsonStr":Some.util.jsonToStr(searchData)
				}
			);
		},
		
		reset:function(){
			$("#type").val(0);
			$("#userName").val('');
			$("#phoneNum").val('');
			$("#weName").val('');
			sysMemberOperation.search();
		}
	};
</script>
	
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="sysMember_tabel_search" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
		<div class="search-div">
			<label>类型</label>
	       	<s:select id="type" style="height:22px"
	       		list="memberTypeList"
		       	listKey="value"   
		       	listValue="name" 
		       	headerKey="0"
		       	headerValue="--请选择--"/>
		</div>
		
		<div class="search-div">
			<label>用户名</label>
			<input type="text" id="userName"/>
		</div>
		
		<div class="search-div">
			<label>手机号码</label>
			<input type="text" id="phoneNum"/>
		</div>
		
		<div class="search-div">
			<label>微信号</label>
			<input type="text" id="weName"/>
		</div>
		
		<div class="search-div">
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="sysMemberOperation.search()">搜索</a> 
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="sysMemberOperation.reset()">重置</a> 
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="sysMemberTable"></table>
	</div>
</div>
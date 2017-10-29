<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
            url:"sysOrderAction!listData.act",
            queryParams:{"jsonStr":"{'orderDate':${dateStr}}"},
            frozenColumns:[[
       			{field:'ck',checkbox:true},
       			/*
       			{field:'code',title: "编号",width:100,sortable:true
					
				},
				*/
				{field:'name',title: "名称",width:80,sortable:true
					
				},
				{field:'roomsStr',title: "房间数量",width:100,sortable:true
					
				},
				{field:'totalArea',title: "总面积",width:60,sortable:true
					
				},
				{field:'totalAmount',title: "总价",width:60,sortable:true
					
				},
				{field:'orderDate',title: "下单时间",width:80,sortable:true
					
				}
            ]],
			columns:[[
				{field:'contact',title: "联系人",width:80,sortable:true
					
				},
				{field:'contactTel',title: "联系方式",width:80,sortable:true
					
				},
				{field:'address',title: "装修地址",width:300,sortable:true
					
				},
				{field:'startTimeStr',title: "开始时间",width:140,sortable:true
					
				},
				{field:'endTimeStr',title: "结束时间",width:140,sortable:true
					
				},
				{field:'info',title: "备注",width:200,sortable:true
					
				}
			]]
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
		search:function(){
			var searchData = {};
			searchData["code"]=$("#code").val();
			searchData["name"]=$("#name").val();
			searchData["orderDate"]=$("#orderDate").datebox("getValue");
			searchData["contact"]=$("#contact").val();
			searchData["startTime"]=$("#startTime").datebox("getValue");
			searchData["endTime"]=$("#startTime").datebox("getValue");
			
			$sysOrderDatagrid.datagrid("reload",
				{
					"jsonStr":Some.util.jsonToStr(searchData),
				}
			);
		},
		reset:function(){
			$("#code").val('');
			$("#name").val('');
			$("#orderDate").datebox("setValue",'');
			$("#contact").val('');
			$("#startTime").datebox("setValue",'');
			$("#endTime").datebox("setValue",'');
			sysOrderOperation.search();
		}
	};
</script>
<div class="easyui-layout" data-options="fit:true,border : false">
	<div id="sysOrder_tabel_search" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
		<div class="search-div">
			<label>编号</label>
			<input type="text" id="code"/>
		</div>
		
		<div class="search-div">
			<label>名称</label>
			<input type="text" id="name"/>
		</div>
		
		<div class="search-div">
			<label>地址</label>
			<input type="text" id="address"/>
		</div>
		
		<div class="search-div">
			<label>下单日期</label>
			<div class="select">
				<input id="orderDate" class="easyui-datebox" />
			</div>
		</div>
		
		<div class="search-div">
			<label>联系人</label>
			<input type="text" id="contact"/>
		</div>
		
		<div class="search-div" style = "width:488px;max-width: 488px;">
			<label>预计工期时间</label>
			<div class="select" style="width: 400px;">
				<input id="startTime" class="easyui-datebox" />
				-
				<input id="endTime" class="easyui-datebox" />
			</div>
		</div>
		
		<div class="search-div">
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="sysOrderOperation.search()">搜索</a> 
			<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="sysOrderOperation.reset()">重置</a> 
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="sysOrderTable"></table>
	</div>
</div>
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
		addSysOrder:function(){
 			var dialog = $('<div id="addSysOrder"></div>').dialog({    
				href : "sysOrderAction!saveOrUpdateOrderPage.act",
				width:600,
				height:380,
				title:"新增订单",
				method:'post',
				modal:true,
				resizable:true,
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$('#saveOrUpdate_order').form({    
						    onSubmit: function(){
						    	if(!validateForm()){
						    		return false;
						    	}
						    	initForm();						    	
						    },    
						    success:function(data){ 
						    	handlerResult(data,
						    		function(rs){
						    			dialog.dialog("destroy");
						    			$sysOrderDatagrid.datagrid("reload");
										$show(rs.message);
									},
									function(rs){
										$alert(rs.message);
									}
								);  
						    }    
						}).submit();    
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
		editSysOrder:function(){
			var node = $sysOrderDatagrid.datagrid("getSelected");
			if(null == node){
				$alert("请单选订单行进行编辑操作!");
				return false;
			}
			var dialog = $('<div id="addSysOrder"></div>').dialog({    
				href : "sysOrderAction!saveOrUpdateOrderPage.act",
				queryParams:{"sysOrder.id":node.id},
				width:600,
				height:380,
				title:"编辑订单",
				method:'post',
				modal:true,
				resizable:true,
				buttons:[{
					text:"确定",
					iconCls:'icon-ok',
					handler:function(){
						$('#saveOrUpdate_order').form({    
						    onSubmit: function(){
						    	if(!validateForm()){
						    		return false;
						    	}
						    	initForm();	
						    },    
						    success:function(data){ 
						    	handlerResult(data,
						    		function(rs){
						    			dialog.dialog("destroy");
						    			$sysOrderDatagrid.datagrid("reload");
										$show(rs.message);
									},
									function(rs){
										$alert(rs.message);
									}
								);  
						    }    
						}).submit();    
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
		},
		
		//报价单
		exportSysOrderQuotation:function(){
			exportExcel("sysOrderAction!exportSysOrderQuotation.act?sysOrder.id=");
		},
		
		//采购单
		exportSysOrderPurchase:function(){
			exportExcel("sysOrderAction!exportSysOrderPurchase.act?sysOrder.id=");
		},
		
		//施工单
		exportSysOrderConstruction:function(){
			exportExcel("sysOrderAction!exportSysOrderConstruction.act?sysOrder.id=");
		}
	};
	
	function exportExcel(url){
		var node = $sysOrderDatagrid.datagrid("getSelected");
		if(null == node){
			$alert("请单选订单行进行导出操作!");
			return false;
		}
		url = url+node.id;
		Some.util.newDownLoad({
			url:url,
			handler:function(){
			}
		});
	}
	
	function initForm(){
		var relValues = $("#saveOrUpdate_order #relationPackage").combobox("getValues");
		$("#saveOrUpdate_order #orderPackages").val(relValues.join(","));
	}
	
	function validateForm(){
		if(!$("#saveOrUpdate_order #edit_name").validatebox("isValid")){
    		$("#saveOrUpdate_order #edit_name").focus();
    		$show("请输入订单名称!");
    		return false;
    	}else
    	if(!$("#saveOrUpdate_order #edit_totalAmount").numberbox("isValid")){
    		$show("请输入订单总价!");
    		return false;
    	}else
    	if(!$("#saveOrUpdate_order #edit_totalArea").numberbox("isValid")){
    		$show("请输入订单面积!");
    		return false;
    	}else
    	if(!$("#saveOrUpdate_order #edit_contact").validatebox("isValid")){
    		$show("请输入订单联系人!");
    		$("#saveOrUpdate_order #edit_contact").focus();
    		return false;
    	}else
       	if(!$("#saveOrUpdate_order #edit_contactTel").validatebox("isValid")){
       		$show("请输入订单联系电话!");
       		$("#saveOrUpdate_order #edit_contactTel").focus();
       		return false;
       	}else
        if(!$("#saveOrUpdate_order #edit_address").validatebox("isValid")){
        	$show("请输入订单地址!");
        	$("#saveOrUpdate_order #edit_address").focus();
      		return false;
      	}else
        if(!$("#saveOrUpdate_order #edit_startTime").datetimebox("isValid")){
        	$show("请输入订单预期开始时间!");
       		return false;
       	}else
        if(!$("#saveOrUpdate_order #edit_endTime").datetimebox("isValid")){
        	$show("请输入订单预期结束时间!");
       		return false;
       	}else
       	if(!$("#saveOrUpdate_order #relationPackage").combobox("isValid")){
       		$show("请输入订单关联产品套餐!");
       		return false;
       	}
    	return true;
	} 
</script>
</head>
	
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
<div id="sysOrder_tool_bar">
	<s:if test="#session.defaultMenu.sysOrderActionAdd==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="sysOrderOperation.addSysOrder()">新增</a>
	</s:if>
	<s:if test="#session.defaultMenu.sysOrderActionEdit==1">
		<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="sysOrderOperation.editSysOrder()">编辑</a>
	</s:if>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:true" onclick="sysOrderOperation.exportSysOrderQuotation()">报价单导出</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:true" onclick="sysOrderOperation.exportSysOrderPurchase()">采购单导出</a>
	<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:true" onclick="sysOrderOperation.exportSysOrderConstruction()">施工单导出</a>
</div>
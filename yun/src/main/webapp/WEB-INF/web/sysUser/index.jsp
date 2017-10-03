<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="/public/public.jsp" />
	<head>
		<style type="text/css">
			#selectDiv{
				padding:3px;
				float:left !important;
				height: auto !important;
				width: 100%;
				padding-left: 30px;
			}
			.search-div{
				margin-left: 30px;
				height: 30px;
				margin-top:3px;
				float: left;
				width: 230px;
				*width: auto;
				*float: left !important;
				max-width: 300px;
			}
			
			#selectDiv div label{
				float: left;
			}
			
			#selectDiv div>input,#selectDiv div>select{
				float: right;
				width: 152px;
			}
			
			#selectDiv div.select{
				float: right;
				width: 152px;
			}
			
			#selectDiv div>select{
				width: 152px;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				var $adminList=$('#adminList').datagrid({
		            fitColumns:true,
		            striped:true,
		            singleSelect:true,
		            nowrap:false,
		            fit:true,
		            remoteSort:false,
		            pagination:true, 
		            pageSize:20,  
		            pageList:[20,50,100,150,200],
		            toolbar:"#selectDiv",
		            height: 'auto',
		            url:"sysUserAction!listUser.act",
					columns:[[
						{field:'userName',title: "用户名",width:190,align:"center",sortable:true, //sortable是排序
							sorter:function(a,b){ 
									return a>b? 1:-1; 
							}
						},
						{field:'realName',title:"真实姓名",width:190,align:"center",sortable:true,  sorter:function(a,b){  return a>b? 1:-1; }	},
						{field:'realEmail',title:"电子邮箱",width:190,align:"center",	sortable:true, sorter:function(a,b){ return a>b? 1:-1; }},
						{field:'phoneNum',title:"手机号码",width:190,align:"center",	sortable:true,  sorter:function(a,b){ return a>b? 1:-1; }},
						{field:'deptName',title:"部门",width:190,align:"center",	sortable:true,  sorter:function(a,b){ return a>b? 1:-1; }},
						{field:'createDt',title:"创建时间",width:190,align:"center",sortable:true,  sorter:function(a,b){ return a>b? 1:-1;  }},
						{field:'roles',title:"角色",width:180,align:"center",sortable:true,sorter:function(a,b){ return a>b? 1:-1 ; }}
					]]
				});
			});
		</script>
	</head>
	<body id="body" >
		<div class="easyui-panel" title="用户管理" data-options="border:false,fit:true" style="background-color: #fcfdfe;" >
			<table id="adminList"></table>
			<div id="selectDiv" style="height:auto !important;padding:10px;float:left !important;width: 100%;">
				<div class="search-div">
					<label>用户名：</label>
					<input name="sysUser.userName" id="sysUsreName" class="input easyui-validatebox" data-options="validType:['illegal']"/>
				</div>
				<div class="search-div">
					<label>真实姓名：</label>
					<input name="sysUser.realName" id="sysRealName" class="input easyui-validatebox" data-options="validType:['illegal']"/>
				</div>
				<div class="search-div">
					<label>角色：</label>
					<div class="select">
						<select id="queryRole">
							<option value="" selected="selected">全部</option>
						</select>
					</div>
				</div>
				<div class="search-div">
					<label>部门：</label>
					<div class="select">
						<select id="queryDept" data-options="validType:['illegal']" >
						</select>
					</div>
				</div>
				<div class="search-div">
					<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="adminVar.query()" >搜索</a> 
					<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="adminVar.reset()" >重置</a> 
					<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="adminVar.addAdmin()">新增</a>
				</div>
			</div>
		</div>
	</body>
</html>



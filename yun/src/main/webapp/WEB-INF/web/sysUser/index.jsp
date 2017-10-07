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
		var $userList;	
		$(function(){
				$userList=$('#userList').datagrid({
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
				
				
				
				window.user={
						/*
						$userList:$userList,
						changeRole:function(obj){
							document.getElementById("queryRole").options.length = 0;
							$.post("sysRoleAction!roleList.act",{"majorId":obj.value},
								function(data){
									if(data != null && data.length > 0){
								       	for(var i=0; i<data.length; i++){
								    		$("#queryRole").append("<option value='"+data[i].id+"'>"+data[i].text+"</option>");
								    	}
								    }
							},"json");
						},*/
						/*
						search:function(){
							var input=$("#tools").find("input");
							var flag=false;
							$.each(input, function(i, n){//遍历数组或对象，i是数组或对象索引值，n是对应的数组值或对象
								if($(n).hasClass("validatebox-invalid")){  //hasClass("")检查是否还有指定的类
									flag=true;
								};
							});
							if(flag){
								return false;
							}
							var searchJSON={};
		 					searchJSON["userName"] = $("#queryUsreName").val();
		 					searchJSON["realName"] = $("#queryRealName").val();
		 					searchJSON["major"] = $("#queryMajor").val();
		 					searchJSON["role"] = $("#queryRole").val();
		 					searchJSON["dept"] = $("#queryDept").val();
		 					searchJSON["queryStatus"] = $("#queryStatus").val();
		 					searchJSON["queryType"] = $("#queryType").val();
		 					$userList.datagrid("reload",{"filterJsons":Some.util.toJson(searchJSON)});
						},
						reset:function(){
							$("#queryUsreName,#queryRealName").val("");
							$("#queryMajor").val("");
							$("#queryRole").val("");
							$("#queryDept").val("全部");
							$("#queryStatus").val(-1);
							$userList.datagrid("reload",{});
						},*/
						addUser:function(){
							new Some.dialog({
								top:100,
								title:"添加用户",
								width:450,
								height:"auto",
								href:"sysUserAction!preAdd.act",
								onSuccess:function(){
									alert("a");
								}
							});
						},
						
						updateAdmin:function(id,index){
							this.index=index;
							new Some.dialog({
								top:100,
								title:"<s:text name='modify_administrator'/>",
								width:450,
								height:"auto",
								href:"systemAdmin!modify.act?usrSmUser.id="+id
							});
						},
						accreditAdmin:function(id,index){
							new Some.dialog({
								top:100,
								title:"<s:text name='administrator_authorization'/>",
								width:450,
								height:"auto",
								href:"systemAdmin!preAccredit.act?usrSmUser.id="+id,
							});
						}
						,deleteAdmin:function(id,index){
							$confirm("<s:text name='confirm_remove'/>",function(){
								var loading=new Some.loading();
								loading.show();
								$.post("systemAdmin!delete.act",{"usrSmUser.id":id},function(text){
									loading.close();
									handlerResult(text,function(data){
										//var deleteRow=$adminList.datagrid("getSelected");
										//var deleteIndex=$adminList.datagrid("getRowIndex", deleteRow);
										//$adminList.datagrid("deleteRow",deleteIndex);
										adminVar.$userList.datagrid("reload");
										$show(data.message);
				 					},function(data){
				 						$show(data.message);
				 					});
									/* switch (""+data) {
										case "1":
											$show("删除成功");
											var deleteRow=$adminList.datagrid("getSelected");
											var deleteIndex=$adminList.datagrid("getRowIndex", deleteRow);
											$adminList.datagrid("deleteRow",deleteIndex);
											break;
										case "0":
											$alert("删除失败！");
											break;
										default:
											$alert(data);
											break;
									} */
								});
							});
						}
				};
				
			});
		</script>
	</head>
	<body id="body" >
		<div class="easyui-panel" title="用户管理" data-options="border:false,fit:true" style="background-color: #fcfdfe;" >
			<table id="userList"></table>
			<div id="selectDiv" style="height:auto !important;padding:10px;float:left !important;width: 100%;">
				<div class="search-div">
					<label>用户名：</label>
					<input  id="sysUsreName" class="input easyui-validatebox" data-options="validType:['illegal']"/>
				</div>
				<div class="search-div">
					<label>真实姓名：</label>
					<input  id="sysRealName" class="input easyui-validatebox" data-options="validType:['illegal']"/>
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
					<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="user.search()" >搜索</a> 
					<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="user.reset()" >重置</a> 
					<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="user.addUser()">新增</a>
				</div>
			</div>
		</div>
	</body>
</html>



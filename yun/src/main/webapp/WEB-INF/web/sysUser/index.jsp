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
	            toolbar:"#tool_bar",
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
			
			//角色
			$("#sysRoles").combobox({
				 valueField:'id',
				 textField:'sysRole',
			     multiple:true,
			     editable:false,
			     url:"sysRoleAction!findAll.act",
			     queryParams:{"needAll":true},
			     onSelect:function(record){
			    	if(!record.id){
			    		var selects = $("#sysRoles").combobox("getValues");
			    		$.each(selects,function(index,name){
			    			if(name){
			    				$("#sysRoles").combobox("unselect",name);
			    			}
			    		});
			    	}else{
			    		$("#sysRoles").combobox("unselect","");	
			    	} 
			     }
			     
			});
			//部门
			/* $("#sysRoles").combobox({
				
			}); */
			
		});
		
		window.user={
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
 					searchJSON["userName"] = $("#sysUsreName").val();
 					searchJSON["realName"] = $("#sysRealName").val();
 					searchJSON["roleIds"] = $("#sysRoles").combobox("getValues").join(",");
 					//searchJSON["deptIds"] = $("#queryDepts").combobox("getValues").join(",");
 					$userList.datagrid("reload",{"filterJsons":Some.util.jsonToStr(searchJSON)});
				},
				reset:function(){
					$("#sysUsreName,#sysRealName").val("");
					$("#sysRoles").combobox("setValue","");
					$userList.datagrid("reload",{});
				},
				//新建用户
				/* addUser:function(){
					new Some.dialog({
						top:100,
						title:"添加用户",
						width:450,
						height:"auto",
						href:"sysUserAction!preAdd.act",
					});
				}, */
				//新建用户
				  addUser:function(){
		 			var addDialog =$('<div id="addSysUser"></div>').dialog({    
		 				href:"sysUserAction!preAdd.act",
						width:630,
						height:300,
						title:"添加用户",
						method:'post',
						modal:true,
						resizable:true,
						buttons:[{
							text:"确定",
							iconCls:'icon-ok',
							handler:function(){
								new Some.form({
									render:"#add_fm",
									success:function(data){
										handlerResult(data,function(rs){
											addDialog.dialog("destroy");
											$show(rs.message);
											$userList.datagrid("reload");
										},function(rs){
											$alert(rs.message);
										});
									},
									onSubmit: function(param){
										
								    }  
								}).submit();   
							}
						},{
							text:"取消",
							iconCls:'icon-cancel',
							handler:function(){
								addDialog.dialog("destroy");
							}
						}],
						onClose:function(){
							$(this).dialog("destroy");
						}
		 			});
		 		},
				
		 	   //编辑用户
			   editUser:function(){
				  var select = $userList.datagrid("getSelected");
				  if(!select){
					 $alert("请选择用户进行编辑");
					 return false;
				  }
	 			var editDialog =$('<div id="editSysUser"></div>').dialog({    
	 				href:"sysUserAction!preEdit.act?userId="+select.id+"&roleIds="+select.roleIds,
					width:630,
					height:300,
					title:"编辑用户",
					method:'post',
					modal:true,
					resizable:true,
					buttons:[{
						text:"确定",
						iconCls:'icon-ok',
						handler:function(){
							new Some.form({
								render:"#edit_fm",
								success:function(data){
									handlerResult(data,function(rs){
										editDialog.dialog("destroy");
										$show(rs.message);
										$userList.datagrid("reload");
									},function(rs){
										$alert(rs.message);
									});
								},
								onSubmit: function(param){
									
							    }  
							}).submit();   
						}
					},{
						text:"取消",
						iconCls:'icon-cancel',
						handler:function(){
							editDialog.dialog("destroy");
						}
					}],
					onClose:function(){
						$(this).dialog("destroy");
					}
	 			});
	 		},
		 	/*	
			accreditAdmin:function(id,index){
				new Some.dialog({
					top:100,
					title:"<s:text name='administrator_authorization'/>",
					width:450,
					height:"auto",
					href:"systemAdmin!preAccredit.act?usrSmUser.id="+id,
				});
			},*/
				
			deleteUser:function(id,index){
				var select = $userList.datagrid("getSelected");
				if(!select){
					$alert("请选择用户进行删除");
					return false;
				}
				if(select.id == '${_ADM.id}'){
					$alert("无法删除自身登陆用户");
					return false;
				}
				$confirm("确定删除所选用户吗?",function(){
					var loading=new Some.loading();
					loading.show();
					$.post("sysUserAction!delete.act",{"sysUser.id":select.id},function(text){
						loading.close();
						handlerResult(text,function(data){
							$userList.datagrid("reload");
							$show(data.message);
	 					},function(data){
	 						$show(data.message);
	 					});
					});
				});
			}
		};
				
		
		</script>
	</head>
	<body id="body" >
		<div class="easyui-layout" title="" data-options="border:false,fit:true" style="background-color: #fcfdfe;" >
			
			<div id="selectDiv" class = "table_seach_div" data-options="region:'north',title:'查询条件',border:false,split:false" style="overflow: hidden;background-color: #F8F8F8" >
				<%--<div id="selectDiv" style="height:auto !important;padding:10px;float:left !important;width: 100%;">
					--%><div class="search-div">
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
							<select id="sysRoles"></select>
						</div>
					</div>
					<%-- <div class="search-div">
						<label>部门：</label>
						<div class="select">
							<select id="queryDepts"></select>
						</div>
					</div> --%>
					<div class="search-div">
						<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="user.search()" >搜索</a> 
						<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="user.reset()" >重置</a> 
					</div>
				<%--</div>--%>
			</div>
			
			<div data-options="region:'center',border:false">
				<table id="userList"></table>
			</div>
			
			<div id="tool_bar">
				<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="user.addUser()">新增</a>
				<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain:true" onclick="user.editUser()">编辑</a>
				<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="user.deleteUser()">删除</a>
			</div>
		</div>
	</body>
</html>



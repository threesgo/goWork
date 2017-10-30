<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/public/public.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/menu.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css" />

<title>云网装饰</title>
<script type="text/javascript">
	var index_tabs;
	var index_tabsMenu;
	var index_layout;
	var projectsDialog;
	var modifyPwdDialog;
	var selectRoleDialog;
	
	$(function() {
		index_layout = $('#index_layout').layout({
			fit : true
		});

		index_tabs = $('#index_tabs').tabs({
			fit : true,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				index_tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			}
		});

		index_tabsMenu = $('#index_tabsMenu').menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('title');

				if (type === 'refresh') {
					index_tabs.tabs('getTab', curTabTitle).panel('refresh');
					return;
				}

				if (type === 'close') {
					var t = index_tabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						index_tabs.tabs('close', curTabTitle);
					}
					return;
				}

				var allTabs = index_tabs.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					index_tabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});
		
		/******************按钮操作**********************/
		//选择角色
		$("#sel_rol").click(function() {
			selectRoleDialogFun();
		});
		
		//退出系统
		$("#out").click(function() {
			$confirm("确认退出系统?",function(){
				window.location.href = "loginAction!logout.act";
			});
			return false;
		});
		
		//修改密码
		$("#editPassWord").click(
			function(){
				modifyPwdDialog=$('<div id="editPassWord"></div>').dialog({
					href : "systemAdmin!preUpdatePassword.act",		
					resizable:true,         
					closed: false,
					title:"修改用户密码",
					width:500,
					height:300,
					modal: true,
					onClose:function(){
						modifyPwdDialog.dialog("destroy");
						return true;
					},
					buttons: [{  
				    	text: '确认',
				    	iconCls: 'icon-ok',
				    	handler: function () {
				    		modifyPassWord(modifyPwdDialog);
				    	}},
				    	{
				        text: '取消',
				        iconCls: 'icon-cancel',
				        handler: function () {
				        	modifyPwdDialog.dialog("destroy");
				        }
			    	}]
				});
			}
		);
		
		$("#info_menu").click(
			function(e){
				$('#info').menu('show', {
					left: e.pageX-10,
					top: e.pageY+15
			});
		});
		
		
		//角色选择
		//如果已有默认角色，直接进入
		if('${defaultRole}'){
			$("#roleName").text('${defaultRole.name}');
			enterSystem('${defaultRole.id}');
		}else{
			selectRoleDialogFun();
		}
	});
	
	/******************刷新菜单**********************/
	function reloadMenu(url,param,defaultUrl){
		var $frame = $("#mainIframe");
		$frame.attr("src",defaultUrl||"");
		$.post(url,param,function(data){
			Some.menu({
				render:"#accordion",
				items:data,
				target:"mainIframe",
				handle:function(url){
					if(url){
						$frame.attr("src",url);
					}else{
						$frame.attr("src","");
					}
				}
			})
			
		},"json");
	}
	
	/******************修改密码**********************/
	function modifyPassWord(modifyPwdDialog){
		var $modify_password_form=$("#modify_password_form");
		$modify_password_form.form('submit', {    
		    onSubmit: function(){    
		    },    
		    success:function(text){
		 		handlerResult(text,function(data){
		 			modifyPwdDialog.dialog("destroy");
					$show(data.message);
				},function(data){
					$alert(data.message);
				});
		    }    
		}); 
	};
	
	/******************选择角色**********************/
	function selectRole(row,selectRoleDialog){
		$("#roleName").text(row.name);
		//选择某一个角色后，给用户角色中间表添加一个默认值，关联的其他角色设置为空
		$.post("sysUserAction!updateUserRoleDefault.act",{"roleId":row.id});
		enterSystem(row.id,selectRoleDialog);
	}
	
	/******************根据默认角色或者自选角色进入系统**********************/
	function enterSystem(roleId,selectRoleDialog){
		reloadMenu("mainAction!findUserMenu.act",{roleId:roleId});
		
		if(selectRoleDialog){
			selectRoleDialog.dialog("destroy");
		}
	}
	
	
	/******************角色选择弹出框**********************/
	function selectRoleDialogFun(){
		selectRoleDialog=$('<div id="selectRoleDialog"></div>').dialog({
			href : "mainAction!selectRole.act",
			resizable:true,         
			closed: false,
			title:"角色选择",
			width:350,
			height:220,
			modal: true,
			onClose:function(){
				selectRoleDialog.dialog("destroy");
				return true;
			}
			/*
			,
			buttons: [{  
		    	text: '确认',
		    	iconCls: 'icon-ok',
		    	handler: function () {
		    		selectRole(selectRoleDialog);
	    		}}
	    	]
			*/
		});
	}
	
	/**
	 *页面定时刷新
	 */
 	setInterval(function(){
    	$.post("heartBeatAction.act");
   	}, 300000); 
</script>

<style>
	.button_icon{
		height: 24px;
		min-width: 24px;
		
		display: inline-block;
	    cursor: pointer;
	    -webkit-user-select: none;
	    -moz-user-select: none;
	    -ms-user-select: none;
	    border:0;
	    color: #333;
	}
	.text{
	    padding-left: 26px;
    	color: rgb(71,127,166);
    	font-size: 14px;
	}
	.cs-north-logo{
		float: left;
		margin-left:30px;
		margin-top:10px;
		color: rgb(71,127,166);
		font-family: "微软雅黑";
	}
	.logo{
		height: 70px;
		overflow: hidden;
		background-color:#fafafa;
		background-size:cover;
		/*background-image: url('style/images/north_background.png');*/
		
	}
</style>
</head>
<body>
	<input id="userId" value="${sessionScope._ADM.id}" type="hidden"/>
	
	<div id="index_layout">
		<div data-options="region:'north',href:''"  class="logo">
			<div class="cs-north-bg">
				<div style="float: left; margin-left: 20px;margin-top: 10px">
					<img src="${pageContext.request.contextPath}/style/images/logo.png"  style="height: 50px"></img>
				</div>
				<!-- 
				<div class="cs-north-logo" >
					<img src="${pageContext.request.contextPath}/style/images/name.png"  style="height: 60px;width:140px"></img>
				</div>
				 -->
				<div>
					<ul id="top_menu">
						<li class="sel_pro" id="info_menu"><button class="button_icon user-24 text">[${sessionScope._ADM.userName}]</button></li>
						<li class="sel_pro" id="out"><button class="button_icon exit-24"></button></li>
					</ul>
				</div>
			</div>
			<div id="info" class="easyui-menu" style="width:150px;">
				<div>角色：<span id="roleName"></span></div>
				<div>姓名：<span>[${sessionScope._ADM.realName}]</span></div>
				<div id="editPassWord">修改密码</div>
				<div id="sel_rol">切换角色</div>
			</div>
		</div>
		<%-- href:'${pageContext.request.contextPath}/layout/west.jsp', --%>
		<div data-options="region:'west',split:true" title="模块导航" style="width: 200px; overflow: hidden;" id="accordion"></div>
		<div data-options="region:'center'" title="" style="overflow: hidden;">
			<div id="index_tabs" style="overflow: hidden;">
				<%--
				<div title="首页" data-options="border:false" style="overflow: hidden;">
					<iframe src="${pageContext.request.contextPath}/MyJsp.jsp" frameborder="0" style="border: 0; width: 100%; height: 98%;" id="mainIframe"></iframe>
				</div>
				--%>
				<iframe src="" frameborder="0" style="border: 0; width: 100%; height: 100%;" id="mainIframe" name="mainIframe"></iframe>
			</div>
		</div>
		<%--
			<div data-options="region:'south',href:'${pageContext.request.contextPath}/layout/south.jsp',border:false" style="height: 30px; overflow: hidden;"></div>
		--%>
		</div>
</body>
</html>
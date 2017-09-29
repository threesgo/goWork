<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>YunNet Decorate</title>

		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="${pageContext.request.contextPath}/scriptPlugins/bootstrap-3.3.5/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/login/font-awesome.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/login/ace.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/login/ace-rtl.min.css" />
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									
								</h1>
							</div>

							<div class="space-6"></div>

							<div class="position-relative" style="margin-top: 100px">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<!--  
											<img alt="" src="images/logo.png" style="width: 300px;height:50px">
										     -->
												<h1>
													<i class="icon-leaf green"></i>
													<span class="red">YunDecorate</span>
												</h1>											
											<h4 class="header blue lighter bigger" style="font-weight: bold;">
												<i class="icon-coffee green"></i>
												请输入用户名和密码
											</h4>

											<div class="space-6"></div>

											<form action="loginAction!login.act" method="post"  id="loginForm">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control"  name="sysUser.userName"  placeholder="用户名" value="admin"/>
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" name="sysUser.passWord" placeholder="密码" value="admin"/>
															<i class="icon-lock"></i>
														</span>
													</label>
													
													<!--  
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" id="vcode" name="vcode"  class="form-control"  style="width:110px; float:left" /> 
															<img src="vcodeAction.act" style="cursor:pointer; float:left; 
															margin-left:4px;" onclick="this.src='vcodeAction.act?num='+Math.random()" title="刷新验证码" />
														</span>
													</label>
													-->
													
													<div class="space"></div>

													<div class="clearfix">
														<button type="button"  id="login"  class="width-35 pull-right btn btn-sm btn-primary">
															<i class="icon-key"></i>
															登录
														</button>
													</div>
													<s:if test="#request.actionMessages.size>0">
														<div style=" font:'宋体'; color:#FF0000; font-size:14px; margin-top: 10px; font-weight: bold;" align="center">${actionMessages[0] }</div>
													</s:if>
													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->
									</div><!-- /widget-body -->
								</div><!-- /login-box -->
							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='${pageContext.request.contextPath}/jquery/jquery.min.js'>"+"<"+"/script>");
		</script>

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${pageContext.request.contextPath}/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
		<script type="text/javascript">
			$(function(){
				$("#login").click(function(){
					$("#loginForm")[0].submit();
				});
			});
		</script>
	</body>
</html>

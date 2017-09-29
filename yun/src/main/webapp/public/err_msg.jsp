<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/public/public_msg.jsp" />
<body>
<div class="showMsg" style="text-align:center">
	<h5>提示信息</h5>
    <div class="content guery" style="display:inline-block;display:-moz-inline-stack;zoom:1;*display:inline;max-width:330px">
       <s:if test="actionTransfer.message==null">页面不存在</s:if>
       <s:else>${actionTransfer.message}</s:else>
    
    </div>
    <div class="bottom">
    	<a href="javascript:history.back()">如果您的浏览器没有自动跳转，请点击这里</a>
	<script language="javascript">
	  setTimeout("history.back()",1250);
	</script> 
	 </div>
</div>
</body>
</html>
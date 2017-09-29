<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/public/public_msg.jsp" />
<body>
<div class="showMsg" style="text-align:center">
	<h5>提示信息</h5>
    <div class="content guery" style="display:inline-block;display:-moz-inline-stack;zoom:1;*display:inline;max-width:330px">
      ${actionTransfer.message}
    </div>
    <div class="bottom">
    	<a  id="aid"  href="${actionTransfer.returnUrl}">如果您的浏览器没有自动跳转，请点击这里</a>
	<script language="javascript">
	setTimeout(function(){
	   document.getElementById("aid").click();
	},1250);
	//red
	</script> 
	 </div>
</div>
</body>
</html>
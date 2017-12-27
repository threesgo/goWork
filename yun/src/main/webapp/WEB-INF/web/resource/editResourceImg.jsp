<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/extendScript/imgView.js"></script> 
<script type="text/javascript">
	imgageView($("#file"), $("#img"), $("#div_img"));
	
	function selectFile(){
		$("#file").trigger("click");  
	}
</script>

<style type="text/css">
	#div_img {
		filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(true,sizingMethod=scale )
	}
</style>
<form id="editResourceImgForm" method="post" class="edit-form" enctype="multipart/form-data">
	<input type="hidden" name="sysResource.id" id="sysResource" value="${sysResource.id}"/>
	<input type="file" name="imageFile" accept="image/*" id="file" style="opacity: 0;"/>
	<div align="center">
		<div style="width: 450px;height: 250px;" id="div_img" onclick="selectFile()">
			<img id="img" alt="选择图片" src="${pageContext.request.contextPath}/static/${sysResource.imagePath}?timestamp=${timestamp}"
				 onerror="this.src='${pageContext.request.contextPath}/style/images/upload.png'"  style="max-width: 450px;max-height: 250px;"/>
		</div>
	</div>
</form>
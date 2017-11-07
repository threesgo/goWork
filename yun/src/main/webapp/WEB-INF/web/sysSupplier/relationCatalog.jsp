<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var resourceTypeTree;

$(function(){
	resourceTypeTree=$('#layoutTree').tree({    
		checkbox:true,
		url:"sysSupplierAction!supplierResourceTypeTree.act"
	});
});
</script>

<ul id="resourceTypeTree"></ul>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var brandResourceTypeTree;
$(function(){
	brandResourceTypeTree=$('#brandResourceTypeTree').tree({    
		checkbox:true,
		animate:true,
		url:"sysBrandAction!brandResourceTypeTree.act?sysBrand.id=${sysBrand.id}"
	});
});
</script>
<ul id="brandResourceTypeTree"></ul>
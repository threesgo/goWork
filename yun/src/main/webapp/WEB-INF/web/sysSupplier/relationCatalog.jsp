<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var supplierResourceTypeTree;
$(function(){
	supplierResourceTypeTree=$('#supplierResourceTypeTree').tree({    
		checkbox:true,
		animate:true,
		url:"sysSupplierAction!supplierResourceTypeTree.act?sysSupplier.id=${sysSupplier.id}"
	});
});
</script>
<ul id="supplierResourceTypeTree"></ul>
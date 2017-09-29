<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var layout_west_tree;
	$(function() {
		layout_west_tree = $('#layout_west_tree').tree({
			//url :"mainAction!findUserFunction.act",
			parentField : 'pid',
			onSelect : function(node) {
				if (node.attributes &&node.attributes.url) {
				     //alert(node.attributes.viewType);
					$("#mainIframe").attr("src",node.attributes.url)
				}
			},
			onLoadSuccess:function(node,data){
				
				//$("#mainIframe").attr("src","mainAction!systemIntroduce.act");
				
				/*
				var roots=layout_west_tree.tree("getRoots");//得到所有根目录
				if(roots.length>0){
					//得到第一个根目录下的所有子节点
					var childrens = layout_west_tree.tree("getChildren",roots[0].target);
					if(childrens.length>0){
						//选中第一个根目录下的第一个子节点
						layout_west_tree.tree("select",childrens[0].target)
					}
				}
				*/
			}
		});
	});
</script>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<ul id="layout_west_tree"></ul>
</div>
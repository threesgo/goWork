<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="/public/public.jsp" />
<script type="text/javascript" charset="utf-8">
	var panels;
	$(function() {
		panels = [ {
			id : 'p1',
			title : '待办事项',
			height : 500,
			collapsible : true,
			href : 'mainAction!todo.act'
		}, 
		
		{
			id : 'p2',
			title : '关于系统',
			height : 200,
			collapsible : true,
			href : 'mainAction!about.act'
		},
		
		
		{
			id : 'p3',
			title : '友情链接',
			height : 200,
			collapsible : true,
			href : 'mainAction!link.act'
		},
		
		{
			id : 'p4',
			title : '更新日志',
			height : 500,
			collapsible : true,
			href : 'mainAction!update.act'
		}
		
		];

		$('#portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-state', getPortalState(), {
					expires : 4
				});
			}
		});
		var state = $.cookie('portal-state');
		if (!state) {
			state = 'p1,p2:p3,p4';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		$('#portal').portal('resize');

	});

	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
	
	function getPortalState() {
		var aa = [];
		for ( var columnIndex = 0; columnIndex < 2; columnIndex++) {
			var cc = [];
			var panels = $('#portal').portal('getPanels', columnIndex);
			for ( var i = 0; i < panels.length; i++) {
				cc.push(panels[i].attr('id'));
			}
			aa.push(cc.join(','));
		}
		return aa.join(':');
	}
	
	function addPortalPanels(portalState) {
		var columns = portalState.split(':');
		for ( var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
			var cc = columns[columnIndex].split(',');
			for ( var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					$('#portal').portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="portal" style="position: relative">
			<div></div>
			<div></div>
		</div>
	</div>
</body>
</html>
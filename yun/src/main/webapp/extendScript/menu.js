/**
 * @Date 2013年11月30日 21:08:52
 * 基于jQuery EasyUi的菜单
 * 
 * */
var Some=Some||{};
Some.menu=(function(window,$){
	
	function _menu(options){
		
		var render = $(options.render);
		render.empty();
		var accordion   =  	$("<div></div>").appendTo(render).addClass("accordion").addClass("noborder")
										 	.css({height:"100%",width:"100%"})
										 	.accordion({
												animate : true,
												fit : true,
												border : false
										 	});
		
		if(options.items && options.items.length){
			
			$.each(options.items,function(index,item){
				var ul = $("<ul class='navlist'></ul>");
				if(item.children && item.children.length){
					$.each(item.children,function(i,child){
						ul.append("<li>" 
								 + "<div class='accordion-item ' >" 
								 +   "<a  rel='"+(child.url?child.url:' ')+"' href='#'>"
								 +     "<span class='icon "+child.iconCls+"'>&nbsp;</span>"  
								 +     "<span class='nav'>"+child.text+"</span>"
								 +   "</a>"
								 + "</div>"
								 +"</li>");
					});
					accordion.accordion('add', {
						title : item.text,
						content :ul,
						selected:index == 0,
						border : false,
						iconCls :item.iconCls
					});
				}
			});
			
			bindEvent(render,options);
		}
	}
	function bindEvent(render,options){
		
		render.find(' li a').unbind("click").click(function(){
	        render.find('ul li div').removeClass("selected");
			$(this).parent().addClass("selected");
			options.handle && options.handle.call(this,$(this).attr("rel"));
			
		}).hover(function() {
			$(this).parent().addClass("hover");
			return false;
		}, function() {
			$(this).parent().removeClass("hover");
			return false;
		});
	}
	
	return _menu;
	
	  
})(window,jQuery);

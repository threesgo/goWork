/**
 * frame插件
 * 沈军军
 * 2013年11月29日 22:06:12
 */
var Some=Some||{};
(function(Some,$){
	Some.frame =function(agrs){
		var options=this.options=$.extend({},Some.frame.defaults,agrs);
		var _01="name"+new Date().getTime();
			var _03=$(options.render);
			if(!_03.length)
				_03=$("<iframe frameborder=0 style='" + options.style + "' />").appendTo($(options.parent));
			this.target=_03.attr("name",_01).unbind().bind("load",function(){
						options.loadSuccess(top[_01]);
				});
		this.getDocument=function(){
			if(this.target.length){
				return this.target[0].contentWindow;
			}
			else{
				return null;
			}
		};
		this.load=function(url){
			if(options.beforeLoad(url)==false) return;
			_03.attr("src",url);
		};
	};
	Some.frame.defaults={
		render:null,
		parent:null,
		style:" width: 100%;height: 100%;border: none;",
		beforeLoad:function(url){},
		loadSuccess:function(target){}
	};
})(Some,jQuery);
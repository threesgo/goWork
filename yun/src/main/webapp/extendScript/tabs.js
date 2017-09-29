/**
 * 基于EasyUI中的tabs扩展
 * 沈军军
 * 2013年11月29日 15:11:16
 */
var Some=Some||{};
Some.tabs=(function(window,$){
   function _tabs(args){
	   var $this=this;
	   if(typeof args == "string"|| args instanceof $)
		   $this.render=$(args);
	   else
		   this.render=$(args.render).tabs($.extend({},{},args));
	   for(var method in $.fn.tabs.methods){
		   (function(m,$){
			   $this[m]=function(arg,fn){
				  return $.fn.tabs.methods[m]($this.render,arg,fn);
			   };
		   })(method,$); 
	   }
   }
   _tabs.prototype={
		   closeAll:function(){
			   var $this=this;
			   $this.render.find("ul.tabs span.tabs-title").each(function(){
				  $this.close($(this).text()); 
			   });
		   },
		   getSelectedRender:function(){
			   return this.render.find(" > div.tabs-panels > div.panel > div.panel-body:visible");
		   },
		   refresh:function(_01){
			   var $this=this;
			   $this.getTab((_01||0)).panel("refresh");
		   },
		   addTabs:function(tabs){
			   var $this=this;
			   $.each(tabs,function(index,item){
				   $this.add(item);
			   });
		   }
   };
		 return _tabs;
})(window,jQuery);

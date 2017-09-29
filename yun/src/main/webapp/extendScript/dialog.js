/***
 * 基于EasyUI中的window扩展
 * 2013年12月2日 10:40:25
 * 沈军军
 */
var Some=Some||{};
Some.dialog=(function(window,$){
	 function _dialog(args){
		 var $this=this;
		  for(var method in $.fn.window.methods){
			   (function(m,$){
				   $this[m]=function(arg,fn){
					   return  $.fn.window.methods[m]($this.render,arg,fn);
				   };
			   })(method,$); 
		   }
		   for(var method in $.fn.panel.methods){
			   (function(m,$){
				   $this[m]=function(arg,fn){
					   return  $.fn.panel.methods[m]($this.render,arg,fn);
				   };
			   })(method,$); 
		   }
		   this.show=function(arg){
			   if(!($this.render&&$this.render.hasClass("window-body")))
				   return $this.open();
			 return  $.fn.panel.methods.open($this.render,arg);
		   };
		   this.open=function(){
			   if(typeof args == "string"|| args instanceof $)
				   $this.render=$(args);
			   else{
				   var _01=$.extend({},{
					   id:null,
					   collapsible:false,
					   minimizable:false,
					   maximizable:false,
					   modal:true,
					   parent:"body",
					   onBeforeMove:function(opts){
						   opts.left=opts.left>=0?opts.left:0;
						   opts.top=opts.top>=0?opts.top:0;
						   
						   
					   },
					   onClose:function(){
						   $this.destroy();
					   },
					   onDestroy:function(){
						   
					   }
				   },args);
				   _01.render=_01.render||$("<div "+(_01.id?"id="+_01.id:"")+"></div>").appendTo(_01.parent);
//				   var $parent=$(_01.parent);
//				   if($parent.length){
//					   var p_w=$parent.width();
//					   var p_h=$parent.height();
//					   if(p_w<_01.width){
//						   _01.width=p_w;
//						   _01.left=0; 
//					   }
//					   if(p_h<_01.height){
//						   _01.height=p_h;
//						   _01.top=0;
//					   }
//				   }
				   
				   this.render=$(_01.render).window(_01);
			   }
		   };
		   this.open();
	 }
	_dialog.prototype={
			
	};
	return _dialog;
})(window,jQuery);
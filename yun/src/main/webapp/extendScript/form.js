/**
 * 基于EasyUI中的form扩展
 * 沈军军
 * 2013年12月2日 14:11:35
 */
var Some=Some||{};
Some.form=(function(window,$){
   function _form(args){
	   var $this=this;
	   if(typeof args == "string"|| args instanceof $){
		   $this.render=$(args);
	   }
	   else{
		   var _02=new Some.loading();
		   var _01=$.extend({},{
			   				   validate:true,
							   berforeSubmit:function(param){
								   _02.show();
							   },
							   errorSubmit:function(){_02.close();},
							   berforeSuccess:function(data){
								   _02.close();
							   }
		   				},args);
		   var _03=_01.onSubmit,_04=_01.success;
		   _01.onSubmit=function(param){
			   _01.berforeSubmit(param);
			   if(_01.validate){
				   if(!$this.validate()){
					   _01.errorSubmit();
					   return false;
				   }
			   }
			   var _04=!_03?true:_03(param);
			   if(_04==false){
				   _01.errorSubmit();
			   }
			   return _04;
		   };
		   _01.success=function(data){
			   _01.berforeSuccess(data);
			   return _04(data);
		   };
		   this.render=$(args.render).form(_01);
	   }
	   for(var method in $.fn.form.methods){
		   (function(m){
			   $this[m]=function(arg,fn){
				  return $.fn.form.methods[m]($this.render,arg,fn);
			   };
		   })(method); 
	   }
   }
   _form.prototype={};
   return _form;
})(window,jQuery);

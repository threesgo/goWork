var Some=Some||{};
(function($){
/*	Some.EventsListen={
		start:function(){
			$(window).unbind("keydown",keydown).bind("keydown",keydown);
		},
		end:function(){
			$(window).unbind("keydown",keydown);
			$(window).removeData("event_options");
		}
	};
	
	function keydown(event){
		event=event|| window.event;
		var keyCode=event.keyCode;
		var target=$($(window).data("currentTarget"));
		if(event.ctrlKey){
			return triggerHandler("ctrlKey+"+keyCode,target,event);
		}
		if(event.shiftKey){
			return triggerHandler("shiftKey+"+keyCode,target,event);
		}
		if(event.altKey){
			return triggerHandler("altKey+"+keyCode,target,event);
		}
		return triggerHandler("base+"+keyCode,target,event);
	}
	
	function triggerHandler(key,target,event){
		var options=$(window).data("event_options");
		if(options){
			if(key in options){
				var handler=queryHandler(key,target);
				if(handler){
					return handler(event);
				}
			}
		}
	}
	
	function queryHandler(key,target){
		var $region=target.closest(".event-region");
		var handler=$region.data(key);
		while(!handler && $region.length){
			$region=$region.parent().closest(".event-region");
			handler=$region.data(key);
		}
		return handler;
	}
	$.fn.keyHandler=function(defult){
		var options=$.extend({},{
						key:"base",
						keyCode:null,
						handler:function(event){}
					},defult);
		this.unbind("mouseenter")
			.bind("mouseover ",function(event){
				$(this).css("background-color","yellow");
				$(window).data("currentTarget",this);
			})
			.bind("mouseleave",function(){
				$(this).css("background-color","red");
				$(window).removeData("currentTarget");
			});
		
		
		this.addClass("event-region");
		this.data((options.key+"+"+options.keyCode),options.handler);
		var event_options=$(window).data("event_options")||{};
		event_options[options.key+"+"+options.keyCode]=true;
		$(window).data("event_options",event_options);
		return this;
	};
	*/
	
	
	$.fn.keyEvent=function(defult){
		var options=$.extend({},{
						key:null,
						keyCode:null,
						handler:function(event){}
					},defult);
		this.attr("tabindex",-1);
	/*	var self=this.attr("tabindex",-1);
		self.unbind("mouseenter",queryFocus).bind("mouseenter ",queryFocus)
			.unbind("mouseleave",queryBlus).bind("mouseleave",queryBlus);
		function queryFocus(event){
			if(!self.find(":focus").length){
				self.focus();
			}
		}
		function queryBlus(event){
			if(!$(this).is("body")){
				$(this).blur();
			}
		}*/
		this.unbind("keydown",eventHandler).bind("keydown",eventHandler);
		function eventHandler(event){
			event=event||window.event;
			var key=options.key;
			var flag=true;
			if(key){
				if(key instanceof Array){
					for(var i=0;i<key.length;i++){
						flag=(flag && event[key[i]]);
					}
				}
				else{
					flag= event[key];
				}
				flag=(flag && event.keyCode==options.keyCode);
			}
			else{
				flag=(event.keyCode==options.keyCode);
			}
			if(flag){
				options.handler.call(this,event);
			}
		}
		return this;
	};
	
})(jQuery,Some);
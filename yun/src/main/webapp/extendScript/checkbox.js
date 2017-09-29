(function($){
	function init(self,options){
		var optionValues=options.optionValues;
		self.addClass("checkbox-ul").addClass("tree").attr("style",options.style)
			.css({
				width:options.width,
				height:options.height
			});
		var indexValues=handlerIndexValue(options,optionValues);
		options.indexValues=indexValues;
		$.each(indexValues,function(index,item){
			self.append(initOptions(self,options,index,item.value));
		});
		
		initBinding(self,options);
		
	}
	
	function initBinding(self,options){
		self.find(">li").unbind("click").bind("click",function(){
			var that=$(this);
			var checkbox=that.find("span.tree-checkbox");
			if (checkbox.hasClass("tree-checkbox0")){
				checkbox.removeClass("tree-checkbox0").addClass("tree-checkbox1");
				that.addClass("selected");
			}
			else{
				checkbox.removeClass("tree-checkbox1").addClass("tree-checkbox0");
				that.removeClass("selected");
			}
			options.onChange.call(self);
		});
	}
	
	function initOptions(self,options,id,value){
		var li=$('<li  class="checkbox-li '+options.layout+' " item="'+id+'"></li>');
		$('<span class="tree-checkbox tree-checkbox0"  ></span>').appendTo(li);
		li.append('<span class="tree-title">'+value+'</span>');
		setValue(self,options.values);
		return li;
	}
	
	function handlerIndexValue(options,optionValues){
		var indexValues={};
		if(options.simple){
			$.each(optionValues,function(index,item){
				indexValues[item]={value:item,node:item};
			});
		}else{
			$.each(optionValues,function(index,item){
				indexValues[item[options.valueField]]={value:item[options.textField],node:item};
			});
		}
		return indexValues;
	}
	
	function setValue(self,values){
		var li=self.find("li");
		li.removeClass("selected");
		li.find("span.tree-checkbox").attr("class","tree-checkbox tree-checkbox0");
		var $item=null;
		var $span=null;
		li.each(function(index,item){
			$item=$(item);
			$span=$item.find("span.tree-checkbox");
			if(Some.util.array.indexOf(values,$item.attr("item"))!=-1){
				$span.removeClass("tree-checkbox0").addClass("tree-checkbox1");
				$item.addClass("selected");
			}
		});
	}
	
	
	$.fn.checkbox=function(method,param){
		if (typeof method == "string") {
			return $.fn.checkbox.methods[method](this, param);
		}
		return this.each(function() {
			var options=$.extend({},$.fn.checkbox.defaults,method);
			$(this).data("options", options);  
			
			init($(this),options);
		});
		
	};
	function filterNode(value){
		return value.node;
	}
	
	
	/***
	 * 方法
	 */
	$.fn.checkbox.methods={
			getValue:function(self,filter){
				if(!filter){
					filter=filterNode;
				}
				var selects=self.find("li.selected");
				var options=self.data("options");
				var values=[];
				var value=null;
				selects.each(function(index,item){
					value=options.indexValues[$(item).attr("item")];
					if(value){
						values.push(filter(value));
					}
				});
				return values;
			},
			destroy:function(self){
				return self.each(function(index,item){
					var $item=$(item);
					$item.removeData("options");
					$item.remove();
				});
			},
			setValue:function(self,values){
				return setValue(self, values);
			}
			
	};
	
	
	/***
	 * 默认值
	 */
	$.fn.checkbox.defaults={
		width : "auto",
		height : 22,
		layout:"X",
		optionValues:[],
		simple:true,
		style:"",
		values:[],
		valueField:"id",
		textField:"text",
		onChange : function(_05a,_05b) {
			
		}	
	};
})(jQuery);
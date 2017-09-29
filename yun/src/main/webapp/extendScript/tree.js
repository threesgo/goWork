/**
 * 基于EasyUI中的tree扩展
 * 沈军军
 * 2013年11月28日 17:13:37
 */
var Some=Some||{};
Some.tree=(function(window,$){
   function _tree(args){
	   var $this=this;
	   if(typeof args == "string"|| args instanceof $)
		   $this.render=$(args);
	   else
	    this.render=$(args.render).tree($.extend({},{
	    	animate:true
	    },args));
	   for(var method in $.fn.tree.methods){
		   (function(m,$){
			   $this[m]=function(arg,fn){
				  return $.fn.tree.methods[m]($this.render,arg,fn);
			   };
		   })(method,$); 
	   }
   }
   _tree.prototype={
		   	getLevel:function(target){
			    return $(target).find("span.tree-indent").length;
		   	},
		   	getCloseChild:function(target){
		   		var _01=$(target).next("ul").find(">li");
		   		var _02=[],_03=this;
		   		_01.each(function(index,item){
		   			var _04=$(item).find(">div.tree-node");
		   			if(_04.length)
		   				_02.push(_03.getNode(_04[0]));
		   		});
		   		return _02;
		   	},
		   	isParent:function(target,parent){
		   		/***
		   		 * _01是目前节点对象
		   		 * _02是被比较的父级节点对象
		   		 */
		   		return $(parent).next("ul").find("div.tree-node").index($(target)) > -1;
		   		
		   	},
		   	isBrother:function(_01,_02){
		   		var _03=$(_01).parent("li");
		   		var _04=$(_02).parent("li");
		   		return _03.siblings("li").index(_04)>-1;
		   	},
		   	getBrother:function(target){
		   		var _01=[],_03=this;
		   		$(target).parent("li").siblings("li").each(function(index,item){
		   			var _02=$(item).find(">div.tree-node");
		   			if(_02.length)
		   				_01.push(_03.getNode(_02[0]));
		   		});
		   		return _01;
		   	},
		   	drop:function(target){
		   		var _01=this;
		   		var parent=_01.getParent(target);
		   		this.remove(target);
		   		if(parent&&!tree.hasChildren(parent.target)){
		   			_01.changeFolder(parent.target)
		   		}
		   	},
   			isRoot:function(target){
   				return $(target).closest("ul").hasClass("tree");
   			},
   			trigger:function(target,handler){
   				$(target).trigger(handler);
   			},
   			refreshClick:function(options){
			   var $this=this;
			   var _01=options.target;
			   if($this.isLeaf(_01))
				   _01=$this.getParent(_01).target;
				this.reload(_01,function(){
					$this.click(options.id);
				});
   			},
   			click:function(_01){
   			  var $this=this;
   				var _02=$this.find(_01);
   				if(_02)
   					$(_02.target).trigger("click");
   			},
   			getIndex:function(_01){
   				var _02=$(_01).parent("li");
		   		return _02.parent("ul").find(">li").index(_02);
   			},
   			getBody:function(target){
   				return $(target).closest("li");
   			},
   			unselect:function(target){
   				if($(target).hasClass("tree-node-selected")){
   					$(target).removeClass("tree-node-selected");
   				}
   			},
   			toggleSelect:function(target){
   				if($(target).hasClass("tree-node-selected")){
   					$(target).removeClass("tree-node-selected");
   				}else{
   					this.select(target);
   				}
   			},
   			isSelected:function(target){
   					return $(target).hasClass("tree-node-selected");
   			},
   			hasChildren:function(target){
   				return $(target).next("ul").find(">li").length>0;
   			},
   			getChild:function(target,index){
   				var _01=$(target).next("ul").find(">li:eq("+index+")").find("div.tree-node");
   				if(_01.length)
   					return this.getNode(_01[0]);
   				return null;
   			},
   			getFirstChild:function(target){
   				var _01=$(target).next("ul").find(">li:first-child").find("div.tree-node");
   				if(_01.length)
   					return this.getNode(_01[0]);
   				return null;
   			},
   			getLastChild:function(target){
   				var _01=$(target).next("ul").find(">li:last-child").find("div.tree-node");
   				if(_01.length)
   					return this.getNode(_01[0]);
   				return null;
   				
   			},
   			getNext:function(target){
   				var _01=$(target).parent("li").next("li").find("div.tree-node");
   				if(_01.length)
   					return this.getNode(_01[0]);
   				return null;
   			},
   			getPrev:function(target){
   				var _01=$(target).parent("li").prev("li").find("div.tree-node");
   				if(_01.length)
   					return this.getNode(_01[0]);
   				return null;
   			},
   			isCollapse:function(target){
   				return $(target).find("span.tree-collapsed").length>0;
   			},
   			changeFolder:function(target){
   				var _01=$(target);
   				var _02=_01.find("span.tree-icon");
   				if(_02.hasClass("tree-folder"))
   					return ;
   				_02.attr("class","tree-icon tree-folder");
   				_02.prev("span").attr("class"," tree-hit tree-collapsed");
   			},
   			changeOpenFolder:function(target){
   				var _01=$(target);
   				var _02=_01.find("span.tree-icon");
   				if(_02.hasClass("tree-folder"))
   					return ;
   				_02.attr("class","tree-icon tree-folder tree-folder-open");
   				_02.prev("span").attr("class","tree-hit tree-expanded");
   			},
   			expandAndClickChild:function(array){
   					var $this=this;
   					var index=0;
   					$this.reload(undefined,function(){
   						_01(null,array[index++]);
   					});
   					function _01(target,id){
   						var _02=$this.find(id);
   						if(_02){
   							if(index<array.length)
   								$this.expand(_02.target,function(){
   										_01(_02.target,array[index++]);
   								});
   							else
   								$this.expand(_02.target,function(){
   											$(_02.target).trigger("click");
   								});
   						}
   					}
   			}
   };
		 return _tree;
})(window,jQuery);

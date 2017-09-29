(function(Some, $) {
	Some.Editor=function(defaults){
		/***
		 * 对象实例化别名
		 */
		var self=this;
		
		
		/***
		 * 参数整理
		 */
    	var options=this.options= $.extend({}, Some.Editor.defaults, defaults);
    	
        /**
         * 获取当期页面编辑对象
         */
        function _01(){
        	try {
        		return self.getSelectVar()||{} ;
        	}
        	catch (e) {
        		return {};
        	}
        }
        
        /**
         * 
         * @returns
         */
        function _21(){
        	var _21a=options.loading()||Some.loading;
        	return new _21a({parent:options.render()});
        }
        
        /**
         * 判断当前页面是否为编辑模式
         * @returns
         */
        
        this.isEdit=function(){
        	var _02=_01();
        	return _02.isEdit==true;
        };
        
        
        this.changeEdit=function(flag){
        	return _01().isEdit=flag;
        };
        
        
        /***
         * 获取当前编辑句柄
         */
        this.getSelectVar=function(){
        	return window[options.render().find(".variable").attr("var")];
        };
        this.edit=function(){
        	var _02=_01();
        	if(_02.isEdit==true)
        		/**
        		 * 已处于编辑状态
        		 */
        		return false;
    		/**
    		 * 执行编辑前操作
    		 */
    		if(_02.onEdit&&_02.onEdit.call(self)==false)
                /**
                 * 执行判断错误
                 */
    			
    			return false;
    		 
			/**
			 * 页面跳转模式
			 */
			if(_02.url){
				var _12=_21();
				_12.show();
				$.get(_02.url,function(html){
							_12.close();
							if(_02.onload&&_02.onload(html)==false)
								return;
							options.render().empty().append(html);
							self.setEditing(_02);
						},"html");
			 }
			/**
			 * 本地页面模式
			 */
			else{
				/**
				 * 本地自定义模式
				 */
				if(_02.local&&_02.local==true){
					if(_02.localEdit){
						_02.localEdit.call(self);
					}
				}
				else{
					var _04=options.render().find(".edit-item");
					_04.each(function(index,value){
						var _05=$(value);
						var _06=_05.attr("edit-type")||"text";
						if(Some.Editor.items[_06]){
							var item=Some.Editor.items[_06];
							item.onEdit(_05);
						}
					});
					
					var _23=options.render().find(".edit-title");
					
					_23.each(function(index,value){
						var _24=$(value);
						var _25=_24.attr("edit-title-type");
						var item=Some.Editor.titles[_25];
						if(_25 && item){
							item.onEdit(_24);
						}
					});
					
					
					/**
					 * 设置编辑模式状态
					 */
					self.setEditing(_02);
				}
		     }
		};
		/***
		 * 设置编辑状态
		 */
		this.setEditing=function(_13){
			var _14=_13||_01();
			_14.isEdit=true;
			options.onChangeStatus.call(self,true);
			if(_14.afterEdit)
				/**
				 * 执行编辑后方法
				 */
				_14.afterEdit();
		};
        /**
         * 保存操作
         */
       this.save=function(handler){
    	   /**
    	    * 编辑模式
    	    */
    	   var _03=_01();
    	   if(_03.isEdit){
    		      /**
    		       * 执行保存前的操作
    		       */
             if(_03.onSave&&_03.onSave.call(self,handler)==false){
            	 /**
            	  * 执行判断处出错
            	  */
            	 return false;
             }
         	 var _13=options.render().find("form.edit-form");
         	 var form= new options.form({
             		render:_13,
             		url:_13.attr("action"),
             		validate:true,
             		onSubmit:function(param){
             			var _04=_13.find(".edit-item");
    					_04.each(function(index,value){
    						var _05=$(value);
    						var _06=_05.attr("edit-type")||"text";
    						var item=Some.Editor.items[_06];
    						if(item && item.onSubmit){
    							var _07=_05.siblings(".edit-view-item");
    							_07.blur();
    							item.onSubmit(_05,_07,param);
    						}
    					});
             			
             			if(_03.onSubmit){
             				return	_03.onSubmit(param);
             			}
             		},
             		success:function(data){
             			var context={};
             			if(options.saveSuccess(data,context)!=false){
             				if(_03.success){
             					_03.success(data,context);
             				}
             				if(handler){
             					handler(true,data,context);
             				}
             			}else{
             				if(handler){
             					handler(false,data,context);
             				}
             			}
             		}
             	});
         	 form.submit();
    	   }
        };
       this.view=function(){
    		var _07=_01();
        	if(_07.isEdit){
        		/**
        		 * 执行显示前操作
        		 */
        		if(_07.onView&&_07.onView()==false)
                    /**
                     * 执行判断错误
                     */
        			
        			return false;
        		
    			/**
    			 * 页面跳转模式
    			 */
    			if(_07.url){
    				var _13=_21();
    				_13.show();
    				options.render().load(_07.url,function(html){
    											_13.close();
    											//_12();
    											self.setViewing(_07);
    					                        if(_07.onload)
    					                        	_07.onload(html);
    				                      });
    			 }
    			/**
    			 * 本地页面模式
    			 */
    			else{
    				if(_07.local&&_07.local==true){
    					if(_07.localView){
    						_07.localView();
    					}
    				}
    				else{
    					
    					var _08=options.render().find(".edit-item");
    					_08.each(function(index,value){
    						var _09=$(value);
    						var _10=_09.attr("edit-type")||"text";
    						if(Some.Editor.items[_10]){
    							Some.Editor.items[_10].onSave(_09,_09.siblings(".edit-view-item"));
    						}
    					});
    					
    					
    					var _26=options.render().find(".edit-title");
    					_26.each(function(index,value){
    						var _27=$(value);
    						var _28=_27.attr("edit-title-type");
    						var item=Some.Editor.titles[_28];
    						if(_28 && item){
    							item.onView(_27);
    						}
    					});
    					
    					
    					
    					self.setViewing(_07);
    					//_12();
    				}
				}
        	}
       }; 
       this.setViewing=function(_15){
    		var _16=_15||_01();
    		_16.isEdit=false;
    		options.onChangeStatus.call(self,false);
			if(_16.afterView){
				/**
				 * 执行编辑后方法
				 */
				_16.afterView();
			}
       } ;
       /****
        * 供用户自定义处理函数方法，
        * _17对应的方法签名如（add,delete....)
        * _18 true/false   true表示
        
       this.handler=function(_17,_18,_19){
    	   var _20=_01();
    	   
    	   
       };*/
       this.cancel=function(opts){
    	   	var _opts=opts||{};
        	var _07=_01();
        	if(_07.isEdit || _opts.editable==true){
        		/**
        		 * 执行取消编辑前操作
        		 */
        		if(_07.onCancel&&_07.onCancel()==false)
                    /**
                     * 执行判断错误
                     */
        			
        			return false;
        		
        		
    			/**
    			 * 页面跳转模式
    			 */
    			if(_07.url || _opts.url){
    				var _14=_21();
    				_14.show();
    				options.render().load(_07.url||_opts.url,function(html){
    											_14.close();
    											self.setCanceling(_07);
    					                        if(_07.onload){
    					                        	_07.onload(html);
    					                        }
    					                        if(_opts.handler){
    					                        	_opts.handler.call(self,html);
    					                        }
    					                        
    					                        
    				                      });
    			 }
    			/**
    			 * 本地页面模式
    			 */
    			else{
    				if(_07.local&&_07.local==true){
    					if(_07.localCancel){
    						_07.localCancel();
    					}
    				}
    				else{
    					var _08=options.render().find(".edit-item");
    					_08.each(function(index,value){
    						var _09=$(value);
    						var _10=_09.attr("edit-type")||"text";
    						if(Some.Editor.items[_10])
    							Some.Editor.items[_10].onView(_09,_09.siblings(".edit-view-item"));
    					});
    					
    					var _26=options.render().find(".edit-title");
    					_26.each(function(index,value){
    						var _27=$(value);
    						var _28=_27.attr("edit-title-type");
    						var item=Some.Editor.titles[_28];
    						if(_28 && item){
    							item.onView(_27);
    						}
    					});
    					self.setCanceling(_07);
    				}
					if(_opts.handler){
	                	_opts.handler.call(self,html);
	             	}
    			}
        	}
        };
        this.setCanceling=function(_17){
        	var _18=_17||_01();
        	_18.isEdit=false;
        	options.onChangeStatus.call(self,false);
        	if(_18.afterCancel)
    			/**
    			 * 执行取消编辑后方法
    			 */
        		_18.afterCancel();
        	
        };
        
      };
      Some.Editor.titles={
    	   required:{
    		   onEdit:function(target){
    			   target.before("<span  class=\"title-view\" style=\"color: red;font-size:14;margin-right: 5px;\">*</span>");
    		   },
    		   onView:function(target){
    			   target.prev(".title-view").remove();
    		   }
    	   } 
      };
      
      Some.Editor.items={
    		text:{
    			onEdit:function(target){
    				var _01=target.attr("name");
    				var proxy=$("<input type='text' class='edit-view-item' value='"+target.text()+"'/>").insertAfter(target)
			    				.css({
			    					height:target.innerHeight(),
			    					width:target.innerWidth()
			    				}).attr("name",_01).attr("id",(_01||"").split(".").join("_"))
			    				  .attr("data-options",target.attr("data-options"))
			    				  .validatebox();
    				target.hide();
    				return proxy;
    			},
    			onSave:function(target,proxy){
    				target.text(proxy.val()).show();
    				proxy.remove();
    			},
    			onView:function(target,proxy){
    				target.show();
    				proxy.validatebox("destroy");
    			}
    		},
    		textarea:{
    			onEdit:function(target){
    				var _01=target.attr("name");
    				var _03=Some.util.text.textarea(target.html());
    				var _02=$("<textarea  class='edit-view-item' ></textarea>").val(_03).insertAfter(target)
		    				.css({
		    					height:target.height(),
		    					width:target.width()
		    				}).attr("id",(_01||"").split(".").join("_"))
		    				  .attr("data-options",target.attr("data-options"))
		    				  .validatebox();
    				var _05=$("<input type='hidden' class='edit-view-item-hide' name='"+_01+"' />").val(_03).insertAfter(_02);
    				_02.change(function(){
    					_05.val(Some.util.text.html(_02.val()));
    				});
    				target.hide();
    			},
    			onSave:function(target,proxy){
    				var _01=proxy.next("input.edit-view-item-hide");
    				target.html(_01.val()).show();
    				proxy.validatebox("destroy");
    				_01.remove();
    			},
    			onView:function(target,proxy){
    				target.show();
    				proxy.next("input.edit-view-item-hide").remove();
    				proxy.remove();
    			}
    		} ,
    		select:{
    			onEdit:function(target){
    				var _09=target.attr("name");
    				var _03=target.attr("list");
    				if(_03){
    					var _01=$("<select class='edit-view-item' onchange='"+(target.attr("onchange")||"")+"' list='"+_03+"'></select>").insertAfter(target).css({
        					height:target.height(),
        					width:target.width()
        				}).attr("name",_09).attr("id",(_09||"").split(".").join("_"));
    				}else{
    					var _01=$("<select class='edit-view-item' onchange='"+(target.attr("onchange")||"")+"'></select>").insertAfter(target).css({
        					height:target.height(),
        					width:target.width()
        				}).attr("name",_09).attr("id",(_09||"").split(".").join("_"));
    				}
    				var _02=target.attr("header");
    				if(_02)
    					_01.append("<option value='"+(target.attr("headerValue")||0)+"'>"+_02+"</option>");
    				if(_03){
    					var _04=eval('('+_03+')'), _05=target.attr("key")||"id",_06=target.attr("value")||"name",_07=target.attr("uid");
    					var _09=target.attr("viewname")||_06;
    					$.each(_04,function(index,item){
    						var _08=$("<option viewname='"+item[_09]+"' value='"+item[_05]+"'>"+item[_06]+"</option>").appendTo(_01);
    						if(item[_05]==_07)
    							_08.attr("selected","selected");
    					});
    				}
    				target.hide();
    			},
    			onSave:function(target,proxy){
    				var _01=proxy.val();
    				target.attr("uid",_01);
    				if(_01==(target.attr("headerValue")||-1)){
    					target.text(target.attr("headerViewName")||"");
    				}
    				else{ 
    					target.text(proxy.find("option:selected").attr("viewName"));
    				}
    				target.show();
    				proxy.remove();
    			},
    			onView:function(target,proxy){
    				target.show();
    				proxy.remove();
    			}
    		},
    		numberbox:{
    			onEdit:function(target){
    				$("<input type='text' class='edit-view-item' value='"+target.text()+"'/>").insertAfter(target)
    				.css({
    					height:target.height(),
    					width:target.width()
    				})
    				.attr("name",target.attr("name")).attr("data-options",target.attr("data-options"))
    				.numberbox(target.attr).change(function(){
    					var self=$(this);
    					if(!self.val()){
    						self.val(0);
    					}
    				});
    				target.hide();
    			},
    			onSave:function(target,proxy){
    				target.text(proxy.numberbox("getValue")).show();
    				proxy.numberbox("destroy");
    			},
    			onView:function(target,proxy){
    				target.show();
    				proxy.numberbox("destroy");
    			}
    		},
    		button:{
    			onEdit:function(target){
    				target.show();
    			},
    			onSave:function(target){
    				target.hide();
    			},
    			onView:function(target){
    				target.hide();
    			}
    		}
      };
      Some.Editor.defaults={
      		render:function(){return null;},//获取节点对象
      		loading:function(self){
      			return Some.loading();
      		},
      		isValid:true,
      		editmsg:"正在编辑......",
      		saveSuccess:function(item){},
      		onChangeStatus:function(status){
      			
      		},
      		form:Some.form/*,
      		item:{
      			url:null,
      			local:false,
      			isEdit:false,
      			localEdit:function(){},
      			localView:function(){},
      			onSubmit:function(param){},
      			success:function(data){},
      			onEdit:function(){},
      			onView:function(){},
      			onSave:function(){},
      			onCancel:function(){},
      			afterEdit:function(){},
      			afterView:function(){},
      			afterSave:function(){},
      			afterCancel:function(){}
      		}*/
      	};
  })(Some||{}, jQuery);
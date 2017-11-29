/**
 * 2013年12月3日 22:49:29
 * 工具类
 */
var Some=Some||{};
(function(Some,$){
	/**
	 * 关于easyUI中的window弹出框销毁方法
	 */
	$.fn.close=function(flag){
		this.closest("div.window-body").window("close",flag);
	};
	
	$.fn.destroy=function(flag){
		this.closest("div.window-body").window("destroy",flag);
	};
	
	$.fn.isChild=function(child){
		return $(this).children().index(child) > -1;
	};
	
	$.fn.isBrother=function(target){
		return $(this).siblings().index(target)>-1;
	};
	
	$.fn.loading=function(){
		
	};
	
	$.fn.getAttrtoJson=function(attrName){
		var attr=this.attr(attrName);
		if(attr && attr.length){
			try{
				return toJson(attr);
			}catch(e){
				return null;
			}
		}
		
		function toJson(str){
			try{
				return eval("("+str+")");
			}catch(e){
				return null;
			}
		}
		
	};
	
	Some.util={
			deepClone:function(source){    
	 		    var newObject={ };  
	 		    for(var key in source)  
	 		    {  
	 		        newObject[key]=(typeof(source[key])==='object')?Some.util.deepClone(source[key]):source[key];  
	 		     }  
	 		    return newObject;  
	 		}, 
			
			removeSpecialCharacter:function(str){
				var pattern= new RegExp("[\n]") ; //创建一个包含\n的正则对象
				var result="";  //定义一个空字符
				for(var i=0;i<str.length;i++){
					result=result+str.substr(i,1).replace(pattern,'');//逐字检索 发现\n就换为空;
				} 
				return result; //返回转换完成的新json字符串
			},
			
			jsonObject:function(str){
				return (new Function("return " + str))();
			},
			
			isEmptyObject: function(obj) { 
				for (var name in obj ) { 
					return false; 
				} 
				return true; 
			},
			
			//将form表单元素的值序列化成对象
			serializeObject:function(form) {
				var o = {};
				$.each(form.serializeArray(), function(index) {
					if (this['value'] != undefined && this['value'].length > 0) {// 如果表单项的值非空，才进行序列化操作
						if (o[this['name']]) {
							o[this['name']] = o[this['name']] + "," + this['value'];
						} else {
							o[this['name']] = this['value'];
						}
					}
				});
				return o;
			},
			
			//接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
			stringToList:function(value) {
				if (value != undefined && value != '') {
					var values = [];
					var t = value.split(',');
					for (var i = 0; i < t.length; i++) {
						values.push('' + t[i]);/* 避免他将ID当成数字 */
					}
					return values;
				} else {
					return [];
				}
			},
			
			newDownLoad:function(json){
				var $ifr=$("#down_frame");
				if(!$ifr.length){
					$ifr=$("<iframe id='down_frame' style='display:none' name='down_iframe'></iframe>").appendTo("body");
				}
				
				var $frm=$("#down_form");
				if(!$frm.length){
					$frm=$("<form id='down_form' action='"+json.url+"' method='post' target='down_iframe' style='display:none'></form>").appendTo("body");
				}
				$frm.form('submit', {    
				    success:function(data){
				    	
				    }     
				}).remove();
				Some.util.haddleDownLoading(json.url);
			},
			
			haddleDownLoading:function(url){
				var strs = url.split("!");
				var key = strs[0]+"_"+strs[1].split(".")[0];
				var downLoading = new Some.loading();
				downLoading.show();
				var interval = setInterval(function(){
			    	$.post("heartBeatAction!validateKey.act",{"methodKey":key},function(text){
			    		handlerResult(text,
				    		function(data){
				    			if(data.data == 0){
				    				downLoading.close();
				    				clearInterval(interval);
				    			}
							},function(data){
								downLoading.close();
								clearInterval(interval);
							}
						);
			    	},"json");
			   	},500); 
			},
			
			newDownLoadJson:function(json){
				var $ifr=$("#down_frame");
				if(!$ifr.length){
					$ifr=$("<iframe id='down_frame' style='display:none' name='down_iframe'></iframe>").appendTo("body");
				}
				var $frm=$("#down_form");
				if(!$frm.length){
					$frm=$("<form id='down_form' action='"+json.url+"' method='post' target='down_iframe' style='display:none'>" +
							"<input type='hidden' name='jsonString' value='"+json.data+"'/>" +
							"</form>").appendTo("body");
				}
				$frm.form('submit', {    
				    success:function(data){
				    	
				    }     
				}).remove(); 
				Some.util.haddleDownLoading(json.url);
			},

			versionCompare:function(v1,v2){
				var f_v1=this.conversionVtF(v1);
				var f_v2=this.conversionVtF(v2);
				if(f_v1.it > f_v2.it){
					return 1;
				}else if(f_v1.it==f_v2.it){
					if(f_v1.flt>f_v2.flt){
						return 1;
					}else if(f_v1.flt==f_v2.flt){
						return 0;
					}else{
						return -1;
					}
				}else{
					return -1;
				}
			},
			
			conversionVtF:function(v){
				var lst_v=v.split(".");
				var f_v=lst_v.shift();
				return {it:parseInt("0"+f_v),flt:lst_v.join(".")};
			},
			
			/**
			 * json数据打包成url请求参数字符串
			 */
			jsonToParam:function(json){
					var _01=[];
					$.each(json,function(key,value){
							_01.push(key+"="+value);
					});
				return _01.join("&");
			},
			
			now:function(){
				return new Date().getTime();
			},
			
			/**
			 * 把js对象转换成字符串
			 */
			jsonToStr:function(json){
				if(window.JSON&&window.JSON.stringify)//支持JSON.stringify
					return  JSON.stringify(json);
				try{
					var $this=this;
					switch (typeof json) {
						case "string":
							return "\""+json+"\"";
						case "number":
							return json;
						case "function":
							return "\""+json.toString+"\"";
						case "object":
							var _01=[];//可接收数组
							if(!json.sort){//非可排序
								for(var i in json){
									var _02=json[i];
									if(_02!=undefined&&_02!=null){//如果键值没有意思则不解析
										_01.push("\""+i+"\":"+$this.toJson(_02));
									}
								}
								return "{"+_01.join(",")+"}";
							}
							else{
								for(var i = 0 ;i<json.length;i++){
									var _02=json[i];
									if(_02!=undefined&&_02!=null){//如果键值没有意思则不解析
									 _01.push($this.toJson(json[i]));
									}
								}
								return "["+_01.join(",")+"]";
							}
						default:
							return json;
					}
				}
				catch (e) {
					if(!json.sort)
						return "{}";
					else
						return "[]";
				}
			},
			
			opera:function(_01,_02){
				/**求未知数运算如 1.6(x-1)支持一元运算**/
				var _03 = /[a-zA-Z]/;
				if (_03.test(_01)) {
					try{
						return parseFloat(eval("(" + _01.replace(_03, _02) + ")"));
					}catch(e){
						return 0.00;
					}
				} 
				return parseFloat(_01+"") * parseFloat(_02+"");
			},
			
			toFixed:function(_01,_02){
				var _03=parseFloat(""+_01);
				var _04=_03.toFixed(_02);
				for(var i=0;i<_02;i++){
					var _05=_03.toFixed(i);
					if(_05-_04==0){
						return _05;
					}
				}
				return _04;
			},
			
			string:{
				format:function(){
			        var args = arguments;
			        var str=args[0];
			        for(var index=0;index < args.length;index++){
			        	str=str.replace(new RegExp("\\{"+index+"\\}","g"), args[index+1]);  
			        }
			        return str;
				},
				left:function(str,num,completion ){
					if(str && typeof str=='string'){
						completion=completion||" ";
						var len=str.length;
						if(len>num){
							return str.substring(len-num, len);
						}else{
							var strs=[];
							for(var i=0;i<num-len;i++){
								strs.push(completion);
							}
							strs.push(str);
							return strs.join("");
						}
					}else{
						return null;
					}
				},
				right:function(str,num,completion ){
					if(str && typeof str=='string'){
						completion=completion||" ";
						var len=str.length;
						if(len>num){
							return str.substring(0, num);
						}else{
							var strs=[str];
							for(var i=0;i<num-len;i++){
								strs.push(completion);
							}
							return strs.join("");
						}
					}else{
						return null;
					}
				},
				sameString:function(source,target){
					var ss=(source||"").split("");
					var ts=(target||"").split("");
					var len=ss.length>ts.length?ss.length:ts.length;
					var as=[];
					for(var index=0;index<len;index++){
						if(ss[index]==ts[index]){
							as.push(ss[index]);
						}else{
							break;
						}
					}
					return as.join("");
				}
				
			},
			
			array:{
				insert:function(_01,_02,_03){
					_01.splice(_02, 0, _03);
				},
				remove:function(_01,_02){
					var _03=_01[_02];
					_01.splice(_02, 1);
					return _03;
				},
				indexOf:function(arr,item){
					if(Array.prototype.indexOf){
						return arr.indexOf(item);
					}
					else{
						for(var i=0;i<arr.length;i++){
							if(arr[i]==item){
								return i;
							}
						}
						return -1;
					}
				},
				up:function(array,index){
					if(index==0){
						return;
					}
					var value=array.splice(index,1);
					array.splice(index-1,0,value[0]);
				},
				down:function(array,index){
					if(array.length-index==1){
						return;
					}
					var value=array.splice(index,1);
					array.splice(index+1,0,value[0]);
				}
			},
			
			ifExist:function(_01,_02){
				return (_01 & _02) > 0;
			},
			
			text:{
				html:function(_01){
					if(_01){
						return _01.replace(/\r|\n|\r\n/igm,"<br>"); 
					}
					return _01;
				},
				textarea:function(_02){
					if(_02){
						return  _02.replace(/<br>|<br\/>/igm,"\r\n"); 
					}
					return _02;
				}
			}
	};
})(Some||{},jQuery);
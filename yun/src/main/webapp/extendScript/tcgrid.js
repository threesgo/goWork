/***
 * 2014年3月20日 10:14:44
 * 沈军军
 * 用于工时卡中的分层显示table
 */
(function(Some,$){
	Some.table=function(args){
		/***
		 * 类实例化别名
		 */
		var self=this;
		
		/***
		 * 对象参数
		 */
		var options=this.options=$.extend({},Some.table.defaults,args);
		/***
		 * 渲染节点
		 */
		var $render=this.render=(options.render?$(options.render):$("<table></table>").appendTo($(options.parent))).addClass("some-tc-table-root")
									.attr(options.tableAttr);
		/***
		 * 初始化标识
		 */
		var isInit=false;
		
		/***
		 * 缓存器
		 */
		var cacheData=null;
		
		/***
		 * 加载器
		 */
		var loading=options.loading.call(self);
		
		/***
		 * 数据节点的深度
		 */
		var deep=2;
		
		/**
		 * 解析数据
		 * @param _01a 数据  array类型
		 * @param _01b 节点  table
		 * @param _01c 样式  table css样式
		 */
		function _01(_01a,_01b){
			var _01d=$("<tbody></tbody>").appendTo(_01b.empty());
			_02(_01a,_01d);
		}
		
		/***
		 * 解析数据并生成html
		 * @param _02a 数据  array类型
		 * @param _0b  节点  table
		 */
		function _02(_02a,_02b){
			_02a=_02a||[];
			$.each(_02a,function(index,item){
					var _02c=$("<tr class='root parent_0' node-id='node_"+item.id+"'  deep='"+(item.deep-1)+"'></tr>").appendTo(_02b);
					item.target=_02c;
					_02c.data("data",item);
					//_07(cacheData,"node_"+item.id,item);
					_02c.append("<th  align='center'  colspan='"+deep+"'>"+options.getAsText(item)+"</th>");
					var _02d=item.children||[];
					if(_02d.length){
						_03(_02d,_02b);
					}
			});
		}
		
		/***
		 * 解析数据并生成html
		 * @param _03a  数据  array类型
		 * @param _03b  节点  table
		 */
		function _03(_03a,_03b){
			$.each(_03a,function(index,item){
				var _03c=$("<tr  class='parent_"+(item.deep-1)+"' node-id='node_"+item.id+"'  deep='"+(item.deep-1)+"'></tr>").appendTo(_03b);
				item.target=_03c;
				_03c.data("data",item);
			//	_07(cacheData,"node_"+item.id,item);
				var _03g=$("<td width='"+options.itemWidth+"' rowspan='"+(item.width?item.width:1)+"'>"+options.getAsText(item)+"</td>").appendTo(_03c);
				var _03d=item.children||[];
				if(_03d.length){
					_03(_03d,_03b);
				}
				else{
					if(options.isLeavf.call(self,item)){
						_03g.attr({
							"colspan":deep-item.deep+1,
							deep:item.deep
						});
						_03c.append("<td width='"+options.itemTextWidth+"' class='some-tc-table-code'>"+options.getAsItemText(item)+"</td>");
					}
					else{
					//	_03g.attr("colspan","2");
					}
				}
			});
		}
		
		/***
		 * table对象初始化
		 * @param _04a 渲染节点对象
		 * @param _04b url
		 * @param _04c 本地数据
		 */
		function _04(_04a,_04b,_04c,handler){
			if(loading){
				loading.show();
			}
			/***
			 * url后台请求模式
			 */
			if(_04b){
				$.getJSON(_04b,function(json){
					_04d(json,handler);
					if(loading){
						loading.close();
					}
				});
			}
			/***
			 * 本地数据模式
			 */
			else{
				_04d(_04c,handler);
				if(loading){
					loading.close();
				}
			}
			function _04d(_04e,handler){
				var _04f=_04e||[];
				options.onload.call(self,_04e);
				cacheData=_04e;
				_06(_04f);
				_01(_04f,_04a);
				options.bind.call(self,_04a);
				isInit=true;
				options.initSuccess.call(self);
				if(handler){
					handler.call(self);
				}
			}
		}
		
		/***
		 * 解析数据
		 * @param _05a 数据对象
		 * @param _05b 初始深度
		 */
		function _05(_05a,_05b){
			_05a.deep=_05b=_05b||0;
			
			deep=deep>_05b?deep:_05b;
			
			var _05c=0,_05d=_05b+1;
			$.each(_05a.children||[],function(index,item){
				item.deep=_05d;
				if(options.isLeavf.call(self,item)){
					deep=deep>_05d?deep:_05d;
					_05c++;
				}
				else{
					_05(item,_05d);
					_05c+=(item.width||0);
				}
			});
			/***
			 * 树形节点的宽度
			 */
			_05a.width=_05c+1;
		}
		
		/***
		 * 处理数据
		 */
		function _06(_06a,_06b){
			_06b=_06b||0;
			var _06c=_06b+1;
			deep=deep>_06b?deep:_06b;
			$.each(_06a,function(index,item){
				item.deep=_06b;
				_05(item,_06c);
			});
		}
		
		/***
		 * 
		 * @param _07a 存储对象
		 * @param _07b 标识符 如对象Id
		 * @param _07c 数据
		 */
		function _07(_07a,_07b,_07c){
			_07a[_07b]=_07c;
		}
		
		
		
		/********************************************对象公有方法*******************************************/
		this.getData=function(){
			return cacheData;
		};
		/***
		 * 加载本地数据
		 */
		this.loadData=function(data,handler){
			_04($render,null,data||[],handler);
		},
		
		/***
		 * 重新加载
		 */
		this.reload=function(url,handler){
			_04($render,url||options.url,options.data,handler);
		};
		
		/***
		 * 初始化
		 */
		this.create=function(){
			_04($render,options.url,options.data);
		};
		
		/***
		 * 根据节点获取数据
		 */
		this.getNode=function(target){
			return $(target).closest("tr").data("data");
		};
		
		/***
		 * 根据节点来获取其父级结构数据
		 */
		this.getParent=function(target){
			var tr=$(target).closest("tr");
			var $parent=tr.prevAll("tr.parent_"+(parseInt(tr.attr("deep"))-1)+":first");
			return $parent.data("data");
		};
		
		/***
		 * 获取全部的顶级节点的数据
		 */
		this.getRoots=function(){
			var roots=[];
			$render.find("tr.root").each(function(index,item){
				roots.push($(item).data("data"));
			});
			return roots;
		};
		
		/**
		 * 根据target节点来获取其所属的顶级节点，如果没有指定，那么就返回顶级节点的首个
		 */
		this.getRoot=function(target){
			var $root=null;
			if(target){
				var tr=$(target).closest("tr");
				$root=tr.prevAll("tr.root:first");
			}
			else{
				$root=$render.find("tbody > tr.root:first");
			}
			return $root.data("data");
		};
		
		/***
		 * 获取target节点的父级点的集合
		 */
		this.getParents=function(target){
			var parents=[];
			function getParent(t){
				var parentNode=self.getParent(t);
				if(parentNode){
					parents.push(parentNode);
					getParent(parentNode.target);
				}
			}
			getParent(target);
			return parents;
		};
		
		/***
		 * 清理
		 */
		this.clean=function(){
			
			isInit=false;
		};
		
		/***
		 * 是否已初始化
		 */
		this.isInited=function(){
			
			return isInit;
			
		};
		
		/***
		 * 选择全部
		 */
		this.selectAll=function(){
			$render.find("tbody >tr").show();
		};
		
		/***
		 * 选择以ID为索引的数据
		 */
		this.select=function(id){
			var target=$render.find("tbody >tr[node-id='node_"+id+"']");
			$render.find("tbody >tr").hide();
			target.show().nextUntil("tr.parent_"+target.attr("deep")).show();
		};
		this.destroy=function(){
			$render.find("tr").removeData("data");
			$render.empty();
		};
		
		this.clear=function(){
			$render.empty();
		};
	};
	/**********默认参数**********/
	Some.table.defaults={
		url:null,
		data:null,
		render:null,
		parent:"body",
		minWidth:"30px",
		itemWidth:"auto",
		itemTextWidth:20,
		initSuccess:function(){
			
		},
		rootClass:"some-tc-table-root",
		childrenClass:"some-tc-table-child",
		onload:function(data){
			
		},
		tableAttr:{
			border:1,
			cellspacing:0,
			cellpadding:0,
			bordercolor:"#A6C9E2"
		},
		loading:function(self){
			
		},
		bind:function(self,render){
			
		},
		isLeavf:function(node){
			return node.isOp;
		},
		getAsText:function(node){
			return "<div style='min-width:50px;height:100%;' class='some-tc-table-text'>"+node.name+"</div>";
		},
		getAsItemText:function(node){
			return "<div style='width:30px'>"+node.code+"</div>";
		}
	};
})(Some||{},jQuery);

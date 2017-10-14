/***
 * easyui扩展
 */

(function(window,$){
	
	
	$.extend($.fn.tree.methods, {
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
		}
		
	});
	
	
	
	/*********easyui treegrid扩展************/
	var methods=$.fn.treegrid.methods;
	
	var gridMethods=$.fn.datagrid.methods;
	
	gridMethods.options=function(jq) {
		var grid_data=$.data(jq[0], "datagrid");
		
		if(!grid_data){
			return null;
		}
		
		var _62f = grid_data.options;
		var _630 =null;
		try{
			_630 = $.data(jq[0], "datagrid").panel.panel("options");
		}catch (e) {
			return _62f;
		}
		var opts = $.extend(_62f, {
			width : _630.width,
			height : _630.height,
			closed : _630.closed,
			collapsed : _630.collapsed,
			minimized : _630.minimized,
			maximized : _630.maximized
		});
		return opts;
	};
	
	
	gridMethods.getClickIndex=function($jq,$clickJq){
		 return $clickJq.closest("tr.datagrid-row").attr("datagrid-row-index");
	};
	
	gridMethods.cancelEditColumn=function($jq,opt){
		var jq=$jq[0];
		var gridData = $.data(jq, "datagrid");
		var opts = gridData.options;
		var tr = opts.finder.getTr(jq, opt.rowIndex);
		var row = opts.finder.getRow(jq, opt.rowIndex);
		var td = tr.find("td[field='"+opt.field+"']");
		td.each(function() {
			var cell = $(this).find("div.datagrid-editable");
			if (cell.length) {
				var ed = $.data(cell[0], "datagrid.editor");
				if (ed.actions.destroy) {
					ed.actions.destroy(ed.target);
				}
				cell.html(ed.oldHtml);
				$.removeData(cell[0], "datagrid.editor");
				cell.removeClass("datagrid-editable");
				cell.css("width", "");
			}
		});
		
	};
	
	gridMethods.beginEditColumn=function($jq,opt){
		var jq=$jq[0];
		var gridData = $.data(jq, "datagrid");
		var opts = gridData.options;
		var tr = opts.finder.getTr(jq, opt.rowIndex);
		var row = opts.finder.getRow(jq, opt.rowIndex);
		var td = tr.find("td[field='"+opt.field+"']");
		
		tr.addClass("datagrid-row-editing");
		if(_14a(jq,tr, opts,td,opt)  == false){
			return;
		}
		
		_e6(jq);
		
		
		td.find("div.datagrid-editable").each(function() {
			var ed = $.data(this, "datagrid.editor");
			ed.actions.setValue(ed.target, row[opt.field]);
		});
		
		_14c(tr);
		
	};
	
	//开始验证处理  
	
	function _14c(tr) {
		var vbox = tr.find(".validatebox-text");
		vbox.validatebox("validate");
		vbox.trigger("mouseleave");
		var _16e = tr.find(".validatebox-invalid");
		return _16e.length == 0;
	}
	
	
	//计算尺寸
	function _e6(_eb) {
		var dc = $.data(_eb, "datagrid").dc;
		dc.view.find("div.datagrid-editable").each(function() {
			var _ec = $(this);
			var _ed = _ec.parent().attr("field");
			var col = $(_eb).datagrid("getColumnOption", _ed);
			_ec._outerWidth(col.boxWidth + col.deltaWidth - 1);
			var ed = $.data(this, "datagrid.editor");
			if (ed.actions.resize) {
				ed.actions.resize(ed.target, _ec.width());
			}
		});
	}
	;
	
	function _14a(jq,tr,opts,td,opt) {
		var opts = $.data(jq, "datagrid").options;
		
		var cell = td.find("div.datagrid-cell");
		if(cell.hasClass("datagrid-editable")){
			return false;
		}
		var _164 = opt.field;
		
		//获取列属性
		var col = _73(jq, _164);
		
		
		if (col && col.editor) {
			var _165, _166;
			if (typeof col.editor == "string") {
				_165 = col.editor;
			} else {
				_165 = col.editor.type;
				_166 = col.editor.options;
			}
			var _167 = opts.editors[_165];
			if (_167) {
				var _168 = cell.html();
				var _169 = cell._outerWidth();
				cell.addClass("datagrid-editable");
				cell._outerWidth(_169);
				cell
						.html("<table border=\"0\" cellspacing=\"0\" cellpadding=\"1\"><tr><td></td></tr></table>");
				cell.children("table").bind(
						"click dblclick contextmenu",
						function(e) {
							e.stopPropagation();
						});
				$.data(cell[0], "datagrid.editor", {
					actions : _167,
					target : _167.init(cell.find("td"),
							_166),
					field : _164,
					type : _165,
					oldHtml : _168
				});
			}
		}
	}
	;
	
	function _73(_ee, _ef) {
		function _f0(_f1) {
			if (_f1) {
				for (var i = 0; i < _f1.length; i++) {
					var cc = _f1[i];
					for (var j = 0; j < cc.length; j++) {
						var c = cc[j];
						if (c.field == _ef) {
							return c;
						}
					}
				}
			}
			return null;
		}
		;
		var _f2 = $.data(_ee, "datagrid").options;
		var col = _f0(_f2.columns);
		if (!col) {
			col = _f0(_f2.frozenColumns);
		}
		return col;
	}
	
	
	
	gridMethods.getView=function(jq){
		return $(jq).parent(".datagrid-view");
	};
	
	gridMethods.getToolbar=function(jq){
		return gridMethods.getView(jq).prev(".datagrid-toolbar");
	};
	
	gridMethods.getPanel = function(jq) {
		var grid_data=$.data(jq[0], "datagrid");
		
		if(!grid_data){
			return null;
		}
		
		
		return grid_data.panel;
	};
	gridMethods.getPager =function(jq) {
		var panel=gridMethods.getPanel(jq);
		if(!panel){
			return null;
		}
		return panel
				.children("div.datagrid-pager");
	};
	
	gridMethods.editCell=function(jq,param){
         return jq.each(function(){
//             var opts = $(this).datagrid('options');
             var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
             for(var i=0; i<fields.length; i++){
                 var col = $(this).datagrid('getColumnOption', fields[i]);
                 col.editor1 = col.editor;
                 if (fields[i] != param.field){
                     col.editor = null;
                 }
             }
             $(this).datagrid('beginEdit', param.index||param.id);
             for(var i=0; i<fields.length; i++){
                 var col = $(this).datagrid('getColumnOption', fields[i]);
                 col.editor = col.editor1;
             }
         });
	};
	
	gridMethods.editFilterCell=function(jq,param){
		  return jq.each(function(){
//             var opts = $(this).datagrid('options');
             var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
             for(var i=0; i<fields.length; i++){
                 var col = $(this).datagrid('getColumnOption', fields[i]);
                 col.editor1 = col.editor;
                 if(col.editor  && param.filter.call(this,fields[i])==false){
                	 col.editor = null;
                 }
             }
             $(this).datagrid('beginEdit', param.index||param.id);
             for(var i=0; i<fields.length; i++){
                 var col = $(this).datagrid('getColumnOption', fields[i]);
                 col.editor = col.editor1;
             }
         });
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 根据id获取自己顶级节点，如果本身是顶级节点就直接返回本身
	 */
	methods.getTopRoot=function(jq, _01){
		function _02(_03){
			if(!_03)return null;
			var _05=methods.getParent(jq,_03.id);
			if(!_05)return _03;
			return _02(_05);
		}; 
		return _02(methods.find(jq,_01));
	};
	/**
	 * 判断id节点是否为顶级节点
	 */
	methods.isRoot=function(jq,_01){
			if(methods.getParent(jq,_01))
				return false;
			return true;
	};
	/**
	 * 判断查找父级节点知道满足条件
	 * @param _01{id:11,fn:function(parent){return a==b;}}
	 */
	methods.getParentUntil=function(jq,_01){
			var _02=methods.find(jq,_01.id);
			if(!_02) 
				return null;
			if(_01.fn(_02)==true)
				return _02;
			else{
				var _03=methods.getParent(jq,_02.id);
				if(!_03)
					return null;
				return 	methods.getParentUntil(jq,{id:_03.id,fn:_01.fn});
			}
	};
	/**
	 * 节点_01所在的节点的索引值
	 */
	methods.index=function(jq,_01){
		var _02=methods.getTarget(jq, _01);
		return _02.parent().find(">tr.datagrid-row").index(_02);
	};
	/**
	 * 获取root的总数
	 */
	methods.rootCount=function(jq){
		return methods.getBody(jq).find(" >table.datagrid-btable >tbody >tr.datagrid-row").length;
	};
	/**
	 * 自定义append方法
	 * 主要是解决treegrid中的append方法 每次调用此方法时就会宠幸新建table.datagrid-btable节点与原先节点不在一个父级节点下
	 */
	methods.myAppend=function(jq,_01){
		var _parent = null;
		var state = null;
		if(_01.parent){
			_parent = methods.find(jq,_01.parent);
			if(_parent!=null){
				state = _parent.state;
			}else{
				state='closed';
			}
		}
	
		methods.append(jq,_01);
		var _02=null;
		var _parent = null;
		if(_parent){
			_02=methods.getChildrenTarget(jq,_01.parent).find("> td > div > table.datagrid-btable");
		}
		else{
			_02=methods.getBody(jq).find(">table.datagrid-btable");
		}
			
		if(_02.length>1){ 
			var _03=_02.last();
			_02.first().find(">tbody").append(_03.find(">tbody>tr"));
			_03.remove();
		}
		if(state == 'closed'){
			methods.expand(jq,_01.parent);
		}
	};
	methods.myUpdate=function(jq,args){
//		 recursive
		var removeIds=[];
		if(args.recursive==true){
			var row=args.row;
			var source=methods.find(jq,row.id);
			if(source){
				methods.update(jq,args);
				if(source.children){
					
					
					
					$.each(source.children,function(index,item){
						removeIds.push(item.id);
						methods.remove(jq,item.id);
					});
				}
				
				
				
			}
		}else{
			methods.update(jq,args);
		}
	};
	
	/**
	 * 根据node-id获取所在的jquery节点对象
	 */
	methods.getTarget=function(jq,_01){
		return methods.getBody(jq).find("tr.datagrid-row[node-id='"+_01+"']");
	};
	
	
	methods.getFiled=function(jq,_01){
		return methods.getTarget(jq,_01.id).find(">td[field='"+_01.name+"']");
	};
	
	methods.myUncheckRow=function(jq,_01){
		uncheck(jq,_01);
	};
	
	function uncheck(jq,row){
		if(row.children && row.children.length){
			$.each(row.children,function(index,item){
				uncheck(jq,item);
			});
		}
		gridMethods.uncheckRow(jq,row.id);
	}
	
	
	
	
	/**
	 * 根据node-id获取所属的子级jquery节点对象
	 */
	methods.getChildrenTarget=function(jq,_01){
		return methods.getTarget(jq,_01).next("tr.treegrid-tr-tree");
	};
	
	methods.isCollapse=function(jq,_01){
		return methods.getTarget(jq, _01).find("span.tree-hit").hasClass("tree-collapsed");
	};
	
	
	
	/**
	 * 根据插件节点来获取数据顶级body节点
	 */
	methods.getBody=function(jq){
		return jq.siblings("div.datagrid-view2").find("div.datagrid-body");
	};
	methods.getRow=function(jq,tr){
		return methods.find(jq,$(tr).attr("node-id"));
	};
	
	methods.getUpNode=function(jq,_07){
		var _01=_07.id,_08=_07.fn;
		var _02=methods;
		var _03=_02.find(jq,_01);
		if(!_03)return;
		var _04=methods.getBody(jq).find("tr.datagrid-row[node-id='"+_03.id+"']");
		if(!_04.length)return;
		var _09=_04.parent().find(">tr.datagrid-row").index(_04);
		var _12=_04.prev("tr.treegrid-tr-tree");
		var _05=null;
		if(_12.length)
			_05=_12.prev("tr.datagrid-row");
		else
			_05=_04.prev("tr.datagrid-row");
		if(!_05.length) return;
		return _02.find(jq,_05.attr("node-id"));
	}

	
	methods.getDownNode=function(jq,_09){
		var _01=_09.id,_10=_09.fn;
		var _02=methods;
		var _03=_02.find(jq,_01);
		if(!_03)return;
		var _04=methods.getBody(jq).find("tr.datagrid-row[node-id='"+_03.id+"']");
		if(!_04.length)return;
		var _11=_04.parent().find(">tr.datagrid-row").index(_04);
		var _06=_04.next("tr.treegrid-tr-tree");
		var _05=null;
		if(_06.length)
			 _05=_06.next("tr.datagrid-row");
		else
			_05=_04.next("tr.datagrid-row");
		if(!_05.length)return;
		return _02.find(jq,_05.attr("node-id"));
	}
	
	/**
	 * 根据id来实现节点上移(是利用id获取顶级节点为整体)
	 */
	methods.up=function(jq,_07){
		var _01=_07.id,_08=_07.fn;
		var _02=methods;
		var _03=_02.find(jq,_01);
		if(!_03)return;
		var _04=methods.getBody(jq).find("tr.datagrid-row[node-id='"+_03.id+"']");
		if(!_04.length)return;
		var _09=_04.parent().find(">tr.datagrid-row").index(_04);
		var _12=_04.prev("tr.treegrid-tr-tree");
		var _05=null;
		if(_12.length)
			_05=_12.prev("tr.datagrid-row");
		else
			_05=_04.prev("tr.datagrid-row");
		if(!_05.length)return;
		var _06=_04.next("tr.treegrid-tr-tree");
		_04.insertBefore(_05);
		_06.insertBefore(_05);
		var _10=[];
		if(_02.isRoot(jq, _01))
			_10=_02.getData(jq);
		else
			_10=_02.getParent(jq,_01).children;
		var _11=_10[_09];
		_10[_09]=_10[_09-1];
		_10[_09-1]=_11;
		if(_08)_08(_02.find(jq,_01),_02.find(jq,_05.attr("node-id")));
	};
	
	
	/***
	 * 根据id来实现节点下移(是利用id获取顶级节点为整体)
	 */
	methods.down=function(jq,_09){
		var _01=_09.id,_10=_09.fn;
		var _02=methods;
		var _03=_02.find(jq,_01);
		if(!_03)return;
		var _04=methods.getBody(jq).find("tr.datagrid-row[node-id='"+_03.id+"']");
		if(!_04.length)return;
		var _11=_04.parent().find(">tr.datagrid-row").index(_04);
		var _06=_04.next("tr.treegrid-tr-tree");
		var _05=null;
		if(_06.length)
			 _05=_06.next("tr.datagrid-row");
		else
			_05=_04.next("tr.datagrid-row");
		if(!_05.length)return;
		var _07=_05.next("tr.treegrid-tr-tree");
		var _08=_07.length?_07:_05;
		_04.insertAfter(_08);
		_06.insertAfter(_04);
		var _12=[];
		if(_02.isRoot(jq, _01))
			_12=_02.getData(jq);
		else
			_12=_02.getParent(jq,_01).children;
		var _13=_12[_11];
		_12[_11]=_12[_11+1];
		_12[_11+1]=_13;
		if(_10)_10(_02.find(jq,_01),_02.find(jq,_05.attr("node-id")));
	};
	
	methods.getNext=function(jq,id){
		var tr=methods.getBody(jq).find("tr.datagrid-row[node-id='"+id+"']");
		var $next=tr.nextAll("tr.datagrid-row:first");
		if($next.length){
			return methods.getRow(jq,$next);
		}else{
			return null;
		}
	};
	methods.getPrev=function(jq,id){
		var tr=methods.getBody(jq).find("tr.datagrid-row[node-id='"+id+"']");
		var $prev=tr.prevAll("tr.datagrid-row:first");
		if($prev.length){
			return methods.getRow(jq,$prev);
			
		}else{
			return null;
		}
	};
	
	methods.hasNext=function(jq,id){
		var tr=methods.getBody(jq).find("tr.datagrid-row[node-id='"+id+"']");
		var $next=tr.nextAll("tr.datagrid-row:first");
		if($next.length){
			return true;
		}else{
			return false;
		}
	};
	methods.hasPrev=function(jq,id){
		var tr=methods.getBody(jq).find("tr.datagrid-row[node-id='"+id+"']");
		var $prev=tr.prevAll("tr.datagrid-row:first");
		if($prev.length){
			return true;
		}else{
			return false;
		}
	};
	
	
	
	methods.moreUp=function(jq,args){
		var rows=args.ids;
		if(rows && rows.length){
			var prev=null;
			var rowIndex=getIndex(rows);
			$.each(rows,function(index,item){
				prev=methods.getPrev(jq, item);
				if(prev && !rowIndex["row-"+prev.id]){
					methods.up(jq,{id:item,fn:args.fn});
				}
			});
			if(args.lastHandler){
				args.lastHandler.call(this);
			}
		}
	};
	
	methods.moreDown=function(jq,args){
		var rows=args.ids;
		if(rows && rows.length){
			var next=null;
			var rowIndex=getIndex(rows);
			$.each(rows.reverse(),function(index,item){
				next=methods.getNext(jq, item);
				if(next && !rowIndex["row-"+next.id]){
					methods.down(jq,{id:item,fn:args.fn});
				}
			});
			if(args.lastHandler){
				args.lastHandler.call(this);
			}
		}
	};
	
	function getIndex(rows){
		var rowIndex={};
		$.each(rows,function(index,item){
			rowIndex["row-"+item]=item;
		});
		return rowIndex;
	}
	
	methods.getMygetSelections=function(jq,handler){
		var selections=methods.getSelections(jq);
		var rows=[],rowIndex={};
		$.each(selections,function(index,row){
			if(handler){
				row=handler(row);
			}
			if(!rowIndex["row-"+row.id]){
				rowIndex["row-"+row.id]=row.id;
				rows.push(row);
			}
		});
		return rows;
	};
	methods.MygetChecked=function(jq,handler){
		var selections=gridMethods.getChecked(jq);
		var rows=[],rowIndex={};
		$.each(selections,function(index,row){
			if(methods.find(jq,row.id)){
				if(handler){
					row=handler(row);
				}
				if(!rowIndex["row-"+row.id]){
					rowIndex["row-"+row.id]=row.id;
					rows.push(row);
				}
			}
		});
		return rows;
		
	};
//	getMygetSelections
	
	
	/***
	 *获取所有编辑节点的数据
	 */
	methods.getEditData=function(jq){
		var _02=[];
		methods.getBody(jq).find("tr.datagrid-row-editing")
		  .each(function(index,item){
			  _02.push(methods.find(jq,$(item).attr("node-id")));
		  });
		  return _02;
	};
	
	/**
	 * 
	 * 获取选中行 
	 */
	methods.selectEditData=function(jq){
		var $body=methods.getBody(jq);
		$body.find("tr").removeClass("datagrid-row-selected");
		methods.getBody(jq).find("tr.datagrid-row-editing").addClass("datagrid-row-selected");
	};
	
	gridMethods.selectEditData=function(jq){
		var $body=methods.getBody(jq);
		$body.find("tr").removeClass("datagrid-row-selected");
		methods.getBody(jq).find("tr.datagrid-row-editing").addClass("datagrid-row-selected");
	};
	
	
	/**
	 * 
	 * 选中当前行,编辑完成之后跳转到下一行
	 */
	methods.selectNext=function(jq){
		var $select=methods.getBody(jq).find("tr.datagrid-row-selected");
		if($select.length){
			var $next=$select.next("tr.datagrid-row");
			if(!$next.length){
				$next=$select.next("tr.treegrid-tr-tree").next("tr.datagrid-row");
			}
			if($next.length){
				$select.removeClass("datagrid-row-selected");
				$next.addClass("datagrid-row-selected");
			}
		}
	};
	/**
	 * 判断表格是否存在编辑
	 */
	methods.hasEdit=function(jq){
		return methods.getBody(jq).find("tr.datagrid-row-editing").length>0;
	};
	
	gridMethods.hasEdit=function(jq){
		return methods.getBody(jq).find("tr.datagrid-row-editing").length>0;
	};
	
	/**
	 * 根据id来判断是否处于编辑状态
	 */
	methods.isEdit=function(jq,_01){
		var _02=methods.getBody(jq).find("tr.datagrid-row[node-id='"+_01+"']");
		return _02.hasClass("datagrid-row-editing") ;
	};
	
	gridMethods.isEdit=function(jq,_01){
		var _02=methods.getBody(jq).find("tr.datagrid-row[node-id='"+_01+"']");
		return _02.hasClass("datagrid-row-editing") ;
	};
	
	/*********easyui numberbox扩展************/
	var boxMethods=$.fn.numberbox.methods;
	boxMethods.mySetValue=function(jq,_01){
		return jq.each(function(){
			var opts = $.data(this, "numberbox").options;
			var fn = opts.onChange;
			opts.onChange = function() {};
			boxMethods.setValue($(this),_01);
			opts.onChange = fn;
		});
		
	};
	
	/*************easyui validatebox扩展*********************/
	
	/**
	 * 系统非法字符集
	 */
	$.fn.validatebox.illegals=['#','$','~','^','script','\\','\''];
	
	$.fn.validatebox.short_illegals=['~','^','script','\\','\''];
	
	$.extend($.fn.validatebox.defaults.rules, {
		numberAndLetters: {
			//^\w+$
	        validator: function(value,param){
	            return /^[\w-]+$/.test(value);
	        },
	        message: '输入格式必须为英文和数字.'
	    },
		
		illegal:{
			validator: function(value,param){
				param=param||$.fn.validatebox.illegals;
				if(value){
					for(var index =0 ; index<param.length;index++){
						if(value.indexOf(param[index])>=0){
							return false;
						}
					}
				}
				return true;
			},
			message: '输入内容出现非法字符.'
		},
		
		short_illegals:{
			validator: function(value,param){
				param=param||$.fn.validatebox.short_illegals;
				if(value){
					for(var index =0 ; index<param.length;index++){
						if(value.indexOf(param[index])>=0){
							return false;
						}
					}
				}
				return true;
			},
			message: '输入内容出现非法字符.'
		},
		
		equals: {
			validator: function(value,param){
				return value == $(param[0]).val();
			},
			message: '字段不匹配.'
		},
		le: {
	        validator: function(value,param){
	        	var val=$(param[0]).val();
	        	if(val){
	        		return value <= val;
	        	}
	        },
	        message: 'Field do not match.'
	    },
	    ge: {
	        validator: function(value,param){
	        	var val=$(param[0]).val();
	        	if(val){
	        		return value >= val;
	        	}
	        },
	        message: 'Field do not match.'
	    },
	    version:{
	    	validator: function(value){
				return /^[1-9]*(\d+\.{0,1})*(\d+)$/.test(value);
			},
			message:"请输入合法的版本号,如:10.234.3.45.567"
	    },
		phone: {
			validator: function(value){
				return /^(((1[0-9]{1}[0-9]{1}))+\d{8})$/.test(value);
			},
			message: '手机号码格式不正确.'
		},
		between:{
			 validator: function(value, param){
		            return value >= param[0] && value <= param[1];
		     },
	        message: '数字应在 {0}与{1}之间'
		},
		less:{
			 validator: function(value, param){
		            return value >= param[0] ;
		     },
	        message: '数字应大于{0}'
		},
		greater:{
			 validator: function(value, param){
		            return value <= param[0] ;
		     },
	        message: '数字应小于{0}'
			
		},
	    min: {
	        validator: function(value, param){
	            return value.length >= param[0];
	        },
	        message: '输入内容长度不能低于 {0}.'
	    },
	    max: {
	        validator: function(value, param){
	            return value.length <= param[0];
	        },
	        message: '输入内容长度不能超过 {0}.'
	    },
	    length : {
			validator : function(_40a, _40b) {
				var len = $.trim(_40a).length;
				return len >= _40b[0] && len <= _40b[1];
			},
			message : " 输入内容长度必须介于{0}和{1}个字符之间"
		}
	});
	var editors=$.fn.datagrid.defaults.editors;
	$.extend(editors, {
		select:{
			init: function(container, options){
				var $select=$('<select class="datagrid-editable-select" ></select>');
				
				if(options.header){
					$select.append("<option value=''>"+options.header+"</option>");
				}
				
				$select.appendTo(container);
				$select.data("options",options);
				return $select;
			},
			destroy: function(target){
				var self=$(target);
				self.removeData("options");
				self.remove();
			},
			getValue: function(target){
				var self=$(target);
				return self.val();
			},
			setValue: function(target, value){
				var self=$(target);
				var options=self.data("options");
				var rows=options.data;
				if(typeof options.data=="function"){
					rows=options.data();
				}
				
				if(rows && rows.length){
					$.each(rows,function(index,item){
						self.append("<option value='"+item[options.valueField]+"'  "
								+(value==item[options.valueField]?"selected=selected":"")+"   >"+item[options.textField]+"</option>");
					});
				}
			},
			resize: function(target, width){
				$(target)._outerWidth(width);
			}
		}
	});
	$.extend($.fn.datagrid.defaults,{
		editorTagets:[],
		editorTypes:null,
		editorNode:null
	});
	
	$.extend(gridMethods,{
		endMyEdit : function(jq, _648) {
			return jq.each(function() {
				_595(this, _648, false);
			});
		},
		cancelMyEdit : function(jq, _649) {
			return jq.each(function() {
				_595(this, _649, true);
			});
		},
		beginMyEdit:function(jq, _647) {
			var _01= jq.each(function() {
				_58f(this, _647);
			});
			gridMethods.fixRowHeight(jq,_647);
			return _01;
		},
		initEditorCache:function(self,size){
			setTimeout(_initEditorCache(self,size), 0);
		}
	});
	
	function _initEditorCache(self,size){
		if(!size){
			size=3;
		}
		
		var opts= $.data(self[0], "datagrid").options;
		var array=opts.editorTagets;
		var target=opts.editorNode;
		if(!target){
			target=$("<div></div>").hide().appendTo("body");
			opts.editorNode=target;
		}
		var ed=opts.editorTypes;
		if(!ed){
			return;
		}
		var _editors=opts.editors;
		var editor_target=null;
 		for(var i=0;i<size;i++){
 			editor_target={};
	 		$.each(ed,function(index,item){
	 			var span=$("<div class='edit_div'></div>").appendTo(target)
	 			.bind("click dblclick contextmenu",function(e) {
					e.stopPropagation();
			    });
	 			var _5ab, _5ac;
				if (typeof item== "string") {
					_5ab = item;
				} else {
					_5ab = item.type;
					_5ac = item.options;
				}
				var _5ad =_editors[_5ab];
				if (_5ad) {
					editor_target[index]={
						node:span,
						target:	_5ad.init(span,_5ac),
						type:_5ab,
						field:index
					};
				}
	 		});
			array.push(editor_target);
 		}
 		return function(){
 		};
 	}
	 
	 
	function _595(jq, id, flag) {
		var opts = $.data(jq, "datagrid").options;
		var _599 = $.data(jq, "datagrid").updatedRows;
		var _59a = $.data(jq, "datagrid").insertedRows;
		var tr = opts.finder.getTr(jq, id);
		var row = opts.finder.getRow(jq, id);
		if (!tr.hasClass("datagrid-row-editing")) {
			return;
		}
		if (!flag) {
			if (!_594(tr, id)) {
				return;
			}
			var _59b = false;
			var _59c = {};
			tr.find("div.datagrid-editable").each(function() {
				var _59d = $(this).parent().attr("field");
				var ed = $.data(this, "datagrid.editor");
				var _59e = ed.actions.getValue(ed.target);
				if (row[_59d] != _59e) {
					row[_59d] = _59e;
					_59b = true;
					_59c[_59d] = _59e;
				}
			});
			if (_59b) {
				if (_4aa(_59a, row) == -1) {
					if (_4aa(_599, row) == -1) {
						_599.push(row);
					}
				}
			}
		}
		tr.removeClass("datagrid-row-editing");
		_59f(jq, id);
		$(jq).datagrid("refreshRow", id);
		if (!flag) {
			opts.onAfterEdit.call(jq, id, row, _59c);
		} else {
			opts.onCancelEdit.call(jq, id, row);
		}
	}
	
	function _4aa(a, o) {
		for ( var i = 0, len = a.length; i < len; i++) {
			if (a[i] == o) {
				return i;
			}
		}
		return -1;
	}
	
	function _59f(jq, id) {
		var opts = $.data(jq, "datagrid").options;
		var tr = opts.finder.getTr(jq, id);
		var array=opts.editorTagets;
		var editNode={};
		
		tr.children("td").each(function() {
			var cell = $(this).find("div.datagrid-editable");
			if (cell.length) {
				var ed = $.data(cell[0], "datagrid.editor");
				
				editNode[ed.field]={
					node:ed.node,
					target:ed.target,
					type:ed.type
				};
				opts.editorNode.append(ed.node);
				cell.html(ed.oldHtml);
				$.removeData(cell[0], "datagrid.editor");
				cell.removeClass("datagrid-editable");
				cell.css("width", "");
			}
		});
		array.push(editNode);
	}
	 
	function _58f(self, index) {
		var opts = $.data(self, "datagrid").options;
		var tr = opts.finder.getTr(self, index);
		var row = opts.finder.getRow(self, index);
		if (tr.hasClass("datagrid-row-editing")) {
			return;
		}
		if (opts.onBeforeEdit.call(self, index, row) == false) {
			return;
		}
		
		
		tr.addClass("datagrid-row-editing");
		
		_592(self, tr);
		_537(self);
		tr.find("div.datagrid-editable").each(function() {
			var _593 = $(this).parent().attr("field");
			var ed = $.data(this, "datagrid.editor");
			ed.actions.setValue(ed.target, row[_593]);
		});
		_594(tr);
		
	}
	
	
	
	
	function _537(_538) {
		var dc = $.data(_538, "datagrid").dc;
		dc.view.find("div.datagrid-editable").each(function() {
			var cell = $(this);
			var _539 = cell.parent().attr("field");
			var col = $(_538).datagrid("getColumnOption", _539);
			cell._outerWidth(col.width);
			var ed = $.data(this, "datagrid.editor");
			if (ed.actions.resize) {
				ed.actions.resize(ed.target, cell.width());
			}
		});
	}
	
	
	function _594(tr) {
		if (!tr.hasClass("datagrid-row-editing")) {
			return true;
		}
		var vbox = tr.find(".validatebox-text");
		vbox.validatebox("validate");
		vbox.trigger("mouseleave");
		var _5b4 = tr.find(".validatebox-invalid");
		return _5b4.length == 0;
	}
	
	function _592(_5a8, tr) {
		var opts = $.data(_5a8, "datagrid").options;
//		var tr = opts.finder.getTr(_5a8, _5a9);
		var editor=opts.editorTagets.pop();
		if(editor==null){
			return;
		}
		tr
				.children("td")
				.each(
						function() {
							var _5aa = $(this).attr("field");
							var edit_node=editor[_5aa];
							if(edit_node){
								var cell = $(this).find("div.datagrid-cell");

								var _5ae = cell.html();
								var _5af = cell._outerWidth();
								cell.addClass("datagrid-editable");
								cell._outerWidth(_5af);
								cell.empty();
								cell.append(edit_node.node);
								
								$.data(cell[0], "datagrid.editor", {
									actions : opts.editors[edit_node.type],
									target : edit_node.target,
									field : _5aa,
									node:edit_node.node,
									type :edit_node.type,
									oldHtml : _5ae
								});
								
							}
						});
	}
	function _4ff(_53a, _53b) {
		function find(_53c) {
			if (_53c) {
				for ( var i = 0; i < _53c.length; i++) {
					var cc = _53c[i];
					for ( var j = 0; j < cc.length; j++) {
						var c = cc[j];
						if (c.field == _53b) {
							return c;
						}
					}
				}
			}
			return null;
		}
		;
		var opts = $.data(_53a, "datagrid").options;
		var col = find(opts.columns);
		if (!col) {
			col = find(opts.frozenColumns);
		}
		return col;
	}
	
	//主要是修改树形节点单选可以自主控制
	$.fn.treegrid.defaults.view.renderRow=function(_790, _791, _792, _793, row) {
		var opts = $.data(_790, "treegrid").options;
		var cc = [];
		if (_792 && opts.rownumbers) {
			cc
					.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">0</div></td>");
		}
		for ( var i = 0; i < _791.length; i++) {
			var _794 = _791[i];
			var col = $(_790).datagrid("getColumnOption",
					_794);
			if (col) {
				var css = col.styler ? (col.styler(
						row[_794], row) || "") : "";
				var _795 = "";
				var _796 = "";
				if (typeof css == "string") {
					_796 = css;
				} else {
					if (cc) {
						_795 = css["class"] || "";
						_796 = css["style"] || "";
					}
				}
				var cls = _795 ? "class=\"" + _795 + "\""
						: "";
				var _797 = col.hidden ? "style=\"display:none;"
						+ _796 + "\""
						: (_796 ? "style=\"" + _796 + "\""
								: "");
				cc.push("<td field=\"" + _794 + "\" " + cls
						+ " " + _797 + ">");
				if (col.checkbox) {
					var _797 = "";
				} else {
					var _797 = _796;
					if (col.align) {
						_797 += ";text-align:" + col.align
								+ ";";
					}
					if (!opts.nowrap) {
						_797 += ";white-space:normal;height:auto;";
					} else {
						if (opts.autoRowHeight) {
							_797 += ";height:auto;";
						}
					}
				}
				cc.push("<div style=\"" + _797 + "\" ");
				if (col.checkbox) {
					cc.push("class=\"datagrid-cell-check ");
				} else {
					cc.push("class=\"datagrid-cell "
							+ col.cellClass);
				}
				cc.push("\">");
				if (col.checkbox ) {
					
					if(row.checkStatus!=false){
						if (row.checked) {
							cc
							.push("<input type=\"checkbox\" checked=\"checked\"");
						} else {
							cc.push("<input type=\"checkbox\"");
						}
						cc
						.push(" name=\""
								+ _794
								+ "\" value=\""
								+ (row[_794] != undefined ? row[_794]
								: "") + "\"/>");
					}
				} else {
					var val = null;
					if (col.formatter) {
						val = col.formatter(row[_794], row);
					} else {
						val = row[_794];
					}
					if (_794 == opts.treeField) {
						for ( var j = 0; j < _793; j++) {
							cc
									.push("<span class=\"tree-indent\"></span>");
						}
						if (row.state == "closed") {
							cc
									.push("<span class=\"tree-hit tree-collapsed\"></span>");
							cc
									.push("<span class=\"tree-icon tree-folder "
											+ (row.iconCls ? row.iconCls
													: "")
											+ "\"></span>");
						} else {
							if (row.children
									&& row.children.length) {
								cc
										.push("<span class=\"tree-hit tree-expanded\"></span>");
								cc
										.push("<span class=\"tree-icon tree-folder tree-folder-open "
												+ (row.iconCls ? row.iconCls
														: "")
												+ "\"></span>");
							} else {
								cc
										.push("<span class=\"tree-indent\"></span>");
								cc
										.push("<span class=\"tree-icon tree-file "
												+ (row.iconCls ? row.iconCls
														: "")
												+ "\"></span>");
							}
						}
						cc
								.push("<span class=\"tree-title\">"
										+ val + "</span>");
					} else {
						cc.push(val);
					}
				}
				cc.push("</div>");
				cc.push("</td>");
			}
		}
		return cc.join("");
	};
	
	//行号自适应方法
	$.extend($.fn.datagrid.methods, {
	    fixRownumber : function (jq) {
	        return jq.each(function () {
	            var panel = $(this).datagrid("getPanel");
	            //获取最后一行的number容器,并拷贝一份
	            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
	            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
	            clone.css({
	                "position" : "absolute",
	                 left : -1000
	            }).appendTo("body");
	            var width = clone.width("auto").width();
	            //默认宽度是25,所以只有大于25的时候才进行fix
	            if (width > 25) {
	                //多加5个像素,保持一点边距
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
	                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
	                $(this).datagrid("resize");
	                //一些清理工作
	                clone.remove();
	                clone = null;
	            } else {
	                //还原成默认状态
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
	                $(this).datagrid("resize");
	            }
	        });
	    }
	});
	
	
	/*
	$.extend($.fn.datagrid.methods, {  
        updateColumn: function(datagrid,data) {  
            datagrid.each(function(){  
                //获取缓存中的配置数据  
                var gridObj=$.data(this,"datagrid");  
                var opts=gridObj.options;  
                //获取行数据  
                var row=opts.finder.getRow(this,data.index);  
                data.row=data.row||{};  
                var update=false;  
                //判断是否需要更新  
                for(var updateColumn in data.row){  
                    if(data.row[updateColumn]!==row[updateColumn]){  
                        update=true;  
                        break;  
                    }  
                }  
                if(update){  
                    var tr=opts.finder.getTr(this,data.index);  
                    var view=opts.view.renderRow.call(opts.view,this,["attr1"],true,data.index,data.row);  
                    if(tr.hasClass("datagrid-row-editing")){  
                        //找到所有需要更新值的列  
                        tr.find('div').each(function(i){  
                            if(data.row[$(this).parent().attr('field')]!=undefined){  
                                if($(this).attr('class').indexOf('datagrid-editable')!=-1){  
                                    var ed=$.data(this,"datagrid.editor");  
                                    if(ed!=undefined){  
                                        ed.actions.setValue(ed.target,data.row[$(this).parent().attr('field')]);  
                                    }else{  
                                        $(this).html(data.row[$(this).attr('field')]);  
                                    }                                             
                                }else{  
                                    $(this).html(data.row[$(this).attr('field')]);  
                                    <span style="background-color: rgb(255, 0, 0);">$(this).addClass("datagrid-editable");</span>  
                                }  
                            }  
                        });  
                    }  
                }  
            });  
        }  
    });  
	*/
	
	
	/**
	 * @author 孙宇
	 * 
	 * @requires jQuery,EasyUI
	 * 
	 * 防止panel/window/dialog组件超出浏览器边界
	 * @param left
	 * @param top
	 */
	var easyuiPanelOnMove = function(left, top) {
		var l = left;
		var t = top;
		if (l < 1) {
			l = 1;
		}
		if (t < 1) {
			t = 1;
		}
		var width = parseInt($(this).parent().css('width')) + 14;
		var height = parseInt($(this).parent().css('height')) + 14;
		var right = l + width;
		var buttom = t + height;
		var browserWidth = $(window).width();
		var browserHeight = $(window).height();
		if (right > browserWidth) {
			l = browserWidth - width;
		}
		if (buttom > browserHeight) {
			t = browserHeight - height;
		}
		$(this).parent().css({/* 修正面板位置 */
			left : l,
			top : t
		});
	};
	$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
	$.fn.window.defaults.onMove = easyuiPanelOnMove;
	$.fn.panel.defaults.onMove = easyuiPanelOnMove;
})(window,jQuery);
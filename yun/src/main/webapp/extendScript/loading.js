/****
 * @param loading
 * 加载插件
 * 2013年11月30日 19:30:10
 * 沈军军
 * @return
 */
(function(Some,$){
	Some.loading=function(args){
			var options=this.options=$.extend({},Some.loading.defaults,args);
			var _05=options.parent?$(options.parent):$("body");
			var _06=[];
			this.show=function(){
				this.close();
				var _03= $("<div class='some-loading '>"+options.text+"</div>").appendTo(_05);
				_03.css({
						 "left":_03.position().left-(_03.width()/2),
						 "top":_03.position().top-(_03.height()/2),
						 "background":"#fff url('"+options.imgUrl+"') no-repeat scroll 5px 10px"
					});
				_06.push(_03);
				if(options.modal){
					var _04= $("<div class='some-loading-modal'></div>").appendTo(_05);
					_04.css({
						"backgroundColor":options.modalBackgroundColor,
						"filter":"alpha(opacity="+(options.filter*100)+")",
						"opacity":options.filter
					});
					_06.push(_04);
				}
			};
			this.close=function(){
				$.each(_06,function(index,item){
					item.remove();
				});
				_06=[];
			};
		};
	Some.loading.defaults={
				parent:"body",
				text:$.fn.datagrid.defaults.loadMsg,
				imgUrl:"style/images/loading.gif",
				modal:true,
				modalBackgroundColor:"#fff",
				filter:"0.4"
		};
})(Some||{},jQuery);
var imgageView=(function(window,$){
	return function(file,img,div){
	//判断浏览器是否有FileReader接口
	if(typeof FileReader =='undefined')
	{
		//如果浏览器是ie
		if($.browser.msie===true)
		{
			//ie6直接用file input的value值本地预览
			if($.browser.version==6)
			{
				$("#imgUpload").change(function(event){                        
					//ie6下怎么做图片格式判断?
					$(img).attr(event.target.value);
					//var src = document.selection.createRange().text;        //选中后 selection对象就产生了 这个对象只适合ie
				});
			}
			//ie7,8使用滤镜本地预览
			else if($.browser.version==7 || $.browser.version==8)
			{
				$(file).change(function(event){
					$(event.target).select();
					var src = document.selection.createRange().text;
					//使用滤镜 成功率高
					$(img).hide();
					$(div)[0].filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src= src;
					//使用和ie6相同的方式 设置src为绝对路径的方式 有些图片无法显示 效果没有使用滤镜好
					/*var img = '<img src="'+src+'" width="200px" height="200px" />';
                       $("#destination").empty().append(img);*/
				});
			}
		}
		//如果是不支持FileReader接口的低版本firefox 可以用getAsDataURL接口
		else if($.browser.mozilla===true)
		{
			$(file).change(function(event){
				//firefox2.0没有event.target.files这个属性 就像ie6那样使用value值 但是firefox2.0不支持绝对路径嵌入图片 放弃firefox2.0
				//firefox3.0开始具备event.target.files这个属性 并且开始支持getAsDataURL()这个接口 一直到firefox7.0结束 不过以后都可以用HTML5的FileReader接口了
				if(event.target.files)
				{
					//console.log(event.target.files);
					for(var i=0;i<event.target.files.length;i++)
						$(img).attr("src",event.target.files.item(i).getAsDataURL());
				}
			});
		}
	}
	else
	{
		//多图上传 input file控件里指定multiple属性 e.target是dom类型
		$(file).change(function(e){  
			for(var i=0;i<e.target.files.length;i++)
			{
				var _file = e.target.files.item(i);
				if(!_file) continue;
				//允许文件MIME类型 也可以在input标签中指定accept属性
				//console.log(/^image\/.*$/i.test(file.type));
				if(!(/^image\/.*$/i.test(_file.type)))
					continue;            //不是图片 就跳出这一次循环
				//实例化FileReader API
				var freader = new FileReader();
				freader.readAsDataURL(_file);
				freader.onload=function(e)
				{
					$(img).attr("src",e.target.result);
				};
			}
		});
		
		//处理图片拖拽的代码
		$(div)[0].addEventListener('dragover',function(event){
			event.stopPropagation();
			event.preventDefault();
		},false);
		
		$(div)[0].addEventListener('drop',function(event){
			event.stopPropagation();
			event.preventDefault();
			var img_file = event.dataTransfer.files.item(0);                //获取拖拽过来的文件信息 暂时取一个
			if(!img_file) return false;
			if(!(/^image\/.*$/.test(img_file.type)))
				return false;
			fReader = new FileReader();
			fReader.readAsDataURL(img_file);
			fReader.onload = function(event){
				$(img).attr("src",event.target.result);
			};
		},false);
	}
	};
})(window,jQuery);
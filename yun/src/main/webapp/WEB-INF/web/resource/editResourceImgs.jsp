<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<link href="${pageContext.request.contextPath}/scriptPlugins/fileupload/css/iconfont.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/scriptPlugins/fileupload/css/fileUpload.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scriptPlugins/fileupload/js/fileUpload.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/scriptPlugins/fileupload/js/iconfont.js"></script> 

<div>
    <div id="fileUploadContent" class="fileUploadContent"></div>
    
    <button onclick="testUpload()">提交</button>
</div>


<script type="text/javascript">
    $("#fileUploadContent").initUpload({
        "uploadUrl":"resourceAction!updateResourceImgs.act?sysResource.id=${sysResource.id}",//上传文件信息地址
        //"size":350,//文件大小限制，单位kb,默认不限制
        //"maxFileNumber":3,//文件个数限制，为整数
        "filelSavePath":"",//文件上传地址，后台设置的根目录
        "beforeUpload":beforeUploadFun,//在上传前执行的函数
        "onUpload":onUploadFun,//在上传后执行的函数
        //autoCommit:true,//文件是否自动上传
        "fileType":['png','jpg']//文件类型限制，默认不限制，注意写的是文件后缀
    });
    
    function beforeUploadFun(opt){
    }
    
    function onUploadFun(opt,data){
        alert(data);
        uploadTools.uploadError(opt);//显示上传错误
        uploadTools.uploadSuccess(opt);//显示上传成功
    }
    
    function testUpload(){
    	var opt = uploadTools.getOpt("fileUploadContent");
    	alert(Some.util.jsonToStr(opt));
    	uploadEvent.uploadFileEvent(opt);
    }
</script>


package com.yunwang.util.action;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.yunwang.model.pojo.SysUser;
import com.yunwang.util.Constant;

@ParentPackage("MethodInterceptor")
public abstract class AbstractUpDownAction extends BaseAction{
	
	/*
	 * @date 2016-9-22
	 * @author YBF
	 * TODO
	 */
	private static final long serialVersionUID = -1757190991704920437L;

	protected SysUser sessionAdm=null;
    
    protected String message;
    
    protected String importResourceFileName;
    protected String importResourceContentType;
    protected File importResource;
	protected InputStream exportResourceStream; // 输入流的名字
	protected String exportResourceFileName; // 输出文件的名字
    
    protected ActionTransfer actionTransfer = new ActionTransfer ( ) ;

    public void setSession(Map<String,Object> sessionMap){
        super.setSession(sessionMap);
        sessionAdm=sessionGet(Constant.SESSION_ADMIN);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 例如编辑，新建等操作用页面跳转，成功操作后所return 页面
	 * 
	 * @param msg
	 * @return
	 */
	public String pageSuccess ( String msg ) {
		actionTransfer.setMessage ( msg ) ;
		return "success_msg" ;
	}


	/**
	 * 与pageSuccess相反
	 * 
	 * @param msg
	 * @return
	 */
	public String pageError ( String msg ) {
		actionTransfer.setMessage ( msg ) ;
		return "err_msg" ;
	}

	public ActionTransfer getActionTransfer() {
		return actionTransfer;
	}

	public void setActionTransfer(ActionTransfer actionTransfer) {
		this.actionTransfer = actionTransfer;
	}

	public String getImportResourceFileName() {
		return importResourceFileName;
	}

	public void setImportResourceFileName(String importResourceFileName) {
		this.importResourceFileName = importResourceFileName;
	}

	public String getImportResourceContentType() {
		return importResourceContentType;
	}

	public void setImportResourceContentType(String importResourceContentType) {
		this.importResourceContentType = importResourceContentType;
	}

	public File getImportResource() {
		return importResource;
	}

	public void setImportResource(File importResource) {
		this.importResource = importResource;
	}

	public InputStream getExportResourceStream() {
		return exportResourceStream;
	}

	public void setExportResourceStream(InputStream exportResourceStream) {
		this.exportResourceStream = exportResourceStream;
	}

	public String getExportResourceFileName() {
		return exportResourceFileName;
	}

	public void setExportResourceFileName(String exportResourceFileName) {
		this.exportResourceFileName = exportResourceFileName;
	}
}

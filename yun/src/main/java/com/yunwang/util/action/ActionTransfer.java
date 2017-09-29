package com.yunwang.util.action ;

/**
 * 
 * @author Administrator 用于Action传输用的类
 */
public class ActionTransfer {

	private String message ;// 页面显示的提示信息

	private String returnUrl ;// 请求路径

	private String dialogId ;// 用于页面的弹出框ID


	public String getMessage () {


		return message ;
	}


	public void setMessage ( String message ) {


		this.message = message ;
	}


	public String getReturnUrl () {


		return returnUrl ;
	}


	public void setReturnUrl ( String returnUrl ) {


		this.returnUrl = returnUrl ;
	}


	public String getDialogId () {


		return dialogId ;
	}


	public void setDialogId ( String dialogId ) {


		this.dialogId = dialogId ;
	}


	public ActionTransfer ( String message , String returnUrl , String dialogId ) {


		super ( ) ;
		this.message = message ;
		this.returnUrl = returnUrl ;
		this.dialogId = dialogId ;
	}


	public ActionTransfer () {


		super ( ) ;
	}
}

package com.yunwang.util.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.yunwang.model.pojo.SysUser;
import com.yunwang.util.Constant;

@ParentPackage("LoginInterceptor")
public abstract class AbstractLoginAction extends BaseAction{
	
	/*
	 * @date 2016-9-22
	 * @author YBF
	 * TODO
	 */
	private static final long serialVersionUID = -1757190991704920437L;

	protected SysUser sessionAdm=null;
    
    protected String message;
    
    
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

}

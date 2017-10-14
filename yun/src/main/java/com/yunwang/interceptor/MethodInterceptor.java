package com.yunwang.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.yunwang.util.annotation.DownloadAnnotation;

public class MethodInterceptor extends MethodFilterInterceptor{

	/*
	 * @date 2017-3-23
	 * @author YBF
	 * 拦截重复下载
	 */
	private static final long serialVersionUID = 1L;

	private static final String PRE_FIX = "download_";
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		ActionProxy actionProxy = invocation.getProxy();
		Method method = action.getClass().getMethod(actionProxy.getMethod());
		String methodValue = getMethodValue(method);
		if(methodValue == null){
			return invocation.invoke();
		}
		if(!getDownloadLock(ServletActionContext.getRequest().getSession(),methodValue)){
			//没能占有下载锁  要加提示
			System.out.println("没能占有下载锁  要加提示");
			ServletActionContext.getResponse().setHeader("Content-Type","text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print("{message:'正在下载中,请勿重复操作'}");
			return null;
		}
		try{
			return invocation.invoke();
		}finally{
			releaseDownloadLock(ServletActionContext.getRequest().getSession(),methodValue);
		}
		
//		ctx.getSession()
//		UsrSmUser adm = (UsrSmUser) ctx.getSession().get(SessionConstant.SESSION_ADMIN);
//		System.out.println(adm.getUserName());
//		System.out.println(method.getName());
//		return null;
	}

	/**
	 * @Description: 释放锁
	 * @param @param session
	 * @param @param methodValue   
	 * @return void  
	 * @throws
	 * @author KXL
	 * @date 2017-8-15
	 */
	private void releaseDownloadLock(HttpSession session,String methodValue) {
		synchronized (session) {
			String key = PRE_FIX + methodValue;
			session.removeAttribute(key);
		}
	}

	/**
	 * @Description: 获取拦截方法
	 * @param @param method
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author KXL
	 * @date 2017-8-15
	 */
	private String getMethodValue(Method method) {
		DownloadAnnotation download = method.getAnnotation(DownloadAnnotation.class);
		if(download != null){
			return download.value();
		}
		return null;
	}

	/**
	 * @param methodValue 拦截的方法名
	 * @param session 
	 * @Description: 占有锁
	 * @param    
	 * @return void  
	 * @throws
	 * @author KXL
	 * @date 2017-8-15
	 */
	private boolean getDownloadLock(HttpSession session, String methodValue) {
		synchronized (session) {
			String key = PRE_FIX + methodValue;
			Boolean lock = (Boolean) session.getAttribute(key);
			if(lock == null || !lock){
				session.setAttribute(key, true);
				return true;
			}
			return false;
		}
	}
}

package com.yunwang.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yunwang.model.pojo.SysUser;
import com.yunwang.util.Constant;

/*
 * @date 2016-9-22
 * @author YBF
 * TODO 用户登录拦截
 */
public class LoginInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		SysUser adm = (SysUser) ctx.getSession().get(Constant.SESSION_ADMIN);
		if (adm != null) {
			return invocation.invoke();
		} else {
			HttpServletRequest request = ServletActionContext.getRequest();
			StringBuffer sb = new StringBuffer();
			@SuppressWarnings("unchecked")
			Enumeration<String> postString = request.getParameterNames();
			boolean flag = false;
			while (postString.hasMoreElements()) {
				String a = postString.nextElement();
				if (flag)
					sb.append("&");
				sb.append(a).append("=").append(request.getParameter(a));
				flag = true;
			}
			String path = request.getRequestURI().substring( request.getContextPath().length());
			if (sb.length() > 0) {
				request.setAttribute("returnUrl", path + "?" + sb);
			} else {
				request.setAttribute("returnUrl", path);
			}
			return "login";
		}
	}
}

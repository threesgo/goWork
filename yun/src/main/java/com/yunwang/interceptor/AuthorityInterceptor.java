package com.yunwang.interceptor;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yunwang.dao.SysMenuDaoI;
import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysUser;
import com.yunwang.util.Constant;

/*
 * @date 2016-9-22
 * @author YBF
 * TODO 用户登录拦截
 */
public class AuthorityInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	SysMenuDaoI sysMenuDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Object action = invocation.getAction();
		SysUser adm = (SysUser) ctx.getSession().get(Constant.SESSION_ADMIN);
		List<SysMenu> listMenu = (List<SysMenu>)ctx.getSession().get("defaultMenu");
		if(null != listMenu && listMenu.size()>0){
			for(SysMenu Menu:listMenu){
				if(action.getClass().getSimpleName().equals(Menu.getUrl().split("!")[0])){
					
					//if(Menu.getUrl().split("!")[1].equals(anObject))
				}
			}
		}
		
		// System.out.println(action.getClass().getSimpleName());
		Method[] methods = action.getClass().getMethods();
		for(Method method:methods){
			String name = method.getName();
			//System.out.println(name);
		}
		if (adm != null) {
			System.out.println(adm.getId());
			
			return invocation.invoke();
			
		} else {
			return null;
		}
	}
}

package com.yunwang.init;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yunwang.service.InitService;

public class InitServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public void init(){
		InitService initService = WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean(InitService.class);
		//初始化系统数据
		initService.initData();
	}
}

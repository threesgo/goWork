package com.yunwang.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yunwang.util.file.FileSupport;
import com.yunwang.util.string.MyStringUtil;

/**
 * @author 				shenjunjun
 * @creatTime			2017年5月27日 下午9:43:02
 * @description			静态数据获取	
 */
public class StaticResourceServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4233132458910499049L;

	private static final Logger LOGGER = Logger.getLogger(StaticResourceServlet.class);

	private static final String PREFIX = "/static/";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		String uri = MyStringUtil.substringAfter(req.getRequestURI(), PREFIX);
		if(MyStringUtil.isBlank(uri)){
			return ;
		}
		try {
			response.getOutputStream().write(FileSupport.read(uri));
		} catch (Exception e) {
			LOGGER.error("===获取文件出错,文件地址为:=="+req.getRequestURI());
		}
	}
}

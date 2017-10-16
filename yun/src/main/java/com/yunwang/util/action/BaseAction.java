package com.yunwang.util.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yunwang.util.exception.MineException;

@Namespace("/")
public abstract class BaseAction extends ActionSupport implements SessionAware{
	
	/*
	 * @date 2016-9-22
	 * @author YBF
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Log LOG =LogFactory.getLog(BaseAction.class);
	
	
	protected Map<String,Object> sessionMap=null;
	
	/**
	 * 当前页数.
	 */
	protected int page;
	
	/**
	 * 每页显示记录数.
	 */
	protected int rows;
	
	
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T sessionGet(String key) {
        return (T)sessionMap.get(key);
    }
	
	/**
	  * Administrator
	  * 2015-4-15
	  * @return
	  * <p>获取HttpServletRequest</p>
	*/
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	/**
	  * Administrator
	  * 2015-4-15
	  * @return
	  * <p>获取HttpServletResponse</p>
	*/
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	/**
	  * Administrator
	  * 2015-4-15
	  * @return
	  * <p>获取ServletContext</p>
	*/
	protected ServletContext getServletContext(){
		return ServletActionContext.getServletContext();
	}
	
	/**
	  * Administrator
	  * 2015-4-15
	  * @return
	  * <p>获取ActionContext</p>
	*/
	protected ActionContext getActionContext(){
		return ActionContext.getContext();
	}
	
    /** 
     * ajax() method
     * @author 方宜斌
     * @date 2014-4-2 上午11:30:24
     * <p>返回数据</p> 
     * @param content
     * @param type
     * @return String  
    */ 
	protected String ajax(Object content, String type) {
		HttpServletResponse response=null;
       	try {
           	response=getResponse();
           	response.setContentType(type + ";charset=UTF-8");
           	response.setHeader("Pragma", "No-cache");
           	response.setHeader("Cache-Control", "no-cache");
           	response.setDateHeader("Expires", 0);
           	response.getWriter().print(content);
       	} catch (IOException e) {
           	LOG.error(e.getMessage());
       	}
       	finally{
       		try {
       			response.getWriter().flush();
       			response.getWriter().close();
       		} catch (Exception e) {
       			LOG.error(e.getMessage());
       		}
   		}
       	return null;
	}
   
	protected void ajax(InputStream stream) {
		HttpServletResponse response=null;
       	try {
           	response=getResponse();
           	response.setContentType("text/html;charset=UTF-8");
           	response.setHeader("Pragma", "No-cache");
           	response.setHeader("Cache-Control", "no-cache");
           	response.setDateHeader("Expires", 0);
           	IOUtils.copy(stream, response.getWriter());
       	} catch (IOException e) {
           	LOG.error(e.getMessage());
       	}
       	finally{
       		try {
       			if(stream!=null){
       				stream.close();
       			}
       			response.getWriter().flush();
       			response.getWriter().close();
       		} catch (Exception e) {
       			LOG.error(e.getMessage());
       		}
   		}
	}
	
	/** 
     * ajaxText() method
     * @author 方宜斌
     * @date 2014-4-2 上午11:30:34
     * <p>AJAX输出文本，返回null </p> 
     * @param text
   */ 
   protected String ajaxText(Object text) {
       return ajax(text, "text/html");
   }

   /** 
     * ajaxHtml() method
     * @author 方宜斌
     * @date 2014-4-2 上午11:31:40
     * <p>AJAX输出HTML，返回null </p> 
     * @param html
     * @return String  
   */ 
   protected String ajaxHtml(Object html) {
       return ajax(html, "text/html");
   }

   /** 
     * ajaxXml() method
     * @author 方宜斌
     * @date 2014-4-2 上午11:31:49
     * <p> AJAX输出XML，返回null</p> 
     * @param xml
     * @return String  
   */ 
   protected String ajaxXml(Object xml) {
       return ajax(xml, "text/xml");
   }
	
    /**
 	 * Administrator
 	 * 2015-4-15
 	 * @param list
 	 * @return
 	 * <p>集合转换json数组</p>
 	 */
    protected String ajaxJSONArr(Collection<?> list) {
    	return ajax(JSONArray.fromObject(list),"text/html");
    }
  
    protected String success(String message,Object value){
    	return ajaxJSONObj(requestStatus("success",message,value));
    }
    protected String success(String message){
    	return success(message,null);
    }
    
    protected String error(String message){
    	return ajaxJSONObj(requestStatus("error",message,null));
    }
    
    protected String error(String message,Exception e){
    	if(e instanceof MineException){
    		return ajaxJSONObj(requestStatus("error",e.getMessage(),null));
    	}else{
    		return ajaxJSONObj(requestStatus("error",message,null));
    	}
    }
    
    private JSONObject requestStatus(String status, String message, Object value) {
        JSONObject result=new JSONObject();
        result.put("status", status);
        result.put("message", message);
        result.put("data", value);
        return result;
    }
    
    /**
 	 * Administrator
 	 * 2015-4-15
 	 * @param jsonArray
 	 * @return
 	 * <p>直接返回json数组</p>
 	 */
    protected String ajaxJSONArr(JSONArray jsonArray) {
       	return ajax(jsonArray,"text/html");
    }
  
    /**
 	 * Administrator
 	 * 2015-4-15
 	 * @param obj
 	 * @return
 	 * <p>对象转换为json返回</p>
 	 */
    protected String ajaxJSONObj(Object obj) {
    	if(obj instanceof JSONObject){
    		return ajax(obj,"text/html");
    	}
    	return ajax(JSONObject.fromObject(obj),"text/html");
    }
  
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}

package com.yunwang.util.action ;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

/**
 * @author YBF
 * @date 2016-12-12
 * <p>页面定时刷新，无其他作用</p>
 */
@Action(value="heartBeatAction")
public class HeartBeatAction extends BaseAction{
	
	private String methodKey;
	
    private static final long serialVersionUID = 1L;
    
	public String execute () {
		return null ;
	}
	
	public String validateKey(){
		try{
			if(null!=ServletActionContext.getRequest().getSession().getAttribute("download_"+methodKey)){
				return success(getText("add_success"),1);
			}else{
				return success(getText("add_success"),0);
			}
		}catch(Exception e){
			return error(getText("add_failed"));
		}
	}

	public String getMethodKey() {
		return methodKey;
	}

	public void setMethodKey(String methodKey) {
		this.methodKey = methodKey;
	}
}

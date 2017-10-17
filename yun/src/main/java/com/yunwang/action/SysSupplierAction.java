package com.yunwang.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.service.SysSupplierService;
import com.yunwang.util.action.AbstractLoginAction;
@Action(
	value = "sysSupplierAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/sysSupplier/index.jsp")
	}
)
public class SysSupplierAction extends AbstractLoginAction{

	/*
	 * @date 2017-10-17
	 * @author YBF
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysSupplierService sysSupplierService;
	
	@Override
	public String execute(){
		return "index";
	} 
	
	
	public String listData(){
		return null;
	}
}

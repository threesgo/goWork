package com.yunwang.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.service.SysSupplierService;
import com.yunwang.util.action.AbstractLoginAction;

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
	public String execute() throws Exception {
		return "index";
	} 

}

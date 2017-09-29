package com.yunwang.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.util.action.AbstractLoginAction;

@Action(
	value = "resourceTypeAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resourceType/index.jsp")
	}
)
public class ResourceTypeAction extends AbstractLoginAction{

	/*
	 * @date 2017-9-27
	 * @author YBF
	 * TODO  系统资源类型管理
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysResourceTypeService sysResourceTypeService;
	
	private SysRsRcCatalog sysRsRcCatalog;
	
	@Override
	public String execute() throws Exception {
		return "index";
	} 
	
	public String findTree(){
		return null;
	}
	
	public String save(){
		try{
			sysResourceTypeService.saveRsRcCatalog(sysRsRcCatalog);
			return success("操作成功!");
		}catch(Exception e){
			return error("操作失败!");
		}
	}
	
	public String delete(){
		try{
			sysResourceTypeService.deleteRsRcCatalog(sysRsRcCatalog);
			return success("操作成功!");
		}catch(Exception e){
			return error("操作失败!");
		}
	}
	
	public String update(){
		try{
			return success("操作成功!");
		}catch(Exception e){
			return error("操作失败!");
		}
	}

	public SysRsRcCatalog getSysRsRcCatalog() {
		return sysRsRcCatalog;
	}

	public void setSysRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		this.sysRsRcCatalog = sysRsRcCatalog;
	}
}

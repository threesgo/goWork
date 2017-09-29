package com.yunwang.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.service.SysUserService;
import com.yunwang.util.action.AbstractLoginAction;

@Action(value = "sysUserAction")
public class SysUserAction extends AbstractLoginAction{

	private final static Logger LOG =Logger.getLogger(SysUserAction.class);
	/*
	 * @date 2017-9-27
	 * @author YBF
	 * TODO  //系统用户管理
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysUserService sysUserService;
	
	private Integer roleId;
	
	
	/**
	 * @date 2017-9-28
	 * @author YBF
	 * @return
	 * <p></p>
	 */
	public String updateUserRoleDefault(){
		try{
			success("更新默认角色成功!");
			sysUserService.updateUserRoleDefault(sessionAdm.getId(),roleId);
			return success("更新默认角色成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("更新默认角色失败!");
		}
	}


	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}

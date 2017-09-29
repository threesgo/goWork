package com.yunwang.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysUser;
import com.yunwang.service.SysUserService;
import com.yunwang.util.Constant;
import com.yunwang.util.action.BaseAction;
import com.yunwang.util.string.MyStringUtil;

@Action(
	value = "loginAction", 
	results = {
		@Result(name = "success", location = "mainAction.act", type="redirect"),
		@Result(name = "next", location = "${returnUrl}", type="redirect")
	}
)
@ParentPackage("base")
public class LoginAction extends BaseAction{
	
	private static final long serialVersionUID = 4953763528021024034L;

	@Autowired
	private SysUserService userService;
	
	private String returnUrl;
	
	private SysUser sysUser;
	
	private String vcode;

	@Override
	public String execute() throws Exception {
		return null;
	}
	
	public String login(){
		try {
			if(MyStringUtil.isBlank(sysUser.getUserName())){
					addActionMessage("用户名不能为空!");
					return LOGIN;
			}
			if(MyStringUtil.isBlank(sysUser.getPassWord())){
					addActionMessage("用户名密码不能为空!");
					return LOGIN;
			}
			
//			Object realVcode = sessionMap.get("vcode");
//			if (null  == realVcode|| !realVcode.equals(vcode)) {
//					addActionMessage("验证码输入错误");
//					return LOGIN;
//	      	} 
//			sessionMap.remove("vcode");
			
			SysUser adm = userService.login(sysUser);
			if(adm==null){
				addActionMessage("用户名或者密码不正确!");
				return LOGIN;
			}
			sessionMap.put(Constant.SESSION_ADMIN,adm);
			if (StringUtils.isNotBlank(returnUrl)) {
				return "next";
			}else{
				return SUCCESS;
			}
		} catch (Exception e) {
			addActionMessage("系统发生错误!");
			return LOGIN;
		}
	}
	
	public String logout() {
		sessionMap.remove(Constant.SESSION_ADMIN);
		return LOGIN;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
}

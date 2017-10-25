package com.yunwang.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysUser;

public interface SysUserDaoI extends BaseDaoI<SysUser>{
	
	/**
	 * @date 2017-9-29
	 * @author YBF
	 * @param userName 用户名
	 * @return
	 * <p>根据用户名查询</p>
	 */
	SysUser getByUserName(String userName);
	
	Pager<SysUser> findAllUser(JSONObject json, int page,int rows);
	
	List<SysUser> findBySysUserName(String userName);
	
	List<SysUser> findBySysUserNameExceptUserId(String userName,Integer userId);

}

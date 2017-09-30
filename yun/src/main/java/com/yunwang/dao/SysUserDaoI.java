package com.yunwang.dao;

import com.alibaba.fastjson.JSONObject;
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
	
	Pager<SysUser> findBySysUserId(JSONObject json, int page,int rows);

}

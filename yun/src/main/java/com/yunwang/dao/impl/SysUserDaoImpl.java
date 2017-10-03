package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysUserDaoI;
import com.yunwang.model.pojo.SysUser;

@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDaoI{
	
	@Override
	public SysUser getByUserName(String userName) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userName",userName);
		return get("SELECT model FROM SysUser model WHERE model.userName=:userName",map);
	}
}

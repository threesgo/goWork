package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysRoleDaoI;
import com.yunwang.model.pojo.SysRole;

@Repository
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDaoI{

	@Override
	public List<SysRole> findByUserId(Integer userId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId",userId);
		return find("SELECT model FROM SysRole model,SysUserRole userRole " 
				+"WHERE model.id = userRole.roleId AND userRole.userId=:userId ",map);
	}

	@Override
	public SysRole getDefaultRoleByUserId(Integer userId) {
		String hql="SELECT model FROM SysRole model,SysUserRole userRole " 
				+" WHERE model.id = userRole.roleId AND userRole.userId =:userId AND userRole.isDefault=1 ";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userId",userId);
		return get(hql,map);
	}
}

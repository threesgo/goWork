package com.yunwang.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysRoleMenuDaoI;
import com.yunwang.model.pojo.SysRoleMenu;

@Repository
public class SysRoleMenuDaoImpl extends BaseDaoImpl<SysRoleMenu> implements SysRoleMenuDaoI{

	@Override
	public List<SysRoleMenu> findByRoleId(Integer roleId) {
		String hql ="SELECT model FROM SysRoleMenu model WHERE model.roleId=? ";
		return find(hql, roleId);
	}

}

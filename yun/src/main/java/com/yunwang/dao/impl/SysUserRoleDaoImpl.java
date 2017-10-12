package com.yunwang.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysUserRoleDaoI;
import com.yunwang.model.pojo.SysUserRole;

@Repository
public class SysUserRoleDaoImpl extends BaseDaoImpl<SysUserRole> implements SysUserRoleDaoI{

	@Override
	public List<SysUserRole> findUserAndRole() {
		String hql = "SELECT new SysUserRole(model.id,model.userId,model.roleId,sysUser.userName,sysRole.name) "
				   + "FROM SysUserRole model,SysUser sysUser,SysRole sysRole "
				   + "WHERE model.userId=sysUser.id AND model.roleId=sysRole.id ";
		return find(hql);
	}
	
}

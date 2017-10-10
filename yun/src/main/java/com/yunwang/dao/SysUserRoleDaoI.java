package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysUserRole;

public interface SysUserRoleDaoI extends BaseDaoI<SysUserRole>{

	public List<SysUserRole> findUserAndRole();
}

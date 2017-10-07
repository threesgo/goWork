package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysRole;

public interface SysRoleDaoI extends BaseDaoI<SysRole>{

	/**
	 * @date 2017-9-29
	 * @author YBF
	 * @param userId  用户id
	 * @return
	 * <p>根据用户id查询关联角色</p>
	 */
	List<SysRole> findByUserId(Integer userId);

	/**
	 * @date 2017-9-29
	 * @author YBF
	 * @param userId 用户id
	 * @return
	 * <p>查询用户关联的默认角色</p>
	 */
	SysRole getDefaultRoleByUserId(Integer userId);
	
	List<SysRole> findAllRole();
}

/**
 * 
 */
package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysRoleMenu;

/**
 * @author Administrator
 * @date2017-10-17
 * <p>SysRoleMenuDaoI</p>
 * @param
 * @return
 */
public interface SysRoleMenuDaoI extends BaseDaoI<SysRoleMenu>{

	List<SysRoleMenu> findByRoleId(Integer roleId);
}

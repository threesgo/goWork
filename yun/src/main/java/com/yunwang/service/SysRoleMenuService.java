/**
 * 
 */
package com.yunwang.service;

import java.util.List;

import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysRoleMenu;

/**
 * @author Administrator
 * @date2017-10-17
 * <p>SysRoleMenuService</p>
 * @param
 * @return
 */
public interface SysRoleMenuService {
	
	public List<SysRoleMenu> findAll();
	
	void saveRoleRelMenu(SysRole sysRole,String menus);
}

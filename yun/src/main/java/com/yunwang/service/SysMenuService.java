/**
 * 
 */
package com.yunwang.service;

import java.util.List;
import com.yunwang.model.pojo.SysMenu;

/**
 * @author Administrator
 * @date2017-10-17
 * <p>SysMenuService</p>
 * @param
 * @return
 */
public interface SysMenuService {
	
	public List<SysMenu> findAll();
	
	public List<Object> findMenuByRoleId(Integer roleId);
	
	public List<SysMenu> findRelMenuByRoleIdAndViewType(Integer roleId,Integer viewType);
		
}

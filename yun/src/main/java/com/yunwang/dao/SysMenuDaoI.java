package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysMenu;

public interface SysMenuDaoI extends BaseDaoI<SysMenu>{

	List<SysMenu> findMenuByRoleId(Integer roleId);
	
	List<SysMenu> findMenuByRoleIdAndViewType(Integer roleId,Integer viewType);
}

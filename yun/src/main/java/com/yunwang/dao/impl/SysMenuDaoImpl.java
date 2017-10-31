package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysMenuDaoI;
import com.yunwang.model.pojo.SysMenu;

@Repository
public class SysMenuDaoImpl extends BaseDaoImpl<SysMenu> implements SysMenuDaoI{

	@Override
	public List<SysMenu> findMenuByRoleId(Integer roleId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("roleId",roleId);
		return find("SELECT model FROM SysMenu model,SysRoleMenu roleMenu " 
				+"WHERE model.id = roleMenu.menuId AND roleMenu.roleId=:roleId "
				+"ORDER BY model.id",map);
	}
}

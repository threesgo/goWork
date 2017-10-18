/**
 * 
 */
package com.yunwang.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysRoleMenuDaoI;
import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysRoleMenu;
import com.yunwang.service.SysRoleMenuService;

/**
 * @author Administrator
 * @date2017-10-17
 * <p>SysRoleMenuSwrviceImpl</p>
 * @param
 * @return
 */

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService{

	@Autowired
	private SysRoleMenuDaoI sysRoleMenuDao;
	/**
	 * @Description: 查询所有角色和菜单关系
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-10-17
	 */
	@Override
	public List<SysRoleMenu> findAll() {
		return sysRoleMenuDao.findAll();
	}
	/**
	 * @Description:保存角色和菜单的关系
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-10-18
	 */
	@Override
	public void saveRoleRelMenu(SysRole sysRole, String menus) {
		Integer roleId = sysRole.getId();
		//删除角色对应模块关系信息
		sysRoleMenuDao.deleteByProperty("roleId", roleId);
		
		if(StringUtils.isNotBlank(menus)){
			String[] menuIdArr = menus.split(",");
			if(menuIdArr!=null && menuIdArr.length>0){
				List<SysRoleMenu> roleMenuList = new ArrayList<SysRoleMenu>();
				for(String menuId : menuIdArr){
					SysRoleMenu sysRoleMenu = new SysRoleMenu();
					sysRoleMenu.setRoleId(roleId);
					sysRoleMenu.setMenuId(Integer.parseInt(menuId));
					roleMenuList.add(sysRoleMenu);
				}
				//保存角色对应模块信息
				sysRoleMenuDao.saveOrUpdateAll(roleMenuList);
			}
		}
		
	}

}

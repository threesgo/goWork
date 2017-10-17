/**
 * 
 */
package com.yunwang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysRoleMenuDaoI;
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

}

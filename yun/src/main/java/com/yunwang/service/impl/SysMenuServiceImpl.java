package com.yunwang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysMenuDaoI;
import com.yunwang.model.pojo.SysMenu;
import com.yunwang.service.SysMenuService;


@Service
public class SysMenuServiceImpl implements SysMenuService{
	
	@Autowired
	private SysMenuDaoI sysMenuDao;

	/**
	 * @Description: 查询所有菜单
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-10-17
	 */
	@Override
	public List<SysMenu> findAll() {
		return sysMenuDao.findAll();
	}

	

	

}

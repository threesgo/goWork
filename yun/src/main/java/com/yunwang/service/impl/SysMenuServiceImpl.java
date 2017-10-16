package com.yunwang.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yunwang.dao.SysMenuDaoI;
import com.yunwang.dao.SysRoleDaoI;
import com.yunwang.dao.SysUserDaoI;
import com.yunwang.dao.SysUserRoleDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysUser;
import com.yunwang.model.pojo.SysUserRole;
import com.yunwang.service.SysMenuService;
import com.yunwang.service.SysUserService;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.util.string.SecurityUtil;
import com.yunwang.util.string.StringBufferByCollectionUtil;


@Service
public class SysMenuServiceImpl implements SysMenuService{
	
	@Autowired
	private SysUserDaoI sysUserDao;
	
	@Autowired
	private SysUserRoleDaoI sysUserRoleDao;
	
	@Autowired
	private SysRoleDaoI sysRoleDao;
	
	@Autowired
	private SysMenuDaoI sysMenuDao;

	@Override
	public List<SysMenu> findMenuByRole(Integer sysRoleId) {
		// TODO Auto-generated method stub
		return sysMenuDao.findMenuByRoleId(sysRoleId);
	}


}

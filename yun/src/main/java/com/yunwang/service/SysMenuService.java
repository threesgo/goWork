package com.yunwang.service;

import java.util.List;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysUser;

/**
 * @author KX
 * @date 2017-10-16
 * <p>内容管理业务层</p>
 */
public interface SysMenuService {

	List<SysMenu> findMenuByRole(Integer sysRoleId);
}

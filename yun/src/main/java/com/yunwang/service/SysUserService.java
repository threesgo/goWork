package com.yunwang.service;

import java.util.List;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysDepartMent;
import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysUser;

/**
 * @author YBF
 * @date 2016-9-21
 * <p>内容管理业务层</p>
 */
public interface SysUserService {
	public SysUser login(SysUser user);
	
	public List<SysUser> findList();

	public SysUser get(Integer id);

	public Pager<SysUser> findPageBySearch(Pager<SysUser> pager, SysUser user);

	public void update(SysUser updateUser);

	public void deleteList(Integer[] userIds);

	public void save(SysUser user);
	
	public void saveUserAndRole(SysUser user,String roleIds);
	
	public void updateUserAndRole(SysUser user);

	public void updateUserRoleDefault(Integer userId, Integer roleId);

	public List<SysMenu> findMenuByRoleId(Integer roleId);

	public List<SysRole> findRoleByUserId(Integer userId);

	public SysRole getDefaultRoleByUserId(Integer userId);
	
	public Pager<SysUser> findAllUser(String filterJsons,int page,int rows);
	
	public List<SysRole> findByUserId(Integer userId);
	
	public List<SysRole> findAllRole();
	
	public List<SysUser> findBySysUserName(String userName);
	
	public List<SysUser> findBySysUserNameExceptUserId(String userName,Integer userId);
	
	public void delete(Integer userId);
	
	public boolean isExist(String name);
	
	public boolean isExistDepartMent(String code);
	
	public void saveSysRole(SysRole sysRole);
	
	public SysRole findRoleByRoleId(Integer sysRoleId);
	
	public void deleteSysRole(SysRole sysRole);
	
	public void updateRole(SysRole sysRole);
	
	public List<SysDepartMent> findDepartMentByParentId(Integer parentId);
	
	public void saveSysDepartMent(SysDepartMent sysDepartMent);
}

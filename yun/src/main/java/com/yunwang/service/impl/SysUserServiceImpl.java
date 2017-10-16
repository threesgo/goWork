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
import com.yunwang.service.SysUserService;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.util.string.SecurityUtil;
import com.yunwang.util.string.StringBufferByCollectionUtil;


@Service
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	private SysUserDaoI sysUserDao;
	
	@Autowired
	private SysUserRoleDaoI sysUserRoleDao;
	
	@Autowired
	private SysRoleDaoI sysRoleDao;
	
	@Autowired
	private SysMenuDaoI sysMenuDao;

	@Override
	public List<SysUser> findList() {
		return sysUserDao.find("");
	}

	@Override
	public SysUser login(SysUser user) {
		SysUser adm = sysUserDao.getByUserName(user.getUserName());
		if (adm != null && adm.getPassWord().equals(SecurityUtil.getMD5(user.getPassWord()))) {
			return adm;
		} else {
			return null;
		}
	}
	
	@Override
	public SysUser get(Integer id) {
		return sysUserDao.get(SysUser.class, id);
	}

	@Override
	public Pager<SysUser> findPageBySearch(Pager<SysUser> pager, SysUser user) {
		StringBuffer buff = new StringBuffer ( "SELECT model from User as model where model.type=2 " ) ;
		if(null!=user){
			if(null!=user.getCreateStartDate()){
				buff.append ( "	and model.createDate>'" + user.getCreateStartDateStr () + "'" ) ;
			}
			if(null!=user.getCreateEndDate()){
				buff.append ( "  and model.createDate<'" + user.getCreateEndDateStr () + "'" ) ;
			}
			if(MyStringUtil.isNotBlank(user.getPhoneNum())){
				buff.append ( "	and model.phoneNum like '%" + user.getPhoneNum() + "%'" ) ;
			}
		}
		buff.append ( " ORDER BY model.createDate " ) ;
		return sysUserDao.pagedQuery  ( buff.toString ( ) , pager.getCurrentPage ( ) , pager.getPageSize ( ) ) ;
	}

	@Override
	public void update(SysUser updateUser) {
		sysUserDao.update(updateUser);
	}

	@Override
	public void deleteList(Integer[] userIds) {
		sysUserDao.executeHql("DELETE from User as model where model.id IN("+
				StringBufferByCollectionUtil.convertCollection(userIds, null, ",")+")");
	}

	@Override
	public void save(SysUser user) {
		user.setCreateDate(new Date());
		sysUserDao.save(user);
	}
	
	@Override
	public void updateUserRoleDefault(Integer userId, Integer roleId){
		List<SysUserRole> sysUsers = sysUserRoleDao.findByProperty("userId",userId);
		for(SysUserRole ur : sysUsers){
			if(ur.getRoleId().equals(roleId)){
				ur.setIsDefault(BigDecimal.ONE);
			}else{
				ur.setIsDefault(null);
			}
			sysUserRoleDao.update(ur);
		}
	}

	@Override
	public List<SysMenu> findMenuByRoleId(Integer roleId) {
		return sysMenuDao.findMenuByRoleId(roleId);
	}

	@Override
	public List<SysRole> findRoleByUserId(Integer userId) {
		return sysRoleDao.findByUserId(userId);
	}

	@Override
	public SysRole getDefaultRoleByUserId(Integer userId) {
		return sysRoleDao.getDefaultRoleByUserId(userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<SysUser> findAllUser(String filterJsons, int page, int rows) {
		JSONObject json = JSONObject.parseObject(filterJsons);
		Pager<SysUser> pager = sysUserDao.findAllUser(json, page, rows);
		List<SysUser> list = (List<SysUser>) pager.getData();
		
		List<SysUserRole> listUserRole = sysUserRoleDao.findUserAndRole();
		Map<Integer, List<SysUserRole>> mapList = new HashMap<Integer,List<SysUserRole>>();
		List<SysUserRole> listUR = null;
		for(SysUserRole userRole:listUserRole){
			if(null == mapList.get(userRole.getUserId())){
				listUR = new ArrayList<SysUserRole>();
				listUR.add(userRole);
				mapList.put(userRole.getUserId(), listUR);
			}else{
				mapList.get(userRole.getUserId()).add(userRole);
			}
		}
		
		for(SysUser user:list){
			List<SysUserRole> userRoleList = mapList.get(user.getId());
			if(userRoleList != null){
				user.setRoles(StringBufferByCollectionUtil.convertCollection(userRoleList, "name", ","));
				user.setRoleIds(StringBufferByCollectionUtil.convertCollection(userRoleList, "roleId", ","));
				
			}
			
		}
		
		return pager;
	}

	@Override
	public List<SysRole> findByUserId(Integer userId) {
		return sysRoleDao.findByUserId(userId);
	}

	@Override
	public List<SysRole> findAllRole() {
		return sysRoleDao.findAllRole();
	}

	@Override
	public List<SysUser> findBySysUserName(String userName) {
		return sysUserDao.findBySysUserName(userName);
	}
	
	public List<SysUser> findBySysUserNameExceptUserId(String userName,Integer userId) {
		return sysUserDao.findBySysUserNameExceptUserId(userName,userId);
	}
	

	@Override
	public void saveUserAndRole(SysUser user, String roleIds) {
		user.setCreateDate(new Date());
		sysUserDao.save(user);
		SysUserRole userRole =null;
		String[] roleId = roleIds.split(",");
		for(String id:roleId){
			userRole = new SysUserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId(Integer.parseInt(id.trim()));
			userRole.setIsDefault(new BigDecimal(1));
			sysUserRoleDao.save(userRole);
		}
	}
	
	public void updateUserAndRole(SysUser user){
		SysUser updateUser = sysUserDao.get(SysUser.class, user.getId());
		updateUser.setUserName(user.getUserName());
		updateUser.setRealName(user.getRealName());
		updateUser.setPassWord(user.getPassWord());
		updateUser.setPhoneNum(user.getPhoneNum());
		updateUser.setRelMail(user.getRelMail());
		sysUserDao.update(updateUser);
		sysUserRoleDao.deleteByProperty("userId",user.getId());
		SysUserRole userRole =null;
		String[] roleId = user.getRoleIds().split(",");
		for(String id:roleId){
			userRole = new SysUserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId(Integer.parseInt(id.trim()));
			userRole.setIsDefault(new BigDecimal(1));
			sysUserRoleDao.save(userRole);
		}
		
	}

	@Override
	public void delete(Integer userId) {
		sysUserDao.deleteByProperty("id", userId);
		sysUserRoleDao.deleteByProperty("userId", userId);
	}

}

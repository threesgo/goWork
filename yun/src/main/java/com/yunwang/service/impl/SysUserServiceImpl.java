package com.yunwang.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysDepartMentDaoI;
import com.yunwang.dao.SysMenuDaoI;
import com.yunwang.dao.SysPositionDaoI;
import com.yunwang.dao.SysRoleDaoI;
import com.yunwang.dao.SysRoleMenuDaoI;
import com.yunwang.dao.SysUserDaoI;
import com.yunwang.dao.SysUserRoleDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysDepartMent;
import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysPosition;
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
	
	@Autowired
	SysRoleMenuDaoI sysRoleMenuDao;
	
	@Autowired
	SysDepartMentDaoI sysDepartMentDao;
	@Autowired
	SysPositionDaoI sysPositionDao;
	
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
		JSONObject json = JSONObject.fromObject(filterJsons);
		Pager<SysUser> pager = sysUserDao.findAllUser(json, page, rows);
		if(null != pager){
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
		user.setPassWord(SecurityUtil.getMD5(user.getPassWord()));
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
		updateUser.setDepartmentId(user.getDepartmentId());
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

	@Override
	public boolean isExist(String name) {
		List<SysRole> list = sysRoleDao.findByName(name);
		if(list.size()>0){
			return false;
		}
		return true;
	}

	@Override
	public void saveSysRole(SysRole sysRole) {
		SysRole role = new SysRole();
		role.setName(sysRole.getName());
		role.setIconCls(sysRole.getIconCls());
		sysRoleDao.save(role);
	}

	
	@Override
	public SysRole findRoleByRoleId(Integer sysRoleId) {
		return sysRoleDao.get(SysRole.class, sysRoleId);
	}

	@Override
	public void deleteSysRole(SysRole sysRole) {
		sysRoleDao.deleteByProperty("id",sysRole.getId());
		sysUserRoleDao.deleteByProperty("roleId", sysRole.getId());
		sysRoleMenuDao.deleteByProperty("roleId", sysRole.getId());
		
	}

	@Override
	public void updateRole(SysRole sysRole) {
		sysRoleDao.update(sysRole);
	}

	/**
	 * @Description: 根据父级id查找部门
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-10-23
	 */
	@Override
	public List<SysDepartMent> findDepartMentByParentId(Integer parentId) {
		return sysDepartMentDao.findDepartMentByParentId(parentId);
	}

	/**
	 * @Description: 根据部门code判断是否存在
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-10-23
	 */
	@Override
	public boolean isExistDepartMent(String code) {
		List<SysDepartMent> list = sysDepartMentDao.findByProperty("code", code);
		if(list.size()>0){
			return false;
		}
		return true;
	}

	/**
	 * @Description: 新建部门信息
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-10-23
	 */
	@Override
	public void saveSysDepartMent(SysDepartMent sysDepartMent) {
		SysDepartMent departMent = new SysDepartMent();
		departMent.setCode(sysDepartMent.getCode());
		departMent.setName(sysDepartMent.getName());
		if(-1 == sysDepartMent.getParentId()){
			departMent.setParentId(0);
		}else{
			departMent.setParentId(sysDepartMent.getParentId());
		}
		
		Integer maxOrderNo = sysDepartMentDao.findMaxSeqByPfield("orderNo", "parentId", departMent.getParentId());
		departMent.setOrderNo(maxOrderNo+1);
		//组合顺序号
		String maxStrOrderNo = sysDepartMentDao.findMaxStrSeqByPfield("strOrderNo", "parentId",departMent.getParentId());
		if(StringUtils.isBlank(maxStrOrderNo)){
			if(0 == departMent.getParentId()){
				maxStrOrderNo = "000";
			}else{
				SysDepartMent parentDep = sysDepartMentDao.get(SysDepartMent.class,sysDepartMent.getParentId());
				maxStrOrderNo = parentDep.getStrOrderNo()+"000";
			}
		}
		String format = "%0"+maxStrOrderNo.length()+"d";
		Integer num = Integer.parseInt(maxStrOrderNo)+1;
		
		String strOrderNo = String.format(format, num);
		departMent.setStrOrderNo(strOrderNo);
		sysDepartMentDao.save(departMent);
	}

	@Override
	public List<SysDepartMent> findAllDepartMent() {
		return sysDepartMentDao.findAllDepartMent();
	}

	/**
	 * @Description: 更新或保存职位信息
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-10-25
	 */
	@Override
	public void saveOrUpdatePositionGrid(JSONObject obj,Integer departMentId) {
		if(null != obj){
			Integer id = Integer.parseInt(obj.getString("id"));
			SysPosition sysPosition = null;
			if(id>0){
				//更新
				sysPosition = sysPositionDao.get(SysPosition.class, id);
				
			}else{
				//新增
				sysPosition = new SysPosition();
				sysPosition.setDepartMentId(departMentId);
			}
			String code = obj.getString("code");
			sysPosition.setCode(code);
			String name = obj.getString("name");
			sysPosition.setName(name);
			String positionType = obj.getString("positionType");
			sysPosition.setPositionType(Integer.parseInt(positionType));
			sysPositionDao.saveOrUpdate(sysPosition);
		}
		
	}

	@Override
	public List<SysPosition> findPositionByDepartMentId(Integer departMentId) {
		return sysPositionDao.findByProperty("departMentId", departMentId);
	}

	@Override
	public void deletePositionByIds(String ids) {
		sysPositionDao.deleteByPropertys("id", ids);
	}

	@Override
	/**
	 * @Description: 根据id以及parentId删除部门
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-11-1
	 */
	public void deleteDepartMentById(String ids) {
		sysDepartMentDao.deleteByPropertys("id", ids);
		sysDepartMentDao.deleteByPropertys("parentId", ids);
	}

	/**
	 * @Description:创建或保存部门信息
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-11-1
	 */
	@Override
	public void saveOrUpdateDepartMentGrid(JSONObject obj, Integer parentId) {
		if(null != obj){
			Integer id = Integer.parseInt(obj.getString("id"));
			SysDepartMent sysDepartMent = null;
			if(id>0){
				//更新
				sysDepartMent = sysDepartMentDao.get(SysDepartMent.class, id);
			}else{
				//新增
				sysDepartMent = new SysDepartMent();
				sysDepartMent.setParentId(parentId);
				Integer maxOrderNo = sysDepartMentDao.findMaxSeqByPfield("orderNo", "parentId", sysDepartMent.getParentId());
				sysDepartMent.setOrderNo(maxOrderNo+1);
				//组合顺序号
				String maxStrOrderNo = sysDepartMentDao.findMaxStrSeqByPfield("strOrderNo", "parentId",sysDepartMent.getParentId());
				if(StringUtils.isBlank(maxStrOrderNo)){
					if(0 == sysDepartMent.getParentId()){
						maxStrOrderNo = "000";
					}else{
						SysDepartMent parentDep = sysDepartMentDao.get(SysDepartMent.class,sysDepartMent.getParentId());
						maxStrOrderNo = parentDep.getStrOrderNo()+"000";
					}
				}
				String format = "%0"+maxStrOrderNo.length()+"d";
				Integer num = Integer.parseInt(maxStrOrderNo)+1;
				
				String strOrderNo = String.format(format, num);
				sysDepartMent.setStrOrderNo(strOrderNo);
			}
			String code = obj.getString("code");
			sysDepartMent.setCode(code);
			String name = obj.getString("name");
			sysDepartMent.setName(name);
			sysDepartMentDao.saveOrUpdate(sysDepartMent);
		}
		
	}
}

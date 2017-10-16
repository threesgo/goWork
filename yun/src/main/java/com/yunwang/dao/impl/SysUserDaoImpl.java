package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.yunwang.dao.SysUserDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysUser;

@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDaoI{
	
	@Override
	public SysUser getByUserName(String userName) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userName",userName);
		return get("SELECT model FROM SysUser model WHERE model.userName=:userName",map);
	}
	
	/**
	 * 
	* @Title: findBySysUserId
	* @Description: 用户管理列表
	* @param @param json 查询条件
	* @param @param page 当前页
	* @param @param rows 每页显示大记录数
	* @return Pager<SysUser>    
	* @throws
	 */
	public Pager<SysUser> findAllUser(JSONObject json, int page,int rows){
		StringBuffer buf = new StringBuffer();
		/*buf.append("SELECT model FROM SysUser model,SysRole sysRole,SysUserRole sysUserRole "
				+ " WHERE model.id = sysUserRole.userId AND sysUserRole.roleId = sysRole.id ");*/
		/*buf.append("SELECT distinct model.ID id,"
				 + "model.USER_NAME userName,"
				 + "model.REAL_NAME realName,"
				 + "model.PHONE_NUM phoneNum,"
				 + "model.REL_EMAIL relMail,"
				 + "model.CREATE_DATE createDate,"
				 + "model.UPDATE_DATE updateDate,"
				 + "model.DEPARTMENT_ID departmentId "
				 + "FROM SYS_USER model "
				 + "LEFT JOIN SYS_USER_ROLE sysUserRole ON model.ID=sysUserRole.USER_ID ");
		
		if(null != json){
			//用户名
			String userName = json.getString("userName");
			if(StringUtils.isNotBlank(userName)){
				if(buf.indexOf("WHERE")!=-1){
					buf.append(" AND ");
				}else{
					buf.append(" WHERE ");
				}
				buf.append("model.USER_NAME LIKE '%"+userName+"%'");
			}
			//真实名称
			String realName = json.getString("realName");
			if(StringUtils.isNotBlank(realName)){
				if(buf.indexOf("WHERE")!=-1){
					buf.append(" AND ");
				}else{
					buf.append(" WHERE ");
				}
				buf.append("model.REAL_NAME LIKE '%"+realName+"%'");
			}
			
			//真实名称
			String roleIds = json.getString("roleIds");
			if(StringUtils.isNotBlank(roleIds)){
				if(buf.indexOf("WHERE")!=-1){
					buf.append(" AND ");
				}else{
					buf.append(" WHERE ");
				}
				buf.append("sysUserRole.ROLE_ID IN ("+roleIds+") ");
			}
		}
		
		buf.append(" ORDER BY model.id ");*/
		buf.append("SELECT distinct model FROM SysUser model,SysRole sysRole,SysUserRole sysUserRole "
				+ " WHERE model.id = sysUserRole.userId AND sysUserRole.roleId = sysRole.id ");
//		buf.append("SELECT model FROM SysUser model");
		/*Map<String, Object> parmeMap = new HashMap<String, Object>();
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("userName", new StringType());
		scalarMap.put("realName", new StringType());
		scalarMap.put("phoneNum", new StringType());
		scalarMap.put("relMail", new StringType());
		scalarMap.put("createDate", new DateType());
		scalarMap.put("updateDate", new DateType());
		scalarMap.put("departmentId", new IntegerType());
		
		return null;*/
		
		//return pagedSqlQuery(buf.toString(), page, rows, parmeMap, scalarMap, SysUser.class);
		
		if(json != null){
			//用户名,模糊查询
			String sysUsreName = json.getString("userName");
			if(StringUtils.isNotBlank(sysUsreName)){
				buf.append("AND model.userName LIKE '%"+sysUsreName+"%' ");
			}
			
			//真实姓名,模糊查询
			String sysRealName = json.getString("realName");
			if(StringUtils.isNotBlank(sysRealName)){
				buf.append("AND model.realName LIKE '%"+sysRealName+"%' ");
			}
			
			//角色,多选
			String sysRoles = json.getString("roleIds");
			if(StringUtils.isNotBlank(sysRoles)){
				buf.append("AND sysUserRole.roleId IN ("+sysRoles+") ");
			}
		}
		
		buf.append(" ORDER BY model.id ");
		return pagedQuery(buf.toString(), page, rows);
	}

	@Override
	public List<SysUser> findBySysUserName(String userName) {
		String hql ="SELECT model FROM SysUser model WHERE model.userName=? ";
		return find(hql,userName);
	}

	@Override
	public List<SysUser> findBySysUserNameExceptUserId(String userName,Integer userId) {
		String hql ="SELECT model FROM SysUser model WHERE model.userName=? AND model.id!=?";
		return find(hql,userName,userId);
	}
}

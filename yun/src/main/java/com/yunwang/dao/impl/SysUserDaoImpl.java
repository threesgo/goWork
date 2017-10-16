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
		buf.append("SELECT distinct model FROM SysUser model,SysRole sysRole,SysUserRole sysUserRole "
				+ " WHERE model.id = sysUserRole.userId AND sysUserRole.roleId = sysRole.id ");
//		buf.append("SELECT model FROM SysUser model");
		
		
		if(json != null){
			//用户名,模糊查询
			String sysUsreName = json.getString("sysUsreName");
			if(StringUtils.isNotBlank(sysUsreName)){
				buf.append("AND model.userName LIKE '%"+sysUsreName+"%' ");
			}
			
			//真实姓名,模糊查询
			String sysRealName = json.getString("sysRealName");
			if(StringUtils.isNotBlank(sysRealName)){
				buf.append("AND model.realName LIKE '%"+sysRealName+"%' ");
			}
			
			//角色,多选
			String sysRole = json.getString("sysRole");
			if(StringUtils.isNotBlank(sysRealName)){
				buf.append("AND sysUserRole.roleId IN ("+sysRole+") ");
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

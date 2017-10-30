package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

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
//		buf.append("SELECT distinct model FROM SysUser model,SysRole sysRole,SysUserRole sysUserRole "
//				+ " WHERE model.id = sysUserRole.userId AND sysUserRole.roleId = sysRole.id ");
		buf.append("SELECT model.ID id, "
				+ "model.PASSWORD passWord, "
				+ "model.USER_NAME userName,"
				+ "model.REAL_NAME realName,"
				+ "model.PHONE_NUM phoneNum,"
				+ "model.REL_EMAIL relMail,  "
				//+ "model.CREATE_DATE createDate, "
				+ "model.UPDATE_DATE updateDate,"
				+ "model.DEPARTMENT_ID departmentId, "
				+ "model.POSITION_ID positionId,"
				+ "sysDepartMent.CODE code "
				+ "FROM SYS_USER model "
				+ "LEFT JOIN SYS_USER_ROLE sysUserRole ON model.ID=sysUserRole.USER_ID "
				+ "LEFT JOIN SYS_ROLE sysRole ON sysUserRole.ROLE_ID=sysRole.ID "
				+ "LEFT JOIN SYS_DEPARTMENT sysDepartMent ON model.DEPARTMENT_ID=sysDepartMent.ID "
				+ "LEFT JOIN SYS_POSITION sysPosition ON model.POSITION_ID=sysPosition.ID ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		System.out.println(buf.indexOf("WHERE"));
		if(!json.isEmpty()){
			//用户名,模糊查询
			String sysUsreName = json.getString("userName");
			if(StringUtils.isNotBlank(sysUsreName)){
				if(buf.indexOf("WHERE") == -1){
					buf.append(" WHERE ");
				}else{
					buf.append(" AND ");
				}
				buf.append(" model.USER_NAME LIKE '%"+sysUsreName+"%' ");
			}
			
			//真实姓名,模糊查询
			String sysRealName = json.getString("realName");
			if(StringUtils.isNotBlank(sysRealName)){
				if(buf.indexOf("WHERE") == -1){
					buf.append(" WHERE ");
				}else{
					buf.append(" AND ");
				}
				buf.append(" model.REAL_NAME LIKE '%"+sysRealName+"%' ");
			}
			
			//角色,多选
			String sysRole = json.getString("roleIds");
			if(StringUtils.isNotBlank(sysRole)){
				if(buf.indexOf("WHERE") == -1){
					buf.append(" WHERE ");
				}else{
					buf.append(" AND ");
				}
				buf.append(" sysUserRole.role_Id IN ("+sysRole+") ");
			}
			
			//部门,单选
			String userDepartMent = json.getString("userDepartMent");
			if(StringUtils.isNotBlank(userDepartMent)){
				if(buf.indexOf("WHERE") == -1){
					buf.append(" WHERE ");
				}else{
					buf.append(" AND ");
				}
				buf.append(" model.DEPARTMENT_ID=:userDepartMent ");
				parmeMap.put("userDepartMent", userDepartMent);
			}
		}
		
		buf.append(" ORDER BY model.ID ");
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("passWord", new StringType());
		scalarMap.put("userName", new StringType());
		scalarMap.put("realName", new StringType());
		scalarMap.put("phoneNum", new StringType());
		scalarMap.put("relMail", new StringType());
		//scalarMap.put("createDate", new DateType());
		scalarMap.put("updateDate", new DateType());
		scalarMap.put("departmentId", new IntegerType());
		scalarMap.put("positionId", new IntegerType());
		scalarMap.put("code", new StringType());
		
		return pagedSqlQuery(buf.toString(),page,rows,parmeMap,scalarMap);
		//return pagedQuery(buf.toString(), page, rows);
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

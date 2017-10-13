package com.yunwang.action;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysUser;
import com.yunwang.service.SysUserService;
import com.yunwang.util.action.AbstractLoginAction;

@Action(value = "sysUserAction", results = {
		@Result(name="index",location="/WEB-INF/web/sysUser/index.jsp")//用户管理主页面
		,@Result(name="addUser",location="/WEB-INF/web/sysUser/addUser.jsp")//新建页面
		,@Result(name="editUser",location="/WEB-INF/web/sysUser/editUser.jsp")//编辑页面
		
		,@Result(name="modifyAdminInfo",location="/WEB-INF/web/adminRightsManage/modifyAdminInfo.jsp")
		,@Result(name="modifyPassWord",location="/WEB-INF/web/adminRightsManage/modifyPassWord.jsp")
		,@Result(name="userInfo",location="/WEB-INF/web/ProjectAuthorityManage/userInfo.jsp")
		,@Result(name="addRight",location="/WEB-INF/web/adminRightsManage/accreditAdmin.jsp")
	}
)
public class SysUserAction extends AbstractLoginAction{

	private final static Logger LOG =Logger.getLogger(SysUserAction.class);
	/*
	 * @date 2017-9-27
	 * @author YBF
	 * //系统用户管理
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysUserService sysUserService;
	
	private SysUser sysUser;
	private Integer roleId;
	private String filterJsons;
	private String roleIds;
	private String userId;
	
	/*
	 * @date 2017-9-29
	 * @author KXL
	 * 
	 */
	public String execute()   {
		return "index";
	}
	
	/*
	 * @date 2017-9-29
	 * @author KXL
	 * 用户管理列表页面
	 */
	public String listUser()   {
		Pager<SysUser> pager = sysUserService.findAllUser(filterJsons,page,rows);
		JSONObject obj = new JSONObject();
		if(null != pager){
			obj.put("total", pager.getTotalRows());
			obj.put("rows", JSONArray.fromObject(pager.getData()));
		}else{
			obj.put("total", 0);
			obj.put("rows", "[]");
		}
		return ajaxJSONObj(obj);
	}
	
	/*
	 * @date 2017-9-29
	 * @author KXL
	 * 用户管理列表页面
	 */
	public String preAdd() {
		return "addUser";
	}
	
	/**
	 * 
	* @Title: add
	* @Description: 添加用户
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public String add(){
		try{
			List<SysUser> listUser = sysUserService.findBySysUserName(sysUser.getUserName());
			if(listUser.size()>0){
				return error("用户名已存在");
			}
			sysUserService.saveUserAndRole(sysUser,roleIds);
			return success("添加成功");
		}catch(Exception e){
			LOG.error(e);
			return error("添加失败");
		}
		
	}
	
	public String preEdit() {
		sysUser = sysUserService.get(Integer.parseInt(userId));
		return "editUser";
	}
	
	public String edit(){
		try{
			List<SysUser> listUser = sysUserService.findBySysUserNameExceptUserId(sysUser.getUserName(),sysUser.getId());
			if(listUser.size()>0){
				return error("用户名已存在");
			}
			System.out.println(roleIds);
			sysUserService.updateUserAndRole(sysUser);
			return success("添加成功");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("添加失败");
		}
	}
	
	/**
	* @Title: updateUserRoleDefault
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/
	public String updateUserRoleDefault(){
		try{
			sysUserService.updateUserRoleDefault(sessionAdm.getId(),roleId);
			return success("更新默认角色成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("更新默认角色失败!");
		}
	}


	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getFilterJsons() {
		return filterJsons;
	}

	public void setFilterJsons(String filterJsons) {
		this.filterJsons = filterJsons;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

package com.yunwang.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysUser;
import com.yunwang.service.SysMenuService;
import com.yunwang.service.SysUserService;
import com.yunwang.util.action.AbstractLoginAction;
import com.yunwang.util.string.SecurityUtil;

@Action(value = "sysUserAction", results = {
		@Result(name="index",location="/WEB-INF/web/sysUser/index.jsp")//用户管理主页面
		,@Result(name="addUser",location="/WEB-INF/web/sysUser/addUser.jsp")//新建页面
		,@Result(name="editUser",location="/WEB-INF/web/sysUser/editUser.jsp")//编辑页面
		,@Result(name="modifyPassWord",location="/WEB-INF/web/sysUser/modifyPassWord.jsp")
		
	}
)
@ParentPackage("AuthorityInterceptor")
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
	@Autowired
	private SysMenuService sysMenuService;
	
	private SysUser sysUser;
	private Integer roleId;
	private String filterJsons;
	private String roleIds;
	private String userId;
	private String oldPassword;
	
	/*
	 * @date 2017-9-29
	 * @author KXL
	 * 
	 */
	public String execute()   {
		return "index";
	}
	
	
	public String preUpdatePassword(){
	    return "modifyPassWord";
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
			//List<SysUser>  list = (List<SysUser>) pager.getData();
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
			
			sysUserService.updateUserAndRole(sysUser);
			return success("添加成功");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("添加失败");
		}
	}
	
	public String delete(){
		try{
			sysUserService.delete(sysUser.getId());
			return success("删除成功");
		}catch(Exception e){
			e.printStackTrace();
			return error("删除失败");
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
			//获取默认角色所关联的菜单
			List<SysMenu> listMenu = sysMenuService.findRelMenuByRoleIdAndViewType(roleId, 2);
			sessionMap.put("defaultMenu",listMenu);
			return success("更新默认角色成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("更新默认角色失败!");
		}
	}

	public String updatePassword(){
		SysUser updateUser = sysUserService.get(sysUser.getId());
		try{
			if(!SecurityUtil.getMD5(oldPassword).equals(updateUser.getPassWord())){
			   return error("原始密码不正确,请重新填写");
			}
			updateUser.setPassWord(SecurityUtil.getMD5(sysUser.getPassWord()));
			sysUserService.update(updateUser);
			return success("密码修改成功");
		}catch(Exception e){
		    return error("密码修改失败");
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


	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
}

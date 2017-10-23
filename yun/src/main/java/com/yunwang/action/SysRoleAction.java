package com.yunwang.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysRoleMenu;
import com.yunwang.service.SysMenuService;
import com.yunwang.service.SysRoleMenuService;
import com.yunwang.service.SysUserService;
import com.yunwang.util.action.AbstractLoginAction;



@Action(value = "sysRoleAction", results = {
		@Result(name="index",location="/WEB-INF/web/sysRole/index.jsp"),
		//@Result(name="preAdd",location="/WEB-INF/web/sysRole/preAdd.jsp"),
		@Result(name="preAddOrEdit",location="/WEB-INF/web/sysRole/preAddOrEdit.jsp"),
		@Result(name="edit",location="/WEB-INF/web/sysRole/edit.jsp"),
		@Result(name="list",location="/WEB-INF/web/sysRole/list.jsp")
	}
)
public class SysRoleAction extends AbstractLoginAction{
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	private SysRole sysRole;
	
	private String id;
	private String needAll;
	
	private String menus;
	private String roles;
	private String jsonInfo;
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 角色管理主页面
	 */
	public String execute(){
		return "index";
	}	
	
	
	/**
	 * @date 2017.10.3
	 * @author KXL
	 * @return
	 * <p>角色列表结构树</p>
	 */
	public String findTree(){
		JSONArray jsoArr=new JSONArray();
		JSONObject json=null;
		if(null==id){
			json=new JSONObject();
			json.put("id","root");
			json.put("text","角色列表");
			json.put("state", "closed");
			jsoArr.add(json);
		}else if(id.startsWith("root")){
			//SysUser user = (SysUser) sessionMap.get(Constant.SESSION_ADMIN);
			//List<SysRole> listSysRole = sysUserService.findByUserId(user.getId()); 
			List<SysRole> listSysRole = sysUserService.findAllRole();
			JSONObject jso=null;
			for(SysRole role:listSysRole){
			    jso=new JSONObject();
				jso.put("id","role"+role.getId());
				jso.put("text",role.getName());
				jso.put("state", "open");
				jso.put("attributes", JSONObject.fromObject(role));
				jsoArr.add(jso);
			}
		}
		return ajaxText(jsoArr);
	}
	 
	/**
	 * @date 2017.10.4
	 * @author KXL
	 * @return
	 * <p>加载角色下拉框</p>
	 */
	public String findAll(){
		JSONObject obj = null;
		JSONArray arr = new JSONArray();
		if("true".equals(needAll)){
			obj = new JSONObject();
			obj.put("id", "");
			obj.put("sysRole", "全部");
			obj.put("selected", true);
			arr.add(obj);
		}
		List<SysRole> listRole = sysUserService.findAllRole();
		if(listRole.size()>0){
			for(SysRole role:listRole){
				obj = new JSONObject();
				obj.put("id", role.getId());
				obj.put("sysRole", role.getName());
				arr.add(obj);
			}
		}
		return ajaxText(arr.toString());
	}
	
	/**
	 * <p>
	 * 1.角色列表-跳转添加角色
	 * 2.跳转修改角色名称页面
	 * </p>
	 */
	public String preAddOrEdit(){
		if(sysRole != null){
			sysRole=sysUserService.findRoleByRoleId(sysRole.getId());
		}
		return "preAddOrEdit";
	}
	
	/**
	 * @date 2016-12-12
	 * @author LKX
	 * @return
	 * <p>跳转修改角色名称页面</p>
	 */
/*	public String preEdit(){
		sysRole=sysUserService.findRoleByRoleId(sysRole.getId());
		return "preEdit";
	}*/
	
	/**
	 * @date 2016-12-12
	 * @author LKX
	 * @return
	 * <p>更新修改后角色名称</p>
	 */
	public String update(){
			String newName = sysRole.getName();
			sysRole = sysUserService.findRoleByRoleId(sysRole.getId());
			String oldName=sysRole.getName();
			if(oldName.equals(newName)){
				return success(getText("update_success"));
			}else if(!sysUserService.isExist(newName)){
				return error("该用户已存在");
			}else{
				sysRole.setName(newName);
				sysUserService.updateRole(sysRole);
				return success("更新成功",JSONObject.fromObject(sysRole));	
			}
			
	}
	
	/**
	 * <p>角色列表-保存角色信息</p>
	 */
	public String add(){
		if(sysUserService.isExist(sysRole.getName())){
			sysUserService.saveSysRole(sysRole);
			return success(getText("添加成功"),JSONObject.fromObject(sysRole));
		}else{
			return error(getText("该用户已存在"));
		}
	}
	
	/**
	 * <p>关联模块-删除角色信息</p>
	 */
	public String delete(){
		try{
			sysRole = sysUserService.findRoleByRoleId(sysRole.getId());
			sysUserService.deleteSysRole(sysRole);							
			return success("删除成功",JSONObject.fromObject(sysRole));
		}catch(Exception e){
			e.printStackTrace();
			return error("删除失败");
		}
	}
	
	/**
	 * <p>角色编辑</p>
	 */
	public String edit(){
		if(sysRole!=null){
			List<Object> list = sysMenuService.findMenuByRoleId(sysRole.getId());
			jsonInfo = JSONArray.fromObject(list).toString();
		}
		return "edit";
	}
	
	/**
	
	 * <p>保存--保存角色对应模块关系信息</p>
	 */
	public String saveRoleRelMenu(){
		try{
			sysRoleMenuService.saveRoleRelMenu(sysRole,menus);
			return success("保存成功");
		}catch (Exception e) {
			e.printStackTrace();
			return error("保存失败");
		}
		
	}
	
	/**
	 * @date 2017.10.16
	 * @author kx
	 * @return
	 * <p>角色管理-角色列表</p>
	 */
	public String list(){
		List<SysMenu> menuList = sysMenuService.findAll();
		List<SysRole> roleList = sysUserService.findAllRole();
		//查询所有角色对应权限关系
		List<SysRoleMenu> relMenuList = sysRoleMenuService.findAll();
		Map<Integer, Set<Integer>> roleRelMenuMap = null;
		if(relMenuList != null && !relMenuList.isEmpty()){
			roleRelMenuMap = new HashMap<Integer, Set<Integer>>();
			for(SysRoleMenu sysRoleMenu : relMenuList){
				Integer key = sysRoleMenu.getMenuId();
				Set<Integer> roleIds = roleRelMenuMap.get(key);
				if(roleIds == null){
					roleIds = new HashSet<Integer>();
				}
				roleIds.add(sysRoleMenu.getRoleId());
				roleRelMenuMap.put(key, roleIds);
			}
		}
		
		List<Map<String, Object>> menuMapList = new ArrayList<Map<String,Object>>();
		if(menuList!=null && !menuList.isEmpty()){
			for(SysMenu sysMenu : menuList){
				Map<String, Object> menuMap = new HashMap<String, Object>();
				menuMap.put("name", getText(sysMenu.getName()));
				menuMap.put("id", sysMenu.getId());
				menuMap.put("parentId", sysMenu.getParentId());
				if(roleList!=null && !roleList.isEmpty() && roleRelMenuMap!=null){
					Integer key = sysMenu.getId();
					Set<Integer> roleIds = roleRelMenuMap.get(key);
					for(SysRole sysRole : roleList){
						String valueKey = "role_"+sysRole.getId();
						if(roleIds!=null){
							menuMap.put(valueKey, roleIds.contains(sysRole.getId()));
						}else{
							menuMap.put(valueKey,false);
						}
					}
				}
				menuMapList.add(menuMap);
			}
		}
		roles = JSONArray.fromObject(roleList).toString();
		menus = JSONArray.fromObject(menuMapList).toString();
		return "list";
	}

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNeedAll() {
		return needAll;
	}

	public void setNeedAll(String needAll) {
		this.needAll = needAll;
	}

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getJsonInfo() {
		return jsonInfo;
	}

	public void setJsonInfo(String jsonInfo) {
		this.jsonInfo = jsonInfo;
	}
	
}

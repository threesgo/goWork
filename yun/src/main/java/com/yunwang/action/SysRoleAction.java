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
		
		@Result(name="preAdd",location="/WEB-INF/web/sysRole/preAdd.jsp"),
		@Result(name="preEdit",location="/WEB-INF/web/sysRole/preEdit.jsp"),
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
	
	/*private String moduleIds;
	
	
	
	private Integer majorId;*/
	
	/*@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private ISysModuleService sysModuleService;
	@Autowired
	private ISysRoleModuleService sysRoleModuleService;
*/
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 角色管理主页面
	 */
	public String execute(){
		return "index";
	}	
	
	/**
	 * @author SW
	 * @date 2017-3-23
	 * <p>根据专业查询角色类型</p>
	 * @param
	 * @throws
	 * @return String
	 *//*
	public String roleList(){
		JSONArray json = sysRoleService.findByMajorIdToJSON(majorId);
		return ajaxText(json.toString());
	}*/
	
	/**
	 * @author SW
	 * @date 2017-5-2
	 * <p>从数据字典里查询所有的专业</p>
	 * @param
	 * @throws
	 * @return String
	 *//*
	public String majorList(){
		JSONArray json = sysRoleService.findMajorToJSON();
		return ajaxText(json.toString());
	}*/
	
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
				jso.put("text","角色："+role.getName());
				jso.put("state", "open");
				jso.put("attributes", JSONObject.fromObject(role));
				jsoArr.add(jso);
			}
		}
		return ajaxText(jsoArr);
		//return null;
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
	 * <p>角色列表-跳转添加角色</p>
	 */
	public String preAdd(){
		return "preAdd";
	}
	
	/**
	 * @date 2016-12-12
	 * @author LKX
	 * @return
	 * <p>修改角色名</p>
	 *//*
	public String preEdit(){
		sysRole=sysRoleService.get(sysRole.getId());
		return "preEdit";
	}
	
	//更新编辑姓名后的的系统角色数据
	public String update(){
			String newName=sysRole.getName();
			sysRole=sysRoleService.get(sysRole.getId());
			String oldName=sysRole.getName();
			if(oldName.equals(newName)){
				return success(getText("update_success"));
			}else if(sysRoleService.isExist(newName)){
				return error(getText("该用户已存在"));
			}else{
				sysRole.setName(newName);
				sysRoleService.update(sysRole);
				return success(getText("update_success"),JSONObject.fromObject(sysRole));	
			}
			
	}*/
	
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
	/*
	public String delete(){
		try{
			sysRole = sysRoleService.get(sysRole.getId());
			sysRoleService.deleteSysRole(sysRole);							
			return success(getText("delete_success"),JSONObject.fromObject(sysRole));
		}catch(Exception e){
			return error(handlerException(e,getText("delete_failed")));
		}
	}
	
	*//**
	 * @date 2016-11-29
	 * @author YBF
	 * @return
	 * <p>角色编辑</p>
	 */
	public String edit(){
		if(sysRole!=null){
			List<Object> list = sysMenuService.findMenuByRoleId(sysRole.getId());
			//List<Object> list = sysModuleService.findModulesByRole(sysRole.getId());
			jsonInfo = JSONArray.fromObject(list).toString();
		}
		return "edit";
	}
	
	/**
	 * saveRoleRelModule method.
	 * @author 冯英峰
	 * @date 2016年12月1日 下午1:51:07
	 * <p>保存角色对应模块关系信息</p>
	 * @return
	 * @return String
	 *//*
	public String saveRoleRelModule(){
		sysRoleService.saveRoleRelModule(sysRole, moduleIds);
		return success(getText("operation_successfully"));
	}
	
	*//**
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

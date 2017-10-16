package com.yunwang.action;


import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysUser;
import com.yunwang.service.SysUserService;
import com.yunwang.util.Constant;
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
	
	private SysRole sysRole;
	
	private String id;
	
	/*private String jsonInfo;
	
	private String moduleIds;
	
	private String modules;
	
	private String roles;
	
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
			SysUser user = (SysUser) sessionMap.get(Constant.SESSION_ADMIN);
			List<SysRole> listSysRole = sysUserService.findByUserId(user.getId()); 
			JSONObject jso=null;
			for(SysRole role:listSysRole){
			    jso=new JSONObject();
				jso.put("id","role"+role.getId());
				jso.put("text","角色："+role.getName());
				jso.put("state", "closed");
				jso.put("attributes", JSONObject.fromObject(role));
				jsoArr.add(jso);
			}
		}/*else if(id.startsWith("role")){
			//菜单管理
			Integer role = Integer.parseInt(id.substring(4,id.length()));
			List<SysMenu> sysMenuList = sysUserService.findMenuByRoleId(role);
			JSONObject jso=null;
			for(SysMenu vo:sysMenuList){
			    jso=new JSONObject();
				jso.put("id","Menu"+vo.getId());
				jso.put("text","菜单："+vo.getName());
				jso.put("state", "open");
				jso.put("attributes", JSONObject.fromObject(vo));
				jsoArr.add(jso);
			}
		}*/
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
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		List<SysRole> listRole = sysUserService.findAllRole();
		if(listRole.size()>0){
			for(SysRole role:listRole){
				obj.put("id", role.getId());
				obj.put("sysRole", role.getName());
				arr.add(obj);
			}
		}
		return ajaxText(arr.toString());
	}
	
	/**
	 * 
	 * @date 2016-11-29
	 * @author YBF
	 * @return
	 * <p>添加角色</p>
	 *//*
	public String preAdd(){
		return "preAdd";
	}
	
	*//**
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
			
	}
	public String add(){
		if(!sysRoleService.isExist(sysRole.getName())){
			sysRoleService.saveSysRole(sysRole);
			return success(getText("save_success"),JSONObject.fromObject(sysRole));
		}else{
			return error(getText("该用户已存在"));
		}
		
	}
	
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
	 *//*
	public String edit(){
		if(sysRole!=null){
			List<Object> list = sysModuleService.findModulesByRole(sysRole.getId());
			jsonInfo = JSONArray.fromObject(list).toString();
		}
		return "edit";
	}
	
	*//**
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
	 * <p>角色列表</p>
	 */
	public String list(){
		//List<SysModule> moduleList = sysModuleService.findAll();
		List<SysModule> moduleList = sysModuleService.findByMajorId(Integer.parseInt(id.substring(2,id.length())));
		
		List<SysRole> roleList = sysRoleService.findByMajorId(Integer.parseInt(id.substring(2,id.length())));
		//查询所有角色对应权限关系
		List<SysRoleModule> relModuleList = sysRoleModuleService.findAll();
		Map<Integer, Set<Integer>> roleRelModuleMap = null;
		if(relModuleList!=null && !relModuleList.isEmpty()){
			roleRelModuleMap = new HashMap<Integer, Set<Integer>>();
			for(SysRoleModule sysRoleModule : relModuleList){
				Integer key = sysRoleModule.getModuleId();
				Set<Integer> roleIds = roleRelModuleMap.get(key);
				if(roleIds == null){
					roleIds = new HashSet<Integer>();
				}
				roleIds.add(sysRoleModule.getRoleId());
				roleRelModuleMap.put(key, roleIds);
			}
		}
		List<Map<String, Object>> moduleMapList = new ArrayList<Map<String,Object>>();
		if(moduleList!=null && !moduleList.isEmpty()){
			for(SysModule sysModule : moduleList){
				Map<String, Object> moduleMap = new HashMap<String, Object>();
				moduleMap.put("name", getText(sysModule.getName()));
				moduleMap.put("id", sysModule.getId());
				moduleMap.put("parentId", sysModule.getParentId());
				if(roleList!=null && !roleList.isEmpty() && roleRelModuleMap!=null){
					Integer key = sysModule.getId();
					Set<Integer> roleIds = roleRelModuleMap.get(key);
					for(SysRole sysRole : roleList){
						String valueKey = "role_"+sysRole.getId();
						if(roleIds!=null){
							moduleMap.put(valueKey, roleIds.contains(sysRole.getId()));
						}else{
							moduleMap.put(valueKey,false);
						}
					}
				}
				moduleMapList.add(moduleMap);
			}
		}
		roles = JSONArray.fromObject(roleList).toString();
		modules = JSONArray.fromObject(moduleMapList).toString();
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
	
	/*public String getJsonInfo() {
		return jsonInfo;
	}

	public void setJsonInfo(String jsonInfo) {
		this.jsonInfo = jsonInfo;
	}
	
	public String getModuleIds() {
		return moduleIds;
	}
	
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}
	
	public String getModules() {
		return modules;
	}
	
	public void setModules(String modules) {
		this.modules = modules;
	}
	
	public String getRoles() {
		return roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Integer getMajorId() {
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}*/
	
}

package com.yunwang.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysUser;
import com.yunwang.service.SysMenuService;
import com.yunwang.service.SysUserService;
import com.yunwang.util.action.AbstractLoginAction;

@Action(
	value = "mainAction", 
	results = {
		@Result(name="index",location="/WEB-INF/web/main.jsp"),
		@Result(name="selectRole",location="/WEB-INF/web/selectRole.jsp"),
		@Result(name="center",location="/WEB-INF/web/center.jsp"),
		@Result(name="todo",location="/WEB-INF/web/portal/todo.jsp"),
		@Result(name="about",location="/WEB-INF/web/portal/about.jsp"),
		@Result(name="link",location="/WEB-INF/web/portal/link.jsp"),
		@Result(name="update",location="/WEB-INF/web/portal/update.jsp")
	}
)
public class MainAction extends AbstractLoginAction{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -8579430291037327132L;
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysMenuService sysMenuService;
	
	private Integer roleId;
	
	private List<SysRole> roleList;
	
	private SysRole defaultRole;
	
	private String roleListStr;
	
	@Override
	public String execute() throws Exception {
		//获取用户的默认角色
		defaultRole = sysUserService.getDefaultRoleByUserId(sessionAdm.getId());
		//获取默认角色所关联的菜单
		if(null != defaultRole){
			List<SysMenu> listMenu = sysMenuService.findRelMenuByRoleIdAndViewType(defaultRole.getId(),2);
			Map<String,Integer> map = new HashMap<String, Integer>();
			for(SysMenu sysMenu:listMenu){
				if(null == map.get(sysMenu.getUrl())){
					map.put(sysMenu.getUrl(), 1);
				}
			}
			sessionMap.put("defaultMenu",map);
		}
		return "index";
	}
	
	
    /**
     * @date 2017-9-28
     * @author YBF
     * @return
     * <p>角色选择</p>
     */
    public String selectRole(){
    	roleList = sysUserService.findRoleByUserId(sessionAdm.getId());
    	roleListStr = JSONArray.fromObject(roleList).toString();
    	return "selectRole";
    }
	
    /**
     * @date 2017-9-28
     * @author YBF
     * @return
     * <p>查找用户关联的菜单</p>
     */
    public String findUserMenu(){
    	//根据用户角色查询关联的菜单模块
    	List<SysMenu> sysMenus = sysUserService.findMenuByRoleId(roleId);
    	Map<Integer,SysMenu> moudleMap=new HashMap<Integer,SysMenu>();
    	List<SysMenu> mewMenus = new ArrayList<SysMenu>();
		for(SysMenu sysMenu:sysMenus){
			sysMenu.setAttributes(JSONObject.fromObject(sysMenu));//其他对象转化为json对象
			SysMenu pMenu = moudleMap.get(sysMenu.getParentId());
			if(null==pMenu){
				moudleMap.put(sysMenu.getId(),sysMenu);
				mewMenus.add(sysMenu);
			}else{
				pMenu.getChildren().add(sysMenu);
			}
		}
		return ajaxText(JSONArray.fromObject(mewMenus).toString());
    }
    
    /**
     * @date 2017-11-16
     * @author YBF
     * @return
     * <p>中心面板</p>
     */
    public String centerIndex(){
    	SysUser sysUser = sysUserService.get(sessionAdm.getId());
    	System.out.println(sysUser.getId());
    	return "center";
    }
    
    /**
     * @date 2017-11-16
     * @author YBF
     * @return
     * <p>代办事项</p>
     */
    public String todo(){
    	return "todo";
    }
    
    /**
     * @date 2017-11-16
     * @author YBF
     * @return
     * <p>关于系统</p>
     */
    public String about(){
    	return "about";
    }
    
    /**
     * @date 2017-11-16
     * @author YBF
     * @return
     * <p>链接</p>
     */
    public String link(){
    	return "link";
    }
    
    /**
     * @date 2017-11-16
     * @author YBF
     * @return
     * <p>系统更新</p>
     */
    public String update(){
    	return "update";
    }
    

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}

	public SysRole getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(SysRole defaultRole) {
		this.defaultRole = defaultRole;
	}

	public String getRoleListStr() {
		return roleListStr;
	}

	public void setRoleListStr(String roleListStr) {
		this.roleListStr = roleListStr;
	}
}

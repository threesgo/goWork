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



@Action(value = "sysDepartMentAction", results = {
		@Result(name="index",location="/WEB-INF/web/sysDepartMent/index.jsp"),
	}
)
public class SysDepartMentAction extends AbstractLoginAction{
	
	
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
	//public String findTree(){
		/*JSONArray jsoArr=new JSONArray();
		JSONObject json=null;
		if(null==id){
			json=new JSONObject();
			json.put("id","root");
			json.put("text","角色列表");
			json.put("state", "closed");
			jsoArr.add(json);
		}else if(id.startsWith("root")){
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
		return ajaxText(jsoArr);*/
	//}
	 
}

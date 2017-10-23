package com.yunwang.action;


import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysDepartMent;
import com.yunwang.service.SysUserService;
import com.yunwang.util.action.AbstractLoginAction;



@Action(value = "sysDepartMentAction", results = {
		@Result(name="index",location="/WEB-INF/web/sysDepartMent/index.jsp"),
		@Result(name="preAddOrEdit",location="/WEB-INF/web/sysDepartMent/preAddOrEdit.jsp")
	}
)
public class SysDepartMentAction extends AbstractLoginAction{
	
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysUserService sysUserService;
	
	private SysDepartMent sysDepartMent;
	 
	private String id;
	
	private String operateType;
	
	private String nodeId;
	
	/**
	 * 部门管理主页面
	 */
	public String execute(){
		return "index";
	}	
	
	
	
	/**
	 * @date 2017.10.23
	 * @author KXL
	 * @return
	 * <p>部门列表结构树</p>
	 */
	public String findTree(){
		JSONArray jsoArr=new JSONArray();
		JSONObject json=null;
		if(null==id){
			json=new JSONObject();
			json.put("id","root");
			json.put("text","部门列表");
			json.put("state", "closed");
			jsoArr.add(json);
		}else if(id.startsWith("root")){
			List<SysDepartMent> listSysDepartMent = sysUserService.findDepartMentByParentId(0);
			JSONObject jso=null;
			for(SysDepartMent departMent:listSysDepartMent){
			    jso=new JSONObject();
				jso.put("id",departMent.getId());
				jso.put("text",departMent.getCode()+","+departMent.getName());
				jso.put("state", "open");
				jso.put("attributes", JSONObject.fromObject(departMent));
				jsoArr.add(jso);
			}
		}else{
			List<SysDepartMent> listSysDepartMent = sysUserService.findDepartMentByParentId(Integer.parseInt(id));
			JSONObject jso=null;
			for(SysDepartMent departMent:listSysDepartMent){
			    jso=new JSONObject();
				jso.put("id",departMent.getId());
				jso.put("text",departMent.getCode()+","+departMent.getName());
				jso.put("state", "open");
				jso.put("attributes", JSONObject.fromObject(departMent));
				jsoArr.add(jso);
			}
		}
		return ajaxText(jsoArr);
	}

	
	/**
	 * <p>
	 * 1.部门列表-跳转添加部门
	 * 2.部门列表-跳转修改部门
	 * </p>
	 */
	public String preAddOrEdit(){
		//if("del".equals(operateType)){
			
		//}
		//sysRole=sysUserService.findRoleByRoleId(sysRole.getId());
		return "preAddOrEdit";
	}
	
	
	/**
	 * @date 2016-12-12
	 * @author LKX
	 * @return
	 * <p>更新修改后角色名称</p>
	 */
/*	public String update(){
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
			
	}*/
	
	/**
	 * <p>部门列表-保部门信息</p>
	 */
	public String add(){
		if(sysUserService.isExistDepartMent(sysDepartMent.getCode())){
			sysUserService.saveSysDepartMent(sysDepartMent);
			return success(getText("添加成功"),JSONObject.fromObject(sysDepartMent));
		}else{
			return error(getText("该部门已存在"));
		}
	}
	
	/**
	 * <p>关联模块-删除角色信息</p>
	 */
/*	public String delete(){
		try{
			sysRole = sysUserService.findRoleByRoleId(sysRole.getId());
			sysUserService.deleteSysRole(sysRole);							
			return success("删除成功",JSONObject.fromObject(sysRole));
		}catch(Exception e){
			e.printStackTrace();
			return error("删除失败");
		}
	}*/


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	 
	
}

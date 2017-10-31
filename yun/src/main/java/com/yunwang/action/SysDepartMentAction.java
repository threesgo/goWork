package com.yunwang.action;


import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import antlr.StringUtils;

import com.yunwang.model.pojo.SysDepartMent;
import com.yunwang.model.pojo.SysPosition;
import com.yunwang.service.SysUserService;
import com.yunwang.util.action.AbstractLoginAction;
import com.yunwang.util.string.MyStringUtil;



@Action(value = "sysDepartMentAction", results = {
		@Result(name="index",location="/WEB-INF/web/sysDepartMent/index.jsp"),
		@Result(name="preAddOrEdit",location="/WEB-INF/web/sysDepartMent/preAddOrEdit.jsp"),
		@Result(name="preDepartMent",location="/WEB-INF/web/sysDepartMent/preDepartMent.jsp"),
		@Result(name="prePosition",location="/WEB-INF/web/sysDepartMent/prePosition.jsp")
	}
)
public class SysDepartMentAction extends AbstractLoginAction{
	
	
	private static final long serialVersionUID = 1L;
	private final static Logger LOG =Logger.getLogger(SysDepartMentAction.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	private SysDepartMent sysDepartMent;
	private String id;
	private String ids;
	
	private String nodeId;
	private String departMentId;
	private String jsonStr;
	private String noAll;
	/**
	 * 部门管理主页面
	 */
	public String execute(){
		return "index";
	}	
	
	/**
	 * 
	 * @Description: 查询所有部门
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author KXL
	 * @date 2017-10-27
	 */
	public String findAll(){
		JSONArray arr = new JSONArray();
		JSONObject obj = null;
		List<SysDepartMent> list = sysUserService.findAllDepartMent();
		if(MyStringUtil.isBlank(noAll)){
			obj = new JSONObject();
			obj.put("id", "");
			obj.put("userDepartMent", "全部");
			obj.put("selected", true);
			arr.add(obj);
		}
		
		for(SysDepartMent departMent:list){
			obj = new JSONObject();
			obj.put("id", departMent.getId());
			obj.put("userDepartMent",departMent.getName());
			arr.add(obj);
		}
		return ajaxText(arr);
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
			JSONObject obj = new JSONObject();
			obj.put("id",-1);
			json.put("attributes",obj);
			jsoArr.add(json);
		}else if(id.startsWith("root")){
			List<SysDepartMent> listSysDepartMent = sysUserService.findDepartMentByParentId(0);
			JSONObject jso=null;
			for(SysDepartMent departMent:listSysDepartMent){
			    jso=new JSONObject();
				jso.put("id",departMent.getId());
				jso.put("text",departMent.getCode()+","+departMent.getName());
				jso.put("state", "closed");
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
				jso.put("state", "closed");
				jso.put("attributes", JSONObject.fromObject(departMent));
				jsoArr.add(jso);
			}
		}
		return ajaxText(jsoArr);
	}
	
	/**
	 * <p>
	 * 	  root-所有部门列表页面
	 * </p>
	 */
	public String preDepartMent(){
		return "preDepartMent";
	}
	
	/**
	 * <p>
	 * 	    所在部门职位列表页面
	 * </p>
	 */
	public String prePosition(){
		return "prePosition";
	}
	
	/**
	 * <p>
	 * 部门管理--部门下的职位
	 * </p>
	 */
	public String findPosition(){
		List<SysPosition> list = sysUserService.findPositionByDepartMentId(Integer.parseInt(departMentId));
		return ajaxJSONArr(list);
	}
	/**
	 * <p>
	 * root-所有部门列表
	 * </p>
	 */
	public String listDepartMent(){
		List<SysDepartMent> list = sysUserService.findAllDepartMent();
		return ajaxJSONArr(list);
	}
	
	public String saveOrUpdatePositionGrid(){
		try{
			JSONObject obj = JSONObject.fromObject(jsonStr);
			sysUserService.saveOrUpdatePositionGrid(obj,Integer.parseInt(departMentId));
			return success("行数据保存成功!",obj);
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("行数据保存失败!");
		}
	}
	
	public String deletePosition(){
		try{
			sysUserService.deletePositionByIds(ids);
			return success("删除成功");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("删除失败");
		}
	}
	/**
	 * <p>
	 * 1.部门列表-跳转添加部门
	 * 2.部门列表-跳转修改部门
	 * </p>
	 */
	public String preAddOrEdit(){
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
	public String saveOrUpdate(){
		if(sysDepartMent.getId() == null){
			if(sysUserService.isExistDepartMent(sysDepartMent.getCode())){
				sysUserService.saveSysDepartMent(sysDepartMent);
				return success(getText("添加成功"),JSONObject.fromObject(sysDepartMent));
			}else{
				return error(getText("该部门已存在"));
			}
		}else{
			return null;
		}
	}
	
	/**
	 * <p>删除部门信息</p>
	 */
	public String deleteDepartMent(){
		try{
			List<SysDepartMent> list = sysUserService.findDepartMentByParentId(sysDepartMent.getId());
			if(list.size()>0){
				return error("请先删除该部门下的子部门");
			}
			sysUserService.deleteDepartMentById(sysDepartMent.getId());							
			return success("删除成功");
		}catch(Exception e){
			e.printStackTrace();
			return error("删除失败");
		}
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public SysDepartMent getSysDepartMent() {
		return sysDepartMent;
	}

	public void setSysDepartMent(SysDepartMent sysDepartMent) {
		this.sysDepartMent = sysDepartMent;
	}

	public String getDepartMentId() {
		return departMentId;
	}

	public void setDepartMentId(String departMentId) {
		this.departMentId = departMentId;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getNoAll() {
		return noAll;
	}

	public void setNoAll(String noAll) {
		this.noAll = noAll;
	}
	
}

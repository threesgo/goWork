package com.yunwang.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.util.action.AbstractLoginAction;

@Action(
	value = "resourceTypeAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resourceType/index.jsp"),
		@Result(name = "add",location="/WEB-INF/web/resourceType/add.jsp"),
		@Result(name = "edit",location="/WEB-INF/web/resourceType/edit.jsp"),
		@Result(name = "info",location="/WEB-INF/web/resourceType/info.jsp"),
		@Result(name = "childrenPage",location="/WEB-INF/web/resourceType/childrenPage.jsp")
	}
)
public class ResourceTypeAction extends AbstractLoginAction{

	/*
	 * @date 2017-9-27
	 * @author YBF
	 * TODO  系统资源类型管理
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysResourceTypeService sysResourceTypeService;
	
	private SysRsRcCatalog sysRsRcCatalog;
	private String id;
	
	@Override
	public String execute() throws Exception {
		return "index";
	} 
	
	/**
	 * @return  
	 * <p>查询树结构</p>
	 */
	public String findTree(){
		JSONArray jsonArr=new JSONArray();
		if (id==null) {
			JSONObject json=new JSONObject();
			json.put("id", "root");
			json.put("text", "产品类别");
			JSONObject obj=new JSONObject();
			obj.put("id",0);
			json.put("attributes",obj);
			json.put("state", "closed");
			jsonArr.add(json);
		}else{
			List<SysRsRcCatalog> sysRcRsrcOrgList;
			if("root".equals(id)){
				sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(0);
			}else{
				int _id=Integer.parseInt(id.substring(3,id.length()));
				sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(_id);
			}
			for(SysRsRcCatalog s:sysRcRsrcOrgList){
				jsonArr.add(getJson(s));
			}
		}
		return ajaxText(jsonArr);
	}
	
	private JSONObject getJson(SysRsRcCatalog sysRcRsrcOrg){
		JSONObject json=new JSONObject();
		json.put("id","org"+sysRcRsrcOrg.getId());
		json.put("text",sysRcRsrcOrg.getCatalogName());
		json.put("attributes", JSONObject.fromObject(sysRcRsrcOrg));
		json.put("state", "closed");
		return json;
	}
	
	/**
	 * @return 查看类型信息
	 */
	public String info(){
		//基本信息
		sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
				
		//继承属性
				
		//本身属性
		
		return "info";
	}
	
	
	/**
	 * @return  查询子集列表
	 */
	public String childrenPage(){
		return "childrenPage";
	}
	
	
	/**
	 * @return  查询子集列表
	 */
	public String childrenList(){
		List<SysRsRcCatalog> sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(sysRsRcCatalog.getId());
		return ajaxJSONArr(sysRcRsrcOrgList);
	}
	
	
	/**
	 * @return 基本信息
	 */
	public String attrListPage(){
		return "attrListPage";
	}
	
	/**
	 * @return 属性列表
	 */
	public String attrList(){
		List<SysRsRcCatalog> sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(sysRsRcCatalog.getId());
		return ajaxJSONArr(sysRcRsrcOrgList);
	}
	
	
	
	public String addResourceType(){
		return "add";
	}
	
	/**
	 * @return  保存类型
	 */
	public String save(){
		try{
			sysRsRcCatalog.setCatalogStatus(1);
			sysRsRcCatalog.setOrderNo(sysResourceTypeService.getMaxOrder(sysRsRcCatalog.getParentId()));			
			sysResourceTypeService.saveRsRcCatalog(sysRsRcCatalog);
			return success("操作成功!");
		}catch(Exception e){
			return error("操作失败!");
		}
	}
	
	public String delete(){
		try{
			sysResourceTypeService.deleteRsRcCatalog(sysRsRcCatalog);
			return success("操作成功!");
		}catch(Exception e){
			return error("操作失败!");
		}
	}
	
	public String update(){
		try{
			return success("操作成功!");
		}catch(Exception e){
			return error("操作失败!");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysRsRcCatalog getSysRsRcCatalog() {
		return sysRsRcCatalog;
	}

	public void setSysRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		this.sysRsRcCatalog = sysRsRcCatalog;
	}
	
}

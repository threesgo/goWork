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
		@Result(name = "saveOrUpdateTypePage",location="/WEB-INF/web/resourceType/saveOrUpdateTypePage.jsp"),
		@Result(name = "info",location="/WEB-INF/web/resourceType/info.jsp"),
		@Result(name = "childrenPage",location="/WEB-INF/web/resourceType/childrenPage.jsp"),
		@Result(name = "attrsPage",location="/WEB-INF/web/resourceType/attrsPage.jsp"),
		@Result(name = "addTypeAttr",location="/WEB-INF/web/resourceType/addTypeAttr.jsp")
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
		json.put("text",sysRcRsrcOrg.getCatalogCode()+","+sysRcRsrcOrg.getCatalogName());
		json.put("attributes", JSONObject.fromObject(sysRcRsrcOrg));
		json.put("state", "closed");
		return json;
	}
	
	/**
	 * @return 查看类型信息
	 */
	public String info(){
		return "info";
	}
	
	/**
	 * @return 查找继承属性
	 */
	public String findExtendsAttr(){
		return ajaxJSONArr(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
	}
	
	/**
	 * @return 查找本身属性
	 */
	public String findAttr(){
		return ajaxJSONArr(sysResourceTypeService.findAttr(sysRsRcCatalog));
	}
	
	/**
	 * @return 新增类型属性
	 */
	public String addTypeAttr(){
		return "addTypeAttr";
	} 
	
	/**
	 * @return  获取类别需要编辑的属性，打包成json数组  （转换model类的属性，排除不可编辑行，通过国际化显示名称）
	 */
	public String infoData(){
		sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
		JSONArray jsonArr=new JSONArray();
		
		JSONObject json_name=new JSONObject();
		json_name.put("attrName", "类型名称");
		json_name.put("value", sysRsRcCatalog.getCatalogName());
		jsonArr.add(json_name);
		
		JSONObject json_code=new JSONObject();
		json_code.put("attrName","类型代号");
		json_code.put("value", sysRsRcCatalog.getCatalogCode());
		jsonArr.add(json_code);
		
		JSONObject json_type=new JSONObject();
		json_type.put("attrName","类型类别");
		json_type.put("value", sysRsRcCatalog.getCatalogType()==1?"产品":"工人");
		jsonArr.add(json_type);
		return ajaxText(jsonArr);
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
	public String attrsPage(){
		return "attrsPage";
	}
	
	/**
	 * @return 属性列表
	 */
	public String attrList(){
		List<SysRsRcCatalog> sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(sysRsRcCatalog.getId());
		return ajaxJSONArr(sysRcRsrcOrgList);
	}
	
	/**
	 * @return 新增资源属性
	 */
	public String saveOrUpdateTypePage(){
		if(null != sysRsRcCatalog.getId()){
			sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
		}
		return "saveOrUpdateTypePage";
	}
	
	/**
	 * @return  保存类型
	 */
	public String saveOrUpdateType(){
		try{
			if(null!=sysRsRcCatalog.getId()){
				SysRsRcCatalog updateSysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
				updateSysRsRcCatalog.setCatalogCode(sysRsRcCatalog.getCatalogCode());
				updateSysRsRcCatalog.setCatalogName(sysRsRcCatalog.getCatalogName());
				updateSysRsRcCatalog.setCatalogType(sysRsRcCatalog.getCatalogType());
				sysResourceTypeService.saveOrUpdateRsRcCatalog(updateSysRsRcCatalog);
			}else{
				sysRsRcCatalog.setCatalogStatus(1);
				sysRsRcCatalog.setOrderNo(sysResourceTypeService.getMaxOrder(sysRsRcCatalog.getParentId()));			
				sysResourceTypeService.saveOrUpdateRsRcCatalog(sysRsRcCatalog);
			}
			return success("操作成功!",JSONObject.fromObject(sysRsRcCatalog));
		}catch(Exception e){
			return error("操作失败!");
		}
	}
	
	public String saveOrUpdateAttrPage(){
		return "saveOrUpdateAttrPage";
	}
	
	public String saveOrUpdateAttr(){
		return null;
	}
	
	/**
	 * @return 删除资源属性
	 */
	public String delete(){
		try{
			sysResourceTypeService.deleteRsRcCatalog(sysRsRcCatalog);
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

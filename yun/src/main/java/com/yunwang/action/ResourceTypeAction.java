package com.yunwang.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcBaseData;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.SysRcBaseDataTypeUtil;
import com.yunwang.util.action.AbstractLoginAction;

@Action(
	value = "resourceTypeAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resourceType/index.jsp"),
		@Result(name = "saveOrUpdateTypePage",location="/WEB-INF/web/resourceType/saveOrUpdateTypePage.jsp"),
		@Result(name = "info",location="/WEB-INF/web/resourceType/info.jsp"),
		@Result(name = "childrenPage",location="/WEB-INF/web/resourceType/childrenPage.jsp"),
		@Result(name = "attrsPage",location="/WEB-INF/web/resourceType/attrsPage.jsp"),
		@Result(name = "saveOrUpdateAttrPage",location="/WEB-INF/web/resourceType/saveOrUpdateAttrPage.jsp")
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
	private SysRsRcAttribCatalog sysRsRcAttribCatalog;
	private String id;
	private List<SysRsRcBaseData> dataTypeList;
	private List<SysRsRcBaseData> controlTypeList;
	private List<SysRsRcBaseData> unitGroupList;
	private List<SysRsRcBaseData> unitList;
	private List<SysDataDictionary> catalogTypeList;
	
	
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
		json_type.put("value",BaseDataDictionaryUtil.valueMap.get(1).get(sysRsRcCatalog.getCatalogType().toString()).getName());
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
		catalogTypeList = BaseDataDictionaryUtil.baseDataMap.get(1);
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
		dataTypeList = SysRcBaseDataTypeUtil.dataTypeList;
		controlTypeList = SysRcBaseDataTypeUtil.controlTypelist;
		unitList = SysRcBaseDataTypeUtil.unitTypelist;
		unitGroupList = SysRcBaseDataTypeUtil.unitGroupList;
		if(null!=sysRsRcAttribCatalog.getId()){
			sysRsRcAttribCatalog = sysResourceTypeService.getSysRsRcAttribCatalog(sysRsRcAttribCatalog.getId());
		}
		return "saveOrUpdateAttrPage";
	}
	
	public String findSysRcBaseDataTypeByGroup(){
		List<SysRsRcBaseData> list = sysResourceTypeService.findSysRcBaseDataTypeByGroup(id);
		return ajaxJSONArr(list);
	}
	
	/**
	 * @return 更新属性
	 */
	public String saveOrUpdateAttr(){
		try{
			if(null != sysRsRcAttribCatalog.getId()){
				SysRsRcAttribCatalog dbSysRsRcAttribCatalog = sysResourceTypeService.getSysRsRcAttribCatalog(sysRsRcAttribCatalog.getId());
				dbSysRsRcAttribCatalog.setRsrcAttribCode(sysRsRcAttribCatalog.getRsrcAttribCode());
				dbSysRsRcAttribCatalog.setRsrcAttribName(sysRsRcAttribCatalog.getRsrcAttribName());
				dbSysRsRcAttribCatalog.setControlTypeId(sysRsRcAttribCatalog.getControlTypeId());
				dbSysRsRcAttribCatalog.setDataTypeId(sysRsRcAttribCatalog.getDataTypeId());
				dbSysRsRcAttribCatalog.setDataLength(sysRsRcAttribCatalog.getDataLength());
				dbSysRsRcAttribCatalog.setUnitId(sysRsRcAttribCatalog.getUnitId());
				dbSysRsRcAttribCatalog.setShowInListView(sysRsRcAttribCatalog.getShowInListView());
				dbSysRsRcAttribCatalog.setShowInFinder(sysRsRcAttribCatalog.getShowInFinder());
				dbSysRsRcAttribCatalog.setDataPrecision(sysRsRcAttribCatalog.getDataPrecision());
				dbSysRsRcAttribCatalog.setDefaultValue(sysRsRcAttribCatalog.getDefaultValue());
				dbSysRsRcAttribCatalog.setOrderNo(sysRsRcAttribCatalog.getOrderNo());
			}else{
				sysResourceTypeService.saveOrUpdateSysRsRcAttribCatalog(sysRsRcAttribCatalog);
			}
			return success("操作成功!");
		}catch(Exception e){
			return error("操作失败!");
		}
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

	public List<SysRsRcBaseData> getDataTypeList() {
		return dataTypeList;
	}

	public void setDataTypeList(List<SysRsRcBaseData> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}

	public List<SysRsRcBaseData> getControlTypeList() {
		return controlTypeList;
	}

	public void setControlTypeList(List<SysRsRcBaseData> controlTypeList) {
		this.controlTypeList = controlTypeList;
	}

	public List<SysRsRcBaseData> getUnitGroupList() {
		return unitGroupList;
	}

	public void setUnitGroupList(List<SysRsRcBaseData> unitGroupList) {
		this.unitGroupList = unitGroupList;
	}

	public List<SysRsRcBaseData> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<SysRsRcBaseData> unitList) {
		this.unitList = unitList;
	}

	public List<SysDataDictionary> getCatalogTypeList() {
		return catalogTypeList;
	}

	public void setCatalogTypeList(List<SysDataDictionary> catalogTypeList) {
		this.catalogTypeList = catalogTypeList;
	}

	public SysRsRcAttribCatalog getSysRsRcAttribCatalog() {
		return sysRsRcAttribCatalog;
	}

	public void setSysRsRcAttribCatalog(SysRsRcAttribCatalog sysRsRcAttribCatalog) {
		this.sysRsRcAttribCatalog = sysRsRcAttribCatalog;
	}
}

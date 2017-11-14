package com.yunwang.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.model.pojo.SysSupplierCatalog;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.service.SysSupplierService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;
import com.yunwang.util.collection.CollectionUtil;
@Action(
	value = "sysSupplierAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/sysSupplier/index.jsp"),
		@Result(name = "relationCatalog",location="/WEB-INF/web/sysSupplier/relationCatalog.jsp"),
	}
)
public class SysSupplierAction extends AbstractLoginAction{

	private final static Logger LOG =Logger.getLogger(SysSupplierAction.class);
	/*
	 * @date 2017-10-17
	 * @author YBF
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysSupplierService sysSupplierService;
	@Autowired
	private SysResourceTypeService sysResourceTypeService;
	
	private Map<String,Object> hashMap;
	private SysSupplier sysSupplier;
	private String ids;
	private String jsonStr;
	private List<SysDataDictionary> flowList;
	
	@Override
	public String execute(){
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		hashMap = new HashMap<String,Object>();
		hashMap.put("flowArr", JSONArray.fromObject(BaseDataDictionaryUtil.baseDataMap.get(4)));
		JSONObject obj = new JSONObject();
		for(SysDataDictionary dictionary:flowList){
			obj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("flowObj",obj);
		return "index";
	} 
	
	
	public String listData(){
		JSONObject obj=new JSONObject();
		Pager<SysSupplier> pager = sysSupplierService.findAll(page,rows,
				JSONObject.fromObject(jsonStr));
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
  			obj.put("total", pager.getTotalRows());
  			arr = JSONArray.fromObject(pager.getData());
  	    }else{
  	        obj.put("total",0); 
  	    }
		obj.put("rows", arr);
  		return ajaxText(JSONObject.fromObject(obj).toString());
	}
	
	/**
	 * @date 2017-10-10
	 * @author YBF
	 * @return
	 * <p>保存表格行数据</p>
	 */
	public String saveOrUpdateSupplierGrid(){
		try{
			JSONObject obj = JSONObject.fromObject(jsonStr);
			sysSupplierService.saveOrUpdateSupplierGrid(obj);
			return success("行数据保存成功!",obj);
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("行数据保存失败!",e);
		}
	}
	
	public String deleteSupplier(){
		try{
			sysSupplierService.deleteSupplier(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	
	public String relationCatalog(){
		return "relationCatalog";
	}
	
	/**
	 * @date 2017-11-7
	 * @author YBF
	 * @return
	 * <p>查询供应商关联的类别</p>
	 */
	public String supplierResourceTypeTree(){
		List<SysSupplierCatalog> sysSuppliers = sysSupplierService.findAllRelCatalogSupplier(sysSupplier.getId());
		Map<Integer,SysSupplierCatalog> map = CollectionUtil.listToMap(sysSuppliers,"catalogId");
		return ajaxText(getChildren(0,map));
	}
	
	private JSONArray getChildren(Integer parentId,Map<Integer,SysSupplierCatalog> map){
		List<SysRsRcCatalog> sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(parentId);
		JSONArray arr = new JSONArray();
		for(SysRsRcCatalog child:sysRcRsrcOrgList){
			arr.add(getJson(child,map));
		}
		return arr;
	}
	
	private JSONObject getJson(SysRsRcCatalog sysRcRsrcOrg,Map<Integer,SysSupplierCatalog> map){
		JSONObject json=new JSONObject();
		json.put("id",sysRcRsrcOrg.getId());
		json.put("text",sysRcRsrcOrg.getCatalogName());//sysRcRsrcOrg.getCatalogCode()+","+sysRcRsrcOrg.getCatalogName());
		json.put("attributes", JSONObject.fromObject(sysRcRsrcOrg));
		JSONArray arr = getChildren(sysRcRsrcOrg.getId(),map);
		json.put("children", arr);
		json.put("state", "open");
		if(arr.size() == 0 && null != map.get(sysRcRsrcOrg.getId())){
			json.put("checked",true);
		}
		return json;
	}
	
	public String updateRelationCatalog(){
		try{
			sysSupplierService.updateRelationCatalog(ids,sysSupplier.getId());
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}

	public Map<String, Object> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<String, Object> hashMap) {
		this.hashMap = hashMap;
	}

	public SysSupplier getSysSupplier() {
		return sysSupplier;
	}

	public void setSysSupplier(SysSupplier sysSupplier) {
		this.sysSupplier = sysSupplier;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}


	public List<SysDataDictionary> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<SysDataDictionary> flowList) {
		this.flowList = flowList;
	}
}

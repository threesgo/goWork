package com.yunwang.action;

import java.util.ArrayList;
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
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.service.SysResourceService;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.service.SysSupplierService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;
import com.yunwang.util.collection.CollectionUtil;
import com.yunwang.util.string.StringBufferByCollectionUtil;

@Action(
	value = "resourceAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resource/index.jsp"),
		@Result(name = "resourceList",location="/WEB-INF/web/resource/resourceList.jsp"),
		@Result(name = "addPage",location="/WEB-INF/web/resource/add.jsp"),
		@Result(name="exportResource",type="stream",
				params={"encode","true","contentType","application/vnd.ms-excel;charset=UTF-8",
				  "inputName","exportResourceStream","contentDisposition","attachment;filename=${exportResourceFileName}"}),
		@Result(name="importResourcePage",location="/WEB-INF/web/resource/importResourcePage.jsp")
	}
)
public class ResourceAction extends AbstractLoginAction{

	private final static Logger LOG =Logger.getLogger(ResourceAction.class);
	/*
	 * @date 2017-9-27
	 * @author YBF
	 * TODO 系统资源管理 (产品、工人)
	 */
	private static final long serialVersionUID = 1L;
	
	private SysRsRcCatalog sysRsRcCatalog;
	
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysResourceTypeService sysResourceTypeService;
	@Autowired
	private SysSupplierService sysSupplierService;
	
	private List<SysRsRcAttribCatalog> attribCatalogs;
	private String ids;
	private SysResource sysResource;
	private List<SysDataDictionary> flowList;
	private Map<String,Object> hashMap;
	private String resourceJsonStr;
	private List<SysSupplier> sysSuppliers;
	
	@Override
	public String execute() throws Exception {
		return "index";
	} 
	
	public String resourceList(){
		//sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
		attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
		attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
		attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog));
		
		hashMap = new HashMap<String,Object>();
		
//		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
//		hashMap = new HashMap<String,Object>();
//		hashMap.put("flowArr", JSONArray.fromObject(flowList));
//		JSONObject obj = new JSONObject();
//		for(SysDataDictionary dictionary:flowList){
//			obj.put(dictionary.getValue(), dictionary.getName());
//		}
//		hashMap.put("flowObj",obj);
		
		if(0 != sysRsRcCatalog.getId()){
			sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
			sysSuppliers = sysSupplierService.findByWorkType(sysRsRcCatalog.getCatalogType());
		}else{
			sysSuppliers =sysSupplierService.findByWorkType(null);
		}
		
		JSONObject supplierObj = new JSONObject();
		for(SysSupplier sysSupplier:sysSuppliers){
			supplierObj.put(sysSupplier.getId(),sysSupplier.getName());
		}
		JSONArray arr = JSONArray.fromObject(sysSuppliers);
		JSONObject firstSelect = new JSONObject();
		firstSelect.put("id","0");
		firstSelect.put("name","请选择");
		arr.add(0,firstSelect);
		hashMap.put("supplierObj",supplierObj);
		hashMap.put("supplierArr",arr);
		
		return "resourceList";
	}
	
	/**
	 * @return  //每个资源类型下面显示当前类型下的资源 
	 */
	@SuppressWarnings("unchecked")
	public String resourceListData(){
		JSONObject obj=new JSONObject();
		Pager<SysResource> pager = sysResourceService.findByRsRcCatalogId(sysRsRcCatalog.getId(),page,rows,
				JSONObject.fromObject(resourceJsonStr));
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
			List<SysResource> sysResources = (List<SysResource>) pager.getData();
  			attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
  			attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
  			attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog));
  			
  			List<SysRsRcAttrib> sysRsRcAttribs = sysResourceService.findSysRsRcAttribByResourceIds(
  					StringBufferByCollectionUtil.convertCollection(sysResources,"id"));
  			Map<Integer,Map<Integer,SysRsRcAttrib>> map = conToMap(sysRsRcAttribs);
  			
  			
  			List<SysSupplier> sysSuppliers;
  			if(0 != sysRsRcCatalog.getId()){
  				sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
  				sysSuppliers = sysSupplierService.findByWorkType(sysRsRcCatalog.getCatalogType());
  			}else{
  				sysSuppliers =sysSupplierService.findByWorkType(null);
  			}
  			Map<Integer,SysSupplier> supplierMap = CollectionUtil.listToMap(sysSuppliers,"id");
  			 
  			for(SysResource resource:sysResources){
  				if(null != resource.getSupplierId()&&0!=resource.getSupplierId()){
  					SysSupplier supplier = supplierMap.get(resource.getSupplierId());
  	  				if(null != supplier){
  	  					resource.setSupplier(supplier.getContact());
  	  					resource.setSupplierName(supplier.getName());
  	  					resource.setSupplierPhone(supplier.getPhoneNum());
  	  					resource.setSupplierTel(supplier.getTelNum());
  	  					resource.setSupplierAddress(supplier.getAddress());
  	  				}
  				}
  				
  				JSONObject newObj = JSONObject.fromObject(resource);
  				Map<Integer,SysRsRcAttrib> resourceMap = map.get(resource.getId());
  				if(null != resourceMap){
  					for(SysRsRcAttribCatalog attrCatalog : attribCatalogs){
  						SysRsRcAttrib attrib = resourceMap.get(attrCatalog.getId());
  						newObj.put(attrCatalog.getId(),null != attrib ?attrib.getRsrcAttribValue():"");
  					}
  				}
  				arr.add(newObj);
  			}
  			obj.put("total", pager.getTotalRows());
  	    }else{
  	        obj.put("total",0); 
  	    }
		obj.put("rows", arr);
  		return ajaxText(JSONObject.fromObject(obj).toString());
	}
	
	private Map<Integer,Map<Integer,SysRsRcAttrib>> conToMap(List<SysRsRcAttrib> sysRsRcAttribs){
		Map<Integer,Map<Integer,SysRsRcAttrib>> map = new HashMap<Integer,Map<Integer,SysRsRcAttrib>>();
		for(SysRsRcAttrib attrib:sysRsRcAttribs){
			Map<Integer,SysRsRcAttrib> childMap = map.get(attrib.getRsrcId());
			if(null!=childMap){
				childMap.put(attrib.getRsraAttribCatalogId(), attrib);
			}else{
				childMap = new HashMap<Integer,SysRsRcAttrib>();
				childMap.put(attrib.getRsraAttribCatalogId(), attrib);
				map.put(attrib.getRsrcId(), childMap);
			}
		}
		return map;
	}
	
	public String addPage(){
		attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
		attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
		attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog));
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		return "addPage";
	}
	
	/**
	 * @date 2017-10-10
	 * @author YBF
	 * @return
	 * <p>保存表格行数据</p>
	 */
	public String saveOrUpdateResourceGrid(){
		try{
			JSONObject obj = JSONObject.fromObject(resourceJsonStr);
			sysResourceService.saveOrUpdateResourceGrid(obj,sysRsRcCatalog);
			return success("行数据保存成功!",obj);
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("行数据保存失败!");
		}
	}
	
	/**
	 * @return 删除资源
	 */
	public String deleteResource(){
		try{
			sysResourceService.deleteResource(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String save(){
		//属性空值直接过滤掉
		try{
			sysResourceService.save(sysResource);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public SysResource getSysResource() {
		return sysResource;
	}

	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}

	public SysRsRcCatalog getSysRsRcCatalog() {
		return sysRsRcCatalog;
	}

	public void setSysRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		this.sysRsRcCatalog = sysRsRcCatalog;
	}

	public List<SysRsRcAttribCatalog> getAttribCatalogs() {
		return attribCatalogs;
	}

	public void setAttribCatalogs(List<SysRsRcAttribCatalog> attribCatalogs) {
		this.attribCatalogs = attribCatalogs;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<SysDataDictionary> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<SysDataDictionary> flowList) {
		this.flowList = flowList;
	}

	public Map<String, Object> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<String, Object> hashMap) {
		this.hashMap = hashMap;
	}

	public String getResourceJsonStr() {
		return resourceJsonStr;
	}

	public void setResourceJsonStr(String resourceJsonStr) {
		this.resourceJsonStr = resourceJsonStr;
	}

	public List<SysSupplier> getSysSuppliers() {
		return sysSuppliers;
	}

	public void setSysSuppliers(List<SysSupplier> sysSuppliers) {
		this.sysSuppliers = sysSuppliers;
	}
}

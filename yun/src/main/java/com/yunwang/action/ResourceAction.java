package com.yunwang.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceService;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;
import com.yunwang.util.string.StringBufferByCollectionUtil;

@Action(
	value = "resourceAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resource/index.jsp"),
		@Result(name = "resourceList",location="/WEB-INF/web/resource/resourceList.jsp"),
		@Result(name = "addPage",location="/WEB-INF/web/resource/add.jsp")
	}
)
public class ResourceAction extends AbstractLoginAction{

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
	
	private List<SysRsRcAttribCatalog> attribCatalogs;
	private String ids;
	private SysResource sysResource;
	private List<SysDataDictionary> flowList;
	
	@Override
	public String execute() throws Exception {
		return "index";
	} 
	
	public String resourceList(){
		attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
		attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
		attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog));
		return "resourceList";
	}
	
	/**
	 * @return  //每个资源类型下面显示当前类型下的资源 
	 */
	public String resourceListData(){
		List<SysResource> sysResources = sysResourceService.findByRsRcCatalogId(sysRsRcCatalog.getId());
		attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
		attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
		attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog));
		
		List<SysRsRcAttrib> sysRsRcAttribs = sysResourceService.findSysRsRcAttribByResourceIds(
				StringBufferByCollectionUtil.convertCollection(sysResources,"id"));
		 Map<Integer,Map<Integer,SysRsRcAttrib>> map = conToMap(sysRsRcAttribs);
		 
		JSONArray arr = new JSONArray();
		for(SysResource resource:sysResources){
			JSONObject obj = JSONObject.fromObject(resource);
			obj.put("workTypeName", BaseDataDictionaryUtil.valueMap.get(4).get(resource.getWorkType()).getName());
			Map<Integer,SysRsRcAttrib> resourceMap = map.get(resource.getId());
			if(null != resourceMap){
				for(SysRsRcAttribCatalog attrCatalog : attribCatalogs){
					SysRsRcAttrib attrib = resourceMap.get(attrCatalog.getId());
					obj.put(attrCatalog.getRsrcAttribCode(),null != attrib ?attrib.getRsrcAttribValue():"");
				}
			}
			arr.add(obj);
		}
		return ajaxText(arr.toString());
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
	
	public String save(){
		//属性空值直接过滤掉
		try{
			sysResourceService.save(sysResource);
			return success("操作成功!");
		}catch(Exception e){
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
}

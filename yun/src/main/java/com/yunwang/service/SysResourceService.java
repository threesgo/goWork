package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.model.pojo.SysRsRcPackage;



/**
 * @author YBF
 * @date 2017-9-28
 * <p>资源管理</p>
 */
public interface SysResourceService {

	List<SysResource> findByRsRcCatalogId(Integer catalogId);

	List<SysRsRcAttrib> findSysRsRcAttribByResourceIds(String resourceIds);

	void save(SysResource sysResource);

	void saveOrUpdateResourceGrid(JSONObject rowData,SysRsRcCatalog sysRsRcCatalog);

	Pager<SysResource> findByRsRcCatalogId(Integer rsRcCatalogId, int page, int rows, JSONObject seachJson);

	void deleteResource(String ids);

	void saveImportResources(List<SysResource> resourceList,SysRsRcCatalog sysRsRcCatalog);

	List<SysResource> findParentByRsRcCatalogId(Integer parentId);

	void releaseResource(String ids);

	Pager<SysResourceRel> findRelResources(SysRsRcPackage sysRsRcPackage,int page, int rows,
			JSONObject seachObj);

	void addRelResourceToPackage(Integer packageId,String ids);

	SysResourceRel getRelResource(Integer relResourceId);

	JSONArray getRelResourceInfo(Integer relResourceId, List<SysRsRcAttribCatalog> attrList);

	List<SysResourceRel> findByFlowId(Integer flowId);

	List<SysResourceRel> findByOrderId(Integer orderId);

	Pager<SysResourceRel> selectResourceData(Integer flowId,int page, int rows, JSONObject seachJson);

	void update(SysResource sysResource);

	SysResource get(Integer id);
}

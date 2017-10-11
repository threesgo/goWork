package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcCatalog;



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

}

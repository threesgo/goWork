package com.yunwang.service;

import java.util.List;

import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;



/**
 * @author YBF
 * @date 2017-9-28
 * <p>资源管理</p>
 */
public interface SysResourceService {

	List<SysResource> findByRsRcCatalogId(Integer catalogId);

	List<SysRsRcAttrib> findSysRsRcAttribByResourceIds(String resourceIds);

	void save(SysResource sysResource);

}

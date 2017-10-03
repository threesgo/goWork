package com.yunwang.service;

import java.util.List;

import com.yunwang.model.pojo.SysRsRcCatalog;

public interface SysResourceTypeService {

	void saveRsRcCatalog(SysRsRcCatalog sysRsRcCatalog);

	void deleteRsRcCatalog(SysRsRcCatalog sysRsRcCatalog);

	List<SysRsRcCatalog> findRsRcCatalogByParentId(Integer pId);

	SysRsRcCatalog getRsRcCatalogInfo(Integer catalogId);

	Integer getMaxOrder(Integer parentId);
}

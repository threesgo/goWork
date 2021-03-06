package com.yunwang.service;

import java.util.List;

import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcBaseData;
import com.yunwang.model.pojo.SysRsRcCatalog;

public interface SysResourceTypeService {

	void saveOrUpdateRsRcCatalog(SysRsRcCatalog sysRsRcCatalog);

	void deleteRsRcCatalog(SysRsRcCatalog sysRsRcCatalog);

	List<SysRsRcCatalog> findRsRcCatalogByParentId(Integer pId);

	SysRsRcCatalog getRsRcCatalogInfo(Integer catalogId);

	List<SysRsRcAttribCatalog> findExtendsAttr(Integer sysRsRcCatalogId);

	List<SysRsRcAttribCatalog> findAttr(Integer sysRsRcCatalogId);

	List<SysRsRcBaseData> findSysRcBaseDataTypeByGroup(String groupStr);

	void saveOrUpdateSysRsRcAttribCatalog(SysRsRcAttribCatalog sysRsRcAttribCatalog);

	SysRsRcAttribCatalog getSysRsRcAttribCatalog(Integer attribCatalogId);

	void deleteSysRsRcAttribCatalogs(String ids);

	List<SysRsRcAttribCatalog> findAllAttr(Integer sysRsRcCatalogId);

	SysRsRcAttribCatalog getRsrcAttribName(SysRsRcAttribCatalog sysRsRcAttribCatalog);

	void dragResourceType(Integer point, Integer targetId, Integer sourceId);

	void updateResourceCode();
}

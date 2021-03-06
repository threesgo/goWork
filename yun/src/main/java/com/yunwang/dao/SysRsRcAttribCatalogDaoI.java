package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysRsRcAttribCatalog;

public interface SysRsRcAttribCatalogDaoI extends BaseDaoI<SysRsRcAttribCatalog>{

	List<SysRsRcAttribCatalog> findByCatalogIds(String catalogIds);

	SysRsRcAttribCatalog getRsrcAttribName(SysRsRcAttribCatalog sysRsRcAttribCatalog);

	List<SysRsRcAttribCatalog> findByIds(String ids);
}

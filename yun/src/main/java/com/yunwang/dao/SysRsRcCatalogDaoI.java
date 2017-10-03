package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysRsRcCatalog;

public interface SysRsRcCatalogDaoI extends BaseDaoI<SysRsRcCatalog>{

	List<SysRsRcCatalog> findByParentId(Integer pId);
}

package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysResource;

public interface SysResourceDaoI extends BaseDaoI<SysResource>{

	List<SysResource> findByRsRcCatalogId(Integer catalogId);
}

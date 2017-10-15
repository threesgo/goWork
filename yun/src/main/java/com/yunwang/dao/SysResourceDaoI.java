package com.yunwang.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResource;

public interface SysResourceDaoI extends BaseDaoI<SysResource>{

	List<SysResource> findByRsRcCatalogId(Integer catalogId);

	Pager<SysResource> findByRsRcCatalogId(Integer rsRcCatalogId, int page,
			int rows, JSONObject seachJson);

	Pager<SysResource> findByRsRcCatalogIds(String rsRcCatalogIds, int page,
			int rows, JSONObject seachJson);
}

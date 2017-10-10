package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResource;

@Repository
public class SysResourceDaoImpl extends BaseDaoImpl<SysResource> implements SysResourceDaoI{

	@Override
	public List<SysResource> findByRsRcCatalogId(Integer catalogId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rsrcCatalogId",catalogId);
		return find("SELECT model FROM SysResource model " 
				+"WHERE model.rsrcCatalogId=:rsrcCatalogId ORDER BY model.createDate DESC ",map);
	}

	@Override
	public Pager<SysResource> findByRsRcCatalogId(Integer catalogId,
			int page, int rows) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rsrcCatalogId",catalogId);
		return pagedQuery("SELECT model FROM SysResource model " 
				+"WHERE model.rsrcCatalogId=:rsrcCatalogId ORDER BY model.createDate DESC", page, rows, map);
	}
	
}

package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.model.pojo.SysResource;

@Repository
public class SysResourceDaoImpl extends BaseDaoImpl<SysResource> implements SysResourceDaoI{

	@Override
	public List<SysResource> findByRsRcCatalogId(Integer catalogId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rsrcCatalogId",catalogId);
		return find("SELECT model FROM SysResource model " 
				+"WHERE model.rsrcCatalogId=:rsrcCatalogId ORDER BY model.orderNo ",map);
	}
	
}

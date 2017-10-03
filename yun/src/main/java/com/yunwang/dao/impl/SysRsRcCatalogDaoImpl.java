package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.model.pojo.SysRsRcCatalog;

@Repository
public class SysRsRcCatalogDaoImpl extends BaseDaoImpl<SysRsRcCatalog> implements SysRsRcCatalogDaoI{

	@Override
	public List<SysRsRcCatalog> findByParentId(Integer pId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("parentId",pId);
		return find("SELECT model FROM SysRsRcCatalog model WHERE model.parentId=:parentId",map);
	}
}

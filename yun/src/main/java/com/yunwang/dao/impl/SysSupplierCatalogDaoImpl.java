package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysSupplierCatalogDaoI;
import com.yunwang.model.pojo.SysSupplierCatalog;

@Repository
public class SysSupplierCatalogDaoImpl extends BaseDaoImpl<SysSupplierCatalog> implements SysSupplierCatalogDaoI{

	@Override
	public SysSupplierCatalog getBySupperIdAndCatalogId(Integer supperId,
			Integer catalogId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("supplierId",supperId);
		map.put("catalogId",catalogId);
		return get("SELECT model FROM SysSupplierCatalog model WHERE model.supplierId=:supplierId AND model.catalogId=:catalogId",map);
	}
}

package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysBrandCatalogDaoI;
import com.yunwang.model.pojo.SysBrandCatalog;

@Repository
public class SysBrandCatalogDaoImpl extends BaseDaoImpl<SysBrandCatalog> implements SysBrandCatalogDaoI{

	@Override
	public SysBrandCatalog getByBrandIdAndCatalogId(Integer brandId,
			Integer catalogId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("brandId",brandId);
		map.put("catalogId",catalogId);
		return get("SELECT model FROM SysBrandCatalog model WHERE model.brandId=:brandId AND model.catalogId=:catalogId",map);
	}
}

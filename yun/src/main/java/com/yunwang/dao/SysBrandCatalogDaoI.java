package com.yunwang.dao;

import com.yunwang.model.pojo.SysBrandCatalog;


public interface SysBrandCatalogDaoI extends BaseDaoI<SysBrandCatalog>{

	SysBrandCatalog getByBrandIdAndCatalogId(Integer brandId, Integer catalogId);

}

package com.yunwang.dao;

import com.yunwang.model.pojo.SysSupplierCatalog;


public interface SysSupplierCatalogDaoI extends BaseDaoI<SysSupplierCatalog>{

	SysSupplierCatalog getBySupperIdAndCatalogId(Integer supperId, Integer catalogId);

}

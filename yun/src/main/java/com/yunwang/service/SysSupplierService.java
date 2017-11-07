package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.model.pojo.SysSupplierCatalog;

public interface SysSupplierService {

	void saveOrUpdateSupplierGrid(JSONObject obj);

	Pager<SysSupplier> findAll(int page, int rows, JSONObject fromObject);

	void deleteSupplier(String ids);

	List<SysSupplier> findAll();

	List<SysSupplier> findByCatalogId(Integer catalogId);

	void updateRelationCatalog(String ids, Integer supplierId);

	List<SysSupplierCatalog> findAllRelCatalogSupplier(Integer supplierId);
}

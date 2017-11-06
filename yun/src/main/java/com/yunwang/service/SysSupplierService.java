package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysSupplier;

public interface SysSupplierService {

	void saveOrUpdateSupplierGrid(JSONObject obj);

	Pager<SysSupplier> findAll(int page, int rows, JSONObject fromObject);

	void deleteSupplier(String ids);

	List<SysSupplier> findAll();

	List<SysSupplier> findByCatalogId(Integer catalogId);
}

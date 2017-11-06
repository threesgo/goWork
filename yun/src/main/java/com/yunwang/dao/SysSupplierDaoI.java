package com.yunwang.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysSupplier;

public interface SysSupplierDaoI extends BaseDaoI<SysSupplier>{

	Pager<SysSupplier> findAll(int page, int rows, JSONObject fromObject);

	void deleteSupplier(String ids);

	List<SysSupplier> findByCatalogId(Integer catalogId);
}

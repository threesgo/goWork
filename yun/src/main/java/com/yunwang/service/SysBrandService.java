package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysBrand;
import com.yunwang.model.pojo.SysBrandCatalog;

public interface SysBrandService {

	Pager<SysBrand> findList(int page, int rows, JSONObject fromObject);

	void saveOrUpdateBrandGrid(JSONObject obj);

	void deleteBrand(String ids);

	List<SysBrandCatalog> findAllRelCatalogBrand(Integer id);

	void updateRelationCatalog(String ids, Integer id);

}

package com.yunwang.service;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysSupplier;

public interface SysWorkerService {

	Pager<SysSupplier> findAll(int page, int rows, JSONObject fromObject);

	void saveOrUpdateWorkerGrid(JSONObject obj);

}

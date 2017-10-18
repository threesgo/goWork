package com.yunwang.service;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysWorker;

public interface SysWorkerService {

	Pager<SysWorker> findAll(int page, int rows, JSONObject fromObject);

	void saveOrUpdateWorkerGrid(JSONObject obj);

	void deleteWorker(String ids);

}

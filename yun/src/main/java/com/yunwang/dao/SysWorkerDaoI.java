package com.yunwang.dao;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysWorker;

public interface SysWorkerDaoI extends BaseDaoI<SysWorker>{

	Pager<SysWorker> findAll(int page, int rows, JSONObject fromObject);

	void deleteWorker(String ids);

}

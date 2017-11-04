package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysWorker;

public interface SysWorkerService {

	Pager<SysWorker> findAll(int page, int rows, JSONObject fromObject);

	void saveOrUpdateWorkerGrid(JSONObject obj);

	void deleteWorker(String ids);

	List<SysWorker> findByFlowId(Integer flowId);

	List<SysWorker> findByOrderId(Integer orderId);

	Pager<SysWorker> selectWorkerData(Integer flowId, int page, int rows, JSONObject jsonObject);

}

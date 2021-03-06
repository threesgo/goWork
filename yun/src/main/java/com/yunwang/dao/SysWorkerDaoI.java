package com.yunwang.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysWorker;

public interface SysWorkerDaoI extends BaseDaoI<SysWorker>{

	Pager<SysWorker> findAll(int page, int rows, JSONObject fromObject);

	void deleteWorker(String ids);

	List<SysWorker> findByFlowId(Integer id);

	List<SysWorker> findByOrderId(Integer id);

	Pager<SysWorker> findByWorkTypeAndNotInFlow(Integer flowType,
			Integer flowId, int page, int rows, JSONObject jsonObject);

}

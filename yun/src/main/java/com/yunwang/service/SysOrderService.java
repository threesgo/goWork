package com.yunwang.service;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;


public interface SysOrderService {

	Pager<SysOrder> findAll(int page, int rows, JSONObject fromObject);

	void saveOrUpdateOrderGrid(JSONObject obj);

	void deleteOrder(String ids);


}

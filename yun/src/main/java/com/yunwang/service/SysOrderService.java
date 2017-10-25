package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;


public interface SysOrderService {

	Pager<SysOrder> findAll(int page, int rows, JSONObject fromObject);

	void deleteOrder(String ids);

	SysOrder get(Integer id);

	void saveOrUpdateOrder(SysOrder sysOrder);

	List<SysOrder> findOrderTimeGroup();

	List<SysOrder> findByOrderDate(String orderDate);

}

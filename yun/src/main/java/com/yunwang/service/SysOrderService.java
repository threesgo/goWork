package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.model.pojo.SysOrderFlow;


public interface SysOrderService {

	Pager<SysOrder> findAll(int page, int rows, JSONObject fromObject);

	void deleteOrder(String ids);

	SysOrder get(Integer id);

	void saveOrUpdateOrder(SysOrder sysOrder) throws Exception;

	List<SysOrder> findOrderTimeGroup();

	List<SysOrder> findByOrderDate(String orderDate);

	List<SysOrderFlow> findOrderFlow(Integer orderId);

	SysOrderFlow getOrderFlow(Integer orderFlowId);

}

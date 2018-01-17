package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.model.pojo.SysOrderFlow;
import com.yunwang.model.pojo.SysOrderPackage;
import com.yunwang.model.pojo.SysOrderResource;
import com.yunwang.model.pojo.SysOrderWorker;


public interface SysOrderService {

	Pager<SysOrder> findAll(int page, int rows, JSONObject fromObject);

	void deleteOrder(String ids);

	SysOrder get(Integer id);

	void saveOrUpdateOrder(SysOrder sysOrder) throws Exception;

	List<SysOrder> findOrderTimeGroup();

	List<SysOrder> findByOrderDate(String orderDate);

	List<SysOrderFlow> findOrderFlow(Integer orderId);

	SysOrderFlow getOrderFlow(Integer orderFlowId);

	void dragOrderFlow(Integer point, Integer targetId, Integer sourceId);

	List<SysOrderPackage> findOrderPackage(Integer orderId);

	void saveOrUpdateOrderFlow(SysOrderFlow sysOrderFlow);

	void deleteOrderFlow(SysOrderFlow sysOrderFlow);

	void addWorkerToFlow(String jsonStr, SysOrderFlow sysOrderFlow);

	void addResourceToFlow(String jsonStr, SysOrderFlow sysOrderFlow);

	void updateOrderWorkerTime(SysOrderWorker sysOrderWorker);

	void updateOrderResourceQuantity(SysOrderResource sysOrderResource);

	void deleteOrderWorker(String ids);

	void deleteOrderResource(String ids);

	List<SysOrder> findOrderByMember(Integer memberId);

	List<SysOrderFlow> findOrderFlowByMember(Integer memberId);

	List<SysOrder> findOrderByMemberAndType(Integer memberId, Integer type,String status);
}

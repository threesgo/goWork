package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysOrderFlowDaoI;
import com.yunwang.model.pojo.SysOrderFlow;

@Repository
public class SysOrderFlowDaoImpl extends BaseDaoImpl<SysOrderFlow> implements SysOrderFlowDaoI{

	@Override
	public List<SysOrderFlow> findByOrderId(Integer orderId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId",orderId);
		return find("SELECT model FROM SysOrderFlow model WHERE model.orderId=:orderId ORDER BY model.orderNo ",map);
	}

	@Override
	public List<SysOrderFlow> findGreaterOrder(Integer orderId,Integer orderNo) {
		String hql="SELECT model FROM SysOrderFlow as model " +
				" WHERE model.orderId=:orderId AND model.orderNo>:orderNo ";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId",orderId);
		map.put("orderNo",orderNo);
		return find(hql,map);
	}

	@Override
	public List<SysOrderFlow> findByWorkerId(Integer memberId) {
		String hql="SELECT model FROM SysOrderFlow as model,SysOrderWorker as orderWorker " +
				" WHERE model.id = orderWorker.orderFlowId AND orderWorker.workerId =:workerId " +
				" ORDER BY model.startTime";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("workerId",memberId);
		return find(hql,map);
	}
}

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
		return find("SELECT model FROM SysOrderFlow model WHERE model.orderId=:orderId",map);
	}
}

package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysOrderDaoI;
import com.yunwang.model.pojo.SysOrder;

@Repository
public class SysOrderDaoImpl extends BaseDaoImpl<SysOrder> implements SysOrderDaoI{

	@Override
	public List<SysOrder> findOrderTimeGroup() {
		String sql = "SELECT model.ORDER_DATE orderDate FROM SYS_ORDER model " +
				" GROUP BY model.ORDER_DATE ORDER BY model.ORDER_DATE";
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("orderDate", new StringType());
		return findBySQLQuery(sql,null,scalarMap);
	}

	@Override
	public List<SysOrder> findByOrderDate(String orderDate) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderDate",orderDate);
		return find("SELECT model FROM SysOrder model " 
				+" WHERE model.orderDate=:orderDate ORDER BY model.code ",map);
	}
}

package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysOrderPackageDaoI;
import com.yunwang.model.pojo.SysOrderPackage;

@Repository
public class SysOrderPackageDaoImpl extends BaseDaoImpl<SysOrderPackage> implements SysOrderPackageDaoI{

	@Override
	public List<SysOrderPackage> findByOrderId(Integer orderId) {
		String hql="SELECT model FROM SysOrderPackage as model " +
				"WHERE model.orderId=:orderId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId",orderId);
		return find(hql,map);
	}
}

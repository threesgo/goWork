package com.yunwang.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysOrderDaoI;
import com.yunwang.model.pojo.SysOrder;

@Repository
public class SysOrderDaoImpl extends BaseDaoImpl<SysOrder> implements SysOrderDaoI{

	@Override
	public List<SysOrder> findOrderTimeGroup() {
		String sql = "";
		
		
		return null;
	}
	
	
	
}

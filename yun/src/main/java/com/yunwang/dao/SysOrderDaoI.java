package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysOrder;

public interface SysOrderDaoI extends BaseDaoI<SysOrder>{

	List<SysOrder> findOrderTimeGroup();

	List<SysOrder> findByOrderDate(String orderDate);


}

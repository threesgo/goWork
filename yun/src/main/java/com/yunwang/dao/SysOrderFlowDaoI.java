package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysOrderFlow;

public interface SysOrderFlowDaoI extends BaseDaoI<SysOrderFlow>{

	List<SysOrderFlow> findByOrderId(Integer orderId);


}

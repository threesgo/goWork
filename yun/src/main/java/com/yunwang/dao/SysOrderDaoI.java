package com.yunwang.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;

public interface SysOrderDaoI extends BaseDaoI<SysOrder>{

	List<SysOrder> findOrderTimeGroup();

	List<SysOrder> findByOrderDate(String orderDate);

	Pager<SysOrder> findPageOrder(int page, int rows, JSONObject seachJson);

	void updateStatus(String ids, Integer status);

	List<SysOrder> findOrderByMemberAndType(Integer memberId, Integer type,
			String status);
}

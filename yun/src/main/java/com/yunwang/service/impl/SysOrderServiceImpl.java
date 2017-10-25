package com.yunwang.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysOrderDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.service.SysOrderService;

@Service
public class SysOrderServiceImpl implements SysOrderService {
	
	@Autowired
	private SysOrderDaoI sysOrderDao;

	@Override
	public Pager<SysOrder> findAll(int page, int rows, JSONObject fromObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrder(String ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SysOrder get(Integer id) {
		return sysOrderDao.get(SysOrder.class, id);
	}

	@Override
	public void saveOrUpdateOrder(SysOrder sysOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SysOrder> findOrderTimeGroup() {
		return sysOrderDao.findOrderTimeGroup();
	}

	@Override
	public List<SysOrder> findByOrderDate(String orderDate) {
		return sysOrderDao.findByOrderDate(orderDate);
	}
}

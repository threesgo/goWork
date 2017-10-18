package com.yunwang.service.impl;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.service.SysOrderService;

@Service
public class SysOrderServiceImpl implements SysOrderService {

	@Override
	public Pager<SysOrder> findAll(int page, int rows, JSONObject fromObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdateOrderGrid(JSONObject obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrder(String ids) {
		// TODO Auto-generated method stub
		
	}
	
}

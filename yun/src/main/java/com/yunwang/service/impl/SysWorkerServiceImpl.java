package com.yunwang.service.impl;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.service.SysWorkerService;

@Service
public class SysWorkerServiceImpl implements SysWorkerService{

	@Override
	public Pager<SysSupplier> findAll(int page, int rows, JSONObject fromObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdateWorkerGrid(JSONObject obj) {
		// TODO Auto-generated method stub
		
	}

}

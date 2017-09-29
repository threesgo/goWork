package com.yunwang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.service.SysResourceService;

@Service
public class SysResourceServiceImpl implements SysResourceService{
	
	@Autowired
	private SysRsRcCatalogDaoI sysRsRcCatalogDao; 
	@Autowired
	private SysResourceDaoI sysResourceDao; 
	
	
}

package com.yunwang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysRsRcAttribCatalogDaoI;
import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.service.SysResourceService;

@Service
public class SysResourceServiceImpl implements SysResourceService{
	
	@Autowired
	private SysRsRcCatalogDaoI sysRsRcCatalogDao; 
	@Autowired
	private SysResourceDaoI sysResourceDao;
	@Autowired
	private SysRsRcAttribCatalogDaoI sysRsRcAttribCatalogDao;
	@Autowired
	private SysRsRcAttribDaoI sysRsRcAttribDao;

	@Override
	public List<SysResource> findByRsRcCatalogId(Integer catalogId) {
		return sysResourceDao.findByRsRcCatalogId(catalogId);
	}

	@Override
	public List<SysRsRcAttrib> findSysRsRcAttribByResourceIds(String resourceIds) {
		return sysRsRcAttribDao.findByResourceIds(resourceIds);
	}

	@Override
	public void save(SysResource sysResource) {
		sysResource.setOrderNo(sysResourceDao.findMaxSeqByPfield("orderNo","rsrcCatalogId",sysResource.getRsrcCatalogId())+1);			
		sysResourceDao.save(sysResource);
	}
}

package com.yunwang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysRsRcAttribCatalogDaoI;
import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceTypeService;

@Service
public class SysResourceTypeServiceImpl implements SysResourceTypeService{

	@Autowired
	private SysRsRcCatalogDaoI sysRsRcCatalogDao; 
	@Autowired
	private SysResourceDaoI sysResourceDao; 
	@Autowired
	private SysRsRcAttribCatalogDaoI sysRsRcAttribCatalogDao;
	@Autowired
	private SysRsRcAttribDaoI sysRsRcAttribDao;
	
	@Override
	public void saveRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		sysRsRcCatalogDao.save(sysRsRcCatalog);		
	}

	@Override
	public void deleteRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		sysRsRcCatalogDao.deleteByProperty("id",sysRsRcCatalog.getId());
		sysResourceDao.deleteByProperty("rsrcOrgId", sysRsRcCatalog.getId());
		sysRsRcAttribCatalogDao.deleteByProperty("catalogId", sysRsRcCatalog.getId());
		sysRsRcAttribDao.deleteByProperty("catalogId",sysRsRcCatalog.getId());
	}

	@Override
	public List<SysRsRcCatalog> findRsRcCatalogByParentId(Integer pId) {
		return sysRsRcCatalogDao.findByParentId(pId);
	}

	@Override
	public SysRsRcCatalog getRsRcCatalogInfo(Integer catalogId) {
		return sysRsRcCatalogDao.get(SysRsRcCatalog.class,catalogId);
	}

	@Override
	public Integer getMaxOrder(Integer parentId) {
		return sysRsRcCatalogDao.findMaxSeqByPfield("orderNo","parentId",parentId);
	}
}

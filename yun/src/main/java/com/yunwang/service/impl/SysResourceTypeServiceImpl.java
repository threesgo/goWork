package com.yunwang.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysRsRcAttribCatalogDaoI;
import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.util.string.StringBufferByCollectionUtil;

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
	public void saveOrUpdateRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		sysRsRcCatalogDao.saveOrUpdate(sysRsRcCatalog);		
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

	@Override
	public List<SysRsRcAttribCatalog> findExtendsAttr(
			SysRsRcCatalog sysRsRcCatalog) {
		SysRsRcCatalog dbSysRsRcCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,sysRsRcCatalog.getId());
		List<Integer> parentIds = new ArrayList<Integer>();
		getParents(parentIds,dbSysRsRcCatalog);
		return sysRsRcAttribCatalogDao.findByCatalogIds(
				StringBufferByCollectionUtil.convertCollection(parentIds));
	}
	
	private void getParents(List<Integer> parentIds,SysRsRcCatalog dbSysRsRcCatalog){
		SysRsRcCatalog parentSysRsRcCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,dbSysRsRcCatalog.getParentId());
		parentIds.add(0, parentSysRsRcCatalog.getParentId());
		getParents(parentIds,parentSysRsRcCatalog);
	}

	@Override
	public List<SysRsRcAttribCatalog> findAttr(SysRsRcCatalog sysRsRcCatalog) {
		return sysRsRcAttribCatalogDao.findByCatalogIds(sysRsRcCatalog.getId().toString());
	}
}

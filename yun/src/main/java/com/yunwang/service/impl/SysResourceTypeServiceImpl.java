package com.yunwang.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysResourceRelDaoI;
import com.yunwang.dao.SysRsRcAttribCatalogDaoI;
import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.dao.SysRsRcAttribRelDaoI;
import com.yunwang.dao.SysRsRcBaseDataDaoI;
import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcBaseData;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.util.collection.CollectionUtil;
import com.yunwang.util.string.MyStringUtil;
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
	@Autowired
	private SysRsRcBaseDataDaoI sysRsRcBaseDataDao;
	@Autowired
	private SysRsRcAttribRelDaoI sysRsRcAttribRelDao;
	@Autowired
	private SysResourceRelDaoI sysResourceRelDao;
	
	@Override
	public void saveOrUpdateRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		if(null==sysRsRcCatalog.getId()){
			sysRsRcCatalog.setOrderNo(sysRsRcCatalogDao.findMaxSeqByPfield("orderNo","parentId",sysRsRcCatalog.getParentId())+1);
			if(sysRsRcCatalog.getParentId() != 0 ){
				SysRsRcCatalog pSysRsRcCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,sysRsRcCatalog.getParentId());
				sysRsRcCatalog.setCatalogCode(MyStringUtil.getCombineSeqStr(sysRsRcCatalog.getOrderNo(),pSysRsRcCatalog.getCatalogCode()));
				sysRsRcCatalog.setCatalogType(pSysRsRcCatalog.getCatalogType());
			}else{
				sysRsRcCatalog.setCatalogCode(MyStringUtil.getCombineSeqStr(sysRsRcCatalog.getOrderNo(),sysRsRcCatalog.getCatalogType().toString()));
			}
		}
		sysRsRcCatalogDao.saveOrUpdate(sysRsRcCatalog);		
	}

	@Override
	public void deleteRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(sysRsRcCatalog.getId());
		List<SysRsRcCatalog> children = sysRsRcCatalogDao.findByParentId(sysRsRcCatalog.getId());
		getChildrens(ids,children);
		sysRsRcCatalogDao.deleteByPropertys("id",StringBufferByCollectionUtil.convertCollection(ids));
		sysResourceDao.deleteByPropertys("rsrcCatalogId", StringBufferByCollectionUtil.convertCollection(ids));
		sysRsRcAttribCatalogDao.deleteByPropertys("rsrcCatalogId", StringBufferByCollectionUtil.convertCollection(ids));
		sysRsRcAttribDao.deleteByPropertys("rsrcCatalogId",StringBufferByCollectionUtil.convertCollection(ids));
		
		sysResourceRelDao.deleteByPropertys("rsrcCatalogId", StringBufferByCollectionUtil.convertCollection(ids));
		sysRsRcAttribRelDao.deleteByPropertys("rsrcCatalogId",StringBufferByCollectionUtil.convertCollection(ids));
	}
	
	private void getChildrens(List<Integer> childrenIds,List<SysRsRcCatalog> children){
		for(SysRsRcCatalog child:children){
			childrenIds.add(child.getId());
			List<SysRsRcCatalog> deepChildren = sysRsRcCatalogDao.findByParentId(child.getId());
			getChildrens(childrenIds,deepChildren);
		}
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
	public List<SysRsRcAttribCatalog> findExtendsAttr(
			SysRsRcCatalog sysRsRcCatalog) {
		SysRsRcCatalog dbSysRsRcCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,sysRsRcCatalog.getId());
		List<Integer> parentIds = new ArrayList<Integer>();
		if(null!=dbSysRsRcCatalog){
			getParents(parentIds,dbSysRsRcCatalog);
			return sysRsRcAttribCatalogDao.findByCatalogIds(
					StringBufferByCollectionUtil.convertCollection(parentIds));
		}else{
			return new ArrayList<SysRsRcAttribCatalog>();
		}
	}
	
	private void getParents(List<Integer> parentIds,SysRsRcCatalog dbSysRsRcCatalog){
		if(dbSysRsRcCatalog.getParentId()!=0){
			SysRsRcCatalog parentSysRsRcCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,dbSysRsRcCatalog.getParentId());
			parentIds.add(0, parentSysRsRcCatalog.getId());
			getParents(parentIds,parentSysRsRcCatalog);
		}
	}

	@Override
	public List<SysRsRcAttribCatalog> findAttr(SysRsRcCatalog sysRsRcCatalog) {
		return sysRsRcAttribCatalogDao.findByCatalogIds(sysRsRcCatalog.getId().toString());
	}

	@Override
	public List<SysRsRcBaseData> findSysRcBaseDataTypeByGroup(String groupStr) {
		return sysRsRcBaseDataDao.findByGroup(groupStr);
	}

	@Override
	public void saveOrUpdateSysRsRcAttribCatalog(
			SysRsRcAttribCatalog sysRsRcAttribCatalog) {
		sysRsRcAttribCatalogDao.saveOrUpdate(sysRsRcAttribCatalog);
	}

	@Override
	public SysRsRcAttribCatalog getSysRsRcAttribCatalog(Integer attribCatalogId) {
		return sysRsRcAttribCatalogDao.get(SysRsRcAttribCatalog.class,attribCatalogId);
	}

	@Override
	public void deleteSysRsRcAttribCatalogs(String ids) {
		sysRsRcAttribDao.deleteByPropertys("rsrcAttribCatalogId", ids);
		sysRsRcAttribRelDao.deleteByPropertys("rsrcAttribCatalogId", ids);
		sysRsRcAttribCatalogDao.deleteByPropertys("id", ids);
	}

	@Override
	public List<SysRsRcAttribCatalog> findAllAttr(SysRsRcCatalog sysRsRcCatalog) {
		List<SysRsRcAttribCatalog> attrs = new ArrayList<SysRsRcAttribCatalog>();
		attrs.addAll(findExtendsAttr(sysRsRcCatalog));
		attrs.addAll(findAttr(sysRsRcCatalog));
		return attrs;
	}

	@Override
	public SysRsRcAttribCatalog getRsrcAttribName(
			SysRsRcAttribCatalog sysRsRcAttribCatalog) {
		return sysRsRcAttribCatalogDao.getRsrcAttribName(sysRsRcAttribCatalog);
	}

	@Override
	public void dragResourceType(Integer point, Integer targetId,
			Integer sourceId) {
		SysRsRcCatalog sourceCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,sourceId);
		SysRsRcCatalog targetCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,targetId);
		if(point == 0){
			List<SysRsRcAttribCatalog> sourceAttribCatalog = findExtendsAttr(sourceCatalog);
			List<SysRsRcAttribCatalog> targetAttribCatalog = findExtendsAttr(targetCatalog);
			Map<Integer,SysRsRcAttribCatalog> map = CollectionUtil.listToMap(targetAttribCatalog, "id");
			
			for(SysRsRcAttribCatalog catalog : sourceAttribCatalog){
				SysRsRcAttribCatalog mapCatalog = map.get(catalog.getId());
				if(null == mapCatalog){
					sysRsRcAttribDao.deleteByAttribCatalogAndRsRcCatalog(catalog.getId(),sourceCatalog.getId());
				}
			}
			
			//更新
			sourceCatalog.setParentId(targetCatalog.getId());
			sourceCatalog.setOrderNo(sysRsRcCatalogDao.findMaxSeqByPfield("orderNo","parentId",sourceCatalog.getParentId())+1);			
			sourceCatalog.setCatalogCode(MyStringUtil.getCombineSeqStr(sourceCatalog.getOrderNo(),targetCatalog.getCatalogCode()));
			sysRsRcCatalogDao.update(sourceCatalog);
			
			List<SysResource> sysResources = sysResourceDao.findByRsRcCatalogId(sourceCatalog.getId());
			for(SysResource syresource : sysResources){
				syresource.setRsrcCode(MyStringUtil.getCombineSeqStr(syresource.getOrderNo(),sourceCatalog.getCatalogCode()));
				sysResourceDao.update(syresource);
			}
		}
	}

	//产品编号与类别编号相同，无所谓
	@Override
	public void updateResourceCode() {
		List<SysRsRcCatalog> sysRsRcCatalogs = sysRsRcCatalogDao.findByParentId(0);
		for(SysRsRcCatalog sysRsRcCatalog : sysRsRcCatalogs){
			sysRsRcCatalog.setCatalogCode(MyStringUtil.getCombineSeqStr(sysRsRcCatalog.getOrderNo(),sysRsRcCatalog.getCatalogType().toString()));
			List<SysResource> sysResources = sysResourceDao.findByRsRcCatalogId(sysRsRcCatalog.getId());
			for(SysResource sysResource:sysResources){
				sysResource.setRsrcCode(MyStringUtil.getCombineSeqStr(sysResource.getOrderNo(),sysRsRcCatalog.getCatalogCode()));
			}
			haddleChildren(sysRsRcCatalog);
		}
	}
	
	private void haddleChildren(SysRsRcCatalog pSysRsRcCatalog){
		List<SysRsRcCatalog> sysRsRcCatalogs = sysRsRcCatalogDao.findByParentId(pSysRsRcCatalog.getId());
		for(SysRsRcCatalog sysRsRcCatalog : sysRsRcCatalogs){
			sysRsRcCatalog.setCatalogCode(MyStringUtil.getCombineSeqStr(sysRsRcCatalog.getOrderNo(),pSysRsRcCatalog.getCatalogCode()));
			List<SysResource> sysResources = sysResourceDao.findByRsRcCatalogId(sysRsRcCatalog.getId());
			for(SysResource sysResource:sysResources){
				sysResource.setRsrcCode(MyStringUtil.getCombineSeqStr(sysResource.getOrderNo(),sysRsRcCatalog.getCatalogCode()));
			}
			haddleChildren(sysRsRcCatalog);
		}
	}
}

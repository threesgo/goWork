package com.yunwang.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceService;
import com.yunwang.util.number.MyNumberUtil;
import com.yunwang.util.string.MyStringUtil;

@Service
public class SysResourceServiceImpl implements SysResourceService{
	
	@Autowired
	private SysResourceDaoI sysResourceDao;
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

	@SuppressWarnings("rawtypes")
	@Override
	public void saveOrUpdateResourceGrid(JSONObject rowData,SysRsRcCatalog sysRsRcCatalog) {
         SysResource sysResource = null;
         Integer id = rowData.getInt("id");
         if(id > 0){
    		 sysResource = sysResourceDao.get(SysResource.class,id);
    		 sysResource.setUpdateDate(new Date());
    		 //更新
    		 sysResource.setRsrcStatus(2);
    	 }else{
    		 sysResource = new SysResource();
    		 sysResource.setCreateDate(new Date());
    		 //正常
    		 sysResource.setRsrcStatus(1);
    		 sysResource.setRsrcCatalogId(sysRsRcCatalog.getId());
    		 sysResource.setOrderNo(sysResourceDao.findMaxSeqByPfield("orderNo","rsrcCatalogId",sysRsRcCatalog.getId())+1);			
    		 
    	 }
         String rsrcCode = rowData.getString("rsrcCode");
         sysResource.setRsrcCode(rsrcCode);
         String rsrcName = rowData.getString("rsrcName");
         sysResource.setRsrcName(rsrcName);
         String abbreviaName = rowData.getString("abbreviaName");
         sysResource.setAbbreviaName(abbreviaName);
         String purchasePrice = rowData.getString("purchasePrice");
         sysResource.setPurchasePrice(new BigDecimal(purchasePrice));
         String salePrice = rowData.getString("salePrice");
         sysResource.setSalePrice(new BigDecimal(salePrice));
         Integer workType = rowData.getInt("workType");
         sysResource.setWorkType(workType);
         sysResourceDao.saveOrUpdate(sysResource);
         Iterator it = rowData.keys();  
         while (it.hasNext()) {  
             String key = (String) it.next(); 
             String value = rowData.getString(key);
             if(MyNumberUtil.isNumber(key))
             {
            	 //如果属性值为空，不做处理
            	 if(MyStringUtil.isNotBlank(value)){
            		 //更新的资源
            		 SysRsRcAttrib sysRsRcAttrib = null;
            		 sysRsRcAttrib = sysRsRcAttribDao.getByResourceAndAttr(sysResource.getId(),Integer.parseInt(key));
            		 if(null!=sysRsRcAttrib){
            			 sysRsRcAttrib.setRsrcAttribValue(value);
            		 }else{
            			 sysRsRcAttrib = new SysRsRcAttrib();
            			 sysRsRcAttrib.setRsraAttribCatalogId(Integer.parseInt(key));
            			 sysRsRcAttrib.setRsrcCatalogId(sysRsRcCatalog.getId());
            			 sysRsRcAttrib.setRsrcId(sysResource.getId());
            			 sysRsRcAttrib.setRsrcAttribValue(value);
            		 }
                	 sysRsRcAttribDao.saveOrUpdate(sysRsRcAttrib);
            	 }
             }
         } 
	}

	@Override
	public Pager<SysResource> findByRsRcCatalogId(Integer rsRcCatalogId,
			int page, int rows,JSONObject seachJson) {
		return sysResourceDao.findByRsRcCatalogId(rsRcCatalogId,page,rows,seachJson);
	}

	@Override
	public void deleteResource(String ids) {
		sysRsRcAttribDao.deleteByPropertys("rsrcId", ids);
		sysResourceDao.deleteByPropertys("id",ids);
	}

	@Override
	public void saveImportResources(List<SysResource> resourceList,
			SysRsRcCatalog sysRsRcCatalog) {
		
	}
}

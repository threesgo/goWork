package com.yunwang.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceService;
import com.yunwang.util.collection.CollectionUtil;
import com.yunwang.util.number.MyNumberUtil;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.util.string.StringBufferByCollectionUtil;

@Service
public class SysResourceServiceImpl implements SysResourceService{
	
	@Autowired
	private SysResourceDaoI sysResourceDao;
	@Autowired
	private SysRsRcAttribDaoI sysRsRcAttribDao;
	@Autowired
	private SysRsRcCatalogDaoI sysRsRcCatalogDao;

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
		
		sysRsRcCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,sysRsRcCatalog.getId());
		
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
             sysResource.setRsrcCode(MyStringUtil.getCombineSeqStr(sysResource.getOrderNo(),sysRsRcCatalog.getCatalogCode()));
    	 }
         //String rsrcCode = rowData.getString("rsrcCode");
         
         String rsrcName = rowData.getString("rsrcName");
         sysResource.setRsrcName(rsrcName);
         String abbreviaName = rowData.getString("abbreviaName");
         sysResource.setAbbreviaName(abbreviaName);
         String purchasePrice = rowData.getString("purchasePrice");
         sysResource.setPurchasePrice(new BigDecimal(purchasePrice));
         String salePrice = rowData.getString("salePrice");
         sysResource.setSalePrice(new BigDecimal(salePrice));
        //Integer workType = rowData.getInt("workType");
        //sysResource.setWorkType(workType);
         
         String brand = rowData.getString("brand");
         sysResource.setBrand(brand);
        
         
         Integer supplierId = rowData.getInt("supplierId");
         sysResource.setSupplierId(supplierId);
         
//         String supplierName = rowData.getString("supplierName");
//         sysResource.setSupplierName(supplierName);
//         String supplier = rowData.getString("supplier");
//         sysResource.setSupplier(supplier);
//         String supplierAddress = rowData.getString("supplierAddress");
//         sysResource.setSupplierAddress(supplierAddress);
//         String supplierPhone = rowData.getString("supplierPhone");
//         sysResource.setSupplierPhone(supplierPhone);
         
         sysResourceDao.saveOrUpdate(sysResource);
         
         rowData.put("rsrcCode", sysResource.getRsrcCode());
         rowData.put("id", sysResource.getId());
         
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
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(rsRcCatalogId);
		List<SysRsRcCatalog> children = sysRsRcCatalogDao.findByParentId(rsRcCatalogId);
		getChildrens(ids,children);
		return sysResourceDao.findByRsRcCatalogIds(
				StringBufferByCollectionUtil.convertCollection(ids),page,rows,seachJson);
		//return sysResourceDao.findByRsRcCatalogId(rsRcCatalogId,page,rows,seachJson);
	}
	
	private void getChildrens(List<Integer> childrenIds,List<SysRsRcCatalog> children){
		for(SysRsRcCatalog child:children){
			childrenIds.add(child.getId());
			List<SysRsRcCatalog> deepChildren = sysRsRcCatalogDao.findByParentId(child.getId());
			getChildrens(childrenIds,deepChildren);
		}
	}

	@Override
	public void deleteResource(String ids) {
		sysRsRcAttribDao.deleteByPropertys("rsrcId", ids);
		sysResourceDao.deleteByPropertys("id",ids);
	}

	private Map<Integer,Map<Integer,SysRsRcAttrib>> conAttribToMap(List<SysRsRcAttrib> sysRsRcAttribs){
		Map<Integer,Map<Integer,SysRsRcAttrib>> map = new HashMap<Integer,Map<Integer,SysRsRcAttrib>>();
		for(SysRsRcAttrib attrib:sysRsRcAttribs){
			Map<Integer,SysRsRcAttrib> childMap = map.get(attrib.getRsrcId());
			if(null!=childMap){
				childMap.put(attrib.getRsraAttribCatalogId(), attrib);
			}else{
				childMap = new HashMap<Integer,SysRsRcAttrib>();
				childMap.put(attrib.getRsraAttribCatalogId(), attrib);
				map.put(attrib.getRsrcId(), childMap);
			}
		}
		return map;
	}
	
	@Override
	public void saveImportResources(List<SysResource> resourceList,
			SysRsRcCatalog sysRsRcCatalog) {
		sysRsRcCatalog = sysRsRcCatalogDao.get(SysRsRcCatalog.class,sysRsRcCatalog.getId());
		//数据库已有资源
		List<SysResource> dbResources = sysResourceDao.findByRsRcCatalogId(sysRsRcCatalog.getId());
		Map<String,SysResource> resourceMap = CollectionUtil.listToMap(dbResources, "rsrcCode");
		
		List<SysRsRcAttrib> sysRsRcAttribs = sysRsRcAttribDao.findByResourceIds(
					StringBufferByCollectionUtil.convertCollection(dbResources,"id"));
		Map<Integer,Map<Integer,SysRsRcAttrib>> attribMap = conAttribToMap(sysRsRcAttribs);
		
		int orderNo = sysResourceDao.findMaxSeqByPfield("orderNo", "rsrcCatalogId", sysRsRcCatalog.getId());
		
		for(SysResource resource:resourceList){
			//遍历资源库资源
			SysResource dbResource = resourceMap.get(resource.getRsrcCode());
			if(null != dbResource){
				//更新
				dbResource.setRsrcName(resource.getRsrcName());
				dbResource.setUpdateDate(new Date());
				dbResource.setPurchasePrice(resource.getPurchasePrice());
				dbResource.setSalePrice(resource.getSalePrice());
				//dbResource.setWorkType(resource.getWorkType());
				dbResource.setAbbreviaName(resource.getAbbreviaName());
				
				dbResource.setBrand(resource.getBrand());
				dbResource.setSupplierName(resource.getSupplierName());
				dbResource.setSupplierAddress(resource.getSupplierAddress());
				dbResource.setSupplierPhone(resource.getSupplierPhone());
				
				Map<Integer,SysRsRcAttrib> attrMap = attribMap.get(dbResource.getId());
				
				for(SysRsRcAttrib attrib:resource.getSysRcRsrcAttribList()){
					SysRsRcAttrib dbAttrib = attrMap.get(attrib.getRsraAttribCatalogId());
					if(null != dbAttrib){
						dbAttrib.setRsrcAttribValue(attrib.getRsrcAttribValue());
						sysRsRcAttribDao.update(dbAttrib);
					}else{
						attrib.setRsrcCatalogId(sysRsRcCatalog.getId());
						attrib.setRsrcId(resource.getId());
						attrib.setRsrcAttribValue(attrib.getRsrcAttribValue());
						sysRsRcAttribDao.save(attrib);
					}
				}
			}else{
				//新增
				resource.setRsrcCatalogId(sysRsRcCatalog.getId());
				resource.setRsrcStatus(1);
				resource.setOrderNo(++orderNo);
				resource.setCreateDate(new Date());
				resource.setRsrcCode(MyStringUtil.getCombineSeqStr(resource.getOrderNo(),sysRsRcCatalog.getCatalogCode()));
				sysResourceDao.save(resource);
				for(SysRsRcAttrib attrib:resource.getSysRcRsrcAttribList()){
					attrib.setRsrcCatalogId(sysRsRcCatalog.getId());
					attrib.setRsrcId(resource.getId());
					attrib.setRsrcAttribValue(attrib.getRsrcAttribValue());
					sysRsRcAttribDao.save(attrib);
				}
			}
		}
	}

	@Override
	public List<SysResource> findParentByRsRcCatalogId(Integer parentId) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(parentId);
		List<SysRsRcCatalog> children = sysRsRcCatalogDao.findByParentId(parentId);
		getChildrens(ids,children);
		return sysResourceDao.findByRsRcCatalogIds(StringBufferByCollectionUtil.convertCollection(ids));
	}
}

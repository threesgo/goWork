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
import com.yunwang.dao.SysResourceRelDaoI;
import com.yunwang.dao.SysRsRcAttribCatalogDaoI;
import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.dao.SysRsRcAttribRelDaoI;
import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.dao.SysRsRcPcResourceDaoI;
import com.yunwang.dao.SysSupplierDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcAttribRel;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.model.pojo.SysRsRcPcResource;
import com.yunwang.model.pojo.SysSupplier;
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
	@Autowired
	private SysSupplierDaoI sysSupplierDao;
	@Autowired
	private SysResourceRelDaoI sysResourceRelDao;
	@Autowired
	private SysRsRcAttribRelDaoI sysRsRcAttribRelDao;
	@Autowired
	private SysRsRcAttribCatalogDaoI sysRsRcAttribCatalogDao;
	@Autowired
	private SysRsRcPcResourceDaoI sysRsRcPcResourceDao;


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
         rowData.put("rsrcStatus", sysResource.getRsrcStatus());
         
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
            			 sysRsRcAttrib.setRsrcAttribCatalogId(Integer.parseInt(key));
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
		List<SysResource>  sysResources = sysResourceDao.findInPropertys("id", ids);
		for(SysResource sysResource:sysResources){
			sysResource.setRsrcStatus(0);
			sysResourceDao.update(sysResource);
		}
		//sysRsRcAttribDao.deleteByPropertys("rsrcId", ids);
		//sysResourceDao.deleteByPropertys("id",ids);
	}

	private Map<Integer,Map<Integer,SysRsRcAttrib>> conAttribToMap(List<SysRsRcAttrib> sysRsRcAttribs){
		Map<Integer,Map<Integer,SysRsRcAttrib>> map = new HashMap<Integer,Map<Integer,SysRsRcAttrib>>();
		for(SysRsRcAttrib attrib:sysRsRcAttribs){
			Map<Integer,SysRsRcAttrib> childMap = map.get(attrib.getRsrcId());
			if(null!=childMap){
				childMap.put(attrib.getRsrcAttribCatalogId(), attrib);
			}else{
				childMap = new HashMap<Integer,SysRsRcAttrib>();
				childMap.put(attrib.getRsrcAttribCatalogId(), attrib);
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
		
		
		List<SysSupplier> sysSuppliers = sysSupplierDao.findByWorkType(sysRsRcCatalog.getCatalogType());
		Map<String,SysSupplier> supplierMap = CollectionUtil.listToMap(sysSuppliers,"name");
		
		for(SysResource resource:resourceList){
			//遍历资源库资源
			SysResource dbResource = null;
			if(MyStringUtil.isNotBlank(resource.getRsrcCode())){
				dbResource = resourceMap.get(resource.getRsrcCode().trim());
			}
			SysSupplier sysSupplier = null;
			if(MyStringUtil.isNotBlank(resource.getSupplierName())){
				sysSupplier = supplierMap.get(resource.getSupplierName());
				if(null == sysSupplier){
					sysSupplier = new SysSupplier();
					sysSupplier.setCode((sysSupplierDao.findMaxSeq("code")+1));			
					sysSupplier.setStatus(1);
					sysSupplier.setName(resource.getSupplierName().trim());
					sysSupplier.setContact(resource.getSupplier());
					sysSupplier.setPhoneNum(resource.getSupplierPhone());
					sysSupplier.setTelNum(resource.getSupplierTel());
					sysSupplier.setAddress(resource.getSupplierTel());
					sysSupplier.setWorkType(sysRsRcCatalog.getCatalogType());
					sysSupplierDao.saveOrUpdate(sysSupplier);
				}
			}
			
			if(null != dbResource){
				//更新
				dbResource.setRsrcName(resource.getRsrcName());
				dbResource.setUpdateDate(new Date());
				dbResource.setPurchasePrice(resource.getPurchasePrice());
				dbResource.setSalePrice(resource.getSalePrice());
				//dbResource.setWorkType(resource.getWorkType());
				dbResource.setAbbreviaName(resource.getAbbreviaName());
				dbResource.setBrand(resource.getBrand());
				if(null != sysSupplier){
					dbResource.setSupplierId(sysSupplier.getId());
				}
				Map<Integer,SysRsRcAttrib> attrMap = attribMap.get(dbResource.getId());
				
				for(SysRsRcAttrib attrib:resource.getSysRcRsrcAttribList()){
					SysRsRcAttrib dbAttrib = attrMap.get(attrib.getRsrcAttribCatalogId());
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
				if(null != sysSupplier){
					resource.setSupplierId(sysSupplier.getId());
				}
				sysResourceDao.save(resource);
				resourceMap.put(resource.getRsrcCode(), resource);
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

	@Override
	public void releaseResource(String ids) {
		List<SysResource>  sysResources = sysResourceDao.findInPropertys("id", ids);
		
		List<SysRsRcAttrib> sysRsRcAttribs = sysRsRcAttribDao.findByResourceIds(
				StringBufferByCollectionUtil.convertCollection(sysResources,"id"));
		Map<Integer,Map<Integer,SysRsRcAttrib>> attribMap = conAttribToMap(sysRsRcAttribs);
		
		
		for(SysResource sysResource:sysResources){
			sysResource.setRsrcStatus(3);
			sysResourceDao.update(sysResource);
			//发布数据移动到发布区
			SysResourceRel sysResourceRel = sysResourceRelDao.getByResourceId(sysResource.getId());
			
			if(null != sysResourceRel){
				setDataToSysResource(sysResource, sysResourceRel);
				sysResourceRelDao.update(sysResourceRel);
				sysRsRcAttribRelDao.deleteByProperty("rsrcRelId", sysResourceRel.getId());
			}else{
				sysResourceRel = new SysResourceRel();
				setDataToSysResource(sysResource, sysResourceRel);
				sysResourceRelDao.save(sysResourceRel);
			}
			
			StringBuffer buf = new StringBuffer();
			buf.append((MyStringUtil.isNotBlank(sysResource.getBrand())?sysResource.getBrand():"")
					+(MyStringUtil.isNotBlank(sysResource.getRsrcName())?sysResource.getRsrcName():""));
			
			Map<Integer,SysRsRcAttrib> rattribMap = attribMap.get(sysResource.getId());
			List<Integer> attribCatalogIdList = new ArrayList<Integer>();
			if(null != rattribMap && rattribMap.size() > 0 ){
				for(Integer key:rattribMap.keySet()){
					SysRsRcAttrib attrib = rattribMap.get(key);
					SysRsRcAttribRel sysRsRcAttribRel = new SysRsRcAttribRel();
					sysRsRcAttribRel.setRsrcRelId(sysResourceRel.getId());
					sysRsRcAttribRel.setRsrcAttribCatalogId(attrib.getRsrcAttribCatalogId());
					sysRsRcAttribRel.setRsrcCatalogId(attrib.getRsrcCatalogId());
					sysRsRcAttribRel.setRsrcAttribValue(attrib.getRsrcAttribValue());
					attribCatalogIdList.add(attrib.getRsrcAttribCatalogId());
				}
			}
			if(attribCatalogIdList.size() > 0){
				buf.append("(");
				List<SysRsRcAttribCatalog> attribCatalogs = sysRsRcAttribCatalogDao.findByIds(StringBufferByCollectionUtil.convertCollection(attribCatalogIdList));
				for(SysRsRcAttribCatalog attribCatalog : attribCatalogs){
					buf.append(attribCatalog.getRsrcAttribName()+":"+rattribMap.get(attribCatalog.getId()).getRsrcAttribValue()+" ");
				}
				buf.append(")");
			}
			sysResourceRel.setKeyWord(buf.toString());
			sysResourceRelDao.update(sysResourceRel);
		}
	}

	private void setDataToSysResource(SysResource sysResource,
			SysResourceRel sysResourceRel) {
		sysResourceRel.setResourceId(sysResource.getId());
		sysResourceRel.setKeyWord((MyStringUtil.isNotBlank(sysResource.getBrand())?sysResource.getBrand():"")
							+(MyStringUtil.isNotBlank(sysResource.getRsrcName())?sysResource.getRsrcName():""));
		sysResourceRel.setRsrcCode(sysResource.getRsrcCode());
		sysResourceRel.setRsrcName(sysResource.getRsrcName());
		sysResourceRel.setAbbreviaName(sysResource.getAbbreviaName());
		sysResourceRel.setOrderNo(sysResource.getOrderNo());
		sysResourceRel.setRsrcCatalogId(sysResource.getRsrcCatalogId());
		sysResourceRel.setSalePrice(sysResource.getSalePrice());	
		sysResourceRel.setBrand(sysResource.getBrand());
		sysResourceRel.setSupplierId(sysResource.getSupplierId());
		sysResourceRel.setReleaseDate(new Date());
		sysResourceRel.setRsrcStatus(1);
	}

	@Override
	public Pager<SysResourceRel> findRelResources(SysRsRcPackage sysRsRcPackage,int page, int rows,
			JSONObject seachObj) {
		// TODO Auto-generated method stub
		return sysResourceRelDao.findRelResources(sysRsRcPackage,page,rows,seachObj);
	}

	@Override
	public void addRelResourceToPackage(Integer packageId,String ids) {
		if(MyStringUtil.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			for(String id:idArr){
				SysRsRcPcResource sysRsRcPcResource = new SysRsRcPcResource();
				sysRsRcPcResource.setPackageId(packageId);
				sysRsRcPcResource.setResourceId(Integer.parseInt(id));
				sysRsRcPcResource.setOrderNo(sysRsRcPcResourceDao.findMaxSeq("orderNo")+1);
				sysRsRcPcResourceDao.save(sysRsRcPcResource);
			}
		}
	}
}

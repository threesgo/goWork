package com.yunwang.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysBrandCatalogDaoI;
import com.yunwang.dao.SysBrandDaoI;
import com.yunwang.dao.SysOrderFlowDaoI;
import com.yunwang.dao.SysOrderPackageDaoI;
import com.yunwang.dao.SysPcBrandCatalogDaoI;
import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysResourceRelDaoI;
import com.yunwang.dao.SysRsRcAttribCatalogDaoI;
import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.dao.SysRsRcAttribRelDaoI;
import com.yunwang.dao.SysRsRcCatalogDaoI;
import com.yunwang.dao.SysRsRcPcResourceDaoI;
import com.yunwang.dao.SysSupplierCatalogDaoI;
import com.yunwang.dao.SysSupplierDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysBrand;
import com.yunwang.model.pojo.SysBrandCatalog;
import com.yunwang.model.pojo.SysOrderFlow;
import com.yunwang.model.pojo.SysOrderPackage;
import com.yunwang.model.pojo.SysPcBrandCatalog;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcAttribRel;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.model.pojo.SysRsRcPcResource;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.model.pojo.SysSupplierCatalog;
import com.yunwang.service.SysResourceService;
import com.yunwang.util.Constant;
import com.yunwang.util.collection.CollectionUtil;
import com.yunwang.util.exception.MineException;
import com.yunwang.util.file.FileSupport;
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
	private SysBrandDaoI sysBrandDao;
	@Autowired
	private SysResourceRelDaoI sysResourceRelDao;
	@Autowired
	private SysRsRcAttribRelDaoI sysRsRcAttribRelDao;
	@Autowired
	private SysRsRcAttribCatalogDaoI sysRsRcAttribCatalogDao;
	@Autowired
	private SysRsRcPcResourceDaoI sysRsRcPcResourceDao;
	@Autowired
	private SysOrderFlowDaoI sysOrderFlowDao;
	@Autowired
	private SysOrderPackageDaoI sysOrderPackageDao;
	@Autowired
	private SysSupplierCatalogDaoI SysSupplierCatalogDao;
	@Autowired
	private SysBrandCatalogDaoI sysBrandCatalogDao;
	@Autowired
	private SysPcBrandCatalogDaoI sysPcBrandCatalogDao;

 
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
		if(null == sysRsRcCatalog){
			throw new MineException("请选择具体的子集大类新增,确认产品属于具体的工程类别!");
		}
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
    		 sysResource.setIsRelease(0);
    		 sysResource.setRsrcCatalogId(sysRsRcCatalog.getId());
    		 sysResource.setOrderNo(sysResourceDao.findMaxSeqByPfield("orderNo","rsrcCatalogId",sysRsRcCatalog.getId())+1);			
             sysResource.setRsrcCode(MyStringUtil.getCombineSeqStr(sysResource.getOrderNo(),sysRsRcCatalog.getCatalogCode()));
        }
         
	     String rsrcName = rowData.getString("rsrcName");
	     sysResource.setRsrcName(rsrcName);
	     String abbreviaName = rowData.getString("abbreviaName");
	     sysResource.setAbbreviaName(abbreviaName);
	     String purchasePrice = rowData.getString("purchasePrice");
	     sysResource.setPurchasePrice(new BigDecimal(purchasePrice));
	     String salePrice = rowData.getString("salePrice");
	     sysResource.setSalePrice(new BigDecimal(salePrice));
	
	     Integer brandId = rowData.getInt("brandId");
	     sysResource.setBrandId(brandId);
	     
	     Integer supplierId = rowData.getInt("supplierId");
	     sysResource.setSupplierId(supplierId);
	     
	     sysResourceDao.saveOrUpdate(sysResource);
	     
	     rowData.put("rsrcCode", sysResource.getRsrcCode());
	     rowData.put("id", sysResource.getId());
	     rowData.put("rsrcStatus", sysResource.getRsrcStatus());
	     rowData.put("isRelease", sysResource.getIsRelease());
	     
	     Iterator it = rowData.keys();  
	     while (it.hasNext()) {  
	         String key = (String) it.next(); 
	         String value = rowData.getString(key);
	         if(MyNumberUtil.isNumber(key))
	         {
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

	@Override
	public Pager<SysResource> findByRsRcCatalogId(Integer rsRcCatalogId,
			int page, int rows,JSONObject seachJson) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(rsRcCatalogId);
		List<SysRsRcCatalog> children = sysRsRcCatalogDao.findByParentId(rsRcCatalogId);
		getChildrens(ids,children);
		return sysResourceDao.findByRsRcCatalogIds(
				StringBufferByCollectionUtil.convertCollection(ids),page,rows,seachJson);
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
			if(sysResource.getIsRelease() == 1){
				sysResource.setRsrcStatus(0);
				sysResourceDao.update(sysResource);
			}else{
				sysRsRcAttribDao.deleteByProperty("rsrcId", sysResource.getId());
				sysResourceDao.deleteByProperty("id",sysResource.getId());
			}
		}
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
		
		
		List<SysSupplier> sysSuppliers = sysSupplierDao.findAll();
		Map<String,SysSupplier> supplierMap = CollectionUtil.listToMap(sysSuppliers,"name");
		
		List<SysBrand> sysBrands = sysBrandDao.findAll();
		Map<Integer,SysBrand> sysBrandMap = CollectionUtil.listToMap(sysBrands,"name");
		
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
					sysSupplierDao.save(sysSupplier);
				}
				
				//增加供应商与类别的关联关系
				SysSupplierCatalog sysSupplierCatalog = SysSupplierCatalogDao.getBySupperIdAndCatalogId(sysSupplier.getId(),sysRsRcCatalog.getId());
				if(null == sysSupplierCatalog){
					SysSupplierCatalog addSysSupplierCatalog = new SysSupplierCatalog();
					addSysSupplierCatalog.setCatalogId(sysRsRcCatalog.getId());
					addSysSupplierCatalog.setSupplierId(sysSupplier.getId());
					SysSupplierCatalogDao.save(addSysSupplierCatalog);
				}
			}
			
			SysBrand sysBrand = null;
			if(MyStringUtil.isNotBlank(resource.getBrand())){
				sysBrand = sysBrandMap.get(resource.getBrand().trim());
				if(null == sysBrand){
					sysBrand = new SysBrand();
					sysBrand.setCode((sysBrandDao.findMaxSeq("code")+1));			
					sysBrand.setStatus(1);
					sysBrand.setName(resource.getBrand().trim());
					sysBrandDao.save(sysBrand);
				}
				//增加供应商与类别的关联关系
				SysBrandCatalog sysBrandCatalog = sysBrandCatalogDao.getByBrandIdAndCatalogId(sysBrand.getId(),sysRsRcCatalog.getId());
				if(null == sysBrandCatalog){
					SysBrandCatalog addSysBrandCatalog = new SysBrandCatalog();
					addSysBrandCatalog.setCatalogId(sysRsRcCatalog.getId());
					addSysBrandCatalog.setBrandId(sysBrand.getId());
					sysBrandCatalogDao.save(addSysBrandCatalog);
				}
			}
			
			
			if(null != dbResource){
				//更新
				dbResource.setRsrcName(resource.getRsrcName());
				dbResource.setUpdateDate(new Date());
				dbResource.setPurchasePrice(resource.getPurchasePrice());
				dbResource.setSalePrice(resource.getSalePrice());
				dbResource.setAbbreviaName(resource.getAbbreviaName());
				if(null != sysBrand){
					dbResource.setBrandId(sysBrand.getId());
				}
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
				resource.setIsRelease(0);
				resource.setOrderNo(++orderNo);
				resource.setCreateDate(new Date());
				resource.setRsrcCode(MyStringUtil.getCombineSeqStr(resource.getOrderNo(),sysRsRcCatalog.getCatalogCode()));
				if(null != sysSupplier){
					resource.setSupplierId(sysSupplier.getId());
				}
				if(null != sysBrand){
					resource.setBrandId(sysBrand.getId());
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
		
		List<SysBrand> sysBrands = sysBrandDao.findAll();
		Map<Integer,SysBrand> brandMap = CollectionUtil.listToMap(sysBrands,"id");
		
		for(SysResource sysResource:sysResources){
			//发布数据移动到发布区
			SysResourceRel sysResourceRel = sysResourceRelDao.getByResourceId(sysResource.getId());
			if(sysResource.getRsrcStatus() == 0){
				if(null != sysResourceRel){
					sysResourceRel.setRsrcStatus(0);
					sysResourceRelDao.update(sysResourceRel);  //发布变成失效
				}
				sysRsRcAttribDao.deleteByProperty("rsrcId", sysResource.getId());
				sysResourceDao.delete(sysResource);
			}else{
				sysResource.setRsrcStatus(3);  //所有状态变成正常
				sysResource.setIsRelease(1);
				sysResourceDao.update(sysResource);
				
				if(null != sysResourceRel){
					setDataToSysResource(sysResource, sysResourceRel,brandMap);
					sysResourceRelDao.update(sysResourceRel);
					sysRsRcAttribRelDao.deleteByProperty("rsrcRelId", sysResourceRel.getId());
				}else{
					sysResourceRel = new SysResourceRel();
					setDataToSysResource(sysResource, sysResourceRel,brandMap);
					sysResourceRelDao.save(sysResourceRel);
				}
				
				StringBuffer buf = new StringBuffer();
				buf.append((null!=brandMap.get(sysResource.getBrandId())?brandMap.get(sysResource.getBrandId()).getName():"")
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
						sysRsRcAttribRelDao.save(sysRsRcAttribRel);
						attribCatalogIdList.add(attrib.getRsrcAttribCatalogId());
					}
				}
				if(attribCatalogIdList.size() > 0){
					buf.append("(");
					List<SysRsRcAttribCatalog> attribCatalogs = sysRsRcAttribCatalogDao.findByIds(StringBufferByCollectionUtil.convertCollection(attribCatalogIdList));
					for(SysRsRcAttribCatalog attribCatalog : attribCatalogs){
						if(null!=rattribMap.get(attribCatalog.getId())){
							if(MyStringUtil.isNotBlank(rattribMap.get(attribCatalog.getId()).getRsrcAttribValue())){
								buf.append(attribCatalog.getRsrcAttribName()+":"+rattribMap.get(attribCatalog.getId()).getRsrcAttribValue()+" ");
							}else{
								buf.append(attribCatalog.getRsrcAttribName()+": ");
							}
						}
					}
					buf.append(")");
				}
				sysResourceRel.setKeyWord(buf.toString());
				
				if(MyStringUtil.isNotBlank(sysResource.getImagePath())){
					try {
						byte[] bytes = FileSupport.read(sysResource.getImagePath());
						FileSupport.mkdir("resourceImageRel");
						String fileName=FileSupport.join("resourceImageRel","resourceRel_"+sysResource.getId()+"."+MyStringUtil.getileNameSuffix(sysResource.getImagePath()));
						FileSupport.write(fileName, bytes);
						sysResourceRel.setImagePath(fileName);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				sysResourceRelDao.update(sysResourceRel);
			}
		}
	}

	private void setDataToSysResource(SysResource sysResource,
			SysResourceRel sysResourceRel,Map<Integer,SysBrand> brandMap) {
		sysResourceRel.setResourceId(sysResource.getId());
		sysResourceRel.setKeyWord((null!=brandMap.get(sysResource.getBrandId())?brandMap.get(sysResource.getBrandId()).getName():"")
							+(MyStringUtil.isNotBlank(sysResource.getRsrcName())?sysResource.getRsrcName():""));
		sysResourceRel.setRsrcCode(sysResource.getRsrcCode());
		sysResourceRel.setRsrcName(sysResource.getRsrcName());
		sysResourceRel.setAbbreviaName(sysResource.getAbbreviaName());
		sysResourceRel.setOrderNo(sysResource.getOrderNo());
		sysResourceRel.setRsrcCatalogId(sysResource.getRsrcCatalogId());
		sysResourceRel.setPurchasePrice(sysResource.getPurchasePrice());
		sysResourceRel.setSalePrice(sysResource.getSalePrice());	
		sysResourceRel.setBrandId(sysResource.getBrandId());
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

	@Override
	public SysResourceRel getRelResource(Integer relResourceId) {
		return sysResourceRelDao.get(SysResourceRel.class,relResourceId);
	}

	@Override
	public JSONArray getRelResourceInfo(Integer relResourceId,List<SysRsRcAttribCatalog> attrList) {
		SysResourceRel relResource = sysResourceRelDao.get(SysResourceRel.class,relResourceId);
		List<SysRsRcAttribRel> attribRels = sysRsRcAttribRelDao.findByProperty("rsrcRelId",relResource.getId());
		JSONArray arr = new JSONArray();
		Map<Integer,SysRsRcAttribRel> map = CollectionUtil.listToMap(attribRels,"rsrcAttribCatalogId");
		Map<Integer,SysBrand> brandMap = CollectionUtil.listToMap(sysBrandDao.findAll(),"id");
		
		JSONObject keyWord = new JSONObject();
		keyWord.put("attrName", "产品关键字");
		keyWord.put("value", relResource.getKeyWord());
		arr.add(keyWord);
		
		JSONObject rsrcName = new JSONObject();
		rsrcName.put("attrName", "产品名称");
		rsrcName.put("value", relResource.getRsrcName());
		arr.add(rsrcName);
		
		JSONObject abbreviaName = new JSONObject();
		abbreviaName.put("attrName", "产品简称");
		abbreviaName.put("value", relResource.getAbbreviaName());
		arr.add(abbreviaName);
		
		JSONObject salePrice = new JSONObject();
		salePrice.put("attrName", "产品价格");
		salePrice.put("value", relResource.getSalePrice());
		arr.add(salePrice);
		
		JSONObject brand = new JSONObject();
		brand.put("attrName", "产品品牌");
		brand.put("value", brandMap.get(relResource.getBrandId())!=null?brandMap.get(relResource.getBrandId()).getName():"");
		arr.add(brand);
		
		JSONObject releaseDate = new JSONObject();
		releaseDate.put("attrName", "发布时间");
		releaseDate.put("value", relResource.getReleaseDateStr());
		arr.add(releaseDate);
		
		JSONObject pic = new JSONObject();
		pic.put("attrName", "参考图片");
		pic.put("type",Constant.PIC);
		pic.put("value", relResource.getImagePath());
		arr.add(pic);
		
		for(SysRsRcAttribCatalog attribCatalog:attrList){
			JSONObject attr = new JSONObject();
			attr.put("attrName", attribCatalog.getRsrcAttribName());
			attr.put("value",map.get(attribCatalog.getId())!=null?map.get(attribCatalog.getId()).getRsrcAttribValue():"");
			arr.add(attr);
		}
		return arr;
	}

	@Override
	public List<SysResourceRel> findByFlowId(Integer id) {
		// TODO Auto-generated method stub
		return sysResourceRelDao.findByFlowId(id);
	}

	@Override
	public List<SysResourceRel> findByOrderId(Integer id) {
		// TODO Auto-generated method stub
		return sysResourceRelDao.findByOrderId(id);
	}

	@Override
	public Pager<SysResourceRel> selectResourceData(Integer flowId,int page, int rows, JSONObject seachJson) {
		// TODO Auto-generated method stub
		SysOrderFlow sysOrderFlow = sysOrderFlowDao.get(SysOrderFlow.class,flowId);
		List<SysOrderPackage> sysOrderPackages = sysOrderPackageDao.findByOrderId(sysOrderFlow.getOrderId());
		String packageIds = StringBufferByCollectionUtil.convertCollection(sysOrderPackages, "rsrcPackageId");
		//查询套餐关联的品牌
		List<SysPcBrandCatalog> sysPcBrandCatalogs = sysPcBrandCatalogDao.findInPropertys("packageId", packageIds);
		if(sysPcBrandCatalogs.size()-0==0){
			return sysResourceRelDao.findByBrandCatalogIdsAndNotInFlow("0",flowId,page,rows,seachJson);
		}else{
			return sysResourceRelDao.findByBrandCatalogIdsAndNotInFlow(
					StringBufferByCollectionUtil.convertCollection(sysPcBrandCatalogs,"brandCatalogId"),flowId,page,rows,seachJson);
		}
	}

	@Override
	public void update(SysResource sysResource) {
		sysResourceDao.update(sysResource);
	}
	

	@Override
	public SysResource get(Integer id) {
		return sysResourceDao.get(SysResource.class, id);
	}

	@Override
	public List<SysResourceRel> findResourceByCataLogId(Integer catalogId) {
		return sysResourceRelDao.findResourceByCataLogId(catalogId);
	}

	@Override
	public List<SysRsRcAttribRel> findAttribRelByResourceId(Integer resourceId) {
		return sysRsRcAttribRelDao.findByProperty("rsrcRelId",resourceId);
	}

	@Override
	public List<SysResourceRel> findResourceByCataLogId(Integer catalogId,
			String seachJson) {
		return sysResourceRelDao.findResourceByCataLogId(catalogId,seachJson);
	}
}

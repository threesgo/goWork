package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysResourceRelDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysResourceRelDaoImpl extends BaseDaoImpl<SysResourceRel> implements SysResourceRelDaoI{

	@Override
	public SysResourceRel getByResourceId(Integer resourceId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("resourceId",resourceId);
		return get("SELECT model FROM SysResourceRel model WHERE model.resourceId=:resourceId",map);
	}

	@Override
	public Pager<SysResourceRel> findRelResources(SysRsRcPackage sysRsRcPackage,int page, int rows,
			JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.RESOURCE_ID resourceId,model.IMAGE_PATH imagePath, " +
				" model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				" model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				" model.SALE_PRICE salePrice,model.PURCHASE_PRICE purchasePrice,model.PURCHASE_PRICE purchasePrice,model.BRAND_ID brandId,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate," +
				" model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE catalogType,rsCatalog.WORK_TYPE workType " +
				" FROM SYS_RESOURCE_REL model" +
				" LEFT JOIN SYS_RSRC_CATALOG rsCatalog ON model.RSRC_CATALOG_ID = rsCatalog.ID " +
				" WHERE model.RSRC_STUTAS!=0 ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("keyWord")&&MyStringUtil.isNotBlank(seachJson.getString("keyWord"))){
				buf.append(" AND upper(model.KEY_WORD) like:keyWord");
				parmeMap.put("keyWord","%"+ seachJson.getString("keyWord").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("rsrcName")&&MyStringUtil.isNotBlank(seachJson.getString("rsrcName"))){
				buf.append(" AND upper(model.RSRC_NAME) like:rsrcName");
				parmeMap.put("rsrcName","%"+ seachJson.getString("rsrcName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("abbreviaName")&&MyStringUtil.isNotBlank(seachJson.getString("abbreviaName"))){
				buf.append(" AND upper(model.ABBREVIA_NAME) like:abbreviaName");
				parmeMap.put("abbreviaName","%"+ seachJson.getString("abbreviaName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("brandId")&&0!=seachJson.getInt("brandId")){
				buf.append(" AND model.BRAND_ID=:brandId");
				parmeMap.put("brandId",seachJson.getInt("brandId"));
			}
			if(seachJson.containsKey("supplierId")&&0!=seachJson.getInt("supplierId")){
				buf.append(" AND model.SUPPLIER_ID =:supplierId");
				parmeMap.put("supplierId",seachJson.getInt("supplierId"));
			}
			if(seachJson.containsKey("sysRsRcCatalogId")&&0!=seachJson.getInt("sysRsRcCatalogId")){
				buf.append(" AND model.RSRC_CATALOG_ID =:sysRsRcCatalogId");
				parmeMap.put("sysRsRcCatalogId",seachJson.getInt("sysRsRcCatalogId"));
			}
			if(seachJson.containsKey("catalogType")&&0!=seachJson.getInt("catalogType")){
				buf.append(" AND rsCatalog.CATALOG_TYPE =:catalogType");
				parmeMap.put("catalogType",seachJson.getInt("catalogType"));
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append(" AND rsCatalog.WORK_TYPE =:workType");
				parmeMap.put("workType",seachJson.getInt("workType"));
			}
		}
		if(null != sysRsRcPackage && null != sysRsRcPackage.getId()){
			buf.append(" AND model.ID NOT IN (SELECT pcResource.RESOURCE_ID FROM SYS_RSRC_PC_RESOURCE pcResource WHERE pcResource.PACKAGE_ID =:packageId)");
			parmeMap.put("packageId",sysRsRcPackage.getId());
		}
		buf.append(" ORDER BY model.RSRC_CODE");
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("resourceId", new IntegerType());
		scalarMap.put("imagePath", new StringType());
		scalarMap.put("keyWord", new StringType());
		scalarMap.put("rsrcName", new StringType());
		scalarMap.put("rsrcCode", new StringType());
		scalarMap.put("abbreviaName", new StringType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("salePrice", new BigDecimalType());
		scalarMap.put("purchasePrice", new BigDecimalType());
		scalarMap.put("rsrcCatalogId", new IntegerType());
		scalarMap.put("supplierId", new IntegerType());
		scalarMap.put("brandId", new IntegerType());
		scalarMap.put("releaseDate", new TimestampType());
		scalarMap.put("catalogType", new IntegerType());
		scalarMap.put("workType", new IntegerType());
		
		return pagedSqlQuery(buf.toString(),page,rows,parmeMap,scalarMap);
	}

	@Override
	public List<SysResourceRel> findPackageResourceData(Integer packageId) {
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer buf = new StringBuffer("SELECT model FROM SysResourceRel model WHERE 1=1 ");
		buf.append("AND model.id IN(SELECT pcResource.resourceId " +
				" FROM SysRsRcPcResource pcResource WHERE pcResource.packageId=:packageId ) " +
				" ORDER BY model.rsrcCode");
		map.put("packageId",packageId);
		return find(buf.toString(),map);
	}

	@Override
	public Pager<SysResourceRel> findPackageResourceData(Integer packageId,
			int page, int rows, JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.RESOURCE_ID resourceId,model.IMAGE_PATH imagePath, " +
				" model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				" model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				" model.SALE_PRICE salePrice,model.PURCHASE_PRICE purchasePrice,model.PURCHASE_PRICE purchasePrice,model.BRAND_ID brandId,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate," +
				" model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE catalogType,rsCatalog.WORK_TYPE workType " +
				" FROM SYS_RSRC_PC_RESOURCE pcResource" +
				" LEFT JOIN SYS_RESOURCE_REL model ON model.ID = pcResource.RESOURCE_ID " +
				" LEFT JOIN SYS_RSRC_CATALOG rsCatalog ON model.RSRC_CATALOG_ID = rsCatalog.ID " +
				" WHERE pcResource.PACKAGE_ID=:packageId AND model.RSRC_STUTAS!=0 ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		parmeMap.put("packageId", packageId);
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("keyWord")&&MyStringUtil.isNotBlank(seachJson.getString("keyWord"))){
				buf.append(" AND upper(model.KEY_WORD) like:keyWord");
				parmeMap.put("keyWord","%"+ seachJson.getString("keyWord").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("rsrcName")&&MyStringUtil.isNotBlank(seachJson.getString("rsrcName"))){
				buf.append(" AND upper(model.RSRC_NAME) like:rsrcName");
				parmeMap.put("rsrcName","%"+ seachJson.getString("rsrcName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("abbreviaName")&&MyStringUtil.isNotBlank(seachJson.getString("abbreviaName"))){
				buf.append(" AND upper(model.ABBREVIA_NAME) like:abbreviaName");
				parmeMap.put("abbreviaName","%"+ seachJson.getString("abbreviaName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("brandId")&&0!=seachJson.getInt("brandId")){
				buf.append(" AND model.BRAND_ID=:brandId");
				parmeMap.put("brandId",seachJson.getInt("brandId"));
			}
			if(seachJson.containsKey("supplierId")&&0!=seachJson.getInt("supplierId")){
				buf.append(" AND model.SUPPLIER_ID =:supplierId");
				parmeMap.put("supplierId",seachJson.getInt("supplierId"));
			}
			if(seachJson.containsKey("catalogType")&&0!=seachJson.getInt("catalogType")){
				buf.append(" AND rsCatalog.CATALOG_TYPE =:catalogType");
				parmeMap.put("catalogType",seachJson.getInt("catalogType"));
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append(" AND rsCatalog.WORK_TYPE =:workType");
				parmeMap.put("workType",seachJson.getInt("workType"));
			}
		}
		
		buf.append("  ORDER BY model.RSRC_CODE");
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("resourceId", new IntegerType());
		scalarMap.put("imagePath", new StringType());
		scalarMap.put("keyWord", new StringType());
		scalarMap.put("rsrcName", new StringType());
		scalarMap.put("rsrcCode", new StringType());
		scalarMap.put("abbreviaName", new StringType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("salePrice", new BigDecimalType());
		scalarMap.put("purchasePrice", new BigDecimalType());
		scalarMap.put("rsrcCatalogId", new IntegerType());
		scalarMap.put("supplierId", new IntegerType());
		scalarMap.put("brandId", new IntegerType());
		scalarMap.put("releaseDate", new TimestampType());
		scalarMap.put("catalogType", new IntegerType());
		scalarMap.put("workType", new IntegerType());
		
		return pagedSqlQuery(buf.toString(),page,rows,parmeMap,scalarMap);
	}

	@Override
	public void deletePackageResource(String ids) {
		String hql = "DELETE FROM SysRsRcPcResource model WHERE model.resourceId IN("+ids+")";
		executeHql(hql);
	}

	@Override
	public List<SysResourceRel> findByFlowId(Integer flowId) {
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.RESOURCE_ID resourceId,model.IMAGE_PATH imagePath, " +
				" model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				" model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				" model.SALE_PRICE salePrice,model.PURCHASE_PRICE purchasePrice,model.BRAND_ID brandId,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate," +
				" model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE catalogType,rsCatalog.WORK_TYPE workType,orderResource.QUANTITY quantity,orderResource.ID orderResourceId " +
				" FROM SYS_ORDER_RESOURCE orderResource" +
				" LEFT JOIN SYS_RESOURCE_REL model ON model.ID = orderResource.RESOURCE_ID " +
				" LEFT JOIN SYS_RSRC_CATALOG rsCatalog ON model.RSRC_CATALOG_ID = rsCatalog.ID " +
				" WHERE orderResource.ORDER_FLOW_ID=:flowId ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		parmeMap.put("flowId", flowId);
		
		buf.append("  ORDER BY model.RSRC_CODE");
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("resourceId", new IntegerType());
		scalarMap.put("imagePath", new StringType());
		scalarMap.put("keyWord", new StringType());
		scalarMap.put("rsrcName", new StringType());
		scalarMap.put("rsrcCode", new StringType());
		scalarMap.put("abbreviaName", new StringType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("salePrice", new BigDecimalType());
		scalarMap.put("purchasePrice", new BigDecimalType());
		scalarMap.put("rsrcCatalogId", new IntegerType());
		scalarMap.put("supplierId", new IntegerType());
		scalarMap.put("brandId", new IntegerType());
		scalarMap.put("releaseDate", new TimestampType());
		scalarMap.put("catalogType", new IntegerType());
		scalarMap.put("workType", new IntegerType());
		scalarMap.put("quantity", new BigDecimalType());
		scalarMap.put("orderResourceId", new IntegerType());
		return findBySQLQuery(buf.toString(),parmeMap,scalarMap);
	}

	@Override
	public List<SysResourceRel> findByOrderId(Integer orderId) {
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.RESOURCE_ID resourceId,model.IMAGE_PATH imagePath, " +
				" model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				" model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				" model.SALE_PRICE salePrice,model.PURCHASE_PRICE purchasePrice,model.BRAND_ID brandId,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate," +
				" model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE catalogType,rsCatalog.WORK_TYPE workType,SUM(orderResource.QUANTITY) quantity "+
				" FROM SYS_ORDER_RESOURCE orderResource" +
				" LEFT JOIN SYS_RESOURCE_REL model ON model.ID = orderResource.RESOURCE_ID " +
				" LEFT JOIN SYS_RSRC_CATALOG rsCatalog ON model.RSRC_CATALOG_ID = rsCatalog.ID " +
				" WHERE orderResource.ORDER_ID=:orderId " +
				" GROUP BY model.ID,model.RESOURCE_ID,model.IMAGE_PATH," +
				" model.KEY_WORD,model.RSRC_CODE,model.RSRC_NAME," +
				" model.ABBREVIA_NAME,model.ORDER_NO,model.RSRC_CATALOG_ID," +
				" model.SALE_PRICE,model.PURCHASE_PRICE,model.BRAND_ID,model.SUPPLIER_ID,model.RELEASE_DATE," +
				" model.RSRC_STUTAS,rsCatalog.CATALOG_TYPE,rsCatalog.WORK_TYPE ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		parmeMap.put("orderId", orderId);
		
		buf.append("  ORDER BY model.RSRC_CODE");
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("resourceId", new IntegerType());
		scalarMap.put("imagePath", new StringType());
		scalarMap.put("keyWord", new StringType());
		scalarMap.put("rsrcName", new StringType());
		scalarMap.put("rsrcCode", new StringType());
		scalarMap.put("abbreviaName", new StringType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("salePrice", new BigDecimalType());
		scalarMap.put("purchasePrice", new BigDecimalType());
		scalarMap.put("rsrcCatalogId", new IntegerType());
		scalarMap.put("supplierId", new IntegerType());
		scalarMap.put("brandId", new IntegerType());
		scalarMap.put("releaseDate", new TimestampType());
		scalarMap.put("catalogType", new IntegerType());
		scalarMap.put("workType", new IntegerType());
		scalarMap.put("quantity", new BigDecimalType());
		return findBySQLQuery(buf.toString(),parmeMap,scalarMap);
	}

	@Override
	public Pager<SysResourceRel> findByPackageIdsAndNotInFlow(String packageIds,
			Integer flowId,int page, int rows, JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.RESOURCE_ID resourceId,model.IMAGE_PATH imagePath," +
				" model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				" model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				" model.SALE_PRICE salePrice,model.PURCHASE_PRICE purchasePrice,model.BRAND_ID brandId,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate," +
				" model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE catalogType,rsCatalog.WORK_TYPE workType " +
				" FROM SYS_RSRC_PC_RESOURCE pcResource" +
				" LEFT JOIN SYS_RESOURCE_REL model ON model.ID = pcResource.RESOURCE_ID " +
				" LEFT JOIN SYS_RSRC_CATALOG rsCatalog ON model.RSRC_CATALOG_ID = rsCatalog.ID " +
				" WHERE model.RSRC_STUTAS!=0 " +
				" AND model.id NOT IN(SELECT orderResource.RESOURCE_ID FROM SYS_ORDER_RESOURCE orderResource WHERE orderResource.ORDER_FLOW_ID=:flowId)");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		parmeMap.put("flowId", flowId);
		
		if(MyStringUtil.isNotBlank(packageIds)){
			buf.append(" AND pcResource.PACKAGE_ID IN("+packageIds+")");
		}
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("keyWord")&&MyStringUtil.isNotBlank(seachJson.getString("keyWord"))){
				buf.append(" AND upper(model.KEY_WORD) like:keyWord");
				parmeMap.put("keyWord","%"+ seachJson.getString("keyWord").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("rsrcName")&&MyStringUtil.isNotBlank(seachJson.getString("rsrcName"))){
				buf.append(" AND upper(model.RSRC_NAME) like:rsrcName");
				parmeMap.put("rsrcName","%"+ seachJson.getString("rsrcName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("abbreviaName")&&MyStringUtil.isNotBlank(seachJson.getString("abbreviaName"))){
				buf.append(" AND upper(model.ABBREVIA_NAME) like:abbreviaName");
				parmeMap.put("abbreviaName","%"+ seachJson.getString("abbreviaName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("brandId")&&0!=seachJson.getInt("brandId")){
				buf.append(" AND model.BRAND_ID =:brandId");
				parmeMap.put("brandId",seachJson.getInt("brandId"));
			}
			if(seachJson.containsKey("supplierId")&&0!=seachJson.getInt("supplierId")){
				buf.append(" AND model.SUPPLIER_ID =:supplierId");
				parmeMap.put("supplierId",seachJson.getInt("supplierId"));
			}
			if(seachJson.containsKey("catalogType")&&0!=seachJson.getInt("catalogType")){
				buf.append(" AND rsCatalog.CATALOG_TYPE =:catalogType");
				parmeMap.put("catalogType",seachJson.getInt("catalogType"));
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append(" AND rsCatalog.WORK_TYPE =:workType");
				parmeMap.put("workType",seachJson.getInt("workType"));
			}
		}
		
		buf.append("  ORDER BY model.RSRC_CODE");
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("resourceId", new IntegerType());
		scalarMap.put("imagePath", new StringType());
		scalarMap.put("keyWord", new StringType());
		scalarMap.put("rsrcName", new StringType());
		scalarMap.put("rsrcCode", new StringType());
		scalarMap.put("abbreviaName", new StringType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("salePrice", new BigDecimalType());
		scalarMap.put("purchasePrice", new BigDecimalType());
		scalarMap.put("rsrcCatalogId", new IntegerType());
		scalarMap.put("supplierId", new IntegerType());
		scalarMap.put("brandId", new IntegerType());
		scalarMap.put("releaseDate", new TimestampType());
		scalarMap.put("catalogType", new IntegerType());
		scalarMap.put("workType", new IntegerType());
		
		return pagedSqlQuery(buf.toString(),page,rows,parmeMap,scalarMap);
	}

	@Override
	public Pager<SysResourceRel> findByBrandCatalogIdsAndNotInFlow(
			String brandCatalogIds, Integer flowId,
			int page, int rows, JSONObject seachJson) {

		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.RESOURCE_ID resourceId, " +
				" model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				" model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				" model.SALE_PRICE salePrice,model.PURCHASE_PRICE purchasePrice,model.BRAND_ID brandId,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate," +
				" model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE catalogType,rsCatalog.WORK_TYPE workType " +
				" FROM SYS_RESOURCE_REL model" +
				" LEFT JOIN SYS_RSRC_CATALOG rsCatalog ON (model.RSRC_CATALOG_ID = rsCatalog.ID) " +
				" ,(SELECT brandCatalog.* FROM SYS_BRAND_CATALOG brandCatalog WHERE brandCatalog.ID IN ("+brandCatalogIds+")) selectBrandCatalog " +
				" WHERE model.RSRC_STUTAS!=0 AND (selectBrandCatalog.CATALOG_ID = rsCatalog.ID AND model.BRAND_ID = selectBrandCatalog.BRAND_ID) "+
				" AND model.id NOT IN(SELECT orderResource.RESOURCE_ID FROM SYS_ORDER_RESOURCE orderResource WHERE orderResource.ORDER_FLOW_ID=:flowId)");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		parmeMap.put("flowId", flowId);
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("keyWord")&&MyStringUtil.isNotBlank(seachJson.getString("keyWord"))){
				buf.append(" AND upper(model.KEY_WORD) like:keyWord");
				parmeMap.put("keyWord","%"+ seachJson.getString("keyWord").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("rsrcName")&&MyStringUtil.isNotBlank(seachJson.getString("rsrcName"))){
				buf.append(" AND upper(model.RSRC_NAME) like:rsrcName");
				parmeMap.put("rsrcName","%"+ seachJson.getString("rsrcName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("abbreviaName")&&MyStringUtil.isNotBlank(seachJson.getString("abbreviaName"))){
				buf.append(" AND upper(model.ABBREVIA_NAME) like:abbreviaName");
				parmeMap.put("abbreviaName","%"+ seachJson.getString("abbreviaName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("brandId")&&0!=seachJson.getInt("brandId")){
				buf.append(" AND model.BRAND_ID =:brandId");
				parmeMap.put("brandId",seachJson.getInt("brandId"));
			}
			if(seachJson.containsKey("supplierId")&&0!=seachJson.getInt("supplierId")){
				buf.append(" AND model.SUPPLIER_ID =:supplierId");
				parmeMap.put("supplierId",seachJson.getInt("supplierId"));
			}
			if(seachJson.containsKey("catalogType")&&0!=seachJson.getInt("catalogType")){
				buf.append(" AND rsCatalog.CATALOG_TYPE =:catalogType");
				parmeMap.put("catalogType",seachJson.getInt("catalogType"));
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append(" AND rsCatalog.WORK_TYPE =:workType");
				parmeMap.put("workType",seachJson.getInt("workType"));
			}
		}
		
		buf.append("  ORDER BY model.RSRC_CODE");
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("resourceId", new IntegerType());
		scalarMap.put("keyWord", new StringType());
		scalarMap.put("rsrcName", new StringType());
		scalarMap.put("rsrcCode", new StringType());
		scalarMap.put("abbreviaName", new StringType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("salePrice", new BigDecimalType());
		scalarMap.put("purchasePrice", new BigDecimalType());
		scalarMap.put("rsrcCatalogId", new IntegerType());
		scalarMap.put("supplierId", new IntegerType());
		scalarMap.put("brandId", new IntegerType());
		scalarMap.put("releaseDate", new TimestampType());
		scalarMap.put("catalogType", new IntegerType());
		scalarMap.put("workType", new IntegerType());
		
		return pagedSqlQuery(buf.toString(),page,rows,parmeMap,scalarMap);
	}

	@Override
	public List<SysResourceRel> findResourceByCataLogId(Integer catalogId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rsrcCatalogId",catalogId);
		return find("SELECT model FROM SysResourceRel model WHERE model.rsrcCatalogId=:rsrcCatalogId",map);
	}

	@Override
	public List<SysResourceRel> findResourceByCataLogId(Integer catalogId,
			String seachJson) {
		StringBuffer buf = new StringBuffer("SELECT model FROM SysResourceRel model " 
				+"WHERE model.rsrcCatalogId=:rsrcCatalogId ");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rsrcCatalogId",catalogId);
		if(MyStringUtil.isNotBlank(seachJson)){
			buf.append("AND model.keyWord LIKE :keyWord ");
			map.put("keyWord","%"+seachJson+"%");
		}
		return find(buf.toString(),map);
	}
}

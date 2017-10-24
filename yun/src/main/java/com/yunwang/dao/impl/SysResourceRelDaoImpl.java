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
				" SELECT model.ID id,model.RESOURCE_ID resourceId, " +
				" model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				" model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				" model.SALE_PRICE salePrice,model.BRAND brand,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate," +
				" model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE workType" +
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
			if(seachJson.containsKey("brand")&&MyStringUtil.isNotBlank(seachJson.getString("brand"))){
				buf.append(" AND upper(model.BRAND) like:brand");
				parmeMap.put("brand","%"+ seachJson.getString("brand").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("supplierId")&&0!=seachJson.getInt("supplierId")){
				buf.append(" AND model.SUPPLIER_ID =:supplierId");
				parmeMap.put("supplierId",seachJson.getInt("supplierId"));
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append(" AND rsCatalog.CATALOG_TYPE =:workType");
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
		scalarMap.put("keyWord", new StringType());
		scalarMap.put("rsrcName", new StringType());
		scalarMap.put("rsrcCode", new StringType());
		scalarMap.put("abbreviaName", new StringType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("salePrice", new BigDecimalType());
		scalarMap.put("rsrcCatalogId", new IntegerType());
		scalarMap.put("supplierId", new IntegerType());
		scalarMap.put("brand", new StringType());
		scalarMap.put("releaseDate", new TimestampType());
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
				" SELECT model.ID id,model.RESOURCE_ID resourceId, " +
				" model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				" model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				" model.SALE_PRICE salePrice,model.BRAND brand,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate," +
				" model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE workType" +
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
			if(seachJson.containsKey("brand")&&MyStringUtil.isNotBlank(seachJson.getString("brand"))){
				buf.append(" AND upper(model.BRAND) like:brand");
				parmeMap.put("brand","%"+ seachJson.getString("brand").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("supplierId")&&0!=seachJson.getInt("supplierId")){
				buf.append(" AND model.SUPPLIER_ID =:supplierId");
				parmeMap.put("supplierId",seachJson.getInt("supplierId"));
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append(" AND rsCatalog.CATALOG_TYPE =:workType");
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
		scalarMap.put("rsrcCatalogId", new IntegerType());
		scalarMap.put("supplierId", new IntegerType());
		scalarMap.put("brand", new StringType());
		scalarMap.put("releaseDate", new TimestampType());
		scalarMap.put("workType", new IntegerType());
		
		return pagedSqlQuery(buf.toString(),page,rows,parmeMap,scalarMap);
	}

	@Override
	public void deletePackageResource(String ids) {
		String hql = "DELETE FROM SysRsRcPcResource model WHERE model.resourceId IN("+ids+")";
		executeHql(hql);
	}
}

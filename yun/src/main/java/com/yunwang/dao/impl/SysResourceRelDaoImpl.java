package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysResourceRelDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResourceRel;
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
	public Pager<SysResourceRel> findRelResources(int page, int rows,
			JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				"SELECT model.ID id,model.RESOURCE_ID resourceId, " +
				"model.KEY_WORD keyWord,model.RSRC_CODE rsrcCode,model.RSRC_NAME rsrcName," +
				"model.ABBREVIA_NAME abbreviaName,model.ORDER_NO orderNo,model.RSRC_CATALOG_ID rsrcCatalogId," +
				"model.SALE_PRICE salePrice,model.BRAND brand,model.SUPPLIER_ID supplierId,model.RELEASE_DATE releaseDate" +
				"model.RSRC_STUTAS rsrcStatus,rsCatalog.CATALOG_TYPE workType" +
				"FROM SYS_RESOURCE_REL model" +
				" LEFT JOIN SYS_RSRC_CATALOG rsCatalog ON model.RSRC_CATALOG_ID = rsCatalog.ID " +
				" WHERE model.RSRC_STUTAS!=0 ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("keyWord")&&MyStringUtil.isNotBlank(seachJson.getString("keyWord"))){
				buf.append("AND upper(model.KEY_WORD) like:keyWord");
				parmeMap.put("keyWord","%"+ seachJson.getString("keyWord").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("abbreviaName")&&MyStringUtil.isNotBlank(seachJson.getString("abbreviaName"))){
				buf.append("AND upper(model.ABBREVIA_NAME) like:abbreviaName");
				parmeMap.put("abbreviaName","%"+ seachJson.getString("abbreviaName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("brand")&&MyStringUtil.isNotBlank(seachJson.getString("brand"))){
				buf.append("AND upper(model.BRAND) like:brand");
				parmeMap.put("brand","%"+ seachJson.getString("brand").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("supplierId")&&0!=seachJson.getInt("supplierId")){
				buf.append("AND model.SUPPLIER_ID =:supplierId");
				parmeMap.put("supplierId",seachJson.getInt("supplierId"));
			}
		}
		buf.append(" ORDER BY rsCatalog.catalogType,model.orderNo");
		
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("resourceId", new IntegerType());
		scalarMap.put("keyWord", new StringType());
		scalarMap.put("rsrcName", new StringType());
		scalarMap.put("rsrcCode", new StringType());
		scalarMap.put("abbreviaName", new StringType());
		scalarMap.put("orderNo", new StringType());
		scalarMap.put("salePrice", new StringType());
		scalarMap.put("rsrcCatalogId", new StringType());
		scalarMap.put("brand", new StringType());
		scalarMap.put("releaseDate", new StringType());
		scalarMap.put("workType", new StringType());
		
		return pagedSqlQuery(buf.toString(),page,rows,parmeMap,scalarMap);
	}
}

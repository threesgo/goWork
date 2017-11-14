package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysBrandDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysBrand;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysBrandDaoImpl extends BaseDaoImpl<SysBrand> implements SysBrandDaoI{

	@Override
	public Pager<SysBrand> findList(int page, int rows, JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				"SELECT model FROM SysBrand model WHERE model.status!=0 ");
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("name")&&MyStringUtil.isNotBlank(seachJson.getString("name"))){
				buf.append(" AND upper(model.name) like:name");
				map.put("name","%"+seachJson.getString("name").toUpperCase()+ "%");
			}
			
			if(seachJson.containsKey("info")&&MyStringUtil.isNotBlank(seachJson.getString("info"))){
				buf.append(" AND upper(model.info) like:info");
				map.put("info","%"+seachJson.getString("info").toUpperCase()+ "%");
			}
		}
		buf.append(" ORDER BY model.id");
		return pagedQuery(buf.toString(), page, rows, map);
	}

	@Override
	public List<SysBrand> findByCatalogId(Integer catalogId) {
//		StringBuffer buf = new StringBuffer(
//				"SELECT DISTINCT model FROM SysBrand model,SysBrandCatalog supCatalog " 
//			+"WHERE supCatalog.brandId = model.id AND supCatalog.catalogId=:catalogId AND model.status!=0 ORDER BY model.id");
//		Map<String, Object> map = new HashMap<String,Object>();
//		map.put("catalogId",catalogId);
//		return find(buf.toString(),map);
		
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.CODE code, " +
				" model.NAME name,model.INFO info,model.STATUS status,brandCatalog.ID brandCatalogId" +
				" FROM SYS_BRAND_CATALOG brandCatalog" +
				" LEFT JOIN SYS_BRAND model ON model.ID = brandCatalog.BRAND_ID " +
				" WHERE brandCatalog.CATALOG_ID=:catalogId ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		parmeMap.put("catalogId", catalogId);
		
		buf.append("  ORDER BY model.ID");
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("code", new IntegerType());
		scalarMap.put("name", new StringType());
		scalarMap.put("info", new StringType());
		scalarMap.put("status", new IntegerType());
		scalarMap.put("brandCatalogId", new IntegerType());
		return findBySQLQuery(buf.toString(),parmeMap,scalarMap);
	}

	@Override
	public List<SysBrand> findByName(String name, Integer id) {
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer buf = new StringBuffer(
				"SELECT model FROM SysBrand model WHERE model.status!=0 AND model.name=:name ");
		if(null!=id){
			buf.append(" AND model.id!=:id");
			map.put("id",id);
		}
		map.put("name",name);
		return find(buf.toString(),map);
	}
}

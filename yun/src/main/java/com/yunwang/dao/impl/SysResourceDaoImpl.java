package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.util.number.MyNumberUtil;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysResourceDaoImpl extends BaseDaoImpl<SysResource> implements SysResourceDaoI{

	@Override
	public List<SysResource> findByRsRcCatalogId(Integer catalogId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rsrcCatalogId",catalogId);
		return find("SELECT model FROM SysResource model " 
				+"WHERE model.rsrcCatalogId=:rsrcCatalogId ORDER BY model.orderNo ",map);
	}

	@Override
	public Pager<SysResource> findByRsRcCatalogId(Integer catalogId,
			int page, int rows,JSONObject seachJson) {
		StringBuffer buf = new StringBuffer("SELECT model FROM SysResource model WHERE model.rsrcCatalogId=:rsrcCatalogId ");
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rsrcCatalogId",catalogId);
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("rsrcCode")&&MyStringUtil.isNotBlank(seachJson.getString("rsrcCode"))){
				buf.append("AND upper(model.rsrcCode) like:rsrcCode");
				map.put("rsrcCode","%"+seachJson.getString("rsrcCode").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("rsrcName")&&MyStringUtil.isNotBlank(seachJson.getString("rsrcName"))){
				buf.append("AND upper(model.rsrcName) like:rsrcName");
				map.put("rsrcName","%"+ seachJson.getString("rsrcName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("abbreviaName")&&MyStringUtil.isNotBlank(seachJson.getString("abbreviaName"))){
				buf.append("AND upper(model.abbreviaName) like:abbreviaName");
				map.put("abbreviaName","%"+ seachJson.getString("abbreviaName").toUpperCase()+ "%");
			}
			@SuppressWarnings("rawtypes")
			Iterator it = seachJson.keys();
			while (it.hasNext()) {  
	             String key = (String) it.next(); 
	             String value = seachJson.getString(key);
	             if(MyNumberUtil.isNumber(key)&&MyStringUtil.isNotBlank(value)){
	            	//属性值按照in值判断
	     			buf.append(" AND model.id in (");
	     			buf.append("SELECT attrib.rsrcId FROM SysRsRcAttrib attrib WHERE attrib.rsraAttribCatalogId ="+key
	     					+" AND attrib.rsrcAttribValue like '%"+value+"%'");
	     			buf.append(")");
	             }
			}
		}
		buf.append(" ORDER BY model.orderNo");
		return pagedQuery(buf.toString(), page, rows, map);
	}

	@Override
	public Pager<SysResource> findByRsRcCatalogIds(String rsRcCatalogIds,
			int page, int rows, JSONObject seachJson) {
		StringBuffer buf = new StringBuffer("SELECT model FROM SysResource model WHERE model.rsrcCatalogId in("+rsRcCatalogIds+") ");
		
		Map<String, Object> map = new HashMap<String,Object>();
		//map.put("rsrcCatalogIds",rsRcCatalogIds);
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("rsrcCode")&&MyStringUtil.isNotBlank(seachJson.getString("rsrcCode"))){
				buf.append("AND upper(model.rsrcCode) like:rsrcCode");
				map.put("rsrcCode","%"+seachJson.getString("rsrcCode").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("rsrcName")&&MyStringUtil.isNotBlank(seachJson.getString("rsrcName"))){
				buf.append("AND upper(model.rsrcName) like:rsrcName");
				map.put("rsrcName","%"+ seachJson.getString("rsrcName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("abbreviaName")&&MyStringUtil.isNotBlank(seachJson.getString("abbreviaName"))){
				buf.append("AND upper(model.abbreviaName) like:abbreviaName");
				map.put("abbreviaName","%"+ seachJson.getString("abbreviaName").toUpperCase()+ "%");
			}
			@SuppressWarnings("rawtypes")
			Iterator it = seachJson.keys();
			while (it.hasNext()) {  
	             String key = (String) it.next(); 
	             String value = seachJson.getString(key);
	             if(MyNumberUtil.isNumber(key)&&MyStringUtil.isNotBlank(value)){
	            	//属性值按照in值判断
	     			buf.append(" AND model.id in (");
	     			buf.append("SELECT attrib.rsrcId FROM SysRsRcAttrib attrib WHERE attrib.rsraAttribCatalogId ="+key
	     					+" AND attrib.rsrcAttribValue like '%"+value+"%'");
	     			buf.append(")");
	             }
			}
		}
		buf.append(" ORDER BY model.rsrcCatalogId,model.orderNo");
		return pagedQuery(buf.toString(), page, rows, map);
	}
	
}

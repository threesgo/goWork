package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

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
}

package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysWorkerDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysWorker;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysWorkerDaoImpl extends BaseDaoImpl<SysWorker> implements SysWorkerDaoI{

	@Override
	public Pager<SysWorker> findAll(int page, int rows, JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				"SELECT model FROM SysWorker model WHERE model.status!=0 ");
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("name")&&MyStringUtil.isNotBlank(seachJson.getString("name"))){
				buf.append("AND upper(model.name) like:name");
				map.put("name","%"+seachJson.getString("name").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("sex")&&0!=seachJson.getInt("sex")){
				buf.append("AND model.workType=:sex");
				map.put("sex",seachJson.getInt("sex"));
			}
			if(seachJson.containsKey("phoneNum")&&MyStringUtil.isNotBlank(seachJson.getString("phoneNum"))){
				buf.append("AND upper(model.phoneNum) like:phoneNum");
				map.put("phoneNum","%"+ seachJson.getString("phoneNum").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("telNum")&&MyStringUtil.isNotBlank(seachJson.getString("telNum"))){
				buf.append("AND upper(model.telNum) like:telNum");
				map.put("telNum","%"+ seachJson.getString("telNum").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("address")&&MyStringUtil.isNotBlank(seachJson.getString("address"))){
				buf.append("AND upper(model.address) like:address");
				map.put("address","%"+ seachJson.getString("address").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append("AND model.workType=:workType");
				map.put("workType",seachJson.getInt("workType"));
			}
			if(seachJson.containsKey("company")&&MyStringUtil.isNotBlank(seachJson.getString("company"))){
				buf.append("AND upper(model.company) like:company");
				map.put("company","%"+ seachJson.getString("company").toUpperCase()+ "%");
			}
		}
		buf.append(" ORDER BY model.id");
		return pagedQuery(buf.toString(), page, rows, map);
	}

	@Override
	public void deleteWorker(String ids) {
		String hql = "UPDATE SysWorker model SET model.status = 0 WHERE model.id IN("+ids+")";
		executeHql(hql);
	}
}

package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysSupplierDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysSupplierDaoImpl extends BaseDaoImpl<SysSupplier> implements SysSupplierDaoI{

	@Override
	public Pager<SysSupplier> findAll(int page, int rows, JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				"SELECT model FROM SysSupplier model WHERE model.status!=0 ");
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("name")&&MyStringUtil.isNotBlank(seachJson.getString("name"))){
				buf.append("AND upper(model.name) like:name");
				map.put("name","%"+seachJson.getString("name").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("contact")&&MyStringUtil.isNotBlank(seachJson.getString("contact"))){
				buf.append("AND upper(model.contact) like:contact");
				map.put("contact","%"+ seachJson.getString("contact").toUpperCase()+ "%");
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
		}
		buf.append(" ORDER BY model.id");
		return pagedQuery(buf.toString(), page, rows, map);
	}
	
	public List<SysSupplier> findByWorkType(Integer workType) {
		StringBuffer buf = new StringBuffer(
				"SELECT model FROM SysSupplier model WHERE model.status!=0 ");
		Map<String, Object> map = new HashMap<String,Object>();
		if(null != workType){
			buf.append("AND model.workType=:workType");
			map.put("workType",workType);
		}
		return find(buf.toString(),map);
	}

	@Override
	public void deleteSupplier(String ids) {
		String hql = "UPDATE SysSupplier model SET model.status = 0 WHERE model.id IN("+ids+")";
		executeHql(hql);
	}
}

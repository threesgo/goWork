package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysMemberDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysMember;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysMemberDaoImpl extends BaseDaoImpl<SysMember> implements SysMemberDaoI{

	@Override
	public Pager<SysMember> findAll(int page, int rows, JSONObject seachJson) {

		StringBuffer buf = new StringBuffer(
				"SELECT model FROM SysMember model WHERE 1=1 ");
		Map<String, Object> map = new HashMap<String,Object>();
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("type")&&0!=seachJson.getInt("type")){
				buf.append(" AND model.type=:type");
				map.put("type",seachJson.getInt("type"));
			}
			if(seachJson.containsKey("userName")&&MyStringUtil.isNotBlank(seachJson.getString("userName"))){
				buf.append(" AND upper(model.userName) like:userName");
				map.put("userName","%"+seachJson.getString("userName").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("phoneNum")&&MyStringUtil.isNotBlank(seachJson.getString("phoneNum"))){
				buf.append(" AND upper(model.phoneNum) like:phoneNum");
				map.put("contact","%"+ seachJson.getString("phoneNum").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("weName")&&MyStringUtil.isNotBlank(seachJson.getString("weName"))){
				buf.append(" AND upper(model.weName) like:weName");
				map.put("weName","%"+ seachJson.getString("weName").toUpperCase()+ "%");
			}
		}
		buf.append(" ORDER BY model.regTime DESC");
		return pagedQuery(buf.toString(), page, rows, map);
	}

	@Override
	public SysMember getByWxCode(String wxCode) {
		return getUniqueResult("SELECT model FROM SysMember model WHERE model.weCode = ? ", wxCode);
	}
}

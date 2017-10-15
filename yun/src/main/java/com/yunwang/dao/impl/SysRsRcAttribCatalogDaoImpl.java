package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysRsRcAttribCatalogDaoI;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;

@Repository
public class SysRsRcAttribCatalogDaoImpl extends BaseDaoImpl<SysRsRcAttribCatalog> implements SysRsRcAttribCatalogDaoI{
	@Override
	public List<SysRsRcAttribCatalog> findByCatalogIds(String catalogIds) {
		return find("SELECT model FROM SysRsRcAttribCatalog model "+
					" WHERE model.rsrcCatalogId in ("+catalogIds+") ORDER BY model.orderNo ");
	}

	@Override
	public SysRsRcAttribCatalog getRsrcAttribName(
			SysRsRcAttribCatalog sysRsRcAttribCatalog) {
		StringBuffer buf = new StringBuffer("SELECT model FROM SysRsRcAttribCatalog model "+
				" WHERE model.rsrcCatalogId=:rsrcCatalogId AND model.rsrcAttribName=:rsrcAttribName ");
		if(sysRsRcAttribCatalog.getId() != null){
			buf.append("AND model.id <> "+sysRsRcAttribCatalog.getId());
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rsrcCatalogId",sysRsRcAttribCatalog.getRsrcCatalogId());
		map.put("rsrcAttribName",sysRsRcAttribCatalog.getRsrcAttribName());
		return get(buf.toString(),map);
	}
}

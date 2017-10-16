package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysRsRcAttribDaoI;
import com.yunwang.model.pojo.SysRsRcAttrib;

@Repository
public class SysRsRcAttribDaoImpl extends BaseDaoImpl<SysRsRcAttrib> implements SysRsRcAttribDaoI{

	@Override
	public List<SysRsRcAttrib> findByResourceIds(String resourceIds) {
		OriginalQuerior<SysRsRcAttrib> sqlQuery = new BaseDaoImpl.OriginalQuerior<SysRsRcAttrib>() {
			@Override
			public List<SysRsRcAttrib> query(String ids) {
				return find("SELECT model FROM SysRsRcAttrib model "+
						" WHERE model.rsrcId in ("+ids+")");
			}
		};
		return findByIdsToPaging(resourceIds,sqlQuery);
	}

	@Override
	public SysRsRcAttrib getByResourceAndAttr(Integer resourceId, Integer attrId) {
		return getUniqueResult("SELECT model FROM SysRsRcAttrib model "+
				" WHERE model.rsrcId=? AND model.rsraAttribCatalogId = ?", resourceId,attrId);
	}

	@Override
	public void deleteByAttribCatalogAndRsRcCatalog(Integer attribCatalogId,
			Integer rsRcCatalogId) {
		String hql = "DELETE FROM SysRsRcAttrib model " +
				"WHERE model.rsraAttribCatalogId=:attribCatalogId AND model.rsrcCatalogId=:rsRcCatalogId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("attribCatalogId",attribCatalogId);
		map.put("rsRcCatalogId",rsRcCatalogId);
		executeHql(hql, map);
	}
}

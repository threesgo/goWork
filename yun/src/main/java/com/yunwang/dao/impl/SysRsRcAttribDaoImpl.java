package com.yunwang.dao.impl;

import java.util.List;

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
}

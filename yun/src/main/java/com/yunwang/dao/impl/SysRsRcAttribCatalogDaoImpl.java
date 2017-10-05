package com.yunwang.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysRsRcAttribCatalogDaoI;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;

@Repository
public class SysRsRcAttribCatalogDaoImpl extends BaseDaoImpl<SysRsRcAttribCatalog> implements SysRsRcAttribCatalogDaoI{
	@Override
	public List<SysRsRcAttribCatalog> findByCatalogIds(String catalogIds) {
		return find("SELECT model FROM SysRsRcAttribCatalog model "+
					" WHERE model.rsrcCatalogId in ("+catalogIds+")");
	}
}

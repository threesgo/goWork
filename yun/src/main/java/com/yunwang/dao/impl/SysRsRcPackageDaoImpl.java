package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysRsRcPackageDaoI;
import com.yunwang.model.pojo.SysRsRcPackage;

@Repository
public class SysRsRcPackageDaoImpl extends BaseDaoImpl<SysRsRcPackage> implements SysRsRcPackageDaoI{

	@Override
	public List<SysRsRcPackage> findByPackageType(Integer typeId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("packageType",typeId);
		return find("SELECT model FROM SysRsRcPackage model WHERE model.packageType=:packageType",map);
	}
}

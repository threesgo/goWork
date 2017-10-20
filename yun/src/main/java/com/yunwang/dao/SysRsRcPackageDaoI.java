package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysRsRcPackage;

public interface SysRsRcPackageDaoI  extends BaseDaoI<SysRsRcPackage>{

	List<SysRsRcPackage> findByPackageType(Integer typeId);

}

package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysOrderPackage;

public interface SysOrderPackageDaoI extends BaseDaoI<SysOrderPackage>{

	List<SysOrderPackage> findByOrderId(Integer orderId);



}

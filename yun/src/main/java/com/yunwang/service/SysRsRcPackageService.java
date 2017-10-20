package com.yunwang.service;

import java.util.List;

import com.yunwang.model.pojo.SysRsRcPackage;

public interface SysRsRcPackageService {

	List<SysRsRcPackage> findAll(String order);

	SysRsRcPackage get(Integer id);

}

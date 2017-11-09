package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysPcBrandCatalog;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcPackage;

public interface SysRsRcPackageService {

	List<SysRsRcPackage> findAll(String order);

	SysRsRcPackage get(Integer id);

	List<SysRsRcPackage> findByPackageType(Integer typeId);

	void saveOrUpdateRsRcPackage(SysRsRcPackage updateSysRsRcPackage);

	List<SysResourceRel> findPackageResourceData(Integer packageId);

	Pager<SysResourceRel> findPackageResourceData(Integer packageId, int page,
			int rows, JSONObject seachObj);

	void deletePackageResource(String ids);

	void deleteResourcePackage(Integer packageId);

	List<SysPcBrandCatalog> findAllPcBrandCatalog(Integer packageId);

}

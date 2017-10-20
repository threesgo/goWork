package com.yunwang.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yunwang.dao.SysRsRcPackageDaoI;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.service.SysRsRcPackageService;

/**
 * @author YBF
 * @date 2017-10-20
 * <p></p>
 */
@Service
public class SysRsRcPackageServiceImpl implements SysRsRcPackageService{
	
	private SysRsRcPackageDaoI sysRsRcPackageDao;

	@Override
	public List<SysRsRcPackage> findAll(String order) {
		return sysRsRcPackageDao.findAll();
	}

	@Override
	public SysRsRcPackage get(Integer id) {
		return sysRsRcPackageDao.get(SysRsRcPackage.class,id);
	}

}

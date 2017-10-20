package com.yunwang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceDaoI;
import com.yunwang.dao.SysRsRcPackageDaoI;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.service.SysRsRcPackageService;
import com.yunwang.util.string.MyStringUtil;

/**
 * @author YBF
 * @date 2017-10-20
 * <p></p>
 */
@Service
public class SysRsRcPackageServiceImpl implements SysRsRcPackageService{
	
	@Autowired
	private SysRsRcPackageDaoI sysRsRcPackageDao;
	
	@Autowired
	private SysResourceDaoI sysResourceDao;

	@Override
	public List<SysRsRcPackage> findAll(String order) {
		return sysRsRcPackageDao.findAll();
	}

	@Override
	public SysRsRcPackage get(Integer id) {
		return sysRsRcPackageDao.get(SysRsRcPackage.class,id);
	}

	@Override
	public List<SysRsRcPackage> findByPackageType(Integer typeId) {
		return sysRsRcPackageDao.findByPackageType(typeId);
	}

	@Override
	public void saveOrUpdateRsRcPackage(SysRsRcPackage updateSysRsRcPackage) {
		if(null==updateSysRsRcPackage.getId()){
			updateSysRsRcPackage.setOrderNo(sysRsRcPackageDao.findMaxSeq("orderNo")+1);
			updateSysRsRcPackage.setCode(MyStringUtil.getCombineSeqStr(updateSysRsRcPackage.getOrderNo(),null));
		}
		sysRsRcPackageDao.saveOrUpdate(updateSysRsRcPackage);
	}

	@Override
	public List<SysResource> findPackageResourceData(Integer packageId) {
		// TODO Auto-generated method stub
		return sysResourceDao.findPackageResourceData(packageId);
	}
}

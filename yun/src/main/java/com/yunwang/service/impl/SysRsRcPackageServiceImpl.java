package com.yunwang.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysResourceRelDaoI;
import com.yunwang.dao.SysRsRcPackageDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResourceRel;
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
	private SysResourceRelDaoI sysResourceRelDao;

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
	public List<SysResourceRel> findPackageResourceData(Integer packageId) {
		// TODO Auto-generated method stub
		return sysResourceRelDao.findPackageResourceData(packageId);
	}

	@Override
	public Pager<SysResourceRel> findPackageResourceData(Integer packageId,
			int page, int rows, JSONObject seachObj) {
		// TODO Auto-generated method stub
		return sysResourceRelDao.findPackageResourceData(packageId,page,rows,seachObj);
	}

	@Override
	public void deletePackageResource(String ids) {
		sysResourceRelDao.deletePackageResource(ids);
	}
}

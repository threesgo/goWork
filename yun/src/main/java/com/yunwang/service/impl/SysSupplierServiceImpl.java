package com.yunwang.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysSupplierCatalogDaoI;
import com.yunwang.dao.SysSupplierDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.model.pojo.SysSupplierCatalog;
import com.yunwang.service.SysSupplierService;
import com.yunwang.util.exception.MineException;
import com.yunwang.util.string.MyStringUtil;

@Service
public class SysSupplierServiceImpl implements SysSupplierService {

	@Autowired
	private SysSupplierDaoI sysSupplierDao;
	@Autowired
	private SysSupplierCatalogDaoI sysSupplierCatalogDao;

	@Override
	public void saveOrUpdateSupplierGrid(JSONObject rowData) {
		SysSupplier sysSupplier = null;
		Integer id = rowData.getInt("id");
		if (id > 0) {
			sysSupplier = sysSupplierDao.get(SysSupplier.class, id);
			// 更新
		} else {
			sysSupplier = new SysSupplier();
			sysSupplier.setCode((sysSupplierDao.findMaxSeq("code")+1));			
			sysSupplier.setStatus(1);
			// 正常
		}
		String name = rowData.getString("name");
		
		List<SysSupplier> sysSuppliers = sysSupplierDao.findByName(name,sysSupplier.getId());
		if(sysSuppliers.size()>0){
			throw new MineException("该供应商已经创建,根据供应商名称确认唯一性!");
		}
		sysSupplier.setName(name);
		String contact = rowData.getString("contact");
		sysSupplier.setContact(contact);
		String phoneNum = rowData.getString("phoneNum");
		sysSupplier.setPhoneNum(phoneNum);
		String telNum = rowData.getString("telNum");
		sysSupplier.setTelNum(telNum);
		String address = rowData.getString("address");
		sysSupplier.setAddress(address);
		sysSupplierDao.saveOrUpdate(sysSupplier);
	}

	@Override
	public Pager<SysSupplier> findAll(int page, int rows, JSONObject fromObject) {
		// TODO Auto-generated method stub
		return sysSupplierDao.findAll(page, rows, fromObject);
	}

	@Override
	public void deleteSupplier(String ids) {
		// TODO Auto-generated method stub
		sysSupplierDao.deleteSupplier(ids);
	}

	@Override
	public List<SysSupplier> findAll() {
		// TODO Auto-generated method stub
		return sysSupplierDao.findAll("id");
	}

	@Override
	public List<SysSupplier> findByCatalogId(Integer catalogId) {
		// TODO Auto-generated method stub
		return sysSupplierDao.findByCatalogId(catalogId);
	}

	@Override
	public void updateRelationCatalog(String ids,Integer supplierId) {
		sysSupplierCatalogDao.deleteByProperty("supplierId", supplierId);
		if(MyStringUtil.isNotBlank(ids)){
			String[] idArr = ids.split("\\,");
			for(String id : idArr){
				SysSupplierCatalog sysSupplierCatalog = new SysSupplierCatalog();
				sysSupplierCatalog.setCatalogId(Integer.parseInt(id));
				sysSupplierCatalog.setSupplierId(supplierId);
				sysSupplierCatalogDao.save(sysSupplierCatalog);
			}
		}
	}

	@Override
	public List<SysSupplierCatalog> findAllRelCatalogSupplier(Integer supplierId) {
		// TODO Auto-generated method stub
		return sysSupplierCatalogDao.findByProperty("supplierId", supplierId);
	}

	@Override
	public SysSupplier get(Integer supplierId) {
		return sysSupplierDao.get(SysSupplier.class, supplierId);
	}
}

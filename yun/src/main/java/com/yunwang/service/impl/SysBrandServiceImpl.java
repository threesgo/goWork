package com.yunwang.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysBrandCatalogDaoI;
import com.yunwang.dao.SysBrandDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysBrand;
import com.yunwang.model.pojo.SysBrandCatalog;
import com.yunwang.service.SysBrandService;
import com.yunwang.util.string.MyStringUtil;

@Service
public class SysBrandServiceImpl implements SysBrandService{
	
	@Autowired
	private SysBrandDaoI sysBrandDao;
	
	@Autowired
	private SysBrandCatalogDaoI sysBrandCatalogDao;
	

	@Override
	public Pager<SysBrand> findList(int page, int rows, JSONObject fromObject) {
		// TODO Auto-generated method stub
		return sysBrandDao.findList(page,rows,fromObject);
	}

	@Override
	public void saveOrUpdateBrandGrid(JSONObject rowData) {
		SysBrand sysBrand = null;
		Integer id = rowData.getInt("id");
		if (id > 0) {
			sysBrand = sysBrandDao.get(SysBrand.class, id);
			// 更新
		} else {
			sysBrand = new SysBrand();
			sysBrand.setCode((sysBrandDao.findMaxSeq("code")+1));			
			sysBrand.setStatus(1);
			// 正常
		}
		String name = rowData.getString("name");
		sysBrand.setName(name);
		String info = rowData.getString("info");
		sysBrand.setInfo(info);
		sysBrandDao.saveOrUpdate(sysBrand);
	}

	@Override
	public void deleteBrand(String ids) {
		List<SysBrand> sysBrands = sysBrandDao.findInPropertys("id", ids);
		for(SysBrand sysBrand:sysBrands){
			sysBrand.setStatus(0);
			sysBrandDao.update(sysBrand);
		}
	}

	@Override
	public List<SysBrandCatalog> findAllRelCatalogBrand(Integer brandId) {
		return sysBrandCatalogDao.findByProperty("brandId", brandId);
	}

	@Override
	public void updateRelationCatalog(String ids, Integer brandId) {
		sysBrandCatalogDao.deleteByProperty("brandId", brandId);
		if(MyStringUtil.isNotBlank(ids)){
			String[] idArr = ids.split("\\,");
			for(String id : idArr){
				SysBrandCatalog sysBrandCatalog = new SysBrandCatalog();
				sysBrandCatalog.setCatalogId(Integer.parseInt(id));
				sysBrandCatalog.setBrandId(brandId);
				sysBrandCatalogDao.save(sysBrandCatalog);
			}
		}
	}
}

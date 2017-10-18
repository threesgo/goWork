package com.yunwang.service.impl;

import java.math.BigDecimal;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysWorkerDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysWorker;
import com.yunwang.service.SysWorkerService;
import com.yunwang.util.string.MyStringUtil;

@Service
public class SysWorkerServiceImpl implements SysWorkerService{
	@Autowired
	private SysWorkerDaoI sysWorkerDao;
	
	@Override
	public void saveOrUpdateWorkerGrid(JSONObject rowData) {
		SysWorker sysWorker = null;
		Integer id = rowData.getInt("id");
		if (id > 0) {
			sysWorker = sysWorkerDao.get(SysWorker.class, id);
			// 更新
		} else {
			sysWorker = new SysWorker();
			sysWorker.setStatus(1);
			sysWorker.setCode((sysWorkerDao.findMaxSeq("code")+1)+"");
			// 正常
		}
		String name = rowData.getString("name");
		sysWorker.setName(name);
		Integer sex = rowData.getInt("sex");
		sysWorker.setSex(sex);
		String phoneNum = rowData.getString("phoneNum");
		sysWorker.setPhoneNum(phoneNum);
		String telNum = rowData.getString("telNum");
		sysWorker.setTelNum(telNum);
		String address = rowData.getString("address");
		sysWorker.setAddress(address);
		Integer workType = rowData.getInt("workType");
		sysWorker.setWorkType(workType);
		
		String age = rowData.getString("age");
		if(MyStringUtil.isNotBlank(age)){
			sysWorker.setAge(Integer.parseInt(age));
		}
		String workAge = rowData.getString("workAge");
		if(MyStringUtil.isNotBlank(workAge)){
			sysWorker.setWorkAge(Integer.parseInt(workAge));
		}
		
		Integer education = rowData.getInt("education");
		sysWorker.setEducation(education);
		String birthday = rowData.getString("birthday");
		sysWorker.setBirthday(birthday);
		
		String company = rowData.getString("company");
		sysWorker.setCompany(company);
		
		String wages = rowData.getString("wages");
		sysWorker.setWages(new BigDecimal(wages));
		sysWorkerDao.saveOrUpdate(sysWorker);
	}

	@Override
	public Pager<SysWorker> findAll(int page, int rows, JSONObject fromObject) {
		// TODO Auto-generated method stub
		return sysWorkerDao.findAll(page, rows, fromObject);
	}
	
	@Override
	public void deleteWorker(String ids) {
		// TODO Auto-generated method stub
		sysWorkerDao.deleteWorker(ids);
	}

}

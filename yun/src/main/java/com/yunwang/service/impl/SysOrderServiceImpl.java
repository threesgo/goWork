package com.yunwang.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysOrderDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.service.SysOrderService;
import com.yunwang.util.date.MyDateUtils;

@Service
public class SysOrderServiceImpl implements SysOrderService {
	
	@Autowired
	private SysOrderDaoI sysOrderDao;

	@Override
	public Pager<SysOrder> findAll(int page, int rows, JSONObject seachJson) {
		return sysOrderDao.findPageOrder(page, rows, seachJson);
	}

	@Override
	public void deleteOrder(String ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SysOrder get(Integer id) {
		return sysOrderDao.get(SysOrder.class, id);
	}

	@Override
	public void saveOrUpdateOrder(SysOrder sysOrder) throws Exception {
		if(null != sysOrder.getId()){
			SysOrder dbOrder = sysOrderDao.get(SysOrder.class,sysOrder.getId());
			
			sysOrderDao.update(sysOrder);
		}else{
			sysOrder.setStatus(1);
			sysOrder.setOrderDate(MyDateUtils.getStringByDate(new Date()));
			sysOrder.setStartTime(MyDateUtils.stringToDateTime(sysOrder.getStartTimeStr()));
			sysOrder.setEndTime(MyDateUtils.stringToDateTime(sysOrder.getEndTimeStr()));
			sysOrder.setCode((sysOrderDao.findMaxSeq("orderNo")+1)+"");
			sysOrder.setOrderNo(sysOrderDao.findMaxSeq("orderNo")+1);
			sysOrderDao.save(sysOrder);
		}
	}

	@Override
	public List<SysOrder> findOrderTimeGroup() {
		return sysOrderDao.findOrderTimeGroup();
	}

	@Override
	public List<SysOrder> findByOrderDate(String orderDate) {
		return sysOrderDao.findByOrderDate(orderDate);
	}
}

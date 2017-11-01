package com.yunwang.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysOrderDaoI;
import com.yunwang.dao.SysOrderFlowDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.model.pojo.SysOrderFlow;
import com.yunwang.service.SysOrderService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.date.MyDateUtils;

@Service
public class SysOrderServiceImpl implements SysOrderService {
	
	@Autowired
	private SysOrderDaoI sysOrderDao;
	
	@Autowired
	private SysOrderFlowDaoI sysOrderFlowDao;

	@Override
	public Pager<SysOrder> findAll(int page, int rows, JSONObject seachJson) {
		return sysOrderDao.findPageOrder(page, rows, seachJson);
	}

	@Override
	public void deleteOrder(String ids) {
		List<SysOrder> sysOrders =  sysOrderDao.findInPropertys("id", ids);
		for(SysOrder sysOrder:sysOrders){
			sysOrder.setStatus(0);
			sysOrderDao.update(sysOrder);
		}
	}

	@Override
	public SysOrder get(Integer id) {
		return sysOrderDao.get(SysOrder.class, id);
	}

	@Override
	public void saveOrUpdateOrder(SysOrder sysOrder) throws Exception {
		if(null != sysOrder.getId()){
			SysOrder dbOrder = sysOrderDao.get(SysOrder.class,sysOrder.getId());
			dbOrder.setName(sysOrder.getName());
			dbOrder.setContact(sysOrder.getContact());
			dbOrder.setContactTel(sysOrder.getContactTel());
			dbOrder.setAddress(sysOrder.getAddress());
			dbOrder.setInfo(sysOrder.getInfo());
			dbOrder.setTotalArea(sysOrder.getTotalArea());
			dbOrder.setTotalAmount(sysOrder.getTotalAmount());
			dbOrder.setStartTime(sysOrder.getStartTime());
			dbOrder.setEndTime(sysOrder.getEndTime());
			dbOrder.setOrderType(sysOrder.getOrderType());
			dbOrder.setBalconyNum(sysOrder.getBalconyNum());
			dbOrder.setRoomNum(sysOrder.getRoomNum());
			dbOrder.setHallNum(sysOrder.getHallNum());
			dbOrder.setKitchenNum(sysOrder.getKitchenNum());
			dbOrder.setToiletNum(sysOrder.getToiletNum());
			sysOrderDao.update(sysOrder);
		}else{
			sysOrder.setStatus(1);
			sysOrder.setOrderDate(MyDateUtils.getStringByDate(new Date()));
			//sysOrder.setStartTime(MyDateUtils.stringToDateTime(sysOrder.getStartTimeStr()));
			//sysOrder.setEndTime(MyDateUtils.stringToDateTime(sysOrder.getEndTimeStr()));
			sysOrder.setCode((sysOrderDao.findMaxSeq("orderNo")+1)+"");
			sysOrder.setOrderNo(sysOrderDao.findMaxSeq("orderNo")+1);
			sysOrderDao.save(sysOrder);
			
			//自动创建流程
			List<SysDataDictionary> flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
			for(SysDataDictionary sysDataDictionary:flowList){
				SysOrderFlow sysOrderFlow = new SysOrderFlow();
				sysOrderFlow.setName(sysDataDictionary.getName());
				sysOrderFlow.setFlowType(Integer.parseInt(sysDataDictionary.getValue()));
				sysOrderFlow.setOrderNo(sysOrderFlowDao.findMaxSeqByPfield(
						"orderNo", "orderId", sysOrder.getId()));
				sysOrderFlow.setStatus(1);
				sysOrderFlow.setOrderId(sysOrder.getId());
				sysOrderFlowDao.save(sysOrderFlow);
			}
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

	@Override
	public List<SysOrderFlow> findOrderFlow(Integer orderId) {
		return sysOrderFlowDao.findByOrderId(orderId);
	}

	@Override
	public SysOrderFlow getOrderFlow(Integer orderFlowId) {
		return sysOrderFlowDao.get(SysOrderFlow.class,orderFlowId);
	}
}

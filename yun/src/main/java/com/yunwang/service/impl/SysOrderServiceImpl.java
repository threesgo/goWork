package com.yunwang.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysOrderDaoI;
import com.yunwang.dao.SysOrderFlowDaoI;
import com.yunwang.dao.SysOrderPackageDaoI;
import com.yunwang.dao.SysOrderResourceDaoI;
import com.yunwang.dao.SysOrderWorkerDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.model.pojo.SysOrderFlow;
import com.yunwang.model.pojo.SysOrderPackage;
import com.yunwang.service.SysOrderService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.date.MyDateUtils;
import com.yunwang.util.string.MyStringUtil;

@Service
public class SysOrderServiceImpl implements SysOrderService {
	
	@Autowired
	private SysOrderDaoI sysOrderDao;
	@Autowired
	private SysOrderFlowDaoI sysOrderFlowDao;
	@Autowired
	private SysOrderPackageDaoI sysOrderPackageDao;
	@Autowired
	private SysOrderWorkerDaoI sysOrderWorkerDao;
	@Autowired
	private SysOrderResourceDaoI sysOrderResourceDao;

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
		Integer orderId;
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
			sysOrderDao.update(dbOrder);
			orderId = dbOrder.getId();
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
						"orderNo", "orderId", sysOrder.getId())+1);
				sysOrderFlow.setStatus(1);
				sysOrderFlow.setOrderId(sysOrder.getId());
				sysOrderFlowDao.save(sysOrderFlow);
			}
			orderId = sysOrder.getId();
		}
		
		//新建订单与套餐关联
		sysOrderPackageDao.deleteByProperty("rsrcPackageId", orderId);
		if(MyStringUtil.isNotBlank(sysOrder.getOrderPackages())){
			String[] ids = sysOrder.getOrderPackages().split("\\,");
			for(String id:ids){
				SysOrderPackage sysOrderPackage = new SysOrderPackage();
				sysOrderPackage.setOrderId(orderId);
				sysOrderPackage.setRsrcPackageId(Integer.parseInt(id));
				sysOrderPackageDao.save(sysOrderPackage);
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

	@Override
	public void dragOrderFlow(Integer point, Integer targetId, Integer sourceId) {
		
		SysOrderFlow source = sysOrderFlowDao.get(SysOrderFlow.class,sourceId);
		SysOrderFlow target = sysOrderFlowDao.get(SysOrderFlow.class,targetId);
		
		//只有上移或者下移
		//下移或者下移的方法一样（大于source减1，大于target加1）
		
		//先把大于移动节点顺序的节点减1
		List<SysOrderFlow> sourceGreaters = sysOrderFlowDao.findGreaterOrder(source.getOrderId(),source.getOrderNo());
		for(SysOrderFlow orderFlow:sourceGreaters){
			orderFlow.setOrderNo(orderFlow.getOrderNo()-1);
			sysOrderFlowDao.update(orderFlow);
		}
		
		//再把大于移到节点顺序的节点加1
		List<SysOrderFlow> targetGreaters = sysOrderFlowDao.findGreaterOrder(target.getOrderId(),target.getOrderNo());
		for(SysOrderFlow orderFlow:targetGreaters){
			orderFlow.setOrderNo(orderFlow.getOrderNo()+1);
			sysOrderFlowDao.update(orderFlow);
		}
		
		if(point == 1){
			source.setOrderNo(target.getOrderNo()+1);
		}else if(point == 2){
			source.setOrderNo(target.getOrderNo());
			target.setOrderNo(target.getOrderNo()+1);
		}
		
	}

	@Override
	public List<SysOrderPackage> findOrderPackage(Integer orderId) {
		return sysOrderPackageDao.findByOrderId(orderId);
	}

	@Override
	public void saveOrUpdateOrderFlow(SysOrderFlow sysOrderFlow) {
		if(null != sysOrderFlow.getId()){
			SysOrderFlow dbSysOrderFlow = sysOrderFlowDao.get(SysOrderFlow.class,sysOrderFlow.getId());
			dbSysOrderFlow.setFlowType(sysOrderFlow.getFlowType());
			dbSysOrderFlow.setStartTime(sysOrderFlow.getStartTime());
			dbSysOrderFlow.setEndTime(sysOrderFlow.getEndTime());
			dbSysOrderFlow.setInfo(sysOrderFlow.getInfo());
			dbSysOrderFlow.setName(sysOrderFlow.getName());
			sysOrderFlowDao.update(dbSysOrderFlow);
		}else{
			sysOrderFlow.setOrderNo(sysOrderFlowDao.findMaxSeqByPfield(
					"orderNo", "orderId", sysOrderFlow.getOrderId())+1);
			sysOrderFlow.setStatus(1);
			sysOrderFlowDao.save(sysOrderFlow);
		}
	}

	@Override
	public void deleteOrderFlow(SysOrderFlow sysOrderFlow) {
		// TODO Auto-generated method stub
		//删除步骤与产品
		sysOrderWorkerDao.deleteByProperty("orderFlowId", sysOrderFlow.getId());
		//删除步骤与工人
		sysOrderResourceDao.deleteByProperty("orderFlowId", sysOrderFlow.getId());
		sysOrderFlowDao.deleteByProperty("id", sysOrderFlow.getId());
	}
}

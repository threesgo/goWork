package com.yunwang.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.service.SysOrderService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;
@Action(
	value = "sysOrderAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/sysOrder/showIndex.jsp"),
		@Result(name = "manageIndex",location="/WEB-INF/web/sysOrder/manageIndex.jsp"),
		@Result(name = "saveOrUpdatePage",location="/WEB-INF/web/sysOrder/saveOrUpdatePage.jsp"),
		@Result(name = "workerAndResource",location="/WEB-INF/web/sysOrder/workerAndResource.jsp")
	}
)
public class SysOrderAction  extends AbstractLoginAction{

	private final static Logger LOG =Logger.getLogger(SysOrderAction.class);
	/*
	 * @date 2017-10-17
	 * @author YBF
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysOrderService sysOrderService;
	
	private Map<String,Object> hashMap;
	private SysOrder sysOrder;
	private String id;
	private String ids;
	private String jsonStr;
	private List<SysDataDictionary> flowList;
	private List<SysDataDictionary> dTypeList;
	
	@Override
	public String execute() throws Exception {
		hashMap = new HashMap<String,Object>();
		return "index";
	}
	
	public String manageIndex(){
		return "manageIndex";
	}
	
	public String findTree(){
		JSONArray jsonArr=new JSONArray();
		if (id==null) {
			JSONObject json=new JSONObject();
			json.put("id", "root");
			json.put("text", "订单列表");
			JSONObject obj=new JSONObject();
			obj.put("id",0);
			json.put("attributes",obj);
			json.put("state", "closed");
			jsonArr.add(json);
		}else if("root".equals(id)){
			//时间分组
			List<SysOrder> timeGroups = sysOrderService.findOrderTimeGroup();
			for(SysOrder order:timeGroups){
				JSONObject obj=new JSONObject();
				obj.put("id", "t"+order.getOrderDate());
				obj.put("text", order.getOrderDate());
				jsonArr.add(obj);
			}
		}else if("id".startsWith("t")){
			//具体的订单
			List<SysOrder> timeGroups = sysOrderService.findByOrderDate(id.substring(1, id.length()));
			for(SysOrder order:timeGroups){
				JSONObject obj=new JSONObject();
				obj.put("id", "o"+order.getId());
				obj.put("text", order.getName());
				obj.put("attributes",JSONObject.fromObject(order));
				obj.put("state", "closed");
				jsonArr.add(obj);
			}
		}else{
			//订单步骤
			
		}
		return ajaxText(jsonArr);
	}
	
	
	public String infoData(){
		sysOrder = sysOrderService.get(sysOrder.getId());
		JSONArray jsonArr=new JSONArray();
		addInfoData(jsonArr);
		return ajaxText(jsonArr);
	}

	public String listData(){
		JSONObject obj=new JSONObject();
		Pager<SysOrder> pager = sysOrderService.findAll(page,rows,JSONObject.fromObject(jsonStr));
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
  			obj.put("total", pager.getTotalRows());
  			arr = JSONArray.fromObject(pager.getData());
  	    }else{
  	        obj.put("total",0); 
  	    }
		obj.put("rows", arr);
  		return ajaxText(JSONObject.fromObject(obj).toString());
	}
	
	
	/**
	 * @date 2017-10-10
	 * @author YBF
	 * @return
	 * <p>保存表格行数据</p>
	 */
	public String saveOrUpdatePage(){
		dTypeList = BaseDataDictionaryUtil.baseDataMap.get(7);
		return "saveOrUpdatePage";
	}
	
	/**
	 * @date 2017-10-10
	 * @author YBF
	 * @return
	 * <p>保存表格行数据</p>
	 */
	public String saveOrUpdateOrder(){
		try{
			sysOrderService.saveOrUpdateOrder(sysOrder);
			return success("更新成功!",JSONObject.fromObject(sysOrder));
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("更新失败!",e);
		}
	}
	
	public String workerAndResource(){
		return "workerAndResource";
	}
	
	public String deleteOrder(){
		try{
			sysOrderService.deleteOrder(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	//后期根据国际化来做
	private void addInfoData(JSONArray jsonArr) {
		JSONObject json_code=new JSONObject();
		json_code.put("attrName", "编号");
		json_code.put("value", sysOrder.getCode());
		jsonArr.add(json_code);
		
		JSONObject json_name=new JSONObject();
		json_name.put("attrName", "名称");
		json_name.put("value", sysOrder.getName());
		jsonArr.add(json_name);
		
		JSONObject totalArea=new JSONObject();
		totalArea.put("attrName", "总面积");
		totalArea.put("value", sysOrder.getTotalArea());
		jsonArr.add(totalArea);
		
		JSONObject totalAmount=new JSONObject();
		totalAmount.put("attrName", "总定价");
		totalAmount.put("value", sysOrder.getTotalAmount());
		jsonArr.add(totalAmount);
		
		JSONObject orderType=new JSONObject();
		orderType.put("attrName", "装修类型");
		orderType.put("value", sysOrder.getOrderType());
		jsonArr.add(orderType);
		
		JSONObject orderDate=new JSONObject();
		orderDate.put("attrName", "下单时间");
		orderDate.put("value", sysOrder.getOrderDate());
		jsonArr.add(orderDate);
		
		JSONObject contact=new JSONObject();
		contact.put("attrName", "联系人");
		contact.put("value", sysOrder.getContact());
		jsonArr.add(contact);
		
		JSONObject contactTel=new JSONObject();
		contactTel.put("attrName", "联系方式");
		contactTel.put("value", sysOrder.getContactTel());
		jsonArr.add(contactTel);
		
		JSONObject address=new JSONObject();
		address.put("attrName", "装修地址");
		address.put("value", sysOrder.getAddress());
		jsonArr.add(address);
		
		JSONObject time=new JSONObject();
		time.put("attrName", "预计装修时间");
		time.put("value", sysOrder.getStartTimeStr()+"-"+sysOrder.getEndTimeStr());
		jsonArr.add(time);
		
		JSONObject info=new JSONObject();
		info.put("attrName", "备注");
		info.put("value", sysOrder.getInfo());
		jsonArr.add(info);
		
		JSONObject status=new JSONObject();
		status.put("attrName", "状态");
		status.put("value", sysOrder.getStatus());
		jsonArr.add(info);
	}

	public Map<String, Object> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<String, Object> hashMap) {
		this.hashMap = hashMap;
	}

	public SysOrder getSysOrder() {
		return sysOrder;
	}

	public void setSysOrder(SysOrder sysOrder) {
		this.sysOrder = sysOrder;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public List<SysDataDictionary> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<SysDataDictionary> flowList) {
		this.flowList = flowList;
	}

	public List<SysDataDictionary> getdTypeList() {
		return dTypeList;
	}

	public void setdTypeList(List<SysDataDictionary> dTypeList) {
		this.dTypeList = dTypeList;
	}
}

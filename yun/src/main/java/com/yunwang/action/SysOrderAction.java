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
import com.yunwang.util.action.AbstractLoginAction;
@Action(
	value = "sysOrderAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/sysOrder/index.jsp")
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
	private String ids;
	private String jsonStr;
	private List<SysDataDictionary> flowList;
	
	@Override
	public String execute() throws Exception {
		hashMap = new HashMap<String,Object>();
		
		
		return "index";
	}
	
	public String listData(){
		JSONObject obj=new JSONObject();
		Pager<SysOrder> pager = sysOrderService.findAll(page,rows,
				JSONObject.fromObject(jsonStr));
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
	public String saveOrUpdateOrderGrid(){
		try{
			JSONObject obj = JSONObject.fromObject(jsonStr);
			sysOrderService.saveOrUpdateOrderGrid(obj);
			return success("行数据保存成功!",obj);
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("行数据保存失败!");
		}
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
}

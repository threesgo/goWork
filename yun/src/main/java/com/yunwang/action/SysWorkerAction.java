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
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.model.pojo.SysWorker;
import com.yunwang.service.SysWorkerService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;
@Action(
	value = "sysWorkerAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/sysWorker/index.jsp")
	}
)
public class SysWorkerAction  extends AbstractLoginAction{

	private final static Logger LOG =Logger.getLogger(SysWorkerAction.class);
	/*
	 * @date 2017-10-17
	 * @author YBF
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysWorkerService sysWorkerService;
	
	private Map<String,Object> hashMap;
	private SysWorker sysWorker;
	private String ids;
	private String jsonStr;
	private List<SysDataDictionary> flowList;
	
	@Override
	public String execute() throws Exception {
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		hashMap = new HashMap<String,Object>();
		hashMap.put("flowListArr", JSONArray.fromObject(BaseDataDictionaryUtil.baseDataMap.get(4)));
		JSONObject obj = new JSONObject();
		for(SysDataDictionary dictionary:flowList){
			obj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("flowListObj",obj);
		return "index";
	}
	
	public String listData(){
		JSONObject obj=new JSONObject();
		Pager<SysSupplier> pager = sysWorkerService.findAll(page,rows,
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
	public String saveOrUpdateWorkerGrid(){
		try{
			JSONObject obj = JSONObject.fromObject(jsonStr);
			sysWorkerService.saveOrUpdateWorkerGrid(obj);
			return success("行数据保存成功!",obj);
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("行数据保存失败!");
		}
	}

	public Map<String, Object> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<String, Object> hashMap) {
		this.hashMap = hashMap;
	}

	public SysWorker getSysWorker() {
		return sysWorker;
	}

	public void setSysWorker(SysWorker sysWorker) {
		this.sysWorker = sysWorker;
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

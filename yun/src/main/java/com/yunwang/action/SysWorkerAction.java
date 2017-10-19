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
	private List<SysDataDictionary> educationList;
	private List<SysDataDictionary> sexList;
	
	@Override
	public String execute() throws Exception {
		hashMap = new HashMap<String,Object>();
		
		//流程数据
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		hashMap.put("flowArr", JSONArray.fromObject(BaseDataDictionaryUtil.baseDataMap.get(4)));
		JSONObject flowObj = new JSONObject();
		for(SysDataDictionary dictionary:flowList){
			flowObj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("flowObj",flowObj);
		
		//学历数据
		educationList = BaseDataDictionaryUtil.baseDataMap.get(5);
		hashMap.put("educationArr", JSONArray.fromObject(BaseDataDictionaryUtil.baseDataMap.get(5)));
		JSONObject educationObj = new JSONObject();
		for(SysDataDictionary dictionary:educationList){
			educationObj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("educationObj",educationObj);
		
		//性别数据
		sexList = BaseDataDictionaryUtil.baseDataMap.get(6);
		hashMap.put("sexArr", JSONArray.fromObject(BaseDataDictionaryUtil.baseDataMap.get(6)));
		JSONObject sexObj = new JSONObject();
		for(SysDataDictionary dictionary:sexList){
			sexObj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("sexObj",sexObj);
		
		return "index";
	}
	
	public String listData(){
		JSONObject obj=new JSONObject();
		Pager<SysWorker> pager = sysWorkerService.findAll(page,rows,
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
	
	
	public String deleteWorker(){
		try{
			sysWorkerService.deleteWorker(ids);
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

	public List<SysDataDictionary> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<SysDataDictionary> educationList) {
		this.educationList = educationList;
	}

	public List<SysDataDictionary> getSexList() {
		return sexList;
	}

	public void setSexList(List<SysDataDictionary> sexList) {
		this.sexList = sexList;
	}
}

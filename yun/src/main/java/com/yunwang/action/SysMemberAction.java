package com.yunwang.action;

import java.util.Date;
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
import com.yunwang.model.pojo.SysMember;
import com.yunwang.service.SysMemberService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractUpDownAction;

@Action(
	value = "sysMemberAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/sysMember/index.jsp"),
		@Result(name = "info",location="/WEB-INF/web/sysMember/info.jsp"),
		@Result(name = "register",location="/WEB-INF/web/sysMember/register.jsp"),
		@Result(name = "edit",location="/WEB-INF/web/sysMember/edit.jsp"),
		@Result(name = "listPage",location="/WEB-INF/web/sysMember/listPage.jsp")
	}
)
public class SysMemberAction extends AbstractUpDownAction{

	private final static Logger LOG =Logger.getLogger(SysMemberAction.class);
	/*
	 * @date 2018-1-2
	 * @author YBF
	 * TODO  会员管理
	 */
	private static final long serialVersionUID = 1L;
	
	private String jsonStr;
	private SysMember sysMember;
	private List<SysDataDictionary> memberTypeList;
	private Map<String,Object> hashMap = new HashMap<String,Object>();
	
	
	@Autowired
	private SysMemberService sysMemberService;
	
	
	 
	public String execute(){
		getMemberTypeData();
		return "index";
	}
	
	/**
	 * @date 2018-1-2
	 * @author YBF
	 * @return
	 * <p>基本信息查看</p>
	 */
	public String info(){
		return "info";
	}
	
	/**
	 * @date 2018-1-2
	 * @author YBF
	 * @return
	 * <p>注册（新增）</p>
	 */
	public String register(){
		
		sysMember.setRegTime(new Date());

		
		
		sysMemberService.saveOrUpdate(sysMember);
		return "register";
	}
	
	/**
	 * @date 2018-1-2
	 * @author YBF
	 * @return
	 * <p>编辑页面</p>
	 */
	public String edit(){
		return "edit";
	}
	
	/**
	 * @date 2018-1-2
	 * @author YBF
	 * @return
	 * <p>更新信息操作</p>
	 */
	public String update(){
		return null;
	}
	
	
	/**
	 * @date 2018-1-2
	 * @author YBF
	 * @return
	 * <p>更新授权操作</p>
	 */
	public String updateAuthorize(){
		try{
			SysMember updateSysMember = sysMemberService.get(sysMember.getId());
			updateSysMember.setIsAuthorize(sysMember.getIsAuthorize());
			sysMemberService.saveOrUpdate(updateSysMember);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String listPage(){
		getMemberTypeData();
		return "listPage";
	}

	private void getMemberTypeData() {
		memberTypeList = BaseDataDictionaryUtil.baseDataMap.get(8);
		JSONObject memberTypeObj = new JSONObject();
		for(SysDataDictionary dictionary:memberTypeList){
			memberTypeObj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("memberTypeObj",memberTypeObj);
	}
	
	/**
	 * @date 2018-1-2
	 * @author YBF
	 * @return
	 * <p>数据列表查询</p>
	 */
	public String listData(){
		JSONObject obj=new JSONObject();
		Pager<SysMember> pager = sysMemberService.findAll(page,rows,JSONObject.fromObject(jsonStr));
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
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public SysMember getSysMember() {
		return sysMember;
	}

	public void setSysMember(SysMember sysMember) {
		this.sysMember = sysMember;
	}

	public List<SysDataDictionary> getMemberTypeList() {
		return memberTypeList;
	}

	public void setMemberTypeList(List<SysDataDictionary> memberTypeList) {
		this.memberTypeList = memberTypeList;
	}

	public Map<String, Object> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<String, Object> hashMap) {
		this.hashMap = hashMap;
	}
}

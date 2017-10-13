package com.yunwang.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcBaseData;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;

@Action(
	value = "resourcePackageAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resourcePackage/index.jsp")
	}
)
public class ResourcePackageAction extends AbstractLoginAction{
	
	private final static Logger LOG =Logger.getLogger(ResourcePackageAction.class);

	/*
	 * @date 2017-9-27
	 * @author YBF
	 * TODO  系统资源类型管理
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysResourceService sysResourceService;
	
	@Autowired
	private String id;
	
	
	@Override
	public String execute() throws Exception {
		return "index";
	} 
	
	/**
	 * @return  
	 * <p>查询树结构</p>
	 */
	public String findTree(){
		JSONArray jsonArr=new JSONArray();
		if (id==null) {
			JSONObject json=new JSONObject();
			json.put("id", "root");
			json.put("text", "产品组合");
			JSONObject obj=new JSONObject();
			obj.put("id",0);
			json.put("attributes",obj);
			json.put("state", "closed");
			jsonArr.add(json);
		}else{
		
		}
		return ajaxText(jsonArr);
	}
	
	private JSONObject getJson(SysRsRcCatalog sysRcRsrcOrg){
		JSONObject json=new JSONObject();
		json.put("id","org"+sysRcRsrcOrg.getId());
		json.put("text",sysRcRsrcOrg.getCatalogCode()+","+sysRcRsrcOrg.getCatalogName());
		json.put("attributes", JSONObject.fromObject(sysRcRsrcOrg));
		json.put("state", "closed");
		return json;
	}
	
	/**
	 * @return 查看组合信息
	 */
	public String info(){
		return "info";
	}
	
	/**
	 * @return  获取类别需要编辑的属性，打包成json数组  （转换model类的属性，排除不可编辑行，通过国际化显示名称）
	 */
	public String infoData(){
		JSONArray jsonArr=new JSONArray();
//		sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
//		JSONObject json_name=new JSONObject();
//		json_name.put("attrName", "组合代号");
//		json_name.put("value", sysRsRcCatalog.getCatalogName());
//		jsonArr.add(json_name);
//		
//		JSONObject json_code=new JSONObject();
//		json_code.put("attrName","组合名称");
//		json_code.put("value", sysRsRcCatalog.getCatalogCode());
//		jsonArr.add(json_code);
//		
//		JSONObject json_type=new JSONObject();
//		json_type.put("attrName","组合描述");
//		json_type.put("value",BaseDataDictionaryUtil.valueMap.get(1).get(sysRsRcCatalog.getCatalogType().toString()).getName());
//		jsonArr.add(json_type);
		return ajaxText(jsonArr);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

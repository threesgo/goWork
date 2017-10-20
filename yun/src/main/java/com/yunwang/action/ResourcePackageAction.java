package com.yunwang.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.service.SysRsRcPackageService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;

@Action(
	value = "resourcePackageAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resourcePackage/index.jsp"),
		@Result(name = "info",location="/WEB-INF/web/resourcePackage/info.jsp")
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
	private SysRsRcPackageService sysRsRcPackageService;
	
	private SysRsRcPackage sysRsrcPackage;
	private String id;
	private List<SysDataDictionary> packageTypeList;
	
	
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
			json.put("text", "组合套餐");
			JSONObject obj=new JSONObject();
			obj.put("id",0);
			json.put("attributes",obj);
			json.put("state", "closed");
			jsonArr.add(json);
		}else{
			List<SysRsRcPackage> sysRsrcPackages = sysRsRcPackageService.findAll("orderNo");
			for(SysRsRcPackage pack : sysRsrcPackages){
				jsonArr.add(getJson(pack));
			}
		}
		return ajaxText(jsonArr);
	}
	
	private JSONObject getJson(SysRsRcPackage sysRsrcPackage){
		JSONObject json=new JSONObject();
		json.put("id","pac"+sysRsrcPackage.getId());
		json.put("text",sysRsrcPackage.getName());
		json.put("attributes", JSONObject.fromObject(sysRsrcPackage));
		json.put("state", "open");
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
		
		sysRsrcPackage = sysRsRcPackageService.get(sysRsrcPackage.getId());
		JSONObject json_name=new JSONObject();
		json_name.put("attrName", "组合名称");
		json_name.put("value", sysRsrcPackage.getName());
		jsonArr.add(json_name);
		
		JSONObject json_code=new JSONObject();
		json_code.put("attrName","组合代号");
		json_code.put("value", sysRsrcPackage.getCode());
		jsonArr.add(json_code);
		
		if(sysRsrcPackage.getPackegeType() == 1){
			JSONObject minPrice=new JSONObject();
			minPrice.put("attrName","价格最小值");
			minPrice.put("value", sysRsrcPackage.getMinPrice());
			jsonArr.add(minPrice);
			
			JSONObject maxPrice=new JSONObject();
			maxPrice.put("attrName","价格最大值");
			maxPrice.put("value", sysRsrcPackage.getMaxPrice());
			jsonArr.add(maxPrice);
		}
		return ajaxText(jsonArr);
	}
	
	/**
	 * @date 2017-10-20
	 * @author YBF
	 * @return
	 * <p>更新产品组合</p>
	 */
	public String saveOrUpdatePackage(){
		try{
			packageTypeList = BaseDataDictionaryUtil.baseDataMap.get(8);
		}catch(Exception e){
			LOG.error(e.getMessage());
		}
		return null;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysRsRcPackage getSysRsrcPackage() {
		return sysRsrcPackage;
	}

	public void setSysRsrcPackage(SysRsRcPackage sysRsrcPackage) {
		this.sysRsrcPackage = sysRsrcPackage;
	}

	public List<SysDataDictionary> getPackageTypeList() {
		return packageTypeList;
	}

	public void setPackageTypeList(List<SysDataDictionary> packageTypeList) {
		this.packageTypeList = packageTypeList;
	}
}

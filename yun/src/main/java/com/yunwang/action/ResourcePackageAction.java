package com.yunwang.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.service.SysRsRcPackageService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;

@Action(
	value = "resourcePackageAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resourcePackage/index.jsp"),
		@Result(name = "info",location="/WEB-INF/web/resourcePackage/info.jsp"),
		@Result(name = "childrenPage",location="/WEB-INF/web/resourcePackage/childrenPage.jsp"),
		@Result(name = "saveOrUpdatePackagePage",location="/WEB-INF/web/resourcePackage/saveOrUpdatePackage.jsp"),
		@Result(name = "resourceList",location="/WEB-INF/web/resourcePackage/resourceList.jsp")
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
	
	private SysRsRcPackage sysRsRcPackage;
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
		}else if("root".equals(id)){
			packageTypeList = BaseDataDictionaryUtil.baseDataMap.get(8);
			for(SysDataDictionary dic : packageTypeList){
				JSONObject json=new JSONObject();
				json.put("id", "dic"+dic.getValue());
				json.put("text", dic.getName());
				json.put("attributes",JSONObject.fromObject(dic));
				json.put("state", "closed");
				jsonArr.add(json);
			}
		}else{
			//List<SysRsRcPackage> sysRsrcPackages = sysRsRcPackageService.findAll("orderNo");
			List<SysRsRcPackage> sysRsrcPackages = sysRsRcPackageService.findByPackageType(Integer.parseInt(id.substring(3,id.length())));
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
	 * @date 2017-10-20
	 * @author YBF
	 * @return
	 * <p>组合套餐列表/p>
	 */
	public String packageList(){
		return "childrenPage";
	}
	
	public String childrenData(){
		return ajaxText(JSONArray.fromObject(
				sysRsRcPackageService.findByPackageType(sysRsRcPackage.getPackageType())));
	}
	
	/**
	 * @return 组合关联的资源页面
	 */
	public String resourceList(){
		return "resourceList";
	}
	
	/**
	 * @return 组合关联的资源数据
	 */
	public String packageResourceData(){
		List<SysResource> sysResources = sysRsRcPackageService.findPackageResourceData(sysRsRcPackage.getId());
		return ajaxJSONArr(sysResources);
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
		
		sysRsRcPackage = sysRsRcPackageService.get(sysRsRcPackage.getId());
		JSONObject json_name=new JSONObject();
		json_name.put("attrName", "组合名称");
		json_name.put("value", sysRsRcPackage.getName());
		jsonArr.add(json_name);
		
		JSONObject json_code=new JSONObject();
		json_code.put("attrName","组合代号");
		json_code.put("value", sysRsRcPackage.getCode());
		jsonArr.add(json_code);
		
		if(sysRsRcPackage.getPackageType() == 1){
			JSONObject minPrice=new JSONObject();
			minPrice.put("attrName","价格最小值");
			minPrice.put("value", sysRsRcPackage.getMinPrice());
			jsonArr.add(minPrice);
			
			JSONObject maxPrice=new JSONObject();
			maxPrice.put("attrName","价格最大值");
			maxPrice.put("value", sysRsRcPackage.getMaxPrice());
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
	public String saveOrUpdatePackagePage(){
		packageTypeList = BaseDataDictionaryUtil.baseDataMap.get(8);
		return "saveOrUpdatePackagePage";
	}
	
	/**
	 * @date 2017-10-20
	 * @author YBF
	 * @return
	 * <p>更新产品组合</p>
	 */
	public String saveOrUpdatePackage(){
		try{
			if(null!=sysRsRcPackage.getId()){
				SysRsRcPackage updateSysRsRcPackage = sysRsRcPackageService.get(sysRsRcPackage.getId());
				updateSysRsRcPackage.setCode(sysRsRcPackage.getCode());
				updateSysRsRcPackage.setName(sysRsRcPackage.getName());
				updateSysRsRcPackage.setPackageType(sysRsRcPackage.getPackageType());
				updateSysRsRcPackage.setMinPrice(sysRsRcPackage.getMinPrice());
				updateSysRsRcPackage.setMaxPrice(sysRsRcPackage.getMaxPrice());
				sysRsRcPackageService.saveOrUpdateRsRcPackage(updateSysRsRcPackage);
			}else{
				sysRsRcPackageService.saveOrUpdateRsRcPackage(sysRsRcPackage);
			}
			return success("操作成功!",JSONObject.fromObject(sysRsRcPackage));
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysRsRcPackage getSysRsRcPackage() {
		return sysRsRcPackage;
	}

	public void setSysRsRcPackage(SysRsRcPackage sysRsRcPackage) {
		this.sysRsRcPackage = sysRsRcPackage;
	}

	public List<SysDataDictionary> getPackageTypeList() {
		return packageTypeList;
	}

	public void setPackageTypeList(List<SysDataDictionary> packageTypeList) {
		this.packageTypeList = packageTypeList;
	}
}

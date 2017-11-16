package com.yunwang.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysBrand;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysPcBrandCatalog;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.service.SysBrandService;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.service.SysRsRcPackageService;
import com.yunwang.service.SysSupplierService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractUpDownAction;
import com.yunwang.util.annotation.DownloadAnnotation;
import com.yunwang.util.collection.CollectionUtil;

@Action(
	value = "resourcePackageAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resourcePackage/index.jsp"),
		@Result(name = "info",location="/WEB-INF/web/resourcePackage/info.jsp"),
		@Result(name = "childrenPage",location="/WEB-INF/web/resourcePackage/childrenPage.jsp"),
		@Result(name = "saveOrUpdatePackagePage",location="/WEB-INF/web/resourcePackage/saveOrUpdatePackage.jsp"),
		@Result(name = "packageResourceList",location="/WEB-INF/web/resourcePackage/packageResourceList.jsp"),
		@Result(name = "packageBrand",location="/WEB-INF/web/resourcePackage/packageBrand.jsp"),
		@Result(name="exportResource",type="stream",
		params={"encode","true","contentType","application/vnd.ms-excel;charset=UTF-8",
		  "inputName","exportResourceStream","contentDisposition","attachment;filename=${exportResourceFileName}"}),
	}
)
public class ResourcePackageAction extends AbstractUpDownAction{
	
	private final static Logger LOG =Logger.getLogger(ResourcePackageAction.class);

	/*
	 * @date 2017-9-27
	 * @author YBF
	 * TODO  系统资源类型管理
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysResourceTypeService sysResourceTypeService;
	@Autowired
	private SysRsRcPackageService sysRsRcPackageService;
	@Autowired
	private SysSupplierService sysSupplierService;
	@Autowired
	private SysBrandService sysBrandService;
	
	private SysRsRcPackage sysRsRcPackage;
	private String id;
	private String jsonStr;
	private List<SysDataDictionary> flowList;
	private Map<String,Object> hashMap;
	private List<SysSupplier> sysSuppliers;
	private List<SysBrand> sysBrands;
	private String ids;
	
	
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
			json.put("text", "产品套餐");
			JSONObject obj=new JSONObject();
			obj.put("id",0);
			json.put("attributes",obj);
			json.put("state", "closed");
			jsonArr.add(json);
		}else if("root".equals(id)){
//			packageTypeList = BaseDataDictionaryUtil.baseDataMap.get(8);
//			for(SysDataDictionary dic : packageTypeList){
//				JSONObject json=new JSONObject();
//				json.put("id", "dic"+dic.getValue());
//				json.put("text", dic.getName());
//				json.put("attributes",JSONObject.fromObject(dic));
//				json.put("state", "closed");
//				jsonArr.add(json);
//			}
//		}else{
			List<SysRsRcPackage> sysRsrcPackages = sysRsRcPackageService.findAll("orderNo");
			//List<SysRsRcPackage> sysRsrcPackages = sysRsRcPackageService.findByPackageType(Integer.parseInt(id.substring(3,id.length())));
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
				sysRsRcPackageService.findAll("orderNo")));
	}
	
	/**
	 * @return 组合关联的资源页面
	 */
	public String packageResourceList(){
		//流程数据
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		sysSuppliers =sysSupplierService.findAll();
		sysBrands = sysBrandService.findAll();
		hashMap = new HashMap<String,Object>();
		JSONObject obj = new JSONObject();
		for(SysDataDictionary dictionary:flowList){
			obj.put(dictionary.getValue(), dictionary.getName());
		}
		JSONObject supplierObj = new JSONObject();
		for(SysSupplier sysSupplier:sysSuppliers){
			supplierObj.put(sysSupplier.getId(),sysSupplier.getName());
		}
		JSONObject brandObj = new JSONObject();
		for(SysBrand sysBrand:sysBrands){
			brandObj.put(sysBrand.getId(),sysBrand.getName());
		}
		hashMap.put("brandObj",brandObj);
		hashMap.put("flowObj",obj);
		hashMap.put("supplierObj",supplierObj);
		return "packageResourceList";
	}
	
	/**
	 * @return 组合关联的资源数据
	 */
	public String packageResourceData(){
		//分页查询
		//List<SysResource> sysResources = sysRsRcPackageService.findPackageResourceData(sysRsRcPackage.getId());
		JSONObject obj=new JSONObject();
		JSONObject seachObj = JSONObject.fromObject(jsonStr);
		Pager<SysResourceRel> pager = sysRsRcPackageService.findPackageResourceData(sysRsRcPackage.getId(),page,rows,seachObj);
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
			@SuppressWarnings("unchecked")
			List<SysResourceRel> sysResources = (List<SysResourceRel>) pager.getData();
			sysSuppliers =sysSupplierService.findAll(); 
			sysBrands = sysBrandService.findAll();
			Map<Integer,SysSupplier> supplierMap = CollectionUtil.listToMap(sysSuppliers,"id");
  			Map<Integer,SysBrand> sysBrandMap = CollectionUtil.listToMap(sysBrands,"id");
			for(SysResourceRel resource:sysResources){
  				if(null != resource.getSupplierId()&&0!=resource.getSupplierId()){
  					SysSupplier supplier = supplierMap.get(resource.getSupplierId());
  	  				if(null != supplier){
  	  					resource.setSupplier(supplier.getContact());
  	  					resource.setSupplierName(supplier.getName());
  	  					resource.setSupplierPhone(supplier.getPhoneNum());
  	  					resource.setSupplierTel(supplier.getTelNum());
  	  					resource.setSupplierAddress(supplier.getAddress());
  	  				}
  				}
  				if(null != resource.getBrandId()&&0!=resource.getBrandId()){
  					SysBrand sysBrand = sysBrandMap.get(resource.getBrandId());
  	  				if(null != sysBrand){
  	  					resource.setBrand(sysBrand.getName());
  	  				}
  				}
  				JSONObject newObj = JSONObject.fromObject(resource);
  				arr.add(newObj);
  			}
  			obj.put("total", pager.getTotalRows());
  	    }else{
  	        obj.put("total",0); 
  	    }
		obj.put("rows", arr);
  		return ajaxText(JSONObject.fromObject(obj).toString());
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
		
		JSONObject json_code=new JSONObject();
		json_code.put("attrName","组合代号");
		json_code.put("value", sysRsRcPackage.getCode());
		jsonArr.add(json_code);
		
		JSONObject json_name=new JSONObject();
		json_name.put("attrName", "组合名称");
		json_name.put("value", sysRsRcPackage.getName());
		jsonArr.add(json_name);
		
//		JSONObject minPrice=new JSONObject();
//		minPrice.put("attrName","价格最小值");
//		minPrice.put("value", sysRsRcPackage.getMinPrice());
//		jsonArr.add(minPrice);
//		
//		JSONObject maxPrice=new JSONObject();
//		maxPrice.put("attrName","价格最大值");
//		maxPrice.put("value", sysRsRcPackage.getMaxPrice());
//		jsonArr.add(maxPrice);
		return ajaxText(jsonArr);
	}
	
	/**
	 * @date 2017-10-20
	 * @author YBF
	 * @return
	 * <p>更新产品组合</p>
	 */
	public String saveOrUpdatePackagePage(){
		if(null != sysRsRcPackage&&null != sysRsRcPackage.getId()){
			sysRsRcPackage = sysRsRcPackageService.get(sysRsRcPackage.getId());
		}
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
				updateSysRsRcPackage.setName(sysRsRcPackage.getName());
				//updateSysRsRcPackage.setMinPrice(sysRsRcPackage.getMinPrice());
				//updateSysRsRcPackage.setMaxPrice(sysRsRcPackage.getMaxPrice());
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
	
	/**
	 * @date 2017-11-1
	 * @author YBF
	 * @return
	 * <p>删除组合里面的资源</p>
	 */
	public String deleteResourceOfPackage(){
		try{
			sysRsRcPackageService.deletePackageResource(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	/**
	 * @date 2017-11-1
	 * @author YBF
	 * @return
	 * <p>删除资源组合</p>
	 */
	public String deleteResourcePackage(){
		try{
			sysRsRcPackageService.deleteResourcePackage(sysRsRcPackage.getId());
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	/**
	 * @date 2017-11-9
	 * @author YBF
	 * @return
	 * <p>组合配置页面</p>
	 */
	public String packageBrand(){
		return "packageBrand";
	}
	
	/**
	 * @date 2017-11-9
	 * @author YBF
	 * @return
	 * <p>获取所有最底级类别列表</p>
	 */
	public String allLastChildrenListData(){
		List<SysPcBrandCatalog> sysPcBrandCatalogs = sysRsRcPackageService.findAllPcBrandCatalog(sysRsRcPackage.getId());
		Map<Integer,SysPcBrandCatalog> pcBrandCatalogMap = CollectionUtil.listToMap(sysPcBrandCatalogs,"brandCatalogId");
		
		List<SysRsRcCatalog> sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(0);
		JSONArray arr = new JSONArray();
		for(SysRsRcCatalog sysRsRcCatalog:sysRcRsrcOrgList){
			JSONObject obj = new JSONObject();
			sysRsRcCatalog.setCombineName(sysRsRcCatalog.getCatalogName());
			List<SysRsRcCatalog> childrenList = sysResourceTypeService.findRsRcCatalogByParentId(sysRsRcCatalog.getId());
			if(childrenList.size()>0){
				combineCatalog(arr,sysRsRcCatalog,childrenList,pcBrandCatalogMap);
			}else{
				putBrandData(pcBrandCatalogMap, arr, sysRsRcCatalog, obj);
			}
		}
		return ajaxText(arr.toString());
	}

	//1,自己做工项用作参考用，其他做工项用作计算费用。做工项可以直接写到每个大类下面，根据价格区分，无需根据做工类别区分销售价格为0的颜色标识出来
	
	
	private void combineCatalog(JSONArray arr,SysRsRcCatalog pSysRsRcCatalog,
			List<SysRsRcCatalog> sysRcRsrcOrgList,Map<Integer,SysPcBrandCatalog> pcBrandCatalogMap){
		for(SysRsRcCatalog sysRsRcCatalog:sysRcRsrcOrgList){
			JSONObject obj = new JSONObject();
			sysRsRcCatalog.setCombineName(pSysRsRcCatalog.getCombineName()+">"+sysRsRcCatalog.getCatalogName());
			List<SysRsRcCatalog> childrenList = sysResourceTypeService.findRsRcCatalogByParentId(sysRsRcCatalog.getId());
			if(childrenList.size()>0){
				combineCatalog(arr,sysRsRcCatalog,childrenList,pcBrandCatalogMap);
			}else{
				putBrandData(pcBrandCatalogMap, arr, sysRsRcCatalog, obj);
			}
		}
	}
	
	private void putBrandData(
			Map<Integer, SysPcBrandCatalog> pcBrandCatalogMap, JSONArray arr,
			SysRsRcCatalog sysRsRcCatalog, JSONObject obj) {
		obj.put("id", sysRsRcCatalog.getId());
		obj.put("name", sysRsRcCatalog.getCombineName());
		obj.put("catalogName", sysRsRcCatalog.getCatalogName());
		obj.put("catalogType", sysRsRcCatalog.getCatalogType());
		
		List<SysBrand> sysBrands = sysBrandService.findByCatalogId(sysRsRcCatalog.getId());
		JSONArray brandArr = new JSONArray();
		for(SysBrand sysBrand:sysBrands){
			JSONObject brandObj = JSONObject.fromObject(sysBrand);
			if(null != pcBrandCatalogMap.get(sysBrand.getBrandCatalogId())){
				brandObj.put("isCheck",1);
			}else{
				brandObj.put("isCheck",0);
			}
			brandArr.add(brandObj);
		}
		obj.put("brandArr",brandArr);
		arr.add(obj);
	}
	
	public String savePackageBrand(){
		try{
			sysRsRcPackageService.savePackageBrandCatalog(sysRsRcPackage.getId(),jsonStr);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	@DownloadAnnotation("resourcePackageAction_exportPackageBrand")
	public String exportPackageBrand(){
		sysRsRcPackage = sysRsRcPackageService.get(sysRsRcPackage.getId());
		exportResourceFileName = sysRsRcPackage.getName()+".xls";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Workbook workbook=null;
        try {
            workbook = exportPackageBrandExcel(sysRsRcPackage);
		    workbook.write(output);
	        byte[] ba = output.toByteArray();
	        exportResourceStream = new ByteArrayInputStream(ba);
		}catch(Exception e){
		    LOG.error(e.getMessage());
		}finally{
		    try {
                output.flush();
                output.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
		}
		return "exportResource";
	}
	
	private Workbook exportPackageBrandExcel(SysRsRcPackage sysRsRcPackage2) {
		List<SysPcBrandCatalog> sysPcBrandCatalogs = sysRsRcPackageService.findAllPcBrandCatalog(sysRsRcPackage.getId());
		Map<Integer,SysPcBrandCatalog> pcBrandCatalogMap = CollectionUtil.listToMap(sysPcBrandCatalogs,"brandCatalogId");
		
		Map<String, SysDataDictionary> flowMap = BaseDataDictionaryUtil.valueMap.get(4);
		
		List<SysRsRcCatalog> sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(0);
		JSONArray arr = new JSONArray();
		for(SysRsRcCatalog sysRsRcCatalog:sysRcRsrcOrgList){
			JSONObject obj = new JSONObject();
			sysRsRcCatalog.setCombineName(sysRsRcCatalog.getCatalogName());
			List<SysRsRcCatalog> childrenList = sysResourceTypeService.findRsRcCatalogByParentId(sysRsRcCatalog.getId());
			if(childrenList.size()>0){
				combineCatalog(arr,sysRsRcCatalog,childrenList,pcBrandCatalogMap);
			}else{
				putBrandData(pcBrandCatalogMap, arr, sysRsRcCatalog, obj);
			}
		}
		
	    Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("套餐品牌关联");
		int nCol = 0;  //列编号
		int nRow = 0;  //行编号
		
		// 创建单元格样式
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		
		Row row = sheet.createRow(nRow++);
	    Cell cell =null;
		row.setHeightInPoints(15);// 设定行的高度
		nCol = 0;
		
		sheet.setColumnWidth(nCol, 2500);
		cell = row.createCell(nCol++);
		cell.setCellStyle(style);
		cell.setCellValue(getText("所属流程"));
		
		sheet.setColumnWidth(nCol, 10000);
		cell = row.createCell(nCol++);
		cell.setCellStyle(style);
		cell.setCellValue(getText("产品类别"));
		
		sheet.setColumnWidth(nCol, 2500);
		cell = row.createCell(nCol++);
		cell.setCellStyle(style);
		cell.setCellValue(getText("品牌名称"));
		
		
		for(int i=0;i<arr.size();i++){
			
			//打包表格数据
			row = sheet.createRow(nRow++);
			nCol=0;
			
			JSONObject obj = arr.getJSONObject(i);
			
			
			
			CellStyle nameStyle = wb.createCellStyle();
			nameStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			nameStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cell = row.createCell(nCol++);
			cell.setCellStyle(nameStyle);
			cell.setCellValue(flowMap.get(obj.getString("catalogType")).getName());
			
			nameStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			nameStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cell = row.createCell(nCol++);
			cell.setCellStyle(nameStyle);
			cell.setCellValue(obj.getString("catalogName"));
			
			JSONArray brandArr = obj.getJSONArray("brandArr");
			for(int j=0;j<brandArr.size();j++){
				JSONObject brandObj = brandArr.getJSONObject(j);
				
				CellStyle brandStyle = wb.createCellStyle();
				brandStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				brandStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				sheet.setColumnWidth(nCol, 5000);
				cell = row.createCell(nCol++);
				cell.setCellStyle(brandStyle);
				cell.setCellValue(brandObj.getString("name"));
				
				if(1==brandObj.getInt("isCheck")){
					CellStyle relationStyle = wb.createCellStyle();
					relationStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
					relationStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
					relationStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
					sheet.setColumnWidth(nCol, 1000);
					cell = row.createCell(nCol++);
					cell.setCellStyle(relationStyle);
					cell.setCellValue("√");
				}else{
					sheet.setColumnWidth(nCol, 1000);
					CellStyle relationStyle = wb.createCellStyle();
					relationStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
					relationStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
					cell = row.createCell(nCol++);
					cell.setCellStyle(relationStyle);
					cell.setCellValue("");
				}
			}
		}
		return wb;
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

	public Map<String, Object> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<String, Object> hashMap) {
		this.hashMap = hashMap;
	}

	public List<SysSupplier> getSysSuppliers() {
		return sysSuppliers;
	}

	public void setSysSuppliers(List<SysSupplier> sysSuppliers) {
		this.sysSuppliers = sysSuppliers;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public List<SysBrand> getSysBrands() {
		return sysBrands;
	}

	public void setSysBrands(List<SysBrand> sysBrands) {
		this.sysBrands = sysBrands;
	}
}

package com.yunwang.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.yunwang.model.pojo.SysBrand;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.service.SysBrandService;
import com.yunwang.service.SysResourceService;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.service.SysSupplierService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;
import com.yunwang.util.collection.CollectionUtil;
import com.yunwang.util.exception.MineException;
import com.yunwang.util.file.FileSupport;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.util.string.StringBufferByCollectionUtil;

@Action(
	value = "resourceAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/resource/index.jsp"),
		@Result(name = "resourceList",location="/WEB-INF/web/resource/resourceList.jsp"),
		@Result(name = "addPage",location="/WEB-INF/web/resource/add.jsp"),
		@Result(name="exportResource",type="stream",
				params={"encode","true","contentType","application/vnd.ms-excel;charset=UTF-8",
				  "inputName","exportResourceStream","contentDisposition","attachment;filename=${exportResourceFileName}"}),
		@Result(name="importResourcePage",location="/WEB-INF/web/resource/importResourcePage.jsp"),
		@Result(name="resourceSelect",location="/WEB-INF/web/resource/resourceSelect.jsp"),
		@Result(name="selectResourceList",location="/WEB-INF/web/resource/selectResourceList.jsp"),
		@Result(name="relResourceSelect",location="/WEB-INF/web/releaseResource/relResourceSelect.jsp"),
		@Result(name="relResourceInfo",location="/WEB-INF/web/releaseResource/relResourceInfo.jsp"),
		@Result(name="relResourceTreeIndex",location="/WEB-INF/web/releaseResource/relResourceTreeIndex.jsp"),
		@Result(name="relResourceTreeSelect",location="/WEB-INF/web/releaseResource/relResourceTreeSelect.jsp"),
		@Result(name="editResourceImage",location="/WEB-INF/web/resource/editResourceImg.jsp"),
		@Result(name="editResourceImages",location="/WEB-INF/web/resource/editResourceImgs.jsp")
	}
)
public class ResourceAction extends AbstractLoginAction{

	private final static Logger LOG =Logger.getLogger(ResourceAction.class);
	/*
	 * @date 2017-9-27
	 * @author YBF
	 * TODO 系统资源管理 (产品、工人)
	 */
	private static final long serialVersionUID = 1L;
	
	private SysRsRcCatalog sysRsRcCatalog;
	
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysResourceTypeService sysResourceTypeService;
	@Autowired
	private SysSupplierService sysSupplierService;
	@Autowired
	private SysBrandService sysBrandService;
	
	private List<SysRsRcAttribCatalog> attribCatalogs;
	private String ids;
	private SysResource sysResource;
	private List<SysDataDictionary> flowList;
	private Map<String,Object> hashMap;
	private String resourceJsonStr;
	private List<SysSupplier> sysSuppliers;
	private List<SysBrand> sysBrands;
	private SysRsRcPackage sysRsRcPackage;
	private SysResourceRel sysResourceRel;
	
	/** 上传参考图片*/
	private File[] file;
	private String[] fileFileName;
	private String[] fileContentType;
	
	
	private File imageFile;
	private String imageFileFileName;
	private String imageFileContentType;
	
	private String filelSavePath;
	
	@Override
	public String execute() throws Exception {
		return "index";
	} 
	
	public String resourceList(){
		initResourceListData();
		return "resourceList";
	}

	private void initResourceListData() {
		//sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
		attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
		attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog.getId()));
		attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog.getId()));
		
		hashMap = new HashMap<String,Object>();
		
//		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
//		hashMap = new HashMap<String,Object>();
//		hashMap.put("flowArr", JSONArray.fromObject(flowList));
//		JSONObject obj = new JSONObject();
//		for(SysDataDictionary dictionary:flowList){
//			obj.put(dictionary.getValue(), dictionary.getName());
//		}
//		hashMap.put("flowObj",obj);
		
		if(0 != sysRsRcCatalog.getId()){
			sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
			sysSuppliers = sysSupplierService.findByCatalogId(sysRsRcCatalog.getId());
			sysBrands = sysBrandService.findByCatalogId(sysRsRcCatalog.getId());
		}else{
			sysSuppliers =sysSupplierService.findAll();
			sysBrands = sysBrandService.findAll();
		}
		
		JSONObject supplierObj = new JSONObject();
		for(SysSupplier sysSupplier:sysSuppliers){
			supplierObj.put(sysSupplier.getId(),sysSupplier.getName());
		}
		JSONArray sysSupplierArr = JSONArray.fromObject(sysSuppliers);
		JSONObject sysSupplierFirstSelect = new JSONObject();
		sysSupplierFirstSelect.put("id","0");
		sysSupplierFirstSelect.put("name","请选择");
		sysSupplierArr.add(0,sysSupplierFirstSelect);
		hashMap.put("supplierObj",supplierObj);
		hashMap.put("supplierArr",sysSupplierArr);
		
		
		JSONObject brandObj = new JSONObject();
		for(SysBrand sysBrand:sysBrands){
			brandObj.put(sysBrand.getId(),sysBrand.getName());
		}
		JSONArray brandArr = JSONArray.fromObject(sysBrands);
		JSONObject brandFirstSelect = new JSONObject();
		brandFirstSelect.put("id","0");
		brandFirstSelect.put("name","请选择");
		brandArr.add(0,brandFirstSelect);
		hashMap.put("brandObj",brandObj);
		hashMap.put("brandArr",brandArr);
	}
	
	/**
	 * @return  //每个资源类型下面显示当前类型下的资源 
	 */
	@SuppressWarnings("unchecked")
	public String resourceListData(){
		JSONObject obj=new JSONObject();
		JSONObject seachObj = JSONObject.fromObject(resourceJsonStr);
		Pager<SysResource> pager = sysResourceService.findByRsRcCatalogId(sysRsRcCatalog.getId(),page,rows,
			seachObj);
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
			List<SysResource> sysResources = (List<SysResource>) pager.getData();
  			attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
  			attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog.getId()));
  			attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog.getId()));
  			
  			List<SysRsRcAttrib> sysRsRcAttribs = sysResourceService.findSysRsRcAttribByResourceIds(
  					StringBufferByCollectionUtil.convertCollection(sysResources,"id"));
  			Map<Integer,Map<Integer,SysRsRcAttrib>> map = conToMap(sysRsRcAttribs);
  			
  			if(0 != sysRsRcCatalog.getId()){
  				sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
  				sysSuppliers = sysSupplierService.findByCatalogId(sysRsRcCatalog.getId());
  				sysBrands = sysBrandService.findByCatalogId(sysRsRcCatalog.getId());
  			}else{
  				sysSuppliers =sysSupplierService.findAll();
  				sysBrands = sysBrandService.findAll();
  			}
  			Map<Integer,SysSupplier> supplierMap = CollectionUtil.listToMap(sysSuppliers,"id");
  			Map<Integer,SysBrand> sysBrandMap = CollectionUtil.listToMap(sysBrands,"id");
  		
  			for(SysResource resource:sysResources){
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
  				Map<Integer,SysRsRcAttrib> resourceMap = map.get(resource.getId());
  				if(null != resourceMap){
  					for(SysRsRcAttribCatalog attrCatalog : attribCatalogs){
  						SysRsRcAttrib attrib = resourceMap.get(attrCatalog.getId());
  						newObj.put(attrCatalog.getId(),null != attrib ?attrib.getRsrcAttribValue():"");
  					}
  				}
  				arr.add(newObj);
  			}
  			obj.put("total", pager.getTotalRows());
  	    }else{
  	        obj.put("total",0); 
  	    }
		obj.put("rows", arr);
  		return ajaxText(JSONObject.fromObject(obj).toString());
	}
	
	private Map<Integer,Map<Integer,SysRsRcAttrib>> conToMap(List<SysRsRcAttrib> sysRsRcAttribs){
		Map<Integer,Map<Integer,SysRsRcAttrib>> map = new HashMap<Integer,Map<Integer,SysRsRcAttrib>>();
		for(SysRsRcAttrib attrib:sysRsRcAttribs){
			Map<Integer,SysRsRcAttrib> childMap = map.get(attrib.getRsrcId());
			if(null!=childMap){
				childMap.put(attrib.getRsrcAttribCatalogId(), attrib);
			}else{
				childMap = new HashMap<Integer,SysRsRcAttrib>();
				childMap.put(attrib.getRsrcAttribCatalogId(), attrib);
				map.put(attrib.getRsrcId(), childMap);
			}
		}
		return map;
	}
	
	/**
	 * @return 资源选择页面
	 */
	public String resourceSelect(){
		return "resourceSelect";
	}
	
	
	
	/**
	 * @date 2017-11-3
	 * @author YBF
	 * @return
	 * <p>基本信息页面</p>
	 */
	public String relResourceInfo(){
		return "relResourceInfo";
	}
	
	
	/**
	 * @date 2017-11-3
	 * @author YBF
	 * @return
	 * <p>产品基本信息数据</p>
	 */
	public String relResourceInfoData(){
		sysResourceRel = sysResourceService.getRelResource(sysResourceRel.getId());
		List<SysRsRcAttribCatalog> attrList =  sysResourceTypeService.findAllAttr(sysResourceRel.getRsrcCatalogId());
		JSONArray arr = sysResourceService.getRelResourceInfo(sysResourceRel.getId(),attrList);
		return ajaxText(arr);
	}
	
	
	/**
	 * @return 资源选择页面
	 */
	public String selectResourceList(){
		initResourceListData();
		return "selectResourceList";
	}
	
	public String addPage(){
		attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
		attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog.getId()));
		attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog.getId()));
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		return "addPage";
	}
	
	/**
	 * @date 2017-10-10
	 * @author YBF
	 * @return
	 * <p>保存表格行数据</p>
	 */
	public String saveOrUpdateResourceGrid(){
		try{
			JSONObject obj = JSONObject.fromObject(resourceJsonStr);
			sysResourceService.saveOrUpdateResourceGrid(obj,sysRsRcCatalog);
			return success("行数据保存成功!",obj);
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("行数据保存失败!",e);
		}
	}
	
	/**
	 * @return 删除资源
	 */
	public String deleteResource(){
		try{
			sysResourceService.deleteResource(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!",e);
		}
	}
	
	public String save(){
		//属性空值直接过滤掉
		try{
			sysResourceService.save(sysResource);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!",e);
		}
	}
	
	/**
	 * @date 2017-10-23
	 * @author YBF
	 * @return
	 * <p>发布资源</p>
	 */
	public String releaseResource(){
		
		//方案1：
		//把资源基本信息保存到发布表中
		//但是资源类型的属性需要发布
		//		1、如果资源类型未发布，则不会把自定义属性数据发布过去生
		//      2、如果资源类型以及 属性已经发布，发布资源时需要把属性id以及属性值发布过去
		//      3、资源不跟资源类型关联
		
		
		//发布资源类型的时候，把资源类型基础数据，以及资源类型关联的属性发布到发布表，同时需要把属性和资源的关联关系数据发布到中间表
		//属性按照最新的来，发布表中有的更新名称和代号，发布表中没有的就新增，并且新增属性跟资源的中间表以及数值，
		//删除的删除发布表中数据，以及删除发布属性与资源的关联关系
		
		//方案2：
		//1、资源类型不发布，编辑状态资源和发布资源共用现有的资源类型，以及资源类型属性
		//2、发布资源的时候、直接写入发布资源表，以及发布资源与类型自定义属性的值（原有资源关联的属性值）		
		//3、新增或者删除属性不更新资源状态，删除的时候直接删除编辑资源，以及发布资源的属性数据
		try{
			sysResourceService.releaseResource(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!",e);
		}
	}
	
	
	/**
	 * @return 发布资源选择页面(现在只要一个页面就可以了)
	 */
	public String relResourceSelect(){
		//流程数据
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		sysSuppliers =sysSupplierService.findAll();
		sysBrands =sysBrandService.findAll();
		return "relResourceSelect";
	}
	
	public String relResourceListData(){
		JSONObject obj=new JSONObject();
		JSONObject seachObj = JSONObject.fromObject(resourceJsonStr);
		Pager<SysResourceRel> pager = sysResourceService.findRelResources(sysRsRcPackage,page,rows,seachObj);
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
			@SuppressWarnings("unchecked")
			List<SysResourceRel> sysResources = (List<SysResourceRel>) pager.getData();
			sysSuppliers =sysSupplierService.findAll();
			sysBrands =sysBrandService.findAll();
  			Map<Integer,SysSupplier> supplierMap = CollectionUtil.listToMap(sysSuppliers,"id");
  			Map<Integer,SysBrand> brandMap = CollectionUtil.listToMap(sysBrands,"id");
  			
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
  					SysBrand brand = brandMap.get(resource.getBrandId());
  	  				if(null != brand){
  	  					resource.setBrand(brand.getName());
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
	 * @date 2017-10-24
	 * @author YBF
	 * @return
	 * <p>添加产品到组合</p>
	 */
	public String addRelResourceToPackage(){
		try{
			sysResourceService.addRelResourceToPackage(sysRsRcPackage.getId(),ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String relResourceTreeIndex(){
		return "relResourceTreeIndex";
	}
	
	public String relResourceTreeSelect(){
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		sysSuppliers =sysSupplierService.findAll();
		sysBrands =sysBrandService.findAll();
		return "relResourceTreeSelect";
	}
	
	
	public String editResourceImage(){
		sysResource = sysResourceService.get(sysResource.getId());
		timestamp=System.currentTimeMillis();
		return "editResourceImage";
	}
	
	
	
	public String editResourceImages(){
		sysResource = sysResourceService.get(sysResource.getId());
		return "editResourceImages";
	}
	
	
	public String updateResourceImg(){
		try { 
			sysResource = sysResourceService.get(sysResource.getId());
			upload(sysResource);
			sysResource.setRsrcStatus(2);
			sysResourceService.update(sysResource);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!",e);
		}
	}
	
	
	public String updateResourceImgs(){
		try { 
			sysResource = sysResourceService.get(sysResource.getId());
			for(int i=0;i<file.length;i++){
				File imageFile = file[i];
				String[] strs=MyStringUtil.split(fileFileName[i], "\\.");
			    String fname=strs[strs.length-1].toLowerCase();
			    String[] types={"jpg","jpeg","gif","png","tga","exif","fpx","bmp"};
			    boolean flag=true;
				for(String type:types){
					if(type.equals(fname)){
						flag=false;
						break;
					}
				}
				if(flag){
					throw new MineException("图片格式错误(例如:jpg,jpeg,gif,png,tga,exif,fpx,bmp)");
				}
				if(imageFile.length()>=2097152){
					throw new MineException("图片超过大小范围(2G)");
				}
				
				if(imageFile.length()>=2097152){
					throw new MineException("图片超过大小范围(2G)");
				}
				InputStream is = new FileInputStream(imageFile);
				FileSupport.mkdir("resourceImage");
				
				String fileName=FileSupport.join("resourceImage","resource_"+sysResource.getId()+"."+fname);
				FileSupport.write(fileName, is);
			}
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!",e);
		}
	}
	
	
	/** 
	  * upload() method
	  * @author 方宜斌
	  * @date 2014-1-15 下午1:20:42
	  * <p>资源上传图片</p> 
	  * @return
	  * @throws IOException 
	  * @return boolean  
	*/ 
	private void upload(SysResource sysResource) {
		InputStream is=null;
		OutputStream os=null;
		try {
		    if(imageFile==null){
	            if(MyStringUtil.isNotBlank(sysResource.getImagePath())){
	            	FileSupport.delete(sysResource.getImagePath());
		        }
	            sysResource.setImagePath(null);
			}else{
			    String[] strs=MyStringUtil.split(imageFileFileName, "\\.");
			    String fname=strs[strs.length-1].toLowerCase();
			    String[] types={"jpg","jpeg","gif","png","tga","exif","fpx","bmp"};
			    boolean flag=true;
				for(String type:types){
					if(type.equals(fname)){
						flag=false;
						break;
					}
				}
				if(flag){
					throw new MineException("图片格式错误(例如:jpg,jpeg,gif,png,tga,exif,fpx,bmp)");
				}
				if(imageFile.length()>=2097152){
					throw new MineException("图片超过大小范围(2G)");
				}
				is = new FileInputStream(imageFile);
				FileSupport.mkdir("resourceImage");
				
				String fileName=FileSupport.join("resourceImage","resource_"+sysResource.getId()+"."+fname);
				FileSupport.write(fileName, is);
				
				sysResource.setImagePath(fileName);
			}
      } catch (Exception e) {
          LOG.error(e.getMessage());
          throw new MineException("上传图片失败!");
      }
      finally{
		  try {
	   	   	if(null!=is){
	   	   		is.close(); 
	   	   	}
	   	   	if(null!=os){
	   	   		os.close();
	   	   	}
		  } catch (IOException e) {
	          LOG.error(e.getMessage());
	      }
       }
	}
	
	
	public SysResource getSysResource() {
		return sysResource;
	}

	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}

	public SysRsRcCatalog getSysRsRcCatalog() {
		return sysRsRcCatalog;
	}

	public void setSysRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		this.sysRsRcCatalog = sysRsRcCatalog;
	}

	public List<SysRsRcAttribCatalog> getAttribCatalogs() {
		return attribCatalogs;
	}

	public void setAttribCatalogs(List<SysRsRcAttribCatalog> attribCatalogs) {
		this.attribCatalogs = attribCatalogs;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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

	public String getResourceJsonStr() {
		return resourceJsonStr;
	}

	public void setResourceJsonStr(String resourceJsonStr) {
		this.resourceJsonStr = resourceJsonStr;
	}

	public List<SysSupplier> getSysSuppliers() {
		return sysSuppliers;
	}

	public void setSysSuppliers(List<SysSupplier> sysSuppliers) {
		this.sysSuppliers = sysSuppliers;
	}

	public SysRsRcPackage getSysRsRcPackage() {
		return sysRsRcPackage;
	}

	public void setSysRsRcPackage(SysRsRcPackage sysRsRcPackage) {
		this.sysRsRcPackage = sysRsRcPackage;
	}

	public SysResourceRel getSysResourceRel() {
		return sysResourceRel;
	}

	public void setSysResourceRel(SysResourceRel sysResourceRel) {
		this.sysResourceRel = sysResourceRel;
	}

	public List<SysBrand> getSysBrands() {
		return sysBrands;
	}

	public void setSysBrands(List<SysBrand> sysBrands) {
		this.sysBrands = sysBrands;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String[] getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String[] fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFilelSavePath() {
		return filelSavePath;
	}

	public void setFilelSavePath(String filelSavePath) {
		this.filelSavePath = filelSavePath;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageFileFileName() {
		return imageFileFileName;
	}

	public void setImageFileFileName(String imageFileFileName) {
		this.imageFileFileName = imageFileFileName;
	}

	public String getImageFileContentType() {
		return imageFileContentType;
	}

	public void setImageFileContentType(String imageFileContentType) {
		this.imageFileContentType = imageFileContentType;
	}
}

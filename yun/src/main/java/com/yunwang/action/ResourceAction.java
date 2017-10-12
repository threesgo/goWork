package com.yunwang.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceService;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractLoginAction;
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
		@Result(name="importResourcePage",location="/WEB-INF/web/resource/importResourcePage.jsp")
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
	
	private List<SysRsRcAttribCatalog> attribCatalogs;
	private String ids;
	private SysResource sysResource;
	private List<SysDataDictionary> flowList;
	private Map<String,Object> hashMap;
	private String resourceJsonStr;
	
	private String importResourceFileName;
	private String importResourceContentType;
	private File importResource;
	private InputStream exportResourceStream; // 输入流的名字
	private String exportResourceFileName; // 输出文件的名字
	
	@Override
	public String execute() throws Exception {
		return "index";
	} 
	
	public String resourceList(){
		//sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
		attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
		attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
		attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog));
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		
		hashMap = new HashMap<String,Object>();
		hashMap.put("flowListArr", JSONArray.fromObject(flowList));
		JSONObject obj = new JSONObject();
		for(SysDataDictionary dictionary:flowList){
			obj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("flowListObj",obj);
		return "resourceList";
	}
	
	/**
	 * @return  //每个资源类型下面显示当前类型下的资源 
	 */
	@SuppressWarnings("unchecked")
	public String resourceListData(){/*
		JSONObject obj=new JSONObject();
		Pager<SysResource> pager = sysResourceService.findByRsRcCatalogId(sysRsRcCatalog.getId(),page,rows,
				JSONObject.fromObject(resourceJsonStr));
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
			List<SysResource> sysResources = (List<SysResource>) pager.getData();
  			attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
  			attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
  			attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog));
  			
  			List<SysRsRcAttrib> sysRsRcAttribs = sysResourceService.findSysRsRcAttribByResourceIds(
  					StringBufferByCollectionUtil.convertCollection(sysResources,"id"));
  			Map<Integer,Map<Integer,SysRsRcAttrib>> map = conToMap(sysRsRcAttribs);
  			 
  			for(SysResource resource:sysResources){
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
	*/
		return null;
	}
	
	private Map<Integer,Map<Integer,SysRsRcAttrib>> conToMap(List<SysRsRcAttrib> sysRsRcAttribs){
		Map<Integer,Map<Integer,SysRsRcAttrib>> map = new HashMap<Integer,Map<Integer,SysRsRcAttrib>>();
		for(SysRsRcAttrib attrib:sysRsRcAttribs){
			Map<Integer,SysRsRcAttrib> childMap = map.get(attrib.getRsrcId());
			if(null!=childMap){
				childMap.put(attrib.getRsraAttribCatalogId(), attrib);
			}else{
				childMap = new HashMap<Integer,SysRsRcAttrib>();
				childMap.put(attrib.getRsraAttribCatalogId(), attrib);
				map.put(attrib.getRsrcId(), childMap);
			}
		}
		return map;
	}
	
	public String addPage(){
		attribCatalogs = new ArrayList<SysRsRcAttribCatalog>();
		attribCatalogs.addAll(sysResourceTypeService.findExtendsAttr(sysRsRcCatalog));
		attribCatalogs.addAll(sysResourceTypeService.findAttr(sysRsRcCatalog));
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
			return success("行数据保存成功!");
		}catch(Exception e){
			return error("行数据保存失败!");
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
			return error("操作失败!");
		}
	}
	
	public String save(){
		//属性空值直接过滤掉
		try{
			sysResourceService.save(sysResource);
			return success("操作成功!");
		}catch(Exception e){
			return error("操作失败!");
		}
	}
	
	/** 
	  * importResourcePage() method
	  * @author 方宜斌
	  * @date 2014-7-24 下午2:03:53
	  * <p>导入资源文件选择页面</p> 
	  * @return 
	  * @return String  
	*/
	public String importResourcePage() {
		sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
		return "importResourcePage";
	}
	
	/**
	 * @return 导出产品
	 */
	public String exportResource(){
		sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
		Workbook workbook=null;
//		StringBuffer buf=sysRcRsrcOrgService.getParentTypeId(sysRcRsrcOrg);
//		buf.append(sysRcRsrcOrg.getId());
//		List<BopTmRsRcAttribCatalog> attrList=sysRcRsrcOrgService.getParentAttribType(buf.toString());
//		List<SysRcResourceVo> resourceVoList=sysRcRsrcOrgService.packExportResource(sysRcRsrcOrg.getId());
//		Workbook workbook=null;
//        try {
//            workbook = exportExcel(resourceVoList,attrList);
//        } catch (Exception e) {
//            LOG.error(e.getMessage());
//        }
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try{
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
	
	/** 
	  * exportExcel() method
	  * @author 方宜斌
	  * @date 2014-7-24 下午2:04:15
	  * <p>资源文件导出文件打包</p> 
	  * @param resourceList
	  * @param attrList
	  * @return 
	  * @return Workbook  
	*/ 
//	public Workbook exportExcel(List<SysRcResourceVo> resourceList,List<BopTmRsRcAttribCatalog> attrList)  {
//	    Workbook wb = new HSSFWorkbook();
//		Sheet sheet = wb.createSheet("exportResource");
//		int nCol = 0;  //列编号
//		int nRow = 0;  //行编号
//		sheet.setColumnWidth(nCol++, 3000);
//		sheet.setColumnWidth(nCol++, 3000);
//		List<String> results = new ArrayList<String>();
//		results.add(getText("resource_type_"));
//		results.add(getText("_resource_code"));
//		results.add(getText("resource_name"));
//		results.add(getText("type"));
//		for(BopTmRsRcAttribCatalog attrType:attrList){
//			sheet.setColumnWidth(nCol++, 3000);
//			results.add(attrType.getRsrcAttribName());
//		}
//		// 创建单元格样式
//		CellStyle style = wb.createCellStyle();
//		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		// 创建Excel的sheet的一行
//		Row row = sheet.createRow(nRow++);
//	    Cell cell =null;
//		row.setHeightInPoints(15);// 设定行的高度
//		nCol = 0;
//		for (String s : results) {
//			cell = row.createCell(nCol++);
//			cell.setCellStyle(style);
//			cell.setCellValue(s);
//		}
//		for(SysRcResourceVo resource:resourceList){
//			//打包表格数据
//			row = sheet.createRow(nRow++);
//			nCol=0;
//			cell = row.createCell(nCol++);
//			cell.setCellStyle(style);
//			cell.setCellValue(resource.getRsrcOrgName());
//			
//			cell = row.createCell(nCol++);
//			cell.setCellStyle(style);
//			cell.setCellValue(resource.getRsrcCode());
//			
//			cell = row.createCell(nCol++);
//			cell.setCellStyle(style);
//			cell.setCellValue(resource.getRescName());
//			
//			cell = row.createCell(nCol++);
//			cell.setCellStyle(style);
//			cell.setCellValue(BaseDataDictionaryUtil.valueMap.
//					get(new BigDecimal(11)).get(resource.getRsrcType().toString()).getChName());
//			//根据本身属性和继承属性的查询资源属性值
//			for(BopTmRsRcAttribCatalog attr:attrList){
//				SysRcRsrcAttribVo sysRcRsrcAttrib=null;
//				if(null!=resource.getRsrcAttrs()){
//					for(SysRcRsrcAttribVo attribVo:resource.getRsrcAttrs()){
//						if(attr.getId()-attribVo.getRsraAttribTypeId()==0){
//							sysRcRsrcAttrib=attribVo;
//							break;
//						}
//					}
//				}
//				//如果属性值为空则添加空值
//				if(null==sysRcRsrcAttrib){
//					cell = row.createCell(nCol++);
//					cell.setCellStyle(style);
//					String nullValue=null;
//					cell.setCellValue(nullValue);
//				}else{
//					BopTmBaseDataCatalog sysRcBaseDataType=SysRcBaseDataTypeVo.allBaseDataMap.get(attr.getDataTypeId());
//					cell = row.createCell(nCol++);
//					cell.setCellStyle(style);
//					if(null!=sysRcBaseDataType){
//						//如果类型名称为数值型，给表格设置数值类型
//						if("number".equals(sysRcBaseDataType.getRealName())){
//							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//						}
//					}
//					//如果属性类型为2(范围值)，则要对范围值进行“|”分开
//					if(attr.getRsrcAttribType().equals(new BigDecimal(2))){
//						//范围的前后都不为空
//						if(null!=sysRcRsrcAttrib.getRsrcAttribValue()&&null!=sysRcRsrcAttrib.getRangeValue()){
//							cell.setCellValue(sysRcRsrcAttrib.getRsrcAttribValue()+"|"+
//									sysRcRsrcAttrib.getRangeValue());
//						}else{
//							//范围值都为空
//							if(null==sysRcRsrcAttrib.getRsrcAttribValue()&&null==sysRcRsrcAttrib.getRangeValue()){
//								String nullValue=null;
//								cell.setCellValue(nullValue);
//							}else if(null!=sysRcRsrcAttrib.getRsrcAttribValue()){
//								cell.setCellValue(sysRcRsrcAttrib.getRsrcAttribValue()+"|");
//							}else{
//								cell.setCellValue("|"+sysRcRsrcAttrib.getRangeValue());
//							}
//						}
//					}else{
//						cell.setCellValue(sysRcRsrcAttrib.getRsrcAttribValue());
//					}
//				}
//			}
//		}
//	 return wb;
//	}
	
	/** 
	  * importResource() method
	  * @author 方宜斌
	  * @date 2014-7-24 下午2:04:55
	  * <p>资源导入文件打包</p> 
	  * @return 
	  * @return String  
	*/ 
	public String importResource(){
		sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());
		
//		StringBuffer buf=sysRcRsrcOrgService.getParentTypeId(sysRcRsrcOrg);
//		buf.append(sysRcRsrcOrg.getId());
//		List<BopTmRsRcAttribCatalog> attrList=sysRcRsrcOrgService.getParentAttribType(buf.toString());
//		
//		Map<String,BopTmRsRcAttribCatalog> attrMap=getCataLogMap(attrList);
//		Map<Integer,BopTmRsRcAttribCatalog> importAttrMap=new HashMap<Integer,BopTmRsRcAttribCatalog>();
//		List<BopTmResource> resourceList=new ArrayList<BopTmResource>();
//		InputStream in=null;
//		String valdateString;
//	    try {
//	        in = new FileInputStream(importResource);
//			Workbook workBook=null;
//			if(importResourceFileName.endsWith(".xls")){
//				workBook=new HSSFWorkbook(in);
//			}else{
//				workBook=new XSSFWorkbook(in);
//			}
//			//遍历表格sheet
//			for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
//			    Sheet sheet = workBook.getSheetAt(numSheet);
//			    if (sheet == null) {
//			        continue;
//   			}
//			    //遍历每个sheet的每行row
//				for (int rowNum =0; rowNum <= sheet.getLastRowNum(); rowNum++) {
//					Row row = sheet.getRow(rowNum);
//					if (row == null) {
//						break;
//					}
//					Cell cell;
//					if(rowNum-0==0){
//						//遍历row的cell
//						for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
//							if(cellNum>3){
//								cell=row.getCell(cellNum);
//								String value=cutAfterPoint(getValue(cell,sheet,workBook));
//								BopTmRsRcAttribCatalog attrCata=attrMap.get(value);
//								if(null==attrCata){
//									return ajaxText(String.format(getText("importandaxportresourceaction_importresourceexcel_exception"),
//											rowNum+1,cellNum+1,value));
//								}else{
//									importAttrMap.put(cellNum,attrCata);
//								}
//							}
//						}
//					}else{
//						//打包导入资源数据
//						valdateString=packResourceList(row,sheet,workBook,importAttrMap,resourceList);
//						if(null!=valdateString){
//							return ajaxText(valdateString);
//						}
//					}
//				}
//   		 }
//			sysRcResourceService.saveResources(resourceList,sysRcRsrcOrg);
//			return ajaxText(Constant.STATUS_AVAILABLE);
//	    }catch(Exception e){
//		    return ajaxText(Constant.STATUS_BLOCKED);
//		}finally{
//		    try {
//               in.close();
//           } catch (IOException e) {
//               LOG.error(e.getMessage());
//           }
//		}
		return null;
	}
	
	/** 
	  * packResourceList() method
	  * @author 方宜斌
	  * @date 2014-7-24 下午5:33:33
	  * <p>打包导入资源和资源属性信息</p> 
	  * @param row
	  * @param sheet
	  * @param workBook
	  * @param sysRcRsrcOrg
	  * @param importAttrMap
	  * @param resourceList
	  * @return 
	  * @return String  
	*/ 
//	private String packResourceList(Row row,Sheet sheet,Workbook workBook,
//			Map<Integer,BopTmRsRcAttribCatalog> importAttrMap,List<BopTmResource> resourceList){
//		BopTmResource sysRcResource=new BopTmResource();
//		sysRcResource.setRsrcCode(cutAfterPoint(getValue(row.getCell(1),sheet,workBook)));
//		if(null==sysRcResource.getRsrcCode()){
//			return String.format(getText("importandaxportresourceaction_packresourcelist_exception"),row.getRowNum()+1);
//		}
//		//后来添加（以前写在service层）主要判断导入数据的相同性
//		boolean flag=true;
//		
//		for(BopTmResource addResource:resourceList){
//			if(addResource.getRsrcCode().equals(sysRcResource.getRsrcCode())){
//				//sysRcResource=addResource;
//				flag=false;
//			}
//		}
//		if(flag){
//			sysRcResource.setRescName(cutAfterPoint(getValue(row.getCell(2),sheet,workBook)));
//			List<BopTmRsRcAttrib> sysRcRsrcAttribList=new ArrayList<BopTmRsRcAttrib>();
//			for (int cellNum = 4; cellNum < row.getLastCellNum(); cellNum++) {
//				BopTmRsRcAttribCatalog sysRcRsrcAttribType=importAttrMap.get(cellNum);
//				if(null!=sysRcRsrcAttribType){
//					BopTmRsRcAttrib sysRcRsrcAttrib=new BopTmRsRcAttrib();
//					String stringValue=getValue(row.getCell(cellNum),sheet,workBook);
//					//当单元格数据不为空
//					if(null!=stringValue){
//						//验证导入资源数据长度
//						if(null!=sysRcRsrcAttribType.getDataLength()){
//							String validataLength=validateDataLength(sysRcResource, stringValue,
//									sysRcRsrcAttribType);
//							if(null!=validataLength){
//								return validataLength;
//							}
//						}
//						BopTmBaseDataCatalog dataType=SysRcBaseDataTypeVo.allBaseDataMap.get(sysRcRsrcAttribType.getDataTypeId());
//						BopTmBaseDataCatalog controlType=SysRcBaseDataTypeVo.allBaseDataMap.get(sysRcRsrcAttribType.getControlTypeId());
//						//验证控件类型与数据合法性
//						if(null!=controlType){
//							String validataControlList=validataControlList(sysRcResource, stringValue,
//									sysRcRsrcAttribType, controlType);
//							if(null!=validataControlList){
//								return validataControlList;
//							}
//						}
//						//验证数据类型合法性
//						if(null!=dataType){
//							String validateDataType=validateDataType(sysRcResource, stringValue,
//									sysRcRsrcAttribType, dataType);
//							if(null!=validateDataType){
//								return validateDataType;
//							}
//						}
//						//验证属性类型，当属性为范围值是，导入数据用“|”分隔
//						if(sysRcRsrcAttribType.getRsrcAttribType().equals(new BigDecimal(2))){
//							String[] values=stringValue.split("\\|");
//							//当具有大小范围值时：
//							if(values.length>1){
//								sysRcRsrcAttrib.setRsrcAttribValue(values[0]);
//								sysRcRsrcAttrib.setRangeValue(values[1]);
//							}else{
//								sysRcRsrcAttrib.setRsrcAttribValue(values[0]);
//							}
//						}else{
//							sysRcRsrcAttrib.setRsrcAttribValue(stringValue);
//						}
//					   //}
//					}else{
//						sysRcRsrcAttrib.setRsrcAttribValue(null);
//					}
//					sysRcRsrcAttrib.setRsraAttribTypeId(sysRcRsrcAttribType.getId());
//					sysRcRsrcAttribList.add(sysRcRsrcAttrib);
//				}
//			}
//			sysRcResource.setSysRcRsrcAttribList(sysRcRsrcAttribList);
//			resourceList.add(sysRcResource);
//		}
//		return null; //svn
//	}
	
//	/**
//	 * @author 方宜斌
//	 * <b>验证数据类型</b>
//	 * 2014-11-21
//	 * @param sysRcResource
//	 * @param stringValue
//	 * @param sysRcRsrcAttribType
//	 * @param dataType
//	 * @return
//	 */
//	private String validateDataType(BopTmResource sysRcResource,
//			String stringValue, BopTmRsRcAttribCatalog sysRcRsrcAttribType,
//			BopTmBaseDataCatalog dataType) {
//		if("number".equals(dataType.getRealName())){
//			boolean flag=false;
//			if(sysRcRsrcAttribType.getRsrcAttribType().equals(new BigDecimal(2))){
//				String[] values=stringValue.split("\\|");
//				for(String value:values){
//					if(MyStringUtil.isBlank(value)){
//						continue;
//					}
//					if(!MyNumberUtil.isNumber(value)){
//						flag=true;
//						break;
//					}
//				}
//			}else if(!MyNumberUtil.isNumber(stringValue)){
//				flag=true;
//			}
//			if(flag){
//				return String.format(getText("importandaxportresourceaction_validatedatatype_numtype_exception"),sysRcResource.getRsrcCode(),
//						sysRcRsrcAttribType.getRsrcAttribName(),stringValue);
//			}
//		}
//		if("date".equals(dataType.getRealName())){
//			boolean flag=false;
//			if(sysRcRsrcAttribType.getRsrcAttribType().equals(new BigDecimal(2))){
//				String[] values=stringValue.split("\\|");
//				for(String value:values){
//					if(MyStringUtil.isBlank(value)){
//						continue;
//					}
//					if(!MyDateUtils.validateDate(value)){
//						flag=true;
//						break;
//					}
//				}
//			}else if(!MyDateUtils.validateDate(stringValue)){
//				flag=true;
//			}
//			if(flag){
//				return String.format(getText("importandaxportresourceaction_validatedatatype_date_exception"),sysRcResource.getRsrcCode(),
//						sysRcRsrcAttribType.getRsrcAttribName(),stringValue);
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * @author 方宜斌
//	 * <b>验证控件类型为列表时的导入数据合法性</b>
//	 * 2014-11-21
//	 * @param sysRcResource
//	 * @param stringValue
//	 * @param sysRcRsrcAttribType
//	 * @param controlType
//	 * @return
//	 */
//	private String validataControlList(BopTmResource sysRcResource,
//			String stringValue, BopTmRsRcAttribCatalog sysRcRsrcAttribType,
//			BopTmBaseDataCatalog controlType) {
//		if("checkbox".equals(controlType.getRealName())||"select".equals(controlType.getRealName())){
//			String[] defaultValues=sysRcRsrcAttribType.getDefaultValue().split("\\|");
//			boolean flag=true;
//			for(String value:defaultValues){
//				if(value.equals(stringValue)){
//					flag=false;
//					break;
//				}
//			}
//			if(flag){
//				return String.format(getText("importandaxportresourceaction_validatacontrollist_exception"),sysRcResource.getRsrcCode(),
//						sysRcRsrcAttribType.getRsrcAttribName(),stringValue);
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * @author 方宜斌
//	 * <b>验证数据长度</b>
//	 * 2014-11-21
//	 * @param sysRcResource
//	 * @param stringValue
//	 * @param sysRcRsrcAttribType
//	 * @return
//	 */
//	private String validateDataLength(BopTmResource sysRcResource,
//			String stringValue, BopTmRsRcAttribCatalog sysRcRsrcAttribType) {
//		boolean flag=false;
//		if(sysRcRsrcAttribType.getRsrcAttribType().equals(new BigDecimal(2))){
//			String[] values=stringValue.split("\\|");
//			for(String value:values){
//				if(value.length()>sysRcRsrcAttribType.getDataLength().intValue()){
//					flag=true;
//					break;
//				}
//			}
//		}else if(stringValue.length()>sysRcRsrcAttribType.getDataLength().intValue()){
//			flag=true;
//		}
//		if(flag){
//			return String.format(getText("importandaxportresourceaction_validatedatalength_exception"),sysRcResource.getRsrcCode(),
//					sysRcRsrcAttribType.getRsrcAttribName(),stringValue);
//		}
//		return null;
//	}
	
	/**
	  * @author 方宜斌
	  * 2015-4-16
	  * @param str
	  * @return
	  * <p>判断数值是否是时间</p>
	*/
	public boolean isDate(String str){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try{
			format.parse(str);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	  * @author 方宜斌
	  * 2015-4-16
	  * @param cellValue
	  * @return
	  * <p>截取数值，主要针对传过来的字符串带有小数点</p>
	*/
	private String cutAfterPoint(String cellValue){
		if(null!=cellValue){
			return cellValue.split("\\.")[0];
		}else{
			return cellValue;
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

	public SysResourceService getSysResourceService() {
		return sysResourceService;
	}

	public void setSysResourceService(SysResourceService sysResourceService) {
		this.sysResourceService = sysResourceService;
	}

	public SysResourceTypeService getSysResourceTypeService() {
		return sysResourceTypeService;
	}

	public void setSysResourceTypeService(
			SysResourceTypeService sysResourceTypeService) {
		this.sysResourceTypeService = sysResourceTypeService;
	}

	public String getImportResourceFileName() {
		return importResourceFileName;
	}

	public void setImportResourceFileName(String importResourceFileName) {
		this.importResourceFileName = importResourceFileName;
	}

	public String getImportResourceContentType() {
		return importResourceContentType;
	}

	public void setImportResourceContentType(String importResourceContentType) {
		this.importResourceContentType = importResourceContentType;
	}

	public File getImportResource() {
		return importResource;
	}

	public void setImportResource(File importResource) {
		this.importResource = importResource;
	}

	public InputStream getExportResourceStream() {
		return exportResourceStream;
	}

	public void setExportResourceStream(InputStream exportResourceStream) {
		this.exportResourceStream = exportResourceStream;
	}

	public String getExportResourceFileName() {
		return exportResourceFileName;
	}

	public void setExportResourceFileName(String exportResourceFileName) {
		this.exportResourceFileName = exportResourceFileName;
	}
}

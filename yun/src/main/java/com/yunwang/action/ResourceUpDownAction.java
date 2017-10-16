package com.yunwang.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcAttrib;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcBaseData;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.service.SysResourceService;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.util.PoiUtil;
import com.yunwang.util.SysRcBaseDataTypeUtil;
import com.yunwang.util.action.AbstractUpDownAction;
import com.yunwang.util.annotation.DownloadAnnotation;
import com.yunwang.util.collection.CollectionUtil;
import com.yunwang.util.date.MyDateUtils;
import com.yunwang.util.exception.MineException;
import com.yunwang.util.number.MyNumberUtil;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.util.string.StringBufferByCollectionUtil;

@Action(
	value = "resourceUpDownAction", 
	results = {
		@Result(name="exportResource",type="stream",
				params={"encode","true","contentType","application/vnd.ms-excel;charset=UTF-8",
				  "inputName","exportResourceStream","contentDisposition","attachment;filename=${exportResourceFileName}"}),
		@Result(name="importResourcePage",location="/WEB-INF/web/resource/importResourcePage.jsp")
	}
)
public class ResourceUpDownAction extends AbstractUpDownAction{

	private final static Logger LOG =Logger.getLogger(ResourceUpDownAction.class);
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
	
	/**
	 * @return 导出产品
	 */
    @DownloadAnnotation("resourceUpDownAction_exportResource")
	public String exportResource(){
    	if(0!=sysRsRcCatalog.getId()){
    		sysRsRcCatalog = sysResourceTypeService.getRsRcCatalogInfo(sysRsRcCatalog.getId());	
    		exportResourceFileName = sysRsRcCatalog.getCatalogCode()+"_"+sysRsRcCatalog.getCatalogName()+".xls";
    	}else{
    		exportResourceFileName = "ALL_PRODUCT.xls";
    	}
		List<SysRsRcAttribCatalog> attrList =  sysResourceTypeService.findAllAttr(sysRsRcCatalog);
		List<SysResource> sysResources = sysResourceService.findParentByRsRcCatalogId(sysRsRcCatalog.getId());
		List<SysRsRcAttrib> sysRsRcAttribs = sysResourceService.findSysRsRcAttribByResourceIds(
					StringBufferByCollectionUtil.convertCollection(sysResources,"id"));
		
		JSONArray arr = packageRsources(attrList, sysResources, sysRsRcAttribs);
		Workbook workbook=null;
        try {
            workbook = exportExcel(arr,attrList);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
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

	private JSONArray packageRsources(List<SysRsRcAttribCatalog> attrList,
			List<SysResource> sysResources, List<SysRsRcAttrib> sysRsRcAttribs) {
		JSONArray arr = new JSONArray();
		Map<Integer,Map<Integer,SysRsRcAttrib>> map = conToMap(sysRsRcAttribs);
		for(SysResource resource:sysResources){
			JSONObject newObj = JSONObject.fromObject(resource);
			Map<Integer,SysRsRcAttrib> resourceMap = map.get(resource.getId());
			if(null != resourceMap){
				for(SysRsRcAttribCatalog attrCatalog : attrList){
					SysRsRcAttrib attrib = resourceMap.get(attrCatalog.getId());
					newObj.put(attrCatalog.getId(),null != attrib ?attrib.getRsrcAttribValue():"");
				}
			}
			arr.add(newObj);
		}
		return arr;
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
	public Workbook exportExcel(JSONArray resourceList,List<SysRsRcAttribCatalog> attrList)  {
	    Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("exportResource");
		int nCol = 0;  //列编号
		int nRow = 0;  //行编号
		List<String> results = new ArrayList<String>();
		//results.add(getText("工种"));
		results.add(getText("产品编号"));
		results.add(getText("产品名称"));
		results.add(getText("产品简称"));
		results.add(getText("采购价格"));
		results.add(getText("销售价格"));
		results.add(getText("品牌"));
		
		for(SysRsRcAttribCatalog attrType:attrList){
			results.add(attrType.getRsrcAttribName());
		}
		
		results.add(getText("供应商名称"));
		results.add(getText("供应商联系人"));
		results.add(getText("供应商地址"));
		results.add(getText("供应商电话"));
		
		// 创建单元格样式
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 创建Excel的sheet的一行
		Row row = sheet.createRow(nRow++);
	    Cell cell =null;
		row.setHeightInPoints(15);// 设定行的高度
		nCol = 0;
		for (String s : results) {
			sheet.setColumnWidth(nCol, 4000);
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(s);
		}
		
		for(int index=0;index<resourceList.size();index++){
			JSONObject obj = resourceList.getJSONObject(index);
			//打包表格数据
			row = sheet.createRow(nRow++);
			nCol=0;
			
//			cell = row.createCell(nCol++);
//			cell.setCellStyle(style);
//			cell.setCellValue(BaseDataDictionaryUtil.valueMap.get(4).get(obj.getString("workType")).getName());
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("rsrcCode"));
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("rsrcName"));
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("abbreviaName"));
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("purchasePrice"));
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("salePrice"));
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("brand"));
			
			//根据本身属性和继承属性的查询资源属性值
			for(SysRsRcAttribCatalog attr:attrList){
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				if(obj.containsKey(attr.getId().toString())){
					String value = obj.getString(attr.getId().toString());
					cell.setCellValue(value);
				}else{
					cell.setCellValue("");
				}
			}
			
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("supplierName"));
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("supplier"));
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("supplierAddress"));
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(obj.getString("supplierPhone"));
		}
	 return wb;
	}
	
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
		
		List<SysRsRcAttribCatalog> attrList =  sysResourceTypeService.findAllAttr(sysRsRcCatalog);
		
		Map<String,SysRsRcAttribCatalog> attrMap = CollectionUtil.listToMap(attrList,"rsrcAttribName");
		
		Map<Integer,SysRsRcAttribCatalog> importAttrMap=new LinkedHashMap<Integer,SysRsRcAttribCatalog>();
		List<SysResource> resourceList=new ArrayList<SysResource>();
		InputStream in=null;
		String valdateString;
	    try {
	        in = new FileInputStream(importResource);
			Workbook workBook=null;
			if(importResourceFileName.endsWith(".xls")){
				workBook=new HSSFWorkbook(in);
			}else{
				workBook=new XSSFWorkbook(in);
			}
			//遍历表格sheet
			for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
			    Sheet sheet = workBook.getSheetAt(numSheet);
			    if (sheet == null) {
			        continue;
			    }
			    //遍历每个sheet的每行row
			    for (int rowNum =0; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						break;
					}
					Cell cell;
					if(rowNum-0==0){
						//遍历row的cell
						for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
							if(cellNum > 5){
								cell=row.getCell(cellNum);
								String value=cutAfterPoint(PoiUtil.getCellValue(cell,sheet,workBook));
								if(!value.equals("供应商名称")&&!value.equals("供应商联系人")&&!value.equals("供应商地址")&&!value.equals("供应商电话")){
									SysRsRcAttribCatalog attrCata=attrMap.get(value);
									if(null==attrCata){
										throw new MineException(
												String.format("第(%s)行第(%s)列的产品属性(%s)与产品类型定义属性不匹配",rowNum+1,cellNum+1,value));
									}else{
										importAttrMap.put(cellNum,attrCata);
									}
								}
							}
						}
					}else{
						//打包导入资源数据
						valdateString = packResourceList(row,sheet,workBook,importAttrMap,resourceList);
						if(null!=valdateString){
							throw new MineException(valdateString);
						}
					}
				}
   		 	}
			sysResourceService.saveImportResources(resourceList,sysRsRcCatalog);
			return success("导入成功!");
	    }catch(Exception e){
		    return error("导入失败!",e);
		}finally{
		    try {
               in.close();
           } catch (IOException e) {
               LOG.error(e.getMessage());
           }
		}
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
	private String packResourceList(Row row,Sheet sheet,Workbook workBook,
			Map<Integer,SysRsRcAttribCatalog> importAttrMap,List<SysResource> resourceList){
		SysResource sysRcResource = new SysResource();
		sysRcResource.setRsrcCode(cutAfterPoint(PoiUtil.getCellValue(row.getCell(1),sheet,workBook)));
		if(null==sysRcResource.getRsrcCode()){
			return String.format("第(%s)行的产品代号不能为空",row.getRowNum()+1);
		}
		//后来添加（以前写在service层）主要判断导入数据的相同性
		boolean flag=true;
		
		for(SysResource addResource:resourceList){
			if(addResource.getRsrcCode().equals(sysRcResource.getRsrcCode())){
				flag=false;
			}
		}
		if(flag){
//			String workType = PoiUtil.getCellValue(row.getCell(0),sheet,workBook);
//			if(MyStringUtil.isBlank(workType)){
//				return String.format("第(%s)行工种类型不能为空",row.getRowNum()+1);
//			}
//			SysDataDictionary dictionary = BaseDataDictionaryUtil.nameMap.get(4).get(workType);
//			if(null != dictionary){
//				sysRcResource.setWorkType(Integer.parseInt(dictionary.getValue()));
//			}else{
//				return String.format("第(%s)行工种类型未定义",row.getRowNum()+1);
//			}
			
//			String rsrcCode = cutAfterPoint(PoiUtil.getCellValue(row.getCell(1),sheet,workBook));
//			if(MyStringUtil.isBlank(rsrcCode)){
//				return String.format("第(%s)行产品代号不能为空",row.getRowNum()+1);
//			}
			sysRcResource.setRsrcCode(cutAfterPoint(PoiUtil.getCellValue(row.getCell(0),sheet,workBook)));
			
			sysRcResource.setRsrcName(cutAfterPoint(PoiUtil.getCellValue(row.getCell(1),sheet,workBook)));
			sysRcResource.setAbbreviaName(cutAfterPoint(PoiUtil.getCellValue(row.getCell(2),sheet,workBook)));

			String purchasePrice = PoiUtil.getCellValue(row.getCell(3),sheet,workBook);
			if(MyStringUtil.isNotBlank(purchasePrice) && MyNumberUtil.isNumber(purchasePrice)){
				sysRcResource.setPurchasePrice(new BigDecimal(purchasePrice));
			}else{
				sysRcResource.setPurchasePrice(BigDecimal.ZERO);
				//return String.format("第(%s)行的采购价格不能为空且必须为数值类型",row.getRowNum()+1);	
			}
			
			String salePrice = PoiUtil.getCellValue(row.getCell(4),sheet,workBook);
			if(MyStringUtil.isNotBlank(salePrice) && MyNumberUtil.isNumber(salePrice)){
				sysRcResource.setSalePrice(new BigDecimal(salePrice));
			}else{
				sysRcResource.setPurchasePrice(BigDecimal.ZERO);
				//return String.format("第(%s)行的销售价格不能为空且必须为数值类型",row.getRowNum()+1);	
			}
			sysRcResource.setBrand(cutAfterPoint(PoiUtil.getCellValue(row.getCell(5),sheet,workBook)));
			
			int cellNumIndex = 6;
			List<SysRsRcAttrib> sysRcRsrcAttribList=new ArrayList<SysRsRcAttrib>();
			for(Integer key:importAttrMap.keySet()){
				cellNumIndex++;
				SysRsRcAttribCatalog sysRcRsrcAttribType = importAttrMap.get(key);
				if(null!=sysRcRsrcAttribType){
					SysRsRcAttrib sysRcRsrcAttrib = new SysRsRcAttrib();
					sysRcRsrcAttrib.setRsraAttribCatalogId(sysRcRsrcAttribType.getId());
					String stringValue=PoiUtil.getCellValue(row.getCell(key),sheet,workBook);
					//当单元格数据不为空
					if(null!=stringValue){
						//验证导入资源数据长度
						if(null!=sysRcRsrcAttribType.getDataLength()){
							String validataLength=validateDataLength(sysRcResource, stringValue,sysRcRsrcAttribType);
							if(null!=validataLength){
								return validataLength;
							}
						}
						SysRsRcBaseData dataType = SysRcBaseDataTypeUtil.allBaseDataMap.get(sysRcRsrcAttribType.getDataTypeId());
						SysRsRcBaseData controlType = SysRcBaseDataTypeUtil.allBaseDataMap.get(sysRcRsrcAttribType.getControlTypeId());
						//验证控件类型与数据合法性
						if(null!=controlType){
							String validataControlList=validataControlList(sysRcResource, stringValue,
									sysRcRsrcAttribType, controlType);
							if(null!=validataControlList){
								return validataControlList;
							}
						}
						//验证数据类型合法性
						if(null!=dataType){
							String validateDataType=validateDataType(sysRcResource, stringValue,
									sysRcRsrcAttribType, dataType);
							if(null!=validateDataType){
								return validateDataType;
							}
						}
						sysRcRsrcAttrib.setRsrcAttribValue(stringValue);
					}else{
						sysRcRsrcAttrib.setRsrcAttribValue(null);
					}
					sysRcRsrcAttribList.add(sysRcRsrcAttrib);
				}
			}
			sysRcResource.setSupplierName(cutAfterPoint(PoiUtil.getCellValue(row.getCell(cellNumIndex++),sheet,workBook)));
			sysRcResource.setSupplier(cutAfterPoint(PoiUtil.getCellValue(row.getCell(cellNumIndex++),sheet,workBook)));
			sysRcResource.setSupplierAddress(cutAfterPoint(PoiUtil.getCellValue(row.getCell(cellNumIndex++),sheet,workBook)));
			sysRcResource.setSupplierPhone(cutAfterPoint(PoiUtil.getCellValue(row.getCell(cellNumIndex++),sheet,workBook)));
			
			sysRcResource.setSysRcRsrcAttribList(sysRcRsrcAttribList);
			resourceList.add(sysRcResource);
		}
		return null;
	}
	
	/**
	 * @author 方宜斌
	 * <b>验证数据类型</b>
	 * 2014-11-21
	 * @param sysResource
	 * @param stringValue
	 * @param sysRcRsrcAttribType
	 * @param dataType
	 * @return
	 */
	private String validateDataType(SysResource sysResource,
			String stringValue, SysRsRcAttribCatalog sysRcRsrcAttribType,
			SysRsRcBaseData dataType) {
		if("number".equals(dataType.getRealName())){
			boolean flag=false;
			if(!MyNumberUtil.isNumber(stringValue)){
				flag=true;
			}
			if(flag){
				return String.format(getText("产品代号(%s)的属性列(%s)的导入数据(%s)的类型错误,不为数值类型"),sysResource.getRsrcCode(),
						sysRcRsrcAttribType.getRsrcAttribName(),stringValue);
			}
		}
		if("date".equals(dataType.getRealName())){
			boolean flag=false;
			if(!MyDateUtils.validateDate(stringValue)){
				flag=true;
			}
			if(flag){
				return String.format(getText("产品代号(%s)的属性列(%s)的导入数据(%s)的类型错误，不为日期类型或者不合法"),sysResource.getRsrcCode(),
						sysRcRsrcAttribType.getRsrcAttribName(),stringValue);
			}
		}
		return null;
	}

	/**
	 * @author 方宜斌
	 * <b>验证控件类型为列表时的导入数据合法性</b>
	 * 2014-11-21
	 * @param sysResource
	 * @param stringValue
	 * @param sysRcRsrcAttribType
	 * @param controlType
	 * @return
	 */
	private String validataControlList(SysResource sysResource,
			String stringValue, SysRsRcAttribCatalog sysRcRsrcAttribType,
			SysRsRcBaseData controlType) {
		if("checkbox".equals(controlType.getRealName())||"select".equals(controlType.getRealName())){
			String[] defaultValues=sysRcRsrcAttribType.getDefaultValue().split("\\|");
			boolean flag=true;
			for(String value:defaultValues){
				if(value.equals(stringValue)){
					flag=false;
					break;
				}
			}
			if(flag){
				return String.format(getText("产品代号(%s)的属性列(%s)的导入数据(%s)的与设置列表的值不对应"),sysResource.getRsrcCode(),
						sysRcRsrcAttribType.getRsrcAttribName(),stringValue);
			}
		}
		return null;
	}

	/**
	 * @author 方宜斌
	 * <b>验证数据长度</b>
	 * 2014-11-21
	 * @param sysRcResource
	 * @param stringValue
	 * @param sysRcRsrcAttribType
	 * @return
	 */
	private String validateDataLength(SysResource sysRcResource,
			String stringValue, SysRsRcAttribCatalog sysRcRsrcAttribType) {
		boolean flag=false;
		if(stringValue.length()>sysRcRsrcAttribType.getDataLength().intValue()){
			flag=true;
		}
		if(flag){
			return String.format(getText("产品代号(%s)的属性列(%s)的导入数据(%s)长度大于设置值！"),sysRcResource.getRsrcCode(),
					sysRcRsrcAttribType.getRsrcAttribName(),stringValue);
		}
		return null;
	}
	
	/**
	  * @author 方宜斌
	  * 2015-4-16
	  * @param cellValue
	  * @return
	  * <p>截取数值，主要针对传过来的字符串带有小数点</p>
	*/
	private String cutAfterPoint(String cellValue){
		//判断是否是数值
		if(MyNumberUtil.isNumber(cellValue)){
			String[] values = cellValue.split("\\.");
			if(values.length > 1){
				char[] chars = values[1].toCharArray();
				boolean flag = false;
				for(char c:chars){
					if(c != '0'){
						flag = true;
						break;
					}
				}
				if(flag){
					return cellValue;
				}
			}
			return values[0];
		}
		return cellValue;
		
		//判断是否是正整数
		//if(null!=cellValue){
		//	return cellValue.split("\\.")[0];
		//}else{
		//	return cellValue;
		//}
	}
	
	
	
	

	public SysRsRcCatalog getSysRsRcCatalog() {
		return sysRsRcCatalog;
	}

	public void setSysRsRcCatalog(SysRsRcCatalog sysRsRcCatalog) {
		this.sysRsRcCatalog = sysRsRcCatalog;
	}
}

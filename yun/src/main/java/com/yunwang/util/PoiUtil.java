package com.yunwang.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * @author fangyibin
 * 2015-11-18
 * <p>poi工具类</p>
*/
public class PoiUtil {
	
	private PoiUtil(){}
	
	/**
	 * @date 2017-6-7
	 * @author YBF
	 * @return
	 * <p>获取POI的表格对象</p>
	 * @throws IOException 
	 */
	public static Workbook getWorkBook(InputStream in,String fileName) throws IOException{
		Workbook workBook;
		if(fileName.endsWith(".xls")){
			workBook = new HSSFWorkbook(in);
		}else{
			workBook = new XSSFWorkbook(in);
		}
		return workBook;
	}
	
	/**
	 * @date 2017-6-13
	 * @author YBF
	 * @param workbook
	 * @return
	 * <p>默认的单元格样式</p>
	 */
	public static CellStyle getCellStyle(Workbook workbook) {
		CellStyle newStyle=workbook.createCellStyle();
		newStyle.setBorderBottom(CellStyle.BORDER_THIN);
		newStyle.setBorderLeft(CellStyle.BORDER_THIN);
		newStyle.setBorderRight(CellStyle.BORDER_THIN);
		newStyle.setBorderTop(CellStyle.BORDER_THIN);
		newStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
		return newStyle;
	}
	
	/**
	  * @author 方宜斌
	  * 2015-7-27
	  * @param cell
	  * @param sheet
	  * @param workBook
	  * @return
	  * <p>获取单元格数据</p>
	*/
	public static String getCellValue(Cell cell,Sheet sheet,Workbook workBook) {
	    if(cell!=null){
	    	if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
   			return String.valueOf(cell.getBooleanCellValue());
   		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
   			if(HSSFDateUtil.isCellDateFormatted(cell)){
   	    		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
   	    		Date dt=HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
   	    		return s.format(dt);
   			}
   			return String.valueOf(cell.getNumericCellValue());
   		} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
   			return null;
   		} else if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
   			/*
   			 * 对表格应用了公式的转换
   			 */
   			FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();  
   		    CellReference cellReference = new CellReference(cell);   
   			Row row = sheet.getRow(cellReference.getRow());  
   			CellValue cellValue = evaluator.evaluate(row.getCell(cellReference.getCol())); 
   			return cellValue.formatAsString();
   		}else{
   			return String.valueOf(cell.getStringCellValue().trim());
   		}
		}else{
		    return null;
		}
	}
	
	/**
	 * 由于Excel当中的单元格Cell存在类型,若获取类型错误就会产生异常, 所以通过此方法将Cell内容全部转换为String类型
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if(null==cell){
			return null;
		}
		String str = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			str = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			str = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			str = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			str = String.valueOf((long) cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			str = String.valueOf(cell.getStringCellValue());
			break;
		default:
			str = null;
			break;
		}
		if(null!=str){
			str=str.trim();
		}
		return str;
	}

}

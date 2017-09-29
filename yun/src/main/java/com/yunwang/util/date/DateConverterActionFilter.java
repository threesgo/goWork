package com.yunwang.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
/**
 * @author 刘迪 
 * 解决struts2在个别机器环境下不能将字符转换为Date的问题
 * 2014-1-19  上午9:01:33
 * 
 */
public class DateConverterActionFilter extends StrutsTypeConverter {
	
    private SimpleDateFormat sdfDate = new SimpleDateFormat(MyDateUtils.FORMAT_DATE); 

    private SimpleDateFormat sdfTime = new SimpleDateFormat(MyDateUtils.FORMAT_DATETIME);


	/**
      * convertFromString().
      * @author 刘迪 
      * @date 2014-1-19  上午9:01:33
	  * 判断是否为日期类型
	  * return 日期类型或null
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		} else {
			Date date = null;
			String dateString = values[0];
			if (dateString != null) {
				try {
					date = sdfTime.parse(dateString);
				} catch (ParseException e) {
					date = null;
				}
				if (date == null) {
					try {
						date = sdfDate.parse(dateString);
					} catch (ParseException e) {
						date = null;
					}
				}
			}
			return date;
		}
	}


	/**
      * convertToString().
      * @author 刘迪 
      * @date 2014-1-19  上午9:01:33
	  * 将日期转换为带时分秒的字符串
	  * return String
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		if (o instanceof Date) {
			return sdfTime.format((Date) o);
		} else {
			return "";
		}
	}

}

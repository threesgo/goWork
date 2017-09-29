package com.yunwang.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

//解决struts2在个别机器环境下不能将字符转换为Date的问题
//该类不可删
public class DateConverter extends StrutsTypeConverter {
	
	private static final String FORMATDATE = "yyyy-MM-dd";
    private static final String FORMATTIME = "yyyy-MM-dd HH:mm:ss";
    
	@SuppressWarnings("rawtypes")
	@Override
	 public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		}
		// 有时分秒的要先转换
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATTIME);
		Date date = null;
		String dateString = values[0];
		if (dateString != null) {
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				date = null;
			}
			if (date == null) {
				sdf = new SimpleDateFormat(FORMATDATE);
				try {
					date = sdf.parse(dateString);
				} catch (ParseException e) {
					date = null;
				}
			}
		}
		return date;
	} 

	@SuppressWarnings({ "rawtypes" })
	@Override
	 public String convertToString(Map context, Object o) {
		if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMATTIME);
			return sdf.format((Date) o);
		}
		return "";
	}

}

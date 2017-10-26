package com.yunwang.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * @author 刘迪 
 * 日期类型处理类
 * 2014-1-20  下午1:19:35
 */
public class MyDateUtils extends DateUtils {
    private final static Logger LOG =Logger.getLogger(MyDateUtils.class);
    
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_DATE = "yyyy-MM-dd";

	public static final long SECOND=1000;

	public static final long MINUTE=60*SECOND;
	
	public static final long HOUR=60*MINUTE;
	
	public static final long DAY=24*HOUR;

	// 需要判断年是否正确, 跨年的时候可能需要调整
	public static Date getDateNjudgeY(String dtstring, String pattern) {
		Date m = getDate(dtstring, pattern);
		Calendar mm = Calendar.getInstance();
		mm.setTime(m);
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.YEAR) == mm.get(Calendar.YEAR)
				&& now.get(Calendar.MONTH) == Calendar.JANUARY
				&& mm.get(Calendar.MONTH) == Calendar.DECEMBER) {
			mm.add(Calendar.YEAR, -1);
		}
		return mm.getTime();
	}

	public static Date getLastDate(Date date1, Date date2) {
		if (date1.before(date2)) {
			return date2;
		}
		return date1;
	}

	
	// 转换字符串为指定格式日期时间
	public static Date getDate(String dtstring, String pattern, Locale locale) {
		DateFormat df = new SimpleDateFormat(pattern, locale);
		Date d = null;
		try {
			d = df.parse(dtstring);
		} catch (Exception e) {
		}
		return d;
	}

	// 转换日期时间为指定格式字符串
	public static String formatDateTime(Date myDate, String pattern) {
		DateFormat fd = new SimpleDateFormat(pattern);
		return fd.format(myDate);
	}

	
	// 比较两个日期是否是同日
	public static boolean isSameD(Calendar a, Calendar b) {
		return a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH);
	}

	
	// 比较两个日期是否是同年月日
	public static boolean isSameYMD(Calendar a, Calendar b) {
		if (a.get(Calendar.YEAR) == b.get(Calendar.YEAR)) {
			if (a.get(Calendar.MONTH) == b.get(Calendar.MONTH)) {
				if (a.get(Calendar.DAY_OF_MONTH) == b
						.get(Calendar.DAY_OF_MONTH)) {
					return true;
				}
			}
		}
		return false;
		// DateUtils.truncate(a,Calendar.DAY_OF_MONTH).equals(DateUtils.truncate(b,Calendar.DAY_OF_MONTH));
	}

	
	// 比较两个日期是否是同年同月
	public static boolean isSameYM(Calendar a, Calendar b) {
		if (a.get(Calendar.YEAR) == b.get(Calendar.YEAR)) {
			if (a.get(Calendar.MONTH) == b.get(Calendar.MONTH)) {
				return true;
			}
		}
		return false;
	}

	
	// 比较两个日期是否是同年同月
	public static boolean isSameYM(Date a, Date b) {
		Calendar x = Calendar.getInstance();
		x.setTime(a);
		Calendar y = Calendar.getInstance();
		y.setTime(b);
		return isSameYM(x, y);
	}

	
	// 计算两个日期相隔天数
	public static int daysBetween(Calendar s, Calendar e) {
		s = DateUtils.truncate(s, Calendar.DAY_OF_MONTH);
		e = DateUtils.truncate(e, Calendar.DAY_OF_MONTH);
		long i = s.getTimeInMillis();
		long j = e.getTimeInMillis();
		return (int) ((j - i) / (1000 * 60 * 60 * 24));
	}

	
	// 计算两个日期相隔天数
	public static int daysBetween(Date s, Date e) {
		Calendar ss = Calendar.getInstance();
		ss.setTime(s);
		Calendar ee = Calendar.getInstance();
		ee.setTime(e);
		return daysBetween(ss, ee);
	}

	public static int translateDayofWeek(Calendar c) {
		int s = c.get(Calendar.DAY_OF_WEEK);
		if (s == 1) {
			return 64;
		}
		return (int) Math.pow(2, s - 2);
	}
	
	// 取指定日期的相差天数日期
	public static Date getAppointDate(Integer daynum, Date day) {
		Calendar td = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			td.setTime(sdf.parse(sdf.format(day == null ? day : new Date())));
		} catch (ParseException e) {
		}
		td.add(Calendar.DAY_OF_MONTH, -(daynum - 1));
		return td.getTime();
	}
	
	/** 
	  * getDate() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午1:27:29
	  * <p>转换字符串为指定格式日期时间 </p> 
	  * @param dtstring 字符型日期
	  * @param pattern  需要转换的格式
	  * @return Date  转换后的日期
	*/ 
	public static Date getDate(String dtstring, String pattern) {
	    String temp=pattern;
		int pmIndex = dtstring.toUpperCase().indexOf("PM");
		int amIndex = dtstring.toUpperCase().indexOf("AM");
		int aIndex = temp.indexOf("a");
		
		if ((pmIndex >= 0 || amIndex >= 0) && aIndex == -1) {
			if (temp.indexOf("HH:mm:ss") != -1){
			    temp = temp.replaceAll("HH:mm:ss", "hh:mm:ss a");
			}
			else{
			    temp = temp.replaceAll("hh:mm:ss", "hh:mm:ss a");
			}
		}
		if (pmIndex == -1 && amIndex == -1 && aIndex >= 0) {
		    temp = temp.replaceAll("hh:mm:ss a", "HH:mm:ss");
		}
		
		SimpleDateFormat SDF = new SimpleDateFormat(temp, Locale.getDefault());
		Date date = null;
		try {
			date = SDF.parse(dtstring);
		} catch (Exception e) { 
		    LOG.error(e.getMessage());
		}
		
		if (date == null) {
			try {
				SDF = new SimpleDateFormat(temp, Locale.US);
				date = SDF.parse(dtstring);
			} catch (Exception e) { 
			    LOG.error(e.getMessage());
			}
		}
		
		if (date == null) {
			try {
				SDF = new SimpleDateFormat(temp, Locale.CHINA);
				date = SDF.parse(dtstring);
			} catch (Exception e) { 
			    LOG.error(e.getMessage());
			}
		}
		
		if (amIndex >= 0) {// 解决 12:15 AM 类似问题, 应该是 00:15 AM
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.HOUR_OF_DAY) == 12) {
				cal.add(Calendar.HOUR_OF_DAY, -12);
				date = cal.getTime();
			}
		}
		return date;
	}
	
	
	/** 
	  * convertString() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午1:57:06
	  * <p>转换日期类型为指定格式字符串 </p> 
	  * @param date 日期
	  * @param pattern 需要转换的格式
	  * @return String  转换后的日期字符串
	*/ 
	public static String convertString(Date date, String pattern) {
		if(null==date){
			return null;
		}
	    SimpleDateFormat SDF = new SimpleDateFormat(pattern);
		try {
			return SDF.format(date);
		} catch (Exception e) {
		    LOG.error(e.getMessage());
			return null;
		}
	}

	public static String convertString(Date date) {
		if(null==date){
			return null;
		}
	    SimpleDateFormat SDF = new SimpleDateFormat(FORMAT_DATETIME);
		try {
			return SDF.format(date);
		} catch (Exception e) {
		    LOG.error(e.getMessage());
			return null;
		}
	}
	/** 
	  * getDateTimeByString() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午1:41:51
	  * <p>日期转换 </p> 
	  * @param dtstring
	  * @return Date  返回带时分秒的日期
	*/ 
	public static Date getDateTimeByString(String dtstring) {
		return getDate(dtstring, FORMAT_DATETIME);
	}
	

	/** 
	  * getDateByString() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午1:44:01
	  * <p>日期转换 </p> 
	  * @param dtstring
	  * @return Date  只返回年月日
	*/ 
	public static Date getDateByString(String dtstring){
		return getDate(dtstring, FORMAT_DATE);
	}
	

	/** 
	  * getStringByDateTime() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午1:58:29
	  * <p>日期转换 </p> 
	  * @param date
	  * @return String  返回包含时分秒的字符串日期
	*/ 
	public static String getStringByDateTime(Date date) {
		return convertString(date, FORMAT_DATETIME);
	}
	
	
	/** 
	  * getStringByDate() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午1:59:07
	  * <p>日期转换 </p> 
	  * @param date
	  * @return
	  * @return String  仅返回年月日字符串
	*/ 
	public static String getStringByDate(Date date) {
		return convertString(date, FORMAT_DATE);
	}
	
	
	/** 
	  * getDateByDate() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午1:47:22
	  * <p>日期转换 </p> 
	  * @param day 日期类型
	  * @return Date  只保留年月日
	*/ 
	public static Date getDateByDate(Date day) {
		return getDate(convertString(day, FORMAT_DATE), FORMAT_DATE);
	}

	
	/** 
	  * calculateSeconds() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午3:46:29
	  * <p>计算两个时间相差的秒 </p> 
	  * @param sDay 起码日期
	  * @param eDay 结束日期
	  * @return int  
	*/ 
	public static int calculateSeconds(Date sDay, Date eDay) {
		return minusDateSeconds(sDay.getTime(), eDay.getTime());
	}
	
	public static int calculateSeconds(Calendar sDay, Calendar eDay) {
		return minusDateSeconds(sDay.getTimeInMillis(), eDay.getTimeInMillis());
	}
	
	private static int minusDateSeconds(Long startSeconds, Long endSeconds) {
		return (int) ((endSeconds - startSeconds) / 1000);
	}
	

	/** 
	  * calculateMintues() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午4:06:11
	  * <p>计算两个时间相差的分钟 </p> 
	  * @param sDay
	  * @param eDay
	  * @return double  
	*/ 
	public static double calculateMintues(Date sDay, Date eDay) {
		return minusMinutes( minusDateSeconds( sDay.getTime(), eDay.getTime() ) );
	}

	public static double calculateMintues(Calendar sDay, Calendar eDay) {
		return minusMinutes( minusDateSeconds( sDay.getTimeInMillis(), eDay.getTimeInMillis() ) );
	}
	
	private static double minusMinutes(Integer seconds) {
		return Math.abs(seconds / (60 * 1.0));
	}
	
	
    /** 
      * calculateHours() method.
      * @author 刘迪 
      * @date 2014-1-20 下午4:05:43
      * <p>计算两个时间相差的小时 </p> 
	  * @param sDay
	  * @param eDay
      * @return double  
    */ 
	public static double calculateHours(Date sDay, Date eDay) {
		return minusHours(minusDateSeconds(sDay.getTime(), eDay.getTime()));
	}

	public static double calculateHours(Calendar sDay, Calendar eDay) {
		return minusHours(minusDateSeconds(sDay.getTimeInMillis(), eDay.getTimeInMillis()));
	}

	private static double minusHours(Integer seconds) {
		return seconds / (60 * 60 * 1.0);
	}
	

	/** 
	  * daysBetween() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午3:42:37
	  * <p>计算两个日期相差的天数 </p> 
	  * @param sDay
	  * @param eDay
	  * @return int  
	*/ 
	public static int calculateDay(Date sDay, Date eDay) {
		Calendar s = Calendar.getInstance();
		s.setTime(sDay);
		Calendar e = Calendar.getInstance();
		e.setTime(eDay);
		return calculateDay(s, e);
	}

	public static int calculateDay(Calendar sDay, Calendar eDay) {
	    Calendar sDay_temp = DateUtils.truncate(sDay, Calendar.DAY_OF_MONTH);
	    Calendar eDay_temp = DateUtils.truncate(eDay, Calendar.DAY_OF_MONTH);
		return (int) ( minusDateSeconds(sDay_temp.getTimeInMillis(), eDay_temp.getTimeInMillis()) / (60 * 60 * 24) );
	}


	/** 
	  * calculateDay() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午5:11:05
	  * <p>增减天数 </p> 
	  * @param day
	  * @param num
	  * @return Date  
	*/ 
	public static Date calculateAddDay(Date day, int num) {
		return calculateMinusOrPlus(day, Calendar.DAY_OF_MONTH, num);
	}

	public static Date calculateAddDay(Calendar day, int num) {
		return calculateMinusOrPlus(day, Calendar.DAY_OF_MONTH, num);
	}

	
	/** 
	  * calulateAddMinute() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午5:11:28
	  * <p>增减分 </p> 
	  * @param day
	  * @param num
	  * @return
	  * @return Date  
	*/ 
	public static Date calculateAddMinute(Date day, int num) {
		return calculateMinusOrPlus(day, Calendar.MINUTE, num);
	}
	
	public static Date calculateAddMinute(Calendar day, int num) {
		return calculateMinusOrPlus(day, Calendar.MINUTE, num);
	}

	
	/** 
	  * calulateAddSecond() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午5:11:31
	  * <p>增减秒 </p> 
	  * @param day
	  * @param num
	  * @return
	  * @return Date  
	*/ 
	public static Date calculateAddSecond(Date day, int num) {
		return calculateMinusOrPlus(day, Calendar.SECOND, num);
	}
	
	public static Date calculateAddSecond(Calendar day, int num) {
		return calculateMinusOrPlus(day, Calendar.SECOND, num);
	}
	
	
	/** 
	  * calculateMinusOrPlus() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午5:00:48
	  * <p>根据操作类型增减时间 </p> 
	  * @param date 需要操作的日期
	  * @param type 操作类型
	  * @param num 增减时间值
	  * @return Date 增减后的日期
	*/ 
	private static <T> Date calculateMinusOrPlus(T date, Integer type, int num){
		Calendar day = null;
		if (date instanceof Date) {
			day = Calendar.getInstance();
			day.setTime((Date) date);
		} else if (date instanceof Calendar) {
			day = (Calendar) date;
		} 
		
		if (null != day) {
			day.add(type, num);
			return day.getTime();
		} else {
			return null;
		}
	}
	
	
	/** 
	  * compareToMax() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午5:45:02
	  * <p>取两个日期大的 </p> 
	  * @param sDay
	  * @param eDay
	  * @return Date  
	*/ 
	public static Date compareMax(Date sDay, Date eDay) {
		if (sDay.compareTo(eDay) > 0) {
			return sDay;
		} else {
			return eDay;
		}
	}
	

	/** 
	  * compareMin() method.
	  * @author 刘迪 
	  * @date 2014-1-20 下午5:51:05
	  * <p>取两个日期小的 </p> 
	  * @param sDay
	  * @param eDay
	  * @return Date  
	*/ 
	public static Date compareMin(Date sDay, Date eDay) {
		if (sDay.compareTo(eDay) < 0) {
			return sDay;
		} else {
			return eDay;
		}
	}
	
	/**
	 * 日期验证
	 * 范围：YYYY-MM-DD 或YYYY/MM/DD 或YYYY.MM.DD 或YYYYMMDD，YYYY-M-D
	 */
	public static boolean validateDate(String ADate) {
		String sDate, sYY, sMM, sDD, sPart;
		int iYY, iMM, iDD, iPos, i;
		ArrayList<String> set = new ArrayList<String>();
		if (ADate == null){
			return false;
		}
			
		sDate = ADate.trim();
		if (sDate.length() > 10 || sDate.length() < 8){
			return false;
		}	
		if (isNum(sDate)) {
			if (sDate.length() != 8){
				return false;
			}	
			sYY = sDate.substring(0, 4);
			sMM = sDate.substring(4, 6);
			sDD = sDate.substring(6, 8);
		} else {
			sDate = sDate.replace('.', '-');
			sDate = sDate.replace('/', '-');
			sDate = sDate + "-";
			i = 0;
			while (sDate.indexOf("-") != -1) {
				iPos = sDate.indexOf("-");
				sPart = sDate.substring(0, iPos);
				set.add(i, sPart);
				sDate = sDate.substring(iPos + 1, sDate.length());
				i++;
			}

			if (set.size() != 3){
				return false;
			}
			sYY = (String) set.get(0);
			sMM = (String) set.get(1);
			sDD = (String) set.get(2);
		}

		if (isNum(sYY) == false || isNum(sMM) == false || isNum(sDD) == false)
		{	
			return false;
		}
		iYY = Integer.parseInt(sYY);
		iMM = Integer.parseInt(sMM);
		iDD = Integer.parseInt(sDD);

		if (iMM < 1 || iMM > 12){
			return false;
		}
		if (((iMM == 1 || iMM == 3 || iMM == 5 || iMM == 7 || iMM == 8
				|| iMM == 10 || iMM == 12) && (iDD >= 1 && iDD <= 31))
				|| ((iMM == 4 || iMM == 6 || iMM == 9 || iMM == 11) && (iDD >= 1 && iDD <= 30))
				|| (isLeapYear(iYY) == true && (iDD >= 1 && iDD <= 29))
				|| (isLeapYear(iYY) == false && (iDD >= 1 && iDD <= 28))) {
			return true;
		} else {
			return false;
		}	
	}
	
	/**
	 * 使日、月成为MM,DD格式。
	 */
	public static String formatStr(int iVal) {
		String str = "";
		str = "00" + iVal;
		str = str.substring(str.length() - 2, str.length());
		return str;
	}
	
	/**
	 * 判断是否为闰年
	 */
	public static boolean isLeapYear(int year) {
		if (year > 1582 && year % 4 == 0
				&& (year % 100 != 0 || (year % 100 == 0 && year % 400 == 0))) {
			return true;
		}	
		else {
			return false;
		}	
	}
	
	/**
	 * 检查是否为数字字符串
	 */
	public static boolean isNum(String Astr) {
		if (Astr == null || Astr.equals("") == true){
			return false;
		}
		for (int i = 0; i < Astr.length(); i++) {
			if (Astr.charAt(i) > '9' || Astr.charAt(i) < '0'){
				return false;
			}	
		}
		return true;
	}
	
	public static String replaceEnMonToNum(String dataString){
		return dataString.replace("Jan","1").
			replace("Feb", "2").
			replace("Mar", "3").
			replace("Apr", "4").
			replace("May", "5").
			replace("Jun", "6").
			replace("Jul", "7").
			replace("Aug", "8").
			replace("Sep", "9").
			replace("Oct", "10").
			replace("Nov", "11").
			replace("Dec", "12");
	}
	
	public static Date stringToDateTime(String dateTime) throws ParseException{
		SimpleDateFormat sdf =   new SimpleDateFormat(FORMAT_DATETIME);
		Date date = sdf.parse(dateTime);
		return date;
	}
	
	
	
	
//	public static void main(String[] args) {
//		Date sday = getDateTimeByString("2014-01-01 00:10:11");
//		Date eday = getDateTimeByString("2013-01-01 10:10:10");
//		System.out.println(calculateDay(sday, eday));
//		System.out.println(calculateHours(sday, eday));
//		System.out.println(calculateMintues(sday, eday));
//		System.out.println(calculateSeconds(sday, eday));
//		System.out.println("----");
//		System.out.println(calculateAddDay(sday, 10));
//		System.out.println(calculateAddMinute(sday, 10));
//		System.out.println(calculateAddSecond(sday, 10));
//
//		System.out.println("----");
//		System.out.println(getDate("Sep 19 2011 12:07:09 PM", "MMM dd yyyy HH:mm:ss"));
//		System.out.println(getDate("Fri May 31 2013", "EEE MMM dd yyyy"));
//		System.out.println(getDate("Mon Apr 01 09:46:19 2013", "EEE MMM dd HH:mm:ss yyyy"));
//		System.out.println(getDate("Sep 12, 2013 3:27:17 Pm", "MMM dd, yyyy hh:mm:ss"));
//		
//		System.out.println("----");
//		System.out.println(compareMax(sday, eday));
//		System.out.println(compareMin(sday, eday));
//	}
}

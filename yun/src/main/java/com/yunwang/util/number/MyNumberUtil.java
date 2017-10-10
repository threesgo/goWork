/**
 * 
 */
package com.yunwang.util.number;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.yunwang.util.string.MyStringUtil;

/**
 * @author 方宜斌
 * @date 2014-4-2下午1:00:24
 *       <p>
 *       </p>
 * @version 1.0
 */

public class MyNumberUtil extends NumberUtils {
	public static final MathContext MC = new MathContext(2, RoundingMode.HALF_DOWN);
	
	public static final Integer ZERO=0;
	public static final Integer ONE=1;
	
	
	public static MathContext getMC(int num){
		return new MathContext(num, RoundingMode.HALF_DOWN);
	}
	
	public static double divide(BigDecimal a,BigDecimal b){
		return divide(a, b, 2);
	}
	
	public static double divide(BigDecimal a,BigDecimal b,int num){
		if(a==null || b==null){
			return 0f;
		}else{
			return formatDouble(a.doubleValue()/b.doubleValue(), num);
		}
	}
	
	public static double multiply(BigDecimal a,BigDecimal b){
		return multiply(a, b, 2);
	}
	public static double multiply(BigDecimal a,BigDecimal b,int num){
		if(a==null || b==null){
			return 0f;
		}else{
			return formatDouble(a.doubleValue()*b.doubleValue(),  num);
		}
	}
	

	
	
	
	
	/** 
	  * getDoubleNumber() method
	  * @author 方宜斌
	  * @date 2014-1-15 下午3:17:26
	  * <p>字符串转double保留两位小数</p> 
	  * @param a  需要被转字符串
	  * @return 
	  * @return double  
	*/ 
	public static double getDoubleNumber(String a){
		if(MyStringUtil.isNotBlank(a)){
			BigDecimal bg = new BigDecimal(Float.parseFloat(a));
			return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		else{
			return 0;
		}
	}
 
    /** 
     * formatMinFloat() method
     * @author 方宜斌
     * @date 2014-1-15 下午3:18:28
     * <p>float类型保留小数</p> 
     * @param arg  需要被转float
     * @param size  小数位长度
     * @return 
     * @return String  
   */ 
	public static String formatMinFloat(float arg, int size){
       int intTemp=(int) arg;
       if(size==0){
           return String.valueOf(intTemp);
       }
       else{
         float fltFmt=  formatFloat(arg,size);
         if(intTemp-fltFmt==0){
             return String.valueOf(intTemp);
         }
         else{
             return  String.valueOf(fltFmt);
         }
       }
	}
   
	/** 
	  * formatFloat() method
	  * @author 方宜斌
	  * @date 2014-1-15 下午3:19:16
	  * <p>Number类型转Float</p> 
	  * @param arg   需要被转Number
	  * @param size  小数位长度
	  * @return 
	  * @return float  
	*/ 
	public static float formatFloat(Number arg, int size){
       return Float.parseFloat(format(arg,size));
   }
   
	/** 
	  * formatDouble() method
	  * @author 方宜斌
	  * @date 2014-1-15 下午3:21:35
	  * <p>Number类型转Double</p> 
	  * @param arg  需要被转Number
	  * @param size  小数位长度
	  * @return 
	  * @return double  
	*/ 
	public static double formatDouble(Number arg, int size){
       return Double.parseDouble(format(arg,size));
   }
   
   /** 
     * format() method
     * @author 方宜斌
     * @date 2014-1-15 下午3:23:09
     * <p>Number类型转String</p> 
     * @param arg  需要被转Number
     * @param size 小数位长度
     * @return 
     * @return String  
   */ 
   public static String format(Number arg, int size){
       if(null==arg){
           return "0";
       }
       else{
           StringBuilder strBuff=new StringBuilder("#");
          if(size>0){
              strBuff.append(".");
              for(int i=0;i<size;i++){
                  strBuff.append("0");
              }
          }
          DecimalFormat df=new DecimalFormat(strBuff.toString());
          return df.format(arg);
       }
   }
	
   
   public static String format(Number arg, String pattern){
       if(null==arg){
           return "0";
       }
       else{
          DecimalFormat df=new DecimalFormat(pattern);
          return df.format(arg);
       }
   }
	
	public static Long[] splitArray(long args) {
		List<Long> a = split(args);
		return a.toArray(new Long[a.size()]);
	}
	
	public static BigInteger[] splitArray(BigInteger args) {
		List<BigInteger> a = split(args);
		return a.toArray(new BigInteger[a.size()]);
	}

	// 分解
	public static List<Long> split(long i) {
		List<Long> a = new ArrayList<Long>();
		long j = 1;
		while (j <= i) {
			if ((i & j) > 0) {
				a.add(j);
			}
			j <<= 1;
		}
		return a;
	}
	
	// 分解
	public static List<BigInteger> split(BigInteger i) {
		List<BigInteger> a = new ArrayList<BigInteger>();
		BigInteger j=BigInteger.ONE;
		while (j.compareTo(i)!=1) {
			if ((i.and(j)).compareTo(BigInteger.ZERO)>0) {
				a.add(j);
			}
			j = j.shiftLeft ( 1 ) ;
		}
		return a;
	}

	// 是否包含
	public static boolean ifExist(long comp, long single) {
		return (comp & single) > 0;
	}
	
	public static boolean ifExist ( BigInteger comp , BigInteger single )
	{
		return comp.and (single).compareTo(BigInteger.ZERO ) > 0 ;
	}
	
	/**
	 * 
	 * @Description: 取2个数组的差集，num1，num2中元素相同，位置不同，元素不得重复(TODO)
	 * @param @param num1
	 * @param @param num2
	 * @param @return   
	 * @return Map<Integer,Integer[]>  
	 * @throws
	 * @author KXL
	 * @date 2017-8-10
	 */
	public static Map<Integer,String[]> differenceSet(Integer[] num1,Integer[] num2){
		Map<Integer,String[]> map = new HashMap<Integer,String[]>();//
		String buf = new String("");
		int position = 0;
		for(int i=0;i<num2.length;i++){
			position = Arrays.binarySearch(num1, num2[i]);
			for(int j=position-1;j>=0;j--){
				if(j>=0){
					buf+=num1[j]+",";
				}
			}
			
			for(int z=i-1;z>=0;z--){
				buf = buf.replace(num2[z]+",","");
			}
			map.put(position, buf.split(","));
			buf = new String("");
		}
		return map;
	}
}

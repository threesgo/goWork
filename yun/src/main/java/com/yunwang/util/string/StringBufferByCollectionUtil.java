package com.yunwang.util.string;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author 刘迪 
 * 
 * 字符串拼接工具类
 * JOINSIGN: 默认字段连接符。可自定义
 * 
 * 2014-1-26  上午9:49:05
 */
public class StringBufferByCollectionUtil {
	
    private final static Logger LOG =Logger.getLogger(StringBufferByCollectionUtil.class);
	/**
	 * 默认连接符
	 */
	private final static String JOINSIGN = ",";
	
	/** 
	  * method() getValue.
	  * @author 刘迪 
	  * @date 2014-1-26 上午10:41:11
	  * <p>
	  * 	通过反射机制获取指定字段值
	  * </p> 
	  * @param obj 实例化对象
	  * @param filed 字段名称。如果为空，直接返回toString
	  * @return String  返回指定字段值，未获取到返回null
	*/ 
	public static String getValue(Object obj, String field) {
		if (null == field) {
			return obj.toString();
		} else {
			try {
				Class<?> clazz = obj.getClass();
				PropertyDescriptor pd = new PropertyDescriptor(field, clazz);
				Method getMethod = pd.getReadMethod();
				if (null != pd) {
					Object o = getMethod.invoke(obj);
					if (null != o) {
						return o.toString();
					}
				}
			} catch (SecurityException e) {
				LOG.error(e.getMessage());
			} catch (IllegalArgumentException e) {
				LOG.error(e.getMessage());
			} catch (IntrospectionException e) {
				LOG.error(e.getMessage());
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage());
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage());
			}
			return null;
		}
	}
	
	
	/** 
	  * lstConvert() method.
	  * @author 刘迪 
	  * @date 2014-1-26 上午10:54:52
	  * <p>
	  * 	指定字段拼接
	  * 	只支持list与set暂不支持map
	  * </p> 
	  * @param obj 传入list或set的列表
	  * @param field 字段名
	  * @param strJoin 拼接符，未指定则用默认
	  * @return String 字符串
	*/ 
	public static String convertCollection(Object obj, String field, String strJoin) {
	    String temp=strJoin;
		if (null == strJoin) {
		    temp = JOINSIGN;
		}
		
		Collection<?> collection = null;
		if (obj instanceof List || obj instanceof Set) {
			collection = (Collection<?>) obj;
		}else if(obj.getClass().isArray()){//如果为数组，先转换list
			Object[] o = (Object[]) obj;
			collection = (List<?>)Arrays.asList(o);
		} 

		if (null != collection) {
			StringBuffer sb = new StringBuffer();
			String value;
			for (Object o : collection) {
				value = getValue(o, field);
				if (MyStringUtil.isNotBlank(value)) {
					sb.append(temp).append(value);
				}
			}

			if (sb.length() > 0) {
				return sb.substring(temp.length());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	
	/** 
	  * convertCollection() method.
	  * @author 刘迪 
	  * @date 2014-1-26 下午12:52:51
	  * <p>使用默认拼接符 </p> 
	  * @param obj
	  * @param field
	  * @return String  
	*/ 
	public static String convertCollection(Object obj, String field) {
		return convertCollection(obj, field, null);
	}

	
	/** 
	  * convertCollection() method.
	  * @author 刘迪 
	  * @date 2014-1-26 下午12:53:09
	  * <p>不指定字段名，直接拼接toString方法</p> 
	  * @param obj
	  * @return String  
	*/ 
	public static String convertCollection(Object obj) {
		return convertCollection(obj, null, null);
	}
	
}

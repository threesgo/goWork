package com.yunwang.util.collection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author 刘迪 集合工具类 2014-2-8 上午11:14:51
 */
public class CollectionUtil {

	private final static Logger LOG = Logger.getLogger(CollectionUtil.class);

	/**
	 * 倒序排
	 */
	public final static Integer DESC = -1;

	/**
	 * 正序排
	 */
	public final static Integer ASC = 1;

	/**
	 * lstSort() method.
	 * 
	 * @author 刘迪
	 * @date 2014-2-8 下午2:20:30
	 *       <p>
	 *       使用默认排序
	 *       </p>
	 * @param lst
	 *            数据列表
	 * @param field排序字段名
	 */
	public static <T> void lstSort(List<T> lst, final String field) {
		lstSort(lst, field, ASC);
	}
	
	/**
	 * @author 方宜斌 2015-9-28
	 * @param lst
	 *            list集合
	 * @param field
	 *            属性字段名称
	 * @return <p>
	 *         list集合转换为map
	 *         </p>
	 */
	@SuppressWarnings("unchecked")
	public static <K,T> Map<K,T> listToMap(List<T> lst, final String field) {
		Map<K,T> map = new HashMap<K,T>();
		try {
			if (null != lst) {
				for (T t : lst) {
					Field f = getClassField(t.getClass(),field);
					if(null!=f){
						f.setAccessible(true);
						map.put((K)f.get(t), t);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return map;
	}
	
	public static Field getClassField(Class<?> clazzs,String fieldName){
		Class<?> clazz=clazzs;
		Field field = null ;  
      	for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
	          try {  
	              field = clazz.getDeclaredField(fieldName) ;  
	              return field ;  
	          } catch (Exception e) {  
	        	  //LOG.error(e.getMessage());
	              //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
	              //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
	          }   
      	}
		return field;
	}

	/**
	 * lstSort() method.
	 * 
	 * @author 刘迪
	 * @date 2014-2-8 下午2:21:09
	 *       <p>
	 *       带指定排序参数的排序
	 *       </p>
	 * @param lst
	 *            数据列表
	 * @param field排序字段名
	 */
	public static <T> void lstSort(List<T> lst, final String field,
			final Integer sort) {
		if (null != lst && lst.size() > 1) {
			Class<?> clazz = lst.get(0).getClass();
			try {
				PropertyDescriptor pd = new PropertyDescriptor(field, clazz);
				final Method getMethod = pd.getReadMethod();
				final String fieldType = getMethod.getReturnType().toString();
				Collections.sort(lst, new Comparator<T>() {
					public int compare(T s1, T s2) {
						try {
							return CollectionUtil.compare(getMethod.invoke(s1),
									getMethod.invoke(s2), fieldType, sort);
						} catch (IllegalArgumentException e) {
							LOG.error(e.getMessage());
						} catch (IllegalAccessException e) {
							LOG.error(e.getMessage());
						} catch (InvocationTargetException e) {
							LOG.error(e.getMessage());
						}
						return 0;
					}
				});
			} catch (IntrospectionException e) {
				LOG.error(e.getMessage());
			} catch (IllegalArgumentException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	/**
	 * compare() method.
	 * 
	 * @author 刘迪
	 * @date 2014-2-8 下午3:03:14
	 *       <p>
	 *       根据字段类型比较参数大小
	 *       </p>
	 * @param t1
	 *            待比较参数1
	 * @param t2
	 *            待比较参数2
	 * @param fieldType
	 *            字段类型
	 * @param sort
	 *            排序方式 1为正序， -1为倒序
	 * @return Integer
	 */
	public static Integer compare(Object t1, Object t2, String fieldType,
			Integer sort) {
		if (t1 == null && t2 == null) {
			return 0;
		} else if (t1 == null) {
			return -sort;
		} else if (t2 == null) {
			return sort;
		} else {
			if ("class java.lang.String".equals(fieldType)) {
				return sort * ((String) t1).compareToIgnoreCase((String) t2);
			} else if ("class java.util.Date".equals(fieldType)) {
				return sort * ((Date) t1).compareTo((Date) t2);
			} else if ("class java.lang.Long".equals(fieldType)) {
				return sort * ((Long) t1).compareTo((Long) t2);
			} else if ("class java.lang.Integer".equals(fieldType)) {
				return sort * ((Integer) t1).compareTo((Integer) t2);
			} else if ("class java.lang.Double".equals(fieldType)) {
				return sort * ((Double) t1).compareTo((Double) t2);
			} else if ("class java.lang.Byte".equals(fieldType)) {
				return sort * ((Byte) t1).compareTo((Byte) t2);
			} else {
				return 0;
			}
		}
	}
	
	/**
	 * @date 2017-3-17
	 * @author YBF
	 * @param src
	 * @return
	 * <p>list集合的深度拷贝</p>
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> deepCopyList(List<T> src)
	{
	    List<T> dest = null;
	    try
	    {
	        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	        ObjectOutputStream out = new ObjectOutputStream(byteOut);
	        out.writeObject(src);
	        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
	        ObjectInputStream in = new ObjectInputStream(byteIn);
	        dest = (List<T>) in.readObject();
	    }
	    catch (Exception e)
	    {
	    	LOG.error(e.getMessage());
	    }
	    return dest;
	}

}

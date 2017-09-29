package com.yunwang.util.datasource;

/**
 * @author YBF
 * @date 2017-7-5
 * <p>多数据源控制器</p>
 */
public class DBContextHolder {
	public static final String DATA_SOURCE_01 = "dataSource_01";  
    public static final String DATA_SOURCE_02 = "dataSource_02";  
      
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
      
    public static void setDBType(String dbType) {  
        contextHolder.set(dbType);  
    }  
      
    public static String getDBType() { 
        return contextHolder.get();  
    }  
      
    public static void clearDBType() {  
        contextHolder.remove();  
    } 
}

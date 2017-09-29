package com.yunwang.util.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author YBF
 * @date 2017-7-5
 * <p>数据源动态切换</p>
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DBContextHolder.getDBType();
	}

}

package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysDataDictionary;

public interface SysDataDictionaryDaoI extends BaseDaoI<SysDataDictionary>{
	
	List<SysDataDictionary> findAll();
}

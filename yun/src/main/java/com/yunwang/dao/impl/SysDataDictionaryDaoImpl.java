package com.yunwang.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysDataDictionaryDaoI;
import com.yunwang.model.pojo.SysDataDictionary;

@Repository
public class SysDataDictionaryDaoImpl extends BaseDaoImpl<SysDataDictionary> implements SysDataDictionaryDaoI{
	@Override
	public List<SysDataDictionary> findAllOrderByValue() {
		return find("SELECT model FROM SysDataDictionary model ORDER BY model.type,model.value");
	}

}

package com.yunwang.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunwang.model.pojo.SysDataDictionary;

public class BaseDataDictionaryUtil {

	//根据type分类下的所有数据字典值
	public final static Map<Integer, List<SysDataDictionary>> baseDataMap = new HashMap<Integer, List<SysDataDictionary>>();
	
	public final static Map<Integer,Map<Integer,SysDataDictionary>> idMap = new HashMap<Integer, Map<Integer,SysDataDictionary>>();
	
	public final static Map<Integer,Map<String,SysDataDictionary>> valueMap = new HashMap<Integer, Map<String,SysDataDictionary>>();
	
	public final static Map<Integer,Map<String,SysDataDictionary>> nameMap = new HashMap<Integer, Map<String,SysDataDictionary>>();
	
	private BaseDataDictionaryUtil(){
		
	}
}

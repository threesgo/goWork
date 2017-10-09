package com.yunwang.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysDataDictionaryDaoI;
import com.yunwang.dao.SysRsRcBaseDataDaoI;
import com.yunwang.dao.SysUserDaoI;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysRsRcBaseData;
import com.yunwang.service.InitService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.MyIOUtils;
import com.yunwang.util.SysRcBaseDataTypeUtil;
import com.yunwang.util.string.MyStringUtil;

@Service
public class InitServiceImpl implements InitService{
	@Autowired
	private SysUserDaoI sysUserDao;
	
	@Autowired
	private SysDataDictionaryDaoI sysDataDictionaryDao;
	
	@Autowired
	private SysRsRcBaseDataDaoI sysRsRcBaseDataDao;
	
	
	public void initData() {
		//数据字典
		loadDictionaryData();
		
		//基础单位数据
		loadBaseDataCatalogData();
		
		//初始化系统默认数据（用户配置，工时卡类型、工时卡结构）
		execuSysSql();
	}
	
	
	private void execuSysSql(){
		try {
			Map<String,Object> context=new HashMap<String, Object>();
			handleBatchSqls("classpath*:initSql/sys_*.sql",context);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleBatchSqls(String locationPattern, Map<String, Object> context) throws IOException {
		Resource[] resources=MyIOUtils.getSysResources(locationPattern);
		for (Resource resource : resources) {
			String sql = MyIOUtils.fileToString(resource.getInputStream());
			if(MyStringUtil.isNotBlank(sql)){
				System.out.println(sql);
				sysUserDao.importSQL(sql);
			}
		}
	}
	
	
	private void loadDictionaryData() {
		// 获取有效的数据字典的值
		List<SysDataDictionary> dictionaryAll = sysDataDictionaryDao.findAll();
		for (SysDataDictionary dictionary : dictionaryAll) {
			List<SysDataDictionary> list = BaseDataDictionaryUtil.baseDataMap.get(dictionary.getType());
			if (null != list) {
				list.add(dictionary);
				BaseDataDictionaryUtil.valueMap.get(dictionary.getType()).put(dictionary.getValue(), dictionary);
				BaseDataDictionaryUtil.nameMap.get(dictionary.getType()).put(dictionary.getName(), dictionary);
				BaseDataDictionaryUtil.idMap.get(dictionary.getType()).put(dictionary.getId(), dictionary);
			} else {
				List<SysDataDictionary> newList = new ArrayList<SysDataDictionary>();
				Map<String, SysDataDictionary> valueMap = new HashMap<String, SysDataDictionary>();
				Map<String, SysDataDictionary> nameMap = new HashMap<String, SysDataDictionary>();
				Map<Integer, SysDataDictionary> idMap = new HashMap<Integer, SysDataDictionary>();

				newList.add(dictionary);
				valueMap.put(dictionary.getValue(), dictionary);
				nameMap.put(dictionary.getName(), dictionary);
				idMap.put(dictionary.getId(), dictionary);

				BaseDataDictionaryUtil.baseDataMap.put(dictionary.getType(),newList);
				BaseDataDictionaryUtil.valueMap.put(dictionary.getType(),valueMap);
				BaseDataDictionaryUtil.nameMap.put(dictionary.getType(),nameMap);
				BaseDataDictionaryUtil.idMap.put(dictionary.getType(), idMap);
			}
		}
	}
	
	//数据进行初始化
	private void loadBaseDataCatalogData(){
		List<SysRsRcBaseData> list=sysRsRcBaseDataDao.findAll();
		SysRcBaseDataTypeUtil.dataTypeList.addAll(getTypeList(list,BigDecimal.ONE));
		SysRcBaseDataTypeUtil.unitTypelist.addAll(getTypeList(list,new BigDecimal(2)));
		SysRcBaseDataTypeUtil.controlTypelist.addAll(getTypeList(list,new BigDecimal(3)));
		SysRcBaseDataTypeUtil.unitGroupList.addAll(getUnitGroup(SysRcBaseDataTypeUtil.unitTypelist));
		putBaseDataMap(list);
	}
	
	//获取所有基础数据map集合
	private void putBaseDataMap(List<SysRsRcBaseData> list){
		for(SysRsRcBaseData catalog:list){
			SysRcBaseDataTypeUtil.allBaseDataMap.put(catalog.getId(), catalog);
		}
	}
	
	//根据类型进行分组
	private List<SysRsRcBaseData> getTypeList(List<SysRsRcBaseData> list,BigDecimal type){
		List<SysRsRcBaseData> catalogList=new ArrayList<SysRsRcBaseData>();
		for(SysRsRcBaseData catalog:list){
			if(type.equals(catalog.getDataType())){
				catalogList.add(catalog);
			}
		}
		return catalogList;
	}
	
	//去除重复单位分组信息
	private List<SysRsRcBaseData> getUnitGroup(List<SysRsRcBaseData> list) {
		List<SysRsRcBaseData> typeList=new ArrayList<SysRsRcBaseData>();
		for(SysRsRcBaseData dataType:list){
			boolean flag=true;
			for(SysRsRcBaseData newDataType:typeList){
				if(null!=dataType.getGroup()&&dataType.getGroup().equals(newDataType.getGroup())){
					flag=false;
					break;
				}
			}
			if(flag){
				typeList.add(dataType);
			}
		}
		return typeList;
	}
}

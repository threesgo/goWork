package com.yunwang.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunwang.model.pojo.SysRsRcBaseData;

public class SysRcBaseDataTypeUtil {
	//数据类型
	public final static List<SysRsRcBaseData> dataTypeList = new ArrayList<SysRsRcBaseData>();
	//单位类型
	public final static List<SysRsRcBaseData> unitTypelist = new ArrayList<SysRsRcBaseData>();
	//控件类型
	public final static List<SysRsRcBaseData> controlTypelist = new ArrayList<SysRsRcBaseData>();
	//单位分组
	public final static List<SysRsRcBaseData> unitGroupList = new ArrayList<SysRsRcBaseData>();
	
	public final static Map<Integer,SysRsRcBaseData> allBaseDataMap = new HashMap<Integer,SysRsRcBaseData>();

	private SysRcBaseDataTypeUtil(){
		
	}
}

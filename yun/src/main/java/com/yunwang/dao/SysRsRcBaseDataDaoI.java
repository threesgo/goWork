package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysRsRcBaseData;

public interface SysRsRcBaseDataDaoI extends BaseDaoI<SysRsRcBaseData>{

	List<SysRsRcBaseData> findAll();
	
	List<SysRsRcBaseData> findByGroup(String group);

}

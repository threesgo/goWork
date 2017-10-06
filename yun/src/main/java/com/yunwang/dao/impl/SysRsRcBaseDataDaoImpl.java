package com.yunwang.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysRsRcBaseDataDaoI;
import com.yunwang.model.pojo.SysRsRcBaseData;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysRsRcBaseDataDaoImpl extends BaseDaoImpl<SysRsRcBaseData> implements SysRsRcBaseDataDaoI{
	public List<SysRsRcBaseData> findByGroup(String group) {
		String hql = "FROM SysRsRcBaseData AS model ";
		if(MyStringUtil.isNotBlank(group)){
			hql = hql+ "WHERE model.group=?";
			return find(hql,group);
		}
		return find(hql);
	}
}

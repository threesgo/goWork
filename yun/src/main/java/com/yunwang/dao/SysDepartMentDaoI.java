package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysDepartMent;

public interface SysDepartMentDaoI extends BaseDaoI<SysDepartMent>{

	List<SysDepartMent> findDepartMentByParentId(Integer parentId);
	
	List<SysDepartMent> findAllDepartMent();
	
}

package com.yunwang.dao;

import java.util.List;

import com.yunwang.model.pojo.SysRsRcAttrib;

public interface SysRsRcAttribDaoI extends BaseDaoI<SysRsRcAttrib>{

	List<SysRsRcAttrib> findByResourceIds(String resourceIds);

	SysRsRcAttrib getByResourceAndAttr(Integer resourceId, Integer attrId);
}

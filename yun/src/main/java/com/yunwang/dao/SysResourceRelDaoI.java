package com.yunwang.dao;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcPackage;

public interface SysResourceRelDaoI extends BaseDaoI<SysResourceRel>{

	SysResourceRel getByResourceId(Integer resourceId);

	Pager<SysResourceRel> findRelResources(SysRsRcPackage sysRsRcPackage,int page, int rows,JSONObject seachObj);

}

package com.yunwang.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcPackage;

public interface SysResourceRelDaoI extends BaseDaoI<SysResourceRel>{

	SysResourceRel getByResourceId(Integer resourceId);

	Pager<SysResourceRel> findRelResources(SysRsRcPackage sysRsRcPackage,int page, int rows,JSONObject seachObj);

	List<SysResourceRel> findPackageResourceData(Integer packageId);

	Pager<SysResourceRel> findPackageResourceData(Integer packageId, int page,
			int rows, JSONObject seachObj);

	void deletePackageResource(String ids);

	List<SysResourceRel> findByFlowId(Integer id);

	List<SysResourceRel> findByOrderId(Integer id);

	Pager<SysResourceRel> findByPackageIdsAndNotInFlow(String packageIds,
			Integer flowId,int page, int rows, JSONObject seachJson);

}

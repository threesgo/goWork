package com.yunwang.webservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysBrand;
import com.yunwang.model.pojo.SysMember;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.model.pojo.SysOrderFlow;
import com.yunwang.model.pojo.SysPcBrandCatalog;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcAttribCatalog;
import com.yunwang.model.pojo.SysRsRcAttribRel;
import com.yunwang.model.pojo.SysRsRcCatalog;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.service.SysBrandService;
import com.yunwang.service.SysMemberService;
import com.yunwang.service.SysOrderService;
import com.yunwang.service.SysResourceService;
import com.yunwang.service.SysResourceTypeService;
import com.yunwang.service.SysRsRcPackageService;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.webservice.HandleMemberService;


@WebService(endpointInterface="com.yunwang.webservice.HandleMemberService",serviceName="HandleMemberWs")
public class HandleMemberServiceImpl implements HandleMemberService {
	
	@Autowired
	private SysMemberService sysMemberService;
	@Autowired
	private SysOrderService sysOrderService;
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysResourceTypeService sysResourceTypeService;
	@Autowired
	private SysRsRcPackageService sysRsRcPackageService;
	@Autowired
	private SysBrandService sysBrandService;
	
	@Override
	public String sayHello(String name) {
		return "Hello:"+name;
	}

	@Override
	public SysMember getByWxCode(String wxCode) {
		SysMember sysMember = sysMemberService.getByWxCode(wxCode);
		return sysMember;
	}
	
	@Override
	public SysMember getByPhoneNumber(String phoneNumber) {
		SysMember sysMember = sysMemberService.getByPhoneNumber(phoneNumber);
		return sysMember;
	}
	
	@Override
	public SysMember saveMember(SysMember sysMember){
		sysMemberService.saveOrUpdate(sysMember);
		return sysMember;
	}

	@Override
	public List<SysOrder> findOrderByMemberAndType(Integer memberId,Integer type,String status) {
		return sysOrderService.findOrderByMemberAndType(memberId,type,status);
	}

	@Override
	public List<SysOrderFlow> findOrderFlowByOrder(Integer orderId) {
		return sysOrderService.findOrderFlow(orderId);
	}

	@Override
	public List<SysRsRcCatalog> findCatalogByParentId(Integer parentId) {
		return sysResourceTypeService.findRsRcCatalogByParentId(parentId);
	}

	@Override
	public List<SysResourceRel> findResourceByCataLogId(Integer catalogId) {
		return sysResourceService.findResourceByCataLogId(catalogId);
	}

	@Override
	public SysOrder getOrderById(Integer id) {
		return sysOrderService.get(id);
	}

	@Override
	public List<SysRsRcPackage> findAllPackages() {
		return sysRsRcPackageService.findAll("orderNo");
	}

	@Override
	public List<SysBrand> findBrandByCatalogId(Integer catalogId) {
		return sysBrandService.findByCatalogId(catalogId);
	}

	@Override
	public List<SysPcBrandCatalog> findBrandCatalogByPackageId(Integer packageId) {
		return sysRsRcPackageService.findAllPcBrandCatalog(packageId);
	}

	@Override
	public List<SysRsRcCatalog> findAllLastCatalog(String filterJson) {
		List<SysRsRcCatalog> allChildren = new ArrayList<SysRsRcCatalog>();
		List<SysRsRcCatalog> sysRcRsrcOrgList = sysResourceTypeService.findRsRcCatalogByParentId(0);
		for(SysRsRcCatalog sysRsRcCatalog:sysRcRsrcOrgList){
			sysRsRcCatalog.setCombineName(sysRsRcCatalog.getCatalogName());
			List<SysRsRcCatalog> childrenList = sysResourceTypeService.findRsRcCatalogByParentId(sysRsRcCatalog.getId());
			if(childrenList.size()>0){
				getChildrenCatalog(sysRsRcCatalog,childrenList,allChildren,filterJson);
			}else{
				if(MyStringUtil.isNotBlank(filterJson)){
					if(sysRsRcCatalog.getCatalogName().contains(filterJson)){
						allChildren.add(sysRsRcCatalog);
					}
				}else{
					allChildren.add(sysRsRcCatalog);
				}
			}
		}
		return allChildren;
	}
	
	private void getChildrenCatalog(SysRsRcCatalog pSysRsRcCatalog,
			List<SysRsRcCatalog> sysRcRsrcOrgList,
			List<SysRsRcCatalog> allChildren,String filterJson){
		for(SysRsRcCatalog sysRsRcCatalog:sysRcRsrcOrgList){
			sysRsRcCatalog.setCombineName(pSysRsRcCatalog.getCombineName()+">"+sysRsRcCatalog.getCatalogName());
			List<SysRsRcCatalog> childrenList = sysResourceTypeService.findRsRcCatalogByParentId(sysRsRcCatalog.getId());
			if(childrenList.size()>0){
				getChildrenCatalog(sysRsRcCatalog,childrenList,allChildren,filterJson);
			}else{
				if(MyStringUtil.isNotBlank(filterJson)){
					if(sysRsRcCatalog.getCatalogName().contains(filterJson)){
						allChildren.add(sysRsRcCatalog);
					}
				}else{
					allChildren.add(sysRsRcCatalog);
				}
			}
		}
	}

	@Override
	public SysResourceRel getResourceById(Integer relResourceId) {
		return sysResourceService.getRelResource(relResourceId);
	}

	@Override
	public List<SysRsRcAttribCatalog> findAttribCataByCatalog(Integer catalogId) {
		return sysResourceTypeService.findAllAttr(catalogId);
	}

	@Override
	public List<SysRsRcAttribRel> findAttribByResourceId(Integer resourceId) {
		return sysResourceService.findAttribRelByResourceId(resourceId);
	}

	@Override
	public List<SysResourceRel> findResourceByOrderId(Integer orderId) {
		return sysResourceService.findByOrderId(orderId);
	}
}

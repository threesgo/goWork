package com.yunwang.webservice;

import java.util.List;

import javax.jws.WebService;

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
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.model.pojo.SysWorker;


@WebService
public interface HandleMemberService {
	public String sayHello(String name);
	
//	public List<Cat> findByUser(User user);
//	
//	public @XmlJavaTypeAdapter(FkXmlAdapter.class) Map<String,Cat> getAllCat();
	
	public SysMember getByWxCode(String wxCode);

	SysMember getByPhoneNumber(String phoneNumber);
	
	SysMember saveMember(SysMember sysMember);
	
	SysOrder getOrderById(Integer id);
	
	List<SysOrder> findOrderByMemberAndType(Integer memberId,Integer type,String status);
	
	List<SysOrderFlow> findOrderFlowByOrder(Integer orderId);
	
	//查询子集类别
	List<SysRsRcCatalog> findCatalogByParentId(Integer parentId);
	
	//根据资源类别查询资源 
	List<SysResourceRel> findResourceByCataLogId(Integer catalogId,String seachJson);
	
	//查询所有的套餐
	List<SysRsRcPackage> findAllPackages();
	
	//查询资源类别与品牌的关联关系
	List<SysBrand> findBrandByCatalogId(Integer catalogId);
	
	//查询套餐中资源类别与品牌的关联关系
	List<SysPcBrandCatalog> findBrandCatalogByPackageId(Integer packageId);
	
	//查询所有的子集资源类别
	List<SysRsRcCatalog> findAllLastCatalog(String filterJson);
	
	//
	SysResourceRel getResourceById(Integer relResourceId);
	
	List<SysRsRcAttribCatalog> findAttribCataByCatalog(Integer catalogId);
	
	List<SysRsRcAttribRel> findAttribByResourceId(Integer resourceId);
	
	List<SysResourceRel> findResourceByOrderId(Integer orderId);
	
	SysBrand getSysBrandById(Integer brandId);
	
	SysSupplier getSysSupplierById(Integer supplierId);
	
	List<SysWorker> findWorkerByOrderId(Integer orderId);
	
	SysOrderFlow getOrderFlowById(Integer orderFlowId);
	
	List<SysResourceRel> findResourceByOrderFlowId(Integer orderFlowId);
}

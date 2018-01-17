package com.yunwang.webservice;

import java.util.List;

import javax.jws.WebService;

import com.yunwang.model.pojo.SysMember;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.model.pojo.SysOrderFlow;
import com.yunwang.model.pojo.SysResource;
import com.yunwang.model.pojo.SysRsRcCatalog;


@WebService
public interface HandleMemberService {
	public String sayHello(String name);
	
//	public List<Cat> findByUser(User user);
//	
//	public @XmlJavaTypeAdapter(FkXmlAdapter.class) Map<String,Cat> getAllCat();
	
	public  SysMember getByWxCode(String wxCode);

	SysMember getByPhoneNumber(String phoneNumber);
	
	SysMember saveMember(SysMember sysMember);
	
	List<SysOrder> findOrderByMember(Integer memberId);
	
	List<SysOrderFlow> findOrderFlowByMember(Integer memberId);
	
	List<SysOrderFlow> findOrderFlowByOrder(Integer orderId);
	
	List<SysRsRcCatalog> findByParentId(Integer parentId);
	
	List<SysResource> findByCataLogId(Integer catalogId);
}

package com.yunwang.webservice.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysMember;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.model.pojo.SysOrderFlow;
import com.yunwang.service.SysMemberService;
import com.yunwang.service.SysOrderService;
import com.yunwang.webservice.HandleMemberService;


@WebService(endpointInterface="com.yunwang.webservice.HandleMemberService",serviceName="HandleMemberWs")
public class HandleMemberServiceImpl implements HandleMemberService {
	
	@Autowired
	private SysMemberService sysMemberService;
	@Autowired
	private SysOrderService sysOrderService;
	
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
	public List<SysOrder> findOrderByMember(Integer memberId) {
		return sysOrderService.findOrderByMember(memberId);
	}

	@Override
	public List<SysOrderFlow> findOrderFlowByMember(Integer memberId) {
		return sysOrderService.findOrderFlowByMember(memberId);
	}

	@Override
	public List<SysOrderFlow> findOrderFlowByOrder(Integer orderId) {
		return sysOrderService.findOrderFlow(orderId);
	}
}

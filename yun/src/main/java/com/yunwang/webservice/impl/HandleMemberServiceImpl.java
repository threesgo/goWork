package com.yunwang.webservice.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.pojo.SysMember;
import com.yunwang.service.SysMemberService;
import com.yunwang.webservice.HandleMemberService;


@WebService(endpointInterface="com.yunwang.webservice.HandleMemberService",serviceName="HandleMemberWs")
public class HandleMemberServiceImpl implements HandleMemberService {
	
	@Autowired
	private SysMemberService sysMemberService;
	
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
	public void saveMember(SysMember sysMember){
		sysMemberService.saveOrUpdate(sysMember);
	}
}

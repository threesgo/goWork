package com.yunwang.webservice;

import javax.jws.WebService;

import com.yunwang.model.pojo.SysMember;


@WebService
public interface HandleMemberService {
	public String sayHello(String name);
	
//	public List<Cat> findByUser(User user);
//	
//	public @XmlJavaTypeAdapter(FkXmlAdapter.class) Map<String,Cat> getAllCat();
	
	public  SysMember getByWxCode(String wxCode);

	SysMember getByPhoneNumber(String phoneNumber);
	
	SysMember saveMember(SysMember sysMember);
}

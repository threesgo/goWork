package com.yunwang.webservice.main;

import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxws.EndpointImpl;

import com.yunwang.webservice.HandleMemberService;
import com.yunwang.webservice.auth.AuthInterceptor;
import com.yunwang.webservice.impl.HandleMemberServiceImpl;

public class ServiceMain {
	public static void main(String[] args) {
		HandleMemberService handleMember = new HandleMemberServiceImpl();
		EndpointImpl sd=(EndpointImpl) Endpoint.publish("http://192.168.1.115:8888/yun/HandleMemberService", handleMember);
		//添加拦截器，检查用户名和密码是否正确
		//sd.getInInterceptors().add(new AuthInterceptor());
		System.out.println("成功");
		
//		HelloWorld hello=new HelloWorldImpl();
//		EndpointImpl sd=(EndpointImpl) Endpoint.publish("http://192.168.0.211:8080/yun/helloWorld", hello);
//		//添加拦截器，检查用户名和密码是否正确
//		//sd.getInInterceptors().add(new AuthInterceptor());
//		System.out.println("成功");
	}
}

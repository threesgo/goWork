package com.yunwang.webservice.impl;

import javax.jws.WebService;

import com.yunwang.webservice.HelloWorld;

@WebService(endpointInterface="com.yunwang.webservice.HelloWorld",serviceName="HelloWorldWs")
public class HelloWorldImpl implements HelloWorld {
	@Override
	public String sayHello(String name) {
		return "Hello:"+name;
	}
}

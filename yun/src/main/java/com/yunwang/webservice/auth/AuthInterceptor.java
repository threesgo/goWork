package com.yunwang.webservice.auth;

import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
	public AuthInterceptor() {
		super(Phase.PRE_INVOKE);
	}
	//拦截器的形参就是被拦截到的soap消息
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		List<Header> headers = message.getHeaders();
		if(null==headers||headers.size()<1){
			throw new Fault(new Exception("没有head头"));
		}
		Header firstHead=headers.get(0);
		Element e=(Element) firstHead.getObject();
		NodeList userNames=e.getElementsByTagName("userName");
		NodeList passWords=e.getElementsByTagName("passWord");
		if(userNames.getLength()!=1){
			throw new Fault(new Exception("用户名格式不对"));
		}
		if(passWords.getLength()!=1){
			throw new Fault(new Exception("密码格式不对"));
		}
		String userName=userNames.item(0).getTextContent();
		String passWord=passWords.item(0).getTextContent();
		if(!userName.equals("fangyibin")||!passWord.equals("7739")){
			throw new Fault(new Exception("用户名密码不对，不允许访问"));
		}
	}

}

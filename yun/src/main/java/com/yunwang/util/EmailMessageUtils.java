package com.yunwang.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


/**
  * @author 			shenjunjun
  * @creatTime 			2016年6月12日 上午10:38:42
  * @description 		email 工具类
  */
public abstract class EmailMessageUtils {
	
	/**
	  * @author 				shenjunjun
	  * @creatTime 				2016年6月12日 上午11:26:48
	  * @description 			发送邮件
	  * @param host				主机名  如smtp.163.com
	  * @param sender			发送人的邮箱 
	  * @param password			收件人的邮箱
	  * @param subject			邮件主题
	  * @param body				发送的信息
	  * @param targetAddress	收件人的邮箱
	  * @param charset
	  * @throws EmailException
	  */
	public static void sendEmail(String host,String sender,String password,
								 String subject,String body,String targetAddress,String charset) throws  EmailException{
		HtmlEmail email = new HtmlEmail();  
        // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"  
        email.setHostName(host);  
        // 字符编码集的设置  
        email.setCharset(charset);  
        // 收件人的邮箱  
        email.addTo(targetAddress.split(","));  
        // 发送人的邮箱  
        email.setFrom(sender);  
        // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
        email.setAuthentication(sender,password);  
        // 要发送的邮件主题  
        email.setSubject(subject);  
        // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
        email.setHtmlMsg(body);
        // 发送  
        email.send();  
	}
	
	
	public static void sendEmail(String host,String sender,String password,
								 String subject,String body,String targetAddress) throws  EmailException{
		sendEmail(host, sender, password, subject, body, targetAddress,"utf-8");
	}
 
}

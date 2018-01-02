package com.yunwang.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author YBF
 * @date 2017-11-28
 * <p>会员</p>
 */

@Entity
@Table(name = "SYS_MEMBER")
public class SysMember extends AbstractRowVersionModel{

//	ID	ID		NUMBER(10)			*		
//	NAME	NAME		VARCHAR2(64)					
//	PASSWORD	PASSWORD		VARCHAR2(64)					
//	PHONENUM	PHONENUM		VARCHAR2(24)		*			
//	EMAIL	EMAIL		VARCHAR2(64)					
//	WE_CODE	WE_CODE		VARCHAR2(64)					
//	WE_NAME	WE_NAME		VARCHAR2(64)					
//	REGTIME	REGTIME		dateTime		*			
//	REGIP	REGIP		VARCHAR2(64)		*			
//	LAST_LOGIN_TIME	LAST_LOGIN_TIME		dateTime					记录一下省的每次查询
//	LAST_LOGIN_IP	LAST_LOGIN_IP		VARCHAR2(64)					记录一下省的每次查询
//	LOGIN_FREQUENCY	LOGIN_FREQUENCY		NUMBER(10)					
//	INVITATION_CODE	INVITATION_CODE		VARCHAR2(24)		*			默认手机号
//	INTEGRAL	INTEGRAL		NUMBER(10)		*			默认0
//	TYPE	TYPE		NUMBER(10)		*			1、普通会员（默认） 2、工人   3、供应商（先维护普通会员）

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ORDER_FLOW")
	private Integer id;
	
	@Column(name = "USER_NAME",length=256)
	private String userName;
	
	@Column(name = "PASSWORD",length=64)
	private String passWord;
	
	@Column(name = "PHONENUM",length=24)
	private String phoneNum;
	
	@Column(name = "EMAIL",length=64)
	private String email;
	
	@Column(name = "WE_CODE",length=256)
	private String weCode; //微信code（唯一key值）
	
	@Column(name = "WE_NAME",length=256)
	private String weName; //微信名称
	
	@Column(name = "REGTIME")
	private Date regTime;
	
	@Column(name = "REGIP",length=64)
	private String regIp;
	
	@Column(name = "LAST_LOGIN_TIME")
	private Date lastLoginTime;
	
	@Column(name = "LAST_LOGIN_IP",length=64)
	private String lastLoginIp;
	
	@Column(name = "LOGIN_FREQUENCY")
	private Integer loginFrequency;
	
	@Column(name = "INVITATION_CODE",length=24)
	private String invitationCode;  //邀请码

	@Column(name = "INTEGRAL",columnDefinition="number default 0")
	private Integer integral; //积分
	
	@Column(name = "TYPE",columnDefinition="number default 1")
	private Integer type;  //1、普通会员（默认） 2、工人   3、供应商（先维护普通会员）   //不能更改
	
	@Column(name = "IS_AUTHORIZE",columnDefinition="number default 0")
	private Integer isAuthorize;  //是否授权 ，后台管理员处理
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeCode() {
		return weCode;
	}

	public void setWeCode(String weCode) {
		this.weCode = weCode;
	}

	public String getWeName() {
		return weName;
	}

	public void setWeName(String weName) {
		this.weName = weName;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginFrequency() {
		return loginFrequency;
	}

	public void setLoginFrequency(Integer loginFrequency) {
		this.loginFrequency = loginFrequency;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsAuthorize() {
		return isAuthorize;
	}

	public void setIsAuthorize(Integer isAuthorize) {
		this.isAuthorize = isAuthorize;
	}
}

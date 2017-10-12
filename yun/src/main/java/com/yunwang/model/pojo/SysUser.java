package com.yunwang.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yunwang.util.date.MyDateUtils;

/**
 * @author YBF
 * @date 2016-9-21
 *       <p>
 *       用户管理
 *       </p>
 */
@Entity
@Table(name = "SYS_USER")
public class SysUser extends AbstractRowVersionModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_USER")
	private Integer id;
	
	@Column(name = "PASSWORD",length = 64,nullable = false)
	private String passWord;
	
	@Column(name = "USER_NAME",length = 64,nullable = false)
	private String userName;  //用户名
	
	@Column(name = "REAL_NAME",length = 64)
	private String realName;  //真实姓名

	@Column(name = "PHONE_NUM", length = 32)
	private String phoneNum; // 手机号

	@Column(name = "REL_EMAIL", length = 64)
	private String relMail; // 关联邮箱
	
	@Column(name = "CREATE_DATE")
	private Date createDate; // 创建时间

	@Column(name = "UPDATE_DATE")
	private Date updateDate; // 更新时间
	
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId; // 部门

	@Transient
	private Date createStartDate;
	@Transient
	private Date createEndDate;
	@Transient
    private Date updateStartDate;
	@Transient
	private Date updateEndDate;
	@Transient
	private String roles;
	@Transient
	private String roleIds;
	

	public Date getCreateStartDate() {
		return createStartDate;
	}

	public void setCreateStartDate(Date createStartDate) {
		this.createStartDate = createStartDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	public Date getUpdateStartDate() {
		return updateStartDate;
	}

	public void setUpdateStartDate(Date updateStartDate) {
		this.updateStartDate = updateStartDate;
	}

	public Date getUpdateEndDate() {
		return updateEndDate;
	}

	public void setUpdateEndDate(Date updateEndDate) {
		this.updateEndDate = updateEndDate;
	}

	public String getCreateDateStr() {
		return MyDateUtils.getStringByDateTime ( createDate ) ;
	}
	public String getUpdateDateStr() {
		return MyDateUtils.getStringByDateTime ( updateDate ) ;
	}
	
	public String getCreateStartDateStr() {
		return MyDateUtils.getStringByDateTime ( createStartDate ) ;
	}

	public String getCreateEndDateStr() {
		return MyDateUtils.getStringByDateTime ( createEndDate ) ;
	}

	public String getUpdateStartDateStr() {
		return MyDateUtils.getStringByDateTime ( updateStartDate ) ;
	}

	public String getUpdateEndDateStr() {
		return MyDateUtils.getStringByDateTime ( updateStartDate ) ;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getRelMail() {
		return relMail;
	}

	public void setRelMail(String relMail) {
		this.relMail = relMail;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
}

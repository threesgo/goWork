package com.yunwang.model.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author YBF
 * @date 2017-10-17
 * <p>工人管理</p>
 */
@Entity
@Table(name = "SYS_WORKER")
public class SysWorker extends AbstractRowVersionModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_WORKER")
	private Integer id;
	
	@Column(name = "CODE",nullable = false)
	private Integer code;
	
	@Column(name = "NAME",length = 64,nullable = false)
	private String name;
	
	@Column(name = "SEX")
	private Integer sex;
	
	@Column(name = "PHONENUM",length = 24)
	private String phoneNum;
	
	@Column(name = "TELNUM",length = 24)
	private String telNum;
	
	@Column(name = "ADDRESS",length = 2048)
	private String address;
	
	@Column(name = "AGE")
	private Integer age;
	
	@Column(name = "BIRTHDAY")
	private String birthday;
	
	@Column(name = "WORK_TYPE",nullable = false)
	private Integer workType;
	
	@Column(name = "WORK_AGE")
	private Integer workAge;
	
	@Column(name = "WAGES")
	private BigDecimal wages;
	
	@Column(name = "EDUCATION")
	private Integer education;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "COMPANY",length = 256)
	private String company;

	public SysWorker(){
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	public Integer getWorkAge() {
		return workAge;
	}

	public void setWorkAge(Integer workAge) {
		this.workAge = workAge;
	}

	public BigDecimal getWages() {
		return wages;
	}

	public void setWages(BigDecimal wages) {
		this.wages = wages;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}

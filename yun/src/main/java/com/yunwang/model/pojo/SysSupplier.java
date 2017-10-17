package com.yunwang.model.pojo;

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
 * <p>供应商管理</p>
 */
@Entity
@Table(name = "SYS_SUPPLIER")
public class SysSupplier extends AbstractRowVersionModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_WORKER")
	private Integer id;
	
	@Column(name = "NAME",length = 1024,nullable = false)
	private String name;
	
	@Column(name = "CONTACT",length = 128,nullable = false)
	private String contact;  //联系人
	
	@Column(name = "PHONENUM",length = 24,nullable = false)
	private String phoneNum;  //手机号码
	
	@Column(name = "TELNUM",length = 24)
	private String telNum;  //电话号码
	
	@Column(name = "ADDRESS",length = 2048)
	private String address;  //地址
	
	@Column(name = "WORK_TYPE")
	private Integer workType;  //工种
	
	public SysSupplier(){
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}
}

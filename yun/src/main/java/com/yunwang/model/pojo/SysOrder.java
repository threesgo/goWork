package com.yunwang.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_ORDER")
public class SysOrder extends AbstractRowVersionModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ORDER")
	private Integer id;
	
//	订单ID	ID	ID		NUMBER(10)
//	订单号	CODE	CODE		VARCHAR2(64)
//	订单备注	INFO	INFO		VARCHAR2(2048)
//	下订单时间	ORDER_TIME	ORDER_TIME		dateTime
//	订单联系人	CONTACT	CONTACT		VARCHAR2(128)
//	订单地址	ADDRESS	ADDRESS		VARCHAR2(2048)
//	工期预计开始时间	START_TIME	START_TIME		dateTime
//	工期预计结束时间	END_TIME	END_TIME		dateTime
//	总面积	TOTAL_AREA	TOTAL_AREA		NUMBER(9,2)
//	预计总金额	TOTAL_AMOUNT	TOTAL_AMOUNT		NUMBER(9,2)
//	装修类型	ORDER_TYPE	ORDER_TYPE		NUMBER(10)

	@Column(name = "CODE",length = 64,nullable = false)
	private String code;
	
	@Column(name = "CODE",length = 64,nullable = false)
	private String name;
	
	@Column(name = "INFO",length = 2048)
	private String info;
	
	@Column(name = "ORDER_DATE")
	private Date orderDate;  //只存日期，tree显示根据日期分组
	
	@Column(name = "CONTACT",nullable = false)
	private String contact;
	
	@Column(name = "ADDRESS",nullable = false)
	private String address;
	
	@Column(name = "START_TIME")
	private Date startTime;
	
	@Column(name = "END_TIME")
	private Date endTime;  //报警提示
	
	@Column(name = "TOTAL_AREA",nullable = false)
	private BigDecimal totalArea;
	
	@Column(name = "TOTAL_AMOUNT",nullable = false)
	private BigDecimal totalAmount;
	
	@Column(name = "ORDER_TYPE",nullable = false)
	private Integer orderType; //装修类型
	
	@Column(name = "STATUS",columnDefinition = "number default 1")
	private Integer status;  //0，删除 1，未开始 2、进行中 3，已完成  (手动或者根据时间进行自动管控)
	
	public SysOrder(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

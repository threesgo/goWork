package com.yunwang.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yunwang.util.date.MyDateUtils;

@Entity
@Table(name = "SYS_ORDER_FLOW")
public class SysOrderFlow extends AbstractRowVersionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ORDER_FLOW")
	private Integer id;
	
//	ID	ID	ID		NUMBER(10)
//	订单ID	ORDER_ID	ORDER_ID		NUMBER(10)
//	流程ID	FLOW_TYPE	FLOW_TYPE		NUMBER(10)
//	工期预计开始时间	START_TIME	START_TIME		dateTime
//	工期预计结束时间	END_TIME	END_TIME		dateTime
	
	@Column(name = "ORDER_ID",nullable = false)
	private Integer orderId;
	
	@Column(name = "NAME",nullable = false,length=256)
	private String name;
	
	@Column(name = "FLOW_TYPE",nullable = false)
	private Integer flowType;
	
	@Column(name = "STATUS",columnDefinition = "number default 1")
	private Integer status;  //标注每个流程的完成状态，已经流转
	
	@Column(name = "START_TIME")
	private Date startTime;  //计划开始时间    PALNT
	
	@Column(name = "END_TIME")
	private Date endTime;  //报警提示  //计划结束时间  PALNT
	
	@Column(name = "ACTUAL_START_TIME")
	private Date actualStartTime;  //实际开始时间
	
	@Column(name = "ACTUAL_END_TIME")
	private Date actualEndTime;  //实际结束时间
	
	@Column(name = "INFO",length=2048)
	private String info;
	
	@Column(name="ORDER_NO", nullable = false)
	private Integer orderNo;//顺序号
	
	
	public SysOrderFlow(){
		
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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getFlowType() {
		return flowType;
	}

	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getStartTimeStr(){
		if(null==startTime){
			return "";
		}
		return MyDateUtils.getStringByDate(startTime);
	}
	
	public String getEndTimeStr(){
		if(null==endTime){
			return "";
		}
		return MyDateUtils.getStringByDate(endTime);
	}

	public Date getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public Date getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}
	
	public String getActualStartTimeStr(){
		if(null==actualStartTime){
			return "";
		}
		return MyDateUtils.getStringByDate(actualStartTime);
	}
	
	public String getActualEndTimeStr(){
		if(null==actualEndTime){
			return "";
		}
		return MyDateUtils.getStringByDate(actualEndTime);
	}
}

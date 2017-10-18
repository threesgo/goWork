package com.yunwang.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_ORDER_LAYOUT")
public class SysOrderFlow extends AbstractRowVersionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ORDER_LAYOUT")
	private Integer id;
	
//	ID	ID	ID		NUMBER(10)
//	订单ID	ORDER_ID	ORDER_ID		NUMBER(10)
//	流程ID	FLOW_TYPE	FLOW_TYPE		NUMBER(10)
//	工期预计开始时间	START_TIME	START_TIME		dateTime
//	工期预计结束时间	END_TIME	END_TIME		dateTime
	
	@Column(name = "ORDER_ID",nullable = false)
	private Integer orderId;
	
	@Column(name = "FLOW_TYPE",nullable = false)
	private Integer flowType;
	
	@Column(name = "STATUS",columnDefinition = "number default 1")
	private Integer status;  //标注每个流程的完成状态，已经流转
	
	public SysOrderFlow(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}

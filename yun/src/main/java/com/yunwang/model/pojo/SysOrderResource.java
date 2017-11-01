package com.yunwang.model.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_ORDER_RESOURCE")
public class SysOrderResource extends AbstractRowVersionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ORDER_RESOURCE")
	private Integer id;
	
//	ID	ID	ID		NUMBER(10)
//	流程ID	ORDER_FLOW_ID	ORDER_FLOW_ID		NUMBER(10)
//	产品D	RESOURCE_ID	RESOURCE_ID		NUMBER(10)
//	订单ID	ORDER_ID	ORDER_ID		NUMBER(10)


	@Column(name = "ORDER_FLOW_ID",nullable = false)
	private Integer orderFlowId;
	
	@Column(name = "RESOURCE_ID",nullable = false)
	private Integer resourceId;
	
	@Column(name = "ORDER_ID",nullable = false)
	private Integer orderId;
	
	@Column(name = "QUANTITY",precision=9, scale=2,nullable = false)
	private BigDecimal quantity;
	
	public SysOrderResource(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderFlowId() {
		return orderFlowId;
	}

	public void setOrderFlowId(Integer orderFlowId) {
		this.orderFlowId = orderFlowId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
}

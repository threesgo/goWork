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
 * @date 2017-10-20
 * <p>工人归档列表</p>
 */
@Entity
@Table(name = "SYS_FILE_ORDER_WORKER")
public class SysFileOrderWorker extends AbstractRowVersionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_FILE_ORDER_WORKER")
	private Integer id;
	
//	ID	ID	ID		NUMBER(10)		
//	流程ID	ORDER_FLOW_ID	ORDER_FLOW_ID		NUMBER(10)		*
//	工人ID	WORKER_ID	WORKER_ID		NUMBER(10)		*
//	订单ID	ORDER_ID	ORDER_ID		NUMBER(10)		*

	@Column(name = "ORDER_FLOW_ID",nullable = false)
	private Integer orderFlowId;
	
	@Column(name = "WORKER_ID",nullable = false)
	private Integer workerId;
	
	@Column(name = "WORKER_NAME",nullable = false)
	private Integer workerName;
	
	@Column(name = "WORKER_WAGES",nullable = false)
	private Integer workerWages;
	
	@Column(name = "ORDER_ID",nullable = false)
	private Integer orderId;
	
	@Column(name = "FLOW_TIME_ID")
	private Integer flowTimeId;  //一期先不用，直接放到流程上面
	
	@Column(name = "WORK_TIME",precision=9, scale=2,nullable = false)
	private BigDecimal workTime;
	
	
	public SysFileOrderWorker(){
		
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

	public Integer getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getFlowTimeId() {
		return flowTimeId;
	}

	public void setFlowTimeId(Integer flowTimeId) {
		this.flowTimeId = flowTimeId;
	}

	public BigDecimal getWorkTime() {
		return workTime;
	}

	public void setWorkTime(BigDecimal workTime) {
		this.workTime = workTime;
	}

	public Integer getWorkerName() {
		return workerName;
	}

	public void setWorkerName(Integer workerName) {
		this.workerName = workerName;
	}

	public Integer getWorkerWages() {
		return workerWages;
	}

	public void setWorkerWages(Integer workerWages) {
		this.workerWages = workerWages;
	}
}

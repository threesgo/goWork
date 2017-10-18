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
 * @date 2017-10-18
 * <p>流程时间段管理</p>
 */
@Entity
@Table(name = "SYS_ORDER_FLOW_TIME")
public class SysOrderFlowTime  extends AbstractRowVersionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ORDER_LAYOUT")
	private Integer id;
	
	@Column(name = "ORDER_FLOW_ID",nullable = false)
	private Integer orderFlowId;
	
	@Column(name = "START_TIME")
	private Date startTime;
	
	@Column(name = "END_TIME")
	private Date endTime;  //报警提示
	
	@Column(name = "STATUS",columnDefinition = "number default 1")
	private Integer status;
	
	public SysOrderFlowTime(){
		
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}  
}

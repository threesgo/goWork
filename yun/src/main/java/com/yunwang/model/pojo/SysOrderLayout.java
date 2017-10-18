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
public class SysOrderLayout extends AbstractRowVersionModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ORDER_LAYOUT")
	private Integer id;
	
//	ID	ID	ID		NUMBER(10)			*		
//	订单ID	ORDER_ID	ORDER_ID		NUMBER(10)		*			SYS_ORDER
//	室类型	ROOT_TYPE	ROOT_TYPE		NUMBER(10)		*			SYS_DATA_DICTIONARY
//	值（几室厅）	VALUE	VALUE		NUMBER(10)		*	
	
	@Column(name = "ORDER_ID",nullable = false)
	private Integer orderId;
	
	@Column(name = "ROOT_TYPE",nullable = false)
	private Integer roomType;
	
	@Column(name = "VALUE",nullable = false)
	private Integer value;
	
	public SysOrderLayout(){
		
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

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}

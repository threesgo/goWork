package com.yunwang.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author KXL
 * @date 2017-10-22
 * <p>系统部门（工人和系统用户的区分）</p>
 */
@Entity
@Table(name = "SYS_DEPARTMENT")
public class SysDepartMent extends AbstractRowVersionModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_DEPARTMENT")
	private Integer id;

	//部门名称
	@Column(name = "NAME", nullable = false, length = 64)
	private String name;
	
	//部门代号
	@Column(name = "CODE", length = 64,nullable = false)
	private String code;
	
	//父级部门id
	@Column(name = "PARENT_ID",nullable = false)
	private Integer parentId;
	
	//顺序号
	@Column(name = "ORDER_NO",nullable = false)
	private Integer orderNo;
	
	//组合顺序号
	@Column(name = "STR_ORDER_NO",length = 128,nullable = false)
	private String strOrderNo;
	
	public SysDepartMent() {
		super();
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getStrOrderNo() {
		return strOrderNo;
	}

	public void setStrOrderNo(String strOrderNo) {
		this.strOrderNo = strOrderNo;
	}

}

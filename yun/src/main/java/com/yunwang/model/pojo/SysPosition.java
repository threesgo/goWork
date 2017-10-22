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
@Table(name = "SYS_POSITION")
public class SysPosition extends AbstractRowVersionModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_POSITION")
	private Integer id;

	//职位名称
	@Column(name = "NAME", nullable = false, length = 64)
	private String name;
	
	//职位代号
	@Column(name = "CODE", length = 64,nullable = false)
	private String code;
	
	//职位类别
	@Column(name = "POSITION_TYPE",nullable = false)
	private Integer positionType;
	
	//部门id
	@Column(name = "DEPARTMENT_ID",nullable = false)
	private Integer departMentId;
	
	public SysPosition() {
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

	public Integer getPositionType() {
		return positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}

	public Integer getDepartMentId() {
		return departMentId;
	}

	public void setDepartMentId(Integer departMentId) {
		this.departMentId = departMentId;
	}
	
}

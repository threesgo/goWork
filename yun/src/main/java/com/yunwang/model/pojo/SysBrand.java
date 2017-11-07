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
 * @date 2017-11-7
 * <p>品牌管理</p>
 */
@Entity
@Table(name = "SYS_SUPPLIER")
public class SysBrand extends AbstractRowVersionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_SUPPLIER")
	private Integer id;
	
	@Column(name = "CODE",nullable = false)
	private Integer code;
	
	@Column(name = "NAME",length = 256,nullable = false)
	private String name;
	
	@Column(name = "INFO",length = 4096)
	private String info;
	
	@Column(name = "STATUS",columnDefinition = "number default 1")
	private Integer status; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

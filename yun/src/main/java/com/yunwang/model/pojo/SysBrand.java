package com.yunwang.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author YBF
 * @date 2017-11-7
 * <p>品牌管理</p>
 */
@Entity
@Table(name = "SYS_BRAND")
public class SysBrand extends AbstractRowVersionModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_BRAND")
	private Integer id;
	
	@Column(name = "CODE",nullable = false)
	private Integer code;
	
	@Column(name = "NAME",length = 256,nullable = false)
	private String name;
	
	@Column(name = "INFO",length = 2048)
	private String info;
	
	@Column(name = "STATUS",columnDefinition = "number default 1")
	private Integer status; 
	
	@Transient
	private Integer brandCatalogId;
	
	public SysBrand(){
		
	}

	public SysBrand(Integer id, Integer code, String name, String info,
			Integer status, Integer brandCatalogId) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.info = info;
		this.status = status;
		this.brandCatalogId = brandCatalogId;
	}

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

	public Integer getBrandCatalogId() {
		return brandCatalogId;
	}

	public void setBrandCatalogId(Integer brandCatalogId) {
		this.brandCatalogId = brandCatalogId;
	}
}

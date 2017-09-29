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
 * @date 2017-9-27
 * <p>资源类别定义表</p>
 */
@Entity
@Table(name = "SYS_RSRC_CATALOG")
public class SysRsRcCatalog extends AbstractRowVersionModel{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_BOP_TM_RSRC_CATALOG")
	private Integer id;
	
	@Column(name="ORDER_NO", nullable = false)
	private Integer orderNo;//顺序号
	
	@Column(name="PARENT_ID", nullable = false)
	private Integer parentId;//父级Id
	
	@Column(name="CATALOG_CODE", nullable = false,length=32)
	private String catalogCode;//代号
	
	@Column(name="CATALOG_NAME", nullable = false,length=128)
	private String catalogName;//名称
		
	@Column(name="CATALOG_STUTAS",precision=1,nullable = false,columnDefinition = "number default 1")
	private Integer catalogStatus;//状态
	
	@Column(name="CATALOG_TYPE",precision=2, nullable = false)
	private Integer catalogType;//资源类别
	
	public SysRsRcCatalog(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public Integer getCatalogStatus() {
		return catalogStatus;
	}

	public void setCatalogStatus(Integer catalogStatus) {
		this.catalogStatus = catalogStatus;
	}

	public Integer getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(Integer catalogType) {
		this.catalogType = catalogType;
	}
}

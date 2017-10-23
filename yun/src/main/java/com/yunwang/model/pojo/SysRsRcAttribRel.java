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
 * <p>资源属性中间表-属性值  发布表</p>
 */
@Entity
@Table(name = "SYS_RSRC_ATTRIB_REL")
public class SysRsRcAttribRel extends AbstractRowVersionModel{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RSRC_ATTRIB_REL")
	private Integer id;  //资源属性ID
	
	@Column(name="RSRC_REL_ID", nullable = false)
	private Integer rsrcRelId;//资源ID
	
	@Column(name="RSRC_ATTRIB_CATALOG_REL_ID", nullable = false)
	private Integer rsrcAttribCatalogId;//属性ID
	
	@Column(name="RSRC_ATTRIB_VALUE", length=256)
	private String rsrcAttribValue;//属性值
	
	@Column(name="RSRC_CATALOG_ID", nullable = false)
	private Integer rsrcCatalogId;// 资源类型Id  //便于通过类型删除操作

	public SysRsRcAttribRel(){
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRsrcAttribValue() {
		return rsrcAttribValue;
	}

	public void setRsrcAttribValue(String rsrcAttribValue) {
		this.rsrcAttribValue = rsrcAttribValue;
	}

	public Integer getRsrcRelId() {
		return rsrcRelId;
	}

	public void setRsrcRelId(Integer rsrcRelId) {
		this.rsrcRelId = rsrcRelId;
	}

	public Integer getRsrcAttribCatalogId() {
		return rsrcAttribCatalogId;
	}

	public void setRsrcAttribCatalogId(Integer rsrcAttribCatalogId) {
		this.rsrcAttribCatalogId = rsrcAttribCatalogId;
	}

	public Integer getRsrcCatalogId() {
		return rsrcCatalogId;
	}

	public void setRsrcCatalogId(Integer rsrcCatalogId) {
		this.rsrcCatalogId = rsrcCatalogId;
	}
}

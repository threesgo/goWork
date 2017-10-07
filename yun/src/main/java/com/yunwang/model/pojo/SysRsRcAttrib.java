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
 * <p>资源属性中间表-属性值</p>
 */
@Entity
@Table(name = "SYS_RSRC_ATTRIB")
public class SysRsRcAttrib extends AbstractRowVersionModel{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RSRC_ATTRIB")
	private Integer id;  //资源属性ID
	
	@Column(name="RSRC_ID", nullable = false)
	private Integer rsrcId;//资源ID
	
	@Column(name="RSRC_ATTRIB_CATALOG_ID", nullable = false)
	private Integer rsraAttribCatalogId;//属性ID
	
	@Column(name="RSRC_ATTRIB_VALUE", length=256)
	private String rsrcAttribValue;//属性值
	
	@Column(name="RSRC_CATALOG_ID", nullable = false)
	private Integer rsrcCatalogId;// 资源类型Id  //便于通过类型删除操作

	public SysRsRcAttrib(){
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRsrcId() {
		return rsrcId;
	}

	public void setRsrcId(Integer rsrcId) {
		this.rsrcId = rsrcId;
	}

	public Integer getRsraAttribCatalogId() {
		return rsraAttribCatalogId;
	}

	public void setRsraAttribCatalogId(Integer rsraAttribCatalogId) {
		this.rsraAttribCatalogId = rsraAttribCatalogId;
	}

	public String getRsrcAttribValue() {
		return rsrcAttribValue;
	}

	public void setRsrcAttribValue(String rsrcAttribValue) {
		this.rsrcAttribValue = rsrcAttribValue;
	}

	public Integer getRsrcCatalogId() {
		return rsrcCatalogId;
	}

	public void setRsrcCatalogId(Integer rsrcCatalogId) {
		this.rsrcCatalogId = rsrcCatalogId;
	}
}

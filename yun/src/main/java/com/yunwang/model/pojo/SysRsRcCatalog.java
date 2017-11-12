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
 * @date 2017-9-27
 * <p>资源类别定义表</p>
 */
@Entity
@Table(name = "SYS_RSRC_CATALOG")
public class SysRsRcCatalog extends AbstractRowVersionModel{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RSRC_CATALOG")
	private Integer id;
	
	@Column(name="ORDER_NO", nullable = false)
	private Integer orderNo;//顺序号
	
	@Column(name="PARENT_ID", nullable = false)
	private Integer parentId;//父级Id
	
	@Column(name="CATALOG_CODE", nullable = false,length=128)
	private String catalogCode;//代号  //组合值
	
	@Column(name="CATALOG_NAME", nullable = false,length=128)
	private String catalogName;//名称
		
	@Column(name="CATALOG_STUTAS",precision=1,nullable = false,columnDefinition = "number default 1")
	private Integer catalogStatus;//状态     0，删除tree不显示，发布区显示(发布后物理删除,发布区也删除)   1、新增  2、编辑   3、发布      (第一次创建：新增 ;新增状态下编辑：新增 ;发布和删除状态下编辑：编辑 )
	
	@Column(name="CATALOG_TYPE",nullable = false)
	private Integer catalogType;//资源类别
	
	@Column(name="WORK_TYPE")
	private Integer workType;  //做工种类  (其他类默认为做工项)  1、材料项 2、做工项 是
	
	@Transient
	private String state = "closed";
	
	@Transient
	private String catalogTypeName;
	
	@Transient
	private Integer parentWorkType;
	
	@Transient
	private String combineName;
	
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCatalogTypeName() {
		return catalogTypeName;
	}

	public void setCatalogTypeName(String catalogTypeName) {
		this.catalogTypeName = catalogTypeName;
	}

	public String getCombineName() {
		return combineName;
	}

	public void setCombineName(String combineName) {
		this.combineName = combineName;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	public Integer getParentWorkType() {
		return parentWorkType;
	}

	public void setParentWorkType(Integer parentWorkType) {
		this.parentWorkType = parentWorkType;
	}

	public Object getWorkTypeStr() {
		if(null!=workType){
			if(workType == 1){
				return "材料项";
			}else if(workType == 2){
				return "做工项";
			}
		}
		return "";
	}
}

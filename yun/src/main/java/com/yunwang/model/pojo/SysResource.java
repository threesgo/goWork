package com.yunwang.model.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * <p>资源（产品表）</p>
 */
@Entity
@Table(name = "SYS_RESOURCE")
public class SysResource extends AbstractRowVersionModel{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RESOURCE")
	private Integer id;
	
	@Column(name="RSRC_CODE", length=128, nullable = false)
	private String rsrcCode; //资源型号
	
	@Column(name="RSRC_NAME", length=128)
	private String rsrcName;// 资源名称
	
	@Column(name="ABBREVIA_NAME", length=128)
	private String abbreviaName;
	
	@Column(name="ORDER_NO", nullable = false)
	private Integer orderNo;//顺序号
		
	@Column(name="RSRC_CATALOG_ID", nullable = false)
	private Integer rsrcCatalogId;// 资源类型Id
	
//	@Column(name="WORK_TYPE", nullable = false)
//	private Integer workType;  //工种
	
	@Column(name="PURCHASE_PRICE",precision=9, scale=2,nullable = false)
	private BigDecimal purchasePrice; //采购价格、
	
	@Column(name="SALE_PRICE",precision=9, scale=2, nullable = false)
	private BigDecimal salePrice;  //销售价格
	
	@Column(name="RSRC_STUTAS",nullable = false,columnDefinition = "number default 1")
	private Integer rsrcStatus = 1;//状态
	
	@Column(name = "CREATE_DATE")
	private Date createDate; // 创建时间

	@Column(name = "UPDATE_DATE")
	private Date updateDate; // 更新时间
	
	@Column(name = "BRAND",length=128)
	private String brand;  //品牌
	
//	@Column(name = "SUPPLIER_ID")
//	private String supplierId;  //供应商名称 //后期改成id
	
	@Column(name = "SUPPLIER_NAME",length=1024)
	private String supplierName;  //供应商名称
	
	@Column(name = "SUPPLIER",length=1024)
	private String supplier;  //供应商联系人
	
	@Column(name = "SUPPLIER_ADDRESS",length=1024)
	private String supplierAddress;  //供应商地址
	
	@Column(name = "SUPPLIER_PHONE",length=256)
	private String supplierPhone;  //供应商电话
	
	@Transient
	private List<SysRsRcAttrib> sysRcRsrcAttribList = new ArrayList<SysRsRcAttrib>();
	
	
	public SysResource(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRsrcName() {
		return rsrcName;
	}

	public void setRsrcName(String rsrcName) {
		this.rsrcName = rsrcName;
	}

	public String getRsrcCode() {
		return rsrcCode;
	}

	public void setRsrcCode(String rsrcCode) {
		this.rsrcCode = rsrcCode;
	}

	public Integer getRsrcCatalogId() {
		return rsrcCatalogId;
	}

	public void setRsrcCatalogId(Integer rsrcCatalogId) {
		this.rsrcCatalogId = rsrcCatalogId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getRsrcStatus() {
		return rsrcStatus;
	}

	public void setRsrcStatus(Integer rsrcStatus) {
		this.rsrcStatus = rsrcStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAbbreviaName() {
		return abbreviaName;
	}

	public void setAbbreviaName(String abbreviaName) {
		this.abbreviaName = abbreviaName;
	}

	public List<SysRsRcAttrib> getSysRcRsrcAttribList() {
		return sysRcRsrcAttribList;
	}

	public void setSysRcRsrcAttribList(List<SysRsRcAttrib> sysRcRsrcAttribList) {
		this.sysRcRsrcAttribList = sysRcRsrcAttribList;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
}

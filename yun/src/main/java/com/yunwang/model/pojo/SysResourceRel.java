package com.yunwang.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yunwang.util.date.MyDateUtils;


/**
 * @author YBF
 * @date 2017-9-27
 * <p>资源（产品表）</p>
 */
@Entity
@Table(name = "SYS_RESOURCE_REL")
public class SysResourceRel extends AbstractRowVersionModel{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RESOURCE_REL")
	private Integer id;
	
	@Column(name="RESOURCE_ID")
	private Integer resourceId;
	
	@Column(name="KEY_WORD",length=2048)
	private String keyWord;  //自动生成，可修改（产品显示字段）
	
	@Column(name="RSRC_CODE", length=128)
	private String rsrcCode; //资源型号
	
	@Column(name="RSRC_NAME", length=128)
	private String rsrcName;// 资源名称
	
	@Column(name="ABBREVIA_NAME", length=128)
	private String abbreviaName;
	
	@Column(name="ORDER_NO", nullable = false)
	private Integer orderNo;//顺序号
		
	@Column(name="RSRC_CATALOG_ID", nullable = false)
	private Integer rsrcCatalogId;// 资源类型Id  //发布资源类型表中的ID
	
	@Column(name="SALE_PRICE",precision=9, scale=2, nullable = false)
	private BigDecimal salePrice;  //销售价格
	
	@Column(name = "BRAND_ID")
	private Integer brandId;  //品牌
	
	@Column(name = "SUPPLIER_ID")
	private Integer supplierId;  //供应商名称 //后期改成id
	
	@Column(name = "RELEASE_DATE")
	private Date releaseDate; // 创建时间
	
	@Column(name="RSRC_STUTAS",nullable = false)
	private Integer rsrcStatus;//状态  0 失效  1，正常
	
	@Transient
	private String brand;  //供应商名称
	
	@Transient
	private String supplierName;  //供应商名称
	
	@Transient
	private String supplier;  //供应商联系人
	
	@Transient
	private String supplierPhone;  //供应商联系人手机
	
	@Transient
	private String supplierTel;  //供应商
	
	@Transient
	private String supplierAddress;  //供应商地址
	
	@Transient
	private Integer catalogType;
	
	@Transient
	private Integer workType;
	
	@Transient
	private BigDecimal quantity = BigDecimal.ONE;
	
	@Transient
	private Integer orderResourceId;
	
	
	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public Integer getOrderResourceId() {
		return orderResourceId;
	}

	public void setOrderResourceId(Integer orderResourceId) {
		this.orderResourceId = orderResourceId;
	}

	public SysResourceRel(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
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

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAbbreviaName() {
		return abbreviaName;
	}

	public void setAbbreviaName(String abbreviaName) {
		this.abbreviaName = abbreviaName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getRsrcStatus() {
		return rsrcStatus;
	}

	public void setRsrcStatus(Integer rsrcStatus) {
		this.rsrcStatus = rsrcStatus;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public String getSupplierTel() {
		return supplierTel;
	}

	public void setSupplierTel(String supplierTel) {
		this.supplierTel = supplierTel;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}
	
	public String getReleaseDateStr(){
		return MyDateUtils.getStringByDateTime (releaseDate) ;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(Integer catalogType) {
		this.catalogType = catalogType;
	}
}

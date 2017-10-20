package com.yunwang.model.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author YBF
 * @date 2017-10-20
 * <p>套餐表（分类加验证）</p>
 */
@Entity
@Table(name = "SYS_RSRC_PACKAGE")
public class SysRsRcPackage extends AbstractRowVersionModel{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RSRC_PACKAGE")
	private Integer id;
	
	@Column(name="NAME", nullable = false,length=128)
	private String name;
	
	@Column(name="CODE", nullable = false,length=32)
	private String code;
	
	@Column(name="ORDER_NO", nullable = false)
	private Integer orderNo;//顺序号
	
	@Column(name="PACKAGE_TYPE")
	private Integer packegeType;
	
	@Column(name="MIN_PRICE")
	private BigDecimal minPrice;//最小价格
	
	@Column(name="MAX_PRICE")
	private BigDecimal maxPrice;//最大价格
	
	public SysRsRcPackage(){
		
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

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getPackegeType() {
		return packegeType;
	}

	public void setPackegeType(Integer packegeType) {
		this.packegeType = packegeType;
	}
}

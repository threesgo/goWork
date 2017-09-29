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
 * @date 2017-9-27
 * <p>基础数据类型表(单位)</p>
 */
@Entity
@Table(name = "SYS_RSRC_BASEDATA")
public class SysRsRcBaseData extends AbstractRowVersionModel{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RSRC_BASEDATA") 
	private Integer id;    //基础数据类型ID	number(10)
	
	@Column(name="DATA_TYPE",nullable = false,precision=1)
	private BigDecimal dataType;//分类
	
	@Column(name="DISPLAY_NAME",nullable = false,length=128)
	private String displayName;//显示名称
	
	@Column(name="REAL_NAME",nullable = false,length=64)
	private String realName;   //类型名称（英文名称、dom的显示属性）
	
	@Column(name="DATA_GROUP",length=64)
	private String group;//分组
	
	public SysRsRcBaseData() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getDataType() {
		return dataType;
	}

	public void setDataType(BigDecimal dataType) {
		this.dataType = dataType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
}

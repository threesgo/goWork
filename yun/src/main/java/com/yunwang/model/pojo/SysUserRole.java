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
 * <p>用户角色中间表</p>
 */
@Entity
@Table(name = "SYS_USER_ROLE")
public class SysUserRole extends AbstractRowVersionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_USER_ROLE")
	private Integer id;
	
	//用户ID
	@Column(name = "USER_ID",nullable = false)
	private Integer userId;
	
	//角色ID
	@Column(name = "ROLE_ID",nullable = false)
	private Integer roleId;
	
	//是否默认选择
	@Column(name = "IS_DEFAULT")
	private BigDecimal isDefault;  //1 、选择
	
	
	public SysUserRole(){
		super();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(BigDecimal isDefault) {
		this.isDefault = isDefault;
	}
}

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
 * <p>角色管理</p>
 */
@Entity
@Table(name = "SYS_ROLE")
public class SysRole extends AbstractRowVersionModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ROLE")
	private Integer id;

	//角色名称
	@Column(name = "NAME", nullable = false, length = 64)
	private String name;
	
	//图标
	@Column(name = "ICONCLS", length = 64)
	private String iconCls;
	
	
	public SysRole() {
		super();
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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}

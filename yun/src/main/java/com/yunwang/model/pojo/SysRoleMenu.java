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
 * <p>角色菜单中间表</p>
 */
@Entity
@Table(name = "SYS_ROLE_MENU")
public class SysRoleMenu extends AbstractRowVersionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_ROLE_MENU")
	private Integer id;
	
	//角色ID
	@Column(name = "ROLE_ID",nullable = false)
	private Integer roleId;
	
	//菜单ID
	@Column(name = "MENU_ID",nullable = false)
	private Integer menuId;

	
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

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}

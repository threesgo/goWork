package com.yunwang.model.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

@Entity
@Table(name = "SYS_MENU")
public class SysMenu extends AbstractRowVersionModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_MENU")
	private Integer id;

	//菜单名称
	@Column(name = "NAME", nullable = false, length = 64)
	private String name;
	
	//请求地址
	@Column(name = "URL", length = 128)
	private String url;
	
	//图标
	@Column(name = "ICONCLS", length = 64)
	private String iconCls;
	
	//父级权限ID
	@Column(name = "PARENT_ID", nullable = false)
	private Integer parentId;

	//排序
	@Column(name="ORDER_NO", nullable = false)
	private Integer orderNo;//顺序号
	
	//显示类型
	@Column(name = "VIEW_TYPE")
	private Integer viewType;  //1、菜单，2、功能
	
	//权限类型
	@Column(name = "AUTH_TYPE")
	private Integer authType;  //1、只读,2、读写  (根据功能类型判断。比如：查询（只读），新建、修改、删除（读写）)
	
	@Transient
	private List<SysMenu> children = new ArrayList<SysMenu>();
	
	@Transient
	private JSONObject attributes;
	
	public JSONObject getAttributes() {
		return attributes;
	}

	public void setAttributes(JSONObject attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return "open";
	}
	
	public String getText() {
		return name;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getViewType() {
		return viewType;
	}

	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}
}

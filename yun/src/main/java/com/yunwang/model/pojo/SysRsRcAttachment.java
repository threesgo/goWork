package com.yunwang.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_RSRC_ATTACHMENT")
public class SysRsRcAttachment extends AbstractRowVersionModel{
	
//	附件地址	ADDRESS	ADDRESS	资源附件视频或图片	varchar2(1024)
//	顺序号	ORDER_NO	ORDER_NO	顺序号	number(10)
//	资源ID	RSRC_ID	RSRC_ID	资源ID	number(10)
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RSRC_ATTACHMENT")
	private Integer id;
	
	@Column(name = "ADDRESS", length = 1024)
	private String address;
	
	@Column(name = "RSRC_ID")
	private Integer rsrcId;
	
	
	public SysRsRcAttachment() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRsrcId() {
		return rsrcId;
	}

	public void setRsrcId(Integer rsrcId) {
		this.rsrcId = rsrcId;
	}
}

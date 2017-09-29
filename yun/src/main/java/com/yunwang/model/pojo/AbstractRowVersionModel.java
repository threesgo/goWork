package com.yunwang.model.pojo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractRowVersionModel{

	/*
	 * @date 2017-6-22
	 * @author YBF
	 * TODO
	 */
	@Version
    @Column(name="ROW_VERSION")
    protected Integer  rowVersion;  //版本


	public Integer getRowVersion() {
		return rowVersion;
	}


	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
	}
}

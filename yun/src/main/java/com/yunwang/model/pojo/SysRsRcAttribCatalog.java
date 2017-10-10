package com.yunwang.model.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yunwang.util.string.MyStringUtil;

/**
 * @author YBF
 * @date 2017-9-27
 * <p>资源类别的属性定义表</p>
 */
@Entity
@Table(name = "SYS_RSRC_ATTRIB_CATALOG")
public class SysRsRcAttribCatalog extends AbstractRowVersionModel{ 
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="SEQ_SYS_RSRC_ATTRIB_CATALOG")
	private Integer id;   //属性目录ID
	
	@Column(name="RSRC_CATALOG_ID", nullable = false)
	private Integer rsrcCatalogId;//类型ID   RSRC_CATALOG_ID	资源类型ID	number(10)

	@Column(name="RSRC_ATTRIB_CODE", nullable = false,length=32)
	private String rsrcAttribCode;//属性代号 RSRC_ATTRIB_CODE	属性代号	varchar2(32)		X
	
	@Column(name="RSRC_ATTRIB_NAME", nullable = false,length=128)
	private String rsrcAttribName;//属性名称 RSRC_ATTRIB_NAME	属性名称	varchar2(128)		X
	
	@Column(name="DATA_TYPE_ID", nullable = false)
	private Integer dataTypeId;//数据类型ID  DATA_TYPE_ID	数据类型ID	number(10)
	
	@Column(name="CTRL_TYPE_ID", nullable = false)
	private Integer controlTypeId;//控件类型 CTRL_TYPE_ID	控件类型ID	number(10)
	
	@Column(name="DATA_LENGTH")
	private Integer dataLength;//数据长度 DATA_LENGTH	数据长度	number(10)
	
	@Column(name="DATA_PRECISION")
	private Integer dataPrecision;//小数点位数  PRECISION	小数点位数	number(10)
	
	@Column(name="UNIT_ID")
	private Integer unitId;//单位   UNIT_ID	单位ID	number(10)
	
	@Column(name="SHOW_IN_LISTVIEW", nullable = false,columnDefinition = "number default 1")
	private Integer showInListView;//是否在列表中显示  SHOW_IN_LISTVIEW	列表显示	number(10)
	
	@Column(name="SHOW_IN_FINDER", nullable = false,columnDefinition = "number default 1")
	private Integer showInFinder;//是否在查找中出现  SHOW_IN_FINDER	在查找条件中出现	number(10)
	
	@Column(name="ORDER_NO", nullable = false)
	private Integer orderNo;//顺序号
	
	@Column(name="DEFAULT_VALUE", length = 1024)
	private String defaultValue;//默认值   DEFAULT_VALUE	默认值	varchar2(1024)	“1|2|3” 选择框需要输入
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRsrcAttribCode() {
		return rsrcAttribCode;
	}

	public void setRsrcAttribCode(String rsrcAttribCode) {
		this.rsrcAttribCode = rsrcAttribCode;
	}

	public String getRsrcAttribName() {
		return rsrcAttribName;
	}

	public void setRsrcAttribName(String rsrcAttribName) {
		this.rsrcAttribName = rsrcAttribName;
	}

	public Integer getControlTypeId() {
		return controlTypeId;
	}

	public void setControlTypeId(Integer controlTypeId) {
		this.controlTypeId = controlTypeId;
	}

	public Integer getDataTypeId() {
		return dataTypeId;
	}

	public void setDataTypeId(Integer dataTypeId) {
		this.dataTypeId = dataTypeId;
	}

	public Integer getDataLength() {
		if(null!=dataLength){
			return dataLength;
		}
		return 256;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getShowInListView() {
		return showInListView;
	}

	public void setShowInListView(Integer showInListView) {
		this.showInListView = showInListView;
	}

	public Integer getShowInFinder() {
		return showInFinder;
	}

	public void setShowInFinder(Integer showInFinder) {
		this.showInFinder = showInFinder;
	}

	public Integer getDataPrecision() {
		if(null!=dataPrecision){
			return dataPrecision;
		}
		return 0;
	}

	public void setDataPrecision(Integer dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
	
	public String getArrDefaultValues(){
		JSONArray arr = new JSONArray();
		if(MyStringUtil.isNotBlank(defaultValue)){
			String[] values = defaultValue.split("\\|");
			for(String value:values){
				JSONObject obj = new JSONObject();
				obj.put("id", value);
				obj.put("value", value);
				arr.add(obj);
			}
		}
		return arr.toString();
	}
	
	public BigDecimal getMax(){
		if(dataTypeId == 2 && controlTypeId == 104 ){
			Integer z = getDataLength() - getDataPrecision();
			Integer x = getDataPrecision();
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<z;i++){
				buf.append(9);
			}
			buf.append(".");
			for(int i=0;i<x;i++){
				buf.append(9);
			}
			return new BigDecimal(buf.toString());
		}
		return null;
	}
}

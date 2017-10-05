<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="saveOrUpdate_resource_attr" method="post" action="resourceTypeAction!saveOrUpdateAttr.act">
	<input type="hidden" name="sysRsRcCatalog.id" value="${sysRsRcCatalog.id}"/>  
    <div>   
        <label for="code">属性代号:</label>   
        <input class="easyui-validatebox" type="text" name="sysRsRcAttribCatalog.rsrcAttribCode" 
        style="width:200px;" data-options="required:true,validType:['length[1,10]','illegal']" value="${sysRsRcCatalog.catalogCode}"/>   
    </div>   
    <div>   
        <label for="name">属性名称:</label>   
        <input class="easyui-validatebox" type="text" name="sysRsRcAttribCatalog.rsrcAttribName" 
        style="width:200px;" data-options="required:true,validType:['length[1,40]','illegal']" value="${sysRsRcCatalog.catalogName}"/>   
    </div>
    
    
    
    <div>   
        <label for="type">产品类别:</label>   
        <select id="type" class="easyui-combobox" name="sysRsRcCatalog.catalogType" style="width:200px;">   
		    <option value="1" <s:if test="sysRsRcCatalog.catalogType==1"> selected=‘selected’</s:if>>产品</option>
			<option value="2" <s:if test="sysRsRcCatalog.catalogType==2"> selected=‘selected’</s:if>>工人</option>   
		</select>   
    </div>
    
   <!--
    @Column(name="RSRC_ATTRIB_CODE", nullable = false,length=32)
	private String rsrcAttribCode;//属性代号 RSRC_ATTRIB_CODE	属性代号	varchar2(32)		X
	
	@Column(name="RSRC_ATTRIB_NAME", nullable = false,length=128)
	private String rsrcAttribName;//属性名称 RSRC_ATTRIB_NAME	属性名称	varchar2(128)		X
	
	@Column(name="DATA_TYPE_ID", nullable = false)
	private Integer dataTypeId;//数据类型ID  DATA_TYPE_ID	数据类型ID	number(10)
	
	@Column(name="CTRL_TYPE_ID", nullable = false)
	private Integer controlTypeId;//控件类型 CTRL_TYPE_ID	控件类型ID	number(10)
	
	@Column(name="DATA_LENGTH")
	private BigDecimal dataLength;//数据长度 DATA_LENGTH	数据长度	number(10)
	
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
	 -->
</form>
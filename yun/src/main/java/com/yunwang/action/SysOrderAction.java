package com.yunwang.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysBrand;
import com.yunwang.model.pojo.SysDataDictionary;
import com.yunwang.model.pojo.SysMember;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.model.pojo.SysOrderFlow;
import com.yunwang.model.pojo.SysOrderPackage;
import com.yunwang.model.pojo.SysOrderResource;
import com.yunwang.model.pojo.SysOrderWorker;
import com.yunwang.model.pojo.SysResourceRel;
import com.yunwang.model.pojo.SysRsRcPackage;
import com.yunwang.model.pojo.SysSupplier;
import com.yunwang.model.pojo.SysWorker;
import com.yunwang.service.SysBrandService;
import com.yunwang.service.SysMemberService;
import com.yunwang.service.SysOrderService;
import com.yunwang.service.SysResourceService;
import com.yunwang.service.SysRsRcPackageService;
import com.yunwang.service.SysSupplierService;
import com.yunwang.service.SysWorkerService;
import com.yunwang.util.BaseDataDictionaryUtil;
import com.yunwang.util.action.AbstractUpDownAction;
import com.yunwang.util.annotation.DownloadAnnotation;
import com.yunwang.util.collection.CollectionUtil;
import com.yunwang.util.date.MyDateUtils;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.util.string.StringBufferByCollectionUtil;
@Action(
	value = "sysOrderAction", 
	results = {
		@Result(name = "index",location="/WEB-INF/web/sysOrder/showIndex.jsp"),
		@Result(name = "manageIndex",location="/WEB-INF/web/sysOrder/manageIndex.jsp"),
		@Result(name = "saveOrUpdateOrderPage",location="/WEB-INF/web/sysOrder/saveOrUpdateOrderPage.jsp"),
		@Result(name = "workerAndResourceByFlow",location="/WEB-INF/web/sysOrder/workerAndResourceByFlow.jsp"),
		@Result(name = "workerAndResourceByOrder",location="/WEB-INF/web/sysOrder/workerAndResourceByOrder.jsp"),
		@Result(name = "seachIndex",location="/WEB-INF/web/sysOrder/seachIndex.jsp"),
		@Result(name = "orderInfo",location="/WEB-INF/web/sysOrder/orderInfo.jsp"),
		@Result(name = "flowInfo",location="/WEB-INF/web/sysOrder/flowInfo.jsp"),
		@Result(name = "saveOrUpdateOrderFlowPage",location="/WEB-INF/web/sysOrder/saveOrUpdateOrderFlowPage.jsp"),
		@Result(name = "selectWorker",location="/WEB-INF/web/sysOrder/selectWorker.jsp"),
		@Result(name = "selectResource",location="/WEB-INF/web/sysOrder/selectResource.jsp"),
		@Result(name="exportResource",type="stream",
			params={"encode","true","contentType","application/vnd.ms-excel;charset=UTF-8",
					"inputName","exportResourceStream","contentDisposition","attachment;filename=${exportResourceFileName}"}),
		@Result(name = "gtChart",location="/WEB-INF/web/sysOrder/gtChart.jsp")			
	}
)
public class SysOrderAction extends AbstractUpDownAction{

	private final static Logger LOG =Logger.getLogger(SysOrderAction.class);
	/*
	 * @date 2017-10-17
	 * @author YBF
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SysOrderService sysOrderService;
	@Autowired
	private SysSupplierService sysSupplierService;
	@Autowired
	private SysBrandService sysBrandService;
	@Autowired
	private SysRsRcPackageService sysRsRcPackageService;
	@Autowired
	private SysWorkerService sysWorkerService;
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysMemberService sysMemberService;
	
	
	private Map<String,Object> hashMap;
	private SysOrder sysOrder;
	private SysOrderFlow sysOrderFlow;
	private String id;
	private String ids;
	private String jsonStr;
	private List<SysDataDictionary> typeList;
	private String dateStr;
	private List<SysDataDictionary> flowList;
	private List<SysSupplier> sysSuppliers;
	private List<SysBrand> sysBrands;
	private List<SysDataDictionary> sexList;
	private SysOrderWorker sysOrderWorker;
	private SysOrderResource sysOrderResource;
	
	private Integer point;
	private Integer targetId;
	private Integer sourceId;
	
	private String width;
	private String height;
	
	@Override
	public String execute() throws Exception {
		hashMap = new HashMap<String,Object>();
		return "index";
	}
	
	/**
	 * @return  订单流程管理
	 */
	public String manageIndex(){
		return "manageIndex";
	}
	
	/**
	 * @return  订单查询新增编辑管理
	 */
	public String seachIndex(){
		return "seachIndex";
	}
	
	public String findTree(){
		JSONArray jsonArr=new JSONArray();
		if (id==null) {
			JSONObject json=new JSONObject();
			json.put("id", "root");
			json.put("text", "订单列表");
			JSONObject obj=new JSONObject();
			obj.put("id",0);
			json.put("attributes",obj);
			json.put("state", "closed");
			jsonArr.add(json);
		}else if("root".equals(id)){
			//时间分组
			List<SysOrder> timeGroups = sysOrderService.findOrderTimeGroup();
			for(SysOrder order:timeGroups){
				JSONObject obj=new JSONObject();
				obj.put("id", "t"+order.getOrderDate());
				obj.put("text", order.getOrderDate());
				JSONObject attr=new JSONObject();
				attr.put("id",order.getOrderDate());
				obj.put("attributes",attr);
				obj.put("state", "closed");
				jsonArr.add(obj);
			}
		}else if(id.startsWith("t")){
			//具体的订单
			List<SysOrder> timeGroups = sysOrderService.findByOrderDate(id.substring(1, id.length()));
			for(SysOrder order:timeGroups){
				JSONObject obj=new JSONObject();
				obj.put("id", "o"+order.getId());
				obj.put("text", order.getName());
				obj.put("attributes",JSONObject.fromObject(order));
				obj.put("state", "closed");
				jsonArr.add(obj);
			}
		}else{
			//订单步骤 s开头
			List<SysOrderFlow> orderFlows = sysOrderService.findOrderFlow(
					Integer.parseInt(id.substring(1, id.length())));
			for(SysOrderFlow orderFlow:orderFlows){
				JSONObject obj=new JSONObject();
				obj.put("id", "s"+orderFlow.getId());
				obj.put("text", orderFlow.getName());
				obj.put("attributes",JSONObject.fromObject(orderFlow));
				obj.put("state", "open");
				jsonArr.add(obj);
			}
		}
		return ajaxText(jsonArr);
	}
	
	public String orderInfo(){
		return "orderInfo";
	}
	
	public String flowInfo(){
		return "flowInfo";
	}
	
	public String orderInfoData(){
		sysOrder = sysOrderService.get(sysOrder.getId());
		JSONArray jsonArr=new JSONArray();
		orderInfoData(jsonArr);
		return ajaxText(jsonArr);
	}

	public String listData(){
		JSONObject obj=new JSONObject();
		Pager<SysOrder> pager = sysOrderService.findAll(page,rows,JSONObject.fromObject(jsonStr));
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
  			obj.put("total", pager.getTotalRows());
  			arr = JSONArray.fromObject(pager.getData());
  	    }else{
  	        obj.put("total",0); 
  	    }
		obj.put("rows", arr);
  		return ajaxText(JSONObject.fromObject(obj).toString());
	}
	
	
	/**
	 * @date 2017-10-10
	 * @author YBF
	 * @return
	 * <p>保存表格行数据</p>
	 */
	public String saveOrUpdateOrderPage(){
		Map<Integer,SysOrderPackage> orderPackageMap = new HashMap<Integer,SysOrderPackage>();
		if(null != sysOrder && null != sysOrder.getId()){
			sysOrder = sysOrderService.get(sysOrder.getId());
			if(null != sysOrder.getRelationMemberId()){
				SysMember sysMember = sysMemberService.get(sysOrder.getRelationMemberId());
				sysOrder.setRelationMemberName(sysMember.getUserName());
			}
			List<SysOrderPackage> sysOrderPackages = sysOrderService.findOrderPackage(sysOrder.getId());
			orderPackageMap = CollectionUtil.listToMap(sysOrderPackages,"rsrcPackageId");
		}
		typeList = BaseDataDictionaryUtil.baseDataMap.get(7);
		
		//查找所有的套餐
		hashMap = new HashMap<String,Object>();
		JSONArray packageArr = new JSONArray();
		List<SysRsRcPackage> sysRsRcPackages = sysRsRcPackageService.findAll("orderNo");
		for(SysRsRcPackage sysRsRcPackage:sysRsRcPackages){
			JSONObject obj = new JSONObject();
			obj.put("id", sysRsRcPackage.getId());
			obj.put("text", sysRsRcPackage.getName());
			if(orderPackageMap.get(sysRsRcPackage.getId())!=null){
				obj.put("selected",true);
			}
			packageArr.add(obj);
		}
		hashMap.put("packageArr", packageArr);
		return "saveOrUpdateOrderPage";
	}
	
	/**
	 * @date 2017-10-10
	 * @author YBF
	 * @return
	 * <p>保存表格行数据</p>
	 */
	public String saveOrUpdateOrder(){
		try{
			sysOrderService.saveOrUpdateOrder(sysOrder);
			return success("操作成功!",JSONObject.fromObject(sysOrder));
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!",e);
		}
	}
	
	public String workerAndResourceByFlow(){
		initData();
		return "workerAndResourceByFlow";
	}
	
	public String workerAndResourceByOrder(){
		initData();
		return "workerAndResourceByOrder";
	}
	
	private void initData(){
		hashMap = new HashMap<String,Object>();
		sysSuppliers = sysSupplierService.findAll();
		JSONObject supplierObj = new JSONObject();
		for(SysSupplier sysSupplier:sysSuppliers){
			supplierObj.put(sysSupplier.getId(),sysSupplier.getName());
		}
		hashMap.put("supplierObj",supplierObj);
		
		sysBrands = sysBrandService.findAll();
		JSONObject brandObj = new JSONObject();
		for(SysBrand sysBrand:sysBrands){
			brandObj.put(sysBrand.getId(),sysBrand.getName());
		}
		hashMap.put("brandObj",brandObj);
		
		List<SysDataDictionary> sexList = BaseDataDictionaryUtil.baseDataMap.get(6);
		JSONObject sexObj = new JSONObject();
		for(SysDataDictionary dictionary:sexList){
			sexObj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("sexObj",sexObj);
	}
	
	
	public String workerDataByFlow(){
		List<SysWorker> sysWorkers = sysWorkerService.findByFlowId(sysOrderFlow.getId());
		return ajaxJSONArr(sysWorkers);
	}
	
	public String workerDataByOrder(){
		List<SysWorker> sysWorkers = sysWorkerService.findByOrderId(sysOrder.getId());
		
		JSONObject obj = new JSONObject();
		obj.put("total", sysWorkers.size());
		obj.put("rows", JSONArray.fromObject(sysWorkers));
		
		
		JSONArray footArr = new JSONArray();
		BigDecimal totalTime = BigDecimal.ZERO;
		BigDecimal totalWages = BigDecimal.ZERO;
		
		JSONObject footObj = new JSONObject();
		for(SysWorker worker : sysWorkers){
			totalTime = totalTime.add(worker.getWorkTime());
			totalWages = totalWages.add(worker.getWorkTime().multiply(worker.getWages()));
		}
		footObj.put("name","总薪资:");
		footObj.put("wages",totalWages);
		footArr.add(footObj);
		obj.put("footer", footArr);
		return ajaxText(obj);
	}
	
	public String resourceDataByFlow(){
		List<SysResourceRel> resources = sysResourceService.findByFlowId(sysOrderFlow.getId());
		return ajaxJSONArr(resources);
	}
	
	public String resourceDataByOrder(){
		List<SysResourceRel> resources = sysResourceService.findByOrderId(sysOrder.getId());
		
		JSONObject obj = new JSONObject();
		obj.put("total", resources.size());
		obj.put("rows", JSONArray.fromObject(resources));
		
		JSONArray footArr = new JSONArray();
		BigDecimal quantity = BigDecimal.ZERO;
		BigDecimal totalValue = BigDecimal.ZERO;

		JSONObject footObj = new JSONObject();
		for(SysResourceRel resourceRel : resources){
			quantity = quantity.add(resourceRel.getQuantity());
			totalValue = totalValue.add(resourceRel.getSalePrice().multiply(resourceRel.getQuantity()));
		}
		footObj.put("keyWord","总价格:");
		footObj.put("salePrice",totalValue);
		footArr.add(footObj);
		obj.put("footer", footArr);
		return ajaxText(obj);
		
		//return ajaxJSONArr(resources);
	}
	
	public String selectWorker(){
		hashMap = new HashMap<String,Object>();
		sexList = BaseDataDictionaryUtil.baseDataMap.get(6);
		JSONObject sexObj = new JSONObject();
		for(SysDataDictionary dictionary:sexList){
			sexObj.put(dictionary.getValue(), dictionary.getName());
		}
		hashMap.put("sexObj",sexObj);
		return "selectWorker";
	}
	
	public String selectResource(){
		hashMap = new HashMap<String,Object>();
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		sysSuppliers = sysSupplierService.findAll();
		sysBrands = sysBrandService.findAll();
		hashMap = new HashMap<String,Object>();
		JSONObject obj = new JSONObject();
		for(SysDataDictionary dictionary:flowList){
			obj.put(dictionary.getValue(), dictionary.getName());
		}
		JSONObject supplierObj = new JSONObject();
		for(SysSupplier sysSupplier:sysSuppliers){
			supplierObj.put(sysSupplier.getId(),sysSupplier.getName());
		}
		JSONObject brandObj = new JSONObject();
		for(SysBrand sysBrand:sysBrands){
			brandObj.put(sysBrand.getId(),sysBrand.getName());
		}
		hashMap.put("brandObj",brandObj);
		hashMap.put("flowObj",obj);
		hashMap.put("supplierObj",supplierObj);
		return "selectResource";
	}
	
	
	public String selectWorkerData(){
		JSONObject obj=new JSONObject();
		Pager<SysWorker> pager = sysWorkerService.selectWorkerData(
				sysOrderFlow.getId(),page,rows,JSONObject.fromObject(jsonStr));
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
  			obj.put("total", pager.getTotalRows());
  			arr = JSONArray.fromObject(pager.getData());
  	    }else{
  	        obj.put("total",0); 
  	    }
		obj.put("rows", arr);
  		return ajaxText(JSONObject.fromObject(obj).toString());
	}
	
	public String selectResourceData(){
		JSONObject obj=new JSONObject();
		Pager<SysResourceRel> pager = sysResourceService.selectResourceData(
				sysOrderFlow.getId(),page,rows,JSONObject.fromObject(jsonStr));
		JSONArray arr = new JSONArray();
		if(null!=pager && null!=pager.getData()){
  			obj.put("total", pager.getTotalRows());
  			arr = JSONArray.fromObject(pager.getData());
  	    }else{
  	        obj.put("total",0); 
  	    }
		obj.put("rows", arr);
  		return ajaxText(JSONObject.fromObject(obj).toString());
	}
	
	
	public String addWorkerToFlow(){
		try{
			sysOrderService.addWorkerToFlow(jsonStr,sysOrderFlow);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String addResourceToFlow(){
		try{
			sysOrderService.addResourceToFlow(jsonStr,sysOrderFlow);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String updateOrderWorkerTime(){
		try{
			sysOrderService.updateOrderWorkerTime(sysOrderWorker);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String updateOrderResourceQuantity(){
		try{
			sysOrderService.updateOrderResourceQuantity(sysOrderResource);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String deleteOrderWorker(){
		try{
			sysOrderService.deleteOrderWorker(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	public String deleteOrderResource(){
		try{
			sysOrderService.deleteOrderResource(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	/**
	 * @date 2017-11-1
	 * @author YBF
	 * @return
	 * <p>删除订单</p>
	 */
	public String deleteOrder(){
		try{
			sysOrderService.deleteOrder(ids);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	
	/**
	 * @date 2017-11-1
	 * @author YBF
	 * @return
	 * <p>订单操作步骤信息</p>
	 */
	public String orderFlowInfoData(){
		sysOrderFlow = sysOrderService.getOrderFlow(sysOrderFlow.getId());
		JSONArray jsonArr=new JSONArray();
		orderFlowInfoData(jsonArr);
		return ajaxText(jsonArr);
	}
	
	
	/**
	 * @date 2017-11-2
	 * @author YBF
	 * @return
	 * <p>新增和更新订单步骤流程</p>
	 */
	public String saveOrUpdateOrderFlowPage(){
		if(null != sysOrderFlow && null != sysOrderFlow.getId()){
			sysOrderFlow = sysOrderService.getOrderFlow(sysOrderFlow.getId());
		}
		flowList = BaseDataDictionaryUtil.baseDataMap.get(4);
		return "saveOrUpdateOrderFlowPage";
	}
	
	
	/**
	 * @date 2017-11-2
	 * @author YBF
	 * @return
	 * <p>新增和更新订单步骤流程</p>
	 */
	public String saveOrUpdateOrderFlow(){
		try{
			sysOrderService.saveOrUpdateOrderFlow(sysOrderFlow);
			return success("行数据保存成功!",sysOrderFlow);
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("行数据保存失败!",e);
		}
	}
	
	
	/**
	 * @date 2017-11-2
	 * @author YBF
	 * @return
	 * <p>订单步骤流程顺序调整</p>
	 */
	public String dragOrderFlow(){
		try{
			sysOrderService.dragOrderFlow(point,targetId,sourceId);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	

	/**
	 * @date 2017-11-2
	 * @author YBF
	 * @return
	 * <p>订单步骤流程顺序调整</p>
	 */
	public String deleteOrderFlow(){
		try{
			sysOrderService.deleteOrderFlow(sysOrderFlow);
			return success("操作成功!");
		}catch(Exception e){
			LOG.error(e.getMessage());
			return error("操作失败!");
		}
	}
	
	
	private void orderFlowInfoData(JSONArray jsonArr) {
		JSONObject json_name=new JSONObject();
		json_name.put("attrName", "名称");
		json_name.put("value", sysOrderFlow.getName());
		jsonArr.add(json_name);
		
		JSONObject json_rooms=new JSONObject();
		json_rooms.put("attrName", "步骤类型");
		json_rooms.put("value", sysOrderFlow.getFlowType());
		jsonArr.add(json_rooms);
		
		JSONObject totalStatus=new JSONObject();
		totalStatus.put("attrName", "状态");
		totalStatus.put("value", sysOrderFlow.getStatus());
		jsonArr.add(totalStatus);
		
		JSONObject time=new JSONObject();
		time.put("attrName", "预计装修时间");
		time.put("value", 
				(MyStringUtil.isNotBlank(sysOrderFlow.getStartTimeStr())?sysOrderFlow.getStartTimeStr():"?")
				+"至"+
				(MyStringUtil.isNotBlank(sysOrderFlow.getEndTimeStr())?sysOrderFlow.getEndTimeStr():"?"));
		jsonArr.add(time);
		
		JSONObject info=new JSONObject();
		info.put("attrName", "备注");
		info.put("value", sysOrderFlow.getInfo());
		jsonArr.add(info);
	}
	
	//后期根据国际化来做
	private void orderInfoData(JSONArray jsonArr) {
//		JSONObject json_code=new JSONObject();
//		json_code.put("attrName", "编号");
//		json_code.put("value", sysOrder.getCode());
//		jsonArr.add(json_code);
		
		JSONObject json_name=new JSONObject();
		json_name.put("attrName", "名称");
		json_name.put("value", sysOrder.getName());
		jsonArr.add(json_name);
		
		JSONObject json_rooms=new JSONObject();
		json_rooms.put("attrName", "房间数量");
		json_rooms.put("value", sysOrder.getRoomsStr());
		jsonArr.add(json_rooms);
		
		JSONObject totalArea=new JSONObject();
		totalArea.put("attrName", "总面积");
		totalArea.put("value", sysOrder.getTotalArea()+"㎡");
		jsonArr.add(totalArea);
		
		JSONObject totalAmount=new JSONObject();
		totalAmount.put("attrName", "总价");
		totalAmount.put("value", sysOrder.getTotalAmount()+"元");
		jsonArr.add(totalAmount);
		
		JSONObject orderType=new JSONObject();
		orderType.put("attrName", "装修类型");
		orderType.put("value", BaseDataDictionaryUtil.baseDataMap.get(7).get( sysOrder.getOrderType()).getName());
		jsonArr.add(orderType);
		
		JSONObject orderDate=new JSONObject();
		orderDate.put("attrName", "下单时间");
		orderDate.put("value", sysOrder.getOrderDate());
		jsonArr.add(orderDate);
		
		JSONObject contact=new JSONObject();
		contact.put("attrName", "联系人");
		contact.put("value", sysOrder.getContact());
		jsonArr.add(contact);
		
		JSONObject contactTel=new JSONObject();
		contactTel.put("attrName", "联系方式");
		contactTel.put("value", sysOrder.getContactTel());
		jsonArr.add(contactTel);
		
		JSONObject address=new JSONObject();
		address.put("attrName", "装修地址");
		address.put("value", sysOrder.getAddress());
		jsonArr.add(address);
		
		JSONObject time=new JSONObject();
		time.put("attrName", "预计装修时间");
		time.put("value", sysOrder.getStartTimeStr()+"至"+sysOrder.getEndTimeStr());
		jsonArr.add(time);
		
		JSONObject info=new JSONObject();
		info.put("attrName", "备注");
		info.put("value", sysOrder.getInfo());
		jsonArr.add(info);
		
		JSONObject status=new JSONObject();
		status.put("attrName", "状态");
		status.put("value", sysOrder.getStatus());
		jsonArr.add(status);
	}
	
	//采购单
	@DownloadAnnotation("sysOrderAction_exportSysOrderPurchase")
	public String exportSysOrderPurchase(){
		//根据供应商进行区分
		sysOrder = sysOrderService.get(sysOrder.getId());
		exportResourceFileName = sysOrder.getName()+"_TO_采购单.xls";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Workbook workbook=null;
        try {
            workbook = exportOrderToSupplierExcel(sysOrder);
		    workbook.write(output);
	        byte[] ba = output.toByteArray();
	        exportResourceStream = new ByteArrayInputStream(ba);
		}catch(Exception e){
		    LOG.error(e.getMessage());
		}finally{
		    try {
                output.flush();
                output.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
		}
		return "exportResource";
	}
	
	private Workbook exportOrderToSupplierExcel(SysOrder sysOrder) {
		List<SysResourceRel> resourceRels = sysResourceService.findByOrderId(sysOrder.getId());
	    //根据供应商打包
		Map<Integer,List<SysResourceRel>> resourceMap = packageBySupplier(resourceRels);
		
		List<SysSupplier> sysSuppliers = sysSupplierService.findAll();
		Map<Integer,SysSupplier> supplierMap = CollectionUtil.listToMap(sysSuppliers,"id");
		
		List<SysBrand> sysBrands = sysBrandService.findAll();
		Map<Integer,SysBrand> sysBrandMap = CollectionUtil.listToMap(sysBrands,"id");
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("采购产品");
		int nCol = 0;  //列编号
		int nRow = 0;  //行编号
		List<String> results = new ArrayList<String>();
		results.add(getText("供应商"));
		results.add(getText("产品描述"));
		results.add(getText("产品价格"));
		results.add(getText("数量"));
		results.add(getText("品牌"));
		
		// 创建单元格样式
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 创建Excel的sheet的一行
		Row row = sheet.createRow(nRow++);
	    Cell cell =null;
		row.setHeightInPoints(15);// 设定行的高度
		nCol = 0;
		for (String s : results) {
			if("产品描述".equals(s)){
				sheet.setColumnWidth(nCol, 10000);
			}else{
				sheet.setColumnWidth(nCol, 4000);
			}
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(s);
		}
		
		for(Integer key:resourceMap.keySet()){
			List<SysResourceRel> supResourceRels = resourceMap.get(key);
			BigDecimal totalPrice = BigDecimal.ZERO;
			for(SysResourceRel resourceRel:supResourceRels){
				//打包表格数据
				row = sheet.createRow(nRow++);
				nCol=0;
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(null!=supplierMap.get(resourceRel.getSupplierId())?
						supplierMap.get(resourceRel.getSupplierId()).getName():"");
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(resourceRel.getKeyWord());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(resourceRel.getPurchasePrice().toString());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(resourceRel.getQuantity().toString());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(null!=sysBrandMap.get(resourceRel.getBrandId())?
						sysBrandMap.get(resourceRel.getBrandId()).getName():"");
			
				totalPrice = totalPrice.add(resourceRel.getPurchasePrice().multiply(resourceRel.getQuantity()));
			}
			
			row = sheet.createRow(nRow++);
			nCol=0;
			nCol++;
			nCol++;
			nCol++;
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue("总价:");
			
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(totalPrice.toString());
			
			nRow++;
		}
		return wb;
	}

	private Map<Integer, List<SysResourceRel>> packageBySupplier(
			List<SysResourceRel> resourceRels) {
		Map<Integer,List<SysResourceRel>> map = new HashMap<Integer,List<SysResourceRel>>();
		for(SysResourceRel rel:resourceRels){
			List<SysResourceRel> sourceList = map.get(rel.getSupplierId());
			if(null!=sourceList){
				sourceList.add(rel);
			}else{
				sourceList = new ArrayList<SysResourceRel>();
				sourceList.add(rel);
				map.put(rel.getSupplierId(), sourceList);
			}
		}
		return map;
	}

	//施工单
	@DownloadAnnotation("sysOrderAction_exportSysOrderConstruction")
	public String exportSysOrderConstruction(){
		//根据流程打包
		sysOrder = sysOrderService.get(sysOrder.getId());
		exportResourceFileName = sysOrder.getName()+"_TO_施工单.xls";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Workbook workbook=null;
        try {
            workbook = exportOrderToWorkerExcel(sysOrder);
		    workbook.write(output);
	        byte[] ba = output.toByteArray();
	        exportResourceStream = new ByteArrayInputStream(ba);
		}catch(Exception e){
		    LOG.error(e.getMessage());
		}finally{
		    try {
                output.flush();
                output.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
		}
		return "exportResource";
	}
	
	
	private Workbook exportOrderToWorkerExcel(SysOrder sysOrder) {
		
		List<SysOrderFlow> sysOrderFlows = sysOrderService.findOrderFlow(sysOrder.getId());
		
	    //根据流程打包（每个流程需要的材料，开始时间结束时间，已经每个流程的备注，包含哪些工人）
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("施工单");
		int nCol = 0;  //列编号
		int nRow = 0;  //行编号
		List<String> results = new ArrayList<String>();
		
		results.add(getText("操作步骤"));
		results.add(getText("产品描述"));
		results.add(getText("数量"));
		results.add(getText("工人"));
		results.add(getText("步骤备注"));
		results.add(getText("操作时间"));
		
		// 创建单元格样式
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 创建Excel的sheet的一行
		Row row = sheet.createRow(nRow++);
	    Cell cell =null;
		row.setHeightInPoints(15);// 设定行的高度
		nCol = 0;
		for (String s : results) {
			if("产品描述".equals(s)){
				sheet.setColumnWidth(nCol, 10000);
			}else{
				sheet.setColumnWidth(nCol, 4000);
			}
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(s);
		}
		
		for(SysOrderFlow orderFlow:sysOrderFlows){
			//查询orderFlow关联的资源
			List<SysResourceRel> resourceRels = sysResourceService.findByFlowId(orderFlow.getId());
			
			//查询orderFlow关联的工人
			List<SysWorker> sysWorkers = sysWorkerService.findByFlowId(orderFlow.getId());
			
			for(SysResourceRel resourceRel:resourceRels){
				row = sheet.createRow(nRow++);
				nCol=0;
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(orderFlow.getName());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(resourceRel.getKeyWord());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(resourceRel.getQuantity().toString());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(StringBufferByCollectionUtil.convertCollection(sysWorkers,"name"));
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(orderFlow.getInfo());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(orderFlow.getStartTimeStr()+"-"+orderFlow.getEndTimeStr());
			}
		}
		return wb;
	}
	
	//报价单
	@DownloadAnnotation("sysOrderAction_exportSysOrderQuotation")
	public String exportSysOrderQuotation(){
		//根据流程区分
		//根据工人进行区分
		sysOrder = sysOrderService.get(sysOrder.getId());
		exportResourceFileName = sysOrder.getName()+"_TO_报价单.xls";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Workbook workbook=null;
        try {
            workbook = exportOrderToUserExcel(sysOrder);
		    workbook.write(output);
	        byte[] ba = output.toByteArray();
	        exportResourceStream = new ByteArrayInputStream(ba);
		}catch(Exception e){
		    LOG.error(e.getMessage());
		}finally{
		    try {
                output.flush();
                output.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
		}
		return "exportResource";
	}
	
	private Workbook exportOrderToUserExcel(SysOrder sysOrder) {
		
		List<SysOrderFlow> sysOrderFlows = sysOrderService.findOrderFlow(sysOrder.getId());
		
	    //根据流程打包（每个流程需要的材料，和操作的工人，材料的价格和数量以及统计 ，工人的工时，薪资以及统计）
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("订单工人资源");
		int nCol = 0;  //列编号
		int nRow = 0;  //行编号
		List<String> results = new ArrayList<String>();
		results.add(getText("操作步骤"));
		results.add(getText("产品描述"));
		results.add(getText("品牌"));
		results.add(getText("数量"));
		results.add(getText("产品价格"));
		results.add(getText("工人"));
		results.add(getText("工时"));
		results.add(getText("薪酬"));
		
		// 创建单元格样式
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 创建Excel的sheet的一行
		Row row = sheet.createRow(nRow++);
	    Cell cell =null;
		row.setHeightInPoints(15);// 设定行的高度
		nCol = 0;
		for (String s : results) {
			if("产品描述".equals(s)){
				sheet.setColumnWidth(nCol, 10000);
			}else{
				sheet.setColumnWidth(nCol, 4000);
			}
			cell = row.createCell(nCol++);
			cell.setCellStyle(style);
			cell.setCellValue(s);
		}
		
		for(SysOrderFlow orderFlow:sysOrderFlows){
			//查询orderFlow关联的资源
			List<SysResourceRel> resourceRels = sysResourceService.findByFlowId(orderFlow.getId());
			
			//查询orderFlow关联的工人
			List<SysWorker> sysWorkers = sysWorkerService.findByFlowId(orderFlow.getId());
			
			for(SysResourceRel resourceRel:resourceRels){
				row = sheet.createRow(nRow++);
				nCol=0;
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(orderFlow.getName());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(resourceRel.getKeyWord());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(resourceRel.getQuantity().toString());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(StringBufferByCollectionUtil.convertCollection(sysWorkers,"name"));
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(orderFlow.getInfo());
				
				cell = row.createCell(nCol++);
				cell.setCellStyle(style);
				cell.setCellValue(orderFlow.getStartTimeStr()+"-"+orderFlow.getEndTimeStr());
			}
		}
		return wb;
	}
	
	/**
	 * @date 2017-11-24
	 * @author YBF
	 * @return
	 * <p>工作进度图</p>
	 */
	public String gtChart(){
		//订单步骤 s开头
		List<SysOrderFlow> orderFlows = sysOrderService.findOrderFlow(sysOrder.getId());
		JSONArray arr = new JSONArray();
		for(SysOrderFlow orderFlow:orderFlows){
			JSONObject orderFlowObj = new JSONObject();
			orderFlowObj.put("planBeginDate", MyStringUtil.isNotBlank(
					orderFlow.getStartTimeStr())?orderFlow.getStartTimeStr():MyDateUtils.getStringByDate(new Date()));
			orderFlowObj.put("planEndDate", MyStringUtil.isNotBlank(
					orderFlow.getEndTimeStr())?orderFlow.getEndTimeStr():MyDateUtils.getStringByDate(MyDateUtils.getAppointDate(-5,new Date())));
			orderFlowObj.put("beginDate", orderFlow.getActualStartTimeStr());
			orderFlowObj.put("endDate", orderFlow.getActualEndTimeStr());
			orderFlowObj.put("name", orderFlow.getName());
			arr.add(orderFlowObj);
		}
	    JSONObject obj = new JSONObject();
		obj.put("stageList", arr);
		dateStr = obj.toString();
		return "gtChart";
	}
	
	public Map<String, Object> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<String, Object> hashMap) {
		this.hashMap = hashMap;
	}

	public SysOrder getSysOrder() {
		return sysOrder;
	}

	public void setSysOrder(SysOrder sysOrder) {
		this.sysOrder = sysOrder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public List<SysDataDictionary> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<SysDataDictionary> typeList) {
		this.typeList = typeList;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public SysOrderFlow getSysOrderFlow() {
		return sysOrderFlow;
	}

	public void setSysOrderFlow(SysOrderFlow sysOrderFlow) {
		this.sysOrderFlow = sysOrderFlow;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public List<SysDataDictionary> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<SysDataDictionary> flowList) {
		this.flowList = flowList;
	}

	public List<SysSupplier> getSysSuppliers() {
		return sysSuppliers;
	}

	public void setSysSuppliers(List<SysSupplier> sysSuppliers) {
		this.sysSuppliers = sysSuppliers;
	}

	public List<SysDataDictionary> getSexList() {
		return sexList;
	}

	public void setSexList(List<SysDataDictionary> sexList) {
		this.sexList = sexList;
	}

	public SysOrderWorker getSysOrderWorker() {
		return sysOrderWorker;
	}

	public void setSysOrderWorker(SysOrderWorker sysOrderWorker) {
		this.sysOrderWorker = sysOrderWorker;
	}

	public SysOrderResource getSysOrderResource() {
		return sysOrderResource;
	}

	public void setSysOrderResource(SysOrderResource sysOrderResource) {
		this.sysOrderResource = sysOrderResource;
	}

	public List<SysBrand> getSysBrands() {
		return sysBrands;
	}

	public void setSysBrands(List<SysBrand> sysBrands) {
		this.sysBrands = sysBrands;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}

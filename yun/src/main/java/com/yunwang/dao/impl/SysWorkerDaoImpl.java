package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysWorkerDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysWorker;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysWorkerDaoImpl extends BaseDaoImpl<SysWorker> implements SysWorkerDaoI{

	@Override
	public Pager<SysWorker> findAll(int page, int rows, JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				"SELECT model FROM SysWorker model WHERE model.status!=0 ");
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("name")&&MyStringUtil.isNotBlank(seachJson.getString("name"))){
				buf.append(" AND upper(model.name) like:name");
				map.put("name","%"+seachJson.getString("name").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("sex")&&0!=seachJson.getInt("sex")){
				buf.append(" AND model.workType=:sex");
				map.put("sex",seachJson.getInt("sex"));
			}
			if(seachJson.containsKey("phoneNum")&&MyStringUtil.isNotBlank(seachJson.getString("phoneNum"))){
				buf.append(" AND upper(model.phoneNum) like:phoneNum");
				map.put("phoneNum","%"+ seachJson.getString("phoneNum").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("telNum")&&MyStringUtil.isNotBlank(seachJson.getString("telNum"))){
				buf.append(" AND upper(model.telNum) like:telNum");
				map.put("telNum","%"+ seachJson.getString("telNum").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("address")&&MyStringUtil.isNotBlank(seachJson.getString("address"))){
				buf.append(" AND upper(model.address) like:address");
				map.put("address","%"+ seachJson.getString("address").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append(" AND model.workType=:workType");
				map.put("workType",seachJson.getInt("workType"));
			}
			if(seachJson.containsKey("company")&&MyStringUtil.isNotBlank(seachJson.getString("company"))){
				buf.append(" AND upper(model.company) like:company");
				map.put("company","%"+ seachJson.getString("company").toUpperCase()+ "%");
			}
		}
		buf.append(" ORDER BY model.id");
		return pagedQuery(buf.toString(), page, rows, map);
	}

	@Override
	public void deleteWorker(String ids) {
		String hql = "UPDATE SysWorker model SET model.status = 0 WHERE model.id IN("+ids+")";
		executeHql(hql);
	}

	@Override
	public List<SysWorker> findByFlowId(Integer flowId) {
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.CODE code, " +
				" model.NAME name,model.SEX sex,model.PHONENUM phoneNum," +
				" model.TELNUM telNum,model.ADDRESS address,model.AGE age," +
				" model.BIRTHDAY birthday,model.WORK_TYPE workType,model.WAGES wages,model.WORK_AGE workAge," +
				" model.EDUCATION education,model.STATUS status,model.COMPANY company,orderWorker.WORK_TIME workTime,orderWorker.ID orderWorkerId " +
				" FROM SYS_ORDER_WORKER orderWorker" +
				" LEFT JOIN SYS_WORKER model ON model.ID = orderWorker.WORKER_ID " +
				" WHERE orderWorker.ORDER_FLOW_ID=:flowId ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		parmeMap.put("flowId", flowId);
		
		buf.append("  ORDER BY model.WORK_TYPE");
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("code", new IntegerType());
		scalarMap.put("name", new StringType());
		scalarMap.put("sex", new IntegerType());
		scalarMap.put("phoneNum", new StringType());
		scalarMap.put("telNum", new StringType());
		scalarMap.put("address", new StringType());
		scalarMap.put("age", new IntegerType());
		scalarMap.put("birthday", new StringType());
		scalarMap.put("workType", new IntegerType());
		scalarMap.put("wages", new BigDecimalType());
		scalarMap.put("workAge", new IntegerType());
		scalarMap.put("education", new IntegerType());
		scalarMap.put("status", new IntegerType());
		scalarMap.put("company", new IntegerType());
		scalarMap.put("workTime", new BigDecimalType());
		scalarMap.put("orderWorkerId", new IntegerType());
		return findBySQLQuery(buf.toString(),parmeMap,scalarMap);
	}

	@Override
	public List<SysWorker> findByOrderId(Integer orderId) {
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.CODE code, " +
				" model.NAME name,model.SEX sex,model.PHONENUM phoneNum," +
				" model.TELNUM telNum,model.ADDRESS address,model.AGE age," +
				" model.BIRTHDAY birthday,model.WORK_TYPE workType,model.WAGES wages,model.WORK_AGE workAge," +
				" model.EDUCATION education,model.STATUS status,model.COMPANY company,orderWorker.WORK_TIME workTime,orderWorker.ID orderWorkerId " +
				" FROM SYS_ORDER_WORKER orderWorker" +
				" LEFT JOIN SYS_WORKER model ON model.ID = orderWorker.WORKER_ID " +
				" WHERE orderWorker.ORDER_ID=:orderId ");
		
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		parmeMap.put("orderId", orderId);
		
		buf.append("  ORDER BY model.WORK_TYPE,model.id");
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("code", new IntegerType());
		scalarMap.put("name", new StringType());
		scalarMap.put("sex", new IntegerType());
		scalarMap.put("phoneNum", new StringType());
		scalarMap.put("telNum", new StringType());
		scalarMap.put("address", new StringType());
		scalarMap.put("age", new IntegerType());
		scalarMap.put("birthday", new StringType());
		scalarMap.put("workType", new IntegerType());
		scalarMap.put("wages", new BigDecimalType());
		scalarMap.put("workAge", new IntegerType());
		scalarMap.put("education", new IntegerType());
		scalarMap.put("status", new IntegerType());
		scalarMap.put("company", new IntegerType());
		scalarMap.put("workTime", new BigDecimalType());
		scalarMap.put("orderWorkerId", new IntegerType());
		return findBySQLQuery(buf.toString(),parmeMap,scalarMap);
	}

	@Override
	public Pager<SysWorker> findByWorkTypeAndNotInFlow(Integer flowType,
			Integer flowId,int page, int rows, JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				"SELECT model FROM SysWorker model WHERE model.status!=0 " +
				" AND model.workType=:flowType AND model.id " +
				" NOT IN(SELECT orderWorker.workerId FROM SysOrderWorker orderWorker WHERE orderWorker.orderFlowId=:flowId)");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("flowType", flowType);
		map.put("flowId", flowId);
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("name")&&MyStringUtil.isNotBlank(seachJson.getString("name"))){
				buf.append(" AND upper(model.name) like:name");
				map.put("name","%"+seachJson.getString("name").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("sex")&&0!=seachJson.getInt("sex")){
				buf.append(" AND model.workType=:sex");
				map.put("sex",seachJson.getInt("sex"));
			}
			if(seachJson.containsKey("phoneNum")&&MyStringUtil.isNotBlank(seachJson.getString("phoneNum"))){
				buf.append(" AND upper(model.phoneNum) like:phoneNum");
				map.put("phoneNum","%"+ seachJson.getString("phoneNum").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("telNum")&&MyStringUtil.isNotBlank(seachJson.getString("telNum"))){
				buf.append(" AND upper(model.telNum) like:telNum");
				map.put("telNum","%"+ seachJson.getString("telNum").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("address")&&MyStringUtil.isNotBlank(seachJson.getString("address"))){
				buf.append(" AND upper(model.address) like:address");
				map.put("address","%"+ seachJson.getString("address").toUpperCase()+ "%");
			}
			if(seachJson.containsKey("workType")&&0!=seachJson.getInt("workType")){
				buf.append(" AND model.workType=:workType");
				map.put("workType",seachJson.getInt("workType"));
			}
			if(seachJson.containsKey("company")&&MyStringUtil.isNotBlank(seachJson.getString("company"))){
				buf.append(" AND upper(model.company) like:company");
				map.put("company","%"+ seachJson.getString("company").toUpperCase()+ "%");
			}
		}
		buf.append(" ORDER BY model.id");
		return pagedQuery(buf.toString(), page, rows, map);
	}
}

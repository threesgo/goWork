package com.yunwang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysOrderDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysOrder;
import com.yunwang.util.string.MyStringUtil;

@Repository
public class SysOrderDaoImpl extends BaseDaoImpl<SysOrder> implements SysOrderDaoI{

	@Override
	public void updateStatus(String ids,Integer status) {
		String hql ="UPDATE SYS_ORDER model SET model.STATUS="+status+" WHERE model.ID IN("+ids+")";
		executeHql(hql);
	}
	
	@Override
	public List<SysOrder> findOrderTimeGroup() {
		String sql = "SELECT model.ORDER_DATE orderDate FROM SYS_ORDER model WHERE model.STATUS!=0 " +
				" GROUP BY model.ORDER_DATE ORDER BY model.ORDER_DATE";
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("orderDate", new StringType());
		return findBySQLQuery(sql,null,scalarMap);
	}

	@Override
	public List<SysOrder> findByOrderDate(String orderDate) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderDate",orderDate);
		return find("SELECT model FROM SysOrder model " 
				+" WHERE model.orderDate=:orderDate AND model.status!=0 ORDER BY model.orderNo DESC ",map);
	}

	@Override
	public Pager<SysOrder> findPageOrder(int page, int rows,
			JSONObject seachJson) {
		StringBuffer buf = new StringBuffer(
				" SELECT model.ID id,model.CODE code, " +
				" model.NAME name,model.TOTAL_AREA totalArea,model.TOTAL_AMOUNT totalAmount," +
				" model.ORDER_DATE orderDate,model.CONTACT contact,model.CONTACT_TEL contactTel," +
				" model.ADDRESS address,model.START_TIME startTime,model.END_TIME endTime,model.INFO info," +
				" model.ORDER_TYPE orderType,model.STATUS status,model.ORDER_NO orderNo," +
				" model.ROOM_NUM roomNum,model.HALL_NUM hallNum,model.KITCHEN_NUM kitchenNum," +
				" model.TOILET_NUM toiletNum,model.BALCONY_NUM balconyNum" +
				" FROM SYS_ORDER model WHERE model.STATUS!=0 ");
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		if(null!=seachJson && !seachJson.isEmpty()){
			if(seachJson.containsKey("orderDate")&&MyStringUtil.isNotBlank(seachJson.getString("orderDate"))){
				buf.append(" AND model.ORDER_DATE=:orderDate");
				parmeMap.put("orderDate",seachJson.getString("orderDate"));
			}
		}
		
		buf.append("  ORDER BY model.ORDER_NO DESC");
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("code", new StringType());
		scalarMap.put("name", new StringType());
		scalarMap.put("totalArea", new BigDecimalType());
		scalarMap.put("totalAmount", new BigDecimalType());
		scalarMap.put("orderDate", new StringType());
		scalarMap.put("contact", new StringType());
		scalarMap.put("contactTel", new StringType());
		scalarMap.put("address", new StringType());
		scalarMap.put("startTime", new TimestampType());
		scalarMap.put("endTime", new TimestampType());
		scalarMap.put("info", new StringType());
		scalarMap.put("orderType", new IntegerType());
		scalarMap.put("status", new IntegerType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("roomNum", new IntegerType());
		scalarMap.put("hallNum", new IntegerType());
		scalarMap.put("kitchenNum", new IntegerType());
		scalarMap.put("toiletNum", new IntegerType());
		scalarMap.put("balconyNum", new IntegerType());
		return pagedSqlQuery(buf.toString(),page,rows,parmeMap,scalarMap);
	}

	@Override
	public List<SysOrder> findOrderByMemberAndType(Integer memberId,
			Integer type, String status) {
		StringBuffer buf = new StringBuffer();
		Map<String, Object> parmeMap = new HashMap<String,Object>();
		if(type-1==0){
			buf.append(
					" SELECT model.ID id,model.CODE code, " +
					" model.NAME name,model.TOTAL_AREA totalArea,model.TOTAL_AMOUNT totalAmount," +
					" model.ORDER_DATE orderDate,model.CONTACT contact,model.CONTACT_TEL contactTel," +
					" model.ADDRESS address,model.START_TIME startTime,model.END_TIME endTime,model.INFO info," +
					" model.ORDER_TYPE orderType,model.STATUS status,model.ORDER_NO orderNo," +
					" model.ROOM_NUM roomNum,model.HALL_NUM hallNum,model.KITCHEN_NUM kitchenNum," +
					" model.TOILET_NUM toiletNum,model.BALCONY_NUM balconyNum,model.RELATION_MEMBER_ID relationMemberId " +
					" FROM SYS_ORDER model WHERE " +
					" model.RELATION_MEMBER_ID=:relationMemberId " +
					" AND model.STATUS IN ("+status+")");
			parmeMap.put("relationMemberId",memberId);
		}else if(type-2==0){
			buf.append(
					" SELECT model.ID id,model.CODE code, " +
					" model.NAME name,model.TOTAL_AREA totalArea,model.TOTAL_AMOUNT totalAmount," +
					" model.ORDER_DATE orderDate,model.CONTACT contact,model.CONTACT_TEL contactTel," +
					" model.ADDRESS address,model.START_TIME startTime,model.END_TIME endTime,model.INFO info," +
					" model.ORDER_TYPE orderType,model.STATUS status,model.ORDER_NO orderNo," +
					" model.ROOM_NUM roomNum,model.HALL_NUM hallNum,model.KITCHEN_NUM kitchenNum," +
					" model.TOILET_NUM toiletNum,model.BALCONY_NUM balconyNum,model.RELATION_MEMBER_ID relationMemberId " +
					" FROM SYS_ORDER_WORKER orderWorker LEFT JOIN SYS_ORDER model ON orderWorker.ORDER_ID = model.ID " +
					" WHERE orderWorker.WORKER_ID=:relationMemberId " +
					" AND model.STATUS IN ("+status+")");
			parmeMap.put("relationMemberId",memberId);
		}
		
		buf.append("  ORDER BY model.ORDER_NO DESC");
		
		Map<String, Type> scalarMap = new HashMap<String, Type>();
		scalarMap.put("id", new IntegerType());
		scalarMap.put("code", new StringType());
		scalarMap.put("name", new StringType());
		scalarMap.put("totalArea", new BigDecimalType());
		scalarMap.put("totalAmount", new BigDecimalType());
		scalarMap.put("orderDate", new StringType());
		scalarMap.put("contact", new StringType());
		scalarMap.put("contactTel", new StringType());
		scalarMap.put("address", new StringType());
		scalarMap.put("startTime", new TimestampType());
		scalarMap.put("endTime", new TimestampType());
		scalarMap.put("info", new StringType());
		scalarMap.put("orderType", new IntegerType());
		scalarMap.put("status", new IntegerType());
		scalarMap.put("orderNo", new IntegerType());
		scalarMap.put("roomNum", new IntegerType());
		scalarMap.put("hallNum", new IntegerType());
		scalarMap.put("kitchenNum", new IntegerType());
		scalarMap.put("toiletNum", new IntegerType());
		scalarMap.put("balconyNum", new IntegerType());
		return findBySQLQuery(buf.toString(),parmeMap,scalarMap);
	}
}

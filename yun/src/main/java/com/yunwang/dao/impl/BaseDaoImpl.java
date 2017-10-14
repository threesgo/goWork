package com.yunwang.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yunwang.dao.BaseDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.util.string.StringBufferByCollectionUtil;

@Repository
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> entityClass;

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		 Session s=this.sessionFactory.getCurrentSession();
		 return s;
	}

	public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	
	public Serializable save(T o) {
		if (o != null) {
			return this.getCurrentSession().save(o);
		}
		return null;
	}

	/*
	 * public T get(Serializable id) { return (T)
	 * this.getCurrentSession().get(getEntityClass(), id); }
	 */

	@SuppressWarnings("unchecked")
	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@SuppressWarnings("unchecked")
	public T get(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public T get(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	public void delete(T o) {
		if (o != null) {
			this.getCurrentSession().delete(o);
		}
	}

	public void update(T o) {
		if (o != null) {
			this.getCurrentSession().update(o);
		}
	}

	public void saveOrUpdate(T o) {
		if (o != null) {
			this.getCurrentSession().saveOrUpdate(o);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object... values) {
		Query query = creatQuery(hql, values);
		return (List<T>) query.list();
	}
	
	private Query creatQuery(String hql, Object[] values) {
        Query query = getCurrentSession().createQuery(hql);
        if (null != values && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

	private Query creatQuery(String hql, Map<String,Object> maps) {
        Query query = getCurrentSession().createQuery(hql);
        if(maps!=null){
        	for(Map.Entry<String,Object> entry:maps.entrySet()){
        		query.setParameter(entry.getKey(), entry.getValue());
        	}
        	
        }
        return query;
    }
	
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Map<String, Object> params, int page,
			int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public Long count(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	public int executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	public int executeHql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findBySql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findBySql(String sql, int page, int rows) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findBySql(String sql, Map<String, Object> params,
			int page, int rows) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public int executeSql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	public BigInteger countBySql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}

	public List<T> findByProperties(String[] properties, Object[] values) {
		List<Criterion> lstCriterion = new ArrayList<Criterion>();
		for (int index = 0; index < properties.length; index++) {
			lstCriterion.add(Restrictions.eq(properties[index], values[index]));
		}
		return findByCriteria(lstCriterion);
	}

	public List<T> findByProperty(String property, Object value) {
		List<Criterion> lstCriterion = new ArrayList<Criterion>();
		lstCriterion.add(Restrictions.eq(property, value));
		return findByCriteria(lstCriterion);
	}

	public List<T> findInPropertyValues(String property, String values) {
		String hql = "SELECT model  FROM " + entityClass.getSimpleName()
				+ " model WHERE model." + property + " IN (" + values + ")";
		return find(hql);
	}
	
	public void deleteByProperty(String property, Object value) {
		String hql = "DELETE FROM " + entityClass.getSimpleName()
				+ " model WHERE model." + property + "=" + value;
		executeHql(hql);
	}
	
	public void deleteByPropertys(String property, String values) {
		String hql = "DELETE FROM " + entityClass.getSimpleName()
				+ " model WHERE model." + property + " IN (" + values + ")";
		executeHql(hql);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(List<Criterion> lstCriterion, Order... order) {
		Criteria criteria = getCurrentSession().createCriteria(getEntityClass());
		if (null != lstCriterion) {
			for (Criterion c : lstCriterion) {
				criteria.add(c);
			}
		}
		if (null != order && order.length > 0) {
			for (Order o : order) {
				criteria.addOrder(o);
			}
		}
		return (List<T>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) getCurrentSession().createCriteria(getEntityClass()).list();
	}
	
	@SuppressWarnings("unchecked")
	public Pager<T> pagedQuery(String hql, int page, int pageSize, Object... values) {
       	int temp=pageSize;
        if(pageSize == 0){
            temp = 10;
        }
        int totalRows = getTotalRows(hql, values);
        int totalPages = 0;
        if(totalRows % temp == 0){
            totalPages = totalRows / temp;
        }else{
            totalPages = totalRows / temp + 1;
        }
        int start = (page - 1) * temp;
        if (totalPages < 1){
            return new Pager<T>();
        }
        Query query =creatQuery(hql,values);
		List<T> list = query.setFirstResult(start).setMaxResults(temp).list();
        return new Pager<T>(totalRows, page, totalPages,temp, list);
   	}
	
	public int getTotalRows(String hql, Object... values){
        String countQueryString = " select count(*) " + removeSelect(removeOrders(hql));
        List<?> countlist = find(countQueryString, values);
        if(countlist == null || countlist.isEmpty()){
            return 0;
        }
        return ((Long) countlist.get(0)).intValue();
    }
	
	
	public int getTotalRows(String hql, Map<String, Object> maps){
        String countQueryString = " select count(*) " + removeSelect(removeOrders(hql));
        List<?> countlist = find(countQueryString, maps);
        if(countlist == null || countlist.isEmpty()){
            return 0;
        }
        return ((Long) countlist.get(0)).intValue();
    }
	
	@Override
	@SuppressWarnings("unchecked")
	public Pager<T> pagedQuery(String hql, int currentPage, int pageSize, Map<String, Object> maps) {
		int temp=pageSize;
        if(pageSize == 0){
            temp = 10;
        }
        int totalRows = getTotalRows(hql, maps);
        int totalPages = 0;
        if(totalRows % temp == 0){
            totalPages = totalRows / temp;
        }else{
            totalPages = totalRows / temp + 1;
        }
        int start = (currentPage - 1) * temp;
        if (totalPages < 1){
            return new Pager<T>();
        }
        Query query =creatQuery(hql,maps);
		List<T> list = query.setFirstResult(start).setMaxResults(temp).list();
        return new Pager<T>(totalRows, currentPage, totalPages,temp, list);
	}
	
	private String removeOrders(String hql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }
	
	private String removeSelect(String hql) {
        int beginPos = hql.toLowerCase().indexOf("from");
        return hql.substring(beginPos);
    }

	@SuppressWarnings("unchecked")
	public List<T> findBySQLQuery(String sql, Map<String, Object> parmeMap, Map<String, org.hibernate.type.Type> scalarMap) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		setParameter(query, parmeMap);
		if (null != scalarMap && !scalarMap.isEmpty()) {
			for (String key : scalarMap.keySet()) {
				query.addScalar(key, scalarMap.get(key));
			}
		}
		query.setResultTransformer(Transformers.aliasToBean(getEntityClass()));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <N> List<N> findBySQLQuery(String sql, Map<String, Object> parmeMap, Map<String, org.hibernate.type.Type> scalarMap,Class<N> clazz) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		setParameter(query, parmeMap);
		if (null != scalarMap && !scalarMap.isEmpty()) {
			for (String key : scalarMap.keySet()) {
				query.addScalar(key, scalarMap.get(key));
			}
		}
		query.setResultTransformer(Transformers.aliasToBean(clazz));
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	private void setParameter(Query query, Map<String, Object> maps) {
		if (null != maps && !maps.isEmpty()) {
			Object obj;
			for (String key : maps.keySet()) {
				obj = maps.get(key);
				if (obj instanceof Collection) {
					query.setParameterList(key, (Collection<Object>) obj);
				} else if (obj.getClass().isArray()) {
					query.setParameterList(key, Arrays.asList((Object[]) obj));
				} else {
					query.setParameter(key, maps.get(key));
				}
			}
		}
	}
	
	@Override
	public List<T> findInPropertys(String property,String values) {
		String hql="SELECT model FROM "+entityClass.getSimpleName()+" model WHERE model."+property+" IN ("+values+")";
		return find(hql);
	}
	
	/**
	 * @date 2017-5-24
	 * @author YBF
	 * @param sqls
	 * <p>批处理执行原生态sql</p>
	 */
	@Override
	public void importSQL(String... sqls){
		SessionImpl session=(SessionImpl)getSession();
		Connection connection = session.connection();
	    try {
			Statement statement = connection.createStatement();
			for (String sql : sqls) {
				statement.addBatch(sql);
			}
			statement.executeBatch();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void importSQL(Collection<String> sqls){
		SessionImpl session=(SessionImpl)getSession();
		Connection connection = session.connection();
	    try {
			Statement statement = connection.createStatement();
			for (String sql : sqls) {
				statement.addBatch(sql);
			}
			statement.executeBatch();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Integer findMaxSeq(String field,Object... values){
    	String hql="SELECT MAX(model."+field+") FROM "+entityClass.getSimpleName()+" model ";
		Integer maxOrder=getUniqueResult(hql);
		return maxOrder==null?0:maxOrder;
    }
    
	@Override
    public Integer findMaxSeqByPfield(String seqField,String pField,Integer pValue) {
		String hql="SELECT MAX(model."+seqField+") FROM "+entityClass.getSimpleName()+" model WHERE model."+pField+"=? ";
		Integer maxOrder=getUniqueResult(hql,pValue);
		return maxOrder==null?0:maxOrder;
	}
    
	@SuppressWarnings("unchecked")
	public <N> N getUniqueResult(String hql, Object... values) {
		Query query =creatQuery(hql,values);
        List<Object> list = query.setFirstResult(0).setMaxResults(1).list();
        if(list.isEmpty()){
        	return null;
        }else{
        	return (N) list.get(0);
        }
	}
	
	/**
	 * @date 2017-9-22
	 * @author YBF
	 * @param ids 所有的id字符串，已逗号分割
	 * @param sqlQuery
	 * @return
	 * <p>处理ids大于1000个个数</p>
	 */
	@Override
	public <N>List<N> findByIdsToPaging(String ids,OriginalQuerior<N> sqlQuery) {
		List<N> result = new ArrayList<N>();
		if(MyStringUtil.isNotBlank(ids)){
			List<String> idList = Arrays.asList(ids.split(","));
			int lastSize=idList.size()%1000;
			int maxLength=(idList.size()-lastSize)/1000;
			for(int k=0;k<maxLength;k++){
				result.addAll(sqlQuery.query(StringBufferByCollectionUtil.convertCollection(idList.subList(k*1000, (k+1)*1000))));
			}
			result.addAll(sqlQuery.query(StringBufferByCollectionUtil.convertCollection(idList.subList(maxLength*1000, maxLength*1000+lastSize))));
		}
		return result;
	}
	
	public interface OriginalQuerior<N>{
		List<N> query(String ids);
	}

}

package com.yunwang.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunwang.dao.SysDepartMentDaoI;
import com.yunwang.model.pojo.SysDepartMent;

@Repository
public class SysDepartMentDaoImpl extends BaseDaoImpl<SysDepartMent> implements SysDepartMentDaoI{

	@Override
	public List<SysDepartMent> findDepartMentByParentId(Integer parentId) {
		String hql = "SELECT model FROM SysDepartMent model WHERE model.parentId = ? Order By model.orderNo ";
		return find(hql, parentId);
	}

	@Override
	public List<SysDepartMent> findAllDepartMent() {
		String hql ="SELECT model FROM SysDepartMent model ORDER BY model.strOrderNo ";
		return find(hql);
	}

	/**
	 * @Description: 是否有和本身的code相同的数据
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-11-2
	 */
	@Override
	public List<SysDepartMent> findByCodeAndId(String code, Integer id) {
		String hql = "SELECT model FROM SysDepartMent model WHERE model.id!=? AND model.code='"+code+"' ";
		return find(hql, id);
	}
	
}

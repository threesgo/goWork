/**
 * 
 */
package com.yunwang.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysMember;

/**
 * @author YBF
 * @date 2017-12-15
 * <p>会员</p>
 */
public interface SysMemberService {
	
	public List<SysMember> findAll();

	public Pager<SysMember> findAll(int page, int rows, JSONObject json);

	public SysMember get(Integer id);

	public void saveOrUpdate(SysMember sysMember);

	public SysMember getByWxCode(String wxCode);
		
}

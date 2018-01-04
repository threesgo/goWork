package com.yunwang.dao;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysMember;

public interface SysMemberDaoI extends BaseDaoI<SysMember>{

	Pager<SysMember> findAll(int page, int rows, JSONObject json);

	SysMember getByWxCode(String wxCode);

	SysMember getByPhoneNumber(String phoneNumber);

}

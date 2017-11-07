package com.yunwang.dao;

import net.sf.json.JSONObject;

import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysBrand;

public interface SysBrandDaoI extends BaseDaoI<SysBrand>{

	Pager<SysBrand> findList(int page, int rows, JSONObject fromObject);

}

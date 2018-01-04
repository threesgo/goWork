package com.yunwang.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysMemberDaoI;
import com.yunwang.model.page.Pager;
import com.yunwang.model.pojo.SysMember;
import com.yunwang.service.SysMemberService;


@Service
public class SysMemberServiceImpl implements SysMemberService{
	
	@Autowired
	private SysMemberDaoI sysMemberDao;

	@Override
	public List<SysMember> findAll() {
		return sysMemberDao.findAll("regTime");
	}

	@Override
	public Pager<SysMember> findAll(int page, int rows, JSONObject json) {
		return sysMemberDao.findAll(page,rows,json);
	}

	@Override
	public SysMember get(Integer id) {
		return sysMemberDao.get(SysMember.class,id);
	}

	@Override
	public void saveOrUpdate(SysMember sysMember) {
		sysMemberDao.saveOrUpdate(sysMember);
	}

	@Override
	public SysMember getByWxCode(String wxCode) {
		return sysMemberDao.getByWxCode(wxCode);
	}

	@Override
	public SysMember getByPhoneNumber(String phoneNumber) {
		return sysMemberDao.getByPhoneNumber(phoneNumber);
	}
}

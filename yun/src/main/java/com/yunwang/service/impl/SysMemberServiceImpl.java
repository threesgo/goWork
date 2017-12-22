package com.yunwang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysMemberDaoI;
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
}

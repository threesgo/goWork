package com.yunwang.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysUserDaoI;
import com.yunwang.service.InitService;
import com.yunwang.util.MyIOUtils;
import com.yunwang.util.string.MyStringUtil;
import com.yunwang.util.string.SecurityUtil;

@Service
public class InitServiceImpl implements InitService{
	@Autowired
	private SysUserDaoI sysUserDao;
	
	public void initData() {
		//初始化系统默认数据（用户配置，工时卡类型、工时卡结构）
		execuSysSql();
	}
	
	
	private void execuSysSql(){
//		try {
//			Map<String,Object> context=new HashMap<String, Object>();
//			handleBatchSqls("classpath*:initSql/sys_*.sql",context);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private void handleBatchSqls(String locationPattern, Map<String, Object> context) throws IOException {
		Resource[] resources=MyIOUtils.getSysResources(locationPattern);
		for (Resource resource : resources) {
			String sql = MyIOUtils.fileToString(resource.getInputStream());
			if(MyStringUtil.isNotBlank(sql)){
				sysUserDao.importSQL(MyStringUtil.clearNote(sql));
			}
		}
	}
}

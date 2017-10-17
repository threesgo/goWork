package com.yunwang.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunwang.dao.SysMenuDaoI;
import com.yunwang.dao.SysRoleDaoI;
import com.yunwang.dao.SysRoleMenuDaoI;
import com.yunwang.model.pojo.SysMenu;
import com.yunwang.model.pojo.SysRole;
import com.yunwang.model.pojo.SysRoleMenu;
import com.yunwang.service.SysMenuService;


@Service
public class SysMenuServiceImpl implements SysMenuService{
	
	@Autowired
	private SysMenuDaoI sysMenuDao;
	
	@Autowired
	private SysRoleMenuDaoI sysRoleMenuDao;
	
	@Autowired
	private SysRoleDaoI sysRoleDao;

	/**
	 * @Description: 查询所有菜单
	 * @param   
	 * @return  
	 * @throws
	 * @author KXL
	 * @date 2017-10-17
	 */
	@Override
	public List<SysMenu> findAll() {
		return sysMenuDao.findAll();
	}

	@Override
	public List<Object> findMenuByRoleId(Integer roleId) {
		
		List<Object> modules_list = new ArrayList<Object>();
		//角色对应模块关系
		List<SysRoleMenu> relRoleMenuList = sysRoleMenuDao.findByRoleId(roleId);
		//List<SysRoleModule> relRoleModuleList = sysMenuDao.findByRoleId(roleId);
		Map<Integer, Object> relRoleMenuMap = null;
		if(relRoleMenuList != null && !relRoleMenuList.isEmpty()){
			relRoleMenuMap = new HashMap<Integer, Object>();
			for(SysRoleMenu sysRoleMenu : relRoleMenuList){
				relRoleMenuMap.put(sysRoleMenu.getMenuId(), null);
			}
		}
		//查询所有的菜单模块信息
		//List<SysModule> menuRoleList = sysModuleDao.findModulesByViewType(new BigDecimal(1));
		//List<SysModule> menuRoleList = sysModuleDao.findAll();
		//SysRole sysRole = sysRoleDao.get(SysRole.class,roleId);
		
		List<SysMenu> menuList = sysMenuDao.findAll();
		//List<SysModule> menuRoleList = sysModuleDao.findByMajorId(sysRole.getMajorId());
		if(menuList!=null && !menuList.isEmpty()){
			Map<Integer,List<SysMenu>> mapRefMenus = getAllRefModuleMap(menuList);
			if(mapRefMenus!=null && !mapRefMenus.isEmpty()){
				//查询第一级菜单
				List<SysMenu> topList = mapRefMenus.get(0);
				for(SysMenu sysModule : topList){
					modules_list.add(getChildList(sysModule, mapRefMenus,relRoleMenuMap));
				}
			}
		}
		return modules_list;
	}
	
	/**
	 * <p>设置所有权限父子关系</p>
	 * @return
	 */
	public Map<Integer,List<SysMenu>> getAllRefModuleMap(List<SysMenu> allMenu){
		Map<Integer,List<SysMenu>> mapRefMenus = new HashMap<Integer, List<SysMenu>>();
		if(allMenu!=null && !allMenu.isEmpty()){
			for(SysMenu sysMenu : allMenu){
				Integer key = sysMenu.getParentId();
				if(key!=null && mapRefMenus.containsKey(key)){
					List<SysMenu> childList = mapRefMenus.get(key);
					if(childList == null){
						childList = new ArrayList<SysMenu>();
					}
					childList.add(sysMenu);
					mapRefMenus.put(key, childList);
				}else if(key!=null){
					List<SysMenu> childList = new ArrayList<SysMenu>();
					childList.add(sysMenu);
					mapRefMenus.put(key, childList);
				}
			}
		}
		return mapRefMenus;
	}
		
	
	private Map<String, Object> getChildList(SysMenu menu,Map<Integer,List<SysMenu>> mapRefModules,Map<Integer, Object> relRoleModuleMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> cmap = new HashMap<String, Object>();
		Integer menuId = menu.getId();
		map.put("id", menuId);
		map.put("text",menu.getName());
		cmap.put("parentid", menu.getParentId());
		cmap.put("value",menuId);
		cmap.put("moduleType",menu.getViewType());
		map.put("attributes", cmap);
		/*if(relRoleModuleMap!=null && relRoleModuleMap.containsKey(module.getId())){
			map.put("checked",true);
			//map.put("checked","indeterminate");
			//map.put("indeterminate",true);
		}*/
		//获取子集模块
		List<SysMenu> list = mapRefModules.get(menuId);
		if (list!=null && list.size() > 0) {
			List<Object> module_list = new ArrayList<Object>();
			for (SysMenu s : list) {
				module_list.add(getChildList(s,mapRefModules,relRoleModuleMap));
			}
			map.put("children", module_list);
		}else{
			//没有子集的选中
			if(relRoleModuleMap!=null && relRoleModuleMap.containsKey(menu.getId())){
				map.put("checked",true);
			}
		}
		return map;
	}


}

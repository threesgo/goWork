BEGIN
delete FROM sys_user t WHERE t.id = 1;
insert into sys_user (id,row_version,user_name,real_name,password,create_date)
values (1, 0,'admin','admin','21232F297A57A5A743894A0E4A801FC3',sysdate);
delete FROM sys_role t WHERE t.ID = 1;
insert into sys_role (id,row_version,name,iconcls)
values (1, 0, '系统管理员', '');
delete FROM sys_user_role t WHERE t.user_id = 1;
insert into sys_user_role (id,row_version,is_default,user_id,role_id)
values (1, 0, 1, 1 ,1);
delete FROM sys_menu;
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(1,'用户管理','','status_online',0,1,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(2,'角色管理','sysRoleAction.act','status_online',1,1,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(27,'查看','list','status_online',2,4,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(18,'新增角色','sysRoleActionAdd','status_online',2,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(19,'编辑角色','sysRoleActionEdit','status_online',2,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(20,'删除','sysRoleActionDelete','status_online',2,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(30,'保存菜单','sysRoleActionSave','status_online',2,5,2,1);


insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(3,'用户管理','sysUserAction.act','status_online',1,2,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(28,'查看','list','status_online',3,4,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(21,'新增','sysUserActionAdd','status_online',3,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(22,'编辑','sysUserActionEdit','status_online',3,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(23,'删除','sysUserActionDelete','status_online',3,4,3,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(4,'部门管理','sysDepartMentAction.act','status_online',1,3,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(29,'查看','list','status_online',4,4,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(24,'新增','sysDepartMentActionAdd','status_online',4,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(25,'编辑','sysDepartMentActionEdit','status_online',4,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(26,'删除','sysDepartMentActionDelete','status_online',4,4,3,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(5,'产品管理','','cog_edit',0,2,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(6,'类别管理','resourceTypeAction.act','cog_edit',5,1,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(7,'产品管理','resourceAction.act','cog_edit',5,2,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(8,'供应商管理','sysSupplierAction.act','cog_edit',5,3,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type)
values(31,'品牌管理','sysBrandAction.act','cog_edit',5,4,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(9,'工人管理','sysWorkerAction.act','cog_edit',5,5,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(10,'套餐管理','resourcePackageAction.act','cog_edit',5,6,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(11,'订单管理','','book_edit',0,4,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(12,'订单编辑','sysOrderAction.act','book_edit',11,1,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(13,'订单流程','sysOrderAction!manageIndex.act','book_edit',11,2,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(14,'流程管理','','door_in',0,3,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(15,'流程管理','processFlowAction.act','door_in',14,1,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(16,'报表管理','','chart_bar',0,5,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(17,'报表管理','charAction.act','chart_bar',16,1,1,1);

delete FROM sys_role_menu t WHERE t.role_id = 1;
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (1, 0, 1 ,1);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (2, 0, 1 ,2);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (3, 0, 1 ,3);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (4, 0, 1 ,4);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (5, 0, 1 ,5);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (6, 0, 1 ,6);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (7, 0, 1 ,7);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (8, 0, 1 ,8);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (9, 0, 1 ,9);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (10, 0, 1 ,10);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (11, 0, 1 ,11);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (12, 0, 1 ,12);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (13, 0, 1 ,13);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (14, 0, 1 ,14);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (15, 0, 1 ,15);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (16, 0, 1 ,16);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (17, 0, 1 ,17);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (18, 0, 1 ,27);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (19, 0, 1 ,28);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (20, 0, 1 ,29);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (21, 0, 1 ,31);
END;
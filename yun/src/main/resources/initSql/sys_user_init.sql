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
values(20,'删除角色','sysRoleActionDelete','status_online',2,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(30,'保存菜单','sysRoleActionSave','status_online',2,5,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(3,'用户管理','sysUserAction.act','status_online',1,2,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(28,'查看','list','status_online',3,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(21,'新增','sysUserActionAdd','status_online',3,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(22,'编辑','sysUserActionEdit','status_online',3,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(23,'删除','sysUserActionDelete','status_online',3,4,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(4,'部门管理','sysDepartMentAction.act','status_online',1,3,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(29,'查看','list','status_online',4,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(24,'新增部门','sysDepartMentActionAddDepart','status_online',4,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(25,'编辑部门','sysDepartMentActionEditDepart','status_online',4,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(26,'删除部门','sysDepartMentActionDeleteDepart','status_online',4,4,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(31,'新增职位','sysDepartMentActionAddPos','status_online',4,5,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(32,'编辑职位','sysDepartMentActionEditPos','status_online',4,6,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(33,'删除职位','sysDepartMentActionDeletePos','status_online',4,7,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(5,'产品管理','','cog_edit',0,2,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(6,'类别管理','resourceTypeAction.act','cog_edit',5,1,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(35,'查看','list','status_online',6,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(36,'新增','resourceTypeActionAdd','status_online',6,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(37,'编辑','resourceTypeActionEdit','status_online',6,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(38,'删除','resourceTypeActionDelete','status_online',6,4,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(39,'新增属性','resourceTypeActionAddAttr','status_online',6,5,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(40,'编辑属性','resourceTypeActionEditAttr','status_online',6,6,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(41,'删除属性','resourceTypeActionDeleteAttr','status_online',6,7,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(7,'产品管理','resourceAction.act','cog_edit',5,2,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(42,'查看','list','status_online',7,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(43,'新增','resourceActionAdd','status_online',7,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(44,'编辑','resourceActionEdit','status_online',7,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(45,'删除','resourceActionDelete','status_online',7,4,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(46,'导入','resourceActionImport','status_online',7,5,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(47,'导出','resourceActionExport','status_online',7,6,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(48,'发布','resourceActionRelease','status_online',7,7,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(8,'供应商管理','sysSupplierAction.act','cog_edit',5,3,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(49,'查看','list','status_online',8,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(50,'新增','sysSupplierActionAdd','status_online',8,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(51,'编辑','sysSupplierActionEdit','status_online',8,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(52,'删除','sysSupplierActionDelete','status_online',8,4,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(53,'关联类别','sysSupplierActionRelation','status_online',8,5,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type)
values(34,'品牌管理','sysBrandAction.act','cog_edit',5,4,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(54,'查看','list','status_online',34,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(55,'新增','sysBrandActionAdd','status_online',34,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(56,'编辑','sysBrandActionEdit','status_online',34,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(57,'删除','sysBrandActionDelete','status_online',34,4,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(58,'关联类别','sysBrandActionRelation','status_online',34,5,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(9,'工人管理','sysWorkerAction.act','cog_edit',5,5,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(59,'查看','list','status_online',9,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(60,'新增','sysWorkerActionAdd','status_online',9,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(61,'编辑','sysWorkerActionEdit','status_online',9,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(62,'删除','sysWorkerActionDelete','status_online',9,4,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(10,'套餐管理','resourcePackageAction.act','cog_edit',5,6,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(63,'查看','list','status_online',10,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(64,'新增','resourcePackageActionAdd','status_online',10,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(65,'编辑','resourcePackageActionEdit','status_online',10,3,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(66,'删除','resourcePackageActionDelete','status_online',10,4,2,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(11,'订单管理','','book_edit',0,4,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(12,'订单编辑','sysOrderAction.act','book_edit',11,1,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(67,'查看','list','status_online',12,1,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(68,'新增','sysOrderActionAdd','status_online',12,2,2,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(69,'编辑','sysOrderActionEdit','status_online',12,3,2,1);

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


insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(70,'会员管理','','status_online',0,6,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(71,'会员管理','sysMemberAction.act','status_online',70,1,1,1);


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
values (21, 0, 1 ,34);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (22, 0, 1 ,35);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (23, 0, 1 ,30);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (24, 0, 1 ,42);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (25, 0, 1 ,49);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (26, 0, 1 ,54);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (27, 0, 1 ,59);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (28, 0, 1 ,63);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (29, 0, 1 ,67);

insert into sys_role_menu (id,row_version,role_id,menu_id)
values (30, 0, 1 ,18);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (31, 0, 1 ,19);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (32, 0, 1 ,20);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (33, 0, 1 ,21);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (34, 0, 1 ,22);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (35, 0, 1 ,23);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (36, 0, 1 ,24);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (37, 0, 1 ,25);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (38, 0, 1 ,26);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (39, 0, 1 ,31);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (40, 0, 1 ,32);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (41, 0, 1 ,33);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (42, 0, 1 ,36);

insert into sys_role_menu (id,row_version,role_id,menu_id)
values (43, 0, 1 ,37);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (44, 0, 1 ,38);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (45, 0, 1 ,39);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (46, 0, 1 ,40);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (47, 0, 1 ,41);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (48, 0, 1 ,43);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (49, 0, 1 ,44);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (50, 0, 1 ,45);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (51, 0, 1 ,46);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (52, 0, 1 ,47);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (53, 0, 1 ,48);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (54, 0, 1 ,50);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (55, 0, 1 ,51);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (56, 0, 1 ,52);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (57, 0, 1 ,53);

insert into sys_role_menu (id,row_version,role_id,menu_id)
values (58, 0, 1 ,55);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (59, 0, 1 ,56);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (60, 0, 1 ,57);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (61, 0, 1 ,58);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (62, 0, 1 ,60);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (63, 0, 1 ,61);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (64, 0, 1 ,62);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (65, 0, 1 ,64);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (66, 0, 1 ,65);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (67, 0, 1 ,66);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (68, 0, 1 ,68);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (69, 0, 1 ,69);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (70, 0, 1 ,70);
insert into sys_role_menu (id,row_version,role_id,menu_id)
values (71, 0, 1 ,71);

END;

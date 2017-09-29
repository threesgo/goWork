﻿BEGIN
delete sys_user t WHERE t.id = 1;
insert into sys_user (id,row_version,user_name,password,create_date)
values (1, 0,'admin','21232F297A57A5A743894A0E4A801FC3',sysdate);

delete sys_role t WHERE t.ID = 1;
insert into sys_role (id,row_version,name,iconcls)
values (1, 0, '系统管理员', '');

delete sys_user_role t WHERE t.id = 1;
insert into sys_user_role (id,row_version,is_default,user_id,role_id)
values (1, 0, 1, 1 ,1);

delete sys_menu;
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(1,'用户管理','','icon-user',0,1,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(2,'角色管理','sysRoleAction.act','icon-user',1,1,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(3,'用户管理','sysUserAction.act','icon-user',1,2,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(4,'产品管理','','icon-resource',0,2,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(5,'类别管理','resourceTypeAction.act','icon-resource',4,1,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(6,'产品管理','resourceAction.act','icon-resource',4,2,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(7,'流程管理','','door_in',0,3,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(8,'流程管理','processFlowAction.act','door_in',7,1,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(9,'订单管理','','book_edit',0,4,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(10,'订单管理','orderAction.act','book_edit',9,1,1,1);

insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(11,'报表管理','','chart_bar',0,5,1,1);
insert into sys_menu(id,name,url,iconcls,parent_id,order_no,view_type,auth_type) 
values(12,'报表管理','orderAction.act','chart_bar',11,1,1,1);

delete sys_role_menu t WHERE t.id in (1,2,3,4,5,6,7,8,9,10,11,12);
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
END
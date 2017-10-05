BEGIN
DELETE SYS_RSRC_BASEDATA;
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (1, 0, 1, '字符串', null, 'string');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (2, 0, 1, '数值型', null, 'number');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (3, 0, 1, '时间型', null, 'date');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (4, 0, 2, '度', '角度', 'DEGREE');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (5, 0, 2, '弧度', '角度', 'RAD');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (6, 0, 2, '百分度', '角度', 'GON');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (7, 0, 2, '赫兹', '频率', 'HZ');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (8, 0, 2, '吉赫', '频率', 'GHZ');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (9, 0, 2, '千赫', '频率', 'KHZ');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (10, 0, 2, '兆赫', '频率', 'MHZ');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (11, 0, 2, '百分比', '百分比', '%');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (12, 0, 2, '伏', '电压', 'V');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (13, 0, 2, '千伏', '电压', 'KV');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (14, 0, 2, '毫伏', '电压', 'mV');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (15, 0, 2, '度每分钟', '角速度', 'DEG/M');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (16, 0, 2, '度每秒', '角速度', 'DEG/S');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (17, 0, 2, '弧度每分钟', '角速度', 'RAD/M');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (18, 0, 2, '弧度每秒', '角速度', 'RAD/S');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (19, 0, 2, '转每分钟', '角速度', 'TURNS/M');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (20, 0, 2, '转每秒', '角速度', 'TURNS/S');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (21, 0, 2, '牛米', '扭矩', 'NM');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (22, 0, 2, '元', '货币', 'CNY');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (23, 0, 2, '美元', '货币', 'USD');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (24, 0, 2, '欧元', '货币', 'EUR');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (25, 0, 2, '港币', '货币', 'HKD');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (26, 0, 2, '日元', '货币', 'JPY');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (27, 0, 2, '英镑', '货币', 'GBP');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (28, 0, 2, '毫米', '长度', 'MM');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (29, 0, 2, '厘米', '长度', 'CM');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (30, 0, 2, '分米', '长度', 'DM');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (31, 0, 2, '米', '长度', 'M');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (32, 0, 2, '英寸', '长度', 'INCH');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (33, 0, 2, '英尺', '长度', 'FOOT');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (34, 0, 2, '英里', '长度', 'MILE');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (35, 0, 2, '码', '长度', 'YARD');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (36, 0, 2, '平方毫米', '面积', 'mm2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (37, 0, 2, '平方厘米', '面积', 'cm2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (38, 0, 2, '平方分米', '面积', 'dm2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (39, 0, 2, '平方米', '面积', 'm2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (40, 0, 2, '平方英尺', '面积', 'FOOT2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (41, 0, 2, '平方英寸', '面积', 'INCH2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (42, 0, 2, '平方码', '面积', 'YARD2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (43, 0, 2, '立方毫米', '体积', 'mm3');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (44, 0, 2, '立方厘米', '体积', 'cm3');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (45, 0, 2, '立方分米', '体积', 'dm3');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (46, 0, 2, '立方米', '体积', 'm3');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (47, 0, 2, '立方英尺', '体积', 'FOOT3');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (48, 0, 2, '立方英寸', '体积', 'INCH3');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (49, 0, 2, '升', '体积', 'L');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (50, 0, 2, '毫升', '体积', 'mL');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (51, 0, 2, '毫克', '质量', 'MG');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (52, 0, 2, '克', '质量', 'G');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (53, 0, 2, '千克', '质量', 'KG');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (54, 0, 2, '磅', '质量', 'POUND');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (55, 0, 2, '吨', '质量', 'TON');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (56, 0, 2, '摄氏度', '温度', '°C');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (57, 0, 2, '华氏度', '温度', '°F');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (58, 0, 2, '开氏度', '温度', 'K');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (59, 0, 2, '兰氏度', '温度', '°R');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (60, 0, 2, '列氏度', '温度', '°Re');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (61, 0, 2, '帕', '压力', 'PA');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (62, 0, 2, '千帕', '压力', 'KPA');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (63, 0, 2, '兆帕', '压力', 'MPA');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (64, 0, 2, '工程大气压', '压力', 'AT');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (65, 0, 2, '标准大气压', '压力', 'ATM');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (66, 0, 2, '巴', '压力', 'BAR');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (67, 0, 2, '千牛每平方米', '压力', 'KN/M2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (68, 0, 2, '千克力每平方厘米', '压力', 'KP/CM2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (69, 0, 2, '千克力每平方米', '压力', 'KP/M2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (70, 0, 2, '磅每平方英寸', '压力', 'LB/IN2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (71, 0, 2, '磅每平方米', '压力', 'LB/M2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (72, 0, 2, '毫巴', '压力', 'mBAR');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (73, 0, 2, '牛每平方米', '压力', 'N/M2');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (74, 0, 2, '瓦', '功率', 'WATT');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (75, 0, 2, '千瓦', '功率', 'KWATT');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (76, 0, 2, '马力', '功率', 'HORSEPOWER');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (77, 0, 2, '焦耳', '功/能/热', 'J');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (78, 0, 2, '卡', '功/能/热', 'CAL');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (79, 0, 2, '千卡', '功/能/热', 'KCAL');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (80, 0, 2, '牛', '力', 'N');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (81, 0, 2, '千牛', '力', 'KN');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (82, 0, 2, '千克力', '力', 'KP');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (83, 0, 2, '磅', '力', 'POUND');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (84, 0, 2, '年', '时间', 'y');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (85, 0, 2, '月', '时间', 'm');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (86, 0, 2, '周', '时间', 'week');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (87, 0, 2, '天', '时间', 'd');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (88, 0, 2, '时', '时间', 'h');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (89, 0, 2, '分', '时间', 'min');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (90, 0, 2, '秒', '时间', 's');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (91, 0, 2, '毫秒', '时间', 'ms');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (92, 0, 2, '毫米每秒', '速度', 'MM/S');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (93, 0, 2, '厘米每秒', '速度', 'CM/S');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (94, 0, 2, '米每秒', '速度', 'M/S');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (95, 0, 2, '千米每小时', '速度', 'KM/H');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (96, 0, 2, '英里每小时', '速度', 'MILE/H');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (97, 0, 2, '英寸每秒', '速度', 'IN/S');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (98, 0, 2, '马赫', '速度', 'MACH');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (99, 0, 2, '比特', '数据存储', 'bit');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (100, 0, 2, '字节', '数据存储', 'Byte');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (101, 0, 2, '千字节', '数据存储', 'KB');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (102, 0, 2, '兆字节', '数据存储', 'MB');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (103, 0, 2, '千兆字节', '数据存储', 'GB');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (104, 0, 3, '文本框', null, 'text');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (105, 0, 3, '单选列表', null, 'checkbox');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (106, 0, 3, '多选列表', null, 'select');
insert into SYS_RSRC_BASEDATA (id, row_version, data_type, display_name, data_group, real_name)
values (107, 0, 3, '时间选择框', null, 'datetimebox');
END;
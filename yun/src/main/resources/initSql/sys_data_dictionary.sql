BEGIN
DELETE FROM SYS_DATA_DICTIONARY;
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(1,0,1,'产品',1);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(2,0,1,'工人',2);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(3,0,2,'室',1);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(4,0,2,'厅',2);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(5,0,2,'厨',3);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(6,0,2,'卫',4);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(7,0,2,'阳',5);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(8,0,3,'东面',1);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(9,0,3,'南面',2);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(10,0,3,'西面',3);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(11,0,3,'北面',4);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(12,0,3,'地面',5);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(13,0,3,'顶面',6);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(14,0,4,'设计',1);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(15,0,4,'水电工程',2);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(16,0,4,'泥瓦工程',3);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(17,0,4,'木工工程',4);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(18,0,4,'油漆工程',5);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(19,0,4,'收尾工程',6);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(20,0,4,'住前准备',7);
insert into SYS_DATA_DICTIONARY (id,row_version,type,name,value)values(21,0,4,'其它',8);
END;
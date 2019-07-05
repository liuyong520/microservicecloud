use db;
SET STORAGE_ENGINE = InnoDB;

DROP TABLE IF EXISTS `employ`;
CREATE TABLE `employ` (
  `id` bigint(20) NOT NULL COMMENT '员工编号',
  `name` varchar(48) NOT NULL COMMENT '名字',
  `departmentId` bigint(10) NOT NULL COMMENT '部门编号',
  `datasourceId` varchar(64) NOT NULL COMMENT '数据存储位置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into employ(id,name,departmentId,datasourceId) values(1,"zhansan",1,DATABASE());
insert into employ(id,name,departmentId,datasourceId) values(2,"lisi",2,DATABASE());
insert into employ(id,name,departmentId,datasourceId) values(3,"wangwu",3,DATABASE());
insert into employ(id,name,departmentId,datasourceId) values(4,"maliu",4,DATABASE());
insert into employ(id,name,departmentId,datasourceId) values(5,"shuqi",5,DATABASE());
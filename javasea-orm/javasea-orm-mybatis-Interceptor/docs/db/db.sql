CREATE DATABASE /*!32312 IF NOT EXISTS*/`my` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `my`;

DROP TABLE IF EXISTS `t_tenant`;

CREATE TABLE `t_tenant` (
  `tenant_id` varchar(40) NOT NULL DEFAULT '0' COMMENT '租户id',
  `path` varchar(200) DEFAULT NULL COMMENT '租户和子租户路径树',
  `tenant_code` varchar(100) DEFAULT NULL COMMENT '租户编码',
  `name` varchar(50) DEFAULT NULL COMMENT '租户名称',
  `logo` varchar(255) DEFAULT NULL COMMENT '公司logo地址',
  `status` smallint(6) DEFAULT NULL COMMENT '状态1有效0无效',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(100) DEFAULT NULL COMMENT '最后修改人',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `street_address` varchar(200) DEFAULT NULL COMMENT '街道楼号地址',
  PRIMARY KEY (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户的基本信息表';

/*Data for the table `t_tenant` */

insert  into `t_tenant`(`tenant_id`,`path`,`tenant_code`,`name`,`logo`,`status`,`create_by`,`create_time`,`last_update_by`,`last_update_time`,`street_address`) values ('0','0','albb','阿里巴巴','/statics/images/headimg/default.png',1,'P0','2019-11-18 14:32:52','1','2019-11-18 14:32:52',NULL),('1','0-1','aly','阿里云','/statics/images/headimg/default.png',1,'P1','2019-11-18 15:08:30','1','2019-11-18 15:08:30',NULL),('2','0-2','myjf','蚂蚁金服','/statics/images/headimg/default.png',1,'P2','2019-06-21 15:12:28','1','2019-06-21 15:12:28',NULL),('21','0-2-21','zfb','支付宝','/statics/images/headimg/default.png',1,'P21',NULL,NULL,NULL,NULL),('211','0-2-21-211','mysl','蚂蚁森林','/statics/images/headimg/default.png',1,'P211',NULL,NULL,NULL,NULL),('2111','0-2-21-211-2111','mysm','蚂蚁树苗','/statics/images/headimg/default.png',1,'P2111',NULL,NULL,NULL,NULL),('2112','0-2-21-211-2112','etyz','ET养猪','/statics/images/headimg/default.png',1,'P2112',NULL,NULL,NULL,NULL),('212','0-2-21-212','xcx','支付宝小程序','/statics/images/headimg/default.png',1,'P212',NULL,NULL,NULL,NULL),('22','0-2-22','zcb','招财宝','/statics/images/headimg/default.png',1,'P22',NULL,NULL,NULL,NULL),('3','0-3','tm','天猫','/statics/images/headimg/default.png',1,'P3','2019-11-18 14:43:45','1','2019-11-18 14:43:45',NULL),('4','0-4','tb','淘宝','/statics/images/headimg/default.png',1,'P4',NULL,NULL,NULL,NULL);

/*Table structure for table `t_user` */
DROP TABLE IF EXISTS `t_file`;

CREATE TABLE `t_file` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名',
  `create_by` varchar(40) DEFAULT NULL COMMENT '创建者',
  `tenant_id` varchar(40) NOT NULL DEFAULT 'c12dee54f652452b88142a0267ec74b7' COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='文件';

/*Data for the table `t_file` */

insert  into `t_file`(`id`,`file_name`,`create_by`,`tenant_id`) values (16,'053 2019年员工体检的通知.docx','p211A','211'),(17,'新建文本文档.txt','p211B','211'),(18,'爬架项目初步设计信息表.xlsx','p2112A','2112'),(19,'导入明细.xlsx','p2112B','2112'),(20,'ZM-出入库详情.xlsx','p21A','21'),(21,'D商务-04爬架项目概算.xls','p1','1'),(22,'工作簿1.xlsx','p2','1'),(23,'bom清单123456.xlsx','p211B','211'),(25,'1',NULL,'c12dee54f652452b88142a0267ec74b7');

/*Table structure for table `t_tenant` */

DROP TABLE IF EXISTS `t_tenant`;

CREATE TABLE `t_tenant` (
  `tenant_id` varchar(40) NOT NULL DEFAULT '0' COMMENT '租户id',
  `path` varchar(200) DEFAULT NULL COMMENT '租户和子租户路径树',
  `tenant_code` varchar(100) DEFAULT NULL COMMENT '租户编码',
  `name` varchar(50) DEFAULT NULL COMMENT '租户名称',
  `logo` varchar(255) DEFAULT NULL COMMENT '公司logo地址',
  `status` smallint(6) DEFAULT NULL COMMENT '状态1有效0无效',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(100) DEFAULT NULL COMMENT '最后修改人',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `street_address` varchar(200) DEFAULT NULL COMMENT '街道楼号地址',
  PRIMARY KEY (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户的基本信息表';

/*Data for the table `t_tenant` */

insert  into `t_tenant`(`tenant_id`,`path`,`tenant_code`,`name`,`logo`,`status`,`create_by`,`create_time`,`last_update_by`,`last_update_time`,`street_address`) values ('0','0','albb','阿里巴巴','/statics/images/headimg/default.png',1,'P0','2019-11-18 14:32:52','1','2019-11-18 14:32:52',NULL),('1','0-1','aly','阿里云','/statics/images/headimg/default.png',1,'P1','2019-11-18 15:08:30','1','2019-11-18 15:08:30',NULL),('2','0-2','myjf','蚂蚁金服','/statics/images/headimg/default.png',1,'P2','2019-06-21 15:12:28','1','2019-06-21 15:12:28',NULL),('21','0-2-21','zfb','支付宝','/statics/images/headimg/default.png',1,'P21',NULL,NULL,NULL,NULL),('211','0-2-21-211','mysl','蚂蚁森林','/statics/images/headimg/default.png',1,'P211',NULL,NULL,NULL,NULL),('2111','0-2-21-211-2111','mysm','蚂蚁树苗','/statics/images/headimg/default.png',1,'P2111',NULL,NULL,NULL,NULL),('2112','0-2-21-211-2112','etyz','ET养猪','/statics/images/headimg/default.png',1,'P2112',NULL,NULL,NULL,NULL),('212','0-2-21-212','xcx','支付宝小程序','/statics/images/headimg/default.png',1,'P212',NULL,NULL,NULL,NULL),('22','0-2-22','zcb','招财宝','/statics/images/headimg/default.png',1,'P22',NULL,NULL,NULL,NULL),('3','0-3','tm','天猫','/statics/images/headimg/default.png',1,'P3','2019-11-18 14:43:45','1','2019-11-18 14:43:45',NULL),('4','0-4','tb','淘宝','/statics/images/headimg/default.png',1,'P4',NULL,NULL,NULL,NULL);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `tenant_id` varchar(40) NOT NULL DEFAULT 'c12dee54f652452b88142a0267ec74b7' COMMENT '租户id',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `email` varchar(30) DEFAULT NULL COMMENT '邮件',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
  `telephone` varchar(15) DEFAULT NULL COMMENT '办公电话',
  `sex` smallint(6) DEFAULT '9' COMMENT '性别（0：女，1：男 9未知）',
  PRIMARY KEY (`user_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户的用户表';

/*Data for the table `t_user` */

insert  into `t_user`(`user_id`,`tenant_id`,`name`,`email`,`mobile`,`telephone`,`sex`) values ('P0','0',NULL,NULL,NULL,NULL,1),('p1','1',NULL,NULL,NULL,NULL,2),('p2','2',NULL,NULL,NULL,NULL,1),('p21','21',NULL,NULL,NULL,NULL,1),('p2111A','2111',NULL,NULL,NULL,NULL,2),('p2112A','2112',NULL,NULL,NULL,NULL,1),('p2112B','2112',NULL,NULL,NULL,NULL,2),('p211A','211',NULL,NULL,NULL,NULL,2),('p211B','211',NULL,NULL,NULL,NULL,1),('p212','212',NULL,NULL,NULL,NULL,2),('p22','22',NULL,NULL,NULL,NULL,1),('p3','3',NULL,NULL,NULL,NULL,2),('p4','4',NULL,NULL,NULL,NULL,1);

/*Table structure for table `t_user_copy` */

DROP TABLE IF EXISTS `t_user_copy`;

CREATE TABLE `t_user_copy` (
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `tenant_id` varchar(40) NOT NULL DEFAULT 'c12dee54f652452b88142a0267ec74b7' COMMENT '租户id',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `email` varchar(30) DEFAULT NULL COMMENT '邮件',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
  `telephone` varchar(15) DEFAULT NULL COMMENT '办公电话',
  `sex` smallint(6) DEFAULT '9' COMMENT '性别（0：女，1：男 9未知）',
  PRIMARY KEY (`user_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户的用户表';

/*Data for the table `t_user_copy` */

insert  into `t_user_copy`(`user_id`,`tenant_id`,`name`,`email`,`mobile`,`telephone`,`sex`) values ('P0','0',NULL,NULL,NULL,NULL,1),('p1','1',NULL,NULL,NULL,NULL,2),('p2','2',NULL,NULL,NULL,NULL,1),('p21','21',NULL,NULL,NULL,NULL,1),('p2111A','2111',NULL,NULL,NULL,NULL,2),('p2112A','2112',NULL,NULL,NULL,NULL,1),('p2112B','2112',NULL,NULL,NULL,NULL,2),('p211A','211',NULL,NULL,NULL,NULL,2),('p211B','211',NULL,NULL,NULL,NULL,1),('p212','212',NULL,NULL,NULL,NULL,2),('p22','22',NULL,NULL,NULL,NULL,1),('p3','3',NULL,NULL,NULL,NULL,2),('p4','4',NULL,NULL,NULL,NULL,1);

/*Table structure for table `user` */

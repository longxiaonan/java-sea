CREATE TABLE `user` (
  `uid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `age` INT(11) DEFAULT NULL,
  `manage_id` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `tenant_id` VARCHAR(36) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123',17, 0, "2019-09-02 11:55:22", "abc11");
INSERT INTO `user` VALUES ('2', 'root', '123', 18, 1, "2019-09-01 11:55:22", "abc11");
INSERT INTO `user` VALUES ('3', 'long', '123', 19, 1, "2019-08-31 11:55:22", "abc11");
INSERT INTO `user` VALUES ('4', 'longxiaonan', '123',20, 3, "2019-08-30 11:55:22", "abc22");

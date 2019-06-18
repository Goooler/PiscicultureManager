CREATE TABLE `manager` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `timestamp` int(11) NOT NULL COMMENT '时间戳',
  `temperature` DOUBLE(10, 5) NOT NULL COMMENT '温度',
  `oxygen` DOUBLE(10, 5) NOT NULL COMMENT '氧含量',
  `ph` DOUBLE(10, 5) NOT NULL COMMENT '酸碱度',
  `nitrogen` DOUBLE(10, 5) NOT NULL COMMENT '氨氮含量',
  `nitrite` DOUBLE(10, 5) NOT NULL COMMENT '亚硝酸盐含量',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '渔业养殖';
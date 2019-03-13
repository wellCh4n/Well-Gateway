-- ----------------------------
-- Table structure for wl_api
-- ----------------------------
DROP TABLE IF EXISTS `wl_api`;
CREATE TABLE `wl_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL COMMENT 'API路径',
  `target` varchar(2048) DEFAULT NULL COMMENT '目标路径',
  `allow_count` int(11) DEFAULT NULL COMMENT '次数限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) NOT NULL COMMENT '权限标识：必须为英文，用于系统识别，格式：XX:XX:XX',
  `name` varchar(255) NOT NULL COMMENT '权限名称：建议为中文，用于用户识别',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `status` int(2) DEFAULT '1' COMMENT '状态：0-不可用，1-可用',
  `access_type` int(2) DEFAULT NULL COMMENT '方法访问级别权限类型：0-操作权限，1-资源权限',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统权限';
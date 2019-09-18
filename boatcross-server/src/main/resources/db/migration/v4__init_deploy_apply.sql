create table bc_deploy_apply (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(10) NOT NULL COMMENT '用户id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '钉钉审批号',
  `description` varchar(1024) DEFAULT NULL COMMENT '发版说明',
  `env` varchar(20) NOT NULL DEFAULT '' COMMENT '环境',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目部署申请';

create table bc_deploy_project_apply (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `da_id` int(10) NOT NULL COMMENT '部署申请id',
  `project` varchar(64) NOT NULL COMMENT '项目',
  `version` varchar(64) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='申请部署的项目';

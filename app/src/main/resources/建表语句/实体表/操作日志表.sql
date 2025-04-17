CREATE TABLE `operation_log` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `operate_id` int(11) DEFAULT NULL COMMENT '操作人ID',
                                 `operate_type` tinyint(4) DEFAULT '0' COMMENT '操作人类型(0未知 2管理员 1普通用户)',
                                 `ip` varchar(50) DEFAULT NULL COMMENT '操作IP地址',
                                 `operate_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
                                 `class_name` varchar(255) DEFAULT NULL COMMENT '操作类名',
                                 `method_name` varchar(100) DEFAULT NULL COMMENT '操作方法名',
                                 `method_params` text COMMENT '操作方法参数(JSON格式)',
                                 `return_value` text COMMENT '操作方法返回值(JSON格式)',
                                 `cost_time` bigint(20) DEFAULT NULL COMMENT '操作耗时(毫秒)',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_operate_id` (`operate_id`),
                                 KEY `idx_operate_time` (`operate_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表';
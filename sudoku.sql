CREATE DATABASE IF NOT EXISTS `sudoku` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `sudoku`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                int         NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`          varchar(32) NOT NULL COMMENT '用户名',
    `password`          varchar(64) NOT NULL COMMENT '密码',
    `nickname`          varchar(64) NOT NULL COMMENT '昵称',
    `create_time`       DATETIME DEFAULT NOW() COMMENT '创建时间',
    `recent_login_time` DATETIME DEFAULT NOW() COMMENT '最近登录时间',
    `enabled`           tinyint  DEFAULT 1 COMMENT '是否启用',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


INSERT INTO `user`
VALUES (1, 'test1', '$2a$10$VaphyIrQ7C9aELKTx/Wh1.QqGVvBymhd57NrY/OoQhuAjMgNMoEO6', '测试管理员', NOW(), NOW(), 1),
       (2, 'test2', '$2a$10$VaphyIrQ7C9aELKTx/Wh1.QqGVvBymhd57NrY/OoQhuAjMgNMoEO6', '测试用户', NOW(), NOW(), 1);


DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`      int NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
    `user_id` int NOT NULL COMMENT '用户ID',
    `role_id` int NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `user_role`
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 1);


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`      int         NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`    varchar(32) NOT NULL COMMENT '角色名',
    `name_zh` varchar(64) DEFAULT NULL COMMENT '角色名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `role`
VALUES (1, 'ROLE_USER', '用户'),
       (2, 'ROLE_ADMIN', '管理员');


DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`
(
    `id`      int         NOT NULL AUTO_INCREMENT COMMENT '资源ID',
    `perms`   varchar(64) NOT NULL COMMENT '权限标识',
    `name_zh` varchar(64) NOT NULL COMMENT '资源名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `resource`
VALUES (1, 'sudoku:level:list', '数独等级列表'),
       (2, 'sudoku:game:generator', '数独游戏生成'),
       (3, 'sudoku:game:help', '数独游戏提示'),
       (4, 'sudoku:game:check', '数独游戏检查'),
       (5, 'sudoku:rank:list', '数独排行列表'),
       (6, 'sudoku:user:information', '数独用户信息'),
       (7, 'sudoku:user:record', '数独用户游戏记录'),
       (8, 'system:user:add', '用户新增'),
       (9, 'statistics:user:list', '用户统计信息列表')
;


DROP TABLE IF EXISTS `resource_role`;
CREATE TABLE `resource_role`
(
    `id`          int NOT NULL AUTO_INCREMENT COMMENT '资源角色ID',
    `resource_id` int NOT NULL COMMENT '资源ID',
    `role_id`     int NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `resource_role`
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1),
       (4, 4, 1),
       (5, 5, 1),
       (6, 6, 1),
       (7, 7, 1),
       (8, 8, 2),
       (16, 9, 2),
       (9, 1, 2),
       (10, 2, 2),
       (11, 3, 2),
       (12, 4, 2),
       (13, 5, 2),
       (14, 6, 2),
       (15, 7, 2);


DROP TABLE IF EXISTS `sudoku_level`;
CREATE TABLE `sudoku_level`
(
    `id`        int         NOT NULL AUTO_INCREMENT COMMENT '数独难度ID',
    `level`     tinyint DEFAULT 0 COMMENT '难度级别',
    `name`      VARCHAR(64) NOT NULL COMMENT '难度名',
    `min_empty` tinyint DEFAULT 35 COMMENT '最小的空缺格子数',
    `max_empty` tinyint DEFAULT 35 COMMENT '最大的空缺格子数',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `sudoku_level`
VALUES (1, 0, '简单模式', 30, 35),
       (2, 1, '普通模式', 35, 40),
       (3, 2, '困难模式', 40, 45),
       (4, 3, '困难+模式', 45, 50);


DROP TABLE IF EXISTS `user_game_information`;
CREATE TABLE `user_game_information`
(
    `id`                 int NOT NULL AUTO_INCREMENT COMMENT '用户游戏信息的ID',
    `total`              int DEFAULT 0 COMMENT '提交的次数',
    `correct_number`     int DEFAULT 0 COMMENT '提交正确的次数',
    `average_spend_time` int DEFAULT NULL COMMENT '平均用时',
    `min_spend_time`     int DEFAULT NULL COMMENT '最短用时',
    `max_spend_time`     int DEFAULT NULL COMMENT '最长用时',
    `user_id`            int NOT NULL COMMENT '用户ID',
    `sudoku_level_id`    int NOT NULL COMMENT '数独等级ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `user_game_information`
VALUES (1, 1, 1, 600000, 600000, 600000, 1, 1),
       (2, 0, 0, null, null, null, 1, 2),
       (3, 0, 0, null, null, null, 1, 3),
       (4, 0, 0, null, null, null, 1, 4),
       (5, 0, 0, null, null, null, 2, 1),
       (6, 0, 0, null, null, null, 2, 2),
       (7, 0, 0, null, null, null, 2, 3),
       (8, 0, 0, null, null, null, 2, 4);


DROP TABLE IF EXISTS `game_record`;
CREATE TABLE `game_record`
(
    `id`              int NOT NULL AUTO_INCREMENT COMMENT '游戏记录的ID',
    `sudoku_matrix`   char(81) DEFAULT NULL COMMENT '数独矩阵',
    `sudoku_holes`    char(81) DEFAULT NULL COMMENT '空缺的数独',
    `start_time`      DATETIME DEFAULT NULL COMMENT '开始时间',
    `end_time`        DATETIME DEFAULT NULL COMMENT '结束时间',
    `correct`         tinyint  DEFAULT 0 COMMENT '回答是否正确',
    `sudoku_level_id` int NOT NULL COMMENT '数独难度ID',
    `user_id`         int NOT NULL COMMENT '用户ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `game_record`
VALUES (1, '239187465586394127471625398942761583318459276657832914894516732723948651165273849',
        '010100110110111000011011100110110001100100100110000000000011000000001000100101000', '2020-05-24 22:00:00', '2020-05-24
22:10:00', 1, 1, 1);


DROP TABLE IF EXISTS `statistics_user`;
CREATE TABLE `statistics_user`
(
    `id`                int         NOT NULL AUTO_INCREMENT COMMENT '用户统计数据的ID',
    `user_total`        int DEFAULT 0 COMMENT '用户总数',
    `user_active_total` int DEFAULT 0 COMMENT '用户活跃总数',
    `date_name`         VARCHAR(16) NOT NULL COMMENT '统计日期类型的名字',
    `date`              DATETIME    NOT NULL COMMENT '统计的日期',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `statistics_game`;
CREATE TABLE `statistics_game`
(
    `id`              int         NOT NULL AUTO_INCREMENT COMMENT '游戏统计数据的ID',
    `correct_total`   int DEFAULT 0 COMMENT '提交正确的总数',
    `error_total`     int DEFAULT 0 COMMENT '提交错误的总数',
    `sudoku_level_id` int         NOT NULL COMMENT '数独等级ID',
    `date_name`       VARCHAR(16) NOT NULL COMMENT '统计日期类型的名字',
    `date`            DATETIME    NOT NULL COMMENT '统计的日期',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


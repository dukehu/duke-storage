/*
 Navicat Premium Data Transfer

 Source Server         : dukehu
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : dukehu.top:3306
 Source Schema         : duke_storage

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 29/10/2020 10:02:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pdf2word_record
-- ----------------------------
DROP TABLE IF EXISTS `pdf2word_record`;
CREATE TABLE `pdf2word_record`
(
    `id`               varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `name`             varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '名称',
    `file_id`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '附件id',
    `transform_time`   datetime(0)                                             NOT NULL COMMENT '转换时间',
    `take_up_time`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '转换耗时',
    `transformed_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '转换之后文件存储位置',
    `user_id`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

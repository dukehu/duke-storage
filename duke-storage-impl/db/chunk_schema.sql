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

 Date: 22/10/2020 11:01:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chunk
-- ----------------------------
DROP TABLE IF EXISTS `chunk`;
CREATE TABLE `chunk`
(
    `id`                 varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `chunk_number`       int(11)                                                 NOT NULL COMMENT '当前文件块，从1开始',
    `chunk_size`         bigint(20)                                              NOT NULL COMMENT '分块大小',
    `current_chunk_size` bigint(20)                                              NOT NULL COMMENT '当前分块大小',
    `total_size`         bigint(20)                                              NOT NULL COMMENT '文件总大小',
    `total_chunks`       int(11)                                                 NOT NULL COMMENT '总块数',
    `path`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路径',
    `md5`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件标识',
    `file_name`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

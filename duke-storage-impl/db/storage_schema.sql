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

 Date: 22/10/2020 10:55:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`
(
    `id`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `name`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名',
    `suffix`      varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '文件后缀',
    `service_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '服务id',
    `path`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件存储路径',
    `size`        int(20)                                                 NOT NULL COMMENT '文件大小',
    `status`      int(1)                                                  NOT NULL COMMENT '状态',
    `user_id`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '上传用户',
    `type`        int(1)                                                  NOT NULL COMMENT '文档，视频，音频，图片，其他',
    `upload_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '上传时间',
    `delete_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '删除时间',
    `md5`         varchar(50) CHARACTER SET utf8 COLLATE utf8_bin         NULL DEFAULT NULL COMMENT '文件md5值',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '文件存储表'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

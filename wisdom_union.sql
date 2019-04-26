/*
Navicat MySQL Data Transfer

Source Server         : jason
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : wisdom_union

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-04-26 08:29:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sub_user`
-- ----------------------------
DROP TABLE IF EXISTS `sub_user`;
CREATE TABLE `sub_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑关联user表id',
  `sub_user_name` varchar(100) NOT NULL COMMENT '小号名',
  `terminal` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '终端类型(0：电脑端，1：手机端....)',
  `user_type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '小号类型(0:淘宝，1：京东，2：拼多多...)',
  `connect_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '连接状态(0:未连接，1：已连接)',
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sub_user
-- ----------------------------
INSERT INTO `sub_user` VALUES ('7', '5', 'j4', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('9', '5', 'j5-update', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('10', '7', 'c1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('11', '7', 'c2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('12', '7', 'c3', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('14', '8', 'm2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('15', '8', 'm3', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('16', '9', 'w1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('17', '9', 'w2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('18', '10', 'a1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('19', '10', 'a2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('20', '11', 'b1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('21', '11', 'b2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('22', '5', 'j6', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('23', '21', 'E1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('24', '21', 'E2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('25', '15', 'Jocey1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('26', '15', 'Jocey2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('27', '15', 'Jocey3', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('28', '22', 'a1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('30', '22', 'a3', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('31', '23', 'b1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('32', '23', 'b2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('33', '23', 'b3', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('34', '24', 'p1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('35', '24', 'p2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('36', '24', 'p3', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('37', '24', 'p4', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('38', '24', 'p5', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('39', '25', 'h1', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('40', '25', 'h2', '0', '0', '0');
INSERT INTO `sub_user` VALUES ('41', '25', 'h3', '0', '0', '0');

-- ----------------------------
-- Table structure for `sub_user_task`
-- ----------------------------
DROP TABLE IF EXISTS `sub_user_task`;
CREATE TABLE `sub_user_task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sub_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑关联sub_user表id',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑关联user_shop表id',
  `description` varchar(500) NOT NULL COMMENT '备注描述',
  `create_time` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `create_time_idx` (`create_time`),
  KEY `update_time_idx` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sub_user_task
-- ----------------------------

-- ----------------------------
-- Table structure for `task_replace_order`
-- ----------------------------
DROP TABLE IF EXISTS `task_replace_order`;
CREATE TABLE `task_replace_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑关联sub_user_task表id',
  `order_id` varchar(20) NOT NULL COMMENT '淘宝订单id',
  `order_amount` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '订单金额',
  `order_commission` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '订单回扣',
  `order_type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '订单类型(0：电脑单，1：手机单....)',
  `payment_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '垫付状态(0：未垫付，1：已垫付，2：已返款)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_replace_order
-- ----------------------------

-- ----------------------------
-- Table structure for `task_tag`
-- ----------------------------
DROP TABLE IF EXISTS `task_tag`;
CREATE TABLE `task_tag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_id` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑关联sub_user_task表id',
  `tag_type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '标签类型(0：收藏，1：加购，2：已拍，3：聊天，4：足迹)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_tag
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(20) NOT NULL DEFAULT '123456' COMMENT '密码',
  `sex` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '性别（0：男，1：女）',
  `age` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '年龄',
  `location` varchar(50) NOT NULL DEFAULT '' COMMENT '位置',
  `credit_values` tinyint(3) unsigned NOT NULL DEFAULT '100' COMMENT '信用值',
  `payment_code_img` varchar(100) NOT NULL DEFAULT '' COMMENT '付款码图片路径',
  `qq_number` varchar(20) NOT NULL DEFAULT '' COMMENT 'QQ号',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `permission` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '权限(0：超级管理员，1：普通智慧联盟账号)',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '账号状态(0：正常，1：禁用，2：删除,3：待审核)',
  `allow_order_times` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT '账号每日刷单上限次数',
  `valid_time` bigint(20) unsigned DEFAULT '0' COMMENT '有效时间',
  `connect_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '连接状态(0:未连接，1：已连接)',
  `invite_code` varchar(32) DEFAULT NULL COMMENT '邀请码',
  `referrer_user_id` int(11) DEFAULT NULL COMMENT '推荐人id',
  PRIMARY KEY (`id`),
  KEY `user_name_idx` (`user_name`),
  KEY `location_idx` (`location`),
  KEY `qq_number_idx` (`qq_number`),
  KEY `mobile_idx` (`mobile`),
  KEY `status_idx` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', '0', '26', '四川成都', '100', '/images/1.jpg', '978504716', '15883283040', '0', '0', '2', '4702007530000', '0', 'admin', '15');
INSERT INTO `user` VALUES ('3', 'admin', '12345', '0', '21', '四川成都', '100', '/images/3.png', '1093012726', '13533812648', '1', '0', '2', '1577721600000', '0', '231243', '15');
INSERT INTO `user` VALUES ('4', 'Jason1', '13', '0', '21', '四川成都', '100', '/images/4.png', '1093012726', '13533812648', '1', '2', '2', '1577721600000', '0', '343', '15');
INSERT INTO `user` VALUES ('5', 'JasonMei', '123456', '0', '30', '四川成都高新区', '100', '/images/5.png', '1093012722', '15883283041', '1', '0', '10', '1627747200000', '0', 'ejkdflaweio', '15');
INSERT INTO `user` VALUES ('6', 'Jason1', '145', '0', '21', '四川成都', '100', '/images/6.png', '1093012726', '13533812648', '1', '2', '10', '1577721600000', '0', '24jfjaewfji', '15');
INSERT INTO `user` VALUES ('7', 'Chris', '123456', '0', '29', '三环内', '100', '/images/7.png', '10890', '13909201097', '1', '0', '2', '1556035200000', '0', 'dfaew', '15');
INSERT INTO `user` VALUES ('8', 'Marry', '123456', '1', '30', '环球', '100', '/images/8.png', '80980', '15733810980', '1', '0', '4', '1565366400000', '0', 'fretg', '15');
INSERT INTO `user` VALUES ('9', 'William', '123456', '0', '29', 'wc', '100', '/images/9.png', '34567', '13609245678', '1', '0', '2', '1575388800000', '0', 'sevvgt', '15');
INSERT INTO `user` VALUES ('10', 'Arron', '123456', '0', '22', 'brother\'s home', '100', '/images/10.png', '28908', '18709809090', '1', '0', '3', '1585843200000', '0', 'dfetfgg', '15');
INSERT INTO `user` VALUES ('11', 'Bluce', '123456', '0', '26', '更高的平台', '100', '/images/11.png', '28907', '17809203021', '1', '0', '2', '1556380800000', '0', 'wqe345', '15');
INSERT INTO `user` VALUES ('12', 'Tomes', '123456', '0', '30', '犀浦', '100', '', '289701', '13533812642', '1', '0', '5', '1569254400000', '0', 'fdayu6322', '15');
INSERT INTO `user` VALUES ('13', 'Elio', '123456', '0', '20', '武侯区三大队', '100', '', '82460', '17809238320', '1', '0', '2', '1556553600000', '0', 'dfawerfvrwe', '15');
INSERT INTO `user` VALUES ('14', 'Ashley', '123456', '1', '26', '地铁', '100', '', '84707', '13325082304', '1', '0', '2', '1663948800000', '0', 'a77c154902fc496ea4f6f862feb4050f', '15');
INSERT INTO `user` VALUES ('15', 'Jocey', '123456', '1', '25', '金牛区', '100', '', '67803', '14409239401', '1', '0', '2', '1556553600000', '0', '69b633a9e6024165905f5cd92d0762e6', '14');
INSERT INTO `user` VALUES ('16', 'Coy', '123456', '0', '36', '服务器旁', '100', '/images/16.png', '54321', '18380209089', '1', '0', '2', '1556121600000', '0', 'a77c154902fc496ea4f6f862feb4050f', '3');
INSERT INTO `user` VALUES ('17', 'Oacen', '123456', '0', '39', '阊门外', '100', '', '62543', '13345678090', '1', '0', '2', '1559318400000', '0', '323c2ca2a89d4dc1882a60f90b342a83', '5');
INSERT INTO `user` VALUES ('18', 'Sara', '123456', '1', '38', '穹顶之下', '100', '/images/18.png', '80909', '15890790364', '1', '0', '7', '1556380800000', '0', 'bd1534aaf1d54d47b6f5565a43ff96fb', '5');
INSERT INTO `user` VALUES ('19', 'monica', '123456', '1', '27', '涛哥旁', '100', '/images/19.png', '65784', '13678904523', '1', '0', '2', '1556553600000', '0', '744be6b4a9fc42b5a8799170fc70d6bf', '15');
INSERT INTO `user` VALUES ('20', '臭熊', '123456', '1', '25', '新乐北街3号', '100', '', '90876', '18380392961', '1', '0', '26', '1735660800000', '0', '28581a0cb8774af9a08010e89a800cf9', '15');
INSERT INTO `user` VALUES ('21', 'Elena', '123456', '1', '26', '马路边', '100', '/images/21.png', '80246', '18023409802', '1', '0', '4', '1564675200000', '0', 'e0c94741f9334eaca0e75017f6d3517f', '1');
INSERT INTO `user` VALUES ('22', 'Andy', '123456', '0', '28', '巴中的', '100', '/images/22.png', '43279', '18070890987', '1', '0', '4', '1575561600000', '0', 'b72b0716b1954cb4b30e47ea485cea16', '1');
INSERT INTO `user` VALUES ('23', 'Bob', '123456', '0', '30', '不知何方', '100', '/images/23.png', '13790', '15883283044', '1', '0', '2', '1556467200000', '0', 'c3519e3e935d438383746a629d8d66f8', '1');
INSERT INTO `user` VALUES ('24', 'Parry', '123456', '0', '36', 'hk', '100', '/images/24.png', '38098', '13890732135', '1', '0', '8', '1560268800000', '0', '66c2342e3e524901a0151a95f2200d65', '1');
INSERT INTO `user` VALUES ('25', 'Harry', '123456', '0', '50', 'hk', '100', '/images/25.png', '13798', '15883283047', '1', '0', '4', '1556553600000', '0', 'ab89db5d05ee4a58abe992731917821e', '1');

-- ----------------------------
-- Table structure for `user_shop`
-- ----------------------------
DROP TABLE IF EXISTS `user_shop`;
CREATE TABLE `user_shop` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑关联user表id',
  `shop_name` varchar(100) NOT NULL COMMENT '店铺名',
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_shop
-- ----------------------------
INSERT INTO `user_shop` VALUES ('3', '5', 'd1-update');
INSERT INTO `user_shop` VALUES ('5', '7', 'chris的小店');
INSERT INTO `user_shop` VALUES ('6', '8', 'marry\'s shop1');
INSERT INTO `user_shop` VALUES ('7', '8', 'marry\'s shop2');
INSERT INTO `user_shop` VALUES ('8', '9', 'william\'s shop1');
INSERT INTO `user_shop` VALUES ('9', '9', 'william\'s shop2');
INSERT INTO `user_shop` VALUES ('10', '10', 'arron\'s shop1');
INSERT INTO `user_shop` VALUES ('11', '10', 'arron\'s shop2');
INSERT INTO `user_shop` VALUES ('12', '11', 'bluce\'s shop1');
INSERT INTO `user_shop` VALUES ('13', '21', 'elena\'s shop');
INSERT INTO `user_shop` VALUES ('14', '22', 'andy\'s little shop');

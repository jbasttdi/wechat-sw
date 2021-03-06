/*
Navicat MySQL Data Transfer

Source Server         : wechat-sw
Source Server Version : 50614
Source Host           : 192.168.168.250:3306
Source Database       : wechat_sw

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2016-03-30 17:22:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `payrecord`
-- ----------------------------
DROP TABLE IF EXISTS `payrecord`;
CREATE TABLE `payrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `openId` varchar(32) NOT NULL COMMENT '微信ID',
  `type` int(4) NOT NULL COMMENT '类型1缴费2预存',
  `userNo` int(8) NOT NULL COMMENT '缴费或者预存的户号',
  `fee` int(16) DEFAULT '0' COMMENT '金额数量',
  `userid` int(11) DEFAULT NULL COMMENT 'User表ID 备用字段',
  `isDelete` int(4) DEFAULT '1' COMMENT '是否删除默认1，不删除；0，删除',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `isChk` int(4) DEFAULT '0' COMMENT '默认为0,是否对过账，对过则改为1',
  `remark` varchar(100) DEFAULT NULL COMMENT '订单号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `isPaySuccess` tinyint(4) DEFAULT '0' COMMENT '进入支付接口时插入数据，0未支付，返回消息时，再次update为1，已支付',
  `orderpayNo` varchar(64) DEFAULT NULL COMMENT '交易单号;兴业银行的单号',
  `realNumber` int(16) unsigned zerofill DEFAULT NULL COMMENT '实付笔数，缴费确认时用到',
  `feeIDs` varchar(300) DEFAULT NULL COMMENT '费用ids,缴费确认时用到',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='充值缴费或者充值预存记录表，一个户号的操作对应一条数据';

-- ----------------------------
-- Records of payrecord
-- ----------------------------
INSERT INTO `payrecord` VALUES ('1', 'dfdb', '1', '3535', '35', null, '1', '2016-01-20 10:56:24', '0', null, '2016-01-27 10:56:29', '0', null, '0000000000000000', null);
INSERT INTO `payrecord` VALUES ('2', 'fgbgfgffvgbhg', '2', '11', '566', null, '1', null, '0', null, '2016-01-20 11:00:57', '0', null, '0000000000000000', null);
INSERT INTO `payrecord` VALUES ('3', 'rwerwer', '2', '444', '0', '444', '1', '0000-00-00 00:00:00', '0', '45reet', '2016-02-28 18:09:21', '0', null, '0000000000000000', null);
INSERT INTO `payrecord` VALUES ('6', '464564', '2', '444', '0', '444', '1', '2016-03-10 18:18:18', '0', null, '2016-02-28 18:18:23', '0', null, '0000000000000000', null);
INSERT INTO `payrecord` VALUES ('7', 'oa5uZuEm53BTuVGJhdYWlwFITMqg', '1', '904926', '709840', '0', '1', '2016-03-28 13:40:13', '0', 'A20160328134008-904926', '2016-03-28 13:40:13', '0', null, '0000000000000000', null);
INSERT INTO `payrecord` VALUES ('8', 'oa5uZuEm53BTuVGJhdYWlwFITMqg', '1', '904926', '709840', '0', '1', '2016-03-28 13:56:24', '0', 'A20160328135618-904926', '2016-03-28 13:56:24', '0', null, '0000000000000000', null);

-- ----------------------------
-- Table structure for `refeeflow`
-- ----------------------------
DROP TABLE IF EXISTS `refeeflow`;
CREATE TABLE `refeeflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(32) DEFAULT NULL COMMENT '微信openID（唯一）',
  `refeeOpenid` varchar(32) DEFAULT NULL COMMENT '推荐人微openID',
  `Source` tinyint(4) DEFAULT NULL COMMENT '推荐方式（备用）',
  `isDelete` tinyint(4) DEFAULT '1' COMMENT '是否删除',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='推荐人绑定表';

-- ----------------------------
-- Records of refeeflow
-- ----------------------------
INSERT INTO `refeeflow` VALUES ('1', '2fgbhbfgbfh', 'terdfghg', null, '1', '2016-01-24 13:48:19', '2016-01-24 13:48:22', null);
INSERT INTO `refeeflow` VALUES ('2', 'oa5uZuEm53BTuVGJhdYWlwFITMqg', 'oa5uZuNXDV_5W8DNrsaKYq5Cwdfo', null, null, '2016-03-24 17:28:12', '2016-03-24 17:28:12', null);

-- ----------------------------
-- Table structure for `scoreflow`
-- ----------------------------
DROP TABLE IF EXISTS `scoreflow`;
CREATE TABLE `scoreflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL COMMENT 'userID用户表ID',
  `openID` varchar(32) NOT NULL COMMENT '微信号。该积分消费时的绑定微信号(不能为空）',
  `type` int(4) NOT NULL DEFAULT '1' COMMENT 'type=1时添加积分type=2时减少积分',
  `Score` int(11) DEFAULT NULL COMMENT '积分消费值(>0）',
  `isDelete` tinyint(4) DEFAULT '1' COMMENT '是否删除',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='积分流水记录表';

-- ----------------------------
-- Records of scoreflow
-- ----------------------------
INSERT INTO `scoreflow` VALUES ('1', '4345', 'GFGHGHFGH', '1', '34', '1', '2016-01-28 14:54:54', '2016-01-24 14:54:59', null);
INSERT INTO `scoreflow` VALUES ('2', '10', 'oa5uZuNXDV_5W8DNrsaKYq5Cwdfo', '1', '10', null, '2016-03-24 17:28:12', '2016-03-24 17:28:12', null);

-- ----------------------------
-- Table structure for `shutoffwaterflow`
-- ----------------------------
DROP TABLE IF EXISTS `shutoffwaterflow`;
CREATE TABLE `shutoffwaterflow` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `keyword1` varchar(50) NOT NULL COMMENT '停水类型',
  `keyword2` varchar(100) NOT NULL COMMENT '停水时间',
  `keyword3` varchar(100) NOT NULL COMMENT '停水区域',
  `remark` varchar(300) NOT NULL COMMENT '停水说明',
  `mouldID` varchar(64) DEFAULT NULL COMMENT '模板ID',
  `url` varchar(64) DEFAULT NULL COMMENT '指向url',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态，job,默认0:没推送;1:推送中;2推送完成',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `isDelete` tinyint(4) DEFAULT '1' COMMENT '是否删除，不删1，删0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='停水通知记录表';

-- ----------------------------
-- Records of shutoffwaterflow
-- ----------------------------
INSERT INTO `shutoffwaterflow` VALUES ('1', '机器故障', '2014年10月到2015年3月', '北京市区', '冬天结冰，水管爆裂', 'BfkzuC__ueuJkF84cPup-27OnSlq1fYrDUFBdmBU2uY', null, '1', '2016-03-05 16:45:08', '2016-03-05 16:45:13', '1');
INSERT INTO `shutoffwaterflow` VALUES ('2', '天气原因', '2014年10月到2015年3月', '北京市区', '冬天结冰，水管爆裂', '48jg5h54jkhgk45gfdhfghrtyhtyjhggj', null, '0', '2016-03-22 17:29:31', '0000-00-00 00:00:00', '1');
INSERT INTO `shutoffwaterflow` VALUES ('3', '计划检修', '2014年8月29日8:00-18:00', '安源区步行街、北桥、万龙湾', '因为城区道路改造需要对自来水供水管路进行停水履行，给您带来不便敬请谅解。市政公告。', 'BfkzuC__ueuJkF84cPup-27OnSlq1fYrDUFBdmBU2uY', null, '1', '2016-03-08 14:13:32', '2016-03-08 14:13:32', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(32) DEFAULT NULL COMMENT '微信id',
  `wxName` varchar(30) DEFAULT NULL COMMENT '微信昵称',
  `type` int(4) NOT NULL COMMENT '1 绑定用户 2后台登录用户',
  `username` varchar(30) DEFAULT NULL COMMENT '登录名type=2',
  `password` varchar(64) DEFAULT NULL COMMENT '登录密码type=2',
  `score` int(16) DEFAULT '0' COMMENT '积分type=1',
  `IsAdmin` int(4) DEFAULT NULL COMMENT '是否为管理员type=2',
  `IsAutho` int(4) DEFAULT '0' COMMENT '是否授权（同城易购）type=1',
  `isDelete` int(4) DEFAULT '1' COMMENT '是否删除',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL COMMENT '备注 type=2时是随机数8位，固定的，登陆用;type=1是微信客户的唯一推荐码',
  `isFollow` tinyint(4) NOT NULL DEFAULT '1' COMMENT ' type=1仅且有效是否关注（默认关注1）取消关注0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='user 用户登录 ，或者微信用户关注';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('6', '666', '小狗', '2', 'superadmin', 'BF83448FF48ABF45B50D86763F0075FD', '0', '0', '0', '1', '2016-01-15 15:19:55', '2016-01-15 15:19:55', 'TpjPLucX', '1');
INSERT INTO `user` VALUES ('7', '', null, '2', 'admin', 'BF83448FF48ABF45B50D86763F0075FD', '0', null, '0', '1', '2016-01-15 15:19:55', '2016-01-15 15:19:55', 'TpjPLucX', '1');
INSERT INTO `user` VALUES ('8', 'oa5uZuEm53BTuVGJhdYWlwFITMqg', 'tzy', '1', null, null, '100', null, '0', '1', '2016-03-16 14:08:55', '2016-03-16 14:08:55', 'ASDFFFGy', '1');
INSERT INTO `user` VALUES ('9', 'oa5uZuDY-VVVasZZoHfeO3OD_Uaw', null, '1', null, null, '1', null, '0', '1', '2016-03-23 14:59:14', '2016-03-23 14:59:14', 'AC6mpN3s', '1');
INSERT INTO `user` VALUES ('10', 'oa5uZuNXDV_5W8DNrsaKYq5Cwdfo', null, '1', null, null, '40', null, '0', '1', '2016-03-23 14:59:54', '2016-03-23 14:59:54', 'dkWudbSv', '1');
INSERT INTO `user` VALUES ('11', null, null, '2', 'swjt', 'BF83448FF48ABF45B50D86763F0075FD', '0', '1', '0', '1', '2016-03-28 09:06:58', '2016-03-28 09:07:03', 'TpjPLucX', '1');

-- ----------------------------
-- Table structure for `userbindflow`
-- ----------------------------
DROP TABLE IF EXISTS `userbindflow`;
CREATE TABLE `userbindflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `openID` varchar(32) NOT NULL COMMENT '微信号的ID',
  `rePayFee` decimal(16,2) unsigned DEFAULT '0.00' COMMENT '预存费用',
  `userNo` int(8) NOT NULL COMMENT '绑定户号',
  `phoneNum` varchar(11) DEFAULT NULL COMMENT '绑定手机号码',
  `address` varchar(3000) DEFAULT NULL COMMENT '绑定地址',
  `userName` varchar(30) DEFAULT NULL COMMENT '绑定姓名',
  `isBinding` tinyint(4) DEFAULT '1' COMMENT '是否绑定（默认绑定1）解绑0',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `isDelete` tinyint(4) DEFAULT '1' COMMENT '是否删除',
  `remark` varchar(100) DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='绑定用户的流水表，微信用户的ID和自来水开发号码绑定';

-- ----------------------------
-- Records of userbindflow
-- ----------------------------
INSERT INTO `userbindflow` VALUES ('22', 'oa5uZuEm53BTuVGJhdYWlwFITMqg', '8.10', '810023', '15167153827', '浦南大道673号浦金商务楼', '徐雪庆', '1', '2016-03-16 11:05:10', '2016-03-22 14:10:08', '1', null);
INSERT INTO `userbindflow` VALUES ('23', 'oa5uZuEm53BTuVGJhdYWlwFITMqg', '32.20', '5962', '15024565218', '城西一苑3幢2单元302室', '楼正洪', '1', '2016-03-17 14:14:32', '2016-03-22 14:10:08', '1', null);
INSERT INTO `userbindflow` VALUES ('24', 'oa5uZuDY-VVVasZZoHfeO3OD_Uaw', '0.00', '904948', '13296968686', '卧龙绿都（文澜苑七幢一单元）', '卧龙绿都', '1', '2016-03-23 15:01:34', '2016-03-23 15:08:57', '1', null);
INSERT INTO `userbindflow` VALUES ('25', 'oa5uZuNXDV_5W8DNrsaKYq5Cwdfo', '0.00', '904961', '15075428632', '卧龙绿都（文澜苑11幢一单元西）', '卧龙绿都', '1', '2016-03-23 15:01:40', '2016-03-23 15:01:40', '1', null);
INSERT INTO `userbindflow` VALUES ('26', 'oa5uZuDY-VVVasZZoHfeO3OD_Uaw', '0.00', '904926', '13296868656', '兰香苑6幢二单元', '卧龙山水绿都', '1', '2016-03-23 15:02:02', '2016-03-23 15:08:57', '1', null);
INSERT INTO `userbindflow` VALUES ('29', 'oa5uZuEm53BTuVGJhdYWlwFITMqg', '0.00', '904926', '12145154874', '兰香苑6幢二单元', '卧龙山水绿都', '1', '2016-03-24 17:28:13', '2016-03-24 17:28:13', '1', null);

/*
 Navicat Premium Data Transfer

 Source Server         : gjw-mysql
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : mini-douyin

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 23/06/2018 10:09:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bgm
-- ----------------------------
DROP TABLE IF EXISTS `bgm`;
CREATE TABLE `bgm`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '播放地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bgm
-- ----------------------------
INSERT INTO `bgm` VALUES ('18052674D26HH32P', '测试歌手名字', 'abc歌曲~~', '\\bgm\\我是.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH33P', '测试歌手名字', 'abc歌曲~~', '\\bgm\\music.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH3X1', '测试歌手名字', 'abc歌曲~~', '\\bgm\\music.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH3X2', '测试歌手名字', 'abc歌曲~~', '\\bgm\\music.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH3X3', '测试歌手名字', 'abc歌曲~~', '\\bgm\\music.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH3X5', '测试歌手名字', 'abc歌曲~~', '\\bgm\\music.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH3X6', '测试歌手名字', 'abc歌曲~~', '\\bgm\\我是.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH3X7', '测试歌手名字', 'abc歌曲~~', '\\bgm\\aa.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH3X8', '测试歌手名字', 'abc歌曲~~', '\\bgm\\music.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH3X9', '测试歌手名字', 'abc歌曲~~', '\\bgm\\music.mp3');
INSERT INTO `bgm` VALUES ('1805290R3WTDMT9P', 'aa', 'aa', '\\bgm\\好歌曲.mp3');
INSERT INTO `bgm` VALUES ('180530DXKK4YYGTC', '达瓦', 'dwadw', '\\bgm\\music好歌曲.mp3');
INSERT INTO `bgm` VALUES ('18062305C41RMCX4', 'dasd', 'asdasd', '\\bgm\\斗地主斗地主.mp3');
INSERT INTO `bgm` VALUES ('180623060PZXTA14', 'dgdf', 'sfdsdf', '\\bgm\\斗地主斗地主.mp3');
INSERT INTO `bgm` VALUES ('18062306PGK37MFW', 'DSSDAF', 'ADSASD', '\\bgm\\aaaaaaa.mp3');
INSERT INTO `bgm` VALUES ('1806230B9ZHYPRWH', 'FDSXFS', 'SDFSDDF', '\\bgm\\斗地主.mp3');
INSERT INTO `bgm` VALUES ('1806230BFPG9SZHH', 'cgfhbcvb', 'cgfhbcvbcbvcvb', '\\bgm\\斗地主.mp3');
INSERT INTO `bgm` VALUES ('1806230Y4YHHAKS8', 'jlkjk', 'jlkjkjljl', '\\bgm\\斗地主.mp3');
INSERT INTO `bgm` VALUES ('1806230YAKZ6T0H0', 'jlkjk', 'jlkjkjljl', '\\bgm\\斗地主.mp3');
INSERT INTO `bgm` VALUES ('1806230YAM24NT2W', 'jlkjk', 'jlkjkjljl', '\\bgm\\斗地主.mp3');
INSERT INTO `bgm` VALUES ('1806230YAM3402K4', 'jlkjk', 'jlkjkjljl', '\\bgm\\斗地主.mp3');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `father_comment_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `to_user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `video_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频id',
  `from_user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言者，评论的用户id',
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程评论表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('135240CW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈1', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('18034CW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈2', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('1803F50CW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈3', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('18052150CW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈4', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('180522F50626894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈5', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('180522F50C126894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈6', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('180522F50CW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈7', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('180522F5346894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈8', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('180522F54CW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈9', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('180523F533Y0837C', NULL, NULL, '180510CD0A6K3SRP', '180425CFA4RB6T0H', 'dwdawdwa', '2018-05-23 19:52:19');
INSERT INTO `comments` VALUES ('180523FATAR6C2Y8', '180523FB1BYS43HH', '180425CFA4RB6T0H', '180522FB4BZ1N9P0', '180518CKMAAM5TXP', '回复评论', '2018-05-23 20:09:30');
INSERT INTO `comments` VALUES ('180523FB1BYS43HH', NULL, NULL, '180522FB4BZ1N9P0', '180425CFA4RB6T0H', '野马~~', '2018-05-23 20:10:09');
INSERT INTO `comments` VALUES ('180523FC0MKCTS3C', NULL, NULL, '180522FB4BZ1N9P0', '180425CFA4RB6T0H', 'dwdw', '2018-05-23 20:13:04');
INSERT INTO `comments` VALUES ('180523FCZM2CTCZC', NULL, NULL, '180522FB4BZ1N9P0', '180425CFA4RB6T0H', 'dwqdwqdqw', '2018-05-23 20:15:58');
INSERT INTO `comments` VALUES ('180523FD1H5HG9P0', NULL, NULL, '180522FB4BZ1N9P0', '180425CFA4RB6T0H', '1fewdew', '2018-05-23 20:16:10');
INSERT INTO `comments` VALUES ('180523FDBT3S3C00', NULL, NULL, '180522FB4BZ1N9P0', '180425CFA4RB6T0H', '123', '2018-05-23 20:17:16');
INSERT INTO `comments` VALUES ('180523FDFX4HS5P0', NULL, NULL, '180522FB4BZ1N9P0', '180425CFA4RB6T0H', '43trgtew', '2018-05-23 20:17:36');
INSERT INTO `comments` VALUES ('180523H79060HNHH', NULL, NULL, '180522FB4BZ1N9P0', '180425CFA4RB6T0H', '哈多好玩聊哦', '2018-05-23 22:47:06');
INSERT INTO `comments` VALUES ('180523HATXR841KP', NULL, NULL, '180522FB4BZ1N9P0', '180425CFA4RB6T0H', '大家诶哦大家欧文', '2018-05-23 22:57:42');
INSERT INTO `comments` VALUES ('180523K2DG3SGAA8', NULL, NULL, '180522FB4BZ1N9P0', '180518CKMAAM5TXP', 'dwdw', '2018-05-23 23:56:38');
INSERT INTO `comments` VALUES ('180523K2YRF1WNXP', NULL, NULL, '180522FB4BZ1N9P0', '180518CKMAAM5TXP', 'ddew', '2018-05-23 23:58:03');
INSERT INTO `comments` VALUES ('180523K3FH1WT7R4', NULL, NULL, '180522FB4BZ1N9P0', '180518CKMAAM5TXP', 'tgergre', '2018-05-23 23:59:45');
INSERT INTO `comments` VALUES ('1805240G4G19R0PH', '180523HATXR841KP', '180425CFA4RB6T0H', '180522FB4BZ1N9P0', '180518CKMAAM5TXP', '回复测试，final', '2018-05-24 00:45:31');
INSERT INTO `comments` VALUES ('18055W46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈11', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('180565016894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈22', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('1805650CW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈33', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('1805twW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈44', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('280522F50CW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈55', '2018-05-22 19:52:02');
INSERT INTO `comments` VALUES ('qq1805twW46894', NULL, NULL, '180510CD0A6K3SRP', '180518CKMAAM5TXP', 'hhhh 测试啊哈哈哈66', '2018-05-22 19:52:02');

-- ----------------------------
-- Table structure for search_records
-- ----------------------------
DROP TABLE IF EXISTS `search_records`;
CREATE TABLE `search_records`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '搜索的内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频搜索的记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of search_records
-- ----------------------------
INSERT INTO `search_records` VALUES ('1', '慕课网');
INSERT INTO `search_records` VALUES ('18051309YBCMHYRP', '风景');
INSERT INTO `search_records` VALUES ('1805130DAXX58ARP', '风景');
INSERT INTO `search_records` VALUES ('1805130DMG6P0ZC0', '风景');
INSERT INTO `search_records` VALUES ('1805130FNGHD3B0H', '慕课网');
INSERT INTO `search_records` VALUES ('180513C94W152Z7C', 'dwqdwq');
INSERT INTO `search_records` VALUES ('180513DXNT7SY04H', '风景');
INSERT INTO `search_records` VALUES ('180618HGPXBK9T7C', 'aa');
INSERT INTO `search_records` VALUES ('180618HXF2ZZF04H', 'aaa');
INSERT INTO `search_records` VALUES ('2', '慕课网');
INSERT INTO `search_records` VALUES ('3', '慕课网');
INSERT INTO `search_records` VALUES ('4', '慕课网');
INSERT INTO `search_records` VALUES ('5', '慕课');
INSERT INTO `search_records` VALUES ('6', '慕课');
INSERT INTO `search_records` VALUES ('7', 'zookeeper');
INSERT INTO `search_records` VALUES ('8', 'zookeeper');
INSERT INTO `search_records` VALUES ('9', 'zookeeper');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `face_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '我的头像，如果没有默认给一张',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `fans_counts` int(11) DEFAULT 0 COMMENT '我的粉丝数量',
  `follow_counts` int(11) DEFAULT 0 COMMENT '我关注的人总数',
  `receive_like_counts` int(11) DEFAULT 0 COMMENT '我接受到的赞美/收藏 的数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1001', 'test-imooc-1111', '9999', '/path000999', '慕课网好牛十分牛~', 123456, 111, 222);
INSERT INTO `users` VALUES ('180425B0B3N6B25P', 'imooc11', 'wzNncBURtPYCDsYd7TUgWQ==', NULL, 'imooc', 0, 0, 0);
INSERT INTO `users` VALUES ('180425BNSR1CG0H0', 'abc', '4QrcOUm6Wau+VuBX8g+IPg==', NULL, 'abc', 0, 0, 0);
INSERT INTO `users` VALUES ('180425CFA4RB6T0H', 'imooc', 'kU8h64TG/bK2Y91vRT9lyg==', '/180425CFA4RB6T0H/face/tmp_3ae776d5faeecc96d1726f1878f430a475cd26664dae04de.jpg', 'imooc1', 1, 8, 7);
INSERT INTO `users` VALUES ('180426F4D35R04X4', 'aaa', 'R7zlx09Yn0hn29V+nKn4CA==', NULL, 'aaa', 0, 0, 0);
INSERT INTO `users` VALUES ('180426F55CZPA9YW', 'aaaa', 'R7zlx09Yn0hn29V+nKn4CA==', NULL, 'aaaa', 0, 0, 0);
INSERT INTO `users` VALUES ('180426GA3SBB4DP0', '1001', 'bfw1xHdW6WLvBV0QSfH47A==', NULL, '1001', 0, 0, 0);
INSERT INTO `users` VALUES ('180426GAK87AB0X4', '10401', 'bfw1xHdW6WLvBV0QSfH47A==', NULL, '10401', 0, 0, 0);
INSERT INTO `users` VALUES ('180426GAWCC17KWH', '104701', 'bfw1xHdW6WLvBV0QSfH47A==', NULL, '104701', 0, 0, 0);
INSERT INTO `users` VALUES ('180426GBDFKDG5D4', '10re4701', 'bfw1xHdW6WLvBV0QSfH47A==', NULL, '10re4701', 0, 0, 0);
INSERT INTO `users` VALUES ('180426GBKN0YRFRP', '10rwee4701', 'bfw1xHdW6WLvBV0QSfH47A==', NULL, '10rwee4701', 0, 0, 0);
INSERT INTO `users` VALUES ('180426GH49XRZHX4', '390213890', 'H9V/tnfgt6nniqH5bDZ0hQ==', NULL, '390213890', 0, 0, 0);
INSERT INTO `users` VALUES ('180426GHH12WXPZC', '390ewqewq213890', 'H9V/tnfgt6nniqH5bDZ0hQ==', NULL, '390ewqewq213890', 0, 0, 0);
INSERT INTO `users` VALUES ('180426GHHPZ7NTC0', '390ewqewq21ewqe3890', 'H9V/tnfgt6nniqH5bDZ0hQ==', NULL, '390ewqewq21ewqe3890', 0, 0, 0);
INSERT INTO `users` VALUES ('180426H0GWP0C3HH', 'jdiowqjodij', 'wEmTz54sy+StEzB+TrtGSQ==', NULL, 'jdiowqjodij', 0, 0, 0);
INSERT INTO `users` VALUES ('180518CHS7SXMCX4', 'aaaaa', 'WU+AOzgKQTlu1j3KOVA1Qg==', NULL, 'aaaaa', 0, 0, 0);
INSERT INTO `users` VALUES ('180518CKMAAM5TXP', 'abc123', '6ZoYxCjLONXyYIU2eJIuAw==', '/180518CKMAAM5TXP/face/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.Q27j7DxNKKk07adf242959818c45238b8f11b64cc255.png', 'abc123', 0, 0, 1);
INSERT INTO `users` VALUES ('180616G4BXB5NMW0', 'douyinuser', '4QrcOUm6Wau+VuBX8g+IPg==', NULL, 'douyinuser', 0, 0, 0);
INSERT INTO `users` VALUES ('180616GPCS09RG2W', 'gjw199513', 'hA3MZrKYmhGguqby2KeNGg==', '/180425CFA4RB6T0H/face/tmp_3ae776d5faeecc96d1726f1878f430a475cd26664dae04de.jpg', 'gjw199513', 0, 0, 0);
INSERT INTO `users` VALUES ('180616GSRGBCC3HH', 'gjw', 'vzIvER9vv85uVh0z+qD5Ag==', NULL, 'gjw', 0, 0, 0);
INSERT INTO `users` VALUES ('180616GW6XATG8PH', 'gjw1995', 'vzIvER9vv85uVh0z+qD5Ag==', NULL, 'gjw1995', 2, 0, 0);
INSERT INTO `users` VALUES ('180616HPGWW93KGC', '111', 'aY1RoZ2KEhzlgUmde3AWaA==', NULL, '111', 0, 0, 0);
INSERT INTO `users` VALUES ('1806170KXXPS6CX4', 'sdfs', '1Y41gq+pkEDie5KxPI8igA==', NULL, 'sdfs', 0, 0, 0);

-- ----------------------------
-- Table structure for users_fans
-- ----------------------------
DROP TABLE IF EXISTS `users_fans`;
CREATE TABLE `users_fans`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `fan_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '粉丝',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `fan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户粉丝关联关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users_fans
-- ----------------------------
INSERT INTO `users_fans` VALUES ('18062102FPAKP18H', '180616GW6XATG8PH', '180425CFA4RB6T0H');

-- ----------------------------
-- Table structure for users_like_videos
-- ----------------------------
DROP TABLE IF EXISTS `users_like_videos`;
CREATE TABLE `users_like_videos`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `video_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_video_rel`(`user_id`, `video_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户喜欢的/赞过的视频' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users_like_videos
-- ----------------------------
INSERT INTO `users_like_videos` VALUES ('18062102453GZB0H', '180425CFA4RB6T0H', '180618HTKGFYPN7C');
INSERT INTO `users_like_videos` VALUES ('180520HBA054FPPH', '180518CKMAAM5TXP', '180510CCX05TABHH');
INSERT INTO `users_like_videos` VALUES ('180520HSBDW6M0SW', '180518CKMAAM5TXP', '180510CD0A6K3SRP');

-- ----------------------------
-- Table structure for users_report
-- ----------------------------
DROP TABLE IF EXISTS `users_report`;
CREATE TABLE `users_report`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `deal_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '被举报用户id',
  `deal_video_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型标题，让用户选择，详情见 枚举',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
  `userid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '举报人的id',
  `create_date` datetime(0) NOT NULL COMMENT '举报时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '举报用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users_report
-- ----------------------------
INSERT INTO `users_report` VALUES ('180521FZ501ABDYW', '180425CFA4RB6T0H', '180510CD0A6K3SRP', '引人不适', '不合时宜的视频', '180518CKMAAM5TXP', '2018-05-21 20:58:35');
INSERT INTO `users_report` VALUES ('180521FZK44ZRWX4', '180425CFA4RB6T0H', '180510CD0A6K3SRP', '引人不适', '不合时宜的视频', '180518CKMAAM5TXP', '2018-05-21 20:59:53');
INSERT INTO `users_report` VALUES ('180521FZR1TYRTXP', '180425CFA4RB6T0H', '180510CD0A6K3SRP', '辱骂谩骂', 't4t43', '180518CKMAAM5TXP', '2018-05-21 21:00:18');
INSERT INTO `users_report` VALUES ('1806210YGZSC38PH', '180425CFA4RB6T0H', '180618HTKGFYPN7C', '色情低俗', 'sdasd', '180425CFA4RB6T0H', '2018-06-21 01:19:47');
INSERT INTO `users_report` VALUES ('1806210YTGM14ACH', '180425CFA4RB6T0H', '1806181NZD1DS2RP', '色情低俗', 'sdfsdfsd', '180425CFA4RB6T0H', '2018-06-21 01:20:35');
INSERT INTO `users_report` VALUES ('1806210Z3RXH6AY8', '180616GPCS09RG2W', '180618HTKGFYPN7C', '色情低俗', 'sdfsf', '180425CFA4RB6T0H', '2018-06-21 01:21:28');

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者id',
  `audio_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户使用音频的信息',
  `video_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '视频描述',
  `video_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频存放的路径',
  `video_seconds` float(6, 2) DEFAULT NULL COMMENT '视频秒数',
  `video_width` int(6) DEFAULT NULL COMMENT '视频宽度',
  `video_height` int(6) DEFAULT NULL COMMENT '视频高度',
  `cover_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '视频封面图',
  `like_counts` bigint(20) NOT NULL DEFAULT 0 COMMENT '喜欢/赞美的数量',
  `status` int(1) NOT NULL COMMENT '视频状态：\r\n1、发布成功\r\n2、禁止播放，管理员操作',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES ('180510CC3819RHBC', '180425CFA4RB6T0H', '', '', '/180425CFA4RB6T0H/video/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.R5zVt7RTx6Vv6ee923498030ecd9328acf709bb3a278.mp4', 10.05, 960, 540, '/180425CFA4RB6T0H/video/wxa2049f5aead89372o6zAJs5sm5bPFcTzKXp_5wXsWusoR5zVt7RTx6Vv6ee923498030ecd9328acf709bb3a278.jpg', 0, 1, '2018-05-10 17:25:13');
INSERT INTO `videos` VALUES ('180510CCCK8A6S14', '180425CFA4RB6T0H', '', '夏天夏天', '/180425CFA4RB6T0H/video/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.upJlpa1Oc99Zaf561ddc63a69c13472506f586cda815.mp4', 10.00, 960, 540, '/180425CFA4RB6T0H/video/wxa2049f5aead89372o6zAJs5sm5bPFcTzKXp_5wXsWusoupJlpa1Oc99Zaf561ddc63a69c13472506f586cda815.jpg', 0, 1, '2018-05-10 17:26:13');
INSERT INTO `videos` VALUES ('180510CCK95WNG7C', '180425CFA4RB6T0H', '', '海贼王', '/180425CFA4RB6T0H/video/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.nPTyoc9tyI3kabf2c7ceeb9b446b60b47b1705668857.mp4', 10.02, 544, 960, '/180425CFA4RB6T0H/video/wxa2049f5aead89372o6zAJs5sm5bPFcTzKXp_5wXsWusonPTyoc9tyI3kabf2c7ceeb9b446b60b47b1705668857.jpg', 0, 1, '2018-05-10 17:26:43');
INSERT INTO `videos` VALUES ('180510CCNC7C9FCH', '180425CFA4RB6T0H', '', '吃饭吃饭', '/180425CFA4RB6T0H/video/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.uG2pt2vkkRI586933d88b91b9577ff8d2d4864443a50.mp4', 6.02, 960, 544, '/180425CFA4RB6T0H/video/wxa2049f5aead89372o6zAJs5sm5bPFcTzKXp_5wXsWusouG2pt2vkkRI586933d88b91b9577ff8d2d4864443a50.jpg', 0, 1, '2018-05-10 17:26:57');
INSERT INTO `videos` VALUES ('180510CCSFC45A5P', '180425CFA4RB6T0H', '', '风景可以', '/180425CFA4RB6T0H/video/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.L5WSh3OtMAgf9e6b52aed5830d1bfcfdcb98b2600280.mp4', 9.90, 960, 404, '/180425CFA4RB6T0H/video/wxa2049f5aead89372o6zAJs5sm5bPFcTzKXp_5wXsWusoL5WSh3OtMAgf9e6b52aed5830d1bfcfdcb98b2600280.jpg', 0, 1, '2018-05-10 17:27:17');
INSERT INTO `videos` VALUES ('180510CCX05TABHH', '180425CFA4RB6T0H', '', '变魔术', '/180425CFA4RB6T0H/video/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.yptyFK8MwgrL9e2d3e37c168928a9db960e3b24f782b.mp4', 6.02, 540, 960, '/180425CFA4RB6T0H/video/tmp_3ae776d5faeecc96d1726f1878f430a475cd26664dae04de.jpg', 1, 1, '2018-05-10 17:27:33');
INSERT INTO `videos` VALUES ('180510CD0A6K3SRP', '180425CFA4RB6T0H', '', '来一场说走就走的旅行', '/180425CFA4RB6T0H/video/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.qtm2zeEgenrf7993bfb60dbac65af7b7847d51ee4445.mp4', 5.02, 960, 544, '/180425CFA4RB6T0H/video/wxa2049f5aead89372o6zAJs5sm5bPFcTzKXp_5wXsWusoqtm2zeEgenrf7993bfb60dbac65af7b7847d51ee4445.jpg', 3, 1, '2018-05-10 17:27:54');
INSERT INTO `videos` VALUES ('180513FW054FFRAW', '180425CFA4RB6T0H', '180616GW6XATG8PH', '复用上传测试', '/180425CFA4RB6T0H/video/wxa2049f5aead89372.o6zAJs5sm5bPFcTzKXp_5wXsWuso.Bc21hNytcS0M2da7b7e0f5cc83d362eba78aec81adb9.mp4', 7.52, 544, 960, '/180425CFA4RB6T0H/video/wxac940a48ec796dec.o6zAJs957cXZhx3BmLs6mND0SMKw.qSgL9JCXFtML347786b3d51ca8a0f9a63495a6d80d13.jpg', 0, 1, '2018-05-13 20:49:03');
INSERT INTO `videos` VALUES ('180522FB4BZ1N9P0', '180425CFA4RB6T0H', '180616GW6XATG8PH', '骑着野马出去浪………~~', '/180425CFA4RB6T0H/video/tmp_5e574148aa8356149758ec969b108886.mp4', 9.00, 540, 960, '/180425CFA4RB6T0H/video/tmp_5e574148aa8356149758ec969b108886.jpg', 0, 1, '2018-05-22 20:10:28');
INSERT INTO `videos` VALUES ('180617GGGC0W2NR4', '180425CFA4RB6T0H', '18052674D26HH32P', 'aaa', '/180425CFA4RB6T0H/video/c1c1bed2-ac5d-476c-b140-cb3c5c02a06a.mp4', 6.03, 960, 544, '/180425CFA4RB6T0H/video/2016619179121702.jpg', 0, 1, '2018-06-17 21:47:45');
INSERT INTO `videos` VALUES ('180617K38RZXT4SW', '180425CFA4RB6T0H', '18052674D26HH32P', 'ceshi', '/180425CFA4RB6T0H/video/67f6fe4d-f0cd-40f6-9427-842c49c51ba0.mp4', 6.03, 960, 544, '/180425CFA4RB6T0H/video/wxac940a48ec796dec.o6zAJs957cXZhx3BmLs6mND0SMKw.UDyqXVdi9F0Yf81095c72aa3af38bb46a9d7726fda8e.jpg', 0, 1, '2018-06-17 23:59:08');
INSERT INTO `videos` VALUES ('1806180P6KDHA8M8', '180425CFA4RB6T0H', '18052674D26HH32P', '', '/180425CFA4RB6T0H/video/aff6a6ba-2bef-49ec-9670-43d19d948eb4.mp4', 6.00, 480, 960, NULL, 0, 1, '2018-06-18 01:00:46');
INSERT INTO `videos` VALUES ('1806180PWM28NRS8', '180425CFA4RB6T0H', '18052674D26HH32P', '', '/180425CFA4RB6T0H/video/b076f8f0-809f-4c06-a059-7b6cbb3c6bf7.mp4', 6.00, 480, 960, NULL, 0, 1, '2018-06-18 01:02:42');
INSERT INTO `videos` VALUES ('1806180RMRXPD1P0', '180425CFA4RB6T0H', '18052674D26HH32P', '吧', '/180425CFA4RB6T0H/video/c9e3639e-67a5-4f71-b8ec-3475fbf1c424.mp4', 6.00, 480, 960, NULL, 0, 1, '2018-06-18 01:05:04');
INSERT INTO `videos` VALUES ('1806181NZD1DS2RP', '180425CFA4RB6T0H', '180616GW6XATG8PH', 'aaa', '/180425CFA4RB6T0H/video/06d8fb1b-8ff6-493f-9cad-7105d14b2b8b.mp4', 6.03, 960, 544, '/180425CFA4RB6T0H/video/wxac940a48ec796dec.o6zAJs957cXZhx3BmLs6mND0SMKw.qSgL9JCXFtML347786b3d51ca8a0f9a63495a6d80d13.jpg', 0, 1, '2018-06-18 02:24:04');
INSERT INTO `videos` VALUES ('180618HTKGFYPN7C', '180616GPCS09RG2W', '18052674D26HH32P', 'ceshiceshi1', '/[object Undefined]/video/76aff35b-57f3-4916-86d1-25b8a69d98c6.mp4', 6.03, 960, 544, '/[object Undefined]/video/wxac940a48ec796dec.jpg', 1, 1, '2018-06-18 23:36:04');

SET FOREIGN_KEY_CHECKS = 1;

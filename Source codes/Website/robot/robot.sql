/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50739 (5.7.39)
 Source Host           : 127.0.0.1:3306
 Source Schema         : robot

 Target Server Type    : MySQL
 Target Server Version : 50739 (5.7.39)
 File Encoding         : 65001

 Date: 29/06/2023 21:14:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for exploration
-- ----------------------------
DROP TABLE IF EXISTS `exploration`;
CREATE TABLE `exploration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `robot_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `maze_exploration_time` int(11) NOT NULL,
  `treasure_count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exploration
-- ----------------------------
BEGIN;
INSERT INTO `exploration` (`id`, `robot_id`, `timestamp`, `maze_exploration_time`, `treasure_count`) VALUES (1, 1, '2023-06-29 13:42:01', 60, 5);
INSERT INTO `exploration` (`id`, `robot_id`, `timestamp`, `maze_exploration_time`, `treasure_count`) VALUES (2, 2, '2023-06-29 13:42:01', 90, 3);
INSERT INTO `exploration` (`id`, `robot_id`, `timestamp`, `maze_exploration_time`, `treasure_count`) VALUES (3, 3, '2023-06-29 13:42:01', 120, 7);
COMMIT;

-- ----------------------------
-- Table structure for robot
-- ----------------------------
DROP TABLE IF EXISTS `robot`;
CREATE TABLE `robot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `location` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of robot
-- ----------------------------
BEGIN;
INSERT INTO `robot` (`id`, `name`, `location`, `image`) VALUES (1, '机器人1', '地点1', 'robot1.png');
INSERT INTO `robot` (`id`, `name`, `location`, `image`) VALUES (2, '机器人2', '地点2', 'robot2.png');
INSERT INTO `robot` (`id`, `name`, `location`, `image`) VALUES (3, '机器人3', '地点3', 'robot3.png');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`) VALUES (1, 'admin', '123456');
INSERT INTO `user` (`id`, `username`, `password`) VALUES (5, 'admin1', '123456');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

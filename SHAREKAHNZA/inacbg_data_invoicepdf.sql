/*
 Navicat Premium Data Transfer

 Source Server         : BACKUP DB XAMPP
 Source Server Type    : MySQL
 Source Server Version : 100138
 Source Host           : 192.168.15.110:3306
 Source Schema         : backup_simrs_indriati

 Target Server Type    : MySQL
 Target Server Version : 100138
 File Encoding         : 65001

 Date: 29/09/2023 15:45:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for inacbg_data_invoicepdf
-- ----------------------------
DROP TABLE IF EXISTS `inacbg_data_invoicepdf`;
CREATE TABLE `inacbg_data_invoicepdf` (
  `no_sep` varchar(40) NOT NULL,
  `filepdf` longtext,
  PRIMARY KEY (`no_sep`),
  CONSTRAINT `inacbg_data_invoicepdf_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;

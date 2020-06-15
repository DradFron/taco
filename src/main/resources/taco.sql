/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 127.0.0.1:3306
 Source Schema         : taco

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 15/06/2020 23:03:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Ingredient
-- ----------------------------
DROP TABLE IF EXISTS `Ingredient`;
CREATE TABLE `Ingredient` (
  `id` varchar(4) NOT NULL,
  `name` varchar(25) NOT NULL,
  `type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Ingredient
-- ----------------------------
BEGIN;
INSERT INTO `Ingredient` VALUES ('CARN', 'Carnitas', 'PROTEIN');
INSERT INTO `Ingredient` VALUES ('CHED', 'Cheddar', 'CHEESE');
INSERT INTO `Ingredient` VALUES ('COTO', 'Corn Tortilla', 'WRAP');
INSERT INTO `Ingredient` VALUES ('FLTO', 'Flour Tortilla', 'WRAP');
INSERT INTO `Ingredient` VALUES ('GRBF', 'Ground Beef', 'PROTEIN');
INSERT INTO `Ingredient` VALUES ('JACK', 'Monterrey', 'CHEESE');
INSERT INTO `Ingredient` VALUES ('LETC', 'Lettuce', 'VEGGIES');
INSERT INTO `Ingredient` VALUES ('SLSA', 'Salad', 'SAUCE');
INSERT INTO `Ingredient` VALUES ('SRCR', 'Sour Cream', 'SAUCE');
INSERT INTO `Ingredient` VALUES ('TMTO', 'Diced Tomatos', 'VEGGIES');
COMMIT;

-- ----------------------------
-- Table structure for Taco
-- ----------------------------
DROP TABLE IF EXISTS `Taco`;
CREATE TABLE `Taco` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `created_at` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ta_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Taco
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for Taco_Ingredients
-- ----------------------------
DROP TABLE IF EXISTS `Taco_Ingredients`;
CREATE TABLE `Taco_Ingredients` (
  `ingredient` varchar(20) NOT NULL,
  `taco` int(50) NOT NULL,
  KEY `taco` (`taco`),
  KEY `ingredient` (`ingredient`),
  CONSTRAINT `Taco_Ingredients_ibfk_1` FOREIGN KEY (`taco`) REFERENCES `Taco` (`id`),
  CONSTRAINT `Taco_Ingredients_ibfk_2` FOREIGN KEY (`ingredient`) REFERENCES `Ingredient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Taco_Ingredients
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for Taco_Order
-- ----------------------------
DROP TABLE IF EXISTS `Taco_Order`;
CREATE TABLE `Taco_Order` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `placed_at` timestamp(6) NULL DEFAULT NULL,
  `delivery_name` varchar(50) DEFAULT NULL,
  `delivery_street` varchar(50) DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `cc_number` varchar(16) DEFAULT NULL,
  `delivery_city` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Taco_Order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for Taco_Order_Tacos
-- ----------------------------
DROP TABLE IF EXISTS `Taco_Order_Tacos`;
CREATE TABLE `Taco_Order_Tacos` (
  `taco_order` int(50) DEFAULT NULL,
  `taco` int(50) DEFAULT NULL,
  KEY `taco_order` (`taco_order`),
  KEY `taco` (`taco`),
  CONSTRAINT `Taco_Order_Tacos_ibfk_1` FOREIGN KEY (`taco_order`) REFERENCES `Taco_Order` (`id`),
  CONSTRAINT `Taco_Order_Tacos_ibfk_2` FOREIGN KEY (`taco`) REFERENCES `Taco` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Taco_Order_Tacos
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- View structure for current
-- ----------------------------
DROP VIEW IF EXISTS `current`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `current` AS select concat(`Taco`.`name`,`Taco_Order`.`id`) AS `concat(Taco.name,Taco_Order.id)` from (`Taco` join `Taco_Order`);

-- ----------------------------
-- Procedure structure for findingredients
-- ----------------------------
DROP PROCEDURE IF EXISTS `findingredients`;
delimiter ;;
CREATE PROCEDURE `findingredients`()
begin
	select id,name,type from Ingredient;  end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table Taco
-- ----------------------------
DROP TRIGGER IF EXISTS `tacoinsert`;
delimiter ;;
CREATE TRIGGER `tacoinsert` AFTER INSERT ON `Taco` FOR EACH ROW begin
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;

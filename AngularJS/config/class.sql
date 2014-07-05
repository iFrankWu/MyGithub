# Host: localhost  (Version: 5.0.20-nt)
# Date: 2014-02-26 16:17:55
# Generator: MySQL-Front 5.3  (Build 4.56)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "class"
#

DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(11) NOT NULL auto_increment,
  `className` varchar(128) NOT NULL,
  `classNotice` varchar(512) default NULL,
  `teacher` varchar(64) default NULL,
  `payAmount` float default NULL,
  `modifyDate` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `createDate` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "class"
#

INSERT INTO `class` VALUES (1,'206班','班级通知','李金兵',1200,'2014-02-22 17:14:32','2014-02-22 17:14:32'),(2,'温塘二中','你好，同学','袁铁兵',400,'2014-02-22 17:15:26','2014-02-22 17:15:26');
select * from stu_class where sid = 1 and isDeleteDiscount = false
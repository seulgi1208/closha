/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.1.66 : Database - closha
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`closha` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `closha`;

/*Table structure for table `cboard` */

DROP TABLE IF EXISTS `cboard`;

CREATE TABLE `cboard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `writer` varchar(20) COLLATE euckr_bin NOT NULL,
  `type` varchar(10) COLLATE euckr_bin NOT NULL,
  `summary` text COLLATE euckr_bin NOT NULL,
  `content` text COLLATE euckr_bin NOT NULL,
  `hitCount` int(11) DEFAULT '0',
  `recommandCount` int(11) DEFAULT '0',
  `email` varchar(50) COLLATE euckr_bin NOT NULL,
  `keyWord` text COLLATE euckr_bin,
  `date` varchar(50) COLLATE euckr_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COLLATE=euckr_bin;

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `writer` varchar(20) COLLATE euckr_bin NOT NULL,
  `comment` text COLLATE euckr_bin NOT NULL,
  `date` varchar(50) COLLATE euckr_bin NOT NULL,
  `email` varchar(50) COLLATE euckr_bin NOT NULL,
  `linkedNum` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COLLATE=euckr_bin;

/*Table structure for table `instancepipeline` */

DROP TABLE IF EXISTS `instancepipeline`;

CREATE TABLE `instancepipeline` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `instanceID` varchar(128) COLLATE euckr_bin NOT NULL,
  `pipelineID` varchar(128) COLLATE euckr_bin NOT NULL,
  `exeID` varchar(128) COLLATE euckr_bin DEFAULT NULL,
  `pipelineName` varchar(128) COLLATE euckr_bin NOT NULL,
  `instanceOwner` varchar(128) COLLATE euckr_bin NOT NULL,
  `ownerEmail` varchar(128) COLLATE euckr_bin NOT NULL,
  `instanceName` varchar(128) COLLATE euckr_bin NOT NULL,
  `instanceDesc` text COLLATE euckr_bin NOT NULL,
  `instanceXML` text COLLATE euckr_bin NOT NULL,
  `status` int(11) DEFAULT NULL,
  `exeCount` int(11) DEFAULT NULL,
  `createDate` varchar(50) COLLATE euckr_bin DEFAULT NULL,
  `register` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`,`instanceID`,`pipelineID`),
  UNIQUE KEY `INSTANCE_PIPELINE_KEY` (`instanceID`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=euckr COLLATE=euckr_bin;

/*Table structure for table `instancepipelinejob` */

DROP TABLE IF EXISTS `instancepipelinejob`;

CREATE TABLE `instancepipelinejob` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pipelineID` varchar(128) CHARACTER SET euckr NOT NULL,
  `instanceID` varchar(128) COLLATE euckr_bin NOT NULL,
  `exeID` varchar(128) COLLATE euckr_bin NOT NULL,
  `startDate` varchar(50) COLLATE euckr_bin DEFAULT NULL,
  `endDate` varchar(50) COLLATE euckr_bin DEFAULT NULL,
  PRIMARY KEY (`id`,`exeID`)
) ENGINE=InnoDB AUTO_INCREMENT=387 DEFAULT CHARSET=euckr COLLATE=euckr_bin;

/*Table structure for table `ontology` */

DROP TABLE IF EXISTS `ontology`;

CREATE TABLE `ontology` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ontologyID` varchar(128) NOT NULL,
  `ontologyName` varchar(128) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`,`ontologyID`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=euckr;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectID` varchar(128) COLLATE euckr_bin NOT NULL,
  `pipelineID` varchar(128) COLLATE euckr_bin NOT NULL,
  `projectName` varchar(128) COLLATE euckr_bin NOT NULL,
  `projectDesc` text COLLATE euckr_bin NOT NULL,
  `projectOwner` varchar(128) COLLATE euckr_bin NOT NULL,
  `assign` int(11) DEFAULT NULL,
  `eCheck` tinyint(1) DEFAULT NULL,
  `createDate` varchar(50) COLLATE euckr_bin DEFAULT NULL,
  PRIMARY KEY (`id`,`projectID`),
  UNIQUE KEY `PROJECT_KEY` (`projectID`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=euckr COLLATE=euckr_bin;

/*Table structure for table `registerpipeline` */

DROP TABLE IF EXISTS `registerpipeline`;

CREATE TABLE `registerpipeline` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pipelineID` varchar(128) COLLATE euckr_bin NOT NULL,
  `registerID` varchar(128) COLLATE euckr_bin NOT NULL,
  `ontologyID` varchar(128) COLLATE euckr_bin NOT NULL,
  `pipelineName` varchar(128) COLLATE euckr_bin NOT NULL,
  `pipelineDesc` text COLLATE euckr_bin NOT NULL,
  `pipelineAuthor` varchar(128) COLLATE euckr_bin NOT NULL,
  `registerDate` varchar(50) COLLATE euckr_bin NOT NULL,
  `updateDate` varchar(50) COLLATE euckr_bin NOT NULL,
  `pipelineXML` text COLLATE euckr_bin NOT NULL,
  `linkedKey` text COLLATE euckr_bin,
  `version` varchar(30) COLLATE euckr_bin NOT NULL,
  PRIMARY KEY (`id`,`pipelineID`,`registerID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=euckr COLLATE=euckr_bin;

/*Table structure for table `registerpipelinemodules` */

DROP TABLE IF EXISTS `registerpipelinemodules`;

CREATE TABLE `registerpipelinemodules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(32) COLLATE euckr_bin NOT NULL,
  `moduleID` varchar(128) COLLATE euckr_bin NOT NULL,
  `ontologyID` varchar(128) COLLATE euckr_bin NOT NULL,
  `appFormat` varchar(30) COLLATE euckr_bin NOT NULL,
  `language` varchar(128) COLLATE euckr_bin DEFAULT NULL,
  `scriptPath` varchar(512) COLLATE euckr_bin DEFAULT NULL,
  `cmd` varchar(512) COLLATE euckr_bin DEFAULT NULL,
  `moduleName` varchar(128) COLLATE euckr_bin NOT NULL,
  `moduleDesc` text COLLATE euckr_bin,
  `moduleAuthor` varchar(128) COLLATE euckr_bin NOT NULL,
  `registerDate` varchar(50) COLLATE euckr_bin NOT NULL,
  `updateDate` varchar(50) COLLATE euckr_bin DEFAULT NULL,
  `parameter` text COLLATE euckr_bin,
  `linkedKey` text COLLATE euckr_bin,
  `version` varchar(30) COLLATE euckr_bin NOT NULL,
  `icon` varchar(50) COLLATE euckr_bin DEFAULT NULL,
  PRIMARY KEY (`id`,`moduleID`),
  UNIQUE KEY `moduleKey` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=euckr COLLATE=euckr_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

CREATE DATABASE  IF NOT EXISTS `cs336database` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `cs336database`;
-- MySQL dump 10.13  Distrib 5.5.44, for debian-linux-gnu (x86_64)
--
-- Host: cs336-1.c28ltethrrc0.us-east-1.rds.amazonaws.com    Database: BarBeerDrinker
-- ------------------------------------------------------
-- Server version	5.6.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `username` varchar(45) NOT NULL,
  `level` int default 1,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;

INSERT INTO `staff` VALUES ('Jango' , '2');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table Structure for table 'Members'
DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `members` (
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `RUID` int(9) NOT NULL,
  `email` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `rID` int(9),
  `dID` int(9),
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;

INSERT INTO `members` VALUES ('Bruce' , 'Willis',  0, 'bw@sixthsense.com', 'brucewillis', 'diesinsixthsense', NULL, NULL);
INSERT INTO `members` VALUES ('Jango' , 'Fett', 2, 'jango', 'Jango', 'jangofett', NULL, NULL);
INSERT INTO `members` VALUES ('placeholder', 'placeholder', 123123123, 'placeholder', 'placeholder', 'placeholder', NULL, NULL);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

SELECT *
FROM members;

DROP TABLE IF EXISTS `drivers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drivers` (
  `dID` int(9) NOT NULL,
  `username` varchar(45) NOT NULL,
  `rating` float(4) NOT NULL,
  `nratings` numeric(9) NOT NULL,
  `#rides` numeric(9),
  PRIMARY KEY  (`dID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
SELECT *
FROM drivers;
    
    SELECT COUNT(*)
    FROM drivers d
    WHERE dID = 2;
    
DROP TABLE IF EXISTS `riders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `riders` (
  `rID` int(10) unsigned NOT NULL,
  `username` varchar(45) NOT NULL,
  `rating` float(4) NOT NULL,
  `nratings` int(9) NOT NULL,
  PRIMARY KEY  (`rID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `riders` WRITE;
/*!40000 ALTER TABLE `riders` DISABLE KEYS */;

INSERT INTO `riders` VALUES ('0' , 'placeholder', '0', '0');
/*!40000 ALTER TABLE `riders` ENABLE KEYS */;
UNLOCK TABLES;


DELETE FROM riders WHERE username='Boba';

SELECT *
FROM riders;

SELECT MAX(rID)
FROM riders;

SELECT COUNT(*) as cnt from riders where username='user';

DROP TABLE IF EXISTS `riderRequests`;
CREATE TABLE `riderRequests`(
	`rID` int(10) NOT NULL,
    `requestID` int(10) NOT NULL,
	`location` varchar(45) NOT NULL,
    `destination` varchar(45) NOT NULL,
    `desiredTime`  dateTime NOT NULL,
    `open` boolean default FALSE,
    PRIMARY KEY(`requestID`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `driverOffers`(
	`dID` int(10) NOT NULL,
    `offerID` int(10) NOT NULL,
    `location` varchar(45) NOT NULL,
    `destination` varchar(45) NOT NULL,
    `startTime` datetime NOT NULL,
    `endTime` datetime NOT NULL,
    `open` boolean default FALSE,
    `recurring` boolean default FALSE,
    PRIMARY KEY(`offerID`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `activeDrives`(
	`driveID` int(10) NOT NULL,
    `requestID` int(10) NOT NULL,
	`dID` int(10) NOT NULL,
    `rID` int(10) NOT NULL,
    PRIMARY KEY (`driveID`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `banned`(
	`banID` int(10) NOT NULL,
	`username` varchar(45) NOT NULL,
    `RUID` int(10) NOT NULL,
    `start` datetime NOT NULL,
    `end` datetime NOT NULL,
    PRIMARY KEY(`banID`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
    

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-24 15:40:46
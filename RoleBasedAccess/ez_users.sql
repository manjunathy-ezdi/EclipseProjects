-- MySQL dump 10.13  Distrib 5.7.12, for Linux (x86_64)
--
-- Host: localhost    Database: ez_users
-- ------------------------------------------------------
-- Server version	5.6.30

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

--
-- Table structure for table `ez_country_state_map`
--

DROP TABLE IF EXISTS `ez_country_state_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ez_country_state_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country_name` varchar(50) DEFAULT NULL,
  `state_name` varchar(50) DEFAULT NULL,
  `city_name` varchar(50) DEFAULT NULL,
  `zipcode` varchar(20) DEFAULT NULL,
  `timezone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `country_name` (`country_name`,`state_name`,`city_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ez_country_state_map`
--

LOCK TABLES `ez_country_state_map` WRITE;
/*!40000 ALTER TABLE `ez_country_state_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `ez_country_state_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ez_event_log`
--

DROP TABLE IF EXISTS `ez_event_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ez_event_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) unsigned NOT NULL,
  `actionType` enum('SUCCESS','FAILURE','LOGOUT','LOGIN','UNSUCCESSFUL') CHARACTER SET latin1 NOT NULL,
  `executiondate` datetime NOT NULL,
  `completiondate` datetime DEFAULT NULL,
  `sessionId` varchar(1000) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `ipAddress` varchar(15) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `isActive` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_EventLog_userId` (`userId`),
  CONSTRAINT `fk_EventLog_userId` FOREIGN KEY (`userId`) REFERENCES `UserLogin` (`userloginid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ez_event_log`
--

LOCK TABLES `ez_event_log` WRITE;
/*!40000 ALTER TABLE `ez_event_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `ez_event_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ez_physician_master`
--

DROP TABLE IF EXISTS `ez_physician_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ez_physician_master` (
  `physician_id` int(11) NOT NULL,
  `active_flag` int(11) DEFAULT '1',
  `clinic_id` int(11) DEFAULT NULL,
  `continue_the_samebucket` int(11) DEFAULT NULL,
  `cosign_mark` varchar(250) DEFAULT NULL,
  `created_by` varchar(250) DEFAULT NULL,
  `created_by_id` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `edit_demographics` int(11) DEFAULT NULL,
  `edit_document` int(11) DEFAULT NULL,
  `enable_cosign` int(11) DEFAULT NULL,
  `enable_tat_modification` int(11) DEFAULT NULL,
  `esign_confirmation_message` int(11) DEFAULT NULL,
  `esign_mark` varchar(250) DEFAULT NULL,
  `external_physician_id` varchar(250) DEFAULT NULL,
  `hospital_external_id` int(11) DEFAULT NULL,
  `hospital_id` int(11) DEFAULT NULL,
  `hospital_name` varchar(250) DEFAULT NULL,
  `lock_document_after_esign` int(11) DEFAULT NULL,
  `physician_address` varchar(50) DEFAULT NULL,
  `physician_city` varchar(50) DEFAULT NULL,
  `physician_country` varchar(50) DEFAULT NULL,
  `physician_doc_path` varchar(100) DEFAULT NULL,
  `physician_fax` varchar(50) DEFAULT NULL,
  `physician_mnemonic` varchar(50) DEFAULT NULL,
  `physician_name` varchar(50) DEFAULT NULL,
  `physician_phone` varchar(50) DEFAULT NULL,
  `physician_pin` int(11) DEFAULT NULL,
  `physician_short_name` varchar(50) DEFAULT NULL,
  `physician_state` varchar(50) DEFAULT NULL,
  `physician_telescriber_enabled` int(11) DEFAULT NULL,
  `physician_telescriber_id` int(11) DEFAULT NULL,
  `physician_wave_path` varchar(100) DEFAULT NULL,
  `physician_wavexml_path` varchar(100) DEFAULT NULL,
  `physician_zipcode` varchar(50) DEFAULT NULL,
  `reject_document` int(11) DEFAULT NULL,
  `speech_rec_enabled` int(11) DEFAULT '0',
  `speech_server_id` int(11) DEFAULT '0',
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_by_id` int(11) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `enable_esign` int(11) DEFAULT '0',
  `edit_reports_status` int(11) DEFAULT '0',
  `reject_reports_status` int(11) DEFAULT '0',
  `physician_mp3_path` varchar(250) DEFAULT NULL,
  `physician_initial` varchar(50) DEFAULT NULL,
  `esign_document` int(11) DEFAULT '0',
  PRIMARY KEY (`physician_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ez_physician_master`
--

LOCK TABLES `ez_physician_master` WRITE;
/*!40000 ALTER TABLE `ez_physician_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `ez_physician_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ez_secretary_physician_map`
--

DROP TABLE IF EXISTS `ez_secretary_physician_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ez_secretary_physician_map` (
  `secretary_id` int(11) NOT NULL DEFAULT '0',
  `secretary_firstname` varchar(50) DEFAULT NULL,
  `secretary_middlename` varchar(50) DEFAULT NULL,
  `secretary_lastname` varchar(50) DEFAULT NULL,
  `physician_id` int(11) NOT NULL DEFAULT '0',
  `physician_firstname` varchar(50) DEFAULT NULL,
  `physician_middlename` varchar(50) DEFAULT NULL,
  `physician_lastname` varchar(50) DEFAULT NULL,
  `hospital_id` int(11) DEFAULT NULL COMMENT 'secretary hospital_id',
  `hospital_name` varchar(250) DEFAULT NULL COMMENT 'secretary hospital_name',
  `physician_hospital_id` int(11) DEFAULT NULL,
  `physician_hospital_name` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`secretary_id`,`physician_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ez_secretary_physician_map`
--

LOCK TABLES `ez_secretary_physician_map` WRITE;
/*!40000 ALTER TABLE `ez_secretary_physician_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `ez_secretary_physician_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ez_user_authentication`
--

DROP TABLE IF EXISTS `ez_user_authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ez_user_authentication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `hospital_id` int(11) DEFAULT NULL,
  `active_flag` int(11) DEFAULT '1',
  `delete_flag` int(11) DEFAULT '0',
  `hospital_name` varchar(250) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password_expiry_date` date DEFAULT NULL,
  `password_reset_flag` tinyint(1) NOT NULL DEFAULT '1',
  `max_count_lock` tinyint(2) unsigned NOT NULL DEFAULT '5',
  `lock_expiration_time_in_min` smallint(5) unsigned NOT NULL DEFAULT '10',
  `last_login_attempt_datetime` datetime DEFAULT NULL,
  `password_expire_days` smallint(5) unsigned NOT NULL DEFAULT '90',
  `failed_count` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `state` varchar(255) DEFAULT NULL,
  `random_token` varchar(500) DEFAULT NULL,
  `token_expiry_date` datetime DEFAULT NULL,
  `is_locked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100066 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ez_user_authentication`
--

LOCK TABLES `ez_user_authentication` WRITE;
/*!40000 ALTER TABLE `ez_user_authentication` DISABLE KEYS */;
INSERT INTO `ez_user_authentication` VALUES (100063,'trump@ezdi.us','$2a$10$ozZ.4PWp8Jp9C1Q5rH1bTuEWs3IU3dC/5kYjc6hD4YjZIhuPemju6',4,1,1,0,'RUMC',1,'ROLE_1_ADMIN','trump',NULL,0,5,10,'2016-03-29 17:27:46',90,0,'Active',NULL,NULL,0),(100065,'clinton@ezdi.us','$2a$10$cnD1YAV65VbiR9Ukb1VU6upXy/JWgVwhwdoD/k0QwIqxdGSONj36a',100064,1,1,0,'RUMC',2,'ROLE_1_USER','clinton',NULL,0,5,10,NULL,90,0,'Active',NULL,NULL,0);
/*!40000 ALTER TABLE `ez_user_authentication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ez_user_master`
--

DROP TABLE IF EXISTS `ez_user_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ez_user_master` (
  `first_name` varchar(50) DEFAULT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `credential` varchar(10) DEFAULT NULL,
  `pneumonic` varchar(50) DEFAULT NULL,
  `email_id` varchar(50) DEFAULT NULL,
  `alternate_email_id` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `street` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `zipcode` varchar(15) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `timezone` varchar(30) DEFAULT NULL,
  `primary_phone_number` varchar(20) DEFAULT NULL,
  `secondary_phone_number` varchar(20) DEFAULT NULL,
  `fax_number` varchar(20) DEFAULT NULL,
  `pager_number` varchar(20) DEFAULT NULL,
  `hospital_id` int(11) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `main_station_id` varchar(50) DEFAULT NULL,
  `main_location` varchar(50) DEFAULT NULL,
  `speciality_name` varchar(50) DEFAULT NULL,
  `specaility_remark` varchar(50) DEFAULT NULL,
  `telescriber_name` varchar(50) DEFAULT NULL,
  `telescriber_location` varchar(50) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `active_flag` int(11) DEFAULT '1',
  `delete_flag` int(11) DEFAULT '0',
  `created_datetime` datetime DEFAULT NULL,
  `updated_datetime` datetime DEFAULT NULL,
  `is_mapped_secretary` int(11) DEFAULT '0',
  `created_by` int(11) DEFAULT NULL,
  `user_log_description` varchar(1000) DEFAULT NULL,
  `login_id` varchar(50) DEFAULT NULL,
  `username_importer` varchar(50) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `main_station` varchar(255) DEFAULT NULL,
  `page_number` varchar(255) DEFAULT NULL,
  `speciality_remark` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ez_user_master`
--

LOCK TABLES `ez_user_master` WRITE;
/*!40000 ALTER TABLE `ez_user_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `ez_user_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ez_user_master_log`
--

DROP TABLE IF EXISTS `ez_user_master_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ez_user_master_log` (
  `first_name` varchar(50) DEFAULT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `credential` varchar(10) DEFAULT NULL,
  `pneumonic` varchar(50) DEFAULT NULL,
  `email_id` varchar(50) DEFAULT NULL,
  `alternate_email_id` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `street` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `zipcode` varchar(15) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `timezone` varchar(30) DEFAULT NULL,
  `primary_phone_number` varchar(20) DEFAULT NULL,
  `secondary_phone_number` varchar(20) DEFAULT NULL,
  `fax_number` varchar(20) DEFAULT NULL,
  `pager_number` varchar(20) DEFAULT NULL,
  `hospital_id` int(11) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `main_station_id` varchar(50) DEFAULT NULL,
  `main_location` varchar(50) DEFAULT NULL,
  `speciality_name` varchar(50) DEFAULT NULL,
  `specaility_remark` varchar(50) DEFAULT NULL,
  `telescriber_name` varchar(50) DEFAULT NULL,
  `telescriber_location` varchar(50) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `active_flag` int(11) DEFAULT '1',
  `delete_flag` int(11) DEFAULT '0',
  `created_datetime` datetime NOT NULL,
  `updated_datetime` datetime DEFAULT NULL,
  `user_log_description` text,
  `is_mapped_secretary` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `login_id` varchar(50) DEFAULT NULL,
  `username_importer` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`created_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ez_user_master_log`
--

LOCK TABLES `ez_user_master_log` WRITE;
/*!40000 ALTER TABLE `ez_user_master_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `ez_user_master_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-12 16:06:34

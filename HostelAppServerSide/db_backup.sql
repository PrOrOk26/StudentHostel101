-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: hosteltasks101
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity_types`
--

DROP TABLE IF EXISTS `activity_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `activity_types` (
  `Activity_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `Activity_type_name` varchar(45) NOT NULL,
  `Activity_type_description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Activity_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_types`
--

LOCK TABLES `activity_types` WRITE;
/*!40000 ALTER TABLE `activity_types` DISABLE KEYS */;
INSERT INTO `activity_types` VALUES (1,'garbage','describes taking out garbage packages performed by users'),(2,'bread','describes buying bread performed by users');
/*!40000 ALTER TABLE `activity_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation`
--

DROP TABLE IF EXISTS `conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conversation` (
  `Conversation_id` int(11) NOT NULL AUTO_INCREMENT,
  `Conversation_Name` varchar(45) DEFAULT NULL,
  `Creator_login` varchar(15) NOT NULL,
  `Creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`Conversation_id`),
  KEY `fk_Conversation_Users1_idx` (`Creator_login`),
  CONSTRAINT `fk_Conversation_Users1` FOREIGN KEY (`Creator_login`) REFERENCES `users` (`user_login`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation`
--

LOCK TABLES `conversation` WRITE;
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
INSERT INTO `conversation` VALUES (33,'test','test1','2018-05-25 10:17:37'),(34,'test','test1','2018-05-25 10:17:37'),(35,'test','test2','2018-05-25 10:21:53'),(36,'test','test4','2018-05-25 10:24:25'),(37,'test','test4','2018-05-25 10:24:26'),(38,'test','test4','2018-05-25 10:24:26'),(41,'test','test2','2018-05-25 21:37:04'),(42,'test','test1','2018-05-25 21:38:07'),(43,'test','test5','2018-05-26 17:25:44'),(44,'test','test5','2018-05-26 17:25:44'),(45,'test','test6','2018-05-27 14:50:52'),(46,'test','test6','2018-05-27 14:50:53'),(47,'test','test6','2018-05-27 14:50:53'),(48,'test','test6','2018-05-27 14:50:53'),(49,'test','test6','2018-05-27 14:50:53');
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `message` (
  `Message_id` int(11) NOT NULL AUTO_INCREMENT,
  `Date_sent` datetime DEFAULT NULL,
  `Message_text` varchar(255) DEFAULT NULL,
  `Sender_Login` varchar(15) NOT NULL,
  `Conversation_id` int(11) NOT NULL,
  PRIMARY KEY (`Message_id`),
  KEY `fk_Message_Users1_idx` (`Sender_Login`),
  KEY `fk_Message_Conversation1_idx` (`Conversation_id`),
  CONSTRAINT `fk_Message_Conversation1` FOREIGN KEY (`Conversation_id`) REFERENCES `conversation` (`conversation_id`),
  CONSTRAINT `fk_Message_Users1` FOREIGN KEY (`Sender_Login`) REFERENCES `users` (`user_login`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'2018-05-25 17:28:26','test1','test1',33),(2,'2018-05-25 17:38:06','test1','test1',33),(3,'2018-05-25 19:37:44','davay','test1',33),(4,'2018-05-25 20:20:11','lol','test1',33),(5,'2018-05-25 20:56:43','fejdjdh','test1',33),(6,'2018-05-25 20:57:01','anu','test1',34),(7,'2018-05-25 20:57:04','anu2','test1',34),(8,'2018-05-25 20:57:06','anu3','test1',34),(9,'2018-05-25 20:57:08','anu4','test1',34),(10,'2018-05-25 20:57:10','anu5','test1',34),(11,'2018-05-25 20:57:24','ni','test1',36),(12,'2018-05-25 20:57:26','nidossk','test1',36),(13,'2018-05-25 20:57:27','nidosskfkk','test1',36),(14,'2018-05-25 20:57:29','nidosskfkklp','test1',36),(15,'2018-05-25 21:37:30','РѕР°РїРѕРѕ','test2',33),(16,'2018-05-25 21:37:41','РїСЂРёРІРЅС‚','test2',35),(17,'2018-05-27 13:52:37','djsnxdjdj','test2',37),(18,'2018-05-27 13:52:43','fjdjddj','test2',35),(19,'2018-05-27 14:49:52','rjcoeof','test2',41),(20,'2018-05-27 14:51:06','djdjfj','test6',49),(21,'2018-05-27 14:51:12','djdjf','test6',47);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagechat`
--

DROP TABLE IF EXISTS `messagechat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `messagechat` (
  `Message_text` varchar(250) NOT NULL,
  `Message_date_time` datetime NOT NULL,
  `User_Login` varchar(15) NOT NULL,
  `Message_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Message_id`),
  UNIQUE KEY `Message_id_UNIQUE` (`Message_id`),
  KEY `fk_MessageChat_Users1` (`User_Login`),
  CONSTRAINT `fk_MessageChat_Users1` FOREIGN KEY (`User_Login`) REFERENCES `users` (`user_login`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messagechat`
--

LOCK TABLES `messagechat` WRITE;
/*!40000 ALTER TABLE `messagechat` DISABLE KEYS */;
INSERT INTO `messagechat` VALUES ('davay','2018-05-16 19:41:36','test2',1),('davaygjsj','2018-05-16 19:41:39','test2',2),('djwdb','2018-05-16 19:41:47','test2',3),('djwdbfkenf','2018-05-16 19:41:48','test2',4),('sishxhe','2018-05-16 19:43:09','test1',5),('sishxhe','2018-05-16 19:43:12','test1',6),('sishxhe','2018-05-16 19:43:12','test1',7),('sishxhe','2018-05-16 19:43:13','test1',8),('sishxhe','2018-05-16 19:43:14','test1',9),('sishxhe','2018-05-16 19:43:14','test1',10),('sishxhe','2018-05-16 19:43:15','test1',11),('sishxhe','2018-05-16 19:43:15','test1',12),('sishxhe','2018-05-16 19:43:18','test1',13),('lol','2018-05-16 19:43:24','test1',14),('lol','2018-05-16 19:43:26','test1',15),('lol','2018-05-16 19:43:27','test1',16),('lol','2018-05-16 19:43:28','test1',17),('dldkf','2018-05-16 19:43:40','test1',18),('dldkf','2018-05-16 19:43:41','test1',19),('dldkf','2018-05-16 19:43:41','test1',20),('fgggg','2018-05-16 20:52:27','test1',21),('time','2018-05-17 10:07:28','test1',22),('gegvvg','2018-05-17 10:18:24','test1',23),('hefgh','2018-05-17 10:25:12','test1',24),('send1','2018-05-17 11:04:17','test1',25),('ikak?','2018-05-17 11:51:38','test1',26),('ceegsgwhyehbehehebehehehgwhevehehrhevehevehe','2018-05-22 16:49:23','test1',27),('ceegsgwhyehbehehebehehehgwhevehehrhevehevehergvdfbfbrbrbhrbdhdhjxxjjxjxjxxj','2018-05-22 16:49:32','test1',28),('fuwbshs','2018-05-25 10:26:26','test3',29),('test3G','2018-05-25 13:00:13','test1',30),('fefgg','2018-05-25 21:06:21','test1',31),('fefggghh','2018-05-25 21:06:25','test1',32),('zfdbj','2018-05-25 21:09:39','test3',33),('РїСЂРёРІРµС‚','2018-05-25 21:35:50','test5',34),('РїСЂРёРІРµС‚','2018-05-25 21:35:57','test5',35),('djwjfjf','2018-05-27 14:49:40','test2',36),('test6fkdkfk','2018-05-27 14:50:41','test6',37);
/*!40000 ALTER TABLE `messagechat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participants`
--

DROP TABLE IF EXISTS `participants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `participants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Entrance_date` datetime DEFAULT NULL,
  `Participant_Login` varchar(15) NOT NULL,
  `Conversation_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Participants_Users1_idx` (`Participant_Login`),
  KEY `fk_Participants_Conversation1_idx` (`Conversation_id`),
  CONSTRAINT `fk_Participants_Conversation1` FOREIGN KEY (`Conversation_id`) REFERENCES `conversation` (`conversation_id`),
  CONSTRAINT `fk_Participants_Users1` FOREIGN KEY (`Participant_Login`) REFERENCES `users` (`user_login`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participants`
--

LOCK TABLES `participants` WRITE;
/*!40000 ALTER TABLE `participants` DISABLE KEYS */;
INSERT INTO `participants` VALUES (28,'2018-05-25 10:17:37','test2',33),(29,'2018-05-25 10:17:37','test1',33),(30,'2018-05-25 10:17:37','test3',34),(31,'2018-05-25 10:17:37','test1',34),(32,'2018-05-25 10:21:53','test3',35),(33,'2018-05-25 10:21:53','test2',35),(34,'2018-05-25 10:24:25','test1',36),(35,'2018-05-25 10:24:25','test4',36),(36,'2018-05-25 10:24:26','test2',37),(37,'2018-05-25 10:24:26','test4',37),(38,'2018-05-25 10:24:26','test3',38),(39,'2018-05-25 10:24:26','test4',38),(40,'2018-05-25 21:37:04','test5',41),(41,'2018-05-25 21:37:05','test2',41),(42,'2018-05-25 21:38:08','test5',42),(43,'2018-05-25 21:38:08','test1',42),(44,'2018-05-26 17:25:44','test3',43),(45,'2018-05-26 17:25:44','test5',43),(46,'2018-05-26 17:25:44','test4',44),(47,'2018-05-26 17:25:44','test5',44),(48,'2018-05-27 14:50:52','test1',45),(49,'2018-05-27 14:50:52','test6',45),(50,'2018-05-27 14:50:53','test2',46),(51,'2018-05-27 14:50:53','test6',46),(52,'2018-05-27 14:50:53','test3',47),(53,'2018-05-27 14:50:53','test6',47),(54,'2018-05-27 14:50:53','test4',48),(55,'2018-05-27 14:50:53','test6',48),(56,'2018-05-27 14:50:53','test5',49),(57,'2018-05-27 14:50:53','test6',49);
/*!40000 ALTER TABLE `participants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `User_Name` varchar(20) NOT NULL,
  `User_Surname` varchar(20) NOT NULL,
  `User_Login` varchar(15) NOT NULL,
  `User_Password` varchar(45) DEFAULT NULL,
  `User_Group` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`User_Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('goebxb','ckskdnd','test1','qwerty','davay'),('xienxj','djsncdj','test2','qwerty','davay'),('dekfnn','vjdkdkf','test3','qwerty','davay'),('fjdjdj','fjejfj','test4','qwerty','KPP'),('gfgjj','hfgjggh','test5','qwerty','KPP'),('tidkfjf','fkekfkr','test6','qwerty',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_activities`
--

DROP TABLE IF EXISTS `users_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_activities` (
  `Users_activities_datetime` datetime NOT NULL,
  `Users_User_Login` varchar(15) NOT NULL,
  `Activity_type_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Activity_type_id`,`Users_User_Login`,`Users_activities_datetime`),
  KEY `fk_Users_activities_Users1_idx` (`Users_User_Login`),
  CONSTRAINT `fk_Users_activities_Activity_types1` FOREIGN KEY (`Activity_type_id`) REFERENCES `activity_types` (`activity_type_id`),
  CONSTRAINT `fk_Users_activities_Users1` FOREIGN KEY (`Users_User_Login`) REFERENCES `users` (`user_login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_activities`
--

LOCK TABLES `users_activities` WRITE;
/*!40000 ALTER TABLE `users_activities` DISABLE KEYS */;
INSERT INTO `users_activities` VALUES ('2018-05-20 18:13:50','test1',1),('2018-05-20 18:40:27','test1',1),('2018-05-20 18:52:18','test1',1),('2018-05-20 18:56:02','test1',1),('2018-05-20 19:49:18','test1',1),('2018-05-20 20:24:16','test1',1),('2018-05-20 20:26:03','test1',1),('2018-05-20 20:26:07','test1',1),('2018-05-20 20:31:35','test1',1),('2018-05-20 20:31:54','test1',1),('2018-05-20 21:12:32','test1',1),('2018-05-22 16:28:00','test1',1),('2018-05-26 18:26:23','test1',1),('2018-05-26 18:28:02','test1',1),('2018-05-26 18:28:22','test1',1),('2018-05-26 18:28:39','test1',1),('2018-05-26 18:28:56','test1',1),('2018-05-26 18:29:47','test1',1),('2018-05-26 18:31:51','test1',1),('2018-05-26 18:32:05','test1',1),('2018-05-26 18:34:34','test1',1),('2018-05-26 19:09:50','test1',1),('2018-05-27 13:32:50','test1',1),('2018-05-27 13:47:41','test1',1),('2018-05-27 14:03:34','test1',1),('2018-05-27 14:25:56','test1',1),('2018-05-27 14:32:12','test1',1),('2018-05-27 14:43:22','test1',1),('2018-05-27 14:48:17','test1',1),('2018-05-20 18:43:41','test1',2),('2018-05-20 18:48:52','test1',2),('2018-05-20 18:51:30','test1',2),('2018-05-20 18:56:37','test1',2),('2018-05-20 20:24:19','test1',2),('2018-05-20 20:26:11','test1',2),('2018-05-20 20:26:12','test1',2),('2018-05-20 20:31:38','test1',2),('2018-05-20 20:31:57','test1',2),('2018-05-20 21:12:34','test1',2),('2018-05-22 16:28:16','test1',2),('2018-05-26 18:28:19','test1',2),('2018-05-26 18:31:54','test1',2),('2018-05-27 13:33:05','test1',2),('2018-05-27 13:47:35','test1',2),('2018-05-27 14:02:17','test1',2),('2018-05-22 16:35:11','test2',1),('2018-05-25 21:36:26','test2',1),('2018-05-27 13:45:24','test2',1),('2018-05-27 14:23:09','test2',1),('2018-05-27 14:27:10','test2',1),('2018-05-22 16:35:23','test2',2),('2018-05-22 16:35:26','test2',2),('2018-05-25 21:36:39','test2',2),('2018-05-27 13:45:35','test2',2),('2018-05-26 18:27:28','test3',1),('2018-05-27 13:46:26','test3',1),('2018-05-26 18:27:23','test3',2),('2018-05-27 13:46:28','test3',2),('2018-05-27 13:55:05','test4',1),('2018-05-27 13:55:10','test4',2);
/*!40000 ALTER TABLE `users_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersnotes`
--

DROP TABLE IF EXISTS `usersnotes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usersnotes` (
  `Note_id` int(11) NOT NULL,
  `Note_text` varchar(250) DEFAULT NULL,
  `Users_User_Login` varchar(15) NOT NULL,
  `Note_date` datetime DEFAULT NULL,
  `Note_Likes` int(11) DEFAULT NULL,
  `Note_Dislikes` int(11) DEFAULT NULL,
  PRIMARY KEY (`Note_id`),
  KEY `fk_UsersNotes_Users1_idx` (`Users_User_Login`),
  CONSTRAINT `fk_UsersNotes_Users1` FOREIGN KEY (`Users_User_Login`) REFERENCES `users` (`user_login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersnotes`
--

LOCK TABLES `usersnotes` WRITE;
/*!40000 ALTER TABLE `usersnotes` DISABLE KEYS */;
/*!40000 ALTER TABLE `usersnotes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-28 13:07:09

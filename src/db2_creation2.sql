-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: database2
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ActivationSchedule`
--

DROP TABLE IF EXISTS `ActivationSchedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ActivationSchedule` (
  `orderId` int NOT NULL,
  `dateStart` date NOT NULL,
  `dateEnd` date NOT NULL,
  PRIMARY KEY (`orderId`),
  CONSTRAINT `orderActSchedule` FOREIGN KEY (`orderId`) REFERENCES `Orders` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ActivationSchedule`
--

LOCK TABLES `ActivationSchedule` WRITE;
/*!40000 ALTER TABLE `ActivationSchedule` DISABLE KEYS */;
INSERT INTO `ActivationSchedule` VALUES (181,'2022-05-10','2023-05-10'),(182,'2022-05-10','2023-05-10'),(183,'2022-05-19','2023-05-19'),(184,'2022-05-25','2023-05-25'),(185,'2022-05-12','2023-05-12'),(186,'2022-05-25','2023-05-25'),(187,'2022-05-20','2023-05-20'),(188,'2022-05-12','2023-05-12'),(189,'2022-05-19','2023-05-19');
/*!40000 ALTER TABLE `ActivationSchedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AuditingTable`
--

DROP TABLE IF EXISTS `AuditingTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AuditingTable` (
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `amount` int NOT NULL,
  `dateTime` datetime NOT NULL,
  PRIMARY KEY (`username`),
  KEY `username_idx` (`username`),
  CONSTRAINT `customerUsername` FOREIGN KEY (`username`) REFERENCES `UserCustomer` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AuditingTable`
--

LOCK TABLES `AuditingTable` WRITE;
/*!40000 ALTER TABLE `AuditingTable` DISABLE KEYS */;
INSERT INTO `AuditingTable` VALUES ('a','a@a',36,'2022-05-08 15:16:02');
/*!40000 ALTER TABLE `AuditingTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AveragePackageOptionalProducts`
--

DROP TABLE IF EXISTS `AveragePackageOptionalProducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AveragePackageOptionalProducts` (
  `servicePackage` varchar(45) NOT NULL,
  `average` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`servicePackage`),
  CONSTRAINT `averagepackageoptionalproducts_ibfk_1` FOREIGN KEY (`servicePackage`) REFERENCES `ServicePackage` (`PackageName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AveragePackageOptionalProducts`
--

LOCK TABLES `AveragePackageOptionalProducts` WRITE;
/*!40000 ALTER TABLE `AveragePackageOptionalProducts` DISABLE KEYS */;
INSERT INTO `AveragePackageOptionalProducts` VALUES ('nuovo ',0.00),('nuovo op',0.00);
/*!40000 ALTER TABLE `AveragePackageOptionalProducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BestOptional`
--

DROP TABLE IF EXISTS `BestOptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BestOptional` (
  `optionalProduct` varchar(45) NOT NULL,
  `pricePerMonth` decimal(10,2) NOT NULL DEFAULT '0.00',
  `totSales` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`optionalProduct`),
  CONSTRAINT `bestoptional_ibfk_1` FOREIGN KEY (`optionalProduct`) REFERENCES `OptionalProduct` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BestOptional`
--

LOCK TABLES `BestOptional` WRITE;
/*!40000 ALTER TABLE `BestOptional` DISABLE KEYS */;
/*!40000 ALTER TABLE `BestOptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FailedPayment`
--

DROP TABLE IF EXISTS `FailedPayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FailedPayment` (
  `idFailedPayment` int NOT NULL AUTO_INCREMENT,
  `amount` decimal(10,2) NOT NULL,
  `DateTime` varchar(45) NOT NULL,
  `failedUser` varchar(45) NOT NULL,
  `orderIdFailed` int NOT NULL,
  PRIMARY KEY (`idFailedPayment`),
  UNIQUE KEY `idFailedPayment_UNIQUE` (`idFailedPayment`),
  KEY `username_idx` (`failedUser`),
  KEY `orderIdFailed_idx` (`orderIdFailed`),
  CONSTRAINT `idOrder` FOREIGN KEY (`orderIdFailed`) REFERENCES `Orders` (`orderId`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FailedPayment`
--

LOCK TABLES `FailedPayment` WRITE;
/*!40000 ALTER TABLE `FailedPayment` DISABLE KEYS */;
/*!40000 ALTER TABLE `FailedPayment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `InternetService`
--

DROP TABLE IF EXISTS `InternetService`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `InternetService` (
  `name` varchar(45) NOT NULL,
  `gigaNum` int NOT NULL,
  `extraFees` decimal(10,3) NOT NULL,
  `fixedInternet` int NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InternetService`
--

LOCK TABLES `InternetService` WRITE;
/*!40000 ALTER TABLE `InternetService` DISABLE KEYS */;
/*!40000 ALTER TABLE `InternetService` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MobilePhoneService`
--

DROP TABLE IF EXISTS `MobilePhoneService`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MobilePhoneService` (
  `name` varchar(45) NOT NULL,
  `minutesNum` int NOT NULL,
  `smsNum` int NOT NULL,
  `extraMinFee` decimal(10,3) NOT NULL,
  `extraSmsFee` decimal(10,3) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `serviceName_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MobilePhoneService`
--

LOCK TABLES `MobilePhoneService` WRITE;
/*!40000 ALTER TABLE `MobilePhoneService` DISABLE KEYS */;
/*!40000 ALTER TABLE `MobilePhoneService` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MontlyFee`
--

DROP TABLE IF EXISTS `MontlyFee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MontlyFee` (
  `IdMontlyFee` int NOT NULL AUTO_INCREMENT,
  `12MonthPrice` decimal(7,3) NOT NULL,
  `24MonthPrice` decimal(7,3) NOT NULL,
  `36MonthPrice` decimal(7,3) NOT NULL,
  PRIMARY KEY (`IdMontlyFee`),
  UNIQUE KEY `IdMontlyFee_UNIQUE` (`IdMontlyFee`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MontlyFee`
--

LOCK TABLES `MontlyFee` WRITE;
/*!40000 ALTER TABLE `MontlyFee` DISABLE KEYS */;
INSERT INTO `MontlyFee` VALUES (11,1.000,1.000,1.000);
/*!40000 ALTER TABLE `MontlyFee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OptionalOrdered`
--

DROP TABLE IF EXISTS `OptionalOrdered`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OptionalOrdered` (
  `optionalProductId` varchar(45) NOT NULL,
  `orderId` int NOT NULL,
  PRIMARY KEY (`optionalProductId`,`orderId`),
  KEY `order_idx` (`orderId`),
  CONSTRAINT `optionalProduct` FOREIGN KEY (`optionalProductId`) REFERENCES `OptionalProduct` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order` FOREIGN KEY (`orderId`) REFERENCES `Orders` (`orderId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OptionalOrdered`
--

LOCK TABLES `OptionalOrdered` WRITE;
/*!40000 ALTER TABLE `OptionalOrdered` DISABLE KEYS */;
INSERT INTO `OptionalOrdered` VALUES ('op1',180),('op2',180);
/*!40000 ALTER TABLE `OptionalOrdered` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OptionalProduct`
--

DROP TABLE IF EXISTS `OptionalProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OptionalProduct` (
  `name` varchar(45) NOT NULL,
  `montlyFee` decimal(10,3) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OptionalProduct`
--

LOCK TABLES `OptionalProduct` WRITE;
/*!40000 ALTER TABLE `OptionalProduct` DISABLE KEYS */;
INSERT INTO `OptionalProduct` VALUES ('op1',1.000),('op2',2.000),('op3',3.000);
/*!40000 ALTER TABLE `OptionalProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `optionalproductsperorder`
--

DROP TABLE IF EXISTS `optionalproductsperorder`;
/*!50001 DROP VIEW IF EXISTS `optionalproductsperorder`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `optionalproductsperorder` AS SELECT 
 1 AS `orderId`,
 1 AS `servicePackage`,
 1 AS `numOrdered`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `ValidityPeriodMonth` int NOT NULL,
  `Valid` int NOT NULL DEFAULT '1',
  `DateStart` date NOT NULL,
  `OrderDateTime` datetime NOT NULL,
  `TotalCost` decimal(10,2) NOT NULL,
  `userOrder` varchar(45) NOT NULL,
  `orderedService` varchar(45) NOT NULL,
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `OrderId_UNIQUE` (`orderId`),
  KEY `userOrder_idx` (`userOrder`),
  KEY `orderedService_idx` (`orderedService`),
  CONSTRAINT `orderedService` FOREIGN KEY (`orderedService`) REFERENCES `ServicePackage` (`PackageName`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `userOrder` FOREIGN KEY (`userOrder`) REFERENCES `UserCustomer` (`username`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (178,12,1,'2022-05-11','2022-05-08 14:41:45',12.00,'a','no op'),(179,12,1,'2022-05-20','2022-05-08 14:42:00',12.00,'a','con op123'),(180,12,1,'2022-05-18','2022-05-08 14:42:11',36.00,'a','con op123'),(181,12,1,'2022-05-10','2022-05-08 15:15:51',12.00,'a','con op123'),(182,12,1,'2022-05-10','2022-05-08 15:30:28',12.00,'a','no op'),(183,12,1,'2022-05-19','2022-05-08 15:31:21',12.00,'a','con op123'),(184,12,1,'2022-05-25','2022-05-08 15:31:35',12.00,'a','no op'),(185,12,1,'2022-05-12','2022-05-08 15:32:28',12.00,'a','no op'),(186,12,1,'2022-05-25','2022-05-08 15:32:54',12.00,'a','con op123'),(187,12,1,'2022-05-20','2022-05-08 15:33:44',12.00,'a','nuovo op'),(188,12,1,'2022-05-12','2022-05-08 15:34:04',12.00,'a','nuovo op'),(189,12,1,'2022-05-19','2022-05-08 15:34:18',12.00,'a','nuovo op');
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PkgServiceInternet`
--

DROP TABLE IF EXISTS `PkgServiceInternet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PkgServiceInternet` (
  `packageService` varchar(45) NOT NULL,
  `internetService` varchar(45) NOT NULL,
  PRIMARY KEY (`packageService`,`internetService`),
  KEY `internetService_idx` (`internetService`),
  CONSTRAINT `internetService` FOREIGN KEY (`internetService`) REFERENCES `InternetService` (`name`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `packageService` FOREIGN KEY (`packageService`) REFERENCES `ServicePackage` (`PackageName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PkgServiceInternet`
--

LOCK TABLES `PkgServiceInternet` WRITE;
/*!40000 ALTER TABLE `PkgServiceInternet` DISABLE KEYS */;
/*!40000 ALTER TABLE `PkgServiceInternet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PkgServicePhone`
--

DROP TABLE IF EXISTS `PkgServicePhone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PkgServicePhone` (
  `servicePackage` varchar(45) NOT NULL,
  `mobilePhone` varchar(45) NOT NULL,
  PRIMARY KEY (`servicePackage`,`mobilePhone`),
  KEY `mobPhone_idx` (`mobilePhone`),
  CONSTRAINT `mobPhone` FOREIGN KEY (`mobilePhone`) REFERENCES `MobilePhoneService` (`name`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `servicePkg` FOREIGN KEY (`servicePackage`) REFERENCES `ServicePackage` (`PackageName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PkgServicePhone`
--

LOCK TABLES `PkgServicePhone` WRITE;
/*!40000 ALTER TABLE `PkgServicePhone` DISABLE KEYS */;
/*!40000 ALTER TABLE `PkgServicePhone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServicePackage`
--

DROP TABLE IF EXISTS `ServicePackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ServicePackage` (
  `PackageName` varchar(45) NOT NULL,
  `fixedPhoneNumber` int NOT NULL,
  `packageFees` int NOT NULL,
  PRIMARY KEY (`PackageName`),
  UNIQUE KEY `Name_UNIQUE` (`PackageName`),
  KEY `packageFees_idx` (`packageFees`),
  CONSTRAINT `packageFees` FOREIGN KEY (`packageFees`) REFERENCES `MontlyFee` (`IdMontlyFee`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServicePackage`
--

LOCK TABLES `ServicePackage` WRITE;
/*!40000 ALTER TABLE `ServicePackage` DISABLE KEYS */;
INSERT INTO `ServicePackage` VALUES ('con op123',0,11),('no op',0,11),('nuovo ',0,11),('nuovo op',0,11);
/*!40000 ALTER TABLE `ServicePackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServicePackageOptional`
--

DROP TABLE IF EXISTS `ServicePackageOptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ServicePackageOptional` (
  `optionalProduct` varchar(45) NOT NULL,
  `servicePackage` varchar(45) NOT NULL,
  PRIMARY KEY (`optionalProduct`,`servicePackage`),
  KEY `servicePackage_idx` (`servicePackage`),
  CONSTRAINT `optionalProd` FOREIGN KEY (`optionalProduct`) REFERENCES `OptionalProduct` (`name`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `servicePackage` FOREIGN KEY (`servicePackage`) REFERENCES `ServicePackage` (`PackageName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServicePackageOptional`
--

LOCK TABLES `ServicePackageOptional` WRITE;
/*!40000 ALTER TABLE `ServicePackageOptional` DISABLE KEYS */;
INSERT INTO `ServicePackageOptional` VALUES ('op1','con op123'),('op2','con op123'),('op3','con op123'),('op1','nuovo op'),('op2','nuovo op'),('op3','nuovo op');
/*!40000 ALTER TABLE `ServicePackageOptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TotalPackageSales`
--

DROP TABLE IF EXISTS `TotalPackageSales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TotalPackageSales` (
  `servicePackage` varchar(45) NOT NULL,
  `totSalesWithOp` decimal(10,2) NOT NULL DEFAULT '0.00',
  `totSalesWithoutOP` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`servicePackage`),
  CONSTRAINT `totalpackagesales_ibfk_1` FOREIGN KEY (`servicePackage`) REFERENCES `ServicePackage` (`PackageName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TotalPackageSales`
--

LOCK TABLES `TotalPackageSales` WRITE;
/*!40000 ALTER TABLE `TotalPackageSales` DISABLE KEYS */;
INSERT INTO `TotalPackageSales` VALUES ('nuovo ',0.00,0.00),('nuovo op',36.00,36.00);
/*!40000 ALTER TABLE `TotalPackageSales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TotalPurchasesPerPacket`
--

DROP TABLE IF EXISTS `TotalPurchasesPerPacket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TotalPurchasesPerPacket` (
  `servicePackage` varchar(45) NOT NULL,
  `purchases` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`servicePackage`),
  CONSTRAINT `totalpurchasesperpacket_ibfk_1` FOREIGN KEY (`servicePackage`) REFERENCES `ServicePackage` (`PackageName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TotalPurchasesPerPacket`
--

LOCK TABLES `TotalPurchasesPerPacket` WRITE;
/*!40000 ALTER TABLE `TotalPurchasesPerPacket` DISABLE KEYS */;
INSERT INTO `TotalPurchasesPerPacket` VALUES ('nuovo ',0),('nuovo op',3);
/*!40000 ALTER TABLE `TotalPurchasesPerPacket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TotalPurchasesPerPacketValidityPeriod`
--

DROP TABLE IF EXISTS `TotalPurchasesPerPacketValidityPeriod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TotalPurchasesPerPacketValidityPeriod` (
  `servicePackage` varchar(45) NOT NULL,
  `purchases12` int NOT NULL DEFAULT '0',
  `purchases24` int NOT NULL DEFAULT '0',
  `purchases36` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`servicePackage`),
  CONSTRAINT `totalpurchasesperpacketvalidityperiod_ibfk_1` FOREIGN KEY (`servicePackage`) REFERENCES `ServicePackage` (`PackageName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TotalPurchasesPerPacketValidityPeriod`
--

LOCK TABLES `TotalPurchasesPerPacketValidityPeriod` WRITE;
/*!40000 ALTER TABLE `TotalPurchasesPerPacketValidityPeriod` DISABLE KEYS */;
INSERT INTO `TotalPurchasesPerPacketValidityPeriod` VALUES ('nuovo ',0,0,0),('nuovo op',3,0,0);
/*!40000 ALTER TABLE `TotalPurchasesPerPacketValidityPeriod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserCustomer`
--

DROP TABLE IF EXISTS `UserCustomer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserCustomer` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `solvent` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserCustomer`
--

LOCK TABLES `UserCustomer` WRITE;
/*!40000 ALTER TABLE `UserCustomer` DISABLE KEYS */;
INSERT INTO `UserCustomer` VALUES ('a','a','a@a',1);
/*!40000 ALTER TABLE `UserCustomer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserEmployee`
--

DROP TABLE IF EXISTS `UserEmployee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserEmployee` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserEmployee`
--

LOCK TABLES `UserEmployee` WRITE;
/*!40000 ALTER TABLE `UserEmployee` DISABLE KEYS */;
INSERT INTO `UserEmployee` VALUES ('a','a');
/*!40000 ALTER TABLE `UserEmployee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `optionalproductsperorder`
--

/*!50001 DROP VIEW IF EXISTS `optionalproductsperorder`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `optionalproductsperorder` AS select `actSched`.`orderId` AS `orderId`,`sp`.`PackageName` AS `servicePackage`,count(`oo`.`orderId`) AS `numOrdered` from (((`activationschedule` `actSched` left join `optionalordered` `oo` on((`actSched`.`orderId` = `oo`.`orderId`))) join `orders` `o`) join `servicepackage` `sp`) where ((`o`.`orderId` = `actSched`.`orderId`) and (`sp`.`PackageName` = `o`.`orderedService`)) group by `actSched`.`orderId` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-08 15:35:57

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
-- Dumping data for table `ActivationSchedule`
--

LOCK TABLES `ActivationSchedule` WRITE;
/*!40000 ALTER TABLE `ActivationSchedule` DISABLE KEYS */;
INSERT INTO `ActivationSchedule` VALUES (71,'2022-05-12','2025-05-12',178),(72,'2022-05-11','2023-05-11',179);
/*!40000 ALTER TABLE `ActivationSchedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `AuditingTable`
--

LOCK TABLES `AuditingTable` WRITE;
/*!40000 ALTER TABLE `AuditingTable` DISABLE KEYS */;
/*!40000 ALTER TABLE `AuditingTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `AveragePackageOptionalProducts`
--

LOCK TABLES `AveragePackageOptionalProducts` WRITE;
/*!40000 ALTER TABLE `AveragePackageOptionalProducts` DISABLE KEYS */;
INSERT INTO `AveragePackageOptionalProducts` VALUES ('family',0.00),('Mobile Base',0.00),('Mobile Platinum',0.00),('Mobile Premium',0.00);
/*!40000 ALTER TABLE `AveragePackageOptionalProducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `BestOptional`
--

LOCK TABLES `BestOptional` WRITE;
/*!40000 ALTER TABLE `BestOptional` DISABLE KEYS */;
INSERT INTO `BestOptional` VALUES ('hotspot',1.20,0.00),('roaming extra UE',2.30,0.00),('roaming UE',2.00,0.00),('Vision',4.99,0.00);
/*!40000 ALTER TABLE `BestOptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `FailedPayment`
--

LOCK TABLES `FailedPayment` WRITE;
/*!40000 ALTER TABLE `FailedPayment` DISABLE KEYS */;
/*!40000 ALTER TABLE `FailedPayment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `InternetService`
--

LOCK TABLES `InternetService` WRITE;
/*!40000 ALTER TABLE `InternetService` DISABLE KEYS */;
INSERT INTO `InternetService` VALUES ('4G',100,0.700,0),('5G platinum',300,0.800,0),('5G premium',150,0.800,0),('fixed internet illimitate',1000,0.000,1),('medium fixed internet',100,1.000,1);
/*!40000 ALTER TABLE `InternetService` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `MobilePhoneService`
--

LOCK TABLES `MobilePhoneService` WRITE;
/*!40000 ALTER TABLE `MobilePhoneService` DISABLE KEYS */;
INSERT INTO `MobilePhoneService` VALUES ('mobile phone base',200,250,0.500,0.500),('mobile phone illimitate',1000,1000,0.000,0.000);
/*!40000 ALTER TABLE `MobilePhoneService` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `MontlyFee`
--

LOCK TABLES `MontlyFee` WRITE;
/*!40000 ALTER TABLE `MontlyFee` DISABLE KEYS */;
INSERT INTO `MontlyFee` VALUES (11,35.990,29.990,25.990),(12,15.990,9.990,7.990),(13,7.990,6.990,5.990),(14,9.990,8.990,7.990);
/*!40000 ALTER TABLE `MontlyFee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `OptionalOrdered`
--

LOCK TABLES `OptionalOrdered` WRITE;
/*!40000 ALTER TABLE `OptionalOrdered` DISABLE KEYS */;
/*!40000 ALTER TABLE `OptionalOrdered` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `OptionalProduct`
--

LOCK TABLES `OptionalProduct` WRITE;
/*!40000 ALTER TABLE `OptionalProduct` DISABLE KEYS */;
INSERT INTO `OptionalProduct` VALUES ('hotspot',1.200),('roaming extra UE',2.300),('roaming UE',2.000),('Vision',4.990);
/*!40000 ALTER TABLE `OptionalProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (178,36,1,'2022-05-12','2022-05-07 23:00:46',935.64,'laura','family'),(179,12,1,'2022-05-11','2022-05-07 23:01:07',191.88,'laura','Mobile Base');
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PkgServiceInternet`
--

LOCK TABLES `PkgServiceInternet` WRITE;
/*!40000 ALTER TABLE `PkgServiceInternet` DISABLE KEYS */;
INSERT INTO `PkgServiceInternet` VALUES ('family','4G'),('Mobile Base','4G'),('Mobile Platinum','5G platinum'),('family','5G premium'),('Mobile Premium','5G premium'),('family','fixed internet illimitate');
/*!40000 ALTER TABLE `PkgServiceInternet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PkgServicePhone`
--

LOCK TABLES `PkgServicePhone` WRITE;
/*!40000 ALTER TABLE `PkgServicePhone` DISABLE KEYS */;
INSERT INTO `PkgServicePhone` VALUES ('family','mobile phone base'),('Mobile Base','mobile phone base'),('family','mobile phone illimitate'),('Mobile Platinum','mobile phone illimitate'),('Mobile Premium','mobile phone illimitate');
/*!40000 ALTER TABLE `PkgServicePhone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ServicePackage`
--

LOCK TABLES `ServicePackage` WRITE;
/*!40000 ALTER TABLE `ServicePackage` DISABLE KEYS */;
INSERT INTO `ServicePackage` VALUES ('family',1,11),('Mobile Base',0,12),('Mobile Platinum',0,14),('Mobile Premium',0,13);
/*!40000 ALTER TABLE `ServicePackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ServicePackageOptional`
--

LOCK TABLES `ServicePackageOptional` WRITE;
/*!40000 ALTER TABLE `ServicePackageOptional` DISABLE KEYS */;
INSERT INTO `ServicePackageOptional` VALUES ('hotspot','family'),('roaming extra UE','family'),('roaming UE','family'),('Vision','family'),('hotspot','Mobile Base'),('roaming extra UE','Mobile Base'),('roaming UE','Mobile Base'),('hotspot','Mobile Platinum'),('roaming extra UE','Mobile Platinum'),('roaming UE','Mobile Platinum'),('Vision','Mobile Platinum');
/*!40000 ALTER TABLE `ServicePackageOptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TotalPackageSales`
--

LOCK TABLES `TotalPackageSales` WRITE;
/*!40000 ALTER TABLE `TotalPackageSales` DISABLE KEYS */;
INSERT INTO `TotalPackageSales` VALUES ('family',1127.52,1127.52),('Mobile Base',1127.52,1127.52),('Mobile Platinum',1127.52,1127.52),('Mobile Premium',1127.52,1127.52);
/*!40000 ALTER TABLE `TotalPackageSales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TotalPurchasesPerPacket`
--

LOCK TABLES `TotalPurchasesPerPacket` WRITE;
/*!40000 ALTER TABLE `TotalPurchasesPerPacket` DISABLE KEYS */;
INSERT INTO `TotalPurchasesPerPacket` VALUES ('family',1),('Mobile Base',1),('Mobile Platinum',0),('Mobile Premium',0);
/*!40000 ALTER TABLE `TotalPurchasesPerPacket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TotalPurchasesPerPacketValidityPeriod`
--

LOCK TABLES `TotalPurchasesPerPacketValidityPeriod` WRITE;
/*!40000 ALTER TABLE `TotalPurchasesPerPacketValidityPeriod` DISABLE KEYS */;
INSERT INTO `TotalPurchasesPerPacketValidityPeriod` VALUES ('family',0,0,1),('Mobile Base',1,0,0),('Mobile Platinum',0,0,0),('Mobile Premium',0,0,0);
/*!40000 ALTER TABLE `TotalPurchasesPerPacketValidityPeriod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `UserCustomer`
--

LOCK TABLES `UserCustomer` WRITE;
/*!40000 ALTER TABLE `UserCustomer` DISABLE KEYS */;
INSERT INTO `UserCustomer` VALUES ('laura','laura','laura@laura',1);
/*!40000 ALTER TABLE `UserCustomer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `UserEmployee`
--

LOCK TABLES `UserEmployee` WRITE;
/*!40000 ALTER TABLE `UserEmployee` DISABLE KEYS */;
INSERT INTO `UserEmployee` VALUES ('a','a');
/*!40000 ALTER TABLE `UserEmployee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-07 23:01:48

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
INSERT INTO `ActivationSchedule` VALUES (190,'2022-05-14','2024-05-14'),(191,'2022-05-26','2023-05-26'),(192,'2022-05-28','2025-05-28');
/*!40000 ALTER TABLE `ActivationSchedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `AuditingTable`
--

LOCK TABLES `AuditingTable` WRITE;
/*!40000 ALTER TABLE `AuditingTable` DISABLE KEYS */;
INSERT INTO `AuditingTable` VALUES ('insolvent with alert','insolvent@alert',468,'2022-05-08 19:27:16');
/*!40000 ALTER TABLE `AuditingTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `AveragePackageOptionalProducts`
--

LOCK TABLES `AveragePackageOptionalProducts` WRITE;
/*!40000 ALTER TABLE `AveragePackageOptionalProducts` DISABLE KEYS */;
INSERT INTO `AveragePackageOptionalProducts` VALUES ('All Illimitate',0.50),('Family Packet',0.00),('Service Package Base',0.00);
/*!40000 ALTER TABLE `AveragePackageOptionalProducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `BestOptional`
--

LOCK TABLES `BestOptional` WRITE;
/*!40000 ALTER TABLE `BestOptional` DISABLE KEYS */;
INSERT INTO `BestOptional` VALUES ('Hotspot',1.00,24.00),('Roaming extra UE',2.50,0.00),('Roaming UE',2.00,0.00);
/*!40000 ALTER TABLE `BestOptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `FailedPayment`
--

LOCK TABLES `FailedPayment` WRITE;
/*!40000 ALTER TABLE `FailedPayment` DISABLE KEYS */;
INSERT INTO `FailedPayment` VALUES (73,119.88,'2022-05-08 19:20:35.967','insolvent',193),(74,155.88,'2022-05-08 19:27:01.292','insolvent with alert',194),(75,155.88,'2022-05-08 19:27:10.088','insolvent with alert',194),(76,155.88,'2022-05-08 19:27:15.738','insolvent with alert',194),(77,155.88,'2022-05-08 19:27:23.538','insolvent with alert',194);
/*!40000 ALTER TABLE `FailedPayment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `InternetService`
--

LOCK TABLES `InternetService` WRITE;
/*!40000 ALTER TABLE `InternetService` DISABLE KEYS */;
INSERT INTO `InternetService` VALUES ('Base Fixed Internet',50,0.890,1),('Fixed Internet Illimitate',1000,0.000,1),('Mobile Internet Base',100,0.990,0),('Mobile Internet Illimitate',1000,0.000,0);
/*!40000 ALTER TABLE `InternetService` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `MobilePhoneService`
--

LOCK TABLES `MobilePhoneService` WRITE;
/*!40000 ALTER TABLE `MobilePhoneService` DISABLE KEYS */;
INSERT INTO `MobilePhoneService` VALUES ('Mobile Phone Base',100,100,0.390,0.500),('Mobile Phone Illimitate',1000,1000,0.000,0.000),('Mobile Phone Premium',300,300,0.150,0.200);
/*!40000 ALTER TABLE `MobilePhoneService` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `MontlyFee`
--

LOCK TABLES `MontlyFee` WRITE;
/*!40000 ALTER TABLE `MontlyFee` DISABLE KEYS */;
INSERT INTO `MontlyFee` VALUES (12,9.990,8.990,7.990),(13,12.990,10.990,9.990),(14,27.990,25.990,23.990);
/*!40000 ALTER TABLE `MontlyFee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `OptionalOrdered`
--

LOCK TABLES `OptionalOrdered` WRITE;
/*!40000 ALTER TABLE `OptionalOrdered` DISABLE KEYS */;
INSERT INTO `OptionalOrdered` VALUES ('Hotspot',190);
/*!40000 ALTER TABLE `OptionalOrdered` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `OptionalProduct`
--

LOCK TABLES `OptionalProduct` WRITE;
/*!40000 ALTER TABLE `OptionalProduct` DISABLE KEYS */;
INSERT INTO `OptionalProduct` VALUES ('Hotspot',1.000),('Roaming extra UE',2.500),('Roaming UE',2.000);
/*!40000 ALTER TABLE `OptionalProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (190,24,1,'2022-05-14','2022-05-08 19:10:12',287.76,'ari','All Illimitate'),(191,12,1,'2022-05-26','2022-05-08 19:10:50',119.88,'ari','Service Package Base'),(192,36,1,'2022-05-28','2022-05-08 19:15:24',359.64,'insolvent','All Illimitate'),(193,12,0,'2022-05-20','2022-05-08 19:20:36',119.88,'insolvent','Service Package Base'),(194,12,0,'2022-05-27','2022-05-08 19:27:01',155.88,'insolvent with alert','All Illimitate');
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PkgServiceInternet`
--

LOCK TABLES `PkgServiceInternet` WRITE;
/*!40000 ALTER TABLE `PkgServiceInternet` DISABLE KEYS */;
INSERT INTO `PkgServiceInternet` VALUES ('Service Package Base','Base Fixed Internet'),('All Illimitate','Fixed Internet Illimitate'),('Family Packet','Fixed Internet Illimitate'),('Family Packet','Mobile Internet Base'),('Service Package Base','Mobile Internet Base'),('All Illimitate','Mobile Internet Illimitate'),('Family Packet','Mobile Internet Illimitate');
/*!40000 ALTER TABLE `PkgServiceInternet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PkgServicePhone`
--

LOCK TABLES `PkgServicePhone` WRITE;
/*!40000 ALTER TABLE `PkgServicePhone` DISABLE KEYS */;
INSERT INTO `PkgServicePhone` VALUES ('Service Package Base','Mobile Phone Base'),('All Illimitate','Mobile Phone Illimitate'),('Family Packet','Mobile Phone Illimitate'),('Family Packet','Mobile Phone Premium');
/*!40000 ALTER TABLE `PkgServicePhone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ServicePackage`
--

LOCK TABLES `ServicePackage` WRITE;
/*!40000 ALTER TABLE `ServicePackage` DISABLE KEYS */;
INSERT INTO `ServicePackage` VALUES ('All Illimitate',0,13),('Family Packet',1,14),('Service Package Base',1,12);
/*!40000 ALTER TABLE `ServicePackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ServicePackageOptional`
--

LOCK TABLES `ServicePackageOptional` WRITE;
/*!40000 ALTER TABLE `ServicePackageOptional` DISABLE KEYS */;
INSERT INTO `ServicePackageOptional` VALUES ('Hotspot','All Illimitate'),('Roaming extra UE','All Illimitate'),('Roaming UE','All Illimitate'),('Roaming extra UE','Family Packet'),('Roaming UE','Family Packet');
/*!40000 ALTER TABLE `ServicePackageOptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TotalPackageSales`
--

LOCK TABLES `TotalPackageSales` WRITE;
/*!40000 ALTER TABLE `TotalPackageSales` DISABLE KEYS */;
INSERT INTO `TotalPackageSales` VALUES ('All Illimitate',647.40,623.40),('Family Packet',0.00,0.00),('Service Package Base',119.88,119.88);
/*!40000 ALTER TABLE `TotalPackageSales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TotalPurchasesPerPacket`
--

LOCK TABLES `TotalPurchasesPerPacket` WRITE;
/*!40000 ALTER TABLE `TotalPurchasesPerPacket` DISABLE KEYS */;
INSERT INTO `TotalPurchasesPerPacket` VALUES ('All Illimitate',2),('Family Packet',0),('Service Package Base',1);
/*!40000 ALTER TABLE `TotalPurchasesPerPacket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `TotalPurchasesPerPacketValidityPeriod`
--

LOCK TABLES `TotalPurchasesPerPacketValidityPeriod` WRITE;
/*!40000 ALTER TABLE `TotalPurchasesPerPacketValidityPeriod` DISABLE KEYS */;
INSERT INTO `TotalPurchasesPerPacketValidityPeriod` VALUES ('All Illimitate',0,1,1),('Family Packet',0,0,0),('Service Package Base',1,0,0);
/*!40000 ALTER TABLE `TotalPurchasesPerPacketValidityPeriod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `UserCustomer`
--

LOCK TABLES `UserCustomer` WRITE;
/*!40000 ALTER TABLE `UserCustomer` DISABLE KEYS */;
INSERT INTO `UserCustomer` VALUES ('ari','ari','ari@ari',1),('insolvent','ins','insolvent@insolvent',0),('insolvent with alert','ins','insolvent@alert',0);
/*!40000 ALTER TABLE `UserCustomer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `UserEmployee`
--

LOCK TABLES `UserEmployee` WRITE;
/*!40000 ALTER TABLE `UserEmployee` DISABLE KEYS */;
INSERT INTO `UserEmployee` VALUES ('laura','laura'),('lauraa','l');
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

-- Dump completed on 2022-05-08 19:55:15

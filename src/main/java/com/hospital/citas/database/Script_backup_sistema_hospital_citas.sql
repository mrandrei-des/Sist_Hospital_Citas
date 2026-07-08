CREATE DATABASE  IF NOT EXISTS `sist_hospital_citas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sist_hospital_citas`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: sist_hospital_citas
-- ------------------------------------------------------
-- Server version	9.7.1

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '67d19235-7246-11f1-a89d-345a6031e648:1-479';

--
-- Table structure for table `accionesensistema`
--

DROP TABLE IF EXISTS `accionesensistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accionesensistema` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombreAccion` varchar(30) NOT NULL,
  `descripcion` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombreAccion` (`nombreAccion`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accionesensistema`
--

LOCK TABLES `accionesensistema` WRITE;
/*!40000 ALTER TABLE `accionesensistema` DISABLE KEYS */;
INSERT INTO `accionesensistema` VALUES (1,'INSERTAR','Ha insertado'),(2,'ACTUALIZAR','Ha actualizadoo'),(3,'ELIMINAR','Ha eliminado'),(4,'ACTIVAR','Ha pasado de Inactivo a Activo'),(5,'INACTIVAR','Ha pasado de Activo a Inactivo'),(6,'CONSULTAR','Ha consultado registros'),(7,'ASIGNAR_PERMISO','Ha asignado permisos'),(8,'ELIMINAR_PERMISO','Ha eliminado permisos'),(9,'DESHABILITAR','Ha deshabilitado/eliminado');
/*!40000 ALTER TABLE `accionesensistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacoracambiosdisponibilidadmedicos`
--

DROP TABLE IF EXISTS `bitacoracambiosdisponibilidadmedicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacoracambiosdisponibilidadmedicos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idAccion` bigint NOT NULL,
  `idDisponibilidadAfectada` bigint NOT NULL,
  `descripcionAccion` varchar(500) NOT NULL,
  `idUsuarioRealizoAccion` bigint NOT NULL,
  `fechaHoraAccion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAccion` (`idAccion`),
  KEY `idDisponibilidadAfectada` (`idDisponibilidadAfectada`),
  KEY `idUsuarioRealizoAccion` (`idUsuarioRealizoAccion`),
  CONSTRAINT `bitacoracambiosdisponibilidadmedicos_ibfk_1` FOREIGN KEY (`idAccion`) REFERENCES `accionesensistema` (`id`),
  CONSTRAINT `bitacoracambiosdisponibilidadmedicos_ibfk_2` FOREIGN KEY (`idDisponibilidadAfectada`) REFERENCES `disponibilidadmedicos` (`id`),
  CONSTRAINT `bitacoracambiosdisponibilidadmedicos_ibfk_3` FOREIGN KEY (`idUsuarioRealizoAccion`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacoracambiosdisponibilidadmedicos`
--

LOCK TABLES `bitacoracambiosdisponibilidadmedicos` WRITE;
/*!40000 ALTER TABLE `bitacoracambiosdisponibilidadmedicos` DISABLE KEYS */;
INSERT INTO `bitacoracambiosdisponibilidadmedicos` VALUES (3,1,4,'El horario ha sido registrado en el sistema.',1,'2026-07-07 09:57:07'),(4,1,5,'El horario ha sido registrado en el sistema.',1,'2026-07-07 09:57:07'),(5,1,6,'El horario ha sido registrado en el sistema.',1,'2026-07-07 09:57:07'),(6,1,7,'El horario ha sido registrado en el sistema.',1,'2026-07-07 09:57:07'),(7,1,8,'El horario ha sido registrado en el sistema.',1,'2026-07-07 09:57:07'),(8,1,9,'El horario ha sido registrado en el sistema.',1,'2026-07-07 09:57:55'),(9,1,10,'El horario ha sido registrado en el sistema.',1,'2026-07-07 09:57:55'),(10,1,11,'El horario ha sido registrado en el sistema.',1,'2026-07-07 17:22:03'),(12,1,13,'El horario ha sido registrado en el sistema.',1,'2026-07-07 17:34:47'),(13,1,14,'El horario ha sido registrado en el sistema.',1,'2026-07-07 17:34:47'),(14,1,15,'El horario ha sido registrado en el sistema.',1,'2026-07-07 17:34:47'),(15,1,16,'El horario ha sido registrado en el sistema.',1,'2026-07-07 19:12:29'),(16,1,17,'El horario ha sido registrado en el sistema.',1,'2026-07-07 19:12:29'),(17,1,18,'El horario ha sido registrado en el sistema.',1,'2026-07-07 19:12:29');
/*!40000 ALTER TABLE `bitacoracambiosdisponibilidadmedicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacoracambiosespecialidades`
--

DROP TABLE IF EXISTS `bitacoracambiosespecialidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacoracambiosespecialidades` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idAccion` bigint NOT NULL,
  `idEspecialidadAfectada` bigint NOT NULL,
  `descripcionAccion` varchar(500) NOT NULL,
  `idUsuarioRealizoAccion` bigint NOT NULL,
  `fechaHoraAccion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAccion` (`idAccion`),
  KEY `idEspecialidadAfectada` (`idEspecialidadAfectada`),
  KEY `idUsuarioRealizoAccion` (`idUsuarioRealizoAccion`),
  CONSTRAINT `bitacoracambiosespecialidades_ibfk_1` FOREIGN KEY (`idAccion`) REFERENCES `accionesensistema` (`id`),
  CONSTRAINT `bitacoracambiosespecialidades_ibfk_2` FOREIGN KEY (`idEspecialidadAfectada`) REFERENCES `especialidades` (`id`),
  CONSTRAINT `bitacoracambiosespecialidades_ibfk_3` FOREIGN KEY (`idUsuarioRealizoAccion`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacoracambiosespecialidades`
--

LOCK TABLES `bitacoracambiosespecialidades` WRITE;
/*!40000 ALTER TABLE `bitacoracambiosespecialidades` DISABLE KEYS */;
INSERT INTO `bitacoracambiosespecialidades` VALUES (1,1,3,'La especialidad ha sido registrada en el sistema.',1,'2026-07-04 22:40:41'),(2,2,3,'A la especialidad se le modificó la descripción.',1,'2026-07-04 23:27:12'),(3,2,1,'A la especialidad se le modificó la descripción.',1,'2026-07-04 23:28:10'),(4,2,1,'A la especialidad se le modificó la descripción.',1,'2026-07-04 23:28:22'),(5,2,2,'A la especialidad se le modificó la descripción.',1,'2026-07-04 23:29:16'),(6,1,1,'La especialidad ha sido registrada en el sistema.',1,'2026-07-04 23:40:26'),(7,1,2,'La especialidad ha sido registrada en el sistema.',1,'2026-07-04 23:40:36'),(8,1,4,'La especialidad ha sido registrada en el sistema.',1,'2026-07-04 23:58:31'),(9,2,3,'A la especialidad se le modificó la descripción.',1,'2026-07-04 23:59:36'),(10,2,3,'A la especialidad se le modificó la descripción.',1,'2026-07-05 00:00:48'),(11,2,2,'A la especialidad se le modificó la descripción.',1,'2026-07-05 00:04:39'),(12,2,1,'A la especialidad se le modificó la descripción.',1,'2026-07-05 00:05:10'),(13,2,1,'A la especialidad se le modificó la descripción.',1,'2026-07-05 00:07:55'),(14,1,5,'La especialidad ha sido registrada en el sistema.',1,'2026-07-05 00:08:12'),(15,1,6,'La especialidad ha sido registrada en el sistema.',1,'2026-07-05 11:24:41'),(16,2,6,'A la especialidad se le modificó la descripción.',1,'2026-07-05 11:24:50'),(17,2,6,'A la especialidad se le modificó la descripción.',1,'2026-07-05 11:25:02'),(18,1,7,'La especialidad ha sido registrada en el sistema.',2,'2026-07-06 00:37:29'),(19,1,8,'La especialidad ha sido registrada en el sistema.',8,'2026-07-06 00:40:20'),(20,2,2,'A la especialidad se le modificó la descripción.',1,'2026-07-06 00:40:55'),(21,2,7,'A la especialidad se le modificó la descripción.',1,'2026-07-07 09:55:31'),(22,1,9,'La especialidad ha sido registrada en el sistema.',1,'2026-07-07 19:09:56'),(23,2,9,'A la especialidad se le modificó la descripción.',1,'2026-07-07 19:10:13');
/*!40000 ALTER TABLE `bitacoracambiosespecialidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacoracambiosmedicos`
--

DROP TABLE IF EXISTS `bitacoracambiosmedicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacoracambiosmedicos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idAccion` bigint NOT NULL,
  `idMedicoAfectado` bigint NOT NULL,
  `descripcionAccion` varchar(500) NOT NULL,
  `idUsuarioRealizoAccion` bigint NOT NULL,
  `fechaHoraAccion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAccion` (`idAccion`),
  KEY `idMedicoAfectado` (`idMedicoAfectado`),
  KEY `idUsuarioRealizoAccion` (`idUsuarioRealizoAccion`),
  CONSTRAINT `bitacoracambiosmedicos_ibfk_1` FOREIGN KEY (`idAccion`) REFERENCES `accionesensistema` (`id`),
  CONSTRAINT `bitacoracambiosmedicos_ibfk_2` FOREIGN KEY (`idMedicoAfectado`) REFERENCES `medicos` (`id`),
  CONSTRAINT `bitacoracambiosmedicos_ibfk_3` FOREIGN KEY (`idUsuarioRealizoAccion`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacoracambiosmedicos`
--

LOCK TABLES `bitacoracambiosmedicos` WRITE;
/*!40000 ALTER TABLE `bitacoracambiosmedicos` DISABLE KEYS */;
INSERT INTO `bitacoracambiosmedicos` VALUES (1,1,3,'El médico ha sido registrado en el sistema.',1,'2026-07-05 17:38:30'),(2,1,4,'El médico ha sido registrado en el sistema.',1,'2026-07-05 17:39:01'),(3,2,1,'El médico ha sido actualizado.',1,'2026-07-05 18:04:59'),(4,3,1,'El médico ha sido eliminado.',1,'2026-07-05 18:22:21'),(5,1,5,'El médico ha sido registrado en el sistema.',1,'2026-07-05 18:23:30'),(6,1,6,'El médico ha sido registrado en el sistema.',1,'2026-07-07 09:56:06'),(7,2,6,'El médico ha sido actualizado.',1,'2026-07-07 09:56:17'),(8,3,4,'El médico ha sido eliminado.',1,'2026-07-07 17:34:12'),(9,1,7,'El médico ha sido registrado en el sistema.',1,'2026-07-07 19:10:52');
/*!40000 ALTER TABLE `bitacoracambiosmedicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacoracambiospermisos`
--

DROP TABLE IF EXISTS `bitacoracambiospermisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacoracambiospermisos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idAccion` bigint NOT NULL,
  `idPermisoAfectado` bigint NOT NULL,
  `descripcionAccion` varchar(500) NOT NULL,
  `idUsuarioRealizoAccion` bigint NOT NULL,
  `fechaHoraAccion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAccion` (`idAccion`),
  KEY `idPermisoAfectado` (`idPermisoAfectado`),
  KEY `idUsuarioRealizoAccion` (`idUsuarioRealizoAccion`),
  CONSTRAINT `bitacoracambiospermisos_ibfk_1` FOREIGN KEY (`idAccion`) REFERENCES `accionesensistema` (`id`),
  CONSTRAINT `bitacoracambiospermisos_ibfk_2` FOREIGN KEY (`idPermisoAfectado`) REFERENCES `permisos` (`id`),
  CONSTRAINT `bitacoracambiospermisos_ibfk_3` FOREIGN KEY (`idUsuarioRealizoAccion`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacoracambiospermisos`
--

LOCK TABLES `bitacoracambiospermisos` WRITE;
/*!40000 ALTER TABLE `bitacoracambiospermisos` DISABLE KEYS */;
/*!40000 ALTER TABLE `bitacoracambiospermisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacoracambiospermisosporrol`
--

DROP TABLE IF EXISTS `bitacoracambiospermisosporrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacoracambiospermisosporrol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idRolAfectado` bigint NOT NULL,
  `idPermisoAfectado` bigint NOT NULL,
  `idUsuarioRegistro` bigint NOT NULL,
  `fechaHoraRegistro` datetime NOT NULL,
  `idUsuarioCambio` bigint NOT NULL,
  `fechaHoraCambio` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idRolAfectado` (`idRolAfectado`),
  KEY `idPermisoAfectado` (`idPermisoAfectado`),
  KEY `idUsuarioRegistro` (`idUsuarioRegistro`),
  KEY `idUsuarioCambio` (`idUsuarioCambio`),
  CONSTRAINT `bitacoracambiospermisosporrol_ibfk_1` FOREIGN KEY (`idRolAfectado`) REFERENCES `roles` (`id`),
  CONSTRAINT `bitacoracambiospermisosporrol_ibfk_2` FOREIGN KEY (`idPermisoAfectado`) REFERENCES `permisos` (`id`),
  CONSTRAINT `bitacoracambiospermisosporrol_ibfk_3` FOREIGN KEY (`idUsuarioRegistro`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `bitacoracambiospermisosporrol_ibfk_4` FOREIGN KEY (`idUsuarioCambio`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacoracambiospermisosporrol`
--

LOCK TABLES `bitacoracambiospermisosporrol` WRITE;
/*!40000 ALTER TABLE `bitacoracambiospermisosporrol` DISABLE KEYS */;
/*!40000 ALTER TABLE `bitacoracambiospermisosporrol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacoracambiosroles`
--

DROP TABLE IF EXISTS `bitacoracambiosroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacoracambiosroles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idAccion` bigint NOT NULL,
  `idRolAfectado` bigint NOT NULL,
  `descripcionAccion` varchar(500) NOT NULL,
  `idUsuarioRealizoAccion` bigint NOT NULL,
  `fechaHoraAccion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAccion` (`idAccion`),
  KEY `idRolAfectado` (`idRolAfectado`),
  KEY `idUsuarioRealizoAccion` (`idUsuarioRealizoAccion`),
  CONSTRAINT `bitacoracambiosroles_ibfk_1` FOREIGN KEY (`idAccion`) REFERENCES `accionesensistema` (`id`),
  CONSTRAINT `bitacoracambiosroles_ibfk_2` FOREIGN KEY (`idRolAfectado`) REFERENCES `roles` (`id`),
  CONSTRAINT `bitacoracambiosroles_ibfk_3` FOREIGN KEY (`idUsuarioRealizoAccion`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacoracambiosroles`
--

LOCK TABLES `bitacoracambiosroles` WRITE;
/*!40000 ALTER TABLE `bitacoracambiosroles` DISABLE KEYS */;
/*!40000 ALTER TABLE `bitacoracambiosroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacoracambiosusuario`
--

DROP TABLE IF EXISTS `bitacoracambiosusuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacoracambiosusuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idAccion` bigint NOT NULL,
  `idUsuarioAfectado` bigint NOT NULL,
  `descripcionAccion` varchar(500) NOT NULL,
  `idUsuarioRealizoAccion` bigint NOT NULL,
  `fechaHoraAccion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAccion` (`idAccion`),
  KEY `idUsuarioAfectado` (`idUsuarioAfectado`),
  KEY `idUsuarioRealizoAccion` (`idUsuarioRealizoAccion`),
  CONSTRAINT `bitacoracambiosusuario_ibfk_1` FOREIGN KEY (`idAccion`) REFERENCES `accionesensistema` (`id`),
  CONSTRAINT `bitacoracambiosusuario_ibfk_2` FOREIGN KEY (`idUsuarioAfectado`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `bitacoracambiosusuario_ibfk_3` FOREIGN KEY (`idUsuarioRealizoAccion`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacoracambiosusuario`
--

LOCK TABLES `bitacoracambiosusuario` WRITE;
/*!40000 ALTER TABLE `bitacoracambiosusuario` DISABLE KEYS */;
INSERT INTO `bitacoracambiosusuario` VALUES (1,1,4,'El usuario ha sido registrado en el sistema.',1,'2026-07-01 23:42:34'),(2,4,1,'',1,'2026-07-02 22:29:40'),(3,4,1,'El usuario realizó la recuperación de contraseña.',1,'2026-07-02 22:35:15'),(4,1,5,'El usuario ha sido registrado en el sistema.',1,'2026-07-03 00:14:15'),(5,1,6,'El usuario ha sido registrado en el sistema.',1,'2026-07-03 10:44:08'),(6,4,1,'El usuario realizó la recuperación de contraseña.',1,'2026-07-03 11:15:23'),(7,4,1,'El usuario realizó la recuperación de contraseña.',1,'2026-07-03 11:17:16'),(8,1,7,'El usuario ha sido registrado en el sistema.',1,'2026-07-03 12:09:22'),(9,1,8,'El usuario ha sido registrado en el sistema.',1,'2026-07-03 12:12:11'),(10,4,1,'El usuario realizó la recuperación de contraseña.',1,'2026-07-04 21:43:24'),(11,1,9,'El usuario ha sido registrado en el sistema.',1,'2026-07-05 23:56:41'),(12,2,8,'El usuario ha actualizado su perfil.',8,'2026-07-06 02:14:06'),(13,2,8,'El usuario ha actualizado su perfil.',8,'2026-07-06 02:15:37'),(14,2,4,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-06 17:21:51'),(15,2,4,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-06 17:22:15'),(16,2,3,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-06 17:22:36'),(17,2,1,'El usuario ha actualizado su perfil.',1,'2026-07-06 17:22:47'),(18,2,1,'El usuario ha actualizado su perfil.',1,'2026-07-06 17:23:02'),(19,2,2,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-06 17:25:32'),(20,2,6,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-06 17:26:15'),(21,2,1,'El usuario ha actualizado su perfil.',1,'2026-07-06 20:48:01'),(22,2,6,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-06 20:48:33'),(23,1,10,'El usuario ha sido registrado en el sistema.',10,'2026-07-07 09:40:11'),(24,2,10,'El usuario ha actualizado su perfil.',10,'2026-07-07 09:45:52'),(25,2,10,'El usuario ha actualizado su perfil.',10,'2026-07-07 09:46:04'),(26,4,1,'El usuario realizó la recuperación de contraseña.',1,'2026-07-07 09:54:04'),(27,2,1,'El usuario ha actualizado su perfil.',1,'2026-07-07 09:55:03'),(28,2,1,'El usuario ha actualizado su perfil.',1,'2026-07-07 09:55:07'),(29,2,9,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 10:46:03'),(30,2,3,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 10:47:39'),(31,2,1,'El usuario ha actualizado su perfil.',1,'2026-07-07 10:52:21'),(32,2,10,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 10:52:35'),(33,2,6,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 10:52:56'),(34,2,5,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 10:54:56'),(35,2,1,'El usuario ha actualizado su perfil.',1,'2026-07-07 11:06:13'),(36,2,5,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 11:08:49'),(37,2,5,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 11:12:59'),(38,1,11,'El usuario ha sido registrado en el sistema.',11,'2026-07-07 17:29:56'),(39,4,11,'El usuario realizó la recuperación de contraseña.',11,'2026-07-07 17:32:05'),(40,2,11,'El usuario ha actualizado su perfil.',11,'2026-07-07 17:32:56'),(41,2,11,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 17:35:35'),(42,2,11,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 17:36:04'),(43,2,11,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 18:22:01'),(44,1,12,'El usuario ha sido registrado en el sistema.',12,'2026-07-07 19:06:06'),(45,2,12,'El usuario ha actualizado su perfil.',12,'2026-07-07 19:07:08'),(46,2,12,'El usuario ha actualizado su perfil.',12,'2026-07-07 19:07:18'),(47,2,3,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 19:08:56'),(48,2,3,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 19:14:15'),(49,1,13,'El usuario ha sido registrado en el sistema.',13,'2026-07-07 19:17:03'),(50,1,14,'El usuario ha sido registrado en el sistema.',14,'2026-07-07 19:39:12'),(51,2,14,'El administrador ha actualizado el perfil del usuario.',1,'2026-07-07 19:39:55'),(52,1,15,'El usuario ha sido registrado en el sistema.',15,'2026-07-07 19:41:17');
/*!40000 ALTER TABLE `bitacoracambiosusuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bitacoradesactivacionpermisosporrol`
--

DROP TABLE IF EXISTS `bitacoradesactivacionpermisosporrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacoradesactivacionpermisosporrol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idRol` bigint NOT NULL,
  `idPermiso` bigint NOT NULL,
  `idUsuarioRegistro` bigint NOT NULL,
  `fechaHoraRegistro` datetime NOT NULL,
  `idUsuarioDesactivo` bigint NOT NULL,
  `fechaHoraDesactivacion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idRol` (`idRol`),
  KEY `idPermiso` (`idPermiso`),
  KEY `idUsuarioRegistro` (`idUsuarioRegistro`),
  KEY `idUsuarioDesactivo` (`idUsuarioDesactivo`),
  CONSTRAINT `bitacoradesactivacionpermisosporrol_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `roles` (`id`),
  CONSTRAINT `bitacoradesactivacionpermisosporrol_ibfk_2` FOREIGN KEY (`idPermiso`) REFERENCES `permisos` (`id`),
  CONSTRAINT `bitacoradesactivacionpermisosporrol_ibfk_3` FOREIGN KEY (`idUsuarioRegistro`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `bitacoradesactivacionpermisosporrol_ibfk_4` FOREIGN KEY (`idUsuarioDesactivo`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacoradesactivacionpermisosporrol`
--

LOCK TABLES `bitacoradesactivacionpermisosporrol` WRITE;
/*!40000 ALTER TABLE `bitacoradesactivacionpermisosporrol` DISABLE KEYS */;
/*!40000 ALTER TABLE `bitacoradesactivacionpermisosporrol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigosrecuperacioncontrasenna_activos`
--

DROP TABLE IF EXISTS `codigosrecuperacioncontrasenna_activos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigosrecuperacioncontrasenna_activos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigoGenerado` varchar(500) NOT NULL,
  `idUsuario` bigint NOT NULL,
  `fechaHoraExpiracion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `codigosrecuperacioncontrasenna_activos_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigosrecuperacioncontrasenna_activos`
--

LOCK TABLES `codigosrecuperacioncontrasenna_activos` WRITE;
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_activos` DISABLE KEYS */;
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_activos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigosrecuperacioncontrasenna_config`
--

DROP TABLE IF EXISTS `codigosrecuperacioncontrasenna_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigosrecuperacioncontrasenna_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idTipoParametro` bigint NOT NULL,
  `nombreParametro` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `valorInt` int DEFAULT NULL,
  `valorVarchar` varchar(100) DEFAULT NULL,
  `valorDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombreParametro` (`nombreParametro`),
  KEY `idTipoParametro` (`idTipoParametro`),
  CONSTRAINT `codigosrecuperacioncontrasenna_config_ibfk_1` FOREIGN KEY (`idTipoParametro`) REFERENCES `tiposparametrosconfig` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigosrecuperacioncontrasenna_config`
--

LOCK TABLES `codigosrecuperacioncontrasenna_config` WRITE;
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigosrecuperacioncontrasenna_expirados`
--

DROP TABLE IF EXISTS `codigosrecuperacioncontrasenna_expirados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigosrecuperacioncontrasenna_expirados` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigoGenerado` varchar(500) NOT NULL,
  `idUsuario` bigint NOT NULL,
  `fechaHoraExpiro` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `codigosrecuperacioncontrasenna_expirados_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigosrecuperacioncontrasenna_expirados`
--

LOCK TABLES `codigosrecuperacioncontrasenna_expirados` WRITE;
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_expirados` DISABLE KEYS */;
INSERT INTO `codigosrecuperacioncontrasenna_expirados` VALUES (1,'822003',3,'2026-07-02 18:54:44'),(2,'822003',3,'2026-07-02 18:54:44'),(3,'822003',3,'2026-07-02 18:54:44'),(4,'822003',3,'2026-07-02 18:54:44'),(5,'822003',3,'2026-07-02 18:54:44'),(6,'822003',3,'2026-07-02 18:54:44'),(7,'822003',3,'2026-07-02 18:54:44'),(8,'822003',3,'2026-07-02 18:54:44'),(9,'621031',1,'2026-07-02 15:04:56'),(10,'980745',1,'2026-07-02 19:36:28'),(11,'570157',1,'2026-07-02 20:32:50'),(12,'793150',1,'2026-07-02 22:22:34'),(13,'248889',1,'2026-07-05 00:38:17');
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_expirados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigosrecuperacioncontrasenna_generados`
--

DROP TABLE IF EXISTS `codigosrecuperacioncontrasenna_generados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigosrecuperacioncontrasenna_generados` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigoGenerado` bigint NOT NULL,
  `idUsuario` bigint NOT NULL,
  `fechaHoraGeneracion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `codigosrecuperacioncontrasenna_generados_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigosrecuperacioncontrasenna_generados`
--

LOCK TABLES `codigosrecuperacioncontrasenna_generados` WRITE;
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_generados` DISABLE KEYS */;
INSERT INTO `codigosrecuperacioncontrasenna_generados` VALUES (1,619799,1,'2026-07-05 00:22:55'),(2,261312,1,'2026-07-05 00:23:13'),(3,248889,1,'2026-07-05 00:23:17'),(4,100218,1,'2026-07-07 09:53:25'),(5,406152,11,'2026-07-07 17:30:43');
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_generados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigosrecuperacioncontrasenna_usados`
--

DROP TABLE IF EXISTS `codigosrecuperacioncontrasenna_usados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codigosrecuperacioncontrasenna_usados` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigoGenerado` varchar(500) NOT NULL,
  `idUsuario` bigint NOT NULL,
  `fechaHoraUso` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `codigosrecuperacioncontrasenna_usados_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigosrecuperacioncontrasenna_usados`
--

LOCK TABLES `codigosrecuperacioncontrasenna_usados` WRITE;
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_usados` DISABLE KEYS */;
INSERT INTO `codigosrecuperacioncontrasenna_usados` VALUES (1,'439442',1,'2026-07-02 21:27:32'),(2,'705518',1,'2026-07-02 22:05:54'),(3,'526156',1,'2026-07-02 22:29:14'),(4,'757635',1,'2026-07-02 22:34:49'),(5,'684814',1,'2026-07-03 11:15:15'),(6,'160780',1,'2026-07-03 11:17:12'),(7,'923411',1,'2026-07-04 21:43:18'),(8,'100218',1,'2026-07-07 09:53:58'),(9,'406152',11,'2026-07-07 17:31:58');
/*!40000 ALTER TABLE `codigosrecuperacioncontrasenna_usados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diasdelasemana`
--

DROP TABLE IF EXISTS `diasdelasemana`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diasdelasemana` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `inicial` char(1) NOT NULL,
  `descripcion` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diasdelasemana`
--

LOCK TABLES `diasdelasemana` WRITE;
/*!40000 ALTER TABLE `diasdelasemana` DISABLE KEYS */;
INSERT INTO `diasdelasemana` VALUES (1,'D','Domingo'),(2,'L','Lunes'),(3,'K','Martes'),(4,'M','Miércoles'),(5,'J','Jueves'),(6,'V','Viernes'),(7,'S','Sábado');
/*!40000 ALTER TABLE `diasdelasemana` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disponibilidadmedicos`
--

DROP TABLE IF EXISTS `disponibilidadmedicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disponibilidadmedicos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idMedico` bigint NOT NULL,
  `idDiaSemana` bigint NOT NULL,
  `horaInicioAtencion` time NOT NULL,
  `horaFinAtencion` time NOT NULL,
  `estado` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idMedico` (`idMedico`),
  KEY `idDiaSemana` (`idDiaSemana`),
  KEY `estado` (`estado`),
  CONSTRAINT `disponibilidadmedicos_ibfk_1` FOREIGN KEY (`idMedico`) REFERENCES `medicos` (`id`),
  CONSTRAINT `disponibilidadmedicos_ibfk_2` FOREIGN KEY (`idDiaSemana`) REFERENCES `diasdelasemana` (`id`),
  CONSTRAINT `disponibilidadmedicos_ibfk_3` FOREIGN KEY (`estado`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disponibilidadmedicos`
--

LOCK TABLES `disponibilidadmedicos` WRITE;
/*!40000 ALTER TABLE `disponibilidadmedicos` DISABLE KEYS */;
INSERT INTO `disponibilidadmedicos` VALUES (4,6,2,'08:00:00','12:30:00',4),(5,6,3,'08:00:00','12:30:00',4),(6,6,4,'08:00:00','12:30:00',4),(7,6,5,'08:00:00','12:30:00',4),(8,6,6,'08:00:00','12:30:00',4),(9,5,2,'13:00:00','16:30:00',4),(10,5,3,'13:00:00','16:30:00',4),(11,5,5,'09:30:00','14:00:00',4),(13,3,2,'08:00:00','12:00:00',4),(14,3,4,'08:00:00','12:00:00',4),(15,3,6,'08:00:00','12:00:00',4),(16,7,2,'08:00:00','12:00:00',4),(17,7,3,'08:00:00','12:00:00',4),(18,7,4,'08:00:00','12:00:00',4);
/*!40000 ALTER TABLE `disponibilidadmedicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidades`
--

DROP TABLE IF EXISTS `especialidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especialidades` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) NOT NULL,
  `estado` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `estado` (`estado`),
  CONSTRAINT `especialidades_ibfk_1` FOREIGN KEY (`estado`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidades`
--

LOCK TABLES `especialidades` WRITE;
/*!40000 ALTER TABLE `especialidades` DISABLE KEYS */;
INSERT INTO `especialidades` VALUES (1,'Cardiología',4),(2,'Ortodoncia',4),(3,'Dermatología',4),(4,'Geriatría',4),(5,'Psicología',4),(6,'Ortopedia',4),(7,'Nutrición Adulto',4),(8,'Fisioterapia',4),(9,'Pediatría 1A',4);
/*!40000 ALTER TABLE `especialidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estados`
--

DROP TABLE IF EXISTS `estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estados` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados`
--

LOCK TABLES `estados` WRITE;
/*!40000 ALTER TABLE `estados` DISABLE KEYS */;
INSERT INTO `estados` VALUES (1,'Pendiente'),(2,'Confirmado'),(3,'Inactivo'),(4,'Activo'),(5,'Temporal'),(6,'Eliminado'),(7,'Cancelado'),(8,'Deshabilitado');
/*!40000 ALTER TABLE `estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupospermisos`
--

DROP TABLE IF EXISTS `grupospermisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupospermisos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `estado` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `estado` (`estado`),
  CONSTRAINT `grupospermisos_ibfk_1` FOREIGN KEY (`estado`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupospermisos`
--

LOCK TABLES `grupospermisos` WRITE;
/*!40000 ALTER TABLE `grupospermisos` DISABLE KEYS */;
INSERT INTO `grupospermisos` VALUES (1,'Usuarios',4),(2,'Médicos',4),(3,'Citas',4),(4,'Reportes',4);
/*!40000 ALTER TABLE `grupospermisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicos`
--

DROP TABLE IF EXISTS `medicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `primerApellido` varchar(30) NOT NULL,
  `segundoApellido` varchar(30) NOT NULL,
  `idEspecialidad` bigint NOT NULL,
  `estado` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idEspecialidad` (`idEspecialidad`),
  KEY `estado` (`estado`),
  CONSTRAINT `medicos_ibfk_1` FOREIGN KEY (`idEspecialidad`) REFERENCES `especialidades` (`id`),
  CONSTRAINT `medicos_ibfk_2` FOREIGN KEY (`estado`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicos`
--

LOCK TABLES `medicos` WRITE;
/*!40000 ALTER TABLE `medicos` DISABLE KEYS */;
INSERT INTO `medicos` VALUES (1,'Ana María','Salazar','Muñoz',1,6),(2,'Luis','Mora','Spencer',2,4),(3,'Samantha','Núñez','Acuña',6,4),(4,'Susana','Romero','Villanueva',1,6),(5,'Javier Eduardo','López','Silva',4,4),(6,'Juan José','Vargas','Araya',7,4),(7,'Kendall','Ulloa','Valverde',9,4);
/*!40000 ALTER TABLE `medicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos`
--

DROP TABLE IF EXISTS `permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `estado` bigint NOT NULL,
  `idGrupoPermiso` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `titulo` (`titulo`),
  KEY `estado` (`estado`),
  KEY `idGrupoPermiso` (`idGrupoPermiso`),
  CONSTRAINT `permisos_ibfk_1` FOREIGN KEY (`estado`) REFERENCES `estados` (`id`),
  CONSTRAINT `permisos_ibfk_2` FOREIGN KEY (`idGrupoPermiso`) REFERENCES `grupospermisos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos`
--

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
INSERT INTO `permisos` VALUES (1,'Creación de usuarios','Permite que el usuario pueda crear usuarios en el sistema',4,1),(2,'Mantenimiento de usuarios','Permite que el usuario pueda darle mantenimiento a los usuarios registrados',4,1),(3,'Listado de usuarios','Permite que el usuario pueda ver los usuarios registrados',4,1),(4,'Cambio Contraseña','Permite que el usuario pueda realizar su propio cambio de contraseña',4,1),(5,'Creación de especialidades','Permite que el usuario pueda crear especialidades en el sistema',4,2),(6,'Listado de especialidades','Permite que el usuario pueda ver las especialidades registradas',4,2),(7,'Mantenimiento de especialidades','Permite que el usuario pueda darle mantenimiento a las especialidades registradas',4,2),(8,'Creación de médicos','Permite que el usuario pueda crear médicos en el sistema',4,2),(9,'Mantenimiento de médicos','Permite que el usuario pueda darle mantenimiento a los médicos en el sistema',4,2),(10,'Listado de médicos','Permite que el usuario pueda ver los médicos registrados',4,2),(11,'Mantenimiento de horarios','Permite que el usuario pueda crearle y darle mantenimiento a los horarios de los médicos',4,2),(12,'Reservar de citas','Permite que el usuario pueda reservar citas en el sistema',4,3),(13,'Cancelar citas','Permite que el usuario pueda cancerlar citas en el sistema',4,3),(14,'Reporte Especialidades','Permite que el usuario pueda generar reportes sobre especialidades',4,4),(15,'Reporte Citas','Permite que el usuario pueda generar reportes sobre citas',4,4);
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisosporrol`
--

DROP TABLE IF EXISTS `permisosporrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisosporrol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idRol` bigint NOT NULL,
  `idPermiso` bigint NOT NULL,
  `idUsuarioRegistro` bigint NOT NULL,
  `fechaHoraRegistro` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idRol` (`idRol`),
  KEY `idPermiso` (`idPermiso`),
  KEY `idUsuarioRegistro` (`idUsuarioRegistro`),
  CONSTRAINT `permisosporrol_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `roles` (`id`),
  CONSTRAINT `permisosporrol_ibfk_2` FOREIGN KEY (`idPermiso`) REFERENCES `permisos` (`id`),
  CONSTRAINT `permisosporrol_ibfk_3` FOREIGN KEY (`idUsuarioRegistro`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisosporrol`
--

LOCK TABLES `permisosporrol` WRITE;
/*!40000 ALTER TABLE `permisosporrol` DISABLE KEYS */;
INSERT INTO `permisosporrol` VALUES (1,1,4,1,'2026-06-28 12:51:51'),(2,1,12,1,'2026-06-28 12:51:51'),(3,1,13,1,'2026-06-28 12:51:51'),(4,2,1,1,'2026-06-28 12:51:51'),(5,2,2,1,'2026-06-28 12:51:51'),(6,2,3,1,'2026-06-28 12:51:51'),(7,2,4,1,'2026-06-28 12:51:51'),(8,2,5,1,'2026-06-28 12:51:51'),(9,2,6,1,'2026-06-28 12:51:51'),(10,2,7,1,'2026-06-28 12:51:51'),(11,2,8,1,'2026-06-28 12:51:51'),(12,2,9,1,'2026-06-28 12:51:51'),(13,2,10,1,'2026-06-28 12:51:51'),(14,2,11,1,'2026-06-28 12:51:51'),(15,2,13,1,'2026-06-28 12:51:51'),(16,2,14,1,'2026-06-28 12:51:51'),(17,2,15,1,'2026-06-28 12:51:51');
/*!40000 ALTER TABLE `permisosporrol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(30) NOT NULL,
  `estado` bigint NOT NULL,
  `paginaInicio` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `estado` (`estado`),
  CONSTRAINT `roles_ibfk_1` FOREIGN KEY (`estado`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Paciente',4,'homePaciente'),(2,'Administrador',4,'homeAdmin');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoidentificaciones`
--

DROP TABLE IF EXISTS `tipoidentificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipoidentificaciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoidentificaciones`
--

LOCK TABLES `tipoidentificaciones` WRITE;
/*!40000 ALTER TABLE `tipoidentificaciones` DISABLE KEYS */;
INSERT INTO `tipoidentificaciones` VALUES (1,'Cédula Física'),(2,'Pasaporte'),(3,'DIMEX');
/*!40000 ALTER TABLE `tipoidentificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposparametrosconfig`
--

DROP TABLE IF EXISTS `tiposparametrosconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposparametrosconfig` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tipoParametro` varchar(50) NOT NULL,
  `fechaHoraRegistro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tipoParametro` (`tipoParametro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposparametrosconfig`
--

LOCK TABLES `tiposparametrosconfig` WRITE;
/*!40000 ALTER TABLE `tiposparametrosconfig` DISABLE KEYS */;
/*!40000 ALTER TABLE `tiposparametrosconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idTipoIdentificacion` bigint NOT NULL,
  `identificacion` varchar(30) NOT NULL,
  `ContrasennaHash` varchar(900) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `primerApellido` varchar(30) NOT NULL,
  `segundoApellido` varchar(30) NOT NULL,
  `correoElectronico` varchar(100) NOT NULL,
  `estado` bigint NOT NULL,
  `idRol` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `identificacion` (`identificacion`),
  UNIQUE KEY `correoElectronico` (`correoElectronico`),
  KEY `idTipoIdentificacion` (`idTipoIdentificacion`),
  KEY `estado` (`estado`),
  KEY `idRol` (`idRol`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`idTipoIdentificacion`) REFERENCES `tipoidentificaciones` (`id`),
  CONSTRAINT `usuarios_ibfk_2` FOREIGN KEY (`estado`) REFERENCES `estados` (`id`),
  CONSTRAINT `usuarios_ibfk_3` FOREIGN KEY (`idRol`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,1,'305310569','$2a$10$ke1t5YDpbIylV3/Kz0LrwuhcSN7gdqsI7e0pvcRXOhb0X/bxH2Gke','Admin','Mesén','Romero','andrei14mesen04@gmail.com',4,2),(2,1,'101110111','123456','Paciente','Paciente','Paciente','paciente@gmail.com',3,1),(3,1,'1011101112','sASas','Juan Pablo','Mora','Díaz','juanmdiaz@gmail.com',4,1),(4,2,'302220131','mmsalaz14','Maria Jose','Salazar','Fuentes','mmsalazar14@gmail.cr',4,1),(5,1,'405610237','$2a$10$I2yvhTSnbj7.YvOW5lyUke9k9/FlzDBxTeRZDIgLP2i.ds82FmI26','Ana','Porras','Fuentes','anaporrasfuentes@gmail.com',4,1),(6,2,'80130456463','$2a$10$jJZyGD4MyiEokjg0eqVAWef9NqY9hJEnQOP5W1kQD0OisgAliez4.','Dylan Enrique','Valverde','Avalos','dylanvalver46@yahoo.com',4,1),(7,1,'707770777','$2a$10$OPOM6iZcxNchVqsxM9uUhuOJM.DwHXjkZUZ6f2ytOTMWDBZXeNk5S','Admin','Respaldo','Respaldo','admin@hospimr.com',3,2),(8,1,'202220222','$2a$10$OPOM6iZcxNchVqsxM9uUhuOJM.DwHXjkZUZ6f2ytOTMWDBZXeNk5S','Lorenzo','AdminDos','Respaldo','admin2@hospimr.com',4,2),(9,1,'405610236','$2a$10$PXfhDbpE/zI39k5IIZ5PC.ACi/2AXPKBOEWx4UdWhtkz2zC9RaF42','Paciente','Mora','Salazar','mrandrei.dev@gmail.com',4,1),(10,1,'701230123','$2a$10$1JBnYjHI74oOrAORkGF6cOmcCVWhky6ugKyJT.826mjbyAC5oAN66','Julieta','Rojas','Ramos','juliroro@gmail.com',3,1),(11,1,'80563013','$2a$10$W1NBFOioqEUCxYcBJWkGHOWsmKTXBK141ISfDfHHPPp6Ir.EG3I0u','Mirania','Romero','Sojo','mandreiesen14@gmail.com',4,2),(12,1,'60130464','$2a$10$X2Q7K7D0KSEKp.S1ZGR5remosk9N4pC0SBikcdm51ZuNO99Dvsu86','Juan','Diaz','Mora','mrandrei24@gmail.com',4,1),(13,1,'704560123','$2a$10$v9cfevk91erTfADyVrroy.XlxV2pQ0/1IBXOWbh4oHU7.Ju.SEVrm','Fabian','Mora','Valverde','fabian@gmail.com',4,1),(14,1,'101110123','$2a$10$YqN.ynvl5IAKja4WBIfh9.OJnxmx8p4FoGZOznzpMefsChH/bGcYa','Admin Prueba','Admin','Admin','admin@admin.com',4,2),(15,1,'901110123','$2a$10$nBC8D.tMSjYLuiVh.zgJcuLHV3ELREpAfm3HYXV9SFw9JxsbPBdJe','Paciente Prueba','Paciente','Paciente','paciente@paciente.com',4,1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'sist_hospital_citas'
--

--
-- Dumping routines for database 'sist_hospital_citas'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_CambioContrasenna` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_CambioContrasenna`(
IN idUsuario bigint,
IN contrasennaNueva VARCHAR(900)
)
BEGIN
	UPDATE Usuarios 
    SET ContrasennaHash = contrasennaNueva
    WHERE id = idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ConsultaHorarioMedicoPorId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaHorarioMedicoPorId`(IN idMedico bigint)
BEGIN
	SELECT d.idMedico, d.idDiaSemana, s.descripcion as dia, d.horaInicioAtencion as horaInicio, d.horaFinAtencion as horaFin
	FROM disponibilidadmedicos d
	JOIN diasdelasemana s on d.idDiaSemana = s.id
	WHERE d.idMedico = idMedico
	ORDER BY d.idDiaSemana ASC, d.horaInicioAtencion ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ConsultaMedicosConHorarioCreado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaMedicosConHorarioCreado`()
BEGIN
	SELECT m.id, m.nombre, m.primerApellido, m.segundoApellido, m.idEspecialidad
	FROM Medicos m
	LEFT JOIN (
		SELECT IDMEDICO 
		FROM disponibilidadmedicos
		WHERE Estado = 4
		GROUP BY IDMEDICO
	) d on m.id = d.idmedico
	WHERE COALESCE(d.idmedico, -1) <> -1
	ORDER BY m.primerApellido ASC, m.segundoApellido ASC, m.nombre ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_EliminaCodigos_OTP_Antiguos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EliminaCodigos_OTP_Antiguos`(
IN idUsuario bigint
)
BEGIN
	DELETE FROM CodigosRecuperacionContrasenna_Activos
    WHERE idUsuario = idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_EliminaCodigo_OTP_Usado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EliminaCodigo_OTP_Usado`(
IN codigoGenerado VARCHAR(500),
IN idUsuario bigint
)
BEGIN
	DELETE FROM CodigosRecuperacionContrasenna_Activos
    WHERE idUsuario = idUsuario AND CodigoGenerado = codigoGenerado;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_EliminarMedico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EliminarMedico`(IN idMedico BIGINT)
BEGIN
    UPDATE medicos m
    SET m.Estado = 6
    WHERE m.id = idMedico;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertaCodigo_OTP_Expirado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_InsertaCodigo_OTP_Expirado`(
IN codigoGenerado VARCHAR(500),
IN idUsuario bigint,
IN fechaExpiro DATETIME
)
BEGIN
	INSERT INTO CodigosRecuperacionContrasenna_Expirados (codigoGenerado, idUsuario, fechaHoraExpiro)
    VALUES(codigoGenerado, idUsuario, fechaExpiro);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertaCodigo_OTP_Generado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_InsertaCodigo_OTP_Generado`(
IN codigoGenerado VARCHAR(500),
IN idUsuario bigint
)
BEGIN
	INSERT INTO codigosrecuperacioncontrasenna_generados (codigoGenerado, idUsuario, fechaHoraGeneracion)
    VALUES(codigoGenerado, idUsuario, NOW());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertaCodigo_OTP_Usado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_InsertaCodigo_OTP_Usado`(
IN codigoGenerado VARCHAR(500),
IN idUsuario bigint
)
BEGIN
	INSERT INTO CodigosRecuperacionContrasenna_Usados (codigoGenerado, idUsuario, fechaHoraUso)
    VALUES(codigoGenerado, idUsuario, NOW());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_Inserta_Registro_Bitacora_Cambios_Especialidades` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_Inserta_Registro_Bitacora_Cambios_Especialidades`(
IN idAccion bigint, 
IN idEspecialidadAfectada bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO bitacoracambiosespecialidades(idAccion, idEspecialidadAfectada, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idEspecialidadAfectada, descripcionAccion, idUsuarioRealizoAccion, NOW());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_Inserta_Registro_Bitacora_Cambios_Horarios_Medicos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_Inserta_Registro_Bitacora_Cambios_Horarios_Medicos`(
IN idAccion bigint, 
IN idDisponibilidadAfectada bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO bitacoracambiosdisponibilidadmedicos(idAccion, idDisponibilidadAfectada, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idDisponibilidadAfectada, descripcionAccion, idUsuarioRealizoAccion, NOW());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_Inserta_Registro_Bitacora_Cambios_Medicos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_Inserta_Registro_Bitacora_Cambios_Medicos`(
IN idAccion bigint, 
IN idMedicoAfectado bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO bitacoracambiosmedicos(idAccion, idMedicoAfectado, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idMedicoAfectado, descripcionAccion, idUsuarioRealizoAccion, NOW());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_Inserta_Registro_Bitacora_Cambios_Usuarios` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_Inserta_Registro_Bitacora_Cambios_Usuarios`(
IN idAccion bigint, 
IN idUsuarioAfectado bigint, 
IN descripcionAccion VARCHAR(500),
IN idUsuarioRealizoAccion bigint
)
BEGIN
	INSERT INTO bitacoracambiosusuario(idAccion, idUsuarioAfectado, descripcionAccion, idUsuarioRealizoAccion, fechaHoraAccion)
    VALUES(idAccion, idUsuarioAfectado, descripcionAccion, idUsuarioRealizoAccion, NOW());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listarMedicosRegistrados` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarMedicosRegistrados`()
BEGIN
	select m.id, m.nombre, m.primerApellido, m.segundoApellido, e.descripcion as Especialidad
	from medicos m
	JOIN especialidades e on m.idEspecialidad = e.id and m.estado = 4 and e.estado = 4
	ORDER BY e.id ASC, m.primerApellido ASC, m.segundoApellido ASC, m.nombre ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listarUltimasEspecialidadesRegistradas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarUltimasEspecialidadesRegistradas`()
BEGIN
	select e.id, e.descripcion, date_format(b.fechaHoraAccion, '%d-%m-%Y %h:%i:%s %p') as fechaRegistro
	from especialidades e
	JOIN bitacoracambiosespecialidades b on e.id = b.idEspecialidadAfectada and b.idAccion = 1
	ORDER BY b.fechaHoraAccion desc
    LIMIT 5;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listaUsuariosPaciente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listaUsuariosPaciente`()
BEGIN
	select u.id, identificacion, concat(nombre, ' ', primerApellido, ' ', segundoApellido) as nombreCompleto, 
	case when coalesce(b.fechaHoraAccion, '01-01-2001') = '01-01-2001' THEN '01-01-2001' ELSE date_format(b.fechahoraaccion, '%d-%m-%Y') end as fechaRegistro, estado
	from usuarios u
	left join bitacoracambiosusuario b on u.id = b.idUsuarioAfectado and b.idAccion = 1
	where idRol = 1
	ORDER BY u.primerApellido ASC, u.segundoApellido ASC, u.Nombre ASC, b.fechaHoraAccion ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-07 19:43:12

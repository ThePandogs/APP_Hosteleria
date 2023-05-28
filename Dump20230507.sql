CREATE DATABASE  IF NOT EXISTS `hosteleria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hosteleria`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: hosteleria
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `cuentas`
--

DROP TABLE IF EXISTS `cuentas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentas` (
  `id_cuenta` int unsigned NOT NULL AUTO_INCREMENT,
  `fecha_hora` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `camarero` int unsigned DEFAULT NULL,
  `mesa` int unsigned NOT NULL,
  `fecha_salida` datetime DEFAULT NULL,
  `comensales` int unsigned DEFAULT NULL,
  `metodo_pago` enum('tarjeta','efectivo') DEFAULT NULL,
  PRIMARY KEY (`id_cuenta`),
  KEY `fk_camarero` (`camarero`),
  KEY `cuentas.ibfk_2_idx` (`mesa`),
  CONSTRAINT `cuentas.ibfk_2` FOREIGN KEY (`mesa`) REFERENCES `mesas` (`id_mesa`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cuentas_ibfk_2` FOREIGN KEY (`camarero`) REFERENCES `usuarios` (`id_usuario`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentas`
--

LOCK TABLES `cuentas` WRITE;
/*!40000 ALTER TABLE `cuentas` DISABLE KEYS */;
INSERT INTO `cuentas` VALUES (11,'2023-05-23 22:47:31',3,1,NULL,0,NULL),(12,'2023-05-23 23:14:15',3,3,'2023-05-27 20:55:40',0,'tarjeta'),(13,'2023-05-23 23:14:26',3,4,NULL,0,NULL),(15,'2023-05-27 21:08:51',5,3,'2023-05-27 21:58:52',0,'efectivo'),(16,'2023-05-27 22:21:53',5,3,'2023-05-27 22:24:38',0,'tarjeta'),(17,'2023-05-27 22:24:52',3,3,NULL,0,NULL),(18,'2023-05-28 19:56:30',3,5,NULL,0,NULL);
/*!40000 ALTER TABLE `cuentas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departamentos`
--

DROP TABLE IF EXISTS `departamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departamentos` (
  `id_departamento` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `jefe_departamento` int unsigned NOT NULL,
  PRIMARY KEY (`id_departamento`),
  UNIQUE KEY `jefe_departamento_UNIQUE` (`jefe_departamento`),
  CONSTRAINT `fk_jefeDepartamento` FOREIGN KEY (`jefe_departamento`) REFERENCES `empleados` (`id_empleado`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departamentos`
--

LOCK TABLES `departamentos` WRITE;
/*!40000 ALTER TABLE `departamentos` DISABLE KEYS */;
INSERT INTO `departamentos` VALUES (1,'Administración',2),(2,'Cocina',4),(3,'Sala',1);
/*!40000 ALTER TABLE `departamentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados` (
  `id_empleado` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `dni` char(9) NOT NULL,
  `departamento` int unsigned NOT NULL,
  PRIMARY KEY (`id_empleado`),
  KEY `fk_departamento_idx` (`departamento`),
  CONSTRAINT `fk_departamento` FOREIGN KEY (`departamento`) REFERENCES `departamentos` (`id_departamento`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES (1,'Lucas Matias','Gutierrez','12654125G',3),(2,'Carlos','Fraile Durán','56215458Z',1),(3,'Ruben','Pazó','12545548H',3),(4,'Andrea','Redondo','95548475J',2),(5,'Lucas','Fernandez','54548712V',3),(6,'Maikel','Cuña','48645544Y',2),(7,'Ana','Pereira','78546554k',3);
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados_telefonos`
--

DROP TABLE IF EXISTS `empleados_telefonos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados_telefonos` (
  `empleado` int unsigned NOT NULL,
  `prefijo_telefono` int unsigned NOT NULL,
  `telefono` int unsigned NOT NULL,
  PRIMARY KEY (`empleado`,`prefijo_telefono`,`telefono`),
  KEY `empleado` (`empleado`),
  CONSTRAINT `empleados_telefonos_ibfk_1` FOREIGN KEY (`empleado`) REFERENCES `empleados` (`id_empleado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados_telefonos`
--

LOCK TABLES `empleados_telefonos` WRITE;
/*!40000 ALTER TABLE `empleados_telefonos` DISABLE KEYS */;
INSERT INTO `empleados_telefonos` VALUES (1,34,645487444),(1,34,695458654),(2,34,654785231),(3,34,699545658),(4,34,628457489),(5,34,654145847);
/*!40000 ALTER TABLE `empleados_telefonos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `establecimientos`
--

DROP TABLE IF EXISTS `establecimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `establecimientos` (
  `id_establecimiento` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `cif` char(9) NOT NULL,
  `prefijo_telefono` int unsigned NOT NULL,
  `telefono` int unsigned NOT NULL,
  PRIMARY KEY (`id_establecimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `establecimientos`
--

LOCK TABLES `establecimientos` WRITE;
/*!40000 ALTER TABLE `establecimientos` DISABLE KEYS */;
INSERT INTO `establecimientos` VALUES (1,'Celme Galego','Valadares, Carr. do Portal, 284, 36315 Vigo, Pontevedra','B25727827',34,986461046);
/*!40000 ALTER TABLE `establecimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupos_productos`
--

DROP TABLE IF EXISTS `grupos_productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupos_productos` (
  `id_grupo` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id_grupo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupos_productos`
--

LOCK TABLES `grupos_productos` WRITE;
/*!40000 ALTER TABLE `grupos_productos` DISABLE KEYS */;
INSERT INTO `grupos_productos` VALUES (1,'Bebidas'),(2,'Entrantes'),(3,'Carnes'),(4,'Pescado'),(5,'Postre');
/*!40000 ALTER TABLE `grupos_productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagenes`
--

DROP TABLE IF EXISTS `imagenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imagenes` (
  `id_imagen` int unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(150) NOT NULL,
  PRIMARY KEY (`id_imagen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagenes`
--

LOCK TABLES `imagenes` WRITE;
/*!40000 ALTER TABLE `imagenes` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesas`
--

DROP TABLE IF EXISTS `mesas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesas` (
  `id_mesa` int unsigned NOT NULL AUTO_INCREMENT,
  `numero` int unsigned NOT NULL,
  `posicionX` int unsigned NOT NULL,
  `posicionY` int unsigned NOT NULL,
  `tamanoX` int unsigned NOT NULL,
  `tamanoY` int unsigned NOT NULL,
  `disponible` tinyint(1) NOT NULL,
  `imagen` int unsigned DEFAULT NULL,
  `sala` int unsigned NOT NULL,
  PRIMARY KEY (`id_mesa`,`numero`,`sala`),
  KEY `fk_sala` (`sala`),
  KEY `fk_imagen` (`imagen`),
  CONSTRAINT `mesas_ibfk_1` FOREIGN KEY (`sala`) REFERENCES `salas` (`id_sala`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mesas_ibfk_2` FOREIGN KEY (`imagen`) REFERENCES `imagenes` (`id_imagen`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesas`
--

LOCK TABLES `mesas` WRITE;
/*!40000 ALTER TABLE `mesas` DISABLE KEYS */;
INSERT INTO `mesas` VALUES (1,1,50,65,100,100,0,NULL,1),(2,1,50,50,100,150,1,NULL,2),(3,5,200,150,200,100,0,NULL,1),(4,2,200,300,100,250,1,NULL,2),(5,2,50,200,100,100,1,NULL,1),(6,3,200,200,50,50,1,NULL,2),(7,4,300,300,250,100,1,NULL,1),(8,3,50,350,100,100,1,NULL,1);
/*!40000 ALTER TABLE `mesas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `producto` int unsigned NOT NULL,
  `cuenta` int unsigned NOT NULL,
  `cantidad` int unsigned NOT NULL,
  PRIMARY KEY (`producto`,`cuenta`),
  KEY `producto` (`producto`),
  KEY `cuenta` (`cuenta`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`producto`) REFERENCES `productos` (`id_producto`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`cuenta`) REFERENCES `cuentas` (`id_cuenta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` VALUES (1,11,4),(1,15,1),(2,11,5),(2,15,1),(3,11,3),(3,12,1),(3,15,22),(3,16,1),(3,17,3),(4,11,2),(4,12,1),(4,13,6),(4,15,28),(4,16,3),(4,17,7),(4,18,3),(5,11,4),(5,15,11),(5,16,3),(5,17,4),(5,18,2),(6,11,3),(6,15,13),(6,16,2),(6,17,3),(7,11,2),(7,15,3),(7,16,1),(8,15,1),(9,15,1);
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id_producto` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `precio` double NOT NULL,
  `disponible` tinyint(1) NOT NULL,
  `imagen` int unsigned DEFAULT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `grupo` int unsigned NOT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `fk_imagen` (`imagen`),
  KEY `fk_grupo_producto` (`grupo`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`imagen`) REFERENCES `imagenes` (`id_imagen`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Cocacola',2.3,1,NULL,0,1),(2,'Kas Naranja',2.1,1,NULL,0,1),(3,'Langostinos',14.5,1,NULL,0,2),(4,'Calamares',9.5,1,NULL,0,2),(5,'Pulpo Pequeña',12.5,1,NULL,0,2),(6,'Pulpo Mediana',15.5,1,NULL,0,2),(7,'Pulpo Grande',19.5,1,NULL,0,2),(8,'Hamburguesa Buey',11.5,1,NULL,0,3),(9,'Dorada al horno',9.5,1,NULL,0,4),(10,'Flan',4.5,1,NULL,0,5);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salas`
--

DROP TABLE IF EXISTS `salas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salas` (
  `id_sala` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `establecimiento` int unsigned NOT NULL,
  PRIMARY KEY (`id_sala`),
  KEY `fk_establecimiento` (`establecimiento`),
  CONSTRAINT `salas_ibfk_1` FOREIGN KEY (`establecimiento`) REFERENCES `establecimientos` (`id_establecimiento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salas`
--

LOCK TABLES `salas` WRITE;
/*!40000 ALTER TABLE `salas` DISABLE KEYS */;
INSERT INTO `salas` VALUES (1,'Principal',1),(2,'Comedor',1);
/*!40000 ALTER TABLE `salas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int unsigned NOT NULL AUTO_INCREMENT,
  `empleado` int unsigned NOT NULL,
  `usuario` varchar(20) NOT NULL,
  `pin` varchar(12) NOT NULL,
  `rol` enum('admin','normal') DEFAULT 'normal',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `usuario_UNIQUE` (`usuario`,`pin`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,1,'Matias','1234','normal'),(2,3,'Lucas','1234','normal'),(3,5,'Ruben','1234','normal'),(4,7,'Carlos','1234','admin'),(5,4,'Andrea','1234','normal'),(6,7,'Ana','1234','normal');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'hosteleria'
--

--
-- Dumping routines for database 'hosteleria'
--
/*!50003 DROP FUNCTION IF EXISTS `BORRAR_PRODUCTO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `BORRAR_PRODUCTO`( ID_PRODUCTO_BORRAR  INT UNSIGNED , ID_CUENTA INT UNSIGNED, CANTIDAD_A_BORRAR INT UNSIGNED) RETURNS tinyint(1)
    READS SQL DATA
BEGIN 
SELECT CANTIDAD INTO @CANTIDAD
    FROM PEDIDOS 
        WHERE cuenta = ID_CUENTA
            AND 
      PRODUCTO=ID_PRODUCTO_BORRAR;
        IF @CANTIDAD = CANTIDAD_A_BORRAR THEN 
        DELETE 
            FROM PEDIDOS 
                WHERE cuenta=ID_CUENTA 
                    AND 
                  PRODUCTO = ID_CUENTA;
            RETURN TRUE;
        ELSE 
        UPDATE PEDIDOS
        SET CANTIDAD=(CANTIDAD -  CANTIDAD_A_BORRAR)
            WHERE cuenta=ID_CUENTA 
                AND PRODUCTO= ID_PRODUCTO_BORRAR;
            RETURN FALSE;
        END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-28 21:16:10

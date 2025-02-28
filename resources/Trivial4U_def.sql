-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.28-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para proyecto
CREATE DATABASE IF NOT EXISTS `proyecto` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `proyecto`;

-- Volcando estructura para tabla proyecto.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `IdCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla proyecto.categorias: ~4 rows (aproximadamente)
DELETE FROM `categorias`;
INSERT INTO `categorias` (`IdCategoria`, `Nombre`) VALUES
	(1, 'Ciencia y Naturaleza'),
	(3, 'Geografia'),
	(6, 'Entretenimiento'),
	(7, 'Cultura general');

-- Volcando estructura para tabla proyecto.detallespartida
CREATE TABLE IF NOT EXISTS `detallespartida` (
  `IdJugador` int(11) NOT NULL,
  `IdPartida` int(11) NOT NULL,
  `Turno` bit(1) DEFAULT NULL,
  `NumCasilla` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdJugador`,`IdPartida`),
  KEY `FK_detallespartida_partida` (`IdPartida`),
  KEY `FK_detallespartida_jugadores` (`IdJugador`),
  CONSTRAINT `FK_detallespartida_jugadores` FOREIGN KEY (`IdJugador`) REFERENCES `jugadores` (`IdJugador`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_detallespartida_partida` FOREIGN KEY (`IdPartida`) REFERENCES `partida` (`IdPartida`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla proyecto.detallespartida: ~6 rows (aproximadamente)
DELETE FROM `detallespartida`;
INSERT INTO `detallespartida` (`IdJugador`, `IdPartida`, `Turno`, `NumCasilla`) VALUES
	(1, 8, b'0', 9),
	(1, 10, b'0', 12),
	(2, 8, b'1', 1),
	(2, 9, b'1', 1),
	(3, 10, b'1', 26),
	(3, 11, b'0', 6);

-- Volcando estructura para tabla proyecto.estadisticas
CREATE TABLE IF NOT EXISTS `estadisticas` (
  `IdPartida` int(11) NOT NULL,
  `IdJugador` int(11) NOT NULL,
  `IdCategoria` int(11) NOT NULL,
  `Acertadas` int(11) DEFAULT NULL,
  `Falladas` int(11) DEFAULT NULL,
  `Quesitos` bit(1) DEFAULT NULL,
  PRIMARY KEY (`IdPartida`,`IdJugador`,`IdCategoria`),
  KEY `FK_estadisticas_jugadores` (`IdJugador`),
  KEY `FK_estadisticas_categorias` (`IdCategoria`),
  CONSTRAINT `FK_estadisticas_categorias` FOREIGN KEY (`IdCategoria`) REFERENCES `categorias` (`IdCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_estadisticas_jugadores` FOREIGN KEY (`IdJugador`) REFERENCES `jugadores` (`IdJugador`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_estadisticas_partida` FOREIGN KEY (`IdPartida`) REFERENCES `partida` (`IdPartida`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla proyecto.estadisticas: ~0 rows (aproximadamente)
DELETE FROM `estadisticas`;

-- Volcando estructura para tabla proyecto.jugadores
CREATE TABLE IF NOT EXISTS `jugadores` (
  `IdJugador` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdJugador`),
  UNIQUE KEY `Nombre` (`Nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla proyecto.jugadores: ~2 rows (aproximadamente)
DELETE FROM `jugadores`;
INSERT INTO `jugadores` (`IdJugador`, `Nombre`, `password`) VALUES
	(1, 'Diego', '1234'),
	(2, 'Eric', '1234'),
	(3, 'jelopez', '1234');

-- Volcando estructura para tabla proyecto.partida
CREATE TABLE IF NOT EXISTS `partida` (
  `IdPartida` int(11) NOT NULL AUTO_INCREMENT,
  `nombrePartida` varchar(50) DEFAULT NULL,
  `idCreador` int(11) DEFAULT NULL,
  `activa` bit(1) DEFAULT NULL,
  `contrincante` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdPartida`),
  KEY `FK_partida_jugadores` (`idCreador`),
  KEY `FK_partida_jugadores_2` (`contrincante`),
  CONSTRAINT `FK_partida_jugadores` FOREIGN KEY (`idCreador`) REFERENCES `jugadores` (`IdJugador`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_partida_jugadores_2` FOREIGN KEY (`contrincante`) REFERENCES `jugadores` (`IdJugador`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla proyecto.partida: ~3 rows (aproximadamente)
DELETE FROM `partida`;
INSERT INTO `partida` (`IdPartida`, `nombrePartida`, `idCreador`, `activa`, `contrincante`) VALUES
	(8, 'Prueba Eric', 2, b'1', 1),
	(9, 'Prueba Eric 2', 2, b'1', NULL),
	(10, 'Partida2702_1731', 1, b'1', 3),
	(11, 'partida1', 3, b'1', NULL);

-- Volcando estructura para tabla proyecto.preguntas
CREATE TABLE IF NOT EXISTS `preguntas` (
  `IdPregunta` int(11) NOT NULL AUTO_INCREMENT,
  `Texto` varchar(100) DEFAULT NULL,
  `Respuesta1` varchar(50) DEFAULT NULL,
  `Respuesta2` varchar(50) DEFAULT NULL,
  `Respuesta3` varchar(50) DEFAULT NULL,
  `Respuesta4` varchar(50) DEFAULT NULL,
  `Correcta` int(11) DEFAULT NULL,
  `IdCategoria` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdPregunta`),
  KEY `FK_preguntas_categorias` (`IdCategoria`),
  CONSTRAINT `FK_preguntas_categorias` FOREIGN KEY (`IdCategoria`) REFERENCES `categorias` (`IdCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla proyecto.preguntas: ~13 rows (aproximadamente)
DELETE FROM `preguntas`;
INSERT INTO `preguntas` (`IdPregunta`, `Texto`, `Respuesta1`, `Respuesta2`, `Respuesta3`, `Respuesta4`, `Correcta`, `IdCategoria`) VALUES
	(1, '¿Cuantos bits son un byte?', '8', '1', '16', '2', 2, 1),
	(2, '¿Qué empresa desarrolló el sistema operativo Android?', 'Microsoft', 'Apple', 'Nvidia', 'Google', 4, 7),
	(3, '¿Quién formuló la teoría de la relatividad?', 'Isaac Newton', 'Galileo Galilei', 'Albert Einstein', 'James Clerk Maxwell', 3, 1),
	(4, '¿Cuál es el elemento químico con el símbolo "O"?', 'Óxido', 'Oxígeno', 'Hidróxido', 'Óxido ácido', 2, 1),
	(5, '¿Qué planeta es conocido como el "Planeta Rojo"?', 'Marte', 'Saturno', 'La Tierra', 'Mercurio', 1, 1),
	(7, '¿Qué país es conocido como "La Tierra del Sol Naciente?', 'Australia', 'Rusia', 'Japón', 'China', 3, 3),
	(13, '¿Cuál es el país más grande del mundo en superficie?', 'Canadá', 'Estados Unidos', 'China', 'Rusia', 4, 3),
	(14, '¿Cuál es la capital de España?', 'Valencia', 'Madrid', 'Zaragoza', 'Barcelona', 2, 3),
	(20, '¿Qué saga de películas tiene a Darth Vader como villano?', 'Harry Potter', ' El Señor de los Anillos', 'Star Wars', 'Marvel', 3, 6),
	(21, '¿Quién es el personaje principal de la serie Los Simpson?', 'Bart Simpson', 'Lisa Simpson', 'Homer Simpson', 'Ned Flanders', 3, 6),
	(22, '¿Cuál de estos superhéroes pertenece al universo Marvel?', 'Batman', 'Superman', 'Spider-Man', 'Wonder Woman', 3, 6),
	(23, '¿Cuál es el metal más abundante en la corteza terrestre?', 'Oro\n', 'Hierro', 'Aluminio', 'Cobre', 3, 7),
	(24, '¿Qué gas necesitamos para respirar y vivir?', 'Dióxido de carbono', 'Oxígeno', 'Nitrógeno', 'Hidrógeno', 2, 7);

-- Volcando estructura para tabla proyecto.tablero
CREATE TABLE IF NOT EXISTS `tablero` (
  `NumeroCasilla` int(11) NOT NULL,
  `Fila` int(11) NOT NULL,
  `Columna` int(11) NOT NULL,
  `IdTipo` int(11) NOT NULL,
  `IdCategoria` int(11) DEFAULT NULL,
  `Contenido` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`NumeroCasilla`),
  KEY `FK_tablero_categorias` (`IdCategoria`),
  KEY `FK_tablero_tipocasilla` (`IdTipo`),
  CONSTRAINT `FK_tablero_categorias` FOREIGN KEY (`IdCategoria`) REFERENCES `categorias` (`IdCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_tablero_tipocasilla` FOREIGN KEY (`IdTipo`) REFERENCES `tipocasilla` (`IdTipo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla proyecto.tablero: ~32 rows (aproximadamente)
DELETE FROM `tablero`;
INSERT INTO `tablero` (`NumeroCasilla`, `Fila`, `Columna`, `IdTipo`, `IdCategoria`, `Contenido`) VALUES
	(1, 1, 1, 2, 1, '▲'),
	(2, 1, 2, 1, 6, NULL),
	(3, 1, 3, 3, NULL, '🎲'),
	(4, 1, 4, 1, 3, NULL),
	(5, 1, 5, 1, 1, NULL),
	(6, 1, 6, 1, 7, NULL),
	(7, 1, 7, 3, NULL, '🎲'),
	(8, 1, 8, 1, 6, NULL),
	(9, 1, 9, 2, 3, '▲'),
	(10, 2, 9, 1, 1, ''),
	(11, 3, 9, 3, NULL, '🎲'),
	(12, 4, 9, 1, 7, NULL),
	(13, 5, 9, 1, 3, NULL),
	(14, 6, 9, 1, 1, NULL),
	(15, 7, 9, 3, NULL, '🎲'),
	(16, 8, 9, 1, 6, NULL),
	(17, 9, 9, 2, 7, '▲'),
	(18, 9, 8, 1, 3, NULL),
	(19, 9, 7, 3, NULL, '🎲'),
	(20, 9, 6, 1, 1, NULL),
	(21, 9, 5, 1, 6, NULL),
	(22, 9, 4, 1, 3, NULL),
	(23, 9, 3, 3, NULL, '🎲'),
	(24, 9, 2, 1, 7, NULL),
	(25, 9, 1, 2, 6, '▲'),
	(26, 8, 1, 1, 3, NULL),
	(27, 7, 1, 3, NULL, '🎲'),
	(28, 6, 1, 1, 7, NULL),
	(29, 5, 1, 1, 6, NULL),
	(30, 4, 1, 1, 1, NULL),
	(31, 3, 1, 3, NULL, '🎲'),
	(32, 2, 1, 1, 7, NULL);

-- Volcando estructura para tabla proyecto.tipocasilla
CREATE TABLE IF NOT EXISTS `tipocasilla` (
  `IdTipo` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdTipo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla proyecto.tipocasilla: ~3 rows (aproximadamente)
DELETE FROM `tipocasilla`;
INSERT INTO `tipocasilla` (`IdTipo`, `Nombre`) VALUES
	(1, 'Pregunta'),
	(2, 'Quesito'),
	(3, 'Volver a tirar');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

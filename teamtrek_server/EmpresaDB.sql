-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 06-11-2023 a las 00:17:02
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `EmpresaDB`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Asistencia`
--

CREATE TABLE `Asistencia` (
  `EmpleadoID` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Entrada` time NOT NULL,
  `Salida` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Asistencia`
--

INSERT INTO `Asistencia` (`EmpleadoID`, `Fecha`, `Entrada`, `Salida`) VALUES
(101, '2023-11-01', '08:00:00', '16:00:00'),
(102, '2023-11-01', '08:10:00', '16:15:00'),
(103, '2023-11-01', '08:05:00', '16:10:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Avisos`
--

CREATE TABLE `Avisos` (
  `id_aviso` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha_publicacion` datetime DEFAULT current_timestamp(),
  `fecha_expiracion` datetime DEFAULT NULL,
  `autor_id` int(11) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Avisos`
--

INSERT INTO `Avisos` (`id_aviso`, `titulo`, `descripcion`, `fecha_publicacion`, `fecha_expiracion`, `autor_id`, `activo`) VALUES
(1, 'Mantenimiento del Servidor', 'El servidor estará en mantenimiento el próximo sábado.', '2023-11-04 18:31:22', '2023-12-05 08:00:00', 101, 1),
(2, 'Política de Vacaciones', 'La política de vacaciones ha sido actualizada. Por favor, revisen el documento adjunto.', '2023-11-04 18:31:22', '2024-01-01 00:00:00', 102, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `DatosPersonales`
--

CREATE TABLE `DatosPersonales` (
  `EmpleadoID` int(11) NOT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Direccion` varchar(255) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `FechaNacimiento` date DEFAULT NULL,
  `NIF` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `DatosPersonales`
--

INSERT INTO `DatosPersonales` (`EmpleadoID`, `Telefono`, `Direccion`, `Email`, `FechaNacimiento`, `NIF`) VALUES
(101, '555-1234', 'Calle Ficticia 123, Guadalajara', 'alberto.perez@empresa.com', '1986-01-01', '12345678A'),
(102, '555-5678', 'Avenida de la Imaginación 45, Madrid', 'laura.garcia@empresa.com', '1990-07-22', '87654321B'),
(103, '555-9101', 'Paseo de la Invención 67, Valencia', 'carlos.sanchez@empresa.com', '1978-11-30', '11223344C'),
(104, '555-632', 'Calle de la Piruleta 15, Barcelona', 'sandra.marcos@empresa.com', '2000-06-14', '77223344C'),
(105, '555-981', 'Paseo de la Barca 10, León', 'pedro.lopez@empresa.com', '1996-08-02', '55993366A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Departamentos`
--

CREATE TABLE `Departamentos` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Departamentos`
--

INSERT INTO `Departamentos` (`ID`, `Nombre`) VALUES
(1, 'Tecnología'),
(2, 'Recursos Humanos'),
(3, 'Marketing');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `EmpleadoHorario`
--

CREATE TABLE `EmpleadoHorario` (
  `EmpleadoID` int(11) NOT NULL,
  `HorarioID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `EmpleadoHorario`
--

INSERT INTO `EmpleadoHorario` (`EmpleadoID`, `HorarioID`) VALUES
(101, 1),
(102, 1),
(103, 1),
(104, 3),
(105, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Empleados`
--

CREATE TABLE `Empleados` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `FechaIngreso` date NOT NULL,
  `DepartamentoID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Empleados`
--

INSERT INTO `Empleados` (`ID`, `Nombre`, `FechaIngreso`, `DepartamentoID`) VALUES
(101, 'Alberto Perez', '2023-01-15', 1),
(102, 'Laura García', '2023-02-20', 2),
(103, 'Carlos Sánchez', '2023-03-10', 3),
(104, 'Sandra Marcos', '2023-11-01', 3),
(105, 'Pedro López', '2023-11-03', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fichajes`
--

CREATE TABLE `fichajes` (
  `id_fichaje` int(11) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `fecha_hora_entrada` datetime DEFAULT NULL,
  `fecha_hora_salida` datetime DEFAULT NULL,
  `nota` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `fichajes`
--

INSERT INTO `fichajes` (`id_fichaje`, `usuario_id`, `fecha_hora_entrada`, `fecha_hora_salida`, `nota`) VALUES
(1, 101, '2023-11-06 08:00:00', '2023-11-06 16:00:00', 'Jornada laboral completa'),
(2, 102, '2023-11-06 08:30:00', '2023-11-06 16:30:00', 'Jornada laboral completa'),
(3, 103, '2023-11-06 09:00:00', '2023-11-06 17:00:00', 'Jornada laboral completa'),
(4, 104, '2023-11-06 09:30:00', '2023-11-06 17:30:00', 'Jornada laboral completa'),
(5, 105, '2023-11-06 10:00:00', '2023-11-06 18:00:00', 'Jornada laboral completa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Horarios`
--

CREATE TABLE `Horarios` (
  `ID` int(11) NOT NULL,
  `Descripcion` varchar(255) NOT NULL,
  `HoraInicio` time NOT NULL,
  `HoraFin` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Horarios`
--

INSERT INTO `Horarios` (`ID`, `Descripcion`, `HoraInicio`, `HoraFin`) VALUES
(1, 'Mañana', '08:00:00', '16:00:00'),
(2, 'Tarde', '14:00:00', '22:00:00'),
(3, 'Noche', '22:00:00', '06:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Roles`
--

CREATE TABLE `Roles` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Descripcion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Roles`
--

INSERT INTO `Roles` (`ID`, `Nombre`, `Descripcion`) VALUES
(1, 'Administrador', 'Usuario con acceso total al sistema.'),
(2, 'Usuario', 'Usuario estándar con acceso limitado.'),
(3, 'Invitado', 'Usuario con acceso mínimo.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `UsuarioRoles`
--

CREATE TABLE `UsuarioRoles` (
  `UsuarioID` int(11) NOT NULL,
  `RolID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `UsuarioRoles`
--

INSERT INTO `UsuarioRoles` (`UsuarioID`, `RolID`) VALUES
(101, 1),
(102, 2),
(103, 2),
(104, 2),
(105, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuarios`
--

CREATE TABLE `Usuarios` (
  `EmpleadoID` int(11) NOT NULL,
  `Usuario` varchar(50) NOT NULL,
  `Contrasena` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Usuarios`
--

INSERT INTO `Usuarios` (`EmpleadoID`, `Usuario`, `Contrasena`) VALUES
(101, 'alberto', 'alberto'),
(102, 'laurag', 'laurag'),
(103, 'carloss', 'carloss'),
(104, 'sandram', 'sandram'),
(105, 'pedrol', 'pedrol');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `VacacionesAusencias`
--

CREATE TABLE `VacacionesAusencias` (
  `EmpleadoID` int(11) NOT NULL,
  `FechaInicio` date NOT NULL,
  `FechaFin` date NOT NULL,
  `Tipo` enum('VACACIONES','AUSENCIA') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `VacacionesAusencias`
--

INSERT INTO `VacacionesAusencias` (`EmpleadoID`, `FechaInicio`, `FechaFin`, `Tipo`) VALUES
(101, '2023-12-01', '2023-12-10', 'VACACIONES'),
(102, '2023-11-15', '2023-11-20', 'AUSENCIA'),
(103, '2023-12-20', '2023-12-30', 'VACACIONES');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Asistencia`
--
ALTER TABLE `Asistencia`
  ADD KEY `fk_EmpleadoID_Asistencia` (`EmpleadoID`);

--
-- Indices de la tabla `Avisos`
--
ALTER TABLE `Avisos`
  ADD PRIMARY KEY (`id_aviso`),
  ADD KEY `autor_id` (`autor_id`);

--
-- Indices de la tabla `DatosPersonales`
--
ALTER TABLE `DatosPersonales`
  ADD PRIMARY KEY (`EmpleadoID`);

--
-- Indices de la tabla `Departamentos`
--
ALTER TABLE `Departamentos`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `EmpleadoHorario`
--
ALTER TABLE `EmpleadoHorario`
  ADD PRIMARY KEY (`EmpleadoID`,`HorarioID`),
  ADD KEY `fk_HorarioID` (`HorarioID`);

--
-- Indices de la tabla `Empleados`
--
ALTER TABLE `Empleados`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_DepartamentoID` (`DepartamentoID`);

--
-- Indices de la tabla `fichajes`
--
ALTER TABLE `fichajes`
  ADD PRIMARY KEY (`id_fichaje`),
  ADD KEY `usuario_id` (`usuario_id`);

--
-- Indices de la tabla `Horarios`
--
ALTER TABLE `Horarios`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `Roles`
--
ALTER TABLE `Roles`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Nombre` (`Nombre`);

--
-- Indices de la tabla `UsuarioRoles`
--
ALTER TABLE `UsuarioRoles`
  ADD PRIMARY KEY (`UsuarioID`,`RolID`),
  ADD KEY `RolID` (`RolID`);

--
-- Indices de la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD PRIMARY KEY (`EmpleadoID`);

--
-- Indices de la tabla `VacacionesAusencias`
--
ALTER TABLE `VacacionesAusencias`
  ADD KEY `fk_EmpleadoID_VacacionesAusencias` (`EmpleadoID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Avisos`
--
ALTER TABLE `Avisos`
  MODIFY `id_aviso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `fichajes`
--
ALTER TABLE `fichajes`
  MODIFY `id_fichaje` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `Horarios`
--
ALTER TABLE `Horarios`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Asistencia`
--
ALTER TABLE `Asistencia`
  ADD CONSTRAINT `fk_EmpleadoID_Asistencia` FOREIGN KEY (`EmpleadoID`) REFERENCES `Empleados` (`ID`);

--
-- Filtros para la tabla `Avisos`
--
ALTER TABLE `Avisos`
  ADD CONSTRAINT `Avisos_ibfk_1` FOREIGN KEY (`autor_id`) REFERENCES `Usuarios` (`EmpleadoID`);

--
-- Filtros para la tabla `DatosPersonales`
--
ALTER TABLE `DatosPersonales`
  ADD CONSTRAINT `FK_DatosPersonales_Empleado` FOREIGN KEY (`EmpleadoID`) REFERENCES `Empleados` (`ID`);

--
-- Filtros para la tabla `EmpleadoHorario`
--
ALTER TABLE `EmpleadoHorario`
  ADD CONSTRAINT `fk_EmpleadoID` FOREIGN KEY (`EmpleadoID`) REFERENCES `Empleados` (`ID`),
  ADD CONSTRAINT `fk_HorarioID` FOREIGN KEY (`HorarioID`) REFERENCES `Horarios` (`ID`);

--
-- Filtros para la tabla `Empleados`
--
ALTER TABLE `Empleados`
  ADD CONSTRAINT `fk_DepartamentoID` FOREIGN KEY (`DepartamentoID`) REFERENCES `Departamentos` (`ID`);

--
-- Filtros para la tabla `fichajes`
--
ALTER TABLE `fichajes`
  ADD CONSTRAINT `fichajes_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `Usuarios` (`EmpleadoID`);

--
-- Filtros para la tabla `UsuarioRoles`
--
ALTER TABLE `UsuarioRoles`
  ADD CONSTRAINT `UsuarioRoles_ibfk_1` FOREIGN KEY (`UsuarioID`) REFERENCES `Usuarios` (`EmpleadoID`),
  ADD CONSTRAINT `UsuarioRoles_ibfk_2` FOREIGN KEY (`RolID`) REFERENCES `Roles` (`ID`);

--
-- Filtros para la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD CONSTRAINT `fk_EmpleadoID_Usuarios` FOREIGN KEY (`EmpleadoID`) REFERENCES `Empleados` (`ID`);

--
-- Filtros para la tabla `VacacionesAusencias`
--
ALTER TABLE `VacacionesAusencias`
  ADD CONSTRAINT `fk_EmpleadoID_VacacionesAusencias` FOREIGN KEY (`EmpleadoID`) REFERENCES `Empleados` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

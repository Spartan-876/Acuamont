-- 1. Crear y usar la base de datos
CREATE DATABASE IF NOT EXISTS acceso;
USE acceso;

-- 2. Limpiar tablas anteriores para evitar errores de "ya existe"
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `usuarios`;
DROP TABLE IF EXISTS `perfiles`;
SET FOREIGN_KEY_CHECKS = 1;

-- 3. Crear tabla Perfiles
CREATE TABLE `perfiles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nombre_perfil` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Insertar Perfiles (OBLIGATORIO antes que usuarios)
INSERT INTO `perfiles` (`id`, `nombre`, `descripcion`) VALUES
(1, 'Administrador', 'Acceso total al sistema.'),
(2, 'Editor', 'Puede gestionar usuarios pero no perfiles.'),
(3, 'Supervisor', 'Solo puede visualizar información.');

-- 5. Crear tabla Usuarios
CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `estado` int(11) NOT NULL DEFAULT 1,
  `id_perfil` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_usuario` (`usuario`),
  CONSTRAINT `FK_usuarios_perfiles` FOREIGN KEY (`id_perfil`) REFERENCES `perfiles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Insertar Usuarios
INSERT INTO `usuarios` (`id`, `nombre`, `usuario`, `clave`, `correo`, `estado`, `id_perfil`) VALUES
(8, 'Daryl', 'admin', '$2a$10$OZuN1MJlw/01gIodlwqaQOKk.d5XhfbWAD8X2adyG9pkKtpDlVN1O', 'luis@ejemplo.com', 1, 1),
(10, 'María Supervisor', 'supervisor', '$2a$10$N9qo8uLOickgx2ZMRZoMye5aZl8ZzO8Fns2h0eCZgP2h7ZWCpU9/y', 'supervisor@ejemplo.com', 0, 3),
(14, 'Luis Antonio', 'luis', '$2a$10$bDRnfg7TQgcBeV.e0cd.ZuNfDUGfPRPhp62tfLVtycqwV/unM0VWm', 'luis@ejemplo.com', 1, 2);

COMMIT;
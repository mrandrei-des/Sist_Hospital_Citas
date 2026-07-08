DROP PROCEDURE IF EXISTS sp_listaUsuariosPaciente;
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listaUsuariosPaciente`()
BEGIN
	select u.id, identificacion, concat(nombre, ' ', primerApellido, ' ', segundoApellido) as nombreCompleto, 
	case when coalesce(b.fechaHoraAccion, '01-01-2001') = '01-01-2001' THEN '01-01-2001' ELSE date_format(b.fechahoraaccion, '%d-%m-%Y') end as fechaRegistro, estado
	from usuarios u
	left join bitacoracambiosusuario b on u.id = b.idUsuarioAfectado and b.idAccion = 1
	where idRol = 1
	ORDER BY u.primerApellido ASC, u.segundoApellido ASC, u.Nombre ASC, b.fechaHoraAccion ASC;
END$$
DELIMITER ;
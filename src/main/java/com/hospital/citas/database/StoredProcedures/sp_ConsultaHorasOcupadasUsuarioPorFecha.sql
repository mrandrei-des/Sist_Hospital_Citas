DROP PROCEDURE IF EXISTS sp_ConsultaHorasOcupadasUsuarioPorFecha;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaHorasOcupadasUsuarioPorFecha`(
IN idUsuario bigint, 
IN fechaBusqueda date
)
BEGIN
	select hora 
	from ResevarCitas
	where estado in (1, 2) and idUsuario = idUsuario and fecha = fechaBusqueda
    order by hora ASC;
END$$
DELIMITER ;

CALL sp_ConsultaHorasOcupadasUsuarioPorFecha(16, '2026-08-04');

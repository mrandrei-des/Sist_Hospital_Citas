DROP PROCEDURE IF EXISTS sp_ConsultaHorasOcupadasMedicoPorFecha;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaHorasOcupadasMedicoPorFecha`(
IN idMedico bigint, 
IN fechaBusqueda date
)
BEGIN
	select hora 
	from ResevarCitas
	where estado in (1, 2) and idMedico = idMedico and fecha = fechaBusqueda
    order by hora ASC;
END$$
DELIMITER ;

CALL sp_ConsultaHorasOcupadasMedicoPorFecha(11, '2026-08-04')

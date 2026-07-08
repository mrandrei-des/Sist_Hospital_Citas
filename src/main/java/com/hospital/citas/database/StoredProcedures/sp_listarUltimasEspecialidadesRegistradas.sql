DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarUltimasEspecialidadesRegistradas`()
BEGIN
	select e.id, e.descripcion, date_format(b.fechaHoraAccion, '%d-%m-%Y %h:%i:%s %p') as fechaRegistro
	from especialidades e
	JOIN bitacoracambiosespecialidades b on e.id = b.idEspecialidadAfectada and b.idAccion = 1
	ORDER BY b.fechaHoraAccion desc
    LIMIT 5;
END$$
DELIMITER ;
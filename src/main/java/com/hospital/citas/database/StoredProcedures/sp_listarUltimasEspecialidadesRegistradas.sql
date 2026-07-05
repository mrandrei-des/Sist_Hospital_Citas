DELIMITER $$
CREATE PROCEDURE `sp_listarUltimasEspecialidadesRegistradas`()
BEGIN
	select e.id, e.descripcion, b.fechaHoraAccion as fechaRegistro
	from especialidades e
	JOIN bitacoracambiosespecialidades b on e.id = b.idEspecialidadAfectada and b.idAccion = 1
	ORDER BY b.fechaHoraAccion desc
    LIMIT 5;
END$$
DELIMITER ;
DROP PROCEDURE IF EXISTS sp_consultaCantidadCitasPacientesFiltros;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaCantidadCitasPacientesFiltros`(IN filtEstado bigint, IN filtEspecialidad bigint, 
IN filtMedico bigint, IN filtFechaInicio date, IN filtFechaFin DATE)
BEGIN
	SELECT COUNT(*) as CantidadCitas
	FROM resevarcitas r
	JOIN medicos m on r.idMedico = m.id
	JOIN especialidades e on m.idEspecialidad = e.id
	JOIN usuarios u on r.idUsuario = u.id
	WHERE 1 = 1
	AND (filtEstado IS NULL OR r.estado = filtEstado)
	AND (filtEspecialidad IS NULL OR m.idEspecialidad = filtEspecialidad)
	AND (filtMedico IS NULL OR r.idMedico = filtMedico)
	AND (filtFechaInicio IS NULL OR r.fecha >= filtFechaInicio)
	AND (filtFechaFin IS NULL OR r.fecha <= filtFechaFin);
END$$
DELIMITER ;

call sp_consultaCantidadCitasPacientesFiltros(NULL, NULL, 2, '2026-08-06', '2026-08-07');
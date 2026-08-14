DROP PROCEDURE IF EXISTS sp_consultaCitasPacientesFiltrosParaReporte;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaCitasPacientesFiltrosParaReporte`(IN filtEstado bigint, IN filtEspecialidad bigint, 
IN filtMedico bigint, IN filtFechaInicio date, IN filtFechaFin DATE)
BEGIN    
	SELECT r.id as idCita, e.id as idEspecialidad, e.descripcion as especialidad, 
    u.id as idPaciente, concat(u.nombre, ' ', u.primerApellido, ' ', u.segundoApellido) as paciente, 
    m.id as idMedico, concat(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) as medico, r.fecha, r.hora, 
    r.estado as idEstado, es.descripcion as estado
	FROM resevarcitas r
	JOIN medicos m on r.idMedico = m.id
	JOIN especialidades e on m.idEspecialidad = e.id
	JOIN usuarios u on r.idUsuario = u.id
    JOIN estados es on r.estado = es.id
	WHERE 1 = 1
	AND (filtEstado IS NULL OR r.estado = filtEstado)
	AND (filtEspecialidad IS NULL OR m.idEspecialidad = filtEspecialidad)
	AND (filtMedico IS NULL OR r.idMedico = filtMedico)
	AND (filtFechaInicio IS NULL OR r.fecha >= filtFechaInicio)
	AND (filtFechaFin IS NULL OR r.fecha <= filtFechaFin)
	ORDER BY r.estado ASC, r.fecha ASC, r.hora ASC, r.idUsuario ASC;
END$$
DELIMITER ;

call sp_consultaCitasPacientesFiltrosParaReporte(NULL, NULL, 2, '2026-04-06', '2026-08-07');
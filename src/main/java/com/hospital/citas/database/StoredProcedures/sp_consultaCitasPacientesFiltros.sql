DROP PROCEDURE IF EXISTS sp_consultaCitasPacientesFiltros;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaCitasPacientesFiltros`(IN filtEstado bigint, IN filtEspecialidad bigint, 
IN filtMedico bigint, IN filtFechaInicio date, IN filtFechaFin DATE, IN numeroPagina INT, IN tamannoPagina INT)
BEGIN
	DECLARE cantidadSalto INT;
    SET cantidadSalto = (numeroPagina - 1) * tamannoPagina;
    
	SELECT r.id, e.descripcion as especialidad, 
	concat(u.nombre, ' ', u.primerApellido, ' ', u.segundoApellido) as paciente, 
	concat(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) as medico, r.fecha, r.hora, r.estado,
    date_format(r.fecha, '%d/%m/%Y') as fechaFormateada
	FROM resevarcitas r
	JOIN medicos m on r.idMedico = m.id
	JOIN especialidades e on m.idEspecialidad = e.id
	JOIN usuarios u on r.idUsuario = u.id
	WHERE 1 = 1
	AND (filtEstado IS NULL OR r.estado = filtEstado)
	AND (filtEspecialidad IS NULL OR m.idEspecialidad = filtEspecialidad)
	AND (filtMedico IS NULL OR r.idMedico = filtMedico)
	AND (filtFechaInicio IS NULL OR r.fecha >= filtFechaInicio)
	AND (filtFechaFin IS NULL OR r.fecha <= filtFechaFin)
	ORDER BY r.estado ASC, r.fecha ASC, r.hora ASC, r.idUsuario ASC
    LIMIT cantidadSalto, tamannoPagina;
END$$
DELIMITER ;

call sp_consultaCitasPacientesFiltros(NULL, NULL, 2, '2026-08-06', '2026-08-07', 2, 5);
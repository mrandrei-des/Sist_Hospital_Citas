DROP PROCEDURE IF EXISTS sp_consultaHistorialMedicoPendientePaciente;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaHistorialMedicoPendientePaciente`(IN idUsuario bigint)
BEGIN
	SELECT r.id, e.descripcion as especialidad, concat(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) as medico, r.fecha, r.hora, r.estado
	FROM resevarcitas r
	JOIN medicos m on r.idMedico = m.id
	JOIN especialidades e on m.idEspecialidad = e.id
	WHERE r.idUsuario = idUsuario and r.estado = 1
	ORDER BY r.estado ASC, r.fecha ASC, r.hora ASC;
END$$
DELIMITER ;

call sp_consultaHistorialMedicoPendientePaciente(9)
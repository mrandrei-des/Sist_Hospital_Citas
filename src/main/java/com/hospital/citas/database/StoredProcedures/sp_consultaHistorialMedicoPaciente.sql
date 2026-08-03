DROP PROCEDURE IF EXISTS sp_consultaHistorialMedicoPaciente;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaHistorialMedicoPaciente`(IN idUsuario bigint)
BEGIN
	SELECT r.id, e.descripcion as especialidad, concat(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) as medico, r.fecha, r.hora, r.estado
	FROM resevarcitas r
	JOIN medicos m on r.idMedico = m.id
	JOIN especialidades e on m.idEspecialidad = e.id
	WHERE r.idUsuario = idUsuario
	ORDER BY r.estado ASC, r.fecha ASC, r.hora ASC;
END$$
DELIMITER ;

call sp_consultaHistorialMedicoPaciente(9)
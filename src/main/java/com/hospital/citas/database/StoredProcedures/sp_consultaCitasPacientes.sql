DROP PROCEDURE IF EXISTS sp_consultaCitasPacientes;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaCitasPacientes`()
BEGIN
	SELECT r.id, e.descripcion as especialidad, concat(u.nombre, ' ', u.primerApellido, ' ', u.segundoApellido) as paciente, concat(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) as medico, r.fecha, r.hora, r.estado
	FROM resevarcitas r
	JOIN medicos m on r.idMedico = m.id
	JOIN especialidades e on m.idEspecialidad = e.id
    JOIN usuarios u on r.idUsuario = u.id
	ORDER BY r.estado ASC, r.fecha ASC, r.hora ASC, r.idUsuario ASC;
END$$
DELIMITER ;

call sp_consultaCitasPacientes()
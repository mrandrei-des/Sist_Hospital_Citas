DROP PROCEDURE IF EXISTS sp_consultaEspecialidadesConCitas;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaEspecialidadesConCitas`()
BEGIN
	SELECT m.idEspecialidad, e.descripcion as especialidad
	FROM Medicos m
	JOIN (
		SELECT idMedico 
		FROM resevarcitas 
		GROUP BY idMedico
	) r on m.id = r.idMedico
	JOIN Especialidades e on m.idEspecialidad = e.id and e.estado = 4
	WHERE M.estado = 4
	GROUP BY m.idEspecialidad, e.descripcion
	ORDER BY m.idEspecialidad ASC;
END$$
DELIMITER ;

call sp_consultaEspecialidadesConCitas()
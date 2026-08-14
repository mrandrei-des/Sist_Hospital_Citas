DROP PROCEDURE IF EXISTS sp_consultaMedicosConCitasPorEspecialidad;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaMedicosConCitasPorEspecialidad`(IN idEspecialidad bigint)
BEGIN
	SELECT m.id, m.nombre, m.primerApellido, m.segundoApellido
	FROM Medicos m
	JOIN (
		SELECT idMedico 
		FROM resevarcitas 
		GROUP BY idMedico
	) r on m.id = r.idMedico
	JOIN Especialidades e on m.idEspecialidad = e.id and e.Estado = 4
	WHERE m.estado = 4 and e.id = idEspecialidad
	ORDER BY m.id ASC;
END$$
DELIMITER ;

call sp_consultaMedicosConCitasPorEspecialidad()
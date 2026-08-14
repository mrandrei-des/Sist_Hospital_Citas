DROP PROCEDURE IF EXISTS sp_consultaMedicosConCitas;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultaMedicosConCitas`()
BEGIN
	SELECT m.id, m.nombre, m.primerApellido, m.segundoApellido
	FROM Medicos m
	JOIN (
		SELECT idMedico 
		FROM resevarcitas 
		GROUP BY idMedico
	) r on m.id = r.idMedico	
	WHERE m.estado = 4
	ORDER BY m.id ASC;
END$$
DELIMITER ;

call sp_consultaMedicosConCitas()
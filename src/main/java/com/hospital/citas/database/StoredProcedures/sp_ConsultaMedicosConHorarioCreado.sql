DROP PROCEDURE IF EXISTS sp_ConsultaMedicosConHorarioCreado;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaMedicosConHorarioCreado`()
BEGIN
	SELECT m.id, m.nombre, m.primerApellido, m.segundoApellido, m.idEspecialidad
	FROM Medicos m
	LEFT JOIN (
		SELECT IDMEDICO 
		FROM disponibilidadmedicos
		WHERE Estado = 4
		GROUP BY IDMEDICO
	) d on m.id = d.idmedico
	WHERE COALESCE(d.idmedico, -1) <> -1
	ORDER BY m.primerApellido ASC, m.segundoApellido ASC, m.nombre ASC;
END$$
DELIMITER ;
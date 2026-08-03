DROP PROCEDURE IF EXISTS sp_ConsultaDiasHorarioMedicoPorId;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaDiasHorarioMedicoPorId`(IN idMedico bigint)
BEGIN
	SELECT d.idDiaSemana as id, di.inicial, di.descripcion
	FROM disponibilidadmedicos d
    JOIN diasdelasemana di on d.idDiaSemana = di.id
	WHERE d.idMedico = idMedico and d.estado = 4
	GROUP BY d.idDiaSemana,  di.inicial, di.descripcion
	ORDER BY d.idDiaSemana ASC;
END$$
DELIMITER ;
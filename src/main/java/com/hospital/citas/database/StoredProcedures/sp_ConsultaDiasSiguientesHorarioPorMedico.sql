DROP PROCEDURE IF EXISTS sp_ConsultaDiasSiguientesHorarioPorMedico 
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaDiasSiguientesHorarioPorMedico`(IN idMedico bigint, IN idDiaInicio bigint)
BEGIN
	SELECT d.idDiaSemana as id, di.inicial, di.descripcion
	FROM disponibilidadmedicos d
    JOIN diasdelasemana di on d.idDiaSemana = di.id
	WHERE d.idMedico = idMedico and d.estado = 4 and d.idDiaSemana > idDiaInicio
	GROUP BY d.idDiaSemana,  di.inicial, di.descripcion
	ORDER BY d.idDiaSemana ASC;
END$$
DELIMITER ;

CALL sp_ConsultaDiasSiguientesHorarioPorMedico(2, 4)
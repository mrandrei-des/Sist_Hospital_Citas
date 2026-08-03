DROP PROCEDURE IF EXISTS sp_ConsultaHorarioMedicoPorIdDia;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ConsultaHorarioMedicoPorIdDia`(IN idMedico bigint, IN idDia bigint)
BEGIN
	SELECT d.idMedico, d.idDiaSemana, s.descripcion as dia, d.horaInicioAtencion as horaInicio, d.horaFinAtencion as horaFin
	FROM disponibilidadmedicos d
	JOIN diasdelasemana s on d.idDiaSemana = s.id
	WHERE d.idMedico = idMedico AND d.estado = 4 AND d.idDiaSemana = idDia
	ORDER BY horaInicioAtencion ASC;
END$$
DELIMITER ;
